package words.com.wordservice.features.playlists.main.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import words.com.wordservice.features.playlists.db.entities.WordPlayListEntity;
import words.com.wordservice.features.playlists.db.projections.PlayListCountProjection;
import words.com.wordservice.features.playlists.main.enums.PlayListVisibility;
import words.com.wordservice.features.playlists.main.models.PlayListGrade;
import words.com.wordservice.features.playlists.main.models.PlayListTitle;
import words.com.wordservice.features.playlists.main.models.WordPlayList;
import words.com.wordservice.features.playlists.main.models.WordPlayListCount;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.LearningHistoryType;
import words.com.wordservice.features.shared.main.events.LearningHistoryEvent;
import words.com.wordservice.features.shared.utils.IdGenerator;
import words.com.wordservice.features.shared.utils.ModelUtils;
import words.com.wordservice.features.words.db.actions.UserWordGradeUpdateAction;
import words.com.wordservice.features.words.db.entities.PinnedWordEntity;
import words.com.wordservice.features.words.db.entities.UserWordEntity;
import words.com.wordservice.features.words.db.entities.WordEntity;
import words.com.wordservice.features.words.main.mappers.UserWordDomainMapper;
import words.com.wordservice.features.words.main.models.PinnedWord;

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

    public LearningHistoryEvent.Entry toHistoryEntry(PlayListGrade model, String wordId) {
        return new LearningHistoryEvent.Entry(
                model.userId(),
                wordId,
                LearningHistoryType.UPDATE,
                model.wordGrade(),
                OffsetDateTime.now(ZoneId.of("UTC"))
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
