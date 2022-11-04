package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    // Проверка передачи всех верных данных
    @Test
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldFindAllData_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldFindAllData_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAnswerCommentById_shouldFindAllData_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/comment/answer/{answerId}", 100)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(2)))

                .andExpect(jsonPath("$.items[0].id", Is.is(100)))
                .andExpect(jsonPath("$.items[0].answerId", Is.is(100)))
                .andExpect(jsonPath("$.items[0].lastRedactionDate", isA(String.class)))
                .andExpect(jsonPath("$.items[0].persistDate", isA(String.class)))
                .andExpect(jsonPath("$.items[0].text", Is.is("Comment 100")))
                .andExpect(jsonPath("$.items[0].userId", Is.is(100)))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(2700)))

                .andExpect(jsonPath("$.items[1].id", Is.is(101)))
                .andExpect(jsonPath("$.items[1].answerId", Is.is(100)))
                .andExpect(jsonPath("$.items[1].lastRedactionDate", isA(String.class)))
                .andExpect(jsonPath("$.items[1].persistDate", isA(String.class)))
                .andExpect(jsonPath("$.items[1].text", Is.is("Comment 101")))
                .andExpect(jsonPath("$.items[1].userId", Is.is(101)))
                .andExpect(jsonPath("$.items[1].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[1].reputation", Is.is(2700)));
    }

    // Пользователь авторизован как admin
    @Test
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldIsForbidden_whenAdmin/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldIsForbidden_whenAdmin/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAnswerCommentById_shouldIsForbidden_whenAdmin() throws Exception {
        mockMvc.perform(get("/api/user/comment/answer/{answerId}", 100)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    // Пользователь не указывает id ответа
    @Test
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldIsNotFound_whenEmptyId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldIsNotFound_whenEmptyId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAnswerCommentById_shouldIsNotFound_whenEmptyId() throws Exception {
        mockMvc.perform(get("/api/user/comment/answer/{answerId}", "")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // Пользователь указывает не существующий id ответа
    @Test
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldEmpty_whenNotExistsId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldEmpty_whenNotExistsId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAnswerCommentById_shouldEmpty_whenNotExistsId() throws Exception {
        mockMvc.perform(get("/api/user/comment/answer/{answerId}", 110)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(0)))
                .andExpect(jsonPath("$.items.length()", Is.is(0)));
    }

    // Пользователь указывает id ответа равный 0
    @Test
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldEmpty_whenZeroId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldEmpty_whenZeroId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAnswerCommentById_shouldEmpty_whenZeroId() throws Exception {
        mockMvc.perform(get("/api/user/comment/answer/{answerId}", 0)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(0)))
                .andExpect(jsonPath("$.items.length()", Is.is(0)));
    }

    // Пользователь указывает отрицательный id ответа
    @Test
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldEmpty_whenNegativeNumberId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldEmpty_whenNegativeNumberId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAnswerCommentById_shouldEmpty_whenNegativeNumberId() throws Exception {
        mockMvc.perform(get("/api/user/comment/answer/{answerId}", -100)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(0)))
                .andExpect(jsonPath("$.items.length()", Is.is(0)));
    }

    // Проверка пагинации - верные данные (id ответа существует, currentPage положительный, items положительный)
    @Test
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldFindAllPaginationData_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldFindAllPaginationData_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAnswerCommentById_shouldFindAllPaginationData_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/comment/answer/{answerId}", 100)
                        .param("currentPage", "2")
                        .param("items", "3")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(10)))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(2)))
                .andExpect(jsonPath("$.items.length()", Is.is(3)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].answerId", Is.is(100)))
                .andExpect(jsonPath("$.items[0].lastRedactionDate", isA(String.class)))
                .andExpect(jsonPath("$.items[0].persistDate", isA(String.class)))
                .andExpect(jsonPath("$.items[0].text", Is.is("Comment 103")))
                .andExpect(jsonPath("$.items[0].userId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(2700)))

                .andExpect(jsonPath("$.items[1].id", Is.is(104)))
                .andExpect(jsonPath("$.items[1].answerId", Is.is(100)))
                .andExpect(jsonPath("$.items[1].lastRedactionDate", isA(String.class)))
                .andExpect(jsonPath("$.items[1].persistDate", isA(String.class)))
                .andExpect(jsonPath("$.items[1].text", Is.is("Comment 104")))
                .andExpect(jsonPath("$.items[1].userId", Is.is(100)))
                .andExpect(jsonPath("$.items[1].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[1].reputation", Is.is(2700)))

                .andExpect(jsonPath("$.items[2].id", Is.is(105)))
                .andExpect(jsonPath("$.items[2].answerId", Is.is(100)))
                .andExpect(jsonPath("$.items[2].lastRedactionDate", isA(String.class)))
                .andExpect(jsonPath("$.items[2].persistDate", isA(String.class)))
                .andExpect(jsonPath("$.items[2].text", Is.is("Comment 105")))
                .andExpect(jsonPath("$.items[2].userId", Is.is(101)))
                .andExpect(jsonPath("$.items[2].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[2].reputation", Is.is(2700)));
    }

    // Проверка пагинации - не верные данные (id ответа не переданы, currentPage положительный, items положительный)
    @Test
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldPaginationIsNotFound_whenEmptyId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldPaginationIsNotFound_whenEmptyId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAnswerCommentById_shouldPaginationIsNotFound_whenEmptyId() throws Exception {
        mockMvc.perform(get("/api/user/comment/answer/{answerId}", "")
                        .param("currentPage", "2")
                        .param("items", "3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // Проверка пагинации - не верные данные (id ответа не существует, currentPage положительный, items положительный)
    @Test
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldPaginationEmpty_whenNotExistsId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldPaginationEmpty_whenNotExistsId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAnswerCommentById_shouldPaginationEmpty_whenNotExistsId() throws Exception {
        mockMvc.perform(get("/api/user/comment/answer/{answerId}", 110)
                        .param("currentPage", "2")
                        .param("items", "3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(0)))
                .andExpect(jsonPath("$.items.length()", Is.is(0)));
    }

    // Проверка пагинации - не верные данные (id ответа равен 0, currentPage положительный, items положительный)
    @Test
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldPaginationEmpty_whenZeroId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldPaginationEmpty_whenZeroId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAnswerCommentById_shouldPaginationEmpty_whenZeroId() throws Exception {
        mockMvc.perform(get("/api/user/comment/answer/{answerId}", 0)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(0)))
                .andExpect(jsonPath("$.items.length()", Is.is(0)));
    }

    // Проверка пагинации - не верные данные (id ответа отрицательный, currentPage положительный, items положительный)
    @Test
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldPaginationEmpty_whenNegativeNumberId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldPaginationEmpty_whenNegativeNumberId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAnswerCommentById_shouldPaginationEmpty_whenNegativeNumberId() throws Exception {
        mockMvc.perform(get("/api/user/comment/answer/{answerId}", -100)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(0)))
                .andExpect(jsonPath("$.items.length()", Is.is(0)));
    }

    // Проверка пагинации - сортировка по дате создания.
    @Test
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldPaginationOrderByPersistDate_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldPaginationOrderByPersistDate_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAnswerCommentById_shouldPaginationOrderByPersistDate_whenExists() throws Exception {
        mockMvc.perform(get("/api/user/comment/answer/{answerId}", 100)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(5)))
                .andExpect(jsonPath("$.items[0].persistDate", Is.is("2022-06-02T16:34:00")))
                .andExpect(jsonPath("$.items[1].persistDate", Is.is("2022-09-02T16:34:00")))
                .andExpect(jsonPath("$.items[2].persistDate", Is.is("2022-11-02T16:34:00")))
                .andExpect(jsonPath("$.items[3].persistDate", Is.is("2022-11-06T16:34:00")))
                .andExpect(jsonPath("$.items[4].persistDate", Is.is("2022-11-06T17:34:00")));
    }
}