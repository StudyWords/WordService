package words.com.wordservice.main.services;

import org.springframework.data.domain.Page;
import words.com.wordservice.main.models.filters.WordPlayListCountFilter;
import words.com.wordservice.main.models.filters.WordPlayListFilter;
import words.com.wordservice.main.models.playlist.*;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;


public interface WordPlayListService {

    Page<WordPlayList> findBy(WordPlayListFilter filter);

    Page<WordPlayListCount> findBy(WordPlayListCountFilter filter);

    void save(Collection<PlayListTitle> playLists);

    void update(Collection<PlayListTitle> playLists);

    Set<String> assign(Collection<String> playListIds, String userId);

    Set<AssignedPlaylist> findAssigned(String userId);

    void updateGrades(Collection<PlayListGrade> grades);

    void delete(UserPlayLists playLists);

    Optional<WordPlayListCount> findRandom(String userId);
}
