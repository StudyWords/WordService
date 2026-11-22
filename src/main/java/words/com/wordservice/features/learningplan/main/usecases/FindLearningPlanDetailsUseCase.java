package words.com.wordservice.features.learningplan.main.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import words.com.wordservice.features.learninghistory.main.services.LearningHistoryService;
import words.com.wordservice.features.learningplan.main.models.LearningPlan;
import words.com.wordservice.features.learningplan.main.models.LearningPlanDetails;
import words.com.wordservice.features.learningplan.main.services.LearningPlanService;
import words.com.wordservice.features.learningstreak.main.services.LearningStreakService;
import words.com.wordservice.features.shared.utils.ZoneUtils;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindLearningPlanDetailsUseCase {
    private final LearningPlanService learningPlanService;
    private final LearningHistoryService learningHistoryService;
    private final LearningStreakService learningStreakService;

    public Optional<LearningPlanDetails> execute(String userId, @Nullable String zoneId,
                                                 @Nullable Integer utcOffsetMinutes) {
        return learningPlanService.findByUserId(userId)
                .map(plan -> saveTimeZone(plan, zoneId, utcOffsetMinutes))
                .map(this::toDetails);
    }

    private LearningPlan saveTimeZone(LearningPlan plan, @Nullable String zoneId, @Nullable Integer utcOffsetMinutes) {
        var validZoneId = ZoneUtils.normalize(zoneId);
        if (validZoneId == null && utcOffsetMinutes == null) {
            return plan;
        }
        var offset = utcOffsetMinutes == null ? 0 : utcOffsetMinutes;
        if (Objects.equals(plan.zoneId(), validZoneId) && Objects.equals(plan.utcOffsetMinutes(), offset)) {
            return plan;
        }
        learningPlanService.updateTimeZone(plan.userId(), validZoneId, offset);
        return new LearningPlan(
                plan.userId(),
                plan.wordsPerDay(),
                plan.nativeLang(),
                plan.learningLang(),
                plan.cefrs(),
                validZoneId,
                offset,
                plan.createdAt(),
                plan.updatedAt()
        );
    }

    private LearningPlanDetails toDetails(LearningPlan plan) {
        var zone = ZoneUtils.resolve(plan.zoneId(), plan.utcOffsetMinutes());
        var progress = learningHistoryService.findProgress(plan.userId(), plan.cefrs(), zone);
        learningStreakService.markCompletedIfDone(plan, progress, zone);
        var streak = learningStreakService.find(plan.userId(), zone);
        return new LearningPlanDetails(plan, progress, streak);
    }
}
