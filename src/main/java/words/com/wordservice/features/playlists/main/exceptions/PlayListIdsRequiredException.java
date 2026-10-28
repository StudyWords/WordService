package words.com.wordservice.features.playlists.main.exceptions;

import words.com.wordservice.features.shared.main.exceptions.AppException;

public class PlayListIdsRequiredException extends AppException {
    public PlayListIdsRequiredException(String message) {
        super(message);
    }
}
