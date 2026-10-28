package words.com.wordservice.db.daos.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import words.com.wordservice.db.actions.WordDeleteAction;
import words.com.wordservice.db.daos.WordDao;
import words.com.wordservice.db.entities.WordEntity;
import words.com.wordservice.db.searches.WordSearch;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
class WordDaoImpl implements WordDao {
    private final WordRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<WordEntity> findBy(WordSearch search, Pageable pageable) {
        return repository.findAll(search, pageable);
    }

    @Override
    @Transactional
    public void save(Collection<WordEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    @Transactional
    public void update(WordEntity entity) {
        repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Collection<WordDeleteAction> actions) {
        ArrayList<Specification<WordEntity>> searches = new ArrayList<>();

        for (WordDeleteAction action : actions) {
            searches.add(WordSearch.builder()
                    .wordId(action.id())
                    .type(action.type())
                    .build());
        }
        if (searches.isEmpty()) {
            return;
        }
        repository.delete(Specification.anyOf(searches));
    }
}
