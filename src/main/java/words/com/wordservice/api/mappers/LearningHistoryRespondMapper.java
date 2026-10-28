package words.com.wordservice.api.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.api.responds.history.CountLearningHistoryRespond;
import words.com.wordservice.api.responds.history.LearningHistoryRespond;
import words.com.wordservice.api.responds.history.StatisticsLearningHistoryRespond;
import words.com.wordservice.main.models.history.CountLearningHistory;
import words.com.wordservice.main.models.history.LearningHistory;
import words.com.wordservice.main.models.history.StatisticsLearningHistory;
import words.com.wordservice.shared.utils.ModelUtils;

@Component
public class LearningHistoryRespondMapper {

    public LearningHistoryRespond toRespond(LearningHistory model) {
        return ModelUtils.convert(model, LearningHistoryRespond.class);
    }

    public StatisticsLearningHistoryRespond toRespond(StatisticsLearningHistory model) {
        return ModelUtils.convert(model, StatisticsLearningHistoryRespond.class);
    }

    public CountLearningHistoryRespond toRespond(CountLearningHistory model) {
        return ModelUtils.convert(model, CountLearningHistoryRespond.class);
    }
}
