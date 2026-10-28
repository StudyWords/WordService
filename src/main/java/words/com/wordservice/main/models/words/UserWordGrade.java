package words.com.wordservice.main.models.words;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserWordGrade(
        String userWordId,
        String userId,
        long value
) {

}