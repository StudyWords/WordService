package words.com.wordservice.main.models.pins;

import words.com.wordservice.main.models.playlist.PlayListWord;

import java.util.Collection;

public record UserPins(
        Collection<PlayListWord> pins,
        String userId
) {
}
