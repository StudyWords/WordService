package words.com.wordservice.main.models.words;

import lombok.Builder;


@Builder(toBuilder = true)
public record UserWordCreateDto(
        String id,
        String userId,

        String soundFileName,
        String imageFileName,
        ModifyWord word
) {

}
