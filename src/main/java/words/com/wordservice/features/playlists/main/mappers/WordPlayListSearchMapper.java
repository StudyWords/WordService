package words.com.wordservice.features.playlists.main.mappers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import words.com.wordservice.features.playlists.db.entities.WordPlayListEntity;
import words.com.wordservice.features.playlists.db.searches.WordPlayListCountSearch;
import words.com.wordservice.features.playlists.db.searches.WordPlayListSearch;
import words.com.wordservice.features.playlists.main.enums.WordPlaylistSortField;
import words.com.wordservice.features.playlists.main.models.WordPlayListCountFilter;
import words.com.wordservice.features.playlists.main.models.WordPlayListFilter;
import words.com.wordservice.features.shared.utils.ModelUtils;
import words.com.wordservice.features.shared.utils.SortUtils;

import java.util.Map;
import java.util.Objects;

import static words.com.wordservice.features.playlists.main.enums.WordPlaylistSortField.*;

@Component
public class WordPlayListSearchMapper {
    private final Map<WordPlaylistSortField, String> sortFieldMap = Map.of(
            NAME, SortUtils.getSortColumnName(WordPlayListEntity.class, WordPlayListEntity::getName),
            CREATED_AT, SortUtils.getSortColumnName(WordPlayListEntity.class, WordPlayListEntity::getCreatedAt),
            CEFR, "cefrs",
            LANGUAGE, SortUtils.getSortColumnName(WordPlayListEntity.class, WordPlayListEntity::getLanguage),
            TRANSLATE_LANGUAGE, SortUtils.getSortColumnName(WordPlayListEntity.class, WordPlayListEntity::getTranslateLanguage)
    );

    public Pageable toPageable(WordPlayListFilter filter) {
        var sort = toSort(filter.sortField(), filter.asc(), false);
        return PageRequest.of(filter.page(), filter.size(), sort);
    }

    public Pageable toPageable(WordPlayListCountFilter filter) {
        var sort = toSort(filter.sortField(), filter.asc(), true);
        return PageRequest.of(filter.page(), filter.size(), sort);
    }

    private Sort toSort(WordPlaylistSortField field, boolean asc, boolean sortByPin) {
        var sortField = sortFieldMap.get(Objects.requireNonNullElse(field, NAME));
        var direction = asc ? Sort.Direction.ASC : Sort.Direction.DESC;
        var mainOrder = new Sort.Order(direction, sortField);

        if (sortByPin) {
            var pinnedOrder = new Sort.Order(Sort.Direction.DESC, "pinnedAt", Sort.NullHandling.NULLS_LAST);
            return Sort.by(pinnedOrder, mainOrder);
        }

        return Sort.by(mainOrder);
    }

    public WordPlayListSearch toSearch(WordPlayListFilter filter) {
        return ModelUtils.convert(filter, WordPlayListSearch.class);
    }

    public WordPlayListCountSearch toSearch(WordPlayListCountFilter filter) {
        return ModelUtils.convert(filter, WordPlayListCountSearch.class);
    }
}
