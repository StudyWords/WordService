package words.com.wordservice.features.words.api.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.nure.words.gateway.userctx.Role;
import ua.nure.words.gateway.userctx.User;
import words.com.wordservice.features.shared.utils.DecodeUtils;
import words.com.wordservice.features.shared.utils.IdGenerator;
import words.com.wordservice.features.shared.utils.ModelUtils;
import words.com.wordservice.features.words.api.requests.*;
import words.com.wordservice.features.words.main.models.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserWordRequestMapper {
    private final IdGenerator idGenerator;
    private final WordRequestMapper wordRequestMapper;

    public UserWordFilter toFilter(User user, UserWordFilterRequest getRequest) {
        var filterBuilder = ModelUtils.convert(getRequest, UserWordFilter.class).toBuilder();
        DecodeUtils.decode(filterBuilder::original, getRequest::original);
        DecodeUtils.decode(filterBuilder::translate, getRequest::translate);
        DecodeUtils.decodeCollection(filterBuilder::categories, getRequest::categories);

        if (Role.ADMINISTRATION != user.role()){
            filterBuilder.userIds(Collections.singleton(user.id()));
        }

        return filterBuilder.build();
    }

    public List<UserWordCreateDto> toCreateDtos(User user, Collection<UserWordCreateRequest> requests) {
        return requests.stream()
                .map(request -> toModel(user, request))
                .toList();
    }

    public List<UserWordLink> toLinks(User user, Collection<UserWordPinRequest> requests) {
        return requests.stream()
                .map(request -> toModel(user, request))
                .toList();
    }

    public Set<UserWordDeleteDto> toDeleteDtos(String userId, Collection<UserWordDeleteRequest> requests) {
        return requests.stream()
                .map(request -> toDeleteDto(request, userId))
                .collect(Collectors.toSet());
    }

    public UserWordCreateDto toModel(User user, UserWordCreateRequest request) {
        return new UserWordCreateDto(
                idGenerator.generate(),
                user.id(),
                request.customSoundFileName(),
                request.customImageFileName(),
                wordRequestMapper.toWordDraft(request.word(), user)
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


    public UserWordDeleteDto toDeleteDto(UserWordDeleteRequest request, String userId) {
        return new UserWordDeleteDto(
                request.id(),
                userId,
                request.wordId()
        );
    }

    public UserWordEditDto toModel(User user, UserWordEditRequest request) {
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
