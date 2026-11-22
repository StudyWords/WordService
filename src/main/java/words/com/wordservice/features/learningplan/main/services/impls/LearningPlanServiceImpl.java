package words.com.wordservice.features.learningplan.main.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import words.com.wordservice.features.learningplan.db.daos.LearningPlanDao;
import words.com.wordservice.features.learningplan.db.searches.LearningPlanSearch;
import words.com.wordservice.features.learningplan.main.mappers.LearningPlanDomainMapper;
import words.com.wordservice.features.learningplan.main.mappers.LearningPlanSearchMapper;
import words.com.wordservice.features.learningplan.main.models.LearningPlan;
import words.com.wordservice.features.learningplan.main.models.LearningPlanFilter;
import words.com.wordservice.features.learningplan.main.services.LearningPlanService;
import words.com.wordservice.features.shared.utils.ZoneUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class LearningPlanServiceImpl implements LearningPlanService {
    private final LearningPlanDao learningPlanDao;
    private final LearningPlanSearchMapper learningPlanSearchMapper;
    private final LearningPlanDomainMapper learningPlanDomainMapper;

    @Override
    public Page<LearningPlan> findBy(LearningPlanFilter filter) {
        var search = learningPlanSearchMapper.toSearch(filter);
        var pageable = learningPlanSearchMapper.toPageable(filter);
        return learningPlanDao.findBy(search, pageable)
                .map(learningPlanDomainMapper::toModel);
    }

    @Override
    public LearningPlan create(LearningPlan learningPlan) {
        return Optional.ofNullable(learningPlan)
                .map(this::withValidZone)
                .map(learningPlanDomainMapper::toEntity)
                .map(learningPlanDao::save)
                .map(learningPlanDomainMapper::toModel)
                .orElse(null);
    }

    @Override
    public void update(LearningPlan learningPlan) {
        var plan = withValidZone(learningPlan);
        if (plan.zoneId() == null && plan.utcOffsetMinutes() == null) {
            plan = findByUserId(plan.userId())
                    .map(stored -> withZone(learningPlan, stored.zoneId(), stored.utcOffsetMinutes()))
                    .orElse(plan);
        }
        learningPlanDao.update(learningPlanDomainMapper.toEntity(plan));
    }

    @Override
    public void updateTimeZone(String userId, @Nullable String zoneId, @Nullable Integer utcOffsetMinutes) {
        var search = LearningPlanSearch.builder().userId(userId).build();
        learningPlanDao.findBy(search).stream()
                .findFirst()
                .map(entity -> entity.toBuilder()
                        .zoneId(ZoneUtils.normalize(zoneId))
                        .utcOffsetMinutes(utcOffsetMinutes == null ? 0 : utcOffsetMinutes)
                        .build())
                .ifPresent(learningPlanDao::update);
    }

    private LearningPlan withValidZone(LearningPlan plan) {
        return withZone(plan, ZoneUtils.normalize(plan.zoneId()), plan.utcOffsetMinutes());
    }

    private LearningPlan withZone(LearningPlan plan, @Nullable String zoneId, @Nullable Integer utcOffsetMinutes) {
        return new LearningPlan(
                plan.userId(),
                plan.wordsPerDay(),
                plan.nativeLang(),
                plan.learningLang(),
                plan.cefrs(),
                zoneId,
                utcOffsetMinutes,
                plan.createdAt(),
                plan.updatedAt()
        );
    }
}
