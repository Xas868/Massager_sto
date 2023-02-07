package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


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
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 101")))
                .andExpect(jsonPath("$.items[0].questionId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].persistDateTime", Is.is("2022-10-06T00:00:00")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 1")))
                .andExpect(jsonPath("$.items[0].countAnswer", Is.is(2)))

                .andExpect(jsonPath("$.items[1].title", Is.is("Question 102")))
                .andExpect(jsonPath("$.items[1].questionId", Is.is(102)))
                .andExpect(jsonPath("$.items[1].persistDateTime", Is.is("2022-10-06T00:00:00")))
                .andExpect(jsonPath("$.items[1].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.items[1].listTagDto[0].id", Is.is(104)))
                .andExpect(jsonPath("$.items[1].listTagDto[0].name", Is.is("vfOxMU4")))
                .andExpect(jsonPath("$.items[1].listTagDto[0].description", Is.is("Description of tag 4")));
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


    //Пользователь оставил 3 ответа за неделю
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getAnswersPerWeekByUserId_shouldFindAllData_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getAnswersPerWeekByUserId_shouldFindAllData_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAnswersPerWeekByUserId_shouldFindAllData_whenExists() throws Exception {
        mockMvc.perform(get("/api/user/profile/question/week")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(3)));
    }

    //Пользователь оставил 0 ответов за неделю
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getAnswersPerWeekByUserId_shouldFindAllData_WhenEmpty/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getAnswersPerWeekByUserId_shouldFindAllData_WhenEmpty/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAnswersPerWeekByUserId_shouldFindAllData_whenEmpty() throws Exception {
        mockMvc.perform(get("/api/user/profile/question/week")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(0)));
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
                .andExpect(jsonPath("$.items.[0].reputationType", Is.is("VOTE_UP_QUESTION")))
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
                .andExpect(jsonPath("$.items.[0].reputationType", Is.is("VOTE_UP_QUESTION")))
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
                .andExpect(jsonPath("$.items.[0].reputationType", Is.is("VOTE_UP_QUESTION")))
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


    // Пользователь добавил в закладки 3 вопроса, questionId [101, 102, 103]
    @Test
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllBookMarksInUserProfile_ShouldFindAllData_WhenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllBookMarksInUserProfile_ShouldFindAllData_WhenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllBookMarksInUserProfile_ShouldFindAllData_WhenExists() throws Exception {
        mockMvc.perform(get("/api/user/profile/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()", Is.is(3)))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))

                .andExpect(jsonPath("$.items[0].bookmarkId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].groupBookmarkTitle", Is.is("group 101")))
                .andExpect(jsonPath("$.items[0].questionId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 101")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 1")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 2")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 3")))
                .andExpect(jsonPath("$.items[0].countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.items[0].countView", Is.is(0)))
                .andExpect(jsonPath("$.items[0].note", Is.is("title 101")))

                .andExpect(jsonPath("$.items[1].bookmarkId", Is.is(102)))
                .andExpect(jsonPath("$.items[1].groupBookmarkTitle", Is.is("group 101")))
                .andExpect(jsonPath("$.items[1].questionId", Is.is(102)))
                .andExpect(jsonPath("$.items[1].title", Is.is("Question 102")))
                .andExpect(jsonPath("$.items[1].listTagDto[0].id", Is.is(104)))
                .andExpect(jsonPath("$.items[1].listTagDto[0].name", Is.is("vfOxMU4")))
                .andExpect(jsonPath("$.items[1].listTagDto[0].description", Is.is("Description of tag 4")))
                .andExpect(jsonPath("$.items[1].listTagDto[1].id", Is.is(105)))
                .andExpect(jsonPath("$.items[1].listTagDto[1].name", Is.is("iThKcj5")))
                .andExpect(jsonPath("$.items[1].listTagDto[1].description", Is.is("Description of tag 5")))
                .andExpect(jsonPath("$.items[1].listTagDto[2].id", Is.is(106)))
                .andExpect(jsonPath("$.items[1].listTagDto[2].name", Is.is("LTGDJP6")))
                .andExpect(jsonPath("$.items[1].listTagDto[2].description", Is.is("Description of tag 6")))
                .andExpect(jsonPath("$.items[1].countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.items[1].countView", Is.is(0)))
                .andExpect(jsonPath("$.items[1].note", Is.is("title 102")))

                .andExpect(jsonPath("$.items[2].bookmarkId", Is.is(103)))
                .andExpect(jsonPath("$.items[2].groupBookmarkTitle", Is.is("group 101")))
                .andExpect(jsonPath("$.items[2].questionId", Is.is(103)))
                .andExpect(jsonPath("$.items[2].title", Is.is("Question 103")))
                .andExpect(jsonPath("$.items[2].listTagDto[0].id", Is.is(107)))
                .andExpect(jsonPath("$.items[2].listTagDto[0].name", Is.is("vfOxMU7")))
                .andExpect(jsonPath("$.items[2].listTagDto[0].description", Is.is("Description of tag 7")))
                .andExpect(jsonPath("$.items[2].listTagDto[1].id", Is.is(108)))
                .andExpect(jsonPath("$.items[2].listTagDto[1].name", Is.is("iThKcj8")))
                .andExpect(jsonPath("$.items[2].listTagDto[1].description", Is.is("Description of tag 8")))
                .andExpect(jsonPath("$.items[2].listTagDto[2].id", Is.is(109)))
                .andExpect(jsonPath("$.items[2].listTagDto[2].name", Is.is("LTGDJP9")))
                .andExpect(jsonPath("$.items[2].listTagDto[2].description", Is.is("Description of tag 9")))
                .andExpect(jsonPath("$.items[2].countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.items[2].countView", Is.is(0)))
                .andExpect(jsonPath("$.items[2].note", Is.is("title 103")));

    }

    // Пользователь добавил в закладки 0 вопросов, JSON Body = []
    @Test
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllBookMarksInUserProfile_ShouldFindAllData_WhenEmpty/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllBookMarksInUserProfile_ShouldFindAllData_WhenEmpty/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllBookMarksInUserProfile_ShouldFindAllData_WhenEmpty() throws Exception {
        mockMvc.perform(get("/api/user/profile/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()", Is.is(0)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(0)));

    }

    //Проверка сортировки по дате создания вопроса с параметром sortBookmark=NEW
    @Test
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllBookMarksInUserProfile_sortBy_persistDate_NEW/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllBookMarksInUserProfile_sortBy_persistDate_NEW/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllBookMarksInUserProfile_sortBy_persistDate_NEW() throws Exception {
        mockMvc.perform(get("/api/user/profile/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.items[0].bookmarkId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].questionId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].persistDateTime", Is.is("2022-12-28T00:00:00")))

                .andExpect(jsonPath("$.items[1].bookmarkId", Is.is(102)))
                .andExpect(jsonPath("$.items[1].questionId", Is.is(102)))
                .andExpect(jsonPath("$.items[1].persistDateTime", Is.is("2022-12-27T00:00:00")))

                .andExpect(jsonPath("$.items[2].bookmarkId", Is.is(103)))
                .andExpect(jsonPath("$.items[2].questionId", Is.is(103)))
                .andExpect(jsonPath("$.items[2].persistDateTime", Is.is("2022-12-26T00:00:00")));
    }


    //Проверка сортировки с параметром sortBookmark=VIEW - по количеству просмотров
    @Test
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllBookMarksInUserProfile_sortBy_QuestionViewed/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllBookMarksInUserProfile_sortBy_QuestionViewed/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllBookMarksInUserProfile_sortBy_QuestionViewed() throws Exception {
        mockMvc.perform(get("/api/user/profile/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("sortBookmark", "VIEW")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()", Is.is(8)))

                .andExpect(jsonPath("$.items[0].bookmarkId", Is.is(108)))
                .andExpect(jsonPath("$.items[0].questionId", Is.is(108)))
                .andExpect(jsonPath("$.items[0].countView", Is.is(4)))

                .andExpect(jsonPath("$.items[1].bookmarkId", Is.is(103)))
                .andExpect(jsonPath("$.items[1].questionId", Is.is(103)))
                .andExpect(jsonPath("$.items[1].countView", Is.is(3)))

                .andExpect(jsonPath("$.items[2].bookmarkId", Is.is(102)))
                .andExpect(jsonPath("$.items[2].questionId", Is.is(102)))
                .andExpect(jsonPath("$.items[2].countView", Is.is(1)));
    }


    //Проверка сортировки с параметром sortBookmark=VOTE - по количеству голосов
    @Test
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllBookMarksInUserProfile_sortBy_QuestionVoted/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllBookMarksInUserProfile_sortBy_QuestionVoted/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllBookMarksInUserProfile_sortBy_QuestionVoted() throws Exception {
        mockMvc.perform(get("/api/user/profile/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("sortBookmark", "VOTE")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()", Is.is(8)))

                .andExpect(jsonPath("$.items[0].bookmarkId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].questionId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].countVote", Is.is(4)))

                .andExpect(jsonPath("$.items[1].bookmarkId", Is.is(102)))
                .andExpect(jsonPath("$.items[1].questionId", Is.is(102)))
                .andExpect(jsonPath("$.items[1].countVote", Is.is(3)))

                .andExpect(jsonPath("$.items[2].bookmarkId", Is.is(103)))
                .andExpect(jsonPath("$.items[2].questionId", Is.is(103)))
                .andExpect(jsonPath("$.items[2].countVote", Is.is(1)));

    }

    //Проверка выборки закладок по принадлежности группе groupId = 101
    @Test
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllBookMarksInUserProfile_By_GroupId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllBookMarksInUserProfile_By_GroupId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllBookMarksInUserProfile_By_GroupId() throws Exception {
        mockMvc.perform(get("/api/user/profile/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("groupId", "101")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()", Is.is(4)))

                .andExpect(jsonPath("$.items[0].bookmarkId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].groupBookmarkId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].groupBookmarkTitle", Is.is("group 101")))

                .andExpect(jsonPath("$.items[0].bookmarkId", Is.is(102)))
                .andExpect(jsonPath("$.items[0].groupBookmarkId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].groupBookmarkTitle", Is.is("group 101")))

                .andExpect(jsonPath("$.items[0].bookmarkId", Is.is(103)))
                .andExpect(jsonPath("$.items[0].groupBookmarkId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].groupBookmarkTitle", Is.is("group 101")))

                .andExpect(jsonPath("$.items[0].bookmarkId", Is.is(104)))
                .andExpect(jsonPath("$.items[0].groupBookmarkId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].groupBookmarkTitle", Is.is("group 101")));
    }


    //Проверка выборки закладок по принадлежности группе groupId = 101
    @Test
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllBookMarksInUserProfile_By_GroupId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllBookMarksInUserProfile_By_GroupId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllBookMarksInUserProfile_By_GroupId_WhenEmpty() throws Exception {
        mockMvc.perform(get("/api/user/profile/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("groupId", "102")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()", Is.is(0)));
        ;
    }
    //Проверка работы пагинации
    @Test
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllBookMarksInUserProfilePagination_WhenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllBookMarksInUserProfilePagination_WhenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllBookMarksInUserProfilePagination_WhenExists() throws Exception {
        mockMvc.perform(get("/api/user/profile/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "1")
                        .param("items", "2")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(4)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(2)))

                .andExpect(jsonPath("$.items[0].bookmarkId", Is.is(102)))
                .andExpect(jsonPath("$.items[0].groupBookmarkTitle", Is.is("group 101")))
                .andExpect(jsonPath("$.items[0].questionId", Is.is(102)))
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 102")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(104)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU4")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 4")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(105)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj5")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 5")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(106)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP6")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 6")))
                .andExpect(jsonPath("$.items[0].countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.items[0].countView", Is.is(0)))
                .andExpect(jsonPath("$.items[0].note", Is.is("title 102")))

                .andExpect(jsonPath("$.items[1].bookmarkId", Is.is(101)))
                .andExpect(jsonPath("$.items[1].groupBookmarkTitle", Is.is("group 101")))
                .andExpect(jsonPath("$.items[1].questionId", Is.is(101)))
                .andExpect(jsonPath("$.items[1].title", Is.is("Question 101")))
                .andExpect(jsonPath("$.items[1].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[1].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[1].listTagDto[0].description", Is.is("Description of tag 1")))
                .andExpect(jsonPath("$.items[1].listTagDto[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[1].listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items[1].listTagDto[1].description", Is.is("Description of tag 2")))
                .andExpect(jsonPath("$.items[1].listTagDto[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[1].listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items[1].listTagDto[2].description", Is.is("Description of tag 3")))
                .andExpect(jsonPath("$.items[1].countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.items[1].countView", Is.is(0)))
                .andExpect(jsonPath("$.items[1].note", Is.is("title 101")));
    }

    // Проверка всех данных, questionId у CommentAnswer должен быть = answerId у CommentAnswer
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileCommentDtoById_shouldFindAllData_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileCommentDtoById_shouldFindAllData_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserProfileCommentDtoById_shouldFindAllData_whenExists() throws Exception {
        mockMvc.perform(get("/api/user/profile/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(4)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].commentText", Is.is("Random CommentAnswer 1")))
                .andExpect(jsonPath("$.items[0].answerId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].questionId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].commentType", Is.is("ANSWER")))

                .andExpect(jsonPath("$.items[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[1].commentText", Is.is("Random CommentAnswer 2")))
                .andExpect(jsonPath("$.items[1].answerId", Is.is(102)))
                .andExpect(jsonPath("$.items[1].questionId", Is.is(102)))
                .andExpect(jsonPath("$.items[1].commentType", Is.is("ANSWER")))

                .andExpect(jsonPath("$.items[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[2].commentText", Is.is("Random CommentQuestion 1")))
                .andExpect(jsonPath("$.items[2].answerId", Is.is(IsNull.nullValue())))
                .andExpect(jsonPath("$.items[2].questionId", Is.is(101)))
                .andExpect(jsonPath("$.items[2].commentType", Is.is("QUESTION")))

                .andExpect(jsonPath("$.items[3].id", Is.is(104)))
                .andExpect(jsonPath("$.items[3].commentText", Is.is("Random CommentQuestion 2")))
                .andExpect(jsonPath("$.items[3].answerId", Is.is(IsNull.nullValue())))
                .andExpect(jsonPath("$.items[3].questionId", Is.is(102)))
                .andExpect(jsonPath("$.items[3].commentType", Is.is("QUESTION")));
    }

    //Пользователь не оставил ни одного комментария
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileCommentDtoById_shouldFindAllData_whenEmpty/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileCommentDtoById_shouldFindAllData_whenEmpty/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserProfileCommentDtoById_shouldFindAllData_whenEmpty() throws Exception {
        mockMvc.perform(get("/api/user/profile/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(0)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(0)));
    }

    //Проверка пагинации
    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileCommentDtoPaginationById_shouldFindAllData_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/getAllUserProfileCommentDtoPaginationById_shouldFindAllData_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserProfileCommentDtoPaginationById_shouldFindAllData_whenExists() throws Exception {
        mockMvc.perform(get("/api/user/profile/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("items", "2")
                        .param("page", "3")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.itemsOnPage", Is.is(2)))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(3)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(5)))

                .andExpect(jsonPath("$.items[0].id", Is.is(105)))
                .andExpect(jsonPath("$.items[0].commentText", Is.is("Random CommentAnswer 5")))
                .andExpect(jsonPath("$.items[0].answerId", Is.is(102)))
                .andExpect(jsonPath("$.items[0].questionId", Is.is(102)))
                .andExpect(jsonPath("$.items[0].commentType", Is.is("ANSWER")))

                .andExpect(jsonPath("$.items[1].id", Is.is(106)))
                .andExpect(jsonPath("$.items[1].commentText", Is.is("Random CommentQuestion 1")))
                .andExpect(jsonPath("$.items[1].answerId", Is.is(IsNull.nullValue())))
                .andExpect(jsonPath("$.items[1].questionId", Is.is(101)))
                .andExpect(jsonPath("$.items[1].commentType", Is.is("QUESTION")));
    }
}