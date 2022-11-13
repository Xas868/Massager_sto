package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestUserResourceController extends AbstractClassForDRRiderMockMVCTests {

    // Проверка передачи всех верных данных
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUserDtoId_shouldFindAllData_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUserDtoId_shouldFindAllData_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserDtoId_shouldFindAllData_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/{userId}", 101)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Is.is(7)))
                .andExpect(jsonPath("$.id", Is.is(101)))
                .andExpect(jsonPath("$.email", Is.is("user101@mail.ru")))
                .andExpect(jsonPath("$.fullName", Is.is("User 101")))
                .andExpect(jsonPath("$.imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.city", Is.is("Moskow")))
                .andExpect(jsonPath("$.reputation", Is.is(900)))

                .andExpect(jsonPath("$.listTagDto.length()", Is.is(3)))
                .andExpect(jsonPath("$.listTagDto[0].id", Is.is(100)))
                .andExpect(jsonPath("$.listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.listTagDto[0].description", Is.is("Description of tag 1")))

                .andExpect(jsonPath("$.listTagDto[1].id", Is.is(101)))
                .andExpect(jsonPath("$.listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.listTagDto[1].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.listTagDto[2].id", Is.is(102)))
                .andExpect(jsonPath("$.listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.listTagDto[2].description", Is.is("Description of tag 3")));
    }

    // Пользователь авторизован как admin
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUserDtoId_shouldIsForbidden_whenAdmin/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUserDtoId_shouldIsForbidden_whenAdmin/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserDtoId_shouldIsForbidden_whenAdmin() throws Exception {

        mockMvc.perform(get("/api/user/{userId}", 100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    // Пользователь не указывает userId
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUserDtoId_shouldIsForbidden_whenEmptyId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUserDtoId_shouldIsForbidden_whenEmptyId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserDtoId_shouldIsForbidden_whenEmptyId() throws Exception {

        mockMvc.perform(get("/api/user/{userId}", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // Пользователь указывает не существующий userId
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUserDtoId_shouldBadRequest_whenNotExistsId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUserDtoId_shouldBadRequest_whenNotExistsId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserDtoId_shouldMessage_whenNotExistsId() throws Exception {

        mockMvc.perform(get("/api/user/{userId}", 1000)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("Пользователь не найден!")));
    }

    // Пользователь указывает userId равный 0
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUserDtoId_shouldBadRequest_whenZeroId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUserDtoId_shouldBadRequest_whenZeroId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserDtoId_shouldBadRequest_whenZeroId() throws Exception {

        mockMvc.perform(get("/api/user/{userId}", 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("Пользователь не найден!")));
    }

    // Пользователь указывает отрицательный userId
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUserDtoId_shouldBadRequest_whenNegativeNumberId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUserDtoId_shouldBadRequest_whenNegativeNumberId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUserDtoId_shouldBadRequest_whenNegativeNumberId() throws Exception {

        mockMvc.perform(get("/api/user/{userId}", -100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("Пользователь не найден!")));
    }
}
