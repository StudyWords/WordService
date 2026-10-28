package words.com.wordservice.features.learningplan.main.models;

import lombok.Builder;
import lombok.Singular;

import java.util.Collection;

@Builder
public record LearningPlanFilter(
        @Singular(ignoreNullCollections = true)
        Collection<String> userIds,
        int page,
        int size
) {


}
