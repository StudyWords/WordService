package words.com.wordservice.playlist.main.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import words.com.wordservice.db.daos.PlayListDao;
import words.com.wordservice.playlist.db.actions.UnPinPlayListUpdateAction;
import words.com.wordservice.playlist.main.exceptions.UnPinPlayListException;
import words.com.wordservice.playlist.main.models.UnPinPlayListDto;

@Component
@RequiredArgsConstructor
public class UpPinPlayListUseCase {
    private final PlayListDao playListDao;

    public void execute(UnPinPlayListDto dto) {
        var action = new UnPinPlayListUpdateAction(
                dto.userId(),
                dto.playListId()
        );
        if (playListDao.update(action) == 0) {
            throw new UnPinPlayListException("Failed to unpin playlist");
        }
    }
}
