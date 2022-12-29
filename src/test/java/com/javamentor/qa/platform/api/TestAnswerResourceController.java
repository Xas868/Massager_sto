package com.javamentor.qa.platform.api;

import com.google.gson.Gson;
import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.apache.coyote.Response;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mapstruct.MapperConfig;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.xmlunit.util.Mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TestAnswerResourceController extends AbstractClassForDRRiderMockMVCTests {

    //Голосование за
    @Test
    @Sql(scripts = "/script/TestAnswerResourceController/upVote/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestAnswerResourceController/upVote/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void upVote() throws Exception {
//        {questionId}
        mockMvc.perform(post("/api/user/question/101/answer/101/upVote")
//                        .param("password", "46xEPoAOu")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(1)));
        String persistDateTimeVar = String.valueOf(entityManager.createQuery("select sum(count) from Reputation where author = 101")
                .getResultList());
    }

    //Голосование за
    @Test
    @Sql(scripts = "/script/TestAnswerResourceController/downVote/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestAnswerResourceController/downVote/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void downVote() throws Exception {

        mockMvc.perform(post("/api/user/question/101/answer/101/downVote")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(-1)));


    }


    //Создание ответа на вопрос
    @Test
    @Sql(scripts = "/script/TestAnswerResourceController/createAnswer/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestAnswerResourceController/createAnswer/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createAnswer() throws Exception {
        String user1 = "";
        //jsonPathResultMatchers = jsonPath("$.persistDateTime");
        MvcResult Mockmvc = mockMvc.perform(post("/api/user/question/101/answer/add")
                        .content("string")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(1)))
                .andExpect(jsonPath("$.userId", Is.is(101)))
                .andExpect(jsonPath("$.userReputation", Is.is(1000)))
                .andExpect(jsonPath("$.questionId", Is.is(101)))
                .andExpect(jsonPath("$.htmlBody", Is.is("string")))
                .andExpect(jsonPath("$.isHelpful", Is.is(false)))
                .andExpect(jsonPath("$.isUserVote", Is.is(IsNull.nullValue())))
                .andExpect(jsonPath("$.dateAccept", Is.is(IsNull.nullValue())))
                .andExpect(jsonPath("$.countValuable", Is.is(0)))
                .andExpect(jsonPath("$.image", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.nickName", Is.is("user_101")))
                .andExpect(jsonPath("$.commentOnTheAnswerToTheQuestion").isEmpty())
                .andReturn();


        String responseBody = Mockmvc.getResponse().getContentAsString();



//        ResponseDto responseDto
//                = new Gson().fromJson(responseBody, ResponseDto.class);

        String persistDateTimeVar = String.valueOf(entityManager.createQuery("select persistDateTime from Answer where id = 1")
                .getResultList());
        persistDateTimeVar = persistDateTimeVar.replaceAll("[()<\\[\\]>]","");

        Boolean bool = responseBody.contains(persistDateTimeVar);
        assertThat(bool).isEqualTo(true);


    }


}

