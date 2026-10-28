package words.com.wordservice.db.daos.impls;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import words.com.wordservice.db.entities.WordEntity;

interface WordRepository extends ListCrudRepository<WordEntity, String>, JpaSpecificationExecutor<WordEntity> {



}
