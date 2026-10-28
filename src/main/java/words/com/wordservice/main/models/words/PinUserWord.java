package words.com.wordservice.main.models.words;

public record PinUserWord(
        String userId,
        String wordId,
        String soundFileName,
        String imageFileName
) {
}
