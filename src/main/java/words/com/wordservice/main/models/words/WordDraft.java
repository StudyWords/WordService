package words.com.wordservice.main.models.words;


import lombok.Builder;
import words.com.wordservice.main.models.enums.CEFR;
import words.com.wordservice.main.models.enums.Language;
import words.com.wordservice.main.models.enums.WordType;

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
