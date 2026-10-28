package words.com.wordservice.api.mappers;

import org.springframework.stereotype.Component;
import words.backend.authmodule.net.models.Role;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.requests.history.CountLearningHistoryFilterRequest;
import words.com.wordservice.api.requests.history.LearningHistoryFilterRequest;
import words.com.wordservice.api.requests.history.StatisticsLearningHistoryFilterRequest;
import words.com.wordservice.main.models.filters.history.CountLearningHistoryFilter;
import words.com.wordservice.main.models.filters.history.LearningHistoryFilter;
import words.com.wordservice.main.models.filters.history.StatisticsLearningHistoryFilter;
import words.com.wordservice.shared.utils.ModelUtils;

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

    public CountLearningHistoryFilter toModel(User user, CountLearningHistoryFilterRequest request){
        var filter = ModelUtils.convert(request, CountLearningHistoryFilter.class);
        if (user.role() == Role.ADMINISTRATION){
            return filter;
        }
        return filter.toBuilder()
                .userIds(Collections.singleton(user.id()))
                .build();
    }
}
