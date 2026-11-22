package words.com.wordservice.features.learningstreak.db.daos.impls;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import words.com.wordservice.features.learningstreak.db.entities.LearningStreakDayEntity;
import words.com.wordservice.features.learningstreak.db.entities.LearningStreakDayId;

import java.time.LocalDate;
import java.time.OffsetDateTime;

interface LearningStreakDayJpaRepository extends JpaRepository<LearningStreakDayEntity, LearningStreakDayId>,
        JpaSpecificationExecutor<LearningStreakDayEntity> {

    String UPSERT = """
            INSERT INTO learning_plan_streak_day (user_id, day, words_count, zone_id, utc_offset_minutes, created_at)
            VALUES (:userId, :day, :wordsCount, :zoneId, :utcOffsetMinutes, :createdAt)
            ON CONFLICT (user_id, day) DO UPDATE
            SET words_count = GREATEST(learning_plan_streak_day.words_count, EXCLUDED.words_count)
            """;

    @Modifying
    @Query(value = UPSERT, nativeQuery = true)
    void upsert(
            String userId,
            LocalDate day,
            int wordsCount,
            String zoneId,
            int utcOffsetMinutes,
            OffsetDateTime createdAt
    );
}
