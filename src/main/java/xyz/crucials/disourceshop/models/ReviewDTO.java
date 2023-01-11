package xyz.crucials.disourceshop.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long id;
    private String text;
    private int rate;
    private List<String> likedUsernames;
    private String authorUsername;
}
