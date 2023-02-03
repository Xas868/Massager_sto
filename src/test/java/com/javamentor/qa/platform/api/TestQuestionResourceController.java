package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static java.lang.String.valueOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TestQuestionResourceController extends AbstractClassForDRRiderMockMVCTests {

//Проверка создания нового вопроса + проверка добавление репутации автору вопроса
    @Test
    @Sql (scripts = "/script/TestQuestionResourceController/postCreateNewQuestion/Before.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql (scripts = "/script/TestQuestionResourceController/postCreateNewQuestion/After.sql",
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
void postCreateNewQuestion () throws Exception {
        mockMvc.perform(post("/api/user/question")
                .content("{\"title\": \"string\", \"description\": \"string\", \"tags\": [{\"id\": 0,\"name\": \"firstTag\",\"description\": \"string\"}]}")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value("string"))
                .andExpect(jsonPath("$.authorId").value("101"))
                .andExpect(jsonPath("$.authorName").value("user101"))
                .andExpect(jsonPath("$.authorImage").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.description").value("string"))
                .andExpect(jsonPath("$.viewCount").value("0"))
                .andExpect(jsonPath("$.countAnswer").value("0"))
                .andExpect(jsonPath("$.countValuable").value("0"))
                .andExpect(jsonPath("$.listTagDto[0].name").value("firstTag"));

        String count1 = valueOf(entityManager.createQuery("select sum(r.count) as s from Reputation as r where r.author = 101")
                .getResultList());
        count1 = count1.replaceAll("[()<\\[\\]>]","");
        assertThat(count1).isEqualTo("35");

    }


}
