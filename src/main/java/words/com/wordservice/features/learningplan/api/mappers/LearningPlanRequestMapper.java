package words.com.wordservice.features.learningplan.api.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.features.learningplan.api.requests.LearningPlanRequest;
import words.com.wordservice.features.learningplan.main.models.LearningPlan;

import java.time.OffsetDateTime;

@Component
public class LearningPlanRequestMapper {

    public LearningPlan toModel(LearningPlanRequest request, String userId) {
        return new LearningPlan(
                userId,
                request.wordsPerDay(),
                request.nativeLang(),
                request.learningLang(),
                request.cefrs(),
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );
    }
}
