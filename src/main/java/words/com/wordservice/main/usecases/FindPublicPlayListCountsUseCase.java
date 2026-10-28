package words.com.wordservice.main.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import words.com.wordservice.main.models.enums.PlayListVisibility;
import words.com.wordservice.main.models.filters.WordPlayListCountFilter;
import words.com.wordservice.main.models.playlist.AssignedPlaylist;
import words.com.wordservice.main.models.playlist.WordPlayListCount;
import words.com.wordservice.main.services.WordPlayListService;

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
