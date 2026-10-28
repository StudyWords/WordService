package words.com.wordservice.features.learninghistory.main.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import words.com.wordservice.features.learninghistory.db.daos.LearningHistoryDao;
import words.com.wordservice.features.learninghistory.main.mappers.LearningHistoryDomainMapper;
import words.com.wordservice.features.shared.main.events.LearningHistoryEvent;

@Component
@RequiredArgsConstructor
public class LearningHistoryListener {
    private final LearningHistoryDao learningHistoryDao;
    private final LearningHistoryDomainMapper learningHistoryDomainMapper;

    @EventListener
    public void handle(LearningHistoryEvent event) {
        if (event.entries().isEmpty()) {
            return;
        }
        var entities = event.entries().stream()
                .map(learningHistoryDomainMapper::toEntity)
                .toList();
        learningHistoryDao.save(entities);
    }
}
