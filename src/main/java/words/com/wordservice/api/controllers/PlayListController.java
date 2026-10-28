package words.com.wordservice.api.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.mappers.PlayListRequestMapper;
import words.com.wordservice.api.mappers.PlayListRespondMapper;
import words.com.wordservice.api.requests.playlist.*;
import words.com.wordservice.api.responds.playlist.*;
import words.com.wordservice.main.services.WordPlayListService;
import words.com.wordservice.main.usecases.FindPublicPlayListCountsUseCase;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/play-list")
@RequiredArgsConstructor
@Validated
public class PlayListController {
    private final WordPlayListService wordPlayListService;
    private final FindPublicPlayListCountsUseCase findPublicPlayListCountsUseCase;
    private final PlayListRequestMapper requestMapper;
    private final PlayListRespondMapper respondMapper;


    @GetMapping
    public PagedModel<PlayListRespond> getPlayLists(
            @AuthenticationPrincipal User user,
            @Valid PlayListGetRequest request
    ) {
        var filter = requestMapper.toFilter(user, request);
        var paged = wordPlayListService.findBy(filter)
                .map(respondMapper::toRespond);

        return new PagedModel<>(paged);
    }

    @GetMapping("/public")
    public PagedModel<PlayListRespond> getPublicPlayLists(
            @Valid PublicPlayListGetRequest request
    ) {
        var filter = requestMapper.toFilter(request);
        var paged = wordPlayListService.findBy(filter)
                .map(respondMapper::toRespond);

        return new PagedModel<>(paged);
    }

    @GetMapping("/count")
    public PagedModel<PlayListCountRespond> getCountPlayLists(
            @AuthenticationPrincipal User user,
            @Valid PlayListCountGetRequest request
    ) {
        var filter = requestMapper.toCountFilter(user, request);
        var paged = wordPlayListService.findBy(filter)
                .map(respondMapper::toRespond);

        return new PagedModel<>(paged);
    }

    @GetMapping("/count/public")
    public PagedModel<PublicPlayListCountRespond> getCountPlayLists(
            @AuthenticationPrincipal User user,
            @Valid PublicPlayListCountGetRequest request
    ) {
        var filter = requestMapper.toPublicFilter(request);
        var paged = findPublicPlayListCountsUseCase.execute(user == null ? null : user.id(), filter)
                .map(respondMapper::toPublicRespond);

        return new PagedModel<>(paged);
    }

    @GetMapping("/random")
    public ResponseEntity<PlayListCountRespond> getRandomPlayList(
            @AuthenticationPrincipal User user
    ) {
        return wordPlayListService.findRandom(user.id())
                .map(respondMapper::toRespond)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/assigned")
    public List<AssignedPlaylistRespond> getAssignedPlayLists(
            @AuthenticationPrincipal User user
    ) {
        return respondMapper.toAssignedResponds(wordPlayListService.findAssigned(user.id()));
    }

    @PostMapping("/assign")
    public List<PlaylistIdRespond> assignPlayLists(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid AssignPlayListsRequest request) {
        return respondMapper.toIdResponds(wordPlayListService.assign(request.playListIds(), user.id()));
    }



    @PostMapping
    public void save(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid @NotEmpty List<PlayListCreateRequest> playLists) {
        wordPlayListService.save(requestMapper.toCreateTitles(user, playLists));
    }

    @PutMapping
    public void update(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid List<PlayListUpdateRequest> playLists) {
        wordPlayListService.update(requestMapper.toUpdateTitles(user, playLists));
    }

    @PutMapping("/grades")
    public void updateGrades(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid List<PlayListGradeRequest> grades) {
        wordPlayListService.updateGrades(requestMapper.toPlayListGrades(user, grades));
    }

    @DeleteMapping("/delete")
    public void delete(@AuthenticationPrincipal User user, @RequestParam List<String> ids) {
        wordPlayListService.delete(requestMapper.toUserPlayLists(user, ids));
    }
}
