package words.com.wordservice.features.words.main.mappers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import words.com.wordservice.features.shared.utils.ModelUtils;
import words.com.wordservice.features.shared.utils.SortUtils;
import words.com.wordservice.features.words.db.entities.WordEntity;
import words.com.wordservice.features.words.db.searches.WordSearch;
import words.com.wordservice.features.words.main.enums.WordSortField;
import words.com.wordservice.features.words.main.models.WordFilter;

import java.util.Map;
import java.util.Objects;

import static words.com.wordservice.features.words.main.enums.WordSortField.*;

@Component
public class WordSearchMapper {
    private final Map<WordSortField, String> sortFieldMap = Map.of(
            ORIGIN, SortUtils.getSortColumnName(WordEntity.class, WordEntity::getOriginal),
            TRANSLATE, SortUtils.getSortColumnName(WordEntity.class, WordEntity::getTranslate),
            LANG, SortUtils.getSortColumnName(WordEntity.class, WordEntity::getLang),
            TRANSLATE_LANG, SortUtils.getSortColumnName(WordEntity.class, WordEntity::getTranslateLang),
            CATEGORY, SortUtils.getSortColumnName(WordEntity.class, WordEntity::getCategory)
    );

    public Pageable toPageable(WordFilter filter) {
        var direction = filter.asc() ? Sort.Direction.ASC : Sort.Direction.DESC;
        var sortField = Objects.requireNonNullElse(filter.sortField(), ORIGIN);
        var sort = Sort.by(direction, sortFieldMap.get(sortField));
        return PageRequest.of(filter.page(), filter.size(), sort);
    }

    public WordSearch toSearch(WordFilter filter) {
        return ModelUtils.convert(filter, WordSearch.class);
    }

}
