package words.com.wordservice.features.learningplan.api.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import words.com.wordservice.features.shared.api.requests.LanguagesGetter;
import words.com.wordservice.features.shared.api.valid.annotation.DifferentLanguage;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;

@DifferentLanguage(message = "Native language and learning language should be different")
public record LearningPlanRequest(
        @Min(value = 1, message = "Words per day should be more than 0")
        @Max(value = 100, message = "Words per day should be less than 100")
        int wordsPerDay,
        @NotNull(message = "Native language should not be null")
        Language nativeLang,
        @NotNull(message = "Learning language should not be null")
        Language learningLang,
        @NotNull(message = "CEFR level should not be null")
        CEFR cefr
) implements LanguagesGetter {
    @Override
    public Language getLang() {
        return nativeLang;
    }

    @Override
    public Language getTranslateLang() {
        return learningLang;
    }
}