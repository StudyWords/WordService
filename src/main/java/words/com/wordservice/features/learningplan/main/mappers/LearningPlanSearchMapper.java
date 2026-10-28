package words.com.wordservice.features.learningplan.main.mappers;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import words.com.wordservice.features.learningplan.db.searches.LearningPlanSearch;
import words.com.wordservice.features.learningplan.main.models.LearningPlanFilter;

@Component
public class LearningPlanSearchMapper {

    public LearningPlanSearch toSearch(LearningPlanFilter filter) {
        return new LearningPlanSearch(filter.userIds());
    }

    public Pageable toPageable(LearningPlanFilter filter) {
        return Pageable.ofSize(filter.size()).withPage(filter.page());
    }
}
