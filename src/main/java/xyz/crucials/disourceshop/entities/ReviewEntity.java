package xyz.crucials.disourceshop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.crucials.disourceshop.models.ReviewDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor
@Setter
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String text;
    private int rate;
    private List<String> likedUsernames = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    private AccountEntity authorAccount;
    @ManyToOne(fetch = FetchType.EAGER)
    private ProductEntity targetProduct;

    public ReviewEntity(String text, int rate, AccountEntity authorAccount) {
        this.text = text;
        this.rate = rate;
        this.authorAccount = authorAccount;
    }

    public void like() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentAccountUsername = ((AccountEntity) authentication.getPrincipal()).getUsername();

        if(!likedUsernames.contains(currentAccountUsername)) {
            likedUsernames.add(currentAccountUsername);
        }
        else {
            likedUsernames.remove(currentAccountUsername);
        }
    }

    public static ReviewEntity fromDTO(ReviewDTO reviewDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountEntity currentAccount = (AccountEntity) authentication.getPrincipal();
        return new ReviewEntity(reviewDTO.getText(), reviewDTO.getRate(), currentAccount);
    }
}
