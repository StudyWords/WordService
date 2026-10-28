package words.com.wordservice.api.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.api.responds.words.WordRespond;
import words.com.wordservice.main.models.words.Word;
import words.com.wordservice.shared.utils.ModelUtils;

@Component
public class WordRespondMapper {

    public WordRespond toRespond(Word model) {
        return ModelUtils.convert(model, WordRespond.class);
    }
}
