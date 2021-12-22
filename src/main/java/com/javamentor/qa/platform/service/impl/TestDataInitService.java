package com.javamentor.qa.platform.service.impl;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TestDataInitService {

    private final RoleService roleService;
    private final UserService userService;
    private final TagService tagService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final ReputationService reputationService;
    private final VoteQuestionService voteQuestionService;

    private final long NUM_OF_USERS = 10L;
    private final long NUM_OF_TAGS = 5L;
    private final long NUM_OF_QUESTIONS = 20L;
    private final long NUM_OF_ANSWERS = 50L;
    private final long NUM_OF_REPUTATIONS = 10L;
    private final long NUM_OF_VOTEQUESTIONS = 20L;


    public TestDataInitService(RoleService roleService, UserService userService, TagService tagService, QuestionService questionService, AnswerService answerService, ReputationService reputationService, VoteQuestionService voteQuestionService) {
        this.roleService = roleService;
        this.userService = userService;
        this.tagService = tagService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.reputationService = reputationService;
        this.voteQuestionService = voteQuestionService;
    }

    public void createRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_ADMIN"));
        roles.add(new Role("ROLE_USER"));
        roleService.persistAll(roles);
    }

    public void createUsers() {
        List<User> users = new ArrayList<>();
        List<Role> roles = roleService.getAll();
        for (int i = 1; i <= NUM_OF_USERS; i++) {
            Role role = roles.get(new Random().nextInt(roles.size()));
            User user = User.builder()
                    .email("user" + i + "@mail.ru")
                    .password("user" + i)
                    .fullName("User " + i)
                    .city("Moscow")
                    .about("I'm Test user #" + i)
                    .nickname("user_" + i)
                    .role(role)
                    .isEnabled(true)
                    .isDeleted(false)
                    .imageLink("/images/noUserAvatar.png")
                    .build();
            users.add(user);
        }

        userService.persistAll(users);
    }

    public void createTags() {
        List<Tag> tags = new ArrayList<>();
        for (int i = 1; i <= NUM_OF_TAGS; i++) {
            Tag tag = Tag.builder()
                    .name("Tag " + i)
                    .description("Description of tag " + i)
                    .build();
            tags.add(tag);
        }

        tagService.persistAll(tags);
    }

    public void createQuestions() {
        List<Question> questions = new ArrayList<>();
        for (int i = 1; i <= NUM_OF_QUESTIONS; i++) {
            Question question = Question.builder()
                    .title("Question " + i)
                    .description("What you think about question " + i + "?")
                    .user(getRandomUser())
                    .tags(getRandomTagList())
                    .build();
            questions.add(question);
        }

        questionService.persistAll(questions);
    }

    public void createAnswers() {
        List<Answer> answers = new ArrayList<>();
        for (int i = 1; i <= NUM_OF_ANSWERS; i++) {
            Answer answer = Answer.builder()
                    .htmlBody("Answer " + i)
                    .user(getRandomUser())
                    .question(getRandomQuestion())
                    .isDeleted(false)
                    .isHelpful(false)
                    .isDeletedByModerator(false)
                    .build();
            answers.add(answer);
        }

        answerService.persistAll(answers);
    }
    public void createReputations() {
        List<Reputation> reputations = new ArrayList<>();
        for (long i = 1; i <= NUM_OF_REPUTATIONS; i++) {
            Reputation reputation = Reputation.builder()
                    .persistDate(LocalDateTime.now())
                    .author(userService.getById(i).get()) // При getRandomUser  могут не быть все авторы
                    .sender(null)
                    .count(((Number) (getRandomUser().getId() * 100)).intValue())
                    .type(ReputationType.Question)
                    .question(getRandomQuestion())
                    .answer(null)
                    .build();
            reputations.add(reputation);
        }
        reputationService.persistAll(reputations);
    }
    public void createVoteQuestion() {
        List<VoteQuestion> voteQuestions = new ArrayList<>();
        for (long i = 1; i <= NUM_OF_VOTEQUESTIONS; i++) {
            VoteQuestion voteQuestion = VoteQuestion.builder()
                    .question(questionService.getById(i).get()) // При getRandomQuestion могут не быть все вопросы
                    .vote(new Random().nextInt(100) % 2 == 0 ? VoteType.UP_VOTE : VoteType.DOWN_VOTE)
                    .localDateTime(LocalDateTime.now())
                    .user(getRandomUser())
                    .build();
            voteQuestions.add(voteQuestion);
        }
        voteQuestionService.persistAll(voteQuestions);
    }

    private List<Tag> getRandomTagList() {
        List<Tag> tags = tagService.getAll();
        int numOfDeleteTags = new Random().nextInt(tags.size());
        for (int i = 0; i < numOfDeleteTags; i++) {
            tags.remove(new Random().nextInt(tags.size()));
        }
        return tags;
    }

    private User getRandomUser() {
        List<User> users = userService.getAll();
        return users.get(new Random().nextInt(users.size()));
    }

    private Question getRandomQuestion() {
        List<Question> questions = questionService.getAll();
        return questions.get(new Random().nextInt(questions.size()));
    }

    public void init() {
        createRoles();
        createUsers();
        createTags();
        createQuestions();
        createAnswers();
        createReputations();
        createVoteQuestion();
    }

}
