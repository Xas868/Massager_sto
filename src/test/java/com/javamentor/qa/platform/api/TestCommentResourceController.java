package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
}