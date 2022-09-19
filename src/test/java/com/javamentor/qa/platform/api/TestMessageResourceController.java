package com.javamentor.qa.platform.api;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TestMessageResourceController extends AbstractClassForDRRiderMockMVCTests {

    private static final String TEST_URL = "/api/user/message/star";

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true,
            value = {"dataset/MessageResourceController/users.yml",
                    "dataset/MessageResourceController/messages.yml"},
            strategy = SeedStrategy.REFRESH)
    @ExpectedDataSet(value = "dataset/MessageResourceController/expected/message_star.yml")
    public void testAddMessageToStarMessagesMethod() throws Exception {
        String token = getToken("user1@mail.ru", "user1");
        // Попытка отправить пустой пост запрос
        mockMvc.perform(post(TEST_URL)
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
        // Попытка отправить пост запрос с некорректным полем(сообщение с ID 1000 не существует)
        mockMvc.perform(post(TEST_URL)
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + token)
                        .content("1000"))
                .andExpect(status().isBadRequest());
        // Попытка отправить корректный пост запрос(сообщение с ID 100 существует)
        mockMvc.perform(post(TEST_URL)
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + token)
                        .content("1"))
                .andExpect(status().isOk());
        // Попытка добавить то же сообщение в избранное тем же пользователем
        mockMvc.perform(post(TEST_URL)
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + token)
                        .content("1"))
                .andExpect(status().isBadRequest());
        // Попытка отправить что-то что не число
        mockMvc.perform(post(TEST_URL)
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + token)
                        .content("string"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DataSet(value = {"dataset/MessageResourceController/delete_message_star.yml"},
            cleanBefore = true, cleanAfter = true,
            strategy = SeedStrategy.REFRESH)
    public void testDeleteMessageStarByMessageId() throws Exception {
        String token = getToken("user1@mail.ru", "user1");
        // Проверка того, что сообщение не найдено в избранных
        this.mockMvc.perform(delete(TEST_URL)
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + token)
                        .content("wrongId"))
                .andExpect(status().isBadRequest());
        // Попытка отправить пустой запрос
        this.mockMvc.perform(delete(TEST_URL)
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
        // Проверка того, что сообщение удалено из избранных
        this.mockMvc.perform(delete(TEST_URL)
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + token)
                        .content("101"))
                .andExpect(status().isOk());

        assertThat(Objects.equals(null, (entityManager.createQuery(
                        "SELECT ms from MessageStar ms WHERE ms.id =: id")
                .setParameter("id", (long) 101)
                .getResultList().stream().findFirst().orElse(null)))).isTrue();

    }
    @Test
    @DataSet(cleanBefore = true, cleanAfter = true,
            value = {"dataset/MessageResourceController/testGetAllMessageInGlobalChat/users.yml",
                    "dataset/MessageResourceController/testGetAllMessageInGlobalChat/messages.yml"
            },
            strategy = SeedStrategy.REFRESH)
    public void testGetMessageInGlobalChat() throws Exception {

        String token = getToken("user1@mail.ru", "user1");

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/message/global")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + token)

                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id").value(2)) // id message
                .andExpect(jsonPath("$.items[0].message").value("test_message_102"))
                .andExpect(jsonPath("$.items[1].id").value(4))
                .andExpect(jsonPath("$.items[1].message").value("test_message_104"));
    }

}
