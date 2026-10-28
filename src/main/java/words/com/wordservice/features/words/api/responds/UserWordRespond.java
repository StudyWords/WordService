package words.com.wordservice.features.words.api.responds;

import words.com.wordservice.features.words.main.enums.WordType;

import java.time.OffsetDateTime;

public record UserWordRespond(
        String id,
        String userId,
        Long learningGrade,
        OffsetDateTime createdAt,
        OffsetDateTime lastReadDate,
        WordType type,
        WordRespond word
) {
}