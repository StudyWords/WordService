package words.com.wordservice.features.playlists.api.requests;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record AssignPlayListsRequest(
        @NotEmpty(message = "Select at least one playlist")
        Set<String> playListIds
) {
}
