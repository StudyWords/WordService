package words.com.wordservice.features.words.db.searches;

import jakarta.persistence.criteria.*;
import lombok.Builder;
import lombok.Singular;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import words.com.wordservice.features.words.db.entities.PinnedWordEntity;

import java.util.ArrayList;
import java.util.Collection;

@Builder
public record PinnedWordSearch(
        @Singular(ignoreNullCollections = true) Collection<String> playListIds,
        @Singular(ignoreNullCollections = true) Collection<String> userIds,
        @Singular(ignoreNullCollections = true) Collection<String> wordIds,
        @Singular(ignoreNullCollections = true) Collection<String> userWordIds,
        @Singular(ignoreNullCollections = true) Collection<PinnedWordEntity.PinnedWordId> pinnedIds
) implements Specification<PinnedWordEntity> {

    @Override
    public Predicate toPredicate(@NonNull Root<PinnedWordEntity> root, CriteriaQuery<?> query,@NonNull CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        // Add fetch joins only for SELECT (non-count, non-delete/update) queries
        // Hibernate 6 does NOT support fetch joins in DELETE/UPDATE — causes ClassCastException
        assert query != null;
        Class<?> resultType = query.getResultType();
        boolean isSelectQuery = !Long.class.equals(resultType)
                && !Void.class.equals(resultType)
                && resultType != null
                && !resultType.getName().equals("void");
        if (isSelectQuery) {
            try {
                root.fetch("word", JoinType.LEFT).fetch("word", JoinType.LEFT);
                query.distinct(true);
            } catch (Exception ignore) {
                // ignore fetch failures (e.g. in DELETE/UPDATE contexts)
            }
        }

        if (!CollectionUtils.isEmpty(pinnedIds)) {
            predicates.add(root.get("id").in(pinnedIds));
        }
        if (!CollectionUtils.isEmpty(playListIds)) {
            predicates.add(root.get("id").get("playListId").in(playListIds));
        }
        if (!CollectionUtils.isEmpty(userIds)) {
            predicates.add(root.get("word").get("userId").in(userIds));
        }
        if (!CollectionUtils.isEmpty(wordIds)) {
            predicates.add(root.get("word").get("word").get("id").in(wordIds));
        }
        if (!CollectionUtils.isEmpty(userWordIds)) {
            predicates.add(root.get("id").get("userWordId").in(userWordIds));
        }

        return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
    }
}
