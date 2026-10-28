package words.com.wordservice.features.words.main.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import words.com.wordservice.features.shared.utils.IdGenerator;
import words.com.wordservice.features.words.db.actions.WordDeleteAction;
import words.com.wordservice.features.words.db.entities.WordEntity;
import words.com.wordservice.features.words.main.models.Word;
import words.com.wordservice.features.words.main.models.WordDeleteDto;
import words.com.wordservice.features.words.main.models.WordDraft;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WordDomainMapper {
    private final IdGenerator idGenerator;
    private final FileNameMapper fileNameMapper;

    public Word toModel(WordEntity entity) {
        return toModel(entity, null, null);
    }

    public Word toModel(WordEntity entity, @Nullable String customSoundFileName, @Nullable String customImageFileName) {
        var imageFileName = StringUtils.hasText(customImageFileName) ? customImageFileName : entity.getImageFileName();
        var soundFileName = StringUtils.hasText(customSoundFileName) ? customSoundFileName : entity.getSoundFileName();
        return new Word(
                entity.getId(),
                entity.getOriginal(),
                entity.getLang(),
                entity.getTranslate(),
                entity.getTranslateLang(),
                entity.getCategory(),
                fileNameMapper.toLink(soundFileName),
                fileNameMapper.toLink(imageFileName),
                entity.getType(),
                entity.getCefr(),
                entity.getDescription(),
                entity.getCreatedAt()
        );
    }

    public WordEntity toEntity(WordDraft model) {
        return new WordEntity(
                StringUtils.hasText(model.id()) ? model.id() : idGenerator.generate(),
                model.original(),
                model.lang(),
                model.translate(),
                model.translateLang(),
                model.category(),
                model.soundFileName(),
                model.imageFileName(),
                model.type(),
                model.cefr(),
                model.description(),
                OffsetDateTime.now(),
                Collections.emptyList()
        );
    }

    public Collection<WordDeleteAction> toActions(WordDeleteDto options) {
        return options.ids().stream()
                .map(id -> new WordDeleteAction(id, options.type()))
                .collect(Collectors.toSet());
    }
}
