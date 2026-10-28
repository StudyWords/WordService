package words.com.wordservice.features.words.db.actions;

public record UserWordUpsertAction(
        String id,
        String userId,
        String wordId,
        String customImageFileName,
        String customSoundFileName
) {
}
