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

public class TestQuestionResourceController extends AbstractClassForDRRiderMockMVCTests {


//получение пагинированного списка
@Test
@Sql(scripts = "/script/TestQuestionResourceController/allQuestionsWithTrackedTagsAndIgnoredTags/Before.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/script/TestQuestionResourceController/allQuestionsWithTrackedTagsAndIgnoredTags/After.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
void allQuestionsWithTrackedTagsAndIgnoredTags() throws Exception {
    mockMvc.perform(get("/api/user/question")
                    .param("page","1")
                    .content("")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
            )
            .andDo(print())
            .andExpect(status().isOk())


              .andExpect(jsonPath("$.items.[0].questionId", Is.is(101)));
//            .andExpect(jsonPath("$.items.[0].title", Is.is("Question 101")))
//
//            .andExpect(jsonPath("$.items.[0].listTagDto.size()", Is.is(1)))
//            .andExpect(jsonPath("$.items.[0].listTagDto[0].id", Is.is(101)))
//            .andExpect(jsonPath("$.items.[0].listTagDto[0].name", Is.is("iThKcj2")))
//            .andExpect(jsonPath("$.items.[0].listTagDto[0].description", Is.is("Description of tag 2")))
//
//            .andExpect(jsonPath("$.items.[0].countAnswer", Is.is(1)))
//            .andExpect(jsonPath("$.items.[0].view", Is.is(1)))
//            .andExpect(jsonPath("$.items.[0].vote", Is.is(5)))


}

}