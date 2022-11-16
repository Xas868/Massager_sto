package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestChatResourceController extends AbstractClassForDRRiderMockMVCTests {


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

    // Пользователь не добавлен в групповой чат (Чат - существует, Добавляет - автор чата, Пользователь - состоит в чате, Параметр userId - передается)
    @Test
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldBadRequest_whenUserPresent/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldBadRequest_whenUserPresent/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addUserInGroupChat_shouldBadRequest_whenUserPresent() throws Exception {

        mockMvc.perform(post("/api/user/chat/group/{id}/join", 101)
                        .param("userId", "102")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("userPresent")));
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
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "user102"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("This user with id 102 can't invite other users")));
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
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "user102"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("This user with id 102 can't invite other users")));
    }

    // Пользователь не добавлен в групповой чат (Чат - не существует, Добавляет - автор чата, Пользователь - не состоит в чате, Параметр userId - передается)
    @Test
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldBadRequest_whenChatNotExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldBadRequest_whenChatNotExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addUserInGroupChat_shouldBadRequest_whenChatNotExists() throws Exception {

        mockMvc.perform(post("/api/user/chat/group/{id}/join", 150)
                        .param("userId", "102")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("it's bad request")));
    }

    // Пользователь не добавлен в групповой чат (Чат - существует, Добавляет - автор чата, Пользователь - не существует, Параметр userId - передается)
    @Test
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldBadRequest_whenUserNotExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/addUserInGroupChat_shouldBadRequest_whenUserNotExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addUserInGroupChat_shouldBadRequest_whenUserNotExists() throws Exception {

        mockMvc.perform(post("/api/user/chat/group/{id}/join", 101)
                        .param("userId", "150")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("it's bad request")));
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
}
