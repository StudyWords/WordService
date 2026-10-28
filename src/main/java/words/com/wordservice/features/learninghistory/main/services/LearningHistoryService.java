package words.com.wordservice.features.learninghistory.main.services;

import org.springframework.data.domain.Page;
import words.com.wordservice.features.learninghistory.main.models.*;
import words.com.wordservice.features.shared.main.enums.CEFR;

public interface LearningHistoryService {

    Page<LearningHistory> findBy(LearningHistoryFilter filter);

    Page<StatisticsLearningHistory> findBy(StatisticsLearningHistoryFilter filter);

    LearningProgress findProgress(String userId, CEFR cefr, int utcOffsetMinutes);
}
