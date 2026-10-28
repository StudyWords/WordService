package words.com.wordservice.main.models.words;

public record UserWordLink(
        String userId,
        String wordId,
        String soundFileName,
        String imageFileName
) {
}
