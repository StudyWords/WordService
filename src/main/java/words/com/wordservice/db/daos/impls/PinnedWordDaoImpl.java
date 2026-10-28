package words.com.wordservice.db.daos.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import words.com.wordservice.db.daos.PinnedWordDao;
import words.com.wordservice.db.entities.PinnedWordEntity;
import words.com.wordservice.db.searches.PinnedWordSearch;

import java.util.Collection;

@Service
@RequiredArgsConstructor
class PinnedWordDaoImpl implements PinnedWordDao {
    private final PinnedWordRepository repository;
    @Override
    @Transactional(readOnly = true)
    public Page<PinnedWordEntity> findBy(PinnedWordSearch search, Pageable pageable) {
        return repository.findAll(search, pageable);
    }

    @Override
    @Transactional
    public void save(Collection<PinnedWordEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    @Transactional
    public void delete(PinnedWordSearch search) {
        var entities = repository.findAll(search);
        if (entities.isEmpty()) {
            return;
        }
        repository.deleteAll(entities);
    }
}
