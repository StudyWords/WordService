package words.com.wordservice.features.words.main.exceptions;

import words.com.wordservice.features.shared.main.exceptions.AppException;

public class UserWordNotFoundException extends AppException {
    public UserWordNotFoundException(String message) {
        super(message);
    }
}
