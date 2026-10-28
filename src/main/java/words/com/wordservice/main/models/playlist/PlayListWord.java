package words.com.wordservice.main.models.playlist;

import java.time.OffsetDateTime;


public record PlayListWord(
        String playListId,
        String wordId,
        Long learningGrade,
        OffsetDateTime createdAt,
        OffsetDateTime lastReadDate
) {

}
