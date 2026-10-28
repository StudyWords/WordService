package words.com.wordservice.features.words.db.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_words")
@Entity
public class UserWordEntity {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    @Column(nullable = false, updatable = false)
    private String userId;
    @Column(updatable = false)
    private long learningGrade;

    @Nullable
    private String customSoundFileName;
    @Nullable
    private String customImageFileName;

    @CreatedDate
    @Column(updatable = false)
    private OffsetDateTime createdAt;
    private OffsetDateTime lastReadDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "word_id", referencedColumnName = "id", nullable = false)
    private WordEntity word;
}
