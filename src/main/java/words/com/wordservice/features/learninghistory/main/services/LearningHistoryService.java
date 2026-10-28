package words.com.wordservice.features.learninghistory.main.services;

import org.springframework.data.domain.Page;
import words.com.wordservice.features.learninghistory.main.models.*;
import words.com.wordservice.features.shared.main.enums.CEFR;

import java.util.Set;

public interface LearningHistoryService {

    Page<LearningHistory> findBy(LearningHistoryFilter filter);

    Page<StatisticsLearningHistory> findBy(StatisticsLearningHistoryFilter filter);

    LearningProgress findProgress(String userId, Set<CEFR> cefrs, int utcOffsetMinutes);
}
