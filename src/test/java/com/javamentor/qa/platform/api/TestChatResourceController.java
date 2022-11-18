package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestChatResourceController extends AbstractClassForDRRiderMockMVCTests {

    //вариант, есть 3 чата, 2 из них одиночные, в обоих участвует user100@mail.ru
    @Test
    @Sql("script/testChatResourceController/shouldGetAllSingleChats/Before.sql")
    @Sql(scripts = "script/testChatResourceController/shouldGetAllSingleChats/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetAllSingleChats() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/chat/single")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$.[0].id").value(100))
                .andExpect(jsonPath("$.[0].name").value("user101@mail.ru"))
                .andExpect(jsonPath("$.[0].image").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.[0].lastMessage").value("message_from_chat_100_and_user_100"))
                .andExpect(jsonPath("$.[0].persistDateTimeLastMessage").value("2022-10-03T00:00:00"))
                .andExpect(jsonPath("$.[1].id").value(102))
                .andExpect(jsonPath("$.[1].name").value("user102@mail.ru"))
                .andExpect(jsonPath("$.[1].image").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.[1].lastMessage").value("message_from_chat_102_and_user_102"))
                .andExpect(jsonPath("$.[1].persistDateTimeLastMessage").value("2022-10-01T00:00:00"));
    }

    //вариант, есть 3 чата, все из них групповые
    @Test
    @Sql("script/testChatResourceController/shouldGetZeroSingleChatsBecauseAllChatsAreGroup/Before.sql")
    @Sql(scripts = "script/testChatResourceController/shouldGetZeroSingleChatsBecauseAllChatsAreGroup/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetZeroSingleChatsBecauseAllChatsAreGroup() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/chat/single")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isEmpty());
    }

    //вариант, когда авторизован админ
    @Test
    @Sql("script/testChatResourceController/shouldGet403StatusWhenUserIsAdmin/Before.sql")
    @Sql(scripts = "script/testChatResourceController/shouldGet403StatusWhenUserIsAdmin/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGet403StatusWhenUserIsAdmin() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/chat/single")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    //вариант, есть 3 чата, 2 из них одиночные, в обоих участвует и авторизован user100@mail.ru с ролью User, остальные Admin
    @Test
    @Sql("script/testChatResourceController/shouldGetAllSingleChatsWithAdmins/Before.sql")
    @Sql(scripts = "script/testChatResourceController/shouldGetAllSingleChatsWithAdmins/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetAllSingleChatsWithAdmins() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/chat/single")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$.[0].id").value(100))
                .andExpect(jsonPath("$.[0].name").value("user101@mail.ru"))
                .andExpect(jsonPath("$.[0].image").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.[0].lastMessage").value("message_from_chat_100_and_user_100"))
                .andExpect(jsonPath("$.[0].persistDateTimeLastMessage").value("2022-10-03T00:00:00"))
                .andExpect(jsonPath("$.[1].id").value(102))
                .andExpect(jsonPath("$.[1].name").value("user102@mail.ru"))
                .andExpect(jsonPath("$.[1].image").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.[1].lastMessage").value("message_from_chat_102_and_user_102"))
                .andExpect(jsonPath("$.[1].persistDateTimeLastMessage").value("2022-10-01T00:00:00"));
    }

    //вариант, есть 3 чата, 2 из них групповые и в них участвует авторизованный пользователь, items = 10, currentPage = 1
    @Test
    @Sql("script/testChatResourceController/shouldGetAllGroupChats/Before.sql")
    @Sql(scripts = "script/testChatResourceController/shouldGetAllGroupChats/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetAllGroupChats() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/chat")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.size()").value(2))
                .andExpect(jsonPath("$.items[0].id").value(100))
                .andExpect(jsonPath("$.items[0].name").value("group_chat_100"))
                .andExpect(jsonPath("$.items[0].image").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.items[0].lastMessage").value("message_from_chat_100_and_user_100"))
                .andExpect(jsonPath("$.items[0].persistDateTimeLastMessage").value("2022-10-03T00:00:00"))
                .andExpect(jsonPath("$.items[1].id").value(102))
                .andExpect(jsonPath("$.items[1].name").value("group_chat_102"))
                .andExpect(jsonPath("$.items[1].image").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.items[1].lastMessage").value("message_from_chat_102_and_user_102"))
                .andExpect(jsonPath("$.items[1].persistDateTimeLastMessage").value("2022-10-01T00:00:00"));
    }

    //вариант, есть 3 чата, 2 из них групповые и в них участвует авторизованный пользователь, items = 1, currentPage = 1
    @Test
    @Sql("script/testChatResourceController/shouldGetGroupChatWithVariables/Before.sql")
    @Sql(scripts = "script/testChatResourceController/shouldGetGroupChatWithVariables/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetGroupChatWithVariables() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/chat?currentPage=1&items=1")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.size()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(100))
                .andExpect(jsonPath("$.items[0].name").value("group_chat_100"))
                .andExpect(jsonPath("$.items[0].image").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.items[0].lastMessage").value("message_from_chat_100_and_user_100"))
                .andExpect(jsonPath("$.items[0].persistDateTimeLastMessage").value("2022-10-03T00:00:00"));
    }

    //вариант, есть 3 чата, 2 из них групповые и в них НЕ участвует авторизованный пользователь, items = 10, currentPage = 1
    @Test
    @Sql("script/testChatResourceController/shouldZeroGroupChatWhenThatChatsAreWithoutThisUser/Before.sql")
    @Sql(scripts = "script/testChatResourceController/shouldZeroGroupChatWhenThatChatsAreWithoutThisUser/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldZeroGroupChatWhenThatChatsAreWithoutThisUser() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/chat")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isEmpty());
    }

    //вариант: получаем групповые чаты, есть 4 чата из них в 3 есть авторизированный пользователей и из этих 3, два group чата и 1 single чат, items = 10, currentPage = 1
    @Test
    @Sql("script/testChatResourceController/shouldGetGroupChatsWhenAuthorizedUserInTwoGroupChatsAndOneSingleChat/Before.sql")
    @Sql(scripts = "script/testChatResourceController/shouldGetGroupChatsWhenAuthorizedUserInTwoGroupChatsAndOneSingleChat/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetGroupChatsWhenAuthorizedUserInTwoGroupChatsAndOneSingleChat() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/chat")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.size()").value(2))
                .andExpect(jsonPath("$.items[0].id").value(100))
                .andExpect(jsonPath("$.items[0].name").value("group_chat_100"))
                .andExpect(jsonPath("$.items[0].image").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.items[0].lastMessage").value("message_from_chat_100_and_user_100"))
                .andExpect(jsonPath("$.items[0].persistDateTimeLastMessage").value("2022-10-03T00:00:00"))
                .andExpect(jsonPath("$.items[1].id").value(102))
                .andExpect(jsonPath("$.items[1].name").value("group_chat_102"))
                .andExpect(jsonPath("$.items[1].image").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.items[1].lastMessage").value("message_from_chat_102_and_user_102"))
                .andExpect(jsonPath("$.items[1].persistDateTimeLastMessage").value("2022-10-01T00:00:00"));
    }

    //вариант: получаем single чаты, есть 4 чата из них в 3 есть авторизированный пользователей и из этих 3, два сингл чата и 1 групп чат, items = 10, currentPage = 1
    @Test
    @Sql("script/testChatResourceController/shouldGetSingleChatsWhenAuthorizedUserInTwoSingleChatsAndOneGroupChat/Before.sql")
    @Sql(scripts = "script/testChatResourceController/shouldGetSingleChatsWhenAuthorizedUserInTwoSingleChatsAndOneGroupChat/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetSingleChatsWhenAuthorizedUserInTwoSingleChatsAndOneGroupChat() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/chat/single")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$.[0].id").value(101))
                .andExpect(jsonPath("$.[0].name").value("user101@mail.ru"))
                .andExpect(jsonPath("$.[0].image").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.[0].lastMessage").value("message_from_chat_101_and_user_101"))
                .andExpect(jsonPath("$.[0].persistDateTimeLastMessage").value("2022-10-02T00:00:00"))
                .andExpect(jsonPath("$.[1].id").value(103))
                .andExpect(jsonPath("$.[1].name").value("user102@mail.ru"))
                .andExpect(jsonPath("$.[1].image").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.[1].lastMessage").value("message_from_chat_103_and_user_102"))
                .andExpect(jsonPath("$.[1].persistDateTimeLastMessage").value("2022-10-04T00:00:00"));
    }

    //Если чат удаляет автор, то чат удаляется с пользователями
    @Test
    @Sql("/script/TestChatResourceController/shouldAuthorDeleteFromChatAllUsers/Before.sql")
    @Sql(scripts = "/script/TestChatResourceController/shouldAuthorDeleteFromChatAllUsers/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldAuthorDeleteFromChatAllUsers() throws Exception {
        mockMvc.perform(delete("/api/user/chat/{id}", 101)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "user1")))
                .andDo(print())
                .andExpect(content().string("GroupChat deleted"))
                .andExpect(status().isOk());
    }

    //Пользователь пытается удалить глобальный чат
    @Test
    @Sql("/script/TestChatResourceController/shouldNODeleteGlobalChat/Before.sql")
    @Sql(scripts = "/script/TestChatResourceController/shouldNODeleteGlobalChat/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldNODeleteGlobalChat() throws Exception {
        mockMvc.perform(delete("/api/user/chat/{id}", 101)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "user1")))
                .andDo(print())
                .andExpect(content().string("Вы пытаетесь удалить глобальный чат"))
                .andExpect(status().isBadRequest());
    }

    //Если чат удаляет не автор, то чат удаляется только у юзера
    @Test
    @Sql("/script/TestChatResourceController/shouldNotAuthorDeleteFromChatOnlyOneUser/Before.sql")
    @Sql(scripts = "/script/TestChatResourceController/shouldNotAuthorDeleteFromChatOnlyOneUser/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldNotAuthorDeleteFromChatOnlyOneUser() throws Exception {
        mockMvc.perform(delete("/api/user/chat/{id}", 101)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "user1")))
                .andDo(print())
                .andExpect(content().string("GroupChat deleted"))
                .andExpect(status().isOk());
    }
}
