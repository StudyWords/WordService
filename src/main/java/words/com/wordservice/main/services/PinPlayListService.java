package words.com.wordservice.main.services;

import words.com.wordservice.main.models.pins.PinOptions;
import words.com.wordservice.main.models.pins.UnpinOptions;

public interface PinPlayListService {

    void pin(PinOptions options);

    void unpin(UnpinOptions options);
}
