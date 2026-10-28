package words.com.wordservice.features.words.db.actions;

import lombok.Builder;

@Builder
public record UserWordGradeUpdateAction(
        String userWordId,
        String userId,
        long value
) {
}
