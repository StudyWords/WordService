package words.com.wordservice.features.learninghistory.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.features.learninghistory.api.mappers.LearningHistoryRequestMapper;
import words.com.wordservice.features.learninghistory.api.mappers.LearningHistoryRespondMapper;
import words.com.wordservice.features.learninghistory.api.requests.LearningHistoryFilterRequest;
import words.com.wordservice.features.learninghistory.api.requests.StatisticsLearningHistoryFilterRequest;
import words.com.wordservice.features.learninghistory.api.responds.LearningHistoryRespond;
import words.com.wordservice.features.learninghistory.api.responds.StatisticsLearningHistoryRespond;
import words.com.wordservice.features.learninghistory.main.services.LearningHistoryService;

@RestController
@ResponseBody
@RequestMapping("/learning-history")
@RequiredArgsConstructor
public class LearningHistoryController {
    private final LearningHistoryService learningHistoryService;
    private final LearningHistoryRequestMapper requestMapper;
    private final LearningHistoryRespondMapper respondMapper;

    @GetMapping
    public PagedModel<LearningHistoryRespond> getLearningHistory(
            @AuthenticationPrincipal User user,
            @ModelAttribute LearningHistoryFilterRequest request) {
        var filter = requestMapper.toModel(user, request);
        var paged = learningHistoryService.findBy(filter).map(respondMapper::toRespond);
        return new PagedModel<>(paged);
    }

    @GetMapping("/statistics")
    public PagedModel<StatisticsLearningHistoryRespond> getStatisticsLearningHistory(
            @AuthenticationPrincipal User user,
            @ModelAttribute StatisticsLearningHistoryFilterRequest request) {
        var filter = requestMapper.toModel(user, request);
        var paged = learningHistoryService.findBy(filter).map(respondMapper::toRespond);
        return new PagedModel<>(paged);
    }

}
