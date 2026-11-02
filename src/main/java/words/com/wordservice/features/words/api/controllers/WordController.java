package words.com.wordservice.features.words.api.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.nure.words.gateway.userctx.User;
import words.com.wordservice.features.words.api.mappers.WordRequestMapper;
import words.com.wordservice.features.words.api.mappers.WordRespondMapper;
import words.com.wordservice.features.words.api.requests.WordCreateRequest;
import words.com.wordservice.features.words.api.requests.WordDeleteRequest;
import words.com.wordservice.features.words.api.requests.WordGetRequest;
import words.com.wordservice.features.words.api.responds.WordRespond;
import words.com.wordservice.features.words.main.services.WordService;

import java.util.Set;

@RestController
@ResponseBody
@RequestMapping("/words")
@RequiredArgsConstructor
public class WordController {
    private final WordRequestMapper requestMapper;
    private final WordRespondMapper respondMapper;
    private final WordService wordService;

    @GetMapping
    PagedModel<WordRespond> get(@AuthenticationPrincipal(expression = "user") User user, @Valid @ModelAttribute WordGetRequest request) {
        var filter = requestMapper.toFilter(user, request);
        var paged = wordService.findBy(filter).map(respondMapper::toRespond);
        return new PagedModel<>(paged);
    }

    @PostMapping
    void save(@AuthenticationPrincipal(expression = "user") User user, @RequestBody @Valid @NotEmpty Set<WordCreateRequest> words) {
        wordService.save(requestMapper.toWordDrafts(user, words));
    }

    @DeleteMapping
    void delete(@RequestBody @Valid WordDeleteRequest request) {
        wordService.delete(requestMapper.toDeleteDto(request));
    }

}
