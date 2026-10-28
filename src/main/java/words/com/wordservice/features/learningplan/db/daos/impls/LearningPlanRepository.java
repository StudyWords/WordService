package words.com.wordservice.features.learningplan.db.daos.impls;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import words.com.wordservice.features.learningplan.db.entities.LearningPlanEntity;

 interface LearningPlanRepository extends ListCrudRepository<LearningPlanEntity,String>,
        JpaSpecificationExecutor<LearningPlanEntity> {
}
