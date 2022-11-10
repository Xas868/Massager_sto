package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestCommentResourceController extends AbstractClassForDRRiderMockMVCTests {

    // Все хорошо комментарий к вопросу создан
    @Test
    @Sql("/script/TestCommentResourceController/shouldAddCommentQuestion/Before.sql")
    @Sql(scripts = "/script/TestCommentResourceController/shouldAddCommentQuestion/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldAddCommentQuestion() throws Exception {
        mockMvc.perform(post("/api/user/comment/question/{questionId}", 102)
                        .content("text comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(entityManager.createQuery("from Comment c where c.id = :id")
                .setParameter("id", (long) 1)
                .getResultList()
                .isEmpty())
                .isEqualTo(false);
    }

    // Пользователь передает неверный questionId
    @Test
    @Sql("/script/TestCommentResourceController/shouldError400UserSendInvalidQuestionId/Before.sql")
    @Sql(scripts = "/script/TestCommentResourceController/shouldError400UserSendInvalidQuestionId/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldError400UserSendInvalidQuestionId() throws Exception {
        mockMvc.perform(post("/api/user/comment/question/{questionId}", 150)
                        .content("text comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user103@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // Пользователь пытается передать пустой комментарий
    @Test
    @Sql("/script/TestCommentResourceController/shouldUserSendEmptyComment/Before.sql")
    @Sql(scripts = "/script/TestCommentResourceController/shouldUserSendEmptyComment/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldUserSendEmptyComment() throws Exception {
        mockMvc.perform(post("/api/user/comment/question/{questionId}", 102)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user104@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    //идеальный вариант, корректный id question, без параметров
    @Test
    @Sql("script/testCommentResourceController/shouldGetAllCommentsOfQuestion/Before.sql")
    @Sql(scripts = "script/testCommentResourceController/shouldGetAllCommentsOfQuestion/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetAllCommentsOfQuestion() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/comment/question/{questionId}", 100)
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.size()").value(3))
                .andExpect(jsonPath("$.items[0].id").value(104))
                .andExpect(jsonPath("$.items[0].persistDate").value("2022-10-05T00:00:00"))
                .andExpect(jsonPath("$.items[0].text").value("Comment 104"))
                .andExpect(jsonPath("$.items[0].userId").value(100))
                .andExpect(jsonPath("$.items[0].reputation").value(15300))
                .andExpect(jsonPath("$.items[1].id").value(100))
                .andExpect(jsonPath("$.items[1].persistDate").value("2022-10-03T00:00:00"))
                .andExpect(jsonPath("$.items[1].text").value("Comment 100"))
                .andExpect(jsonPath("$.items[1].userId").value(100))
                .andExpect(jsonPath("$.items[1].reputation").value(15300))
                .andExpect(jsonPath("$.items[2].id").value(102))
                .andExpect(jsonPath("$.items[2].persistDate").value("2022-10-01T00:00:00"))
                .andExpect(jsonPath("$.items[2].text").value("Comment 102"))
                .andExpect(jsonPath("$.items[2].userId").value(100))
                .andExpect(jsonPath("$.items[2].reputation").value(15300));
    }

    //вариант, когда в таблице репутации нет упоминания о нужном вопросе
    @Test
    @Sql("script/testCommentResourceController/shouldGetAllCommentsOfQuestionWithoutReputation/Before.sql")
    @Sql(scripts = "script/testCommentResourceController/shouldGetAllCommentsOfQuestionWithoutReputation/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetAllCommentsOfQuestionWithoutReputation() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/comment/question/{questionId}", 100)
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.size()").value(3))
                .andExpect(jsonPath("$.items[0].id").value(104))
                .andExpect(jsonPath("$.items[0].persistDate").value("2022-10-05T00:00:00"))
                .andExpect(jsonPath("$.items[0].text").value("Comment 104"))
                .andExpect(jsonPath("$.items[0].userId").value(100))
                .andExpect(jsonPath("$.items[0].reputation").value(0))
                .andExpect(jsonPath("$.items[1].id").value(100))
                .andExpect(jsonPath("$.items[1].persistDate").value("2022-10-03T00:00:00"))
                .andExpect(jsonPath("$.items[1].text").value("Comment 100"))
                .andExpect(jsonPath("$.items[1].userId").value(100))
                .andExpect(jsonPath("$.items[1].reputation").value(0))
                .andExpect(jsonPath("$.items[2].id").value(102))
                .andExpect(jsonPath("$.items[2].persistDate").value("2022-10-01T00:00:00"))
                .andExpect(jsonPath("$.items[2].text").value("Comment 102"))
                .andExpect(jsonPath("$.items[2].userId").value(100))
                .andExpect(jsonPath("$.items[2].reputation").value(0));
    }

    //корректный id question, items=2 (выводится 2 объекта вместо 3х)
    @Test
    @Sql("script/testCommentResourceController/shouldGetAllCommentsOfQuestionWithVariables/Before.sql")
    @Sql(scripts = "script/testCommentResourceController/shouldGetAllCommentsOfQuestionWithVariables/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetAllCommentsOfQuestionWithVariables() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/comment/question/{questionId}?currentPage=1&items=2", 100)
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.size()").value(2))
                .andExpect(jsonPath("$.items[0].id").value(104))
                .andExpect(jsonPath("$.items[0].persistDate").value("2022-10-05T00:00:00"))
                .andExpect(jsonPath("$.items[0].text").value("Comment 104"))
                .andExpect(jsonPath("$.items[0].userId").value(100))
                .andExpect(jsonPath("$.items[0].reputation").value(15300))
                .andExpect(jsonPath("$.items[1].id").value(100))
                .andExpect(jsonPath("$.items[1].persistDate").value("2022-10-03T00:00:00"))
                .andExpect(jsonPath("$.items[1].text").value("Comment 100"))
                .andExpect(jsonPath("$.items[1].userId").value(100))
                .andExpect(jsonPath("$.items[1].reputation").value(15300));
    }

    //некорректный id question, ничего не находится
    @Test
    @Sql("script/testCommentResourceController/shouldGetZeroCommentsFromWrongQuestionId/Before.sql")
    @Sql(scripts = "script/testCommentResourceController/shouldGetZeroCommentsFromWrongQuestionId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetZeroCommentsFromWrongQuestionId() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/comment/question/{questionId}", 105)
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isEmpty());
    }

}