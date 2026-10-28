package words.com.wordservice.features.shared.api.valid.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;
import words.com.wordservice.features.shared.api.valid.annotation.NullableNotBlank;

public class NullableNotBlankValidator implements ConstraintValidator<NullableNotBlank, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || StringUtils.hasText(value);
    }
}
