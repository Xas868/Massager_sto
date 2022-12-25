package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestAnswerResourceController extends AbstractClassForDRRiderMockMVCTests {

    //Голосование за
    @Test
    @Sql(scripts = "/script/TestUserResourceController/changePassword/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/changePassword/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void upVote() throws Exception {

        mockMvc.perform(patch("/api/user/question/{questionId}/answer/1/upVote")
                        .param("password", "46xEPoAOu")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk());

//        String user = String.valueOf(entityManager.createQuery("select password from User where id = 100")
//                .getResultList());
//        user = user.replaceAll("[()<\\[\\]>]","");
//
//        Boolean bool = passwordEncoder.matches((CharSequence) "46xEPoAOu", user);
//
//        assertThat(bool).isEqualTo(true);


    }
}
