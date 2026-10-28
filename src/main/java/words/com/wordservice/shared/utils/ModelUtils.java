package words.com.wordservice.shared.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.TreeSet;

public final class ModelUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private ModelUtils() {
    }

    public static <T> T convert(Object from, Class<T> type) {
        return OBJECT_MAPPER.convertValue(from, type);
    }

    public static <E> TreeSet<E> readTreeSet(String json, Class<E> elementType) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(
                json,
                OBJECT_MAPPER.getTypeFactory().constructCollectionType(TreeSet.class, elementType)
        );
    }
}
