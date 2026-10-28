package words.com.wordservice.features.playlists.db.searches;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Singular;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import words.com.wordservice.features.playlists.db.entities.WordPlayListEntity;
import words.com.wordservice.features.playlists.main.enums.PlayListVisibility;

import java.util.ArrayList;
import java.util.Collection;

@Builder
public record WordPlayListSearch(
        @Singular(ignoreNullCollections = true) Collection<String> ids,
        @Singular(ignoreNullCollections = true) Collection<String> userIds,
        Boolean hasPin,
        Boolean hasBaseId,
        String name,
        PlayListVisibility visibility
) implements Specification<WordPlayListEntity> {

    @Override
    public Predicate toPredicate(Root<WordPlayListEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(ids)) {
            predicates.add(root.get("id").in(ids));
        }

        if (!CollectionUtils.isEmpty(userIds)) {
            predicates.add(root.get("userId").in(userIds));
        }

        if (StringUtils.hasText(name)) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (visibility != null) {
            predicates.add(cb.equal(root.get("visibility"), visibility));
        }
        if (Boolean.TRUE.equals(hasPin)) {
            predicates.add(cb.isNotNull(root.get("pinnedAt")));
        }
        if (Boolean.FALSE.equals(hasPin)) {
            predicates.add(cb.isNull(root.get("pinnedAt")));
        }

        if (Boolean.TRUE.equals(hasBaseId)) {
            predicates.add(cb.isNotNull(root.get("baseId")));
        }
        if (Boolean.FALSE.equals(hasBaseId)) {
            predicates.add(cb.isNull(root.get("baseId")));
        }

        if (predicates.isEmpty()) {
            return null;
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
