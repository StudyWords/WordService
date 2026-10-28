package words.com.wordservice.features.learningplan.api.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.features.learningplan.api.responds.LearningPlanRespond;
import words.com.wordservice.features.learningplan.main.models.LearningPlan;

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
