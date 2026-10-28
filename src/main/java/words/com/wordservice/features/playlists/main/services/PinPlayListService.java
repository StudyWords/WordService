package words.com.wordservice.features.playlists.main.services;

import words.com.wordservice.features.playlists.main.models.UserPinIds;
import words.com.wordservice.features.playlists.main.models.UserPins;

public interface PinPlayListService {

    void pin(UserPins userPins);

    void unpin(UserPinIds userPins);
}
