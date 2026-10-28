package words.com.wordservice.db.actions;

import words.com.wordservice.main.models.enums.WordType;

public record WordDeleteAction(
        String id,
        WordType type
) {
}