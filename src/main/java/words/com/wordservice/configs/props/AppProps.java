package words.com.wordservice.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.props")
public record AppProps(String filePath) {
}
