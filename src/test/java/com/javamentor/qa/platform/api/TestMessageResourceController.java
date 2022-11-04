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
    @Sql("script.testMessageResourceController/shouldGetAllMessagesInGlobalChat/Before.sql")
    @Sql(scripts = "script.testMessageResourceController/shouldGetAllMessagesInGlobalChat/After.sql",
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

    //вариант, когда глобального чата нет
    @Test
    @Sql("script.testMessageResourceController/shouldGetZeroMessageInGlobalChat/Before.sql")
    @Sql(scripts = "script.testMessageResourceController/shouldGetZeroMessageInGlobalChat/After.sql",
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
    @Sql("script.testMessageResourceController/shouldHaventAccess/Before.sql")
    @Sql(scripts = "script.testMessageResourceController/shouldHaventAccess/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldHaventAccess() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/message/global")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
