package words.com.wordservice.features.words.db.actions;

public record UserWordDeleteAction(
        String id,
        String wordId,
        String userId
) {
}