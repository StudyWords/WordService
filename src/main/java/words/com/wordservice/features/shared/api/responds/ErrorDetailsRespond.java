package words.com.wordservice.features.shared.api.responds;

public record ErrorDetailsRespond(
        String message,
        String exceptionName
) {
}
