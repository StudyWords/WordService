package words.com.wordservice.main.models.pins;

import java.util.Collection;

public record UserPinIds(
        Collection<PinnedWordId> pins,
        String userId
) {
}
