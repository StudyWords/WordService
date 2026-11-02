package words.com.wordservice.features.learningplan.api.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.nure.words.gateway.userctx.User;
import words.com.wordservice.features.learningplan.api.mappers.LearningPlanRequestMapper;
import words.com.wordservice.features.learningplan.api.mappers.LearningPlanRespondMapper;
import words.com.wordservice.features.learningplan.api.requests.LearningPlanRequest;
import words.com.wordservice.features.learningplan.api.responds.LearningPlanDetailsRespond;
import words.com.wordservice.features.learningplan.api.responds.LearningPlanRespond;
import words.com.wordservice.features.learningplan.main.services.LearningPlanService;
import words.com.wordservice.features.learningplan.main.usecases.FindLearningPlanDetailsUseCase;

@ResponseBody
@RestController
@RequiredArgsConstructor
@RequestMapping("/learning-plan")
public class LearningPlanController {
    private final LearningPlanService learningPlanService;
    private final FindLearningPlanDetailsUseCase findLearningPlanDetailsUseCase;
    private final LearningPlanRequestMapper requestMapper;
    private final LearningPlanRespondMapper respondMapper;

    @Nullable
    @GetMapping
    LearningPlanDetailsRespond getLearningPlan(
            @AuthenticationPrincipal(expression = "user") User user,
            @RequestParam(defaultValue = "0") @Min(-840) @Max(840) int utcOffsetMinutes) {
        return findLearningPlanDetailsUseCase.execute(user.id(), utcOffsetMinutes)
                .map(respondMapper::toRespond)
                .orElse(null);
    }

    @PostMapping
    @Validated
    LearningPlanRespond createLearningPlan(
            @AuthenticationPrincipal(expression = "user") User user,
            @RequestBody @Valid LearningPlanRequest request) {
        var model = requestMapper.toModel(request, user.id());
        var result = learningPlanService.create(model);
        return respondMapper.toRespond(result);
    }


    @PutMapping
    @Validated
    void update(
            @AuthenticationPrincipal(expression = "user") User user,
            @RequestBody @Valid LearningPlanRequest request) {
        var model = requestMapper.toModel(request, user.id());
        learningPlanService.update(model);
    }
}
