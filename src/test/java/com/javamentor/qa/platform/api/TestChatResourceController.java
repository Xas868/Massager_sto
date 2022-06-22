package com.javamentor.qa.platform.api;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestChatResourceController extends AbstractClassForDRRiderMockMVCTests {
    //Получаем все Сингл Чаты юзера
    @Test
    @DataSet(value = "dataset/ChatResourceController/getAllSingleChatDtoByUserId.yml"
            , strategy = SeedStrategy.REFRESH)
    public void shouldGetAllSingleChatDtoByUserId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/user/chat/single")
                    .contentType("application/json")
                    .header("Authorization", "Bearer " + getToken("user1@mail.ru","user1")))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("[]"));


    }
}
