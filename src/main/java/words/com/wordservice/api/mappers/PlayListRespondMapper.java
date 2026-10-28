package words.com.wordservice.api.mappers;

import org.springframework.stereotype.Component;
import words.com.wordservice.api.responds.playlist.*;
import words.com.wordservice.main.models.playlist.AssignedPlaylist;
import words.com.wordservice.main.models.playlist.WordPlayList;
import words.com.wordservice.main.models.playlist.WordPlayListCount;
import words.com.wordservice.shared.utils.ModelUtils;

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
