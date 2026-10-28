package words.com.wordservice.main.models.playlist;

import java.util.List;

public record DeletePlayList(
        String userId,
        List<String> ids
) {
}
