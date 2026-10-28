package words.com.wordservice.main.models.playlist;

import java.util.List;

public record UserPlayLists(
        String userId,
        List<String> ids
) {
}
