package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static java.lang.String.valueOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestQuestionResourceController extends AbstractClassForDRRiderMockMVCTests {

    // Голосование ЗА вопрос
    // Устанавливает голос +1 за вопрос и +10 к репутации автора вопроса
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/upVote/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/upVote/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void upVote() throws Exception {

        mockMvc.perform(post("/api/user/question/101/upVote")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(1)));

        String count1 = valueOf(entityManager.createQuery("select sum(r.count) as s from Reputation as r where r.author = 110")
                .getResultList());
        count1 = count1.replaceAll("[()<\\[\\]>]","");
        assertThat(count1).isEqualTo("1510");
    }

    // Голосование ЗА вопрос
    // Передают не верный questionId
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/upVotePassWrongQuestionId/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/upVotePassWrongQuestionId/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void upVotePassWrongQuestionId() throws Exception {

        mockMvc.perform(post("/api/user/question/123/upVote")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("Can't find question with id:123")));
    }

    // Голосование ЗА вопрос
    // Пользователь уже голосовал за этот вопрос
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/upVoteIfTheUserHasAlreadyVoted/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/upVoteIfTheUserHasAlreadyVoted/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void upVoteIfTheUserHasAlreadyVoted() throws Exception {

        mockMvc.perform(post("/api/user/question/101/upVote")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("User was voting")));
    }


    // Голосование ПРОТИВ
    // Устанавливает голос -1 за вопрос и -5 к репутации автора вопроса
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/downVote/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/downVote/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void downVote() throws Exception {

        mockMvc.perform(post("/api/user/question/101/downVote")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(1)));

        String count1 = valueOf(entityManager.createQuery("select sum(r.count) as s from Reputation as r where r.author = 110")
                .getResultList());
        count1 = count1.replaceAll("[()<\\[\\]>]","");
        assertThat(count1).isEqualTo("95");
    }

    // Голосование ПРОТИВ
    // Передают не верный questionId
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/downVotePassWrongQuestionId/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/downVotePassWrongQuestionId/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void downVotePassWrongQuestionId() throws Exception {

        mockMvc.perform(post("/api/user/question/156/upVote")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("Can't find question with id:156")));
    }

    // Голосование ПРОТИВ
    // Пользователь уже голосовал за этот вопрос
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/downVoteIfTheUserHasAlreadyVoted/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/downVoteIfTheUserHasAlreadyVoted/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void downVoteIfTheUserHasAlreadyVoted() throws Exception {

        mockMvc.perform(post("/api/user/question/101/upVote")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("User was voting")));
    }
}
