package words.com.wordservice.features.shared.utils;

import org.springframework.lang.Nullable;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZoneOffset;

public final class ZoneUtils {
    private static final int SECONDS_IN_MINUTE = 60;

    private ZoneUtils() {
    }

    /**
     * Zone id has priority, then the offset in minutes, then UTC.
     */
    public static ZoneId resolve(@Nullable String zoneId, @Nullable Integer utcOffsetMinutes) {
        if (isValid(zoneId)) {
            return ZoneId.of(zoneId);
        }
        if (utcOffsetMinutes != null) {
            return ZoneOffset.ofTotalSeconds(utcOffsetMinutes * SECONDS_IN_MINUTE);
        }
        return ZoneOffset.UTC;
    }

    public static boolean isValid(@Nullable String zoneId) {
        if (zoneId == null || zoneId.isBlank()) {
            return false;
        }
        try {
            ZoneId.of(zoneId);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    @Nullable
    public static String normalize(@Nullable String zoneId) {
        return isValid(zoneId) ? zoneId : null;
    }
}
