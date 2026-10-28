package words.com.wordservice.features.learninghistory.main.services;

import org.springframework.data.domain.Page;
import words.com.wordservice.features.learninghistory.main.models.*;

public interface LearningHistoryService {

    Page<LearningHistory> findBy(LearningHistoryFilter filter);

    Page<StatisticsLearningHistory> findBy(StatisticsLearningHistoryFilter filter);

    Page<CountLearningHistory> findBy(CountLearningHistoryFilter filter);
}
