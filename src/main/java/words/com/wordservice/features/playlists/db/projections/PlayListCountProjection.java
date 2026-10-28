package words.com.wordservice.features.playlists.db.projections;

import words.com.wordservice.features.shared.main.enums.Language;

import java.time.Instant;

public interface PlayListCountProjection {
    String getId();

    String getUserId();

    String getName();

    Instant getCreatedAt();

    Long getCount();

    String getTags();

    String getCefrs();

    Language getLanguage();

    Language getTranslateLanguage();

    java.time.Instant getPinnedAt();
}
