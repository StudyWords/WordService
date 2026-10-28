package words.com.wordservice.features.shared.api.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import words.com.wordservice.features.shared.utils.ModelUtils;
import words.com.wordservice.features.shared.utils.Range;

import java.util.Set;

@Component
class JsonStringToRangeConverter implements GenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Set.of(new ConvertiblePair(String.class, Range.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        var json = (String) source;
        if (!StringUtils.hasText(json)) {
            return null;
        }
        try {
            return ModelUtils.read(json, targetType.getType());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Range must be a JSON object like {\"from\":...,\"to\":...}: " + json, e);
        }
    }
}
