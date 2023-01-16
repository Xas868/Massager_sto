package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import com.javamentor.qa.platform.models.entity.GroupBookmark;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class TestProfileBookmarkResourceController extends AbstractClassForDRRiderMockMVCTests {

    // Проверка BookMarks
    @Test
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getUserBookMarksDtoShouldWhenExist/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getUserBookMarksDtoShouldWhenExist/After.sql",
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
                .andExpect(jsonPath("$.[0].bookmarkId", Is.is(1)))
                .andExpect(jsonPath("$.[0].questionId", Is.is(100)))
                .andExpect(jsonPath("$.[0].title", Is.is("Question 100")))
                .andExpect(jsonPath("$.[0].listTagDto.size()", Is.is(3)))
                .andExpect(jsonPath("$.[0].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.[0].countView", Is.is(4)))
                .andExpect(jsonPath("$.[0].note", Is.is("note 1")));

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

    // Не должен бросать ошибку если нету закладок
    @Test
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getUserBookMarksDtoShouldWhenExist/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getUserBookMarksDtoShouldWhenExist/After.sql",
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
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllUserProfileGroupBookMarkNames/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllUserProfileGroupBookMarkNames/After.sql",
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
                .andExpect(jsonPath("$[0].id", Is.is(100)))
                .andExpect(jsonPath("$[0].title", Is.is("group_bookmark1")))

                .andExpect(jsonPath("$[1].id", Is.is(101)))
                .andExpect(jsonPath("$[1].title", Is.is("group_bookmark2")))

                .andExpect(jsonPath("$[2].id", Is.is(102)))
                .andExpect(jsonPath("$[2].title", Is.is("group_bookmark3")))

                .andExpect(jsonPath("$[3].id", Is.is(103)))
                .andExpect(jsonPath("$[3].title", Is.is("group_bookmark4")));
    }

    //Проверка получения списка имён GroupBookMark не должен бросать ошибку если нету группы закладок у пользователя
    @Test
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllUserProfileGroupBookMarkNames/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllUserProfileGroupBookMarkNames/After.sql",
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
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllUserProfileGroupBookMarkNames/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllUserProfileGroupBookMarkNames/After.sql",
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
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllUserProfileGroupBookMarkNames/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllUserProfileGroupBookMarkNames/After.sql",
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
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllUserProfileGroupBookMarkNames/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/getAllUserProfileGroupBookMarkNames/After.sql",
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

    @Test
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/ShouldDeleteBookmarkByQuestionId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/ShouldDeleteBookmarkByQuestionId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteBookmarkByQuestionId() throws Exception{
        mockMvc.perform(delete("/api/user/profile/bookmark/{id}", 101)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(entityManager
                    .createQuery("select bm.id from BookMarks bm where bm.question.id = 101")
                .getSingleResult())
                .isNotNull();

    }

    @Test
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/ShouldDeleteBookmarkByQuestionId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileBookmarkResourceController/ShouldDeleteBookmarkByQuestionId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteBookmarkByQuestionIdIfNotExist() throws Exception{
        mockMvc.perform(delete("/api/user/profile/bookmark/{id}", 120)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
        assertThat(entityManager
                .createQuery("select bm.id from BookMarks bm where bm.question.id = 101")
                .getSingleResult())
                .isNotNull();

    }


}
