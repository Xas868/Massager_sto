package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TestMessageResourceController extends AbstractClassForDRRiderMockMVCTests {


    //идеальный вариант, есть глобальный чат, получаем все сообщения, сверяем порядок создания
    @Test
    @Sql("script/testMessageResourceController/shouldGetAllMessagesInGlobalChat/Before.sql")
    @Sql(scripts = "script/testMessageResourceController/shouldGetAllMessagesInGlobalChat/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetAllMessageInGlobalChat() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/message/global")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id").value(104))
                .andExpect(jsonPath("$.items[0].message").value("message_104"))
                .andExpect(jsonPath("$.items[0].nickName").value("user100@mail.ru"))
                .andExpect(jsonPath("$.items[0].userId").value(100))
                .andExpect(jsonPath("$.items[0].persistDateTime").value("2022-10-05T00:00:00"))
                .andExpect(jsonPath("$.items[1].id").value(103))
                .andExpect(jsonPath("$.items[1].message").value("message_103"))
                .andExpect(jsonPath("$.items[1].nickName").value("user101@mail.ru"))
                .andExpect(jsonPath("$.items[1].userId").value(101))
                .andExpect(jsonPath("$.items[1].persistDateTime").value("2022-10-04T00:00:00"))
                .andExpect(jsonPath("$.items[2].id").value(100))
                .andExpect(jsonPath("$.items[2].message").value("message_100"))
                .andExpect(jsonPath("$.items[2].nickName").value("user100@mail.ru"))
                .andExpect(jsonPath("$.items[2].userId").value(100))
                .andExpect(jsonPath("$.items[2].persistDateTime").value("2022-10-03T00:00:00"));
    }

    //вариант c параметрами (такая же таблица как в 1 тесте, но ограничиваем выборку 2мя объектами
    @Test
    @Sql("script/testMessageResourceController/shouldGetAllMessageInGlobalChatWithVariables/Before.sql")
    @Sql(scripts = "script/testMessageResourceController/shouldGetAllMessageInGlobalChatWithVariables/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetAllMessageInGlobalChatWithVariables() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/message/global?currentPage=1&items=2")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.size()").value(2))
                .andExpect(jsonPath("$.items[0].id").value(104))
                .andExpect(jsonPath("$.items[0].message").value("message_104"))
                .andExpect(jsonPath("$.items[0].nickName").value("user100@mail.ru"))
                .andExpect(jsonPath("$.items[0].userId").value(100))
                .andExpect(jsonPath("$.items[0].persistDateTime").value("2022-10-05T00:00:00"))
                .andExpect(jsonPath("$.items[1].id").value(103))
                .andExpect(jsonPath("$.items[1].message").value("message_103"))
                .andExpect(jsonPath("$.items[1].nickName").value("user101@mail.ru"))
                .andExpect(jsonPath("$.items[1].userId").value(101))
                .andExpect(jsonPath("$.items[1].persistDateTime").value("2022-10-04T00:00:00"));
    }


    //вариант, когда глобального чата нет
    @Test
    @Sql("script/testMessageResourceController/shouldGetZeroMessageInGlobalChat/Before.sql")
    @Sql(scripts = "script/testMessageResourceController/shouldGetZeroMessageInGlobalChat/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetZeroMessageInGlobalChat() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/message/global")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isEmpty());
    }

    //вариант, когда вход выполнен под ролью админа (403 ошибка)
    @Test
    @Sql("script/testMessageResourceController/shouldHaventAccess/Before.sql")
    @Sql(scripts = "script/testMessageResourceController/shouldHaventAccess/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldHaventAccess() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/message/global")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user120@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    //идеальный вариант, есть глобальный чат, получаем все сообщения, включающие текст "essa", сверяем порядок создания
    @Test
    @Sql("script/testMessageResourceController/shouldFindMessagesInGlobalChat/Before.sql")
    @Sql(scripts = "script/testMessageResourceController/shouldFindMessagesInGlobalChat/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldFindMessagesInGlobalChat() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/message/global/find?text=essa")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id").value(105))
                .andExpect(jsonPath("$.items[0].message").value("message_105"))
                .andExpect(jsonPath("$.items[0].nickName").value("user101@mail.ru"))
                .andExpect(jsonPath("$.items[0].userId").value(101))
                .andExpect(jsonPath("$.items[0].persistDateTime").value("2022-10-06T00:00:00"))
                .andExpect(jsonPath("$.items[1].id").value(103))
                .andExpect(jsonPath("$.items[1].message").value("message_103"))
                .andExpect(jsonPath("$.items[1].nickName").value("user101@mail.ru"))
                .andExpect(jsonPath("$.items[1].userId").value(101))
                .andExpect(jsonPath("$.items[1].persistDateTime").value("2022-10-04T00:00:00"))
                .andExpect(jsonPath("$.items[2].id").value(100))
                .andExpect(jsonPath("$.items[2].message").value("message_100"))
                .andExpect(jsonPath("$.items[2].nickName").value("user100@mail.ru"))
                .andExpect(jsonPath("$.items[2].userId").value(100))
                .andExpect(jsonPath("$.items[2].persistDateTime").value("2022-10-03T00:00:00"));
    }

    //вариант, где есть глобальный чат, получаем все сообщения, включающие текст "essa", ограничиваем пагинацию 2мя объектами
    @Test
    @Sql("script/testMessageResourceController/shouldFindMessagesInGlobalChatWithVariables/Before.sql")
    @Sql(scripts = "script/testMessageResourceController/shouldFindMessagesInGlobalChatWithVariables/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldFindMessagesInGlobalChatWithVariables() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/message/global/find?text=essa&currentPage=1&items=2")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id").value(105))
                .andExpect(jsonPath("$.items[0].message").value("message_105"))
                .andExpect(jsonPath("$.items[0].nickName").value("user101@mail.ru"))
                .andExpect(jsonPath("$.items[0].userId").value(101))
                .andExpect(jsonPath("$.items[0].persistDateTime").value("2022-10-06T00:00:00"))
                .andExpect(jsonPath("$.items[1].id").value(103))
                .andExpect(jsonPath("$.items[1].message").value("message_103"))
                .andExpect(jsonPath("$.items[1].nickName").value("user101@mail.ru"))
                .andExpect(jsonPath("$.items[1].userId").value(101));
    }

    //вариант, когда нет глобального чата, получаем все сообщения, включающие текст "essa"
    @Test
    @Sql("script/testMessageResourceController/shouldFindZeroMessagesInGlobalChatWhenChatIsNotGlobal/Before.sql")
    @Sql(scripts = "script/testMessageResourceController/shouldFindZeroMessagesInGlobalChatWhenChatIsNotGlobal/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldFindZeroMessagesInGlobalChatWhenChatIsNotGlobal() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/message/global/find?text=essa")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.size()").value(0));
    }

    //вариант, когда есть глобальный чат, получаем все сообщения, включающие текст "messss", не находим среди message
    @Test
    @Sql("script/testMessageResourceController/shouldFindZeroMessagesInGlobalChatWithWrongText/Before.sql")
    @Sql(scripts = "script/testMessageResourceController/shouldFindZeroMessagesInGlobalChatWithWrongText/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldFindZeroMessagesInGlobalChatWithWrongText() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/message/global/find?text=messss")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.size()").value(0));
    }
@Test
@Sql("script/testMessageResourceController/shouldAddMessageToStar/Before.sql")
@Sql(scripts = "script/testMessageResourceController/shouldAddMessageToStar/After.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
 public void shouldAddMessageToStar() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/user/message/star")
                    .content("103")
                    .contentType("application/json")
                    .header("Authorization",
                            "Bearer " + getToken("user100@mail.ru", "user100")))
            .andDo(print())
            .andExpect(status().isOk());
 }

    @Test
    @Sql("script/testMessageResourceController/shouldNotAddMessageToStar_MessageDoesntExist/Before.sql")
    @Sql(scripts = "script/testMessageResourceController/shouldNotAddMessageToStar_MessageDoesntExist/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotAddMessageToStar_MessageDoesntExist() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/user/message/star")
                        .content("107")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("script/testMessageResourceController/shouldNotAddMessageToStar_MessageInStarAlready/Before.sql")
    @Sql(scripts = "script/testMessageResourceController/shouldNotAddMessageToStar_MessageInStarAlready/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotAddMessageToStar_MessageInStarAlready() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/user/message/star")
                        .content("100")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("script/testMessageResourceController/shouldDeleteMessageFromStar/Before.sql")
    @Sql(scripts = "script/testMessageResourceController/shouldDeleteMessageFromStar/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeleteMessageFromStar() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/message/star")
                        .content("100")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql("script/testMessageResourceController/shouldNotDeleteMessageFromStar_NoTheMessageInStar/Before.sql")
    @Sql(scripts = "script/testMessageResourceController/shouldNotDeleteMessageFromStar_NoTheMessageInStar/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldNotDeleteMessageFromStar_NoTheMessageInStar() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/message/star")
                        .content("103")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
