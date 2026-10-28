package words.com.wordservice.features.words.main.models;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder(toBuilder = true)
public record PinnedWord(
        Long learningGrade,
        OffsetDateTime createdAt,
        OffsetDateTime lastReadDate,
        UserWord word
) {
}
