package xyz.crucials.disourceshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.crucials.disourceshop.entities.AccountEntity;
import xyz.crucials.disourceshop.exception.UsernameAlreadyInUseException;
import xyz.crucials.disourceshop.models.RegistrationRequest;
import xyz.crucials.disourceshop.repositories.AccountRepository;

@Service
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username " + username + " not found"));
    }

    public void register(RegistrationRequest registrationRequest) {
        String username = registrationRequest.getUsername();
        if(accountRepository.findByUsername(username).isEmpty()) {
            accountRepository.save(new AccountEntity(registrationRequest.getUsername(),
                    passwordEncoder.encode(registrationRequest.getPassword()), true));
        }
        else {
            throw new UsernameAlreadyInUseException(username);
        }
    }

}
