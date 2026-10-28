package words.com.wordservice.features.learninghistory.db.daos.impls;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import words.com.wordservice.features.learninghistory.db.entities.LearningHistoryEntity;
import words.com.wordservice.features.learninghistory.db.projections.LearningProgressProjection;
import words.com.wordservice.features.learninghistory.db.projections.StatisticsLearningHistoryProjection;

import java.time.OffsetDateTime;
import java.util.Collection;

interface LearningHistoryRepository extends ListCrudRepository<LearningHistoryEntity, String>,
        JpaSpecificationExecutor<LearningHistoryEntity> {

    String SELECT_STATISTICS_LEARNING_HISTORY =
            "SELECT count(lh.id) as count, sum(lh.grade) as grades, lh.type," +
                    " CAST(lh.created_at AT TIME ZONE 'UTC' AS DATE) as date from learning_history lh" +
                    " WHERE " +
                    " (:isEmptyUserIds OR lh.user_id in(:userIds)) " +
                    " AND (CAST(:startDate AS TIMESTAMPTZ) IS NULL OR lh.created_at >= :startDate)" +
                    " AND (CAST(:endDate AS TIMESTAMPTZ) IS NULL OR lh.created_at <= :endDate)" +
                    " group by CAST(lh.created_at AT TIME ZONE 'UTC' AS DATE), lh.type" +
                    " order by date, lh.type ";
    String SELECT_PROGRESS = """
            SELECT
                COUNT(*) FILTER (WHERE lh.type = 'CREATE' AND (:hasCefrs AND w.cefr IN (:cefrs))
                    AND lh.created_at >= :dayStart AND lh.created_at < :dayEnd) AS createdTodayByPlan,
                COUNT(*) FILTER (WHERE lh.type = 'CREATE') AS createdTotal,
                COUNT(*) FILTER (WHERE lh.type = 'CREATE' AND (:hasCefrs AND w.cefr IN (:cefrs))) AS createdTotalByPlan,
                COUNT(DISTINCT lh.word_id) FILTER (WHERE lh.type = 'UPDATE' AND (:hasCefrs AND w.cefr IN (:cefrs))
                    AND lh.created_at >= :dayStart AND lh.created_at < :dayEnd
                    AND lh.word_id IN (
                        SELECT c.word_id FROM learning_history c
                        WHERE c.user_id = :userId AND c.type = 'CREATE'
                            AND c.created_at >= :dayStart AND c.created_at < :dayEnd
                    )) AS repeatedNewTodayByPlan,
                COUNT(*) FILTER (WHERE lh.type = 'UPDATE') AS repeatedTotal,
                COUNT(*) FILTER (WHERE lh.type = 'UPDATE' AND (:hasCefrs AND w.cefr IN (:cefrs))) AS repeatedTotalByPlan
            FROM learning_history lh
            LEFT JOIN words w ON w.id = lh.word_id
            WHERE lh.user_id = :userId
            """;

    @Query(value = SELECT_STATISTICS_LEARNING_HISTORY, nativeQuery = true)
    Page<StatisticsLearningHistoryProjection> findStatisticsBy(
            boolean isEmptyUserIds,
            java.util.Collection<String> userIds,
            OffsetDateTime startDate,
            OffsetDateTime endDate,
            Pageable pageable
    );

    @Query(value = SELECT_PROGRESS, nativeQuery = true)
    LearningProgressProjection findProgressBy(
            String userId,
            boolean hasCefrs,
            Collection<String> cefrs,
            OffsetDateTime dayStart,
            OffsetDateTime dayEnd
    );
}
