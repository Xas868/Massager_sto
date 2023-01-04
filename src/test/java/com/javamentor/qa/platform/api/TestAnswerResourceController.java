package com.javamentor.qa.platform.api;

import com.google.gson.Gson;
import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.apache.coyote.Response;
import org.assertj.core.api.Assertions;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mapstruct.MapperConfig;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.xmlunit.util.Mapper;

import static java.lang.String.valueOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TestAnswerResourceController extends AbstractClassForDRRiderMockMVCTests {

    //Голосование за
    @SuppressWarnings("JpaQlInspection")
    @Test
    @Sql(scripts = "/script/TestAnswerResourceController/upVote/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestAnswerResourceController/upVote/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void upVote() throws Exception {
//        {questionId}
        mockMvc.perform(post("/api/user/question/101/answer/101/upVote")
//                        .param("password", "46xEPoAOu")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(1)));

        String count1 = valueOf(entityManager.createQuery("select sum(r.count) as s from Reputation as r where r.author = 110")
//                .setParameter("author",  101L)
                .getResultList());
        count1 = count1.replaceAll("[()<\\[\\]>]","");
        assertThat(count1).isEqualTo("110");
    }

    //Голосование за
    @Test
    @Sql(scripts = "/script/TestAnswerResourceController/downVote/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestAnswerResourceController/downVote/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void downVote() throws Exception {

        mockMvc.perform(post("/api/user/question/101/answer/101/downVote")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(-1)));

        String count1 = valueOf(entityManager.createQuery("select sum(r.count) as s from Reputation as r where r.author = 110")
                .getResultList());
        count1 = count1.replaceAll("[()<\\[\\]>]","");
        assertThat(count1).isEqualTo("95");
    }


    //Создание ответа на вопрос
//    @SuppressWarnings("JpaQlInspection")
    @SuppressWarnings("JpaQlInspection")
    @Test
    @Sql(scripts = "/script/TestAnswerResourceController/createAnswer/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestAnswerResourceController/createAnswer/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createAnswer() throws Exception {

                mockMvc.perform(post("/api/user/question/101/answer/add")
                        .content("string")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.userId", Is.is(101)))
                .andExpect(jsonPath("$.userReputation", Is.is(1000)))
                .andExpect(jsonPath("$.questionId", Is.is(101)))
                .andExpect(jsonPath("$.htmlBody", Is.is("string")))
                .andExpect(jsonPath("$.isHelpful", Is.is(false)))
                .andExpect(jsonPath("$.isUserVote", Is.is(IsNull.nullValue())))
                .andExpect(jsonPath("$.dateAccept", Is.is(IsNull.nullValue())))
                .andExpect(jsonPath("$.countValuable", Is.is(0)))
                .andExpect(jsonPath("$.image", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.nickName", Is.is("user_101")))
                .andExpect(jsonPath("$.commentOnTheAnswerToTheQuestion").isEmpty())
                .andReturn();







//        String persistDateTimeVar = String.valueOf(entityManager.createQuery("select persistDateTime from Answer where id = 1")
//                .getResultList());
//        u.user = 101 and u.question = 101

        String answer1 = valueOf(entityManager.createQuery("select u.question as q from Answer as u where u.user = 101 and u.question = 101")
//                .setParameter("question",  (long)101)
//                .setParameter("user", (long) 101)
                .getResultList());
         answer1 = answer1.replaceAll("[()<\\[\\]>]","");
         assertThat(answer1).isEqualTo("101");

    }


}

