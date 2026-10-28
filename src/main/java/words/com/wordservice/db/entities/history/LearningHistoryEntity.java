package words.com.wordservice.db.entities.history;

import jakarta.persistence.*;
import lombok.*;
import words.com.wordservice.domain.models.enums.LearningHistoryType;

import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "learning_history")
public class LearningHistoryEntity {
    @Id
    @EqualsAndHashCode.Include
    @Column(nullable = false, updatable = false)
    private String id;
    @Column(nullable = false, updatable = false)
    private String userId;
    @Column(nullable = false, updatable = false)
    private String wordId;
    private OffsetDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private LearningHistoryType type;
    private int grade;
}
