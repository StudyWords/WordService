package words.com.wordservice.api.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.api.responds.plan.LearningPlanRespond;
import words.com.wordservice.main.models.LearningPlan;

@Component
public class LearningPlanRespondMapper {

    public LearningPlanRespond toRespond(LearningPlan model) {
        return new LearningPlanRespond(
                model.wordsPerDay(),
                model.nativeLang(),
                model.learningLang(),
                model.cefr(),
                model.createdAt(),
                model.updatedAt()
        );
    }
}
