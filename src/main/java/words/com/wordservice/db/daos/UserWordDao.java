package words.com.wordservice.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.db.actions.UserWordDeleteAction;
import words.com.wordservice.db.actions.UserWordGradeUpdateAction;
import words.com.wordservice.db.actions.UserWordUpsertAction;
import words.com.wordservice.db.entities.UserWordEntity;
import words.com.wordservice.db.searches.UserWordSearch;

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

    void addGrades(Collection<UserWordGradeUpdateAction> entities);

    void delete(Collection<UserWordDeleteAction> actions);

    void delete(List<UserWordEntity> entities);

    long count(UserWordSearch s);
}
