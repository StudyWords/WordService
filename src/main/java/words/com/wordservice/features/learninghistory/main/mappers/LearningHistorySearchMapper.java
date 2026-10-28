package words.com.wordservice.features.learninghistory.main.mappers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import words.com.wordservice.features.learninghistory.db.entities.LearningHistoryEntity;
import words.com.wordservice.features.learninghistory.db.searches.LearningHistorySearch;
import words.com.wordservice.features.learninghistory.db.searches.StatisticsLearningHistorySearch;
import words.com.wordservice.features.learninghistory.main.models.LearningHistoryFilter;
import words.com.wordservice.features.learninghistory.main.models.StatisticsLearningHistoryFilter;

@Component
public class LearningHistorySearchMapper {

    public LearningHistorySearch toSearch(LearningHistoryFilter filter) {
        return new LearningHistorySearch(
                filter.userIds(),
                filter.wordIds(),
                filter.date()
        );
    }

    public Pageable toPageable(LearningHistoryFilter filter) {
        Sort dateSort = Sort.sort(LearningHistoryEntity.class).by(LearningHistoryEntity::getCreatedAt).descending();
        return PageRequest.of(
                filter.page(),
                filter.size(),
                dateSort
        );
    }

    public StatisticsLearningHistorySearch toSearch(StatisticsLearningHistoryFilter filter) {
        return StatisticsLearningHistorySearch.builder()
                .userIds(filter.userIds())
                .date(filter.date())
                .build();
    }
}
