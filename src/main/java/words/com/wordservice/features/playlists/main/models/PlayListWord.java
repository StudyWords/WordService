package words.com.wordservice.features.playlists.main.models;

import java.time.OffsetDateTime;

public record PlayListWord(
        String playListId,
        String wordId,
        Long learningGrade,
        OffsetDateTime createdAt,
        OffsetDateTime lastReadDate
) {

}
