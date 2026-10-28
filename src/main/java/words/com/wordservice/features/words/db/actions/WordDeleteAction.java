package words.com.wordservice.features.words.db.actions;

import words.com.wordservice.features.words.main.enums.WordType;

public record WordDeleteAction(
        String id,
        WordType type
) {
}