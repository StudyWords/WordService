package words.com.wordservice.main.models.pins;

import java.util.Collection;

public record UnpinOptions(
        Collection<PinnedWordId> pins,
        String userId
) {
}
