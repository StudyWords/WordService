package words.com.wordservice.main.services;

import org.springframework.data.domain.Page;
import words.com.wordservice.main.models.filters.history.CountLearningHistoryFilter;
import words.com.wordservice.main.models.filters.history.LearningHistoryFilter;
import words.com.wordservice.main.models.filters.history.StatisticsLearningHistoryFilter;
import words.com.wordservice.main.models.history.CountLearningHistory;
import words.com.wordservice.main.models.history.LearningHistory;
import words.com.wordservice.main.models.history.StatisticsLearningHistory;

public interface LearningHistoryService {

    Page<LearningHistory> findBy(LearningHistoryFilter filter);

    Page<StatisticsLearningHistory> findBy(StatisticsLearningHistoryFilter filter);

    Page<CountLearningHistory> findBy(CountLearningHistoryFilter filter);
}
