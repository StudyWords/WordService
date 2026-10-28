package words.com.wordservice.features.words.main.models;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserWordGrade(
        String userWordId,
        String userId,
        long value
) {

}