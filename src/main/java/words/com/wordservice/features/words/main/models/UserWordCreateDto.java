package words.com.wordservice.features.words.main.models;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserWordCreateDto(
        String id,
        String userId,

        String soundFileName,
        String imageFileName,
        WordDraft word
) {

}
