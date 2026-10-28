package words.com.wordservice.features.playlists.api.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import words.com.wordservice.features.playlists.main.enums.WordPlaylistSortField;
import words.com.wordservice.features.shared.api.requests.LongRange;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;

import java.util.Set;

public record PublicPlayListCountGetRequest(
        Set<String> ids,

        String name,
        LongRange count,
        Set<CEFR> cefrs,
        Set<String> tags,
        Language language,
        Language translateLanguage,
        String associationId,

        @NotNull(message = "Sort field must be specified")
        WordPlaylistSortField sortField,
        boolean asc,
        @Min(value = 0, message = "Page must be greater than or equal to 0")
        int page,
        @Min(value = 1, message = "Size must be greater than or equal to 1")
        @Max(value = 100, message = "Size must be less than or equal to 100")
        int size
) {
}