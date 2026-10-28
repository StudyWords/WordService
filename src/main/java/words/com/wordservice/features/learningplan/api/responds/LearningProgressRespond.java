package words.com.wordservice.features.learningplan.api.responds;

public record LearningProgressRespond(
        long createdTodayByPlan,
        long createdTotal,
        long createdTotalByPlan,
        long repeatedNewTodayByPlan,
        long repeatedTotal,
        long repeatedTotalByPlan
) {
}
