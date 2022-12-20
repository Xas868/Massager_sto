package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .andExpect(jsonPath("$[0].bookmarkId",Is.is(1)))
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
        mockMvc.perform(post("/api/user/profile/bookmark/group")
                        .content("testGroupBookMark")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", Is.is("testGroupBookMark")));
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
}
