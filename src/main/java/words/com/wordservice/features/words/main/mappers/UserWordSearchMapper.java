package words.com.wordservice.features.words.main.mappers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import words.com.wordservice.features.shared.utils.ModelUtils;
import words.com.wordservice.features.shared.utils.SortUtils;
import words.com.wordservice.features.words.db.entities.UserWordEntity;
import words.com.wordservice.features.words.db.entities.WordEntity;
import words.com.wordservice.features.words.db.searches.UserWordSearch;
import words.com.wordservice.features.words.main.enums.UserWordSortField;
import words.com.wordservice.features.words.main.models.UserWordFilter;

import java.util.Map;
import java.util.Objects;

import static words.com.wordservice.features.words.main.enums.UserWordSortField.*;

@Component
public class UserWordSearchMapper {
    private final Map<UserWordSortField, String> sortFieldMap = Map.of(
            ORIGIN, String.join(".",
                    SortUtils.getSortColumnName(UserWordEntity.class, UserWordEntity::getWord),
                    SortUtils.getSortColumnName(WordEntity.class, WordEntity::getOriginal)
            ),
            TRANSLATE, String.join(".",
                    SortUtils.getSortColumnName(UserWordEntity.class, UserWordEntity::getWord),
                    SortUtils.getSortColumnName(WordEntity.class, WordEntity::getTranslate)
            ),
            LANG, String.join(".",
                    SortUtils.getSortColumnName(UserWordEntity.class, UserWordEntity::getWord),
                    SortUtils.getSortColumnName(WordEntity.class, WordEntity::getLang)
            ),
            TRANSLATE_LANG, String.join(".",
                    SortUtils.getSortColumnName(UserWordEntity.class, UserWordEntity::getWord),
                    SortUtils.getSortColumnName(WordEntity.class, WordEntity::getTranslateLang)
            ),
            CATEGORY, String.join(".",
                    SortUtils.getSortColumnName(UserWordEntity.class, UserWordEntity::getWord),
                    SortUtils.getSortColumnName(WordEntity.class, WordEntity::getCategory)
            ),
            CREATED_AT, SortUtils.getSortColumnName(UserWordEntity.class, UserWordEntity::getCreatedAt),
            LAST_READ_DATE, SortUtils.getSortColumnName(UserWordEntity.class, UserWordEntity::getLastReadDate)
    );

    public Pageable toPageable(UserWordFilter filter) {
        var direction = filter.asc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        UserWordSortField sortField = Objects.requireNonNullElse(filter.sortField(), UserWordSortField.ORIGIN);
        var sort = Sort.by(direction, sortFieldMap.get(sortField));
        return PageRequest.of(filter.page(), filter.size(), sort);
    }

    public UserWordSearch toSearch(UserWordFilter filter) {
        return ModelUtils.convert(filter, UserWordSearch.class);
    }
}
