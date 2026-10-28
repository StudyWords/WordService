package words.com.wordservice.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.mappers.PinPlayListRequestMapper;
import words.com.wordservice.api.requests.pins.PinPlayRequest;
import words.com.wordservice.main.services.PinPlayListService;

import java.util.List;

@RestController
@ResponseBody
@RequiredArgsConstructor
@Validated
public class PinController {
    private final PinPlayListService pinService;
    private final PinPlayListRequestMapper requestMapper;

    @PostMapping("/pin")
    public void pin(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid List<PinPlayRequest> requests
    ){
        pinService.pin(requestMapper.toUserPins(user, requests));
    }

    @PostMapping("/unpin")
    public void unpin(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid List<PinPlayRequest> requests
    ){
        pinService.unpin(requestMapper.toUserPinIds(user, requests));
    }
}
