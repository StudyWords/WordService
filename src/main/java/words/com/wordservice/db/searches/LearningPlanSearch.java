package words.com.wordservice.db.searches;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Singular;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import words.com.wordservice.db.entities.LearningPlanEntity;

import java.util.ArrayList;
import java.util.Collection;

@Builder
public record LearningPlanSearch(
        @Singular(ignoreNullCollections = true) Collection<String> userIds
) implements Specification<LearningPlanEntity> {

    @Override
    public Predicate toPredicate(Root<LearningPlanEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(userIds)) {
            predicates.add(root.get("userId").in(userIds));
        }

        if (predicates.isEmpty()) {
            return null;
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
