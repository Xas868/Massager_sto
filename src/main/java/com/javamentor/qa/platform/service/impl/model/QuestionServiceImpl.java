package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReputationDao;
import com.javamentor.qa.platform.dao.abstracts.model.TagDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl extends ReadWriteServiceImpl<Question, Long> implements QuestionService {

    private final QuestionDao questionDao;
    private final ReputationDao reputationDao;
    private final TagDao tagDao;

    public QuestionServiceImpl(QuestionDao questionDao, ReputationDao reputationDao, TagDao tagDao) {
        super(questionDao);
        this.questionDao = questionDao;
        this.reputationDao = reputationDao;
        this.tagDao = tagDao;
    }

    @Override
    public Optional<Long> getCountByQuestion() {
        return questionDao.getCountQuestion();
    }

    public Optional<Question> getQuestionByIdWithAuthor(Long id) {
        return questionDao.getQuestionByIdWithAuthor(id);
    }

    @Override
    public void persist(Question question) {

        List<Tag> listTagForQuestion = new ArrayList<>();

        List<String> listTagName = question.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        List<Tag> tagsThatExistsInDatabase = tagDao.getListTagsByListOfTagName(listTagName);
        Map<String, Tag> mapTagsThatExistsInDatabase = tagsThatExistsInDatabase.stream()
                .collect(Collectors.toMap(Tag::getName, tag -> tag));

        for (String tagName : listTagName) {
            if (mapTagsThatExistsInDatabase.containsKey(tagName)) {
                listTagForQuestion.add(mapTagsThatExistsInDatabase.get(tagName));
            } else {
                Tag tag = new Tag();
                tag.setName(tagName);
                tagDao.persist(tag);
                listTagForQuestion.add(tag);
            }
        }
        question.setTags(listTagForQuestion);
        int countUpVote = 5;
        reputationDao.persist(Reputation.builder()
                .count(countUpVote)
                .persistDate(Timestamp.from(Instant.now()).toLocalDateTime())
                .type(ReputationType.Question)
                .author(question.getUser())
                .sender(question.getUser())
                .question(question)
                .build());

        super.persist(question);

    }
}
