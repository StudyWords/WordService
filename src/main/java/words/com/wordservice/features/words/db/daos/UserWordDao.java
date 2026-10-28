package words.com.wordservice.features.words.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.features.words.db.actions.UserWordDeleteAction;
import words.com.wordservice.features.words.db.actions.UserWordGradeUpdateAction;
import words.com.wordservice.features.words.db.actions.UserWordUpsertAction;
import words.com.wordservice.features.words.db.entities.UserWordEntity;
import words.com.wordservice.features.words.db.searches.UserWordSearch;

import java.util.Collection;
import java.util.List;

public interface UserWordDao {

    Page<UserWordEntity> findBy(UserWordSearch s, Pageable pageable);

    default List<UserWordEntity> findBy(UserWordSearch s) {
        return findBy(s, Pageable.unpaged()).getContent();
    }


    void save(Collection<UserWordEntity> entities);

    void update(UserWordEntity entity);

    void upsertAll(Collection<UserWordUpsertAction> entities);

    List<UserWordGradeUpdateAction> addGrades(Collection<UserWordGradeUpdateAction> actions);

    void delete(Collection<UserWordDeleteAction> actions);

    void delete(List<UserWordEntity> entities);

    long count(UserWordSearch s);
}
