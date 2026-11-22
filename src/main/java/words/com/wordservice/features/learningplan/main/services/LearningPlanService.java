package words.com.wordservice.features.learningplan.main.services;

import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import words.com.wordservice.features.learningplan.main.models.LearningPlan;
import words.com.wordservice.features.learningplan.main.models.LearningPlanFilter;

import java.util.Optional;

public interface LearningPlanService {

    default Optional<LearningPlan> findByUserId(String userId) {
        var filter = LearningPlanFilter.builder().userId(userId).page(0).size(1).build();
        return findBy(filter)
                .stream()
                .findFirst();
    }

    Page<LearningPlan> findBy(LearningPlanFilter filter);

    LearningPlan create(LearningPlan learningPlan);

    void update(LearningPlan learningPlan);

    void updateTimeZone(String userId, @Nullable String zoneId, @Nullable Integer utcOffsetMinutes);
}
