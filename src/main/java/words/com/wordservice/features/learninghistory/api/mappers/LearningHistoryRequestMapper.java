package words.com.wordservice.features.learninghistory.api.mappers;

import org.springframework.stereotype.Component;
import ua.nure.words.gateway.userctx.Role;
import ua.nure.words.gateway.userctx.User;
import words.com.wordservice.features.learninghistory.api.requests.LearningHistoryFilterRequest;
import words.com.wordservice.features.learninghistory.api.requests.StatisticsLearningHistoryFilterRequest;
import words.com.wordservice.features.learninghistory.main.models.LearningHistoryFilter;
import words.com.wordservice.features.learninghistory.main.models.StatisticsLearningHistoryFilter;
import words.com.wordservice.features.shared.utils.ModelUtils;

import java.util.Collections;

@Component
public class LearningHistoryRequestMapper {

    public LearningHistoryFilter toModel(User user, LearningHistoryFilterRequest request){
        var filter = ModelUtils.convert(request, LearningHistoryFilter.class);
        if (user.role() == Role.ADMINISTRATION){
            return filter;
        }
        return filter.toBuilder()
                .userIds(Collections.singleton(user.id()))
                .build();
    }

    public StatisticsLearningHistoryFilter toModel(User user, StatisticsLearningHistoryFilterRequest request){
        var filter = ModelUtils.convert(request, StatisticsLearningHistoryFilter.class);
        if (user.role() == Role.ADMINISTRATION){
            return filter;
        }
        return filter.toBuilder()
                .userIds(Collections.singleton(user.id()))
                .build();
    }
}
