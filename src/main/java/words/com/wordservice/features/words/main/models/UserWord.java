package words.com.wordservice.features.words.main.models;

import lombok.Builder;
import words.com.wordservice.features.words.main.enums.WordType;

import java.time.OffsetDateTime;

@Builder(toBuilder = true)
public record UserWord(
        String id,
        String userId,
        Long learningGrade,
        OffsetDateTime createdAt,
        OffsetDateTime lastReadDate,
        WordType type,
        Word word
) {
}
