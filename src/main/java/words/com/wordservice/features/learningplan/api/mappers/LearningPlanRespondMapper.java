package words.com.wordservice.features.learningplan.api.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.features.learningplan.api.responds.LearningPlanDetailsRespond;
import words.com.wordservice.features.learningplan.api.responds.LearningPlanRespond;
import words.com.wordservice.features.learningplan.api.responds.LearningProgressRespond;
import words.com.wordservice.features.learningplan.main.models.LearningPlan;
import words.com.wordservice.features.learningplan.main.models.LearningPlanDetails;

@Component
public class LearningPlanRespondMapper {

    public LearningPlanRespond toRespond(LearningPlan model) {
        return new LearningPlanRespond(
                model.wordsPerDay(),
                model.nativeLang(),
                model.learningLang(),
                model.cefrs(),
                model.createdAt(),
                model.updatedAt()
        );
    }

    public LearningPlanDetailsRespond toRespond(LearningPlanDetails details) {
        var plan = details.plan();
        var progress = details.progress();
        return new LearningPlanDetailsRespond(
                plan.wordsPerDay(),
                plan.nativeLang(),
                plan.learningLang(),
                plan.cefrs(),
                plan.createdAt(),
                plan.updatedAt(),
                new LearningProgressRespond(
                        progress.createdTodayByPlan(),
                        progress.createdTotal(),
                        progress.createdTotalByPlan(),
                        progress.repeatedNewTodayByPlan(),
                        progress.repeatedTotal(),
                        progress.repeatedTotalByPlan()
                )
        );
    }
}
