package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.codehaus.jackson.map.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        assertThat(SingleResultUtil.getSingleResultOrNull((TypedQuery<Long>) entityManager.createQuery("select r.id as s from Tag as r where r.name =:ids").setParameter("ids", "firstTag")).isEmpty()).isEqualTo(false);

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

    // Голосование ЗА вопрос
    // Устанавливает голос +1 за вопрос и +10 к репутации автора вопроса
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/upVote/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/upVote/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void upVote() throws Exception {

        mockMvc.perform(post("/api/user/question/101/upVote")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(1)));

        String count1 = valueOf(entityManager.createQuery("select sum(r.count) as s from Reputation as r where r.author = 110")
                .getResultList());
        count1 = count1.replaceAll("[()<\\[\\]>]","");
        assertThat(count1).isEqualTo("1510");
    }

    // Голосование ЗА вопрос
    // Передают не верный questionId
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/upVotePassWrongQuestionId/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/upVotePassWrongQuestionId/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void upVotePassWrongQuestionId() throws Exception {

        mockMvc.perform(post("/api/user/question/123/upVote")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("Can't find question with id:123")));
    }

    //Проверка получения всех данных и сортировки по умолчанию (по параметру NEW - от новых вопросов к старым)
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/getAllQuestionsSortedBy_NEW_shouldFindAllData_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/getAllQuestionsSortedBy_NEW_shouldFindAllData_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllQuestionsSortedBy_NEW_shouldFindAllData_whenExists() throws Exception {

        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/user/question/sorted")
                                .param("page", "1")
                                .param("sortedBy", "NEW")
                                .contentType("application/json")
                                .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(104)))
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 104")))
                .andExpect(jsonPath("$.items[0].authorId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].authorReputation", Is.is(30)))
                .andExpect(jsonPath("$.items[0].authorName", Is.is("User 101")))
                .andExpect(jsonPath("$.items[0].authorImage", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].description", Is.is("What do you think about question 104?")))
                .andExpect(jsonPath("$.items[0].viewCount", Is.is(0)))
                .andExpect(jsonPath("$.items[0].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.items[0].countValuable", Is.is(0)))
                .andExpect(jsonPath("$.items[0].persistDateTime", Is.is("2023-02-08T21:44")))
                .andExpect(jsonPath("$.items[0].lastUpdateDateTime", Is.is("2023-02-12T00:00:00")))

                .andExpect(jsonPath("$.items[1].id", Is.is(103)))
                .andExpect(jsonPath("$.items[1].title", Is.is("Question 103")))
                .andExpect(jsonPath("$.items[1].authorId", Is.is(104)))
                .andExpect(jsonPath("$.items[1].authorReputation", Is.is(20)))
                .andExpect(jsonPath("$.items[1].authorName", Is.is("User 104")))
                .andExpect(jsonPath("$.items[1].authorImage", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[1].description", Is.is("What do you think about question 103?")))
                .andExpect(jsonPath("$.items[1].viewCount", Is.is(1)))
                .andExpect(jsonPath("$.items[1].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.items[1].countValuable", Is.is(-1)))
                .andExpect(jsonPath("$.items[1].persistDateTime", Is.is("2023-02-08T21:30")))
                .andExpect(jsonPath("$.items[1].lastUpdateDateTime", Is.is("2023-02-12T00:00:00")))

                .andExpect(jsonPath("$.items[2].id", Is.is(102)))
                .andExpect(jsonPath("$.items[2].title", Is.is("Question 102")))
                .andExpect(jsonPath("$.items[2].authorId", Is.is(101)))
                .andExpect(jsonPath("$.items[2].authorReputation", Is.is(30)))
                .andExpect(jsonPath("$.items[2].authorName", Is.is("User 101")))
                .andExpect(jsonPath("$.items[2].authorImage", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[2].description", Is.is("What do you think about question 102?")))
                .andExpect(jsonPath("$.items[2].viewCount", Is.is(2)))
                .andExpect(jsonPath("$.items[2].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.items[2].countValuable", Is.is(2)))
                .andExpect(jsonPath("$.items[2].persistDateTime", Is.is("2023-02-08T21:26")))
                .andExpect(jsonPath("$.items[2].lastUpdateDateTime", Is.is("2023-02-12T00:00:00")))

                .andExpect(jsonPath("$.items[3].id", Is.is(101)))
                .andExpect(jsonPath("$.items[3].title", Is.is("Question 101")))
                .andExpect(jsonPath("$.items[3].authorId", Is.is(105)))
                .andExpect(jsonPath("$.items[3].authorReputation", Is.is(20)))
                .andExpect(jsonPath("$.items[3].authorName", Is.is("User 105")))
                .andExpect(jsonPath("$.items[3].authorImage", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[3].description", Is.is("What do you think about question 101?")))
                .andExpect(jsonPath("$.items[3].viewCount", Is.is(2)))
                .andExpect(jsonPath("$.items[3].countAnswer", Is.is(2)))
                .andExpect(jsonPath("$.items[3].countValuable", Is.is(0)))
                .andExpect(jsonPath("$.items[3].persistDateTime", Is.is("2023-02-08T21:23")))
                .andExpect(jsonPath("$.items[3].lastUpdateDateTime", Is.is("2023-02-12T00:00:00")));
    }

    //Проверка получения всех вопросов по параметру NoAnswer - которым еще не дан ответ
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/getAllQuestionsSortedBy_NoAnswer/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/getAllQuestionsSortedBy_NoAnswer/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllQuestionsSortedBy_NoAnswer() throws Exception {

        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/user/question/sorted")
                                .param("page", "1")
                                .param("sortedBy", "NoAnswer")
                                .contentType("application/json")
                                .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()", Is.is(2)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 103")))
                .andExpect(jsonPath("$.items[0].authorId", Is.is(104)))
                .andExpect(jsonPath("$.items[0].authorReputation", Is.is(20)))
                .andExpect(jsonPath("$.items[0].authorName", Is.is("User 104")))
                .andExpect(jsonPath("$.items[0].authorImage", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].description", Is.is("What do you think about question 103?")))
                .andExpect(jsonPath("$.items[0].viewCount", Is.is(1)))
                .andExpect(jsonPath("$.items[0].countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.items[0].countValuable", Is.is(-1)))
                .andExpect(jsonPath("$.items[0].persistDateTime", Is.is("2023-02-08T21:30")))
                .andExpect(jsonPath("$.items[0].lastUpdateDateTime", Is.is("2023-02-12T00:00:00")))

                .andExpect(jsonPath("$.items[1].id", Is.is(104)))
                .andExpect(jsonPath("$.items[1].title", Is.is("Question 104")))
                .andExpect(jsonPath("$.items[1].authorId", Is.is(101)))
                .andExpect(jsonPath("$.items[1].authorReputation", Is.is(30)))
                .andExpect(jsonPath("$.items[1].authorName", Is.is("User 101")))
                .andExpect(jsonPath("$.items[1].authorImage", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[1].description", Is.is("What do you think about question 104?")))
                .andExpect(jsonPath("$.items[1].viewCount", Is.is(0)))
                .andExpect(jsonPath("$.items[1].countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.items[1].countValuable", Is.is(0)))
                .andExpect(jsonPath("$.items[1].persistDateTime", Is.is("2023-02-08T21:44")))
                .andExpect(jsonPath("$.items[1].lastUpdateDateTime", Is.is("2023-02-12T00:00:00")));

    }

    //Проверка получения всех вопросов по параметру VIEW - по количеству просмотров
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/getAllQuestionsSortedBy_VIEW/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/getAllQuestionsSortedBy_VIEW/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllQuestionsSortedBy_VIEW() throws Exception {

        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/user/question/sorted")
                                .param("page", "1")
                                .param("sortedBy", "VIEW")
                                .contentType("application/json")
                                .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.items.length()", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 101")))
                .andExpect(jsonPath("$.items[0].authorId", Is.is(105)))
                .andExpect(jsonPath("$.items[0].authorReputation", Is.is(20)))
                .andExpect(jsonPath("$.items[0].authorName", Is.is("User 105")))
                .andExpect(jsonPath("$.items[0].authorImage", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].description", Is.is("What do you think about question 101?")))
                .andExpect(jsonPath("$.items[0].viewCount", Is.is(2)))
                .andExpect(jsonPath("$.items[0].countAnswer", Is.is(2)))
                .andExpect(jsonPath("$.items[0].countValuable", Is.is(0)))
                .andExpect(jsonPath("$.items[0].persistDateTime", Is.is("2023-02-08T21:23")))
                .andExpect(jsonPath("$.items[0].lastUpdateDateTime", Is.is("2023-02-12T00:00:00")))

                .andExpect(jsonPath("$.items[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[1].title", Is.is("Question 102")))
                .andExpect(jsonPath("$.items[1].authorId", Is.is(101)))
                .andExpect(jsonPath("$.items[1].authorReputation", Is.is(30)))
                .andExpect(jsonPath("$.items[1].authorName", Is.is("User 101")))
                .andExpect(jsonPath("$.items[1].authorImage", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[1].description", Is.is("What do you think about question 102?")))
                .andExpect(jsonPath("$.items[1].viewCount", Is.is(2)))
                .andExpect(jsonPath("$.items[1].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.items[1].countValuable", Is.is(2)))
                .andExpect(jsonPath("$.items[1].persistDateTime", Is.is("2023-02-08T21:26")))
                .andExpect(jsonPath("$.items[1].lastUpdateDateTime", Is.is("2023-02-12T00:00:00")))

                .andExpect(jsonPath("$.items[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[2].title", Is.is("Question 103")))
                .andExpect(jsonPath("$.items[2].authorId", Is.is(104)))
                .andExpect(jsonPath("$.items[2].authorReputation", Is.is(20)))
                .andExpect(jsonPath("$.items[2].authorName", Is.is("User 104")))
                .andExpect(jsonPath("$.items[2].authorImage", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[2].description", Is.is("What do you think about question 103?")))
                .andExpect(jsonPath("$.items[2].viewCount", Is.is(1)))
                .andExpect(jsonPath("$.items[2].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.items[2].countValuable", Is.is(-1)))
                .andExpect(jsonPath("$.items[2].persistDateTime", Is.is("2023-02-08T21:30")))
                .andExpect(jsonPath("$.items[2].lastUpdateDateTime", Is.is("2023-02-12T00:00:00")))

                .andExpect(jsonPath("$.items[3].id", Is.is(104)))
                .andExpect(jsonPath("$.items[3].title", Is.is("Question 104")))
                .andExpect(jsonPath("$.items[3].authorId", Is.is(101)))
                .andExpect(jsonPath("$.items[3].authorReputation", Is.is(30)))
                .andExpect(jsonPath("$.items[3].authorName", Is.is("User 101")))
                .andExpect(jsonPath("$.items[3].authorImage", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[3].description", Is.is("What do you think about question 104?")))
                .andExpect(jsonPath("$.items[3].viewCount", Is.is(0)))
                .andExpect(jsonPath("$.items[3].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.items[3].countValuable", Is.is(0)))
                .andExpect(jsonPath("$.items[3].persistDateTime", Is.is("2023-02-08T21:44")))
                .andExpect(jsonPath("$.items[3].lastUpdateDateTime", Is.is("2023-02-12T00:00:00")));

    }

    //Проверка Пагинации
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/getAllQuestionsSortedBy_VIEW/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/getAllQuestionsSortedBy_VIEW/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllQuestionsSortedBy_VIEW_Pagination() throws Exception {

        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/user/question/sorted")
                                .param("page", "3")
                                .param("items", "1")
                                .param("sortedBy", "VIEW")
                                .contentType("application/json")
                                .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.items.length()", Is.is(1)))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(3)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 103")))
                .andExpect(jsonPath("$.items[0].authorId", Is.is(104)))
                .andExpect(jsonPath("$.items[0].authorReputation", Is.is(20)))
                .andExpect(jsonPath("$.items[0].authorName", Is.is("User 104")))
                .andExpect(jsonPath("$.items[0].authorImage", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].description", Is.is("What do you think about question 103?")))
                .andExpect(jsonPath("$.items[0].viewCount", Is.is(1)))
                .andExpect(jsonPath("$.items[0].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.items[0].countValuable", Is.is(-1)))
                .andExpect(jsonPath("$.items[0].persistDateTime", Is.is("2023-02-08T21:30")))
                .andExpect(jsonPath("$.items[0].lastUpdateDateTime", Is.is("2023-02-12T00:00:00")));
    }

    //Проверка получения вопросов по trackedTag
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/getAllQuestionsSortedBy_trackedTag/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/getAllQuestionsSortedBy_trackedTag/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllQuestionsSortedBy_trackedTag() throws Exception {

        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/user/question/sorted")
                                .param("page", "1")
                                .param("trackedTag", "110", "105")
                                .contentType("application/json")
                                .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.items.length()", Is.is(2)))

                .andExpect(jsonPath("$.items[0].id", Is.is(104)))
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 104")))
                .andExpect(jsonPath("$.items[0].authorId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].authorReputation", Is.is(30)))
                .andExpect(jsonPath("$.items[0].authorName", Is.is("User 101")))
                .andExpect(jsonPath("$.items[0].authorImage", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].description", Is.is("What do you think about question 104?")))
                .andExpect(jsonPath("$.items[0].viewCount", Is.is(0)))
                .andExpect(jsonPath("$.items[0].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.items[0].countValuable", Is.is(0)))
                .andExpect(jsonPath("$.items[0].persistDateTime", Is.is("2023-02-08T21:44")))
                .andExpect(jsonPath("$.items[0].lastUpdateDateTime", Is.is("2023-02-12T00:00:00")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(110)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU10")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 10")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(111)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj11")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 11")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(112)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LAST_GDJP12")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 12")))

                .andExpect(jsonPath("$.items[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[1].title", Is.is("Question 102")))
                .andExpect(jsonPath("$.items[1].authorId", Is.is(101)))
                .andExpect(jsonPath("$.items[1].authorReputation", Is.is(30)))
                .andExpect(jsonPath("$.items[1].authorName", Is.is("User 101")))
                .andExpect(jsonPath("$.items[1].authorImage", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[1].description", Is.is("What do you think about question 102?")))
                .andExpect(jsonPath("$.items[1].viewCount", Is.is(2)))
                .andExpect(jsonPath("$.items[1].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.items[1].countValuable", Is.is(2)))
                .andExpect(jsonPath("$.items[1].persistDateTime", Is.is("2023-02-08T21:26")))
                .andExpect(jsonPath("$.items[1].lastUpdateDateTime", Is.is("2023-02-12T00:00:00")))
                .andExpect(jsonPath("$.items[1].listTagDto[0].id", Is.is(104)))
                .andExpect(jsonPath("$.items[1].listTagDto[0].name", Is.is("vfOxMU4")))
                .andExpect(jsonPath("$.items[1].listTagDto[0].description", Is.is("Description of tag 4")))
                .andExpect(jsonPath("$.items[1].listTagDto[1].id", Is.is(105)))
                .andExpect(jsonPath("$.items[1].listTagDto[1].name", Is.is("iThKcj5")))
                .andExpect(jsonPath("$.items[1].listTagDto[1].description", Is.is("Description of tag 5")))
                .andExpect(jsonPath("$.items[1].listTagDto[2].id", Is.is(106)))
                .andExpect(jsonPath("$.items[1].listTagDto[2].name", Is.is("LTGDJP6")))
                .andExpect(jsonPath("$.items[1].listTagDto[2].description", Is.is("Description of tag 6")));
    }

    //Проверка получения вопросов по ignoredTag
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/getAllQuestionsSortedBy_ignoredTag/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/getAllQuestionsSortedBy_ignoredTag/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllQuestionsSortedBy_ignoredTag() throws Exception {

        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/user/question/sorted")
                                .param("page", "1")
                                .param("ignoredTag", "101", "104", "107")
                                .contentType("application/json")
                                .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.items.length()", Is.is(1)))

                .andExpect(jsonPath("$.items[0].id", Is.is(104)))
                .andExpect(jsonPath("$.items[0].title", Is.is("Question 104")))
                .andExpect(jsonPath("$.items[0].authorId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].authorReputation", Is.is(30)))
                .andExpect(jsonPath("$.items[0].authorName", Is.is("User 101")))
                .andExpect(jsonPath("$.items[0].authorImage", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].description", Is.is("What do you think about question 104?")))
                .andExpect(jsonPath("$.items[0].viewCount", Is.is(0)))
                .andExpect(jsonPath("$.items[0].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.items[0].countValuable", Is.is(0)))
                .andExpect(jsonPath("$.items[0].persistDateTime", Is.is("2023-02-08T21:44")))
                .andExpect(jsonPath("$.items[0].lastUpdateDateTime", Is.is("2023-02-12T00:00:00")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(110)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU10")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 10")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(111)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj11")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 11")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(112)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LAST_GDJP12")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 12")));
    }


    // Голосование ЗА вопрос
    // Пользователь уже голосовал за этот вопрос
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/upVoteIfTheUserHasAlreadyVoted/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/upVoteIfTheUserHasAlreadyVoted/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void upVoteIfTheUserHasAlreadyVoted() throws Exception {

        mockMvc.perform(post("/api/user/question/101/upVote")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("User was voting")));
    }


    // Голосование ПРОТИВ
    // Устанавливает голос -1 за вопрос и -5 к репутации автора вопроса
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/downVote/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/downVote/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void downVote() throws Exception {

        mockMvc.perform(post("/api/user/question/101/downVote")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(1)));

        String count1 = valueOf(entityManager.createQuery("select sum(r.count) as s from Reputation as r where r.author = 110")
                .getResultList());
        count1 = count1.replaceAll("[()<\\[\\]>]","");
        assertThat(count1).isEqualTo("95");
    }

    // Голосование ПРОТИВ
    // Передают не верный questionId
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/downVotePassWrongQuestionId/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/downVotePassWrongQuestionId/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void downVotePassWrongQuestionId() throws Exception {

        mockMvc.perform(post("/api/user/question/156/upVote")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("Can't find question with id:156")));
    }

    // Голосование ПРОТИВ
    // Пользователь уже голосовал за этот вопрос
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/downVoteIfTheUserHasAlreadyVoted/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/downVoteIfTheUserHasAlreadyVoted/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void downVoteIfTheUserHasAlreadyVoted() throws Exception {

        mockMvc.perform(post("/api/user/question/101/upVote")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("User was voting")));
    }


    //получение пагинированного списка
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/allQuestionsWithTrackedTagsAndIgnoredTags/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/allQuestionsWithTrackedTagsAndIgnoredTags/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void allQuestionsWithTrackedTagsAndIgnoredTags() throws Exception {
        mockMvc.perform(get("/api/user/question")
                        .param("page", "1")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.items.[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items.[0].title", Is.is("Question 101")))
                .andExpect(jsonPath("$.items.[0].listTagDto.size()", Is.is(1)))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items.[0].listTagDto[0].description", Is.is("Description of tag 2")))
                .andExpect(jsonPath("$.items.[1].id", Is.is(103)))
                .andExpect(jsonPath("$.items.[1].title", Is.is("Question 103")))
                .andExpect(jsonPath("$.items.[1].listTagDto.size()", Is.is(1)))
                .andExpect(jsonPath("$.items.[1].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items.[1].listTagDto[0].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items.[1].listTagDto[0].description", Is.is("Description of tag 2")));

    }
}