package words.com.wordservice.main.mappers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import words.com.wordservice.db.entities.WordEntity;
import words.com.wordservice.db.searches.WordSearch;
import words.com.wordservice.main.models.enums.sortfields.WordSortField;
import words.com.wordservice.main.models.filters.WordFilter;
import words.com.wordservice.main.utils.SortUtils;
import words.com.wordservice.shared.utils.ModelUtils;

import java.util.Map;
import java.util.Objects;

import static words.com.wordservice.main.models.enums.sortfields.WordSortField.*;

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
