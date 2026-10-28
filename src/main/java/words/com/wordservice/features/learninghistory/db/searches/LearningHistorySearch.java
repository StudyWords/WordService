package words.com.wordservice.features.learninghistory.db.searches;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Singular;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import words.com.wordservice.features.learninghistory.db.entities.LearningHistoryEntity;
import words.com.wordservice.features.shared.utils.Range;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Builder
public record LearningHistorySearch(
        @Singular(ignoreNullCollections = true) Collection<String> userIds,
        @Singular(ignoreNullCollections = true) Collection<String> wordIds,
        Range<OffsetDateTime> date
) implements Specification<LearningHistoryEntity> {

    @Override
    public Predicate toPredicate(@NonNull Root<LearningHistoryEntity> root, CriteriaQuery<?> query,@NonNull CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(userIds)) {
            predicates.add(root.get("userId").in(userIds));
        }
        if (!CollectionUtils.isEmpty(wordIds)) {
            predicates.add(root.get("userWordId").in(wordIds));
        }

        if (Range.hasFrom(date)) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), date.getFrom()));
        }
        if (Range.hasTo(date)) {
            predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), date.getTo()));
        }

        return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
    }
}
