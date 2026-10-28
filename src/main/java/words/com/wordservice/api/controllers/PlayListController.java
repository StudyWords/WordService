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
import words.com.wordservice.api.mappers.PlayListApiMapper;
import words.com.wordservice.api.requests.playlist.*;
import words.com.wordservice.api.responds.playlist.*;
import words.com.wordservice.main.models.playlist.UserPlayLists;
import words.com.wordservice.main.services.WordPlayListService;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/play-list")
@RequiredArgsConstructor
@Validated
public class PlayListController {
    private final WordPlayListService wordPlayListService;
    private final PlayListApiMapper playListApiMapper;


    @GetMapping
    public PagedModel<PlayListRespond> getPlayLists(
            @AuthenticationPrincipal User user,
            @Valid PlayListGetRequest request
    ) {
        var filter = playListApiMapper.toFilter(user, request);
        var paged = wordPlayListService.findBy(filter)
                .map(playListApiMapper::toRespond);

        return new PagedModel<>(paged);
    }

    @GetMapping("/public")
    public PagedModel<PlayListRespond> getPublicPlayLists(
            @Valid PublicPlayListGetRequest request
    ) {
        var filter = playListApiMapper.toFilter(request);
        var paged = wordPlayListService.findBy(filter)
                .map(playListApiMapper::toRespond);

        return new PagedModel<>(paged);
    }

    @GetMapping("/count")
    public PagedModel<PlayListCountRespond> getCountPlayLists(
            @AuthenticationPrincipal User user,
            @Valid PlayListCountGetRequest request
    ) {
        var filter = playListApiMapper.toCountFilter(user, request);
        var paged = wordPlayListService.findBy(filter)
                .map(playListApiMapper::toRespond);

        return new PagedModel<>(paged);
    }

    @GetMapping("/count/public")
    public PagedModel<PublicPlayListCountRespond> getCountPlayLists(
            @AuthenticationPrincipal User user,
            @Valid PublicPlayListCountGetRequest request
    ) {
        var filter = playListApiMapper.toPublicFilter(user, request);
        var paged = wordPlayListService.findBy(filter)
                .map(playListApiMapper::toPublicRespond);

        return new PagedModel<>(paged);
    }

    @GetMapping("/random")
    public ResponseEntity<PlayListCountRespond> getRandomPlayList(
            @AuthenticationPrincipal User user
    ) {
        return wordPlayListService.findRandom(user.id())
                .map(playListApiMapper::toRespond)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/assigned")
    public List<AssignedPlaylistRespond> getAssignedPlayLists(
            @AuthenticationPrincipal User user
    ) {
        return wordPlayListService.findAssigned(user.id())
                .stream()
                .map(playListApiMapper::toAssignedRespond)
                .toList();
    }

    @PostMapping("/assign")
    public List<PlaylistIdRespond> assignPlayLists(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid AssignPlayListsRequest request) {
        return wordPlayListService.assign(request.playListIds(), user.id())
                .stream()
                .map(PlaylistIdRespond::new)
                .toList();
    }



    @PostMapping
    public void save(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid @NotEmpty List<PlayListCreateRequest> playLists) {
        var models = playLists.stream().map( it ->
                playListApiMapper.toPlayListTitle(user, it)
        ).toList();
        wordPlayListService.save(models);
    }

    @PutMapping
    public void update(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid List<PlayListUpdateRequest> playLists) {
        var models = playLists.stream()
                .map(it -> playListApiMapper.toPlayListTitle(user, it))
                .toList();
        wordPlayListService.update(models);
    }

    @PutMapping("/grades")
    public void updateGrades(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid List<PlayListGradeRequest> grades) {
        wordPlayListService.updateGrades(grades.stream().map( it ->
                playListApiMapper.toPlayListGrade(user, it)
        ).toList());
    }

    @DeleteMapping("/delete")
    public void delete(@AuthenticationPrincipal User user, @RequestParam List<String> ids) {
        wordPlayListService.delete(new UserPlayLists(user.id(), ids));
    }
}
