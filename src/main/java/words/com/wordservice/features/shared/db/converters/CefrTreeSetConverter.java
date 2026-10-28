package words.com.wordservice.features.shared.db.converters;

import jakarta.persistence.Converter;
import words.com.wordservice.features.shared.main.enums.CEFR;

@Converter
public class CefrTreeSetConverter extends EnumTreeSetConverter<CEFR> {
    protected CefrTreeSetConverter() {
        super(CEFR.class);
    }
}
