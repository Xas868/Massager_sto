package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestProfileUserResourceController extends AbstractClassForDRRiderMockMVCTests {


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














}
