package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestChatResourceController extends AbstractClassForDRRiderMockMVCTests {
    // Все хорошо, пользователь добавлен в group чат
    @Test
    @Sql("/script/TestChatResourceController/shouldAddUserInGroupChat/Before.sql")
    @Sql(scripts = "/script/TestChatResourceController/shouldAddUserInGroupChat/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldAddUserInGroupChat() throws Exception {
        mockMvc.perform(post("/api/user/chat/group/{id}/join", 101)
                        .param("userId","103")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(entityManager.createNativeQuery(" select c from Groupchat_has_users c where c.user_id = :id")
                .setParameter("id", (long) 103)
                .getResultList()
                .isEmpty())
                .isEqualTo(false);
    }
    // добавление несуществующего пользователя
    @Test
    @Sql("/script/TestChatResourceController/shouldError400UserIdInvalid/Before.sql")
    @Sql(scripts = "/script/TestChatResourceController/shouldError400UserIdInvalid/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldError400UserIdInvalid() throws Exception {
        mockMvc.perform(post("/api/user/chat/group/{id}/join", 101)
                        .param("userId", "110")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "user1")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
