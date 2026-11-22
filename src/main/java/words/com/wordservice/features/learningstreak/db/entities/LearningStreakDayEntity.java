package words.com.wordservice.features.learningstreak.db.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "learning_plan_streak_day")
public class LearningStreakDayEntity {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private LearningStreakDayId id;
    private int wordsCount;
    @Nullable
    private String zoneId;
    private int utcOffsetMinutes;
    private OffsetDateTime createdAt;
}
