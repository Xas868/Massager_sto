package com.javamentor.qa.platform.api;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TestMessageResourceController extends AbstractClassForDRRiderMockMVCTests {
    private static final String TEST_URL = "/api/user/message/star";
    @Test
    @DataSet(cleanBefore = true,
            value = {"dataset/MessageResourceController/users.yml",
                    "dataset/MessageResourceController/messages.yml"},
            strategy = SeedStrategy.REFRESH)
    public void testAddMessageToStarMessagesMethod() throws Exception {
        String token = getToken("user1@mail.ru", "user1");
        // Попытка отправить пустой пост запрос - ожидаемый статус: 400
        mockMvc.perform(post(TEST_URL)
                .contentType("application/json")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
        // Попытка отправить пост запрос с некорректным полем(сообщение с ID 1000 не существует) - ожидаемый статус: 400
        mockMvc.perform(post(TEST_URL)
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .content("{\"id\":1000}"))
                .andExpect(status().isBadRequest());
        // Попытка отправить корректный пост запрос(сообщение с ID 1 существует) - ожидаемый статус: 200
        mockMvc.perform(post(TEST_URL)
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .content("{\"id\":1}"))
                .andExpect(status().isOk());
    }
}
