package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestProfileUserResourceController extends AbstractClassForDRRiderMockMVCTests {
    // Проверка получения ответов пользователя по количеству голосов и не бросает ошибку если запрос без параметров
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUserProfileAnswerDtoShouldReturnAllQuestionDto/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUserProfileAnswerDtoShouldReturnAllQuestionDto/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserProfileAnswerDtoShouldReturnAnswerDto() throws Exception {
        mockMvc.perform(get("/api/user/profile/answers")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Is.is(5)))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(3)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(21)))
                .andExpect(jsonPath("$.items.size()", Is.is(10)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)))

                .andExpect(jsonPath("$.items[0].answerId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 101")))

                .andExpect(jsonPath("$.items[0].view", Is.is(1)))
                .andExpect(jsonPath("$.items[0].vote", Is.is(6)))
                .andExpect(jsonPath("$.items[0].questionId", Is.is(101)));

    }

    // Проверка сортировки ответов пользователя по дате создания
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUserProfileAnswerDtoShouldReturnAllQuestionDto/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUserProfileAnswerDtoShouldReturnAllQuestionDto/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserProfileAnswerDtoShouldSortAnswerDtoSortByNew() throws Exception {
        mockMvc.perform(get("/api/user/profile/answers?sort=NEW")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Is.is(5)))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(3)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(21)))
                .andExpect(jsonPath("$.items.size()", Is.is(10)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)))

                .andExpect(jsonPath("$.items[0].answerId", Is.is(104)))
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 105")))

                .andExpect(jsonPath("$.items[0].view", Is.is(1)))
                .andExpect(jsonPath("$.items[0].vote", Is.is(-5)))
                .andExpect(jsonPath("$.items[0].questionId", Is.is(105)));

    }


    // Проверка получения ответов пользователя по параметру items = 5
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUserProfileAnswerDtoShouldReturnAllQuestionDto/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUserProfileAnswerDtoShouldReturnAllQuestionDto/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserProfileAnswerDtoShouldReturnAnswerPageDto() throws Exception {
        mockMvc.perform(get("/api/user/profile/answers?items=5")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Is.is(5)))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(5)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(21)))
                .andExpect(jsonPath("$.items.size()", Is.is(5)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(5)))

                .andExpect(jsonPath("$.items[0].answerId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 101")))

                .andExpect(jsonPath("$.items[0].view", Is.is(1)))
                .andExpect(jsonPath("$.items[0].vote", Is.is(6)))
                .andExpect(jsonPath("$.items[0].questionId", Is.is(101)));

    }


    // Проверка получения ответов пользователя по параметру page = 2
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUserProfileAnswerDtoShouldReturnAllQuestionDto/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUserProfileAnswerDtoShouldReturnAllQuestionDto/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserProfileAnswerDtoShouldSortAnswerDtoSortByNewAndPage() throws Exception {
        mockMvc.perform(get("/api/user/profile/answers?sort=NEW&page=2")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Is.is(5)))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(2)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(3)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(21)))
                .andExpect(jsonPath("$.items.size()", Is.is(10)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)))

                .andExpect(jsonPath("$.items[0].answerId", Is.is(113)))
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 102")))

                .andExpect(jsonPath("$.items[0].view", Is.is(2)))
                .andExpect(jsonPath("$.items[0].vote", Is.is(-1)))
                .andExpect(jsonPath("$.items[0].questionId", Is.is(102)));

    }
}
