package words.com.wordservice.features.shared.api.valid.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import words.com.wordservice.features.shared.api.requests.LanguagesGetter;
import words.com.wordservice.features.shared.api.valid.annotation.DifferentLanguage;

import java.util.Objects;

public class DifferentLanguageValidator implements ConstraintValidator<DifferentLanguage, LanguagesGetter> {
    @Override
    public boolean isValid(LanguagesGetter languagesGetter, ConstraintValidatorContext constraintValidatorContext) {
        return !Objects.equals(languagesGetter.getLang(), languagesGetter.getTranslateLang());
    }
}
