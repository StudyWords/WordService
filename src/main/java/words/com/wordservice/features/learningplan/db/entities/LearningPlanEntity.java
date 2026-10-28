package words.com.wordservice.features.learningplan.db.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;

import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class LearningPlanEntity {
    @Id
    @EqualsAndHashCode.Include
    private String userId;
    private int wordsPerDay;
    @Enumerated(EnumType.STRING)
    private Language nativeLang;
    @Enumerated(EnumType.STRING)
    private Language learningLang;
    @Enumerated(EnumType.STRING)
    private CEFR cefr;
    @Column(updatable = false)
    @CreatedDate
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}