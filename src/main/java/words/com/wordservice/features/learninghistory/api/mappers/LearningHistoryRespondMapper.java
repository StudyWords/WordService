package words.com.wordservice.features.learninghistory.api.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.features.learninghistory.api.responds.CountLearningHistoryRespond;
import words.com.wordservice.features.learninghistory.api.responds.LearningHistoryRespond;
import words.com.wordservice.features.learninghistory.api.responds.StatisticsLearningHistoryRespond;
import words.com.wordservice.features.learninghistory.main.models.CountLearningHistory;
import words.com.wordservice.features.learninghistory.main.models.LearningHistory;
import words.com.wordservice.features.learninghistory.main.models.StatisticsLearningHistory;
import words.com.wordservice.features.shared.utils.ModelUtils;

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
