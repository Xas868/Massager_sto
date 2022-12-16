package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.user.User;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                                "Bearer " + getToken("user122@mail.ru", "102")))
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

    // Пользователь добавлен в групповой чат (Чат - существует, Добавляет - автор чата, Пользователь - не состоит в чате, Параметр userId - передается)
    @Test
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldAddUserInChat_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldAddUserInChat_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addUserInGroupChat_shouldAddUserInChat_whenExists() throws Exception {

        mockMvc.perform(post("/api/user/chat/group/{id}/join", 101)
                        .param("userId", "102")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is("userAdded")));
    }

    // Пользователь не добавлен в групповой чат (Чат - существует, Добавляет - автор чата, Пользователь - не состоит в чате, Параметр userId - не передается)
    @Test
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldBadRequest_whenParameterNotPass/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldBadRequest_whenParameterNotPass/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addUserInGroupChat_shouldBadRequest_whenParameterNotPass() throws Exception {

        mockMvc.perform(post("/api/user/chat/group/{id}/join", 101)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // Пользователь не добавлен в групповой чат (Чат - существует, Добавляет - не автор чата, Пользователь - не состоит в чате, Параметр userId - передается)
    @Test
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldBadRequest_whenChatAuthorNotAdd/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldBadRequest_whenChatAuthorNotAdd/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addUserInGroupChat_shouldBadRequest_whenChatAuthorNotAdd() throws Exception {

        mockMvc.perform(post("/api/user/chat/group/{id}/join", 101)
                        .param("userId", "103")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user112@mail.ru", "user102"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("This user with id 112 can't invite other users")));
    }

    // Пользователь не добавлен в групповой чат (Чат - существует, Добавляет - не автор чата, Пользователь - состоит в чате, Параметр userId - передается)
    @Test
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldBadRequest_whenChatAuthorNotAddAndUserPresent/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldBadRequest_whenChatAuthorNotAddAndUserPresent/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addUserInGroupChat_shouldBadRequest_whenChatAuthorNotAddAndUserPresent() throws Exception {

        mockMvc.perform(post("/api/user/chat/group/{id}/join", 101)
                        .param("userId", "103")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user112@mail.ru", "user102"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("This user with id 112 can't invite other users")));
    }

    // Пользователь добавлен в групповой чат (Чат - существует, Добавляет - пользователь с ролью Админ(состоит в чате и является Автором чата), Пользователь - не состоит в чате, Параметр userId - передается)
    @Test
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldForbidden_whenChatAuthorAdmin/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldForbidden_whenChatAuthorAdmin/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addUserInGroupChat_shouldForbidden_whenChatAuthorAdmin() throws Exception {

        mockMvc.perform(post("/api/user/chat/group/{id}/join", 101)
                        .param("userId", "122")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user121@mail.ru", "user121"))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    // Пользователь добавлен в групповой чат (Чат - существует, Добавляет - пользователь с ролью Админ(не является Автором чата), Пользователь - не состоит в чате, Параметр userId - передается)
    @Test
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldForbidden_whenAdmin/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldForbidden_whenAdmin/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addUserInGroupChat_shouldForbidden_whenAdmin() throws Exception {

        mockMvc.perform(post("/api/user/chat/group/{id}/join", 101)
                        .param("userId", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user121@mail.ru", "user121"))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
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

    //Если чат удаляет автор, то чат удаляется с пользователями
    @Test
    @Sql("/script/TestChatResourceController/shouldAuthorDeleteChat/Before.sql")
    @Sql(scripts = "/script/TestChatResourceController/shouldAuthorDeleteChat/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldAuthorDeleteChat() throws Exception {
        mockMvc.perform(delete("/api/user/chat/{id}", 101)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "user1")))
                .andDo(print())
                .andExpect(content().string("GroupChat deleted"))
                .andExpect(status().isOk());
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

    // Количество сообщений (items), Номер текущей страницы (currentPage), Слово или часть слова (word).
    // 3 сообщения на одной странице и без необязательного параметра items
    @Test
    @Sql("script/testChatResourceController/shouldGetPageMessageByWord/Before.sql")
    @Sql(scripts = "script/testChatResourceController/shouldGetPageMessageByWord/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetPageMessageByWord() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/chat/{id}/message/find", 101)
                        .param("currentPage", "1")
                        .param("word", "mes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber").value(1))
                .andExpect(jsonPath("$.totalPageCount").value(1))
                .andExpect(jsonPath("$.totalResultCount").value(3))
                .andExpect(jsonPath("$.items[0].id").value(101))
                .andExpect(jsonPath("$.items[1].id").value(102))
                .andExpect(jsonPath("$.items[2].id").value(103));
    }

    // 2 сообщения на одной странице и 1 сообщение на другой странице, текущая страница вторая
    @Test
    @Sql("script/testChatResourceController/shouldGetTwoPageOneMessageByWord/Before.sql")
    @Sql(scripts = "script/testChatResourceController/shouldGetTwoPageOneMessageByWord/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldGetTwoPageOneMessageByWord() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/chat/{id}/message/find", 101)
                        .param("items", "2")
                        .param("currentPage", "2")
                        .param("word", "message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber").value(2))
                .andExpect(jsonPath("$.totalPageCount").value(2))
                .andExpect(jsonPath("$.totalResultCount").value(3))
                .andExpect(jsonPath("$.items[0].id").value(103));
    }

    // ошибка 400 не указан обязательный параметр
    @Test
    @Sql("script/testChatResourceController/shouldError400NotParamert/Before.sql")
    @Sql(scripts = "script/testChatResourceController/shouldError400NotParamert/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldError400NotParamert() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/chat/{id}/message/find", 101)
                        .param("word", "message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/chat/{id}/message/find", 101)
                        .param("currentPage", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    //Все хорошо. Картинку пытается изменить создатель чата. Картинку может менять только создатель чата
    @Test
    @Sql("/script/TestChatResourceController/shouldUpdateImageGroupChat/Before.sql")
    @Sql(scripts = "/script/TestChatResourceController/shouldUpdateImageGroupChat/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldUpdateImageGroupChat() throws Exception {
        mockMvc.perform(put("/api/user/chat/{id}/group/image", 114)
                        .content("image")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user110@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isOk());
        GroupChat groupChat1 = (GroupChat) entityManager.createQuery("from GroupChat c where c.id = :id")
                .setParameter("id", (long) 114).getResultList().get(0);
        assertThat(groupChat1.getImage()).isEqualTo("image");
    }

    // Картинку пытается изменить не создатель чата. Картинку может менять только создатель чата
    @Test
    @Sql("/script/TestChatResourceController/shouldNotUpdateImageGroupChat/Before.sql")
    @Sql(scripts = "/script/TestChatResourceController/shouldNotUpdateImageGroupChat/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldNotUpdateImageGroupChat() throws Exception {
        mockMvc.perform(put("/api/user/chat/{id}/group/image", 115)
                        .content("image")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user110@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    // Пользователь добавлен в групповой чат
    @Test
    @Sql(scripts = "/script/TestChatResourceController/shouldAddUserInGroupChat/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/shouldAddUserInGroupChat/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldAddUserInGroupChat() throws Exception {

        mockMvc.perform(post("/api/user/chat/group/{id}/join", 120)
                        .param("userId", "124")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user110@mail.ru", "user1"))
                )
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(entityManager.createQuery
                        (" select u from  GroupChat as gc join gc.users u where u.id =: id AND gc.id =: chatId  ", User.class)
                .setParameter("id", (long) 124)
                .setParameter("chatId", (long) 120).getResultList().isEmpty())
                .isEqualTo(false);


    }

    // Пользователь не добавлен в групповой чат ( Пользователь уже сущетсвует в групп чате)
    @Test
    @Sql(scripts = "/script/TestChatResourceController/shouldError400WhenNotPass/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/shouldError400WhenNotPass/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldError400WhenNotPass() throws Exception {

        mockMvc.perform(post("/api/user/chat/group/{id}/join", 101)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    // Пользователь не добавлен в групповой чат ("юзер с ролью admin пытается добавить пользователя в групп чат")
    @Test
    @Sql(scripts = "/script/TestChatResourceController/shouldAddUserInGroupChatWhenAuthorAdmin/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/shouldAddUserInGroupChatWhenAuthorAdmin/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldAddUserInGroupChatWhenAuthorAdmin() throws Exception {

        mockMvc.perform(post("/api/user/chat/group/{id}/join", 120)
                        .param("userId", "110")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user190@mail.ru", "user1"))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    // Пользователь не добавлен в групповой чат ("добавляет пользователя не автор чата")
    @Test
    @Sql(scripts = "/script/TestChatResourceController/shouldErrorWhenNotPassAddNotAuthor/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/shouldErrorWhenNotPassAddNotAuthor/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldErrorWhenNotPassAddNotAuthor() throws Exception {

        mockMvc.perform(post("/api/user/chat/group/{id}/join", 101)
                        .param("userId", "103")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user112@mail.ru", "user102"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("This user with id 112 can't invite other users")));
    }

    // Пользователь не добавлен в групповой чат (добавляет пользователя автор чата, пользователь не существует)
    @Test
    @Sql(scripts = "/script/TestChatResourceController/shouldAddUserInGroupChatWhenNotExist/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/shouldAddUserInGroupChatWhenNotExist/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldAddUserInGroupChatWhenNotExist() throws Exception {

        mockMvc.perform(post("/api/user/chat/group/{id}/join", 101)
                        .param("userId", "150")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());


    }
    // Пользователь не добавлен в групповой чат

    @Test
    @Sql(scripts = "/script/TestChatResourceController/shouldErrorBadRequestWhenUserPresent/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/shouldErrorBadRequestWhenUserPresent/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldErrorBadRequestWhenUserPresent() throws Exception {

        mockMvc.perform(post("/api/user/chat/group/{id}/join", 101)
                        .param("userId", "102")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

    }


    // все хорошо. лист участников групчата получает участник групчата. групчат существует
    @Test
    @Sql("/script/TestChatResourceController/shouldGetUsersListOfGroupChat/Before.sql")
    @Sql(scripts = "/script/TestChatResourceController/shouldGetUsersListOfGroupChat/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldGetUsersListOfGroupChat() throws Exception {
        mockMvc.perform(get("/api/user/chat/{chatId}/users", 114)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user111@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("$.[2].userId").value(110))
                .andExpect(jsonPath("$.[2].fullName").value("user110@mail.ru"))
                .andExpect(jsonPath("$.[2].linkImage").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.[2].roleChatStatus").value("MODERATOR"))
                .andExpect(jsonPath("$.[1].userId").value(111))
                .andExpect(jsonPath("$.[1].fullName").value("user111@mail.ru"))
                .andExpect(jsonPath("$.[1].linkImage").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.[1].roleChatStatus").value("AUTHOR"))
                .andExpect(jsonPath("$.[0].userId").value(124))
                .andExpect(jsonPath("$.[0].fullName").value("user124@mail.ru"))
                .andExpect(jsonPath("$.[0].linkImage").value("/images/saveMe.png"))
                .andExpect(jsonPath("$.[0].roleChatStatus").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.[3].userId").value(148))
                .andExpect(jsonPath("$.[3].fullName").value("user148@mail.ru"))
                .andExpect(jsonPath("$.[3].linkImage").value("/images/noUserAvatar.png"))
                .andExpect(jsonPath("$.[3].roleChatStatus").value(IsNull.nullValue()));
    }

    // /лист пользователей нельзя получить. авторизированный пользователь не состоит в данном групчате
    @Test
    @Sql("/script/TestChatResourceController/shouldNotGetUsersListOfGroupChat_doesntMemberOfChat/Before.sql")
    @Sql(scripts = "/script/TestChatResourceController/shouldNotGetUsersListOfGroupChat_doesntMemberOfChat/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldNotGetUsersListOfGroupChat_doesntMemberOfChat() throws Exception {
        mockMvc.perform(get("/api/user/chat/{chatId}/users", 116)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user148@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    //лист пользователей нельзя получить. групчат не существует
    @Test
    @Sql("/script/TestChatResourceController/shouldNotGetUsersListOfGroupChat_groupChatDoesntExists/Before.sql")
    @Sql(scripts = "/script/TestChatResourceController/shouldNotGetUsersListOfGroupChat_groupChatDoesntExists/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldNotGetUsersListOfGroupChat_groupChatDoesntExists() throws Exception {
        mockMvc.perform(get("/api/user/chat/{chatId}/users", 150)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user111@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
