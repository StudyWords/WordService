package words.com.wordservice.features.words.main.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import words.com.wordservice.features.shared.main.enums.LearningHistoryType;
import words.com.wordservice.features.shared.main.events.LearningHistoryEvent;
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

    public LearningHistoryEvent.Entry toHistoryEntry(UserWordEntity entity) {
        return new LearningHistoryEvent.Entry(
                entity.getUserId(),
                entity.getWord().getId(),
                LearningHistoryType.CREATE,
                0,
                OffsetDateTime.now(ZoneId.of("UTC"))
        );
    }

    public LearningHistoryEvent.Entry toHistoryEntry(UserWordCreateDto model) {
        return new LearningHistoryEvent.Entry(
                model.userId(),
                model.word().id(),
                LearningHistoryType.CREATE,
                0,
                OffsetDateTime.now(ZoneId.of("UTC"))
        );
    }

    public LearningHistoryEvent.Entry toHistoryEntry(UserWordLink model) {
        return new LearningHistoryEvent.Entry(
                model.userId(),
                model.wordId(),
                LearningHistoryType.CREATE,
                0,
                OffsetDateTime.now(ZoneId.of("UTC"))
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
