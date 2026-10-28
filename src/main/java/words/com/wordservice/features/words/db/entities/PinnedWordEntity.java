package words.com.wordservice.features.words.db.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pinned_word")
public class PinnedWordEntity {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private PinnedWordId id;

    private Long learningGrade;

    private OffsetDateTime createdAt;

    private OffsetDateTime lastReadDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userWordId")
    @JoinColumn(name = "user_word_id", referencedColumnName = "id", nullable = false)
    private UserWordEntity word;


    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class PinnedWordId implements Serializable {
        private String playListId;
        private String userWordId;
    }
}
