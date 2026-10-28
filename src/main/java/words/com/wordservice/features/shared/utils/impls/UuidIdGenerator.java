package words.com.wordservice.features.shared.utils.impls;

import org.springframework.stereotype.Component;
import words.com.wordservice.features.shared.utils.IdGenerator;

import java.util.UUID;

@Component
class UuidIdGenerator implements IdGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
