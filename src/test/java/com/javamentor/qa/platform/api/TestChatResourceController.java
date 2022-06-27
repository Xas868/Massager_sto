package com.javamentor.qa.platform.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.SingleChatDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class TestChatResourceController extends AbstractClassForDRRiderMockMVCTests {
    //Тесты для SingleChatDTO авторизированного пользователя
    @Test
    @DataSet(value = "dataset/ChatResourceController/getAllSingleChatDtoByUserId.yml"
            , strategy = SeedStrategy.REFRESH)

    public void shouldGetAllSingleChatDtoByUserId() throws Exception {
        //Проверка что API доступно
        this.mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/user/chat/single")
                    .contentType("application/json")
                    .header("Authorization", "Bearer " + getToken("user1@mail.ru","user1")))
            .andDo(print())
            .andExpect(status().isOk());

        // Проверка на количество всех сингл чатов
        String sqlAll = "select CAST(count(sc.id) as int) from SingleChat sc";
        int countAllSingleChat = (int) entityManager.createQuery(sqlAll).getSingleResult();
        Assertions.assertTrue(countAllSingleChat == 5);

        // Проверка на количество сингл чатов у авторизированного пользователя c id= 1
        String sql = "select CAST(count(sc.id) as int) from SingleChat sc where sc.userOne.id =: userId OR sc.useTwo.id =: userId";
        int countSingleChat = (int) entityManager.createQuery(sql).setParameter("userId", 1L).getSingleResult();
        Assertions.assertTrue(countSingleChat == 3);

        //Проверка на соответствие полей в выборке
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/chat/single")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user1@mail.ru","user1")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1)) // id single chat
                .andExpect(jsonPath("$.[0].name").value("user_3")) // nickname not auth
                .andExpect(jsonPath("$.[0].image").value("avatar3.png")) // image not auth
                .andExpect(jsonPath("$.[0].lastMessage").value("Test message №1")) // last message
                .andExpect(jsonPath("$.[0].persistDateTimeLastMessage").value("2022-06-23T23:02:51.654")) // date message
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].name").value("user_2"))
                .andExpect(jsonPath("$.[1].image").value("avatar2.png"))
                .andExpect(jsonPath("$.[1].lastMessage").value("Test message №3"))
                .andExpect(jsonPath("$.[1].persistDateTimeLastMessage").value("2022-04-07T23:02:51.654"))
                .andExpect(jsonPath("$.[2].id").value(5))
                .andExpect(jsonPath("$.[2].name").value("user_3"))
                .andExpect(jsonPath("$.[2].image").value("avatar3.png"))
                .andExpect(jsonPath("$.[2].lastMessage").value("Test message №5"))
                .andExpect(jsonPath("$.[2].persistDateTimeLastMessage").value("2022-06-22T23:02:51.654"));
    }

}
