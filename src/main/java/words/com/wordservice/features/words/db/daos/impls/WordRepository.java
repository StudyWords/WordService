package words.com.wordservice.features.words.db.daos.impls;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import words.com.wordservice.features.words.db.entities.WordEntity;

interface WordRepository extends ListCrudRepository<WordEntity, String>, JpaSpecificationExecutor<WordEntity> {



}
