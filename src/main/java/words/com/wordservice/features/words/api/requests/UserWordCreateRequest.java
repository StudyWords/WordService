package words.com.wordservice.features.words.api.requests;

import jakarta.validation.Valid;
import org.springframework.lang.Nullable;
import words.com.wordservice.features.shared.api.valid.annotation.NullableNotBlank;
import words.com.wordservice.features.shared.api.valid.annotation.NullableSize;

public record UserWordCreateRequest(
        @Nullable
        @NullableSize(min = 1, max = 255, message = "Custom Sound File Name must be between 1 and 255 characters")
        @NullableNotBlank(message = "Custom Sound File Name is blank")
        String customSoundFileName,
        @Nullable
        @NullableSize(min = 1, max = 255, message = "Custom Image File Name must be between 1 and 255 characters")
        @NullableNotBlank(message = "Custom Image File Name is blank")
        String customImageFileName,
        @Valid
        WordCreateRequest word
) {
}