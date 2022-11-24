package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//меняем пароль
public class TestUserResourceController1 extends AbstractClassForDRRiderMockMVCTests {
    @Test
    @Sql(scripts = "/script1/TestUserResourceController1/changePassword/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script1/TestUserResourceController1/changePassword/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void changePassword() throws Exception {

        mockMvc.perform(patch("/api/user/change/password")
                        .param("password", "46xEPoAOu")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk());

    }


    //меняем пароль
    @Test
    @Sql("script/testMessageResourceController/shouldHaventAccess/Before.sql")
    @Sql(scripts = "script/testMessageResourceController/shouldHaventAccess/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldHaventAccess() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/user/change/password")
                        .param("password", "$2a$11$twa4u5HMjrO5wGbOTQxHeu1vyF9InNfYYXipblquQ.q46xEPoAO.u")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @Sql("script/testMessageResourceController/shouldHaventAccess/Before.sql")
    @Sql(scripts = "script/testMessageResourceController/shouldHaventAccess/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldHaventAccess1() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/profile/questions")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldFindAllData_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestCommentResourceController/getAnswerCommentById_shouldFindAllData_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAnswerCommentById_shouldFindAllData_whenExists() throws Exception {

        mockMvc.perform(get("api/user/question/count", 100)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

}