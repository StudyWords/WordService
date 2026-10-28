package words.com.wordservice.api.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.api.responds.words.UserWordRespond;
import words.com.wordservice.main.models.words.UserWord;
import words.com.wordservice.shared.utils.ModelUtils;

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
