package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import com.javamentor.qa.platform.models.entity.GroupBookmark;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestProfileUserResourceController extends AbstractClassForDRRiderMockMVCTests {
    // Проверка получения вопросов пользователя по количеству голосов и не бросает ошибку если запрос без параметров
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileQuestionDtoShouldReturnAllQuestionDto/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileQuestionDtoShouldReturnAllQuestionDto/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserProfileQuestionDtoShouldReturnPageQuestionDto() throws Exception {
        mockMvc.perform(get("/api/user/profile/questions")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(2)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(15)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)))
                .andExpect(jsonPath("$.items.[0].questionId", Is.is(101)))
                .andExpect(jsonPath("$.items.[0].title", Is.is("Question 101")))

                .andExpect(jsonPath("$.items.[0].listTagDto.size()", Is.is(1)))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items.[0].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.items.[0].view", Is.is(1)))
                .andExpect(jsonPath("$.items.[0].vote", Is.is(5)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)));

    }

    // Проверка сортировки вопросов пользователя по количеству просмотров
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileQuestionDtoShouldReturnAllQuestionDto/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileQuestionDtoShouldReturnAllQuestionDto/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserProfileQuestionDtoShouldSortPageQuestionDtoByCountOfViews() throws Exception {
        mockMvc.perform(get("/api/user/profile/questions?sort=VIEW")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(2)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(15)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)))
                .andExpect(jsonPath("$.items.[0].questionId", Is.is(100)))
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 100")))

                .andExpect(jsonPath("$.items.[0].listTagDto.size()", Is.is(3)))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].id", Is.is(100)))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].description", Is.is("Description of tag 1")))

                .andExpect(jsonPath("$.items.[0].countAnswer", Is.is(2)))
                .andExpect(jsonPath("$.items.[0].view", Is.is(11)))
                .andExpect(jsonPath("$.items.[0].vote", Is.is(4)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)));

    }

    // Проверка сортировки вопросов пользователя по дате создания
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileQuestionDtoShouldReturnAllQuestionDto/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileQuestionDtoShouldReturnAllQuestionDto/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserProfileQuestionDtoShouldSortPageQuestionDtoByNew() throws Exception {
        mockMvc.perform(get("/api/user/profile/questions?sort=NEW")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(2)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(15)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)))
                .andExpect(jsonPath("$.items.[0].questionId", Is.is(114)))
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 120")))

                .andExpect(jsonPath("$.items.[0].listTagDto.size()", Is.is(1)))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].id", Is.is(102)))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].description", Is.is("Description of tag 3")))

                .andExpect(jsonPath("$.items.[0].countAnswer", Is.is(3)))
                .andExpect(jsonPath("$.items.[0].view", Is.is(2)))
                .andExpect(jsonPath("$.items.[0].vote", Is.is(-2)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)));

    }

    // Проверка получения вопросов пользователя по параметру currentPage
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileQuestionDtoShouldReturnAllQuestionDto/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileQuestionDtoShouldReturnAllQuestionDto/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserProfileQuestionDtoShouldSortPageQuestionDtoByNewByParamCurrentPage() throws Exception {
        mockMvc.perform(get("/api/user/profile/questions?sort=NEW&currentPage=2")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber", Is.is(2)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(2)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(15)))
                .andExpect(jsonPath("$.items.length()", Is.is(5)))
                .andExpect(jsonPath("$.items.[0].questionId", Is.is(103)))
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 103")))

                .andExpect(jsonPath("$.items.[0].listTagDto.size()", Is.is(1)))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items.[0].countAnswer", Is.is(2)))
                .andExpect(jsonPath("$.items.[0].view", Is.is(2)))
                .andExpect(jsonPath("$.items.[0].vote", Is.is(-3)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(5)));

    }

    // Проверка получения вопросов пользователя по параметру items
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileQuestionDtoShouldReturnAllQuestionDto/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileQuestionDtoShouldReturnAllQuestionDto/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserProfileQuestionDtoShouldSortPageQuestionDtoByNewByParamItems() throws Exception {
        mockMvc.perform(get("/api/user/profile/questions?sort=NEW&currentPage=2&items=3")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber", Is.is(2)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(5)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(15)))
                .andExpect(jsonPath("$.items.length()", Is.is(3)))
                .andExpect(jsonPath("$.items.[0].questionId", Is.is(111)))
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 111")))

                .andExpect(jsonPath("$.items.[0].listTagDto.size()", Is.is(1)))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items.[0].countAnswer", Is.is(2)))
                .andExpect(jsonPath("$.items.[0].view", Is.is(2)))
                .andExpect(jsonPath("$.items.[0].vote", Is.is(3)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(3)));

    }

    // Проверка BookMarks
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUserBookMarksDtoShouldWhenExist/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUserBookMarksDtoShouldWhenExist/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserBookMarksDtoShouldWhenExist() throws Exception {
        mockMvc.perform(get("/api/user/profile/bookmarks")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Is.is(2)))
                .andExpect(jsonPath("$[0].bookmarkId", Is.is(1)))
                .andExpect(jsonPath("$.[0].questionId", Is.is(100)))
                .andExpect(jsonPath("$[0].title", Is.is("Question 100")))
                .andExpect(jsonPath("$[0].listTagDto.size()", Is.is(3)))
                .andExpect(jsonPath("$.[0].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.[0].countView", Is.is(4)))
                .andExpect(jsonPath("$.[0].note", Is.is("note 1")));

    }

    // Не должен бросать ошибку если нету закладок
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUserBookMarksDtoShouldWhenExist/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUserBookMarksDtoShouldWhenExist/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserBookMarksDtoShouldWorkWhenNotExist() throws Exception {
        mockMvc.perform(get("/api/user/profile/bookmarks")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Is.is(0)));

    }

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

                .andExpect(jsonPath("$.items[2].answerId", Is.is(105)))
                .andExpect(jsonPath("$.items[2].title", Is.is("Question 103")))

                .andExpect(jsonPath("$.items[2].view", Is.is(2)))
                .andExpect(jsonPath("$.items[2].vote", Is.is(2)))
                .andExpect(jsonPath("$.items[2].questionId", Is.is(103)))
                .andExpect(jsonPath("$.items[2].isHelpful", Is.is(true)));

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
                .andExpect(jsonPath("$.items[0].questionId", Is.is(105)))
                .andExpect(jsonPath("$.items[0].isHelpful", Is.is(false)));

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

                .andExpect(jsonPath("$.items[2].answerId", Is.is(105)))
                .andExpect(jsonPath("$.items[2].title", Is.is("Question 103")))

                .andExpect(jsonPath("$.items[2].view", Is.is(2)))
                .andExpect(jsonPath("$.items[2].vote", Is.is(2)))
                .andExpect(jsonPath("$.items[2].questionId", Is.is(103)))
                .andExpect(jsonPath("$.items[2].isHelpful", Is.is(true)));

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
                .andExpect(jsonPath("$.items[0].questionId", Is.is(102)))
                .andExpect(jsonPath("$.items[0].isHelpful", Is.is(false)));

    }

    // Проверка получения тегов пользователя
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserTagsDtoShouldReturnAllUserProfileTagDto/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserTagsDtoShouldReturnAllUserProfileTagDto/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserTagsDtoShouldReturnAllUserProfileTagDto() throws Exception {
        mockMvc.perform(get("/api/user/profile/tags")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Is.is(4)))
                .andExpect(jsonPath("$[0].id", Is.is(100)))
                .andExpect(jsonPath("$[0].tagName", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$[0].countVoteTag", Is.is(15)))
                .andExpect(jsonPath("$[0].countAnswerQuestion", Is.is(9)));
    }

    // Проверка получения тегов пользователя не должен бросать ошибку если у пользователя нету тегов
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserTagsDtoShouldReturnAllUserProfileTagDto/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserTagsDtoShouldReturnAllUserProfileTagDto/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserTagsDtoShouldNotThrowExceptionIfTagNotExists() throws Exception {
        mockMvc.perform(get("/api/user/profile/tags")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user120@mail.ru", "user120"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Is.is(0)));
    }

    // Проверка получения тегов пользователя если countVoteTag = 0 и countAnswerQuestion = 1
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserTagsDtoShouldReturnAllUserProfileTagDto/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserTagsDtoShouldReturnAllUserProfileTagDto/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserTagsDtoShouldNotThrowExceptionIfTagQuestionNotExists() throws Exception {
        mockMvc.perform(get("/api/user/profile/tags")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user121@mail.ru", "user121"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Is.is(1)))
                .andExpect(jsonPath("$[0].id", Is.is(105)))
                .andExpect(jsonPath("$[0].tagName", Is.is("fbgdsdf8")))
                .andExpect(jsonPath("$[0].countVoteTag", Is.is(0)))
                .andExpect(jsonPath("$[0].countAnswerQuestion", Is.is(1)));
    }

    // Проверка получения тегов пользователя если countAnswerQuestion состоит только из вопросов
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserTagsDtoShouldReturnAllUserProfileTagDto/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserTagsDtoShouldReturnAllUserProfileTagDto/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserTagsDtoShouldNotThrowExceptionIfTagQuestionAnswerNotExists() throws Exception {
        mockMvc.perform(get("/api/user/profile/tags")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user123@mail.ru", "user123"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Is.is(1)))
                .andExpect(jsonPath("$[0].id", Is.is(107)))
                .andExpect(jsonPath("$[0].tagName", Is.is("fbgdsdf10")))
                .andExpect(jsonPath("$[0].countVoteTag", Is.is(0)))
                .andExpect(jsonPath("$[0].countAnswerQuestion", Is.is(2)));
    }

    // Проверка получения тегов пользователя если countVoteTag состоит только из вопросов т.е. пользователь только создает вопросы
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserTagsDtoShouldReturnAllUserProfileTagDto/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserTagsDtoShouldReturnAllUserProfileTagDto/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserTagsDtoShouldWorkIfTagQuestionAnswerNotExists() throws Exception {
        mockMvc.perform(get("/api/user/profile/tags")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user122@mail.ru", "user122"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Is.is(1)))
                .andExpect(jsonPath("$[0].id", Is.is(106)))
                .andExpect(jsonPath("$[0].tagName", Is.is("fbgdsdf9")))
                .andExpect(jsonPath("$[0].countVoteTag", Is.is(-1)))
                .andExpect(jsonPath("$[0].countAnswerQuestion", Is.is(1)));
    }

    //
    //Получение всех вопросов авторизированного пользователя

    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileQuestionDtoById/Before.sql")
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileQuestionDtoById/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllUserProfileQuestionDtoById() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/profile/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization",
                                "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title", Is.is("Question 101")))
                .andExpect(jsonPath("$.[0].questionId", Is.is(101)))
                .andExpect(jsonPath("$.[0].persistDateTime", Is.is("2022-10-06T00:00:00")))
                .andExpect(jsonPath("$.[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.[0].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.[0].listTagDto[0].description", Is.is("Description of tag 1")))
                .andExpect(jsonPath("$.[0].countAnswer", Is.is(2)))

                .andExpect(jsonPath("$.[1].title", Is.is("Question 102")))
                .andExpect(jsonPath("$.[1].questionId", Is.is(102)))
                .andExpect(jsonPath("$.[1].persistDateTime", Is.is("2022-10-06T00:00:00")))
                .andExpect(jsonPath("$.[1].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.[1].listTagDto[0].id", Is.is(104)))
                .andExpect(jsonPath("$.[1].listTagDto[0].name", Is.is("vfOxMU4")))
                .andExpect(jsonPath("$.[1].listTagDto[0].description", Is.is("Description of tag 4")));
        // .andExpect(jsonPath("$.totalResultCount", Is.is(1)));


    }

    //Получение всех удаленных вопросов в виде UserProfileQuestionDto
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileQuestionDtoByUserIdIsDelete/Before.sql")
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileQuestionDtoByUserIdIsDelete/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllUserProfileQuestionDtoByUserIdIsDelete() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/profile/delete/questions")
                        // .get("/delete/questions")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title", Is.is("Question 101")))
                .andExpect(jsonPath("$.[0].questionId", Is.is(101)))
                .andExpect(jsonPath("$.[0].persistDateTime", Is.is("2022-10-06T00:00:00")))
                .andExpect(jsonPath("$.[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.[0].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.[0].listTagDto[0].description", Is.is("Description of tag 1")))
                .andExpect(jsonPath("$.[0].countAnswer", Is.is(2)));


    }


    //Получение всех закладок в профиле пользователя в виде BookMarksDto
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllBookMarksInUserProfile/Before.sql")
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllBookMarksInUserProfile/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllBookMarksInUserProfile() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/profile/bookmarks")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title", Is.is("Question 101")))
                .andExpect(jsonPath("$.[0].questionId", Is.is(101)))
                .andExpect(jsonPath("$.[0].persistDateTime", Is.is("2022-10-06T00:00:00")))
                .andExpect(jsonPath("$.[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.[0].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.[0].listTagDto[0].description", Is.is("Description of tag 1")))
                .andExpect(jsonPath("$.[0].countAnswer", Is.is(2)))
                .andExpect(jsonPath("$.[0].countVote", Is.is(1)))
                .andExpect(jsonPath("$.[0].countView", Is.is(2)));

    }


    //Проверка получения списка имён GroupBookMark
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileGroupBookMarkNames/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileGroupBookMarkNames/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserProfileGroupBookMarkNames() throws Exception {
        mockMvc.perform(get("/api/user/profile/bookmark/group")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Is.is(4)))
                .andExpect(jsonPath("$[0]", Is.is("group_bookmark1")))
                .andExpect(jsonPath("$[1]", Is.is("group_bookmark2")))
                .andExpect(jsonPath("$[2]", Is.is("group_bookmark3")))
                .andExpect(jsonPath("$[3]", Is.is("group_bookmark4")));
    }

    //Проверка получения списка имён GroupBookMark не должен бросать ошибку если нету группы закладок у пользователя
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileGroupBookMarkNames/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileGroupBookMarkNames/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserProfileGroupBookMarkNamesShouldWorkIfGroupBookMarkNotExists() throws Exception {
        mockMvc.perform(get("/api/user/profile/bookmark/group")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Is.is(0)));
    }

    //Проверка создания новой группы закладок
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileGroupBookMarkNames/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileGroupBookMarkNames/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createNewUserProfileGroupBookMark() throws Exception {
        String newGroupBookMarkName = "testGroupBookMark";
        mockMvc.perform(post("/api/user/profile/bookmark/group")
                        .content(newGroupBookMarkName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isCreated());

        List<GroupBookmark> groupBookmarks = entityManager.createQuery("select new com.javamentor.qa.platform.models.entity.GroupBookmark (" +
                        "gb.title," +
                        "gb.user" +
                        ") from GroupBookmark gb where gb.user.id = :id", GroupBookmark.class)
                .setParameter("id", 101L)
                .getResultList();

        Assertions.assertEquals(newGroupBookMarkName, groupBookmarks.get(4).getTitle());
        Assertions.assertEquals(101L, groupBookmarks.get(4).getUser().getId());
    }

    //Проверка создания новой группы закладок должен вернуть ошибку если тело метода пустое
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileGroupBookMarkNames/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileGroupBookMarkNames/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createNewUserProfileGroupBookMarkShouldReturnBadRequestIfBodyIsEmpty() throws Exception {
        mockMvc.perform(post("/api/user/profile/bookmark/group")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("request body (title field) must not be empty")));
    }

    //Проверка создания новой группы закладок
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileGroupBookMarkNames/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileGroupBookMarkNames/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createNewUserProfileGroupBookMarkShouldReturnBadRequestIfGroupBookMarkExists() throws Exception {
        String newGroupBookMarkName = "group_bookmark1";
        mockMvc.perform(post("/api/user/profile/bookmark/group")
                        .content(newGroupBookMarkName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("user already has group bookmark with title group_bookmark1")));
    }

    // Проверка получения репутацуии пользователя и не бросает ошибку если запрос без параметров
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileReputationDtoShouldReturnAllReputationDTO/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileReputationDtoShouldReturnAllReputationDTO/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserProfileReputationDtoShouldReturnAllReputationDTO() throws Exception {
        mockMvc.perform(get("/api/user/profile/reputation")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(2)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(15)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)))
                .andExpect(jsonPath("$.items.[0].countReputation", Is.is(10)))
                .andExpect(jsonPath("$.items.[0].questionTitle", Is.is("Question 106")))

                .andExpect(jsonPath("$.items.[0].questionId", Is.is(106)))
                .andExpect(jsonPath("$.items.[0].answerId", Is.is(18)))
                .andExpect(jsonPath("$.items.[0].reputationType", Is.is("VOTE_UP_CREATE_QUESTION")))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)));

    }
    // Проверка получения репутацуии пользователя при сортировке по дате создания
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileReputationDtoShouldReturnAllReputationDTO/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileReputationDtoShouldReturnAllReputationDTO/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserProfileReputationDtoShouldReturnAllReputationDTOByNew() throws Exception {
        mockMvc.perform(get("/api/user/profile/reputation?sort=NEW")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(2)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(15)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)))
                .andExpect(jsonPath("$.items.[0].countReputation", Is.is(10)))
                .andExpect(jsonPath("$.items.[0].questionTitle", Is.is("Question 106")))

                .andExpect(jsonPath("$.items.[0].questionId", Is.is(106)))
                .andExpect(jsonPath("$.items.[0].answerId", Is.is(18)))
                .andExpect(jsonPath("$.items.[0].reputationType", Is.is("VOTE_UP_CREATE_QUESTION")))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)));

    }
    // Проверка получения репутацуии пользователя пользователя по параметру currentPage
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileReputationDtoShouldReturnAllReputationDTO/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileReputationDtoShouldReturnAllReputationDTO/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserProfileReputationDtoShouldReturnAllReputationDTOByNewByParamCurrentPage() throws Exception {
        mockMvc.perform(get("/api/user/profile/reputation?sort=NEW&currentPage=2")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(2)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(15)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)))
                .andExpect(jsonPath("$.items.[0].countReputation", Is.is(10)))
                .andExpect(jsonPath("$.items.[0].questionTitle", Is.is("Question 106")))

                .andExpect(jsonPath("$.items.[0].questionId", Is.is(106)))
                .andExpect(jsonPath("$.items.[0].answerId", Is.is(18)))
                .andExpect(jsonPath("$.items.[0].reputationType", Is.is("VOTE_UP_CREATE_QUESTION")))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(10)));

    }
    // Проверка получения репутацуии пользователя пользователя по параметру items
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileReputationDtoShouldReturnAllReputationDTO/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getUserProfileReputationDtoShouldReturnAllReputationDTO/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserProfileReputationDtoShouldReturnAllReputationDTOByNewByParamItems() throws Exception {
        mockMvc.perform(get("/api/user/profile/reputation?sort=NEW&currentPage=2&items=3")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(5)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(15)))
                .andExpect(jsonPath("$.items.length()", Is.is(3)))
                .andExpect(jsonPath("$.items.[0].countReputation", Is.is(-5)))
                .andExpect(jsonPath("$.items.[0].questionTitle", Is.is("Question 109")))

                .andExpect(jsonPath("$.items.[0].questionId", Is.is(109)))
                .andExpect(jsonPath("$.items.[0].answerId", Is.is(18)))
                .andExpect(jsonPath("$.items.[0].reputationType", Is.is("VOTE_DOWN_ANSWER")))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(3)));

    }
}