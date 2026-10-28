package words.com.wordservice.features.words.api.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.features.shared.utils.ModelUtils;
import words.com.wordservice.features.words.api.responds.WordRespond;
import words.com.wordservice.features.words.main.models.Word;

@Component
public class WordRespondMapper {

    public WordRespond toRespond(Word model) {
        return ModelUtils.convert(model, WordRespond.class);
    }
}
