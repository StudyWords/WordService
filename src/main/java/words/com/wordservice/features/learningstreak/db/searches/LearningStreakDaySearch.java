package words.com.wordservice.features.learningstreak.db.searches;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;
import words.com.wordservice.features.learningstreak.db.entities.LearningStreakDayEntity;

import java.time.LocalDate;
import java.util.ArrayList;

@Builder
public record LearningStreakDaySearch(
        String userId,
        LocalDate dayFrom,
        LocalDate dayTo
) implements Specification<LearningStreakDayEntity> {

    @Override
    public Predicate toPredicate(Root<LearningStreakDayEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        var predicates = new ArrayList<Predicate>();

        if (userId != null) {
            predicates.add(cb.equal(root.get("id").get("userId"), userId));
        }
        if (dayFrom != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("id").get("day"), dayFrom));
        }
        if (dayTo != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("id").get("day"), dayTo));
        }

        if (predicates.isEmpty()) {
            return null;
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
