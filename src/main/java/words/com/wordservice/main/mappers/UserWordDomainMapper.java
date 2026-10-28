package words.com.wordservice.main.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import words.com.wordservice.db.actions.UserWordGradeUpdateAction;
import words.com.wordservice.db.actions.UserWordUpsertAction;
import words.com.wordservice.db.entities.LearningHistoryEntity;
import words.com.wordservice.db.entities.UserWordEntity;
import words.com.wordservice.db.entities.WordEntity;
import words.com.wordservice.db.searches.UserWordSearch;
import words.com.wordservice.main.models.enums.LearningHistoryType;
import words.com.wordservice.main.models.words.*;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserWordDomainMapper {
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
                StringUtils.hasText(model.id()) ? model.id() : UUID.randomUUID().toString(),
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
                UUID.randomUUID().toString(),
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
                UUID.randomUUID().toString(),
                entity.getUserId(),
                entity.getWord().getId(),
                OffsetDateTime.now(ZoneId.of("UTC")),
                LearningHistoryType.CREATE,
                0
        );
    }

    public LearningHistoryEntity toLearningHistoryEntity(UserWordCreateDto model) {
        return new LearningHistoryEntity(
                UUID.randomUUID().toString(),
                model.userId(),
                model.word().id(),
                OffsetDateTime.now(ZoneId.of("UTC")),
                LearningHistoryType.CREATE,
                0
        );
    }

    public LearningHistoryEntity toLearningHistoryEntity(UserWordLink model) {
        return new LearningHistoryEntity(
                UUID.randomUUID().toString(),
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
