package words.com.wordservice.features.learninghistory.main.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import words.com.wordservice.features.learninghistory.db.entities.LearningHistoryEntity;
import words.com.wordservice.features.learninghistory.db.projections.LearningProgressProjection;
import words.com.wordservice.features.learninghistory.db.projections.StatisticsLearningHistoryProjection;
import words.com.wordservice.features.learninghistory.main.models.LearningHistory;
import words.com.wordservice.features.learninghistory.main.models.LearningProgress;
import words.com.wordservice.features.learninghistory.main.models.StatisticsLearningHistory;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;
import words.com.wordservice.features.shared.main.events.LearningHistoryEvent;
import words.com.wordservice.features.shared.utils.IdGenerator;
import words.com.wordservice.features.words.db.entities.UserWordEntity;
import words.com.wordservice.features.words.db.entities.WordEntity;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LearningHistoryDomainMapper {
    private final IdGenerator idGenerator;

    public LearningHistoryEntity toEntity(LearningHistoryEvent.Entry entry) {
        return new LearningHistoryEntity(
                idGenerator.generate(),
                entry.userId(),
                entry.wordId(),
                entry.createdAt(),
                entry.type(),
                entry.grade()
        );
    }

    public LearningHistory toModel(LearningHistoryEntity entity, UserWordEntity wordEntity) {
        var optWord = Optional.ofNullable(wordEntity).map(UserWordEntity::getWord);

        return new LearningHistory(
                entity.getId(),
                entity.getUserId(),
                entity.getWordId(),
                optWord.map(WordEntity::getOriginal).orElse("Unknown word"),
                optWord.map(WordEntity::getTranslateLang).orElse(Language.UNDEFINED),
                optWord.map(WordEntity::getLang).orElse(Language.UNDEFINED),
                optWord.map(WordEntity::getCefr).orElse(CEFR.A1),
                entity.getCreatedAt().toLocalDate(),
                entity.getType(),
                entity.getGrade()
        );
    }

    public StatisticsLearningHistory toStatisticsModel(StatisticsLearningHistoryProjection entity) {
        return new StatisticsLearningHistory(
                entity.getCount(),
                entity.getGrades(),
                entity.getType(),
                entity.getDate()
        );
    }

    public LearningProgress toModel(LearningProgressProjection projection) {
        return new LearningProgress(
                projection.getCreatedTodayByPlan(),
                projection.getCreatedTotal(),
                projection.getCreatedTotalByPlan(),
                projection.getRepeatedNewTodayByPlan(),
                projection.getRepeatedTotal(),
                projection.getRepeatedTotalByPlan()
        );
    }
}
