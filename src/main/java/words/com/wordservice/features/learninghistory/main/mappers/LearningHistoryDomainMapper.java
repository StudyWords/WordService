package words.com.wordservice.features.learninghistory.main.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.features.learninghistory.db.entities.LearningHistoryEntity;
import words.com.wordservice.features.learninghistory.db.projections.CountLearningHistoryProjection;
import words.com.wordservice.features.learninghistory.db.projections.StatisticsLearningHistoryProjection;
import words.com.wordservice.features.learninghistory.main.models.CountLearningHistory;
import words.com.wordservice.features.learninghistory.main.models.LearningHistory;
import words.com.wordservice.features.learninghistory.main.models.StatisticsLearningHistory;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;
import words.com.wordservice.features.words.db.entities.UserWordEntity;
import words.com.wordservice.features.words.db.entities.WordEntity;

import java.util.Optional;

@Component
public class LearningHistoryDomainMapper {

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

    public CountLearningHistory toModel(CountLearningHistoryProjection entity) {
        return new CountLearningHistory(
                entity.getCount(),
                entity.getType()
        );
    }
}
