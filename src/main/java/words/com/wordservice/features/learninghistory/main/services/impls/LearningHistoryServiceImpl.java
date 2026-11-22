package words.com.wordservice.features.learninghistory.main.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import words.com.wordservice.features.learninghistory.db.daos.LearningHistoryDao;
import words.com.wordservice.features.learninghistory.db.entities.LearningHistoryEntity;
import words.com.wordservice.features.learninghistory.db.searches.LearningProgressSearch;
import words.com.wordservice.features.learninghistory.main.mappers.LearningHistoryDomainMapper;
import words.com.wordservice.features.learninghistory.main.mappers.LearningHistorySearchMapper;
import words.com.wordservice.features.learninghistory.main.models.*;
import words.com.wordservice.features.learninghistory.main.services.LearningHistoryService;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.words.db.daos.UserWordDao;
import words.com.wordservice.features.words.db.entities.UserWordEntity;
import words.com.wordservice.features.words.db.searches.Operations;
import words.com.wordservice.features.words.db.searches.UserWordSearch;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class LearningHistoryServiceImpl implements LearningHistoryService {
    private final LearningHistoryDomainMapper learningHistoryMapper;
    private final LearningHistoryDao learningHistoryDao;
    private final UserWordDao userWordDao;
    private final LearningHistorySearchMapper learningHistorySearchMapper;

    @Override
    public Page<LearningHistory> findBy(LearningHistoryFilter filter) {
        var histories = learningHistoryDao.findBy(
                learningHistorySearchMapper.toSearch(filter),
                learningHistorySearchMapper.toPageable(filter)
        );
        var wordIds = histories.stream().map(LearningHistoryEntity::getWordId).collect(Collectors.toSet());


        var wordById = new HashMap<String, UserWordEntity>();

        var search = UserWordSearch.builder()
                .wordIds(wordIds)
                .userWordIds(wordIds)
                .operation(Operations.OR)
                .build();
        var words = userWordDao.findBy(search);
        words.forEach(e -> wordById.put(e.getWord().getId(), e));
        words.forEach(e -> wordById.put(e.getId(), e));

        return histories.map(e -> learningHistoryMapper.toModel(e, wordById.get(e.getWordId())));
    }

    @Override
    public Page<StatisticsLearningHistory> findBy(StatisticsLearningHistoryFilter filter) {
        return learningHistoryDao.findBy(learningHistorySearchMapper.toSearch(filter), Pageable.unpaged())
                .map(learningHistoryMapper::toStatisticsModel);
    }

    @Override
    public LearningProgress findProgress(String userId, Set<CEFR> cefrs, ZoneId zone) {
        var today = LocalDate.now(zone);
        var dayStart = today.atStartOfDay(zone).toOffsetDateTime();
        var dayEnd = today.plusDays(1).atStartOfDay(zone).toOffsetDateTime();
        var search = LearningProgressSearch.builder()
                .userId(userId)
                .cefrs(cefrs)
                .dayStart(dayStart)
                .dayEnd(dayEnd)
                .build();
        return learningHistoryMapper.toModel(learningHistoryDao.findProgress(search));
    }
}
