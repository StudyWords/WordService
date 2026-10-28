package words.com.wordservice.domain.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import words.com.wordservice.configs.props.AppProps;

@Component
@RequiredArgsConstructor
public class FileNameMapper {
    private final AppProps props;

    public String toLink(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            return null;
        }
        return props.filePath() + "/" + fileName;
    }
}
