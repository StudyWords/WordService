package words.com.wordservice.api.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.api.requests.plan.LearningPlanRequest;
import words.com.wordservice.main.models.LearningPlan;

import java.time.OffsetDateTime;

@Component
public class LearningPlanRequestMapper {

    public LearningPlan toModel(LearningPlanRequest request, String userId) {
        return new LearningPlan(
                userId,
                request.wordsPerDay(),
                request.nativeLang(),
                request.learningLang(),
                request.cefr(),
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );
    }
}
