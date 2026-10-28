package words.com.wordservice.features.playlists.main.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import words.com.wordservice.features.playlists.db.actions.PinPlayListUpdateAction;
import words.com.wordservice.features.playlists.db.daos.PlayListDao;
import words.com.wordservice.features.playlists.main.exceptions.PinPlayListException;
import words.com.wordservice.features.playlists.main.models.PinPlayListDto;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class PinPlayListUseCase {
    private final PlayListDao playListDao;

    public void execute(PinPlayListDto dto) {
        var action = new PinPlayListUpdateAction(
                dto.userId(),
                dto.playListId(),
                OffsetDateTime.now()
        );
        if (playListDao.update(action) == 0) {
            throw new PinPlayListException("Failed to pin playlist");
        }
    }
}
