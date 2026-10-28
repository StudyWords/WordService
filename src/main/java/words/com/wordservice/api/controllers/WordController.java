package words.com.wordservice.api.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.mappers.WordRequestMapper;
import words.com.wordservice.api.mappers.WordRespondMapper;
import words.com.wordservice.api.requests.words.WordCreateRequest;
import words.com.wordservice.api.requests.words.WordDeleteRequest;
import words.com.wordservice.api.requests.words.WordGetRequest;
import words.com.wordservice.api.responds.words.WordRespond;
import words.com.wordservice.main.services.WordService;

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
    PagedModel<WordRespond> get(@AuthenticationPrincipal User user, @Valid @ModelAttribute WordGetRequest request){
        var filter = requestMapper.toFilter(user, request);
        var paged = wordService.findBy(filter).map(respondMapper::toRespond);
        return new PagedModel<>(paged);
    }

    @PostMapping
    void save(@AuthenticationPrincipal User user, @RequestBody @Valid @NotEmpty Set<WordCreateRequest> words) {
        wordService.save(requestMapper.toWordDrafts(user, words));
    }

    @DeleteMapping
    void delete(@RequestBody @Valid WordDeleteRequest request) {
        wordService.delete(requestMapper.toDeleteDto(request));
    }

}
