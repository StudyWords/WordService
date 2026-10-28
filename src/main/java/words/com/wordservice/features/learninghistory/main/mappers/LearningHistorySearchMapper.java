package words.com.wordservice.features.learninghistory.main.mappers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import words.com.wordservice.features.learninghistory.db.entities.LearningHistoryEntity;
import words.com.wordservice.features.learninghistory.db.searches.CountLearningHistorySearch;
import words.com.wordservice.features.learninghistory.db.searches.LearningHistorySearch;
import words.com.wordservice.features.learninghistory.db.searches.StatisticsLearningHistorySearch;
import words.com.wordservice.features.learninghistory.main.models.CountLearningHistoryFilter;
import words.com.wordservice.features.learninghistory.main.models.LearningHistoryFilter;
import words.com.wordservice.features.learninghistory.main.models.StatisticsLearningHistoryFilter;
import words.com.wordservice.features.shared.utils.ModelUtils;
import words.com.wordservice.features.shared.utils.Range;

import java.time.LocalDate;
import java.time.OffsetDateTime;

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
                .date(toLocalDateRange(filter.date()))
                .build();
    }

    private Range<LocalDate> toLocalDateRange(Range<OffsetDateTime> range) {
        if (range == null) {
            return null;
        }
        return Range.<LocalDate>builder()
                .from(range.getFrom() != null ? range.getFrom().toLocalDate() : null)
                .to(range.getTo() != null ? range.getTo().toLocalDate() : null)
                .build();
    }

    public CountLearningHistorySearch toSearch(CountLearningHistoryFilter filter) {
        return ModelUtils.convert(filter, CountLearningHistorySearch.class);
    }
}
