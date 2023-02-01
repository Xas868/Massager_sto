package com.javamentor.qa.platform.api;


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

import java.util.List;

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






//        String persistDateTimeVar2 = "dqwdqwd";
        String persistDateTimeVar = String.valueOf(entityManager.createQuery("select persistDateTime from Answer where id = 1")
                .getResultList());
        Boolean answer1 = entityManager.createQuery("select count(ans)>0 from Answer as ans join ans.user u on ans.user = u.id where u.id=101 and ans.question =101", Boolean.class)
                .getSingleResult();
        assertThat(answer1).isEqualTo(true);


    }


}


import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class TestAnswerResourceController extends AbstractClassForDRRiderMockMVCTests {


    // Получение всех "ответов" по id вопроса
    @Test
    @Sql("/script/TestAnswerResourceController/getAnswers/Before.sql")
    @Sql(scripts = "/script/TestAnswerResourceController/getAnswers/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAnswers() throws Exception {
        mockMvc.perform(get("/api/user/question/{questionId}/answer", 100)
                .content("")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100")))

                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.size()").value(3))

                .andExpect(jsonPath("$.[0].id").value(100))
                .andExpect(jsonPath("$.[0].userId").value(100))
                .andExpect(jsonPath("$.[0].userReputation").value(1000))
                .andExpect(jsonPath("$.[0].questionId").value(100))
                .andExpect(jsonPath("$.[0].htmlBody").value("Answer 100"))
                .andExpect(jsonPath("$.[0].persistDateTime").value("2022-12-02T10:49:13.515771"))
                .andExpect(jsonPath("$.[0].isHelpful").value(false))
                .andExpect(jsonPath("$.[0].isUserVote").value(true))
                .andExpect(jsonPath("$.[0].dateAccept").value("2022-12-02T10:49:13.515771"))
                .andExpect(jsonPath("$.[0].countValuable").value(1))
                .andExpect(jsonPath("$.[0].image").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.[0].nickName").value("user_100"))

                .andExpect(jsonPath("$.[0].commentOnTheAnswerToTheQuestion.size()").value(1))
                .andExpect(jsonPath("$.[0].commentOnTheAnswerToTheQuestion.[0].id").value(100))
                .andExpect(jsonPath("$.[0].commentOnTheAnswerToTheQuestion.[0].answerId").value(100))
                .andExpect(jsonPath("$.[0].commentOnTheAnswerToTheQuestion.[0].lastRedactionDate").value("2023-01-26T00:00:00"))
                .andExpect(jsonPath("$.[0].commentOnTheAnswerToTheQuestion.[0].persistDate").value("2023-01-26T00:00:00"))
                .andExpect(jsonPath("$.[0].commentOnTheAnswerToTheQuestion.[0].text").value("Comment 100"))
                .andExpect(jsonPath("$.[0].commentOnTheAnswerToTheQuestion.[0].userId").value(100))
                .andExpect(jsonPath("$.[0].commentOnTheAnswerToTheQuestion.[0].imageLink").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.[0].commentOnTheAnswerToTheQuestion.[0].reputation").value(1000))


                .andExpect(jsonPath("$.[1].id").value(101))
                .andExpect(jsonPath("$.[1].userId").value(101))
                .andExpect(jsonPath("$.[1].userReputation").value(2000))
                .andExpect(jsonPath("$.[1].questionId").value(100))
                .andExpect(jsonPath("$.[1].htmlBody").value("Answer 101"))
                .andExpect(jsonPath("$.[1].persistDateTime").value("2022-12-03T10:49:13.515771"))
                .andExpect(jsonPath("$.[1].isHelpful").value(false))
                .andExpect(jsonPath("$.[1].isUserVote").value(true))
                .andExpect(jsonPath("$.[1].dateAccept").value("2022-12-03T10:49:13.515771"))
                .andExpect(jsonPath("$.[1].countValuable").value(-1))
                .andExpect(jsonPath("$.[1].image").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.[1].nickName").value("user_101"))

                .andExpect(jsonPath("$.[1].commentOnTheAnswerToTheQuestion.size()").value(1))
                .andExpect(jsonPath("$.[1].commentOnTheAnswerToTheQuestion.[0].id").value(101))
                .andExpect(jsonPath("$.[1].commentOnTheAnswerToTheQuestion.[0].answerId").value(101))
                .andExpect(jsonPath("$.[1].commentOnTheAnswerToTheQuestion.[0].lastRedactionDate").value("2023-01-26T00:00:00"))
                .andExpect(jsonPath("$.[1].commentOnTheAnswerToTheQuestion.[0].persistDate").value("2023-01-26T00:00:00"))
                .andExpect(jsonPath("$.[1].commentOnTheAnswerToTheQuestion.[0].text").value("Comment 101"))
                .andExpect(jsonPath("$.[1].commentOnTheAnswerToTheQuestion.[0].userId").value(101))
                .andExpect(jsonPath("$.[1].commentOnTheAnswerToTheQuestion.[0].imageLink").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.[1].commentOnTheAnswerToTheQuestion.[0].reputation").value(2000))


                .andExpect(jsonPath("$.[2].id").value(102))
                .andExpect(jsonPath("$.[2].userId").value(102))
                .andExpect(jsonPath("$.[2].userReputation").value(4000))
                .andExpect(jsonPath("$.[2].questionId").value(100))
                .andExpect(jsonPath("$.[2].htmlBody").value("Answer 102"))
                .andExpect(jsonPath("$.[2].persistDateTime").value("2022-12-04T10:49:13.515771"))
                .andExpect(jsonPath("$.[2].isHelpful").value(false))
                .andExpect(jsonPath("$.[2].isUserVote").value(true))
                .andExpect(jsonPath("$.[2].dateAccept").value("2022-12-04T10:49:13.515771"))
                .andExpect(jsonPath("$.[2].countValuable").value(-2))
                .andExpect(jsonPath("$.[2].image").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.[2].nickName").value("user_102"))

                .andExpect(jsonPath("$.[2].commentOnTheAnswerToTheQuestion.size()").value(1))
                .andExpect(jsonPath("$.[2].commentOnTheAnswerToTheQuestion.[0].id").value(102))
                .andExpect(jsonPath("$.[2].commentOnTheAnswerToTheQuestion.[0].answerId").value(102))
                .andExpect(jsonPath("$.[2].commentOnTheAnswerToTheQuestion.[0].lastRedactionDate").value("2023-01-26T00:00:00"))
                .andExpect(jsonPath("$.[2].commentOnTheAnswerToTheQuestion.[0].persistDate").value("2023-01-26T00:00:00"))
                .andExpect(jsonPath("$.[2].commentOnTheAnswerToTheQuestion.[0].text").value("Comment 102"))
                .andExpect(jsonPath("$.[2].commentOnTheAnswerToTheQuestion.[0].userId").value(102))
                .andExpect(jsonPath("$.[2].commentOnTheAnswerToTheQuestion.[0].imageLink").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.[2].commentOnTheAnswerToTheQuestion.[0].reputation").value(4000));

    }

    // Удаление ответа
    @Test
    @Sql("/script/TestAnswerResourceController/deleteAnswer/Before.sql")
    @Sql(scripts = "/script/TestAnswerResourceController/deleteAnswer/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAnswer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/question/{questionId}/answer/{id}/delete", 100, 100)
                        .content("100")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk());
    }

}