package xyz.crucials.disourceshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.crucials.disourceshop.entities.AccountEntity;
import xyz.crucials.disourceshop.exception.UsernameAlreadyInUseException;
import xyz.crucials.disourceshop.models.RegistrationRequest;
import xyz.crucials.disourceshop.services.AccountService;

@RestController
@CrossOrigin
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerAccount(@RequestBody RegistrationRequest registrationRequest) {
        try {
            accountService.register(registrationRequest);
            return ResponseEntity.ok("Successfully created new account");
        }
        catch (UsernameAlreadyInUseException exception) {
            return ResponseEntity.badRequest().body("Account with that username already exists");
        }
    }

}
