package words.com.wordservice.features.words.main.models;

import words.com.wordservice.features.words.main.enums.WordType;

import java.util.Collection;

public record WordDeleteDto(
        Collection<String> ids,
        WordType type
) {


}
