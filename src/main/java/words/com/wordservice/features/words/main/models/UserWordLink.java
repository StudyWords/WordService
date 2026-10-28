package words.com.wordservice.features.words.main.models;

public record UserWordLink(
        String userId,
        String wordId,
        String soundFileName,
        String imageFileName
) {
}
