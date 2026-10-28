package words.com.wordservice.api.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import words.backend.authmodule.net.models.Role;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.requests.playlist.*;
import words.com.wordservice.api.utils.DecodeUtils;
import words.com.wordservice.main.models.enums.PlayListVisibility;
import words.com.wordservice.main.models.filters.WordPlayListCountFilter;
import words.com.wordservice.main.models.filters.WordPlayListFilter;
import words.com.wordservice.main.models.playlist.PlayListGrade;
import words.com.wordservice.main.models.playlist.PlayListTitle;
import words.com.wordservice.main.models.playlist.UserPlayLists;
import words.com.wordservice.main.utils.IdGenerator;
import words.com.wordservice.shared.utils.ModelUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlayListRequestMapper {
    private final IdGenerator idGenerator;

    public WordPlayListCountFilter toPublicFilter(PublicPlayListCountGetRequest getRequest) {
        var builder = ModelUtils.convert(getRequest, WordPlayListCountFilter.class).toBuilder();
        DecodeUtils.decode(builder::name, getRequest::name);

        return builder.build();
    }

    public WordPlayListCountFilter toCountFilter(@NonNull User user, PlayListCountGetRequest getRequest) {
        if (user.role().equals(Role.CUSTOMER)) {
            getRequest = getRequest.withUserIds(Collections.singleton(user.id()));
        }
        var builder = ModelUtils.convert(getRequest, WordPlayListCountFilter.class).toBuilder();
        DecodeUtils.decode(builder::name, getRequest::name);

        return builder.build();
    }

    public WordPlayListFilter toFilter(@NonNull User user, PlayListGetRequest getRequest) {
        if (!Role.ADMINISTRATION.equals(user.role())) {
            getRequest = getRequest.withUserIds(Collections.singleton(user.id()));
        }
        var builder = ModelUtils.convert(getRequest, WordPlayListFilter.class).toBuilder();
        DecodeUtils.decode(builder::name, getRequest::name);

        return builder.build();
    }

    public WordPlayListFilter toFilter(PublicPlayListGetRequest getRequest) {

        var builder = ModelUtils.convert(getRequest, WordPlayListFilter.class).toBuilder();
        DecodeUtils.decode(builder::name, getRequest::name);

        return builder
                .visibility(PlayListVisibility.PUBLIC)
                .build();
    }

    public List<PlayListTitle> toUpdateTitles(@NonNull User user, Collection<PlayListUpdateRequest> requests) {
        return requests.stream()
                .map(request -> toPlayListTitle(user, request))
                .toList();
    }

    public List<PlayListTitle> toCreateTitles(@NonNull User user, Collection<PlayListCreateRequest> requests) {
        return requests.stream()
                .map(request -> toPlayListTitle(user, request))
                .toList();
    }

    public List<PlayListGrade> toPlayListGrades(@NonNull User user, Collection<PlayListGradeRequest> requests) {
        return requests.stream()
                .map(request -> toPlayListGrade(user, request))
                .toList();
    }

    public UserPlayLists toUserPlayLists(@NonNull User user, List<String> ids) {
        return new UserPlayLists(user.id(), ids);
    }

    public PlayListTitle toPlayListTitle(@NonNull User user, @NonNull PlayListUpdateRequest request) {
        return new PlayListTitle(
                request.id(),
                user.id(),
                request.name()
        );
    }

    public PlayListTitle toPlayListTitle(@NonNull User user, PlayListCreateRequest request) {
        return new PlayListTitle(
                idGenerator.generate(),
                user.id(),
                request.name()
        );
    }

    public PlayListGrade toPlayListGrade(@NonNull User user, PlayListGradeRequest request) {
        return new PlayListGrade(
                request.wordId(),
                user.id(),
                request.wordGrade()
        );
    }
}
