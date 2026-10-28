package words.com.wordservice.features.words.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.features.words.api.mappers.UserWordRequestMapper;
import words.com.wordservice.features.words.api.mappers.UserWordRespondMapper;
import words.com.wordservice.features.words.api.requests.*;
import words.com.wordservice.features.words.api.responds.UserWordRespond;
import words.com.wordservice.features.words.main.services.UserWordService;

import java.util.List;
import java.util.Set;

@RestController
@ResponseBody
@RequestMapping("/user-words")
@RequiredArgsConstructor
public class UserWordController {
    private final UserWordService userWordService;
    private final UserWordRequestMapper requestMapper;
    private final UserWordRespondMapper respondMapper;


    @GetMapping
    public PagedModel<UserWordRespond> getAll(@AuthenticationPrincipal User user, @ModelAttribute UserWordFilterRequest filter){
        var filterModel = requestMapper.toFilter(user, filter);
        var paged = userWordService.findBy(filterModel).map(respondMapper::toRespond);
        return new PagedModel<>(paged);
    }

    @PostMapping
    public void create(@AuthenticationPrincipal User user, @RequestBody @Valid Set<UserWordCreateRequest> words) {
        userWordService.save(requestMapper.toCreateDtos(user, words));
    }

    @PostMapping("pin")
    public List<UserWordRespond> savePin(@AuthenticationPrincipal User user, @RequestBody @Valid Set<UserWordPinRequest> requests) {
        return respondMapper.toResponds(userWordService.link(requestMapper.toLinks(user, requests)));
    }

    @PostMapping("delete")
    public void delete(@AuthenticationPrincipal User user, @RequestBody @Valid List<UserWordDeleteRequest> requests) {
        userWordService.delete(requestMapper.toDeleteDtos(user.id(), requests));
    }

    @PutMapping
    public void update(@AuthenticationPrincipal User user, @RequestBody @Valid UserWordEditRequest request) {
        var model = requestMapper.toModel(user, request);
        userWordService.update(model);
    }
}
