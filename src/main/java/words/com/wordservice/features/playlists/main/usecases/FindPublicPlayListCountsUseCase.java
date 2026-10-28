package words.com.wordservice.features.playlists.main.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import words.com.wordservice.features.playlists.main.enums.PlayListVisibility;
import words.com.wordservice.features.playlists.main.models.AssignedPlaylist;
import words.com.wordservice.features.playlists.main.models.WordPlayListCount;
import words.com.wordservice.features.playlists.main.models.WordPlayListCountFilter;
import words.com.wordservice.features.playlists.main.services.WordPlayListService;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FindPublicPlayListCountsUseCase {
    private static final String SYSTEM_USER_ID = "system";

    private final WordPlayListService wordPlayListService;

    public Page<WordPlayListCount> execute(@Nullable String userId, WordPlayListCountFilter filter) {
        var builder = filter.toBuilder()
                .userId(SYSTEM_USER_ID)
                .visibility(PlayListVisibility.PUBLIC);

        if (userId != null) {
            var assignedIds = wordPlayListService.findAssigned(userId)
                    .stream()
                    .map(AssignedPlaylist::id)
                    .collect(Collectors.toSet());
            builder.notInIds(assignedIds);
        }

        return wordPlayListService.findBy(builder.build());
    }
}
