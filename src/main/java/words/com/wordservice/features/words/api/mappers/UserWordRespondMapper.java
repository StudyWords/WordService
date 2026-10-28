package words.com.wordservice.features.words.api.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.features.shared.utils.ModelUtils;
import words.com.wordservice.features.words.api.responds.UserWordRespond;
import words.com.wordservice.features.words.main.models.UserWord;

import java.util.Collection;
import java.util.List;

@Component
public class UserWordRespondMapper {

    public UserWordRespond toRespond(UserWord model) {
        return ModelUtils.convert(model, UserWordRespond.class);
    }

    public List<UserWordRespond> toResponds(Collection<UserWord> models) {
        return models.stream()
                .map(this::toRespond)
                .toList();
    }
}
