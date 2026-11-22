package words.com.wordservice.features.learningplan.main.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.features.learningplan.db.entities.LearningPlanEntity;
import words.com.wordservice.features.learningplan.main.models.LearningPlan;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.TreeSet;

@Component
public class LearningPlanDomainMapper {

    public LearningPlanEntity toEntity(LearningPlan model) {
        return new LearningPlanEntity(
                model.userId(),
                model.wordsPerDay(),
                model.nativeLang(),
                model.learningLang(),
                model.cefrs() == null ? new TreeSet<>() : new TreeSet<>(model.cefrs()),
                model.zoneId(),
                model.utcOffsetMinutes() == null ? 0 : model.utcOffsetMinutes(),
                model.createdAt(),
                OffsetDateTime.now()
        );
    }

    public LearningPlan toModel(LearningPlanEntity entity) {
        return new LearningPlan(
                entity.getUserId(),
                entity.getWordsPerDay(),
                entity.getNativeLang(),
                entity.getLearningLang(),
                entity.getCefrs() == null ? Set.of() : entity.getCefrs(),
                entity.getZoneId(),
                entity.getUtcOffsetMinutes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
