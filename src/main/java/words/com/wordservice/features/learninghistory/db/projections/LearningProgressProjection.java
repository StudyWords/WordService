package words.com.wordservice.features.learninghistory.db.projections;

public interface LearningProgressProjection {

    long getCreatedTodayByPlan();

    long getCreatedTotal();

    long getCreatedTotalByPlan();

    long getRepeatedNewTodayByPlan();

    long getRepeatedTotal();

    long getRepeatedTotalByPlan();
}
