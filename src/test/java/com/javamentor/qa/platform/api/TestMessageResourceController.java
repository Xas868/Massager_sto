package com.javamentor.qa.platform.api;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TestMessageResourceController extends AbstractClassForDRRiderMockMVCTests {

    @Test
    @DataSet(value = {"dataset/allMessagesInGlobalChat/users.yml",
            "dataset/allMessagesInGlobalChat/messages.yml"
            },
            strategy = SeedStrategy.CLEAN_INSERT)
    public void testGetMessageInGlobalChat() throws Exception {

        String token = "Bearer " + getToken("user1@mail.ru", "user1");

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/message/global")
                        .contentType("application/json")
                        .header("Authorization", token)

                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id").value(5))
                .andExpect(jsonPath("$.items[0].message").value("test_message_5"))
                .andExpect(jsonPath("$.items[1].id").value(4))
                .andExpect(jsonPath("$.items[1].message").value("test_message_4"))
                .andExpect(jsonPath("$.items[2].id").value(2))
                .andExpect(jsonPath("$.items[2].message").value("test_message_2"));
    }

}
