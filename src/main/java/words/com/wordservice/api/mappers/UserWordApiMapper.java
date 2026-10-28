package words.com.wordservice.api.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import words.backend.authmodule.net.models.Role;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.requests.words.*;
import words.com.wordservice.api.responds.words.UserWordRespond;
import words.com.wordservice.api.utils.DecodeUtils;
import words.com.wordservice.main.models.filters.UserWordFilter;
import words.com.wordservice.main.models.words.*;
import words.com.wordservice.main.utils.IdGenerator;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserWordApiMapper {
    private final IdGenerator idGenerator;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
            .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final WordApiMapper wordApiMapper;

    public UserWordFilter toFilter(User user, UserWordFilterRequest getRequest) {
        var filterBuilder = objectMapper.convertValue(getRequest, UserWordFilter.class).toBuilder();
        DecodeUtils.decode(filterBuilder::original, getRequest::original);
        DecodeUtils.decode(filterBuilder::translate, getRequest::translate);
        DecodeUtils.decodeCollection(filterBuilder::categories, getRequest::categories);

        if (Role.ADMINISTRATION != user.role()){
            filterBuilder.userIds(Collections.singleton(user.id()));
        }

        return filterBuilder.build();
    }

    public UserWordRespond toRespond(UserWord model) {
        return objectMapper.convertValue(model, UserWordRespond.class);
    }

    public UserWordCreateDto toModel(User user, UserWordCreateRequest request) {
        return new UserWordCreateDto(
                idGenerator.generate(),
                user.id(),
                request.customSoundFileName(),
                request.customImageFileName(),
                wordApiMapper.toWordDraft(request.word(), user)
        );
    }

    public UserWordLink toModel(User user, UserWordPinRequest request) {
        return new UserWordLink(
                user.id(),
                request.wordId(),
                request.customSoundFileName(),
                request.customImageFileName()
        );
    }


    public UserWordDeleteDto toOptions(UserWordDeleteRequest request, String userId) {
        return new UserWordDeleteDto(
                request.id(),
                userId,
                request.wordId()
        );
    }

    public UserWordEditDto toModel(@AuthenticationPrincipal User user, @RequestBody @Valid UserWordEditRequest request) {
        return new UserWordEditDto(
                request.id(),
                user.id(),
                request.original(),
                request.lang(),
                request.translate(),
                request.translateLang(),
                request.category(),
                request.soundFileName(),
                request.imageFileName(),
                request.description(),
                request.cefr()
        );
    }
}
