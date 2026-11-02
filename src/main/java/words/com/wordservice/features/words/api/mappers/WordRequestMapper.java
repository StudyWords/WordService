package words.com.wordservice.features.words.api.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.nure.words.gateway.userctx.Role;
import ua.nure.words.gateway.userctx.User;
import words.com.wordservice.features.shared.utils.DecodeUtils;
import words.com.wordservice.features.shared.utils.IdGenerator;
import words.com.wordservice.features.shared.utils.ModelUtils;
import words.com.wordservice.features.words.api.requests.WordCreateRequest;
import words.com.wordservice.features.words.api.requests.WordDeleteRequest;
import words.com.wordservice.features.words.api.requests.WordGetRequest;
import words.com.wordservice.features.words.main.enums.WordType;
import words.com.wordservice.features.words.main.models.WordDeleteDto;
import words.com.wordservice.features.words.main.models.WordDraft;
import words.com.wordservice.features.words.main.models.WordFilter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WordRequestMapper {
    private final IdGenerator idGenerator;

    public WordFilter toFilter(User user, WordGetRequest request) {
        var builder = request.toBuilder();

        if (user != null && !user.role().equals(Role.ADMINISTRATION) && request.userId() != null){
            var previousUserId = request.userId();
            builder.userId(new WordGetRequest.UserId(user.id(), previousUserId.isIn()));
        }
        if (user == null || !user.role().equals(Role.ADMINISTRATION)){
            builder.types(Collections.singleton(WordType.PUBLIC));
        }
        DecodeUtils.decode(builder::original, request::original);
        DecodeUtils.decode(builder::translate, request::translate);
        DecodeUtils.decodeCollection(builder::categories, request::categories);

        return ModelUtils.convert(builder.build(), WordFilter.class);
    }


    public List<WordDraft> toWordDrafts(User user, Collection<WordCreateRequest> words) {
        return words.stream()
                .map(word -> toWordDraft(word, user))
                .toList();
    }

    public WordDraft toWordDraft(WordCreateRequest word, User user) {
        return new WordDraft(
                idGenerator.generate(),
                word.original(),
                word.lang(),
                word.translate(),
                word.translateLang(),
                word.category(),
                word.soundFileName(),
                word.imageFileName(),
                user.role() == Role.ADMINISTRATION ? WordType.PUBLIC : WordType.CUSTOM,
                word.description(),
                word.cefr()
        );
    }

    public WordDeleteDto toDeleteDto(WordDeleteRequest request) {
        return ModelUtils.convert(request, WordDeleteDto.class);
    }
}
