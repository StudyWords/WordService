package words.com.wordservice.main.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import words.com.wordservice.db.actions.UserWordGradeUpdateAction;
import words.com.wordservice.db.entities.*;
import words.com.wordservice.db.projections.PlayListCountProjection;
import words.com.wordservice.main.models.enums.CEFR;
import words.com.wordservice.main.models.enums.LearningHistoryType;
import words.com.wordservice.main.models.enums.PlayListVisibility;
import words.com.wordservice.main.models.playlist.PlayListGrade;
import words.com.wordservice.main.models.playlist.PlayListTitle;
import words.com.wordservice.main.models.playlist.WordPlayList;
import words.com.wordservice.main.models.playlist.WordPlayListCount;
import words.com.wordservice.main.models.words.PinnedWord;
import words.com.wordservice.main.utils.IdGenerator;
import words.com.wordservice.shared.utils.ModelUtils;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

@Component
@RequiredArgsConstructor
public class WordPlayListDomainMapper {
    private final IdGenerator idGenerator;
    private final UserWordDomainMapper userWordDomainMapper;

    public WordPlayList toModel(WordPlayListEntity entity) {
        return toModel(entity, Collections.emptyMap());

    }

    public WordPlayList toModel(WordPlayListEntity entity, Map<String, List<PinnedWordEntity>> pinnedWords) {
        return new WordPlayList(
                entity.getId(),
                entity.getUserId(),
                entity.getName(),
                entity.getCreatedAt(),
                pinnedWords.getOrDefault(entity.getId(), Collections.emptyList()).stream().map(this::toPinnedWordModel).toList(),
                entity.getTags(),
                entity.getCefrs(),
                entity.getLanguage(),
                entity.getTranslateLanguage(),
                entity.getBaseId(),
                entity.getPinnedAt()
        );
    }

    public WordPlayListCount toModel(PlayListCountProjection projection) {
        return new WordPlayListCount(
                projection.getId(),
                projection.getUserId(),
                projection.getName(),
                projection.getCreatedAt().atOffset(ZoneOffset.UTC),
                projection.getCount(),
                parseStringTreeSet(projection.getTags()),
                parseCefrTreeSet(projection.getCefrs()),
                projection.getLanguage(),
                projection.getTranslateLanguage(),
                Optional.ofNullable(projection.getPinnedAt())
                        .map(instant -> instant.atOffset(ZoneOffset.UTC))
                        .orElse(null)
        );
    }

    private TreeSet<String> parseStringTreeSet(String json) {
        if (json == null || json.isEmpty() || "[]".equals(json)) {
            return new TreeSet<>();
        }
        try {
            return ModelUtils.readTreeSet(json, String.class);
        } catch (Exception e) {
            return new TreeSet<>();
        }
    }

    private TreeSet<CEFR> parseCefrTreeSet(String json) {
        if (json == null || json.isEmpty() || "[]".equals(json)) {
            return new TreeSet<>();
        }
        try {
            return ModelUtils.readTreeSet(json, CEFR.class);
        } catch (Exception e) {
            return new TreeSet<>();
        }
    }

    private PinnedWord toPinnedWordModel(PinnedWordEntity entity) {
        return new PinnedWord(
                entity.getLearningGrade(),
                entity.getCreatedAt(),
                entity.getLastReadDate(),
                userWordDomainMapper.toModel(entity.getWord())
        );
    }

    public WordPlayListEntity toEntity(PlayListTitle model) {
        return new WordPlayListEntity(
                model.id(),
                model.userId(),
                model.name(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                null,
                null,
                null,
                null,
                PlayListVisibility.PRIVATE,
                null,
                null,
                null
        );
    }

    public WordPlayListEntity toAssignEntity(WordPlayListEntity entity, String userId) {
        return new WordPlayListEntity(
                idGenerator.generate(),
                userId,
                entity.getName(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                entity.getTags(),
                entity.getCefrs(),
                entity.getLanguage(),
                entity.getTranslateLanguage(),
                PlayListVisibility.PRIVATE,
                entity.getId(),
                entity.getAssociationId(),
                null
        );
    }


    public WordPlayListEntity toUpdateEntity(PlayListTitle model) {
        return new WordPlayListEntity(
                model.id(),
                model.userId(),
                model.name(),
                null,
                OffsetDateTime.now(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public UserWordGradeUpdateAction toAction(PlayListGrade model) {
        return new UserWordGradeUpdateAction(
                model.wordId(),
                model.userId(),
                model.wordGrade()
        );
    }

    public LearningHistoryEntity toEntity(PlayListGrade model) {
        return new LearningHistoryEntity(
                idGenerator.generate(),
                model.userId(),
                model.wordId(),
                OffsetDateTime.now(ZoneId.of("UTC")),
                LearningHistoryType.UPDATE,
                model.wordGrade()
        );
    }

    public UserWordEntity toUserWordEntity(String userId, WordEntity word) {
        assert word != null : "Word entity is null";
        return UserWordEntity.builder()
                .id(idGenerator.generate())
                .userId(userId)
                .learningGrade(0L)
                .createdAt(OffsetDateTime.now())
                .lastReadDate(OffsetDateTime.now())
                .word(word)
                .build();
    }

    public PinnedWordEntity toPinnedWordEntity(String playListId, PinnedWordEntity pin, UserWordEntity userWordEntity) {
        var id = new PinnedWordEntity.PinnedWordId(playListId, userWordEntity.getId());

        return PinnedWordEntity.builder()
                .id(id)
                .learningGrade(pin.getLearningGrade())
                .createdAt(OffsetDateTime.now())
                .lastReadDate(OffsetDateTime.now())
                .word(userWordEntity)
                .build();

    }
}
