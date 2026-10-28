package words.com.wordservice.features.words.db.searches;

import jakarta.persistence.criteria.*;
import lombok.Builder;
import lombok.Singular;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;
import words.com.wordservice.features.words.db.entities.UserWordEntity;
import words.com.wordservice.features.words.db.entities.WordEntity;
import words.com.wordservice.features.words.main.enums.WordType;

import java.util.ArrayList;
import java.util.Collection;

@Builder
public record UserWordSearch(
        @Singular(ignoreNullCollections = true) Collection<String> userWordIds,
        @Singular(ignoreNullCollections = true) Collection<String> wordIds,
        @Singular(ignoreNullCollections = true) Collection<String> userIds,
        @Singular(ignoreNullCollections = true) Collection<Language> languages,
        @Singular(ignoreNullCollections = true) Collection<Language> translateLanguages,
        @Singular(ignoreNullCollections = true) Collection<String> categories,
        @Singular(ignoreNullCollections = true) Collection<CEFR> cefrs,
        @Singular(ignoreNullCollections = true) Collection<WordType> types,
        @Singular(ignoreNullCollections = true) Collection<UserWordSearch> searches,
        String translate,
        String original,
        Boolean hasSound,
        Boolean hasImage,
        Operations operation
) implements Specification<UserWordEntity> {

    @Nullable
    @Override
    public Predicate toPredicate(@NonNull Root<UserWordEntity> root, @Nullable CriteriaQuery<?> query,@NonNull CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(userWordIds)) {
            predicates.add(root.get("id").in(userWordIds));
        }
        if (!CollectionUtils.isEmpty(wordIds)) {
            predicates.add(root.get("word").get("id").in(wordIds));
        }
        if (!CollectionUtils.isEmpty(userIds)) {
            predicates.add(root.get("userId").in(userIds));
        }

        boolean isEntityQuery = query == null || query.getResultType() == null
                || UserWordEntity.class.isAssignableFrom(query.getResultType());

        Join<UserWordEntity, WordEntity> wordJoin = isEntityQuery
                ? (Join<UserWordEntity, WordEntity>) root.<UserWordEntity, WordEntity>fetch("word", JoinType.INNER)
                : root.join("word", JoinType.INNER);

        if (!CollectionUtils.isEmpty(languages)) {
            predicates.add(wordJoin.get("lang").in(languages));
        }
        if (!CollectionUtils.isEmpty(translateLanguages)) {
            predicates.add(wordJoin.get("translateLang").in(translateLanguages));
        }
        if (!CollectionUtils.isEmpty(categories)) {
            predicates.add(wordJoin.get("category").in(categories));
        }
        if (!CollectionUtils.isEmpty(cefrs)) {
            predicates.add(wordJoin.get("cefr").in(cefrs));
        }
        if (!CollectionUtils.isEmpty(types)) {
            predicates.add(wordJoin.get("type").in(types));
        }
        if (!CollectionUtils.isEmpty(searches)) {
            searches.forEach(search -> {
                Predicate subPredicate = search.toPredicate(root, query, cb);
                if (subPredicate != null) {
                    predicates.add(subPredicate);
                }
            });
        }

        if (StringUtils.hasText(translate)) {
            predicates.add(cb.like(cb.lower(wordJoin.get("translate")), "%" + translate.toLowerCase() + "%"));
        }
        if (StringUtils.hasText(original)) {
            predicates.add(cb.like(cb.lower(wordJoin.get("original")), "%" + original.toLowerCase() + "%"));
        }

        if (Boolean.TRUE.equals(hasSound)) {
            predicates.add(cb.or(
                    cb.isNotNull(wordJoin.get("soundFileName")),
                    cb.isNotNull(root.get("customSoundFileName"))
            ));
        }
        if (Boolean.TRUE.equals(hasImage)) {
            predicates.add(cb.or(
                    cb.isNotNull(wordJoin.get("imageFileName")),
                    cb.isNotNull(root.get("customImageFileName"))
            ));
        }
        if (Boolean.FALSE.equals(hasSound)) {
            predicates.add(cb.and(
                    cb.isNull(wordJoin.get("soundFileName")),
                    cb.isNull(root.get("customSoundFileName"))
            ));
        }
        if (Boolean.FALSE.equals(hasImage)) {
            predicates.add(cb.and(
                    cb.isNull(wordJoin.get("imageFileName")),
                    cb.isNull(root.get("customImageFileName"))
            ));
        }

        if (predicates.isEmpty()) {
            return null;
        }
        if (operation == Operations.OR) {
            return cb.or(predicates.toArray(new Predicate[0]));
        }


        return cb.and(predicates.toArray(new Predicate[0]));
    }
}