package words.com.wordservice.features.learningstreak.main.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import words.com.wordservice.features.learninghistory.main.services.LearningHistoryService;
import words.com.wordservice.features.learningplan.main.models.LearningPlan;
import words.com.wordservice.features.learningplan.main.services.LearningPlanService;
import words.com.wordservice.features.learningstreak.main.services.LearningStreakService;
import words.com.wordservice.features.shared.main.events.LearningHistoryEvent;
import words.com.wordservice.features.shared.utils.ZoneUtils;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class LearningStreakListener {
    private final LearningPlanService learningPlanService;
    private final LearningHistoryService learningHistoryService;
    private final LearningStreakService learningStreakService;

    @Order(2)
    @EventListener
    public void handle(LearningHistoryEvent event) {
        event.entries().stream()
                .map(LearningHistoryEvent.Entry::userId)
                .filter(Objects::nonNull)
                .distinct()
                .forEach(this::registerCompletion);
    }

    private void registerCompletion(String userId) {
        // the streak must never break adding words
        try {
            learningPlanService.findByUserId(userId).ifPresent(this::registerCompletion);
        } catch (RuntimeException e) {
            log.error("Failed to register learning plan completion for user {}", userId, e);
        }
    }

    private void registerCompletion(LearningPlan plan) {
        var zone = ZoneUtils.resolve(plan.zoneId(), plan.utcOffsetMinutes());
        if (learningStreakService.isCompletedToday(plan.userId(), zone)) {
            return;
        }
        var progress = learningHistoryService.findProgress(plan.userId(), plan.cefrs(), zone);
        learningStreakService.markCompletedIfDone(plan, progress, zone);
    }
}
