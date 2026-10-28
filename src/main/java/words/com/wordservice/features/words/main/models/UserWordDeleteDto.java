package words.com.wordservice.features.words.main.models;

public record UserWordDeleteDto(
        String id,
        String userId,
        String wordId
) {
}
