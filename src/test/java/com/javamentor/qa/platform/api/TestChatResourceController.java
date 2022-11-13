package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TestChatResourceController extends AbstractClassForDRRiderMockMVCTests {

    //Если чат удаляет автор, то чат удаляется с пользователями
    @Test
    @Sql("/script/TestChatResourceController/shouldAuthorDeleteChatAll/Before.sql")
    @Sql(scripts = "/script/TestChatResourceController/shouldAuthorDeleteChatAll/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldAuthorDeleteChatAll() throws Exception {
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

    //Если пользователь не автор пытается удалить чат, то он просто выходит из него
    @Test
    @Sql("/script/TestChatResourceController/shouldExitUserFromChat/Before.sql")
    @Sql(scripts = "/script/TestChatResourceController/shouldExitUserFromChat/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldExitUserFromChat() throws Exception {
        mockMvc.perform(delete("/api/user/chat/{id}", 101)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user103@mail.ru", "user1")))
                .andDo(print())
                .andExpect(content().string("GroupChat deleted"))
                .andExpect(status().isOk());
    }
}
