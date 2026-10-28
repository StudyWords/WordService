package words.com.wordservice.main.models.words;

public record UserWordDeleteDto(
        String id,
        String userId,
        String wordId
) {
}
