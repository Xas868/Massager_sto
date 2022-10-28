package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestCommentResourceController extends AbstractClassForDRRiderMockMVCTests {

    @Autowired
    private MockMvc mockMvc;

    // Все хорошо комментарий к вопросу создан
    @Test
    @Sql("/script/Before.sql")
    @Sql(scripts = "/script/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldAddCommentQuestion() throws Exception {
        mockMvc.perform(post("/api/user/comment/question/{questionId}", 102)
                        .content("text comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // Пользователь передает неверный questionId
    @Test
    @Sql("/script/Before.sql")
    @Sql(scripts = "/script/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldError400UserSendInvalidQuestionId() throws Exception {
        mockMvc.perform(post("/api/user/comment/question/{questionId}", 103)
                        .content("text comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // Пользователь пытается передать пустой комментарий
    @Test
    @Sql("/script/Before.sql")
    @Sql(scripts = "/script/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldUserSendEmptyComment() throws Exception {
        mockMvc.perform(post("/api/user/comment/question/{questionId}", 102)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}