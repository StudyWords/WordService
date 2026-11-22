package words.com.wordservice.features.learningplan.db.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;
import words.com.wordservice.features.shared.db.converters.CefrTreeSetConverter;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;

import java.time.OffsetDateTime;
import java.util.TreeSet;

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
    @Convert(converter = CefrTreeSetConverter.class)
    @Column(columnDefinition = "TEXT")
    private TreeSet<CEFR> cefrs;
    @Nullable
    private String zoneId;
    @Column(columnDefinition = "integer default 0")
    private int utcOffsetMinutes;
    @Column(updatable = false)
    @CreatedDate
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}