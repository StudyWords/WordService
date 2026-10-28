package words.com.wordservice.main.utils.impls;

import org.springframework.stereotype.Component;
import words.com.wordservice.main.utils.IdGenerator;

import java.util.UUID;

@Component
class UuidIdGenerator implements IdGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
