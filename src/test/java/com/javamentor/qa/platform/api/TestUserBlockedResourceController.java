package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestUserBlockedResourceController extends AbstractClassForDRRiderMockMVCTests {


    // вывод 3 заблокированных пользваотелей

    @Test
    @Sql(scripts = "/script/TestUserBlockedResourceController/shouldGetAllBlockedUsers_WhenExist/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserBlockedResourceController/shouldGetAllBlockedUsers_WhenExist/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldGetAllBlockedUsers_WhenExist() throws Exception {
        mockMvc.perform(get("/api/user/block")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Is.is(3)));

    }

    // не должен выводить заблокированных пользователей, когда их нет

    @Test
    @Sql(scripts = "/script/TestUserBlockedResourceController/shouldNotGetBlockedUsers_WhenNotExist/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserBlockedResourceController/shouldNotGetBlockedUsers_WhenNotExist/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void ShouldNotGetBlockedUsers_WhenNotExist() throws Exception {
        mockMvc.perform(get("/api/user/block")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Is.is(0)));

    }


    @Test
    @Sql(scripts = "/script/TestUserBlockedResourceController/deleteBlockedUserByUserId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserBlockedResourceController/deleteBlockedUserByUserId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteBlockedUserByUserId() throws Exception{
        mockMvc.perform(delete("/api/user/{userId}/block", 102)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(entityManager
                .createQuery("select ub.id from BlockChatUserList ub where ub.profile = 101 and ub.blocked =102")
                .getResultList())
                .isEmpty();

    }


}
