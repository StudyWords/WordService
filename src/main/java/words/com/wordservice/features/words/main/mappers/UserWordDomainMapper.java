package words.com.wordservice.features.words.main.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import words.com.wordservice.features.learninghistory.db.entities.LearningHistoryEntity;
import words.com.wordservice.features.learninghistory.main.enums.LearningHistoryType;
import words.com.wordservice.features.shared.utils.IdGenerator;
import words.com.wordservice.features.words.db.actions.UserWordGradeUpdateAction;
import words.com.wordservice.features.words.db.actions.UserWordUpsertAction;
import words.com.wordservice.features.words.db.entities.UserWordEntity;
import words.com.wordservice.features.words.db.entities.WordEntity;
import words.com.wordservice.features.words.db.searches.UserWordSearch;
import words.com.wordservice.features.words.main.models.*;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class UserWordDomainMapper {
    private final IdGenerator idGenerator;
    private final WordDomainMapper wordDomainMapper;

    public UserWord toModel(UserWordEntity entity) {
        return new UserWord(
                entity.getId(),
                entity.getUserId(),
                entity.getLearningGrade(),
                entity.getCreatedAt(),
                entity.getLastReadDate(),
                entity.getWord().getType(),
                wordDomainMapper.toModel(entity.getWord(), entity.getCustomSoundFileName(), entity.getCustomImageFileName())
        );
    }

    public UserWordEntity toEntity(UserWordCreateDto model) {
        return new UserWordEntity(
                StringUtils.hasText(model.id()) ? model.id() : idGenerator.generate(),
                model.userId(),
                0,
                model.soundFileName(),
                model.imageFileName(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                wordDomainMapper.toEntity(model.word())
        );
    }

    public UserWordUpsertAction toAction(UserWordLink model) {
        return new UserWordUpsertAction(
                idGenerator.generate(),
                model.userId(),
                model.wordId(),
                model.imageFileName(),
                model.soundFileName()
        );
    }

    public UserWordGradeUpdateAction toAction(UserWordGrade model) {
        return new UserWordGradeUpdateAction(
                model.userWordId(),
                model.userId(),
                model.value()
        );
    }

    public UserWordSearch toSearch(UserWordDeleteDto options) {
        return UserWordSearch.builder()
                .userWordId(options.id())
                .wordId(options.wordId())
                .userId(options.userId())
                .build();
    }

    public LearningHistoryEntity toLearningHistoryEntity(UserWordEntity entity) {
        return new LearningHistoryEntity(
                idGenerator.generate(),
                entity.getUserId(),
                entity.getWord().getId(),
                OffsetDateTime.now(ZoneId.of("UTC")),
                LearningHistoryType.CREATE,
                0
        );
    }

    public LearningHistoryEntity toLearningHistoryEntity(UserWordCreateDto model) {
        return new LearningHistoryEntity(
                idGenerator.generate(),
                model.userId(),
                model.word().id(),
                OffsetDateTime.now(ZoneId.of("UTC")),
                LearningHistoryType.CREATE,
                0
        );
    }

    public LearningHistoryEntity toLearningHistoryEntity(UserWordLink model) {
        return new LearningHistoryEntity(
                idGenerator.generate(),
                model.userId(),
                model.wordId(),

                OffsetDateTime.now(ZoneId.of("UTC")),
                LearningHistoryType.CREATE,
                0
        );
    }

    public UserWordEntity toEntity(UserWordEntity old, UserWordEditDto dto) {
        return old.toBuilder()
                .customSoundFileName(dto.soundFileName())
                .customImageFileName(dto.imageFileName())
                .build();
    }

    public WordEntity toEntity(WordEntity old, UserWordEditDto dto) {
        return old.toBuilder()
                .original(dto.original())
                .lang(dto.lang())
                .translate(dto.translate())
                .translateLang(dto.translateLang())
                .category(dto.category())
                .description(dto.description())
                .cefr(dto.cefr())
                .build();
    }
}
