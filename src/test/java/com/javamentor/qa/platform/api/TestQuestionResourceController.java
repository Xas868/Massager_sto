package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;

import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TestQuestionResourceController extends AbstractClassForDRRiderMockMVCTests {

    //Проверка создания нового вопроса + проверка добавление репутации автору вопроса
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/postCreateNewQuestion/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/postCreateNewQuestion/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void postCreateNewQuestion() throws Exception {
        QuestionCreateDto questionCreateDto = new QuestionCreateDto();
        List<TagDto> tagDtos = new ArrayList<>();
        TagDto tagDto = new TagDto(0L, "firstTag", "string");
        tagDtos.add(tagDto);
        questionCreateDto.setTitle("string");
        questionCreateDto.setDescription("string");
        questionCreateDto.setTags(tagDtos);
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/user/question")
                        .content(objectMapper.writeValueAsString(questionCreateDto))
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
        List count = (entityManager.createQuery("select sum(r.count) as s from Reputation as r where r.author = 101")
                .getResultList());
        assertThat(count.get(0).toString()).isEqualTo("35");

    }
    //Проверка создания нового вопроса если новый таг, то он доавляется в список tag
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/postCreateNewQuestion/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/postCreateNewQuestion/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void postCreateNewQuestionCreateNewTag() throws Exception {
        QuestionCreateDto questionCreateDto = new QuestionCreateDto();
        List<TagDto> tagDtos = new ArrayList<>();
        TagDto tagDto = new TagDto(0L, "firstTag", "string");
        tagDtos.add(tagDto);
        questionCreateDto.setTitle("string");
        questionCreateDto.setDescription("string");
        questionCreateDto.setTags(tagDtos);
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/user/question")
                        .content(objectMapper.writeValueAsString(questionCreateDto))
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
        assertThat(SingleResultUtil.getSingleResultOrNull((TypedQuery<Long>) entityManager.createQuery("select r.id as s from Tag as r where r.name =:ids").setParameter("ids","firstTag")).isEmpty()).isEqualTo(false);

    }
    //Проверка создания нового вопроса без указания title. Выбрасывает ошибку
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/postCreateNewQuestion/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/postCreateNewQuestion/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void postCreateNewQuestionWithoutTitle() throws Exception {
        QuestionCreateDto questionCreateDto = new QuestionCreateDto();
        List<TagDto> tagDtos = new ArrayList<>();
        TagDto tagDto = new TagDto(0L, "firstTag", "string");
        tagDtos.add(tagDto);
        questionCreateDto.setTitle("");
        questionCreateDto.setDescription("string");
        questionCreateDto.setTags(tagDtos);
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/user/question")
                        .content(objectMapper.writeValueAsString(questionCreateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    //Проверка создания нового вопроса без указания tag. Выбрасывает ошибку
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/postCreateNewQuestion/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/postCreateNewQuestion/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void postCreateNewQuestionWithoutTag() throws Exception {
        QuestionCreateDto questionCreateDto = new QuestionCreateDto();
        questionCreateDto.setTitle("string");
        questionCreateDto.setDescription("string");
        questionCreateDto.setTags(null);
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/user/question")
                        .content(objectMapper.writeValueAsString(questionCreateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    //Проверка создания нового вопроса без указания Description. Выбрасывает ошибку
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/postCreateNewQuestion/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/postCreateNewQuestion/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void postCreateNewQuestionWithoutDescription() throws Exception {
        QuestionCreateDto questionCreateDto = new QuestionCreateDto();
        List<TagDto> tagDtos = new ArrayList<>();
        TagDto tagDto = new TagDto(0L, "firstTag", "string");
        tagDtos.add(tagDto);
        questionCreateDto.setTitle("string");
        questionCreateDto.setDescription("");
        questionCreateDto.setTags(tagDtos);
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/user/question")
                        .content(objectMapper.writeValueAsString(questionCreateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
