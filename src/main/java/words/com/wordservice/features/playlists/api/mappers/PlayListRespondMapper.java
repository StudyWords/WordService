package words.com.wordservice.features.playlists.api.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.features.playlists.api.responds.*;
import words.com.wordservice.features.playlists.main.models.AssignedPlaylist;
import words.com.wordservice.features.playlists.main.models.WordPlayList;
import words.com.wordservice.features.playlists.main.models.WordPlayListCount;
import words.com.wordservice.features.shared.utils.ModelUtils;

import java.util.Collection;
import java.util.List;

@Component
public class PlayListRespondMapper {

    public PlayListCountRespond toRespond(WordPlayListCount model) {
        return ModelUtils.convert(model, PlayListCountRespond.class);
    }

    public PublicPlayListCountRespond toPublicRespond(WordPlayListCount model) {
        return ModelUtils.convert(model, PublicPlayListCountRespond.class);
    }

    public PlayListRespond toRespond(WordPlayList model) {
        return ModelUtils.convert(model, PlayListRespond.class);
    }

    public AssignedPlaylistRespond toAssignedRespond(AssignedPlaylist assignedPlaylist) {
        return new AssignedPlaylistRespond(assignedPlaylist.id());
    }

    public List<AssignedPlaylistRespond> toAssignedResponds(Collection<AssignedPlaylist> assignedPlaylists) {
        return assignedPlaylists.stream()
                .map(this::toAssignedRespond)
                .toList();
    }

    public List<PlaylistIdRespond> toIdResponds(Collection<String> ids) {
        return ids.stream()
                .map(PlaylistIdRespond::new)
                .toList();
    }
}
