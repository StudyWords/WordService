package words.com.wordservice.main.models.words;

import words.com.wordservice.main.models.enums.WordType;

import java.util.Collection;

public record WordDeleteDto(
        Collection<String> ids,
        WordType type
) {


}
