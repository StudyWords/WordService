package words.com.wordservice.api.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import words.backend.authmodule.net.models.Role;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.requests.words.WordCreateRequest;
import words.com.wordservice.api.requests.words.WordDeleteRequest;
import words.com.wordservice.api.requests.words.WordGetRequest;
import words.com.wordservice.api.utils.DecodeUtils;
import words.com.wordservice.main.models.enums.WordType;
import words.com.wordservice.main.models.filters.WordFilter;
import words.com.wordservice.main.models.words.WordDeleteDto;
import words.com.wordservice.main.models.words.WordDraft;
import words.com.wordservice.main.utils.IdGenerator;
import words.com.wordservice.shared.utils.ModelUtils;

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
