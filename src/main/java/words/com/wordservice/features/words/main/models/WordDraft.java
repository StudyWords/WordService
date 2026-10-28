package words.com.wordservice.features.words.main.models;

import lombok.Builder;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;
import words.com.wordservice.features.words.main.enums.WordType;

@Builder(toBuilder = true)
public record WordDraft(
        String id,
        String original,
        Language lang,
        String translate,
        Language translateLang,
        String category,
        String soundFileName,
        String imageFileName,
        WordType type,
        String description,
        CEFR cefr
) {

}
