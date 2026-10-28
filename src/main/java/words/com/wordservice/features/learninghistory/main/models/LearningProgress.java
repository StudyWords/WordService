package words.com.wordservice.features.learninghistory.main.models;

public record LearningProgress(
        long createdTodayByPlan,
        long createdTotal,
        long createdTotalByPlan,
        long repeatedNewTodayByPlan,
        long repeatedTotal,
        long repeatedTotalByPlan
) {
}
