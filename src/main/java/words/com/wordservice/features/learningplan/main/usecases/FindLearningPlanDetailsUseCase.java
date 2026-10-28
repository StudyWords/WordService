package words.com.wordservice.features.learningplan.main.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import words.com.wordservice.features.learninghistory.main.services.LearningHistoryService;
import words.com.wordservice.features.learningplan.main.models.LearningPlanDetails;
import words.com.wordservice.features.learningplan.main.services.LearningPlanService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindLearningPlanDetailsUseCase {
    private final LearningPlanService learningPlanService;
    private final LearningHistoryService learningHistoryService;

    public Optional<LearningPlanDetails> execute(String userId, int utcOffsetMinutes) {
        return learningPlanService.findByUserId(userId)
                .map(plan -> new LearningPlanDetails(
                        plan,
                        learningHistoryService.findProgress(userId, plan.cefr(), utcOffsetMinutes)
                ));
    }
}
