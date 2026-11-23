package words.com.wordservice.features.learningplan.api.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import words.com.wordservice.features.shared.api.requests.LanguagesGetter;
import words.com.wordservice.features.shared.api.valid.annotation.DifferentLanguage;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;

import java.util.Set;

@DifferentLanguage(message = "Native language and learning language should be different")
public record LearningPlanRequest(
        @Min(value = 1, message = "Words per day should be more than 0")
        @Max(value = 100, message = "Words per day should be less than 100")
        int wordsPerDay,
        @NotNull(message = "Native language should not be null")
        Language nativeLang,
        @NotNull(message = "Learning language should not be null")
        Language learningLang,
        @NotEmpty(message = "CEFR levels should not be empty")
        Set<CEFR> cefrs,
        @Size(max = 64, message = "Zone id should be shorter than 65 characters")
        String zoneId,
        @Min(value = -840, message = "UTC offset should be at least -840 minutes")
        @Max(value = 840, message = "UTC offset should be at most 840 minutes")
        Integer utcOffsetMinutes
) implements LanguagesGetter {
    // Нужны только валидатору @DifferentLanguage; @JsonIgnore убирает их из JSON/Swagger,
    // иначе в схеме появляются дублирующие поля lang и translateLang.
    @JsonIgnore
    @Override
    public Language getLang() {
        return nativeLang;
    }

    @JsonIgnore
    @Override
    public Language getTranslateLang() {
        return learningLang;
    }
}