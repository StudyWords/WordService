package words.com.wordservice.features.playlists.db.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;
import words.com.wordservice.features.playlists.main.enums.PlayListVisibility;
import words.com.wordservice.features.shared.db.converters.CefrTreeSetConverter;
import words.com.wordservice.features.shared.db.converters.StringTreeSetConverter;
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
@Table(name = "word_playlists")
public class WordPlayListEntity {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    @Column(nullable = false, updatable = false)
    private String userId;
    private String name;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;


    @Convert(converter = StringTreeSetConverter.class)
    @Column(columnDefinition = "TEXT", updatable = false)
    private TreeSet<String> tags;
    @Convert(converter = CefrTreeSetConverter.class)
    @Column(columnDefinition = "TEXT", updatable = false)
    private TreeSet<CEFR> cefrs;

    @Nullable
    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private Language language;
    @Nullable
    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private Language translateLanguage;
    @Column(nullable = false, updatable = false, columnDefinition = "varchar(255) DEFAULT 'PRIVATE'")
    @Enumerated(EnumType.STRING)
    private PlayListVisibility visibility;

    @Nullable
    @Column(updatable = false)
    private String baseId;
    @Nullable
    @Column(updatable = false)
    private String associationId;

    @Nullable
    @Column(updatable = false)
    private OffsetDateTime pinnedAt;
}
