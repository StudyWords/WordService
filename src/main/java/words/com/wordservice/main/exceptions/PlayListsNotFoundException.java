package words.com.wordservice.main.exceptions;

import words.com.wordservice.shared.exceptions.AppException;

public class PlayListsNotFoundException extends AppException {
    public PlayListsNotFoundException(String message) {
        super(message);
    }
}
