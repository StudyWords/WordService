package words.com.wordservice.main.exceptions;

import words.com.wordservice.shared.exceptions.AppException;

public class PlayListIdsRequiredException extends AppException {
    public PlayListIdsRequiredException(String message) {
        super(message);
    }
}
