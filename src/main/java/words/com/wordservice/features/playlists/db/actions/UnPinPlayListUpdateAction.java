package words.com.wordservice.features.playlists.db.actions;

public record UnPinPlayListUpdateAction(
        String userId,
        String playListId
) implements PlayListUpdateAction {
}
