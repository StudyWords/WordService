package words.com.wordservice.features.learningstreak.main.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import words.com.wordservice.features.learninghistory.main.models.LearningProgress;
import words.com.wordservice.features.learningplan.main.models.LearningPlan;
import words.com.wordservice.features.learningstreak.db.daos.LearningStreakDayDao;
import words.com.wordservice.features.learningstreak.db.entities.LearningStreakDayEntity;
import words.com.wordservice.features.learningstreak.db.entities.LearningStreakDayId;
import words.com.wordservice.features.learningstreak.db.searches.LearningStreakDaySearch;
import words.com.wordservice.features.learningstreak.main.models.LearningStreak;
import words.com.wordservice.features.learningstreak.main.services.LearningStreakService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class LearningStreakServiceImpl implements LearningStreakService {
    private static final int MAX_STREAK_DAYS = 400;
    private static final int SECONDS_IN_MINUTE = 60;

    private final LearningStreakDayDao learningStreakDayDao;

    @Override
    public LearningStreak find(String userId, ZoneId zone) {
        var today = LocalDate.now(zone);
        var search = LearningStreakDaySearch.builder()
                .userId(userId)
                .dayFrom(today.minusDays(MAX_STREAK_DAYS - 1L))
                .dayTo(today)
                .build();
        Set<LocalDate> days = learningStreakDayDao.findBy(search).stream()
                .map(entity -> entity.getId().getDay())
                .collect(Collectors.toSet());

        var completedToday = days.contains(today);
        var streak = completedToday ? 1 : 0;
        var day = today.minusDays(1);
        while (streak < MAX_STREAK_DAYS && days.contains(day)) {
            streak++;
            day = day.minusDays(1);
        }
        return new LearningStreak(streak, completedToday);
    }

    @Override
    public boolean isCompletedToday(String userId, ZoneId zone) {
        var today = LocalDate.now(zone);
        var search = LearningStreakDaySearch.builder()
                .userId(userId)
                .dayFrom(today)
                .dayTo(today)
                .build();
        return learningStreakDayDao.exists(search);
    }

    @Override
    public void markCompletedIfDone(LearningPlan plan, LearningProgress progress, ZoneId zone) {
        if (!isDone(plan, progress)) {
            return;
        }
        var entity = LearningStreakDayEntity.builder()
                .id(new LearningStreakDayId(plan.userId(), LocalDate.now(zone)))
                .wordsCount((int) progress.createdTodayByPlan())
                .zoneId(zone.getId())
                .utcOffsetMinutes(zone.getRules().getOffset(Instant.now()).getTotalSeconds() / SECONDS_IN_MINUTE)
                .createdAt(OffsetDateTime.now())
                .build();
        learningStreakDayDao.upsert(entity);
    }

    private boolean isDone(LearningPlan plan, LearningProgress progress) {
        return progress.createdTodayByPlan() >= plan.wordsPerDay()
                && progress.repeatedNewTodayByPlan() >= plan.wordsPerDay();
    }
}
