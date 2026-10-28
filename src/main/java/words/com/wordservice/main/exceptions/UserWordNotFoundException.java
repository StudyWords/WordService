package words.com.wordservice.main.exceptions;

import words.com.wordservice.shared.exceptions.AppException;

public class UserWordNotFoundException extends AppException {
    public UserWordNotFoundException(String message) {
        super(message);
    }
}
