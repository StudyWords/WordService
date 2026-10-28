package words.com.wordservice.main.services;

import words.com.wordservice.main.models.pins.UserPinIds;
import words.com.wordservice.main.models.pins.UserPins;

public interface PinPlayListService {

    void pin(UserPins userPins);

    void unpin(UserPinIds userPins);
}
