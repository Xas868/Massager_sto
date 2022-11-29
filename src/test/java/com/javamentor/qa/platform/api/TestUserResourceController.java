package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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

        mockMvc.perform(get("/api/user/{userId}", 121)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user120@mail.ru", "user120"))
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

    // Проверка передачи всех верных данных
    @Test
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldFindAllData_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldFindAllData_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void paginationById_shouldFindAllData_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(10)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)))

                .andExpect(jsonPath("$.items[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user101@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 101")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(1000)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 1")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 3")))

                .andExpect(jsonPath("$.items[9].id", Is.is(110)))
                .andExpect(jsonPath("$.items[9].email", Is.is("user110@mail.ru")))
                .andExpect(jsonPath("$.items[9].fullName", Is.is("User 110")))
                .andExpect(jsonPath("$.items[9].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[9].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[9].reputation", Is.is(100)))

                .andExpect(jsonPath("$.items[9].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[9].listTagDto[0].id", Is.is(110)))
                .andExpect(jsonPath("$.items[9].listTagDto[0].name", Is.is("vfOxMU10")))
                .andExpect(jsonPath("$.items[9].listTagDto[0].description", Is.is("Description of tag 10")))

                .andExpect(jsonPath("$.items[9].listTagDto[1].id", Is.is(111)))
                .andExpect(jsonPath("$.items[9].listTagDto[1].name", Is.is("iThKcj11")))
                .andExpect(jsonPath("$.items[9].listTagDto[1].description", Is.is("Description of tag 11")))

                .andExpect(jsonPath("$.items[9].listTagDto[2].id", Is.is(112)))
                .andExpect(jsonPath("$.items[9].listTagDto[2].name", Is.is("LTGDJP12")))
                .andExpect(jsonPath("$.items[9].listTagDto[2].description", Is.is("Description of tag 12")));
    }

    // Проверка пагинации - верные данные (currentPage положительный, items положительный)
    @Test
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldFindAllDataPaginationData_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldFindAllDataPaginationData_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void paginationById_shouldFindAllDataPaginationData_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/new")
                        .param("page", "2")
                        .param("items", "3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(10)))
                .andExpect(jsonPath("$.items.length()", Is.is(3)))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(3)))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(2)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(104)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user104@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 104")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(700)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(107)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU7")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 7")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(108)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj8")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 8")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(109)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP9")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 9")))

                .andExpect(jsonPath("$.items[1].id", Is.is(105)))
                .andExpect(jsonPath("$.items[1].email", Is.is("user105@mail.ru")))
                .andExpect(jsonPath("$.items[1].fullName", Is.is("User 105")))
                .andExpect(jsonPath("$.items[1].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[1].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[1].reputation", Is.is(600)))

                .andExpect(jsonPath("$.items[1].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[1].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[1].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[1].listTagDto[0].description", Is.is("Description of tag 1")))

                .andExpect(jsonPath("$.items[1].listTagDto[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[1].listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items[1].listTagDto[1].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items[1].listTagDto[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[1].listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items[1].listTagDto[2].description", Is.is("Description of tag 3")))

                .andExpect(jsonPath("$.items[2].id", Is.is(106)))
                .andExpect(jsonPath("$.items[2].email", Is.is("user106@mail.ru")))
                .andExpect(jsonPath("$.items[2].fullName", Is.is("User 106")))
                .andExpect(jsonPath("$.items[2].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[2].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[2].reputation", Is.is(500)))

                .andExpect(jsonPath("$.items[2].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[2].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[2].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[2].listTagDto[0].description", Is.is("Description of tag 1")))

                .andExpect(jsonPath("$.items[2].listTagDto[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[2].listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items[2].listTagDto[1].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items[2].listTagDto[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[2].listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items[2].listTagDto[2].description", Is.is("Description of tag 3")));
    }

    // Проверка сортировки по дате создания юзера.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldPaginationOrderByPersistDate_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldPaginationOrderByPersistDate_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void paginationById_shouldPaginationOrderByPersistDate_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(10)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)))

                .andExpect(jsonPath("$.items[0].id", Is.is(109)))
                .andExpect(jsonPath("$.items[1].id", Is.is(110)))
                .andExpect(jsonPath("$.items[2].id", Is.is(102)))
                .andExpect(jsonPath("$.items[3].id", Is.is(105)))
                .andExpect(jsonPath("$.items[4].id", Is.is(104)))
                .andExpect(jsonPath("$.items[5].id", Is.is(108)))
                .andExpect(jsonPath("$.items[6].id", Is.is(106)))
                .andExpect(jsonPath("$.items[7].id", Is.is(103)))
                .andExpect(jsonPath("$.items[8].id", Is.is(107)))
                .andExpect(jsonPath("$.items[9].id", Is.is(101)));
    }

    // Проверка filter nickname.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldFilterNickname_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldFilterNickname_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void paginationById_shouldFilterNickname_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/new")
                        .param("filter", "user_108")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(1)))
                .andExpect(jsonPath("$.items.length()", Is.is(1)))

                .andExpect(jsonPath("$.items[0].id", Is.is(108)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user108@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 108")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(300)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(104)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU4")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 4")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(105)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj5")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 5")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(106)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP6")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 6")));
    }

    // Проверка filter email.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldFilterEmail_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldFilterEmail_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void paginationById_shouldFilterEmail_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/new")
                        .param("filter", "user110@mail.ru")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(1)))
                .andExpect(jsonPath("$.items.length()", Is.is(1)))

                .andExpect(jsonPath("$.items[0].id", Is.is(110)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user110@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 110")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(100)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(110)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU10")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 10")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(111)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj11")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 11")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(112)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP12")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 12")));
    }

    // Проверка filter fullName.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldFilterFullName_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldFilterFullName_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void paginationById_shouldFilterFullName_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/new")
                        .param("filter", "User 103")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(1)))
                .andExpect(jsonPath("$.items.length()", Is.is(1)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user103@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 103")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(800)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 1")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 3")));
    }

    // Проверка сортировки по дате создания с filter (nickname) несколько значений.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldPaginationFilterNickname_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldPaginationFilterNickname_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void paginationById_shouldPaginationFilterNickname_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/new")
                        .param("filter", "admin_")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(4)))
                .andExpect(jsonPath("$.items.length()", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(102)))
                .andExpect(jsonPath("$.items[1].id", Is.is(105)))
                .andExpect(jsonPath("$.items[2].id", Is.is(104)))
                .andExpect(jsonPath("$.items[3].id", Is.is(103)));
    }

    // Проверка сортировки по дате создания с filter (email) несколько значений.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldPaginationFilterEmail_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldPaginationFilterEmail_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void paginationById_shouldPaginationFilterEmail_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/new")
                        .param("filter", "adminmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(4)))
                .andExpect(jsonPath("$.items.length()", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(102)))
                .andExpect(jsonPath("$.items[1].id", Is.is(105)))
                .andExpect(jsonPath("$.items[2].id", Is.is(104)))
                .andExpect(jsonPath("$.items[3].id", Is.is(103)));
    }

    // Проверка сортировки по дате создания с filter (fullName) несколько значений.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldPaginationFilterFullName_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldPaginationFilterFullName_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void paginationById_shouldPaginationFilterFullName_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/new")
                        .param("filter", "Admin ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(4)))
                .andExpect(jsonPath("$.items.length()", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(102)))
                .andExpect(jsonPath("$.items[1].id", Is.is(105)))
                .andExpect(jsonPath("$.items[2].id", Is.is(104)))
                .andExpect(jsonPath("$.items[3].id", Is.is(103)));
    }

    // Пользователь передает не существующее значение в filter.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldPaginationEmpty_whenFilterEmpty/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/paginationById_shouldPaginationEmpty_whenFilterEmpty/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void paginationById_shouldPaginationEmpty_whenFilterEmpty() throws Exception {

        mockMvc.perform(get("/api/user/new")
                        .param("filter", "Иван Иванов")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.totalResultCount", Is.is(0)))
                .andExpect(jsonPath("$.items.length()", Is.is(0)));
    }

    // Проверка передачи всех верных данных
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldFindAllData_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldFindAllData_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserPaginationByReputation_shouldFindAllData_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/reputation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(10)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)))

                .andExpect(jsonPath("$.items[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user101@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 101")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(1000)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 1")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 3")))

                .andExpect(jsonPath("$.items[9].id", Is.is(110)))
                .andExpect(jsonPath("$.items[9].email", Is.is("user110@mail.ru")))
                .andExpect(jsonPath("$.items[9].fullName", Is.is("User 110")))
                .andExpect(jsonPath("$.items[9].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[9].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[9].reputation", Is.is(100)))

                .andExpect(jsonPath("$.items[9].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[9].listTagDto[0].id", Is.is(110)))
                .andExpect(jsonPath("$.items[9].listTagDto[0].name", Is.is("vfOxMU10")))
                .andExpect(jsonPath("$.items[9].listTagDto[0].description", Is.is("Description of tag 10")))

                .andExpect(jsonPath("$.items[9].listTagDto[1].id", Is.is(111)))
                .andExpect(jsonPath("$.items[9].listTagDto[1].name", Is.is("iThKcj11")))
                .andExpect(jsonPath("$.items[9].listTagDto[1].description", Is.is("Description of tag 11")))

                .andExpect(jsonPath("$.items[9].listTagDto[2].id", Is.is(112)))
                .andExpect(jsonPath("$.items[9].listTagDto[2].name", Is.is("LTGDJP12")))
                .andExpect(jsonPath("$.items[9].listTagDto[2].description", Is.is("Description of tag 12")));
    }

    // Проверка сортировки по репутации
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldSortedByReputation_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldSortedByReputation_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserPaginationByReputation_shouldSortedByReputation_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/reputation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(10)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(9000)))

                .andExpect(jsonPath("$.items[1].id", Is.is(107)))
                .andExpect(jsonPath("$.items[1].reputation", Is.is(6600)))

                .andExpect(jsonPath("$.items[2].id", Is.is(105)))
                .andExpect(jsonPath("$.items[2].reputation", Is.is(4900)))

                .andExpect(jsonPath("$.items[3].id", Is.is(110)))
                .andExpect(jsonPath("$.items[3].reputation", Is.is(3300)))

                .andExpect(jsonPath("$.items[4].id", Is.is(106)))
                .andExpect(jsonPath("$.items[4].reputation", Is.is(1000)))

                .andExpect(jsonPath("$.items[5].id", Is.is(101)))
                .andExpect(jsonPath("$.items[5].reputation", Is.is(1000)))

                .andExpect(jsonPath("$.items[6].id", Is.is(102)))
                .andExpect(jsonPath("$.items[6].reputation", Is.is(800)))

                .andExpect(jsonPath("$.items[7].id", Is.is(104)))
                .andExpect(jsonPath("$.items[7].reputation", Is.is(700)))

                .andExpect(jsonPath("$.items[8].id", Is.is(109)))
                .andExpect(jsonPath("$.items[8].reputation", Is.is(400)))

                .andExpect(jsonPath("$.items[9].id", Is.is(108)))
                .andExpect(jsonPath("$.items[9].reputation", Is.is(200)));
    }

    // Проверка filter nickname.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldFilterNickname_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldFilterNickname_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserPaginationByReputation_shouldFilterNickname_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/reputation")
                        .param("filter", "user_108")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(1)))
                .andExpect(jsonPath("$.items.length()", Is.is(1)))

                .andExpect(jsonPath("$.items[0].id", Is.is(108)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user108@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 108")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(300)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(104)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU4")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 4")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(105)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj5")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 5")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(106)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP6")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 6")));
    }

    // Проверка filter email.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldFilterEmail_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldFilterEmail_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserPaginationByReputation_shouldFilterEmail_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/reputation")
                        .param("filter", "user110@mail.ru")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(1)))
                .andExpect(jsonPath("$.items.length()", Is.is(1)))

                .andExpect(jsonPath("$.items[0].id", Is.is(110)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user110@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 110")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(100)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(110)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU10")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 10")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(111)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj11")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 11")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(112)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP12")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 12")));
    }

    // Проверка filter fullName.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldFilterFullName_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldFilterFullName_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserPaginationByReputation_shouldFilterFullName_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/reputation")
                        .param("filter", "User 103")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(1)))
                .andExpect(jsonPath("$.items.length()", Is.is(1)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user103@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 103")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(800)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 1")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 3")));
    }

    // Проверка сортировки по репутации с filter (nickname) несколько значений.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldPaginationFilterNickname_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldPaginationFilterNickname_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserPaginationByReputation_shouldPaginationFilterNickname_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/reputation")
                        .param("filter", "admin_")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(4)))
                .andExpect(jsonPath("$.items.length()", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].email", Is.is("adminmail103@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("Admin 103")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(1800)))

                .andExpect(jsonPath("$.items[1].id", Is.is(105)))
                .andExpect(jsonPath("$.items[1].email", Is.is("adminmail105@mail.ru")))
                .andExpect(jsonPath("$.items[1].fullName", Is.is("Admin 105")))
                .andExpect(jsonPath("$.items[1].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[1].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[1].reputation", Is.is(1600)))

                .andExpect(jsonPath("$.items[2].id", Is.is(102)))
                .andExpect(jsonPath("$.items[2].email", Is.is("adminmail102@mail.ru")))
                .andExpect(jsonPath("$.items[2].fullName", Is.is("Admin 102")))
                .andExpect(jsonPath("$.items[2].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[2].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[2].reputation", Is.is(900)))

                .andExpect(jsonPath("$.items[3].id", Is.is(104)))
                .andExpect(jsonPath("$.items[3].email", Is.is("adminmail104@mail.ru")))
                .andExpect(jsonPath("$.items[3].fullName", Is.is("Admin 104")))
                .andExpect(jsonPath("$.items[3].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[3].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[3].reputation", Is.is(700)));
    }

    // Проверка сортировки по репутации с filter (email) несколько значений.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldPaginationFilterEmail_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldPaginationFilterEmail_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserPaginationByReputation_shouldPaginationFilterEmail_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/reputation")
                        .param("filter", "adminmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(4)))
                .andExpect(jsonPath("$.items.length()", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].email", Is.is("adminmail103@mail.ru")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(1800)))

                .andExpect(jsonPath("$.items[1].id", Is.is(105)))
                .andExpect(jsonPath("$.items[1].email", Is.is("adminmail105@mail.ru")))
                .andExpect(jsonPath("$.items[1].reputation", Is.is(1600)))

                .andExpect(jsonPath("$.items[2].id", Is.is(102)))
                .andExpect(jsonPath("$.items[2].email", Is.is("adminmail102@mail.ru")))
                .andExpect(jsonPath("$.items[2].reputation", Is.is(900)))

                .andExpect(jsonPath("$.items[3].id", Is.is(104)))
                .andExpect(jsonPath("$.items[3].email", Is.is("adminmail104@mail.ru")))
                .andExpect(jsonPath("$.items[3].reputation", Is.is(700)));
    }

    // Проверка сортировки по репутации с filter (fullName) несколько значений.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldPaginationFilterFullName_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldPaginationFilterFullName_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserPaginationByReputation_shouldPaginationFilterFullName_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/reputation")
                        .param("filter", "Admin ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(4)))
                .andExpect(jsonPath("$.items.length()", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("Admin 103")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(1800)))

                .andExpect(jsonPath("$.items[1].id", Is.is(105)))
                .andExpect(jsonPath("$.items[1].fullName", Is.is("Admin 105")))
                .andExpect(jsonPath("$.items[1].reputation", Is.is(1600)))

                .andExpect(jsonPath("$.items[2].id", Is.is(102)))
                .andExpect(jsonPath("$.items[2].fullName", Is.is("Admin 102")))
                .andExpect(jsonPath("$.items[2].reputation", Is.is(900)))

                .andExpect(jsonPath("$.items[3].id", Is.is(104)))
                .andExpect(jsonPath("$.items[3].fullName", Is.is("Admin 104")))
                .andExpect(jsonPath("$.items[3].reputation", Is.is(700)));
    }

    // Пользователь передает не существующее значение в filter.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldPaginationEmpty_whenFilterEmpty/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getAllUserPaginationByReputation_shouldPaginationEmpty_whenFilterEmpty/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUserPaginationByReputation_shouldPaginationEmpty_whenFilterEmpty() throws Exception {

        mockMvc.perform(get("/api/user/reputation")
                        .param("filter", "Иван Иванов")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.totalResultCount", Is.is(0)))
                .andExpect(jsonPath("$.items.length()", Is.is(0)));
    }

    // Проверка передачи всех верных данных
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldFindAllData_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldFindAllData_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldFindAllData_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(10)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)))

                .andExpect(jsonPath("$.items[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user101@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 101")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(1000)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 1")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 3")))

                .andExpect(jsonPath("$.items[9].id", Is.is(110)))
                .andExpect(jsonPath("$.items[9].email", Is.is("user110@mail.ru")))
                .andExpect(jsonPath("$.items[9].fullName", Is.is("User 110")))
                .andExpect(jsonPath("$.items[9].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[9].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[9].reputation", Is.is(100)))

                .andExpect(jsonPath("$.items[9].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[9].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[9].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[9].listTagDto[0].description", Is.is("Description of tag 1")))

                .andExpect(jsonPath("$.items[9].listTagDto[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[9].listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items[9].listTagDto[1].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items[9].listTagDto[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[9].listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items[9].listTagDto[2].description", Is.is("Description of tag 3")));
    }

    // Проверка сортировки по сумме голосов
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldSortedByVoteAsc_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldSortedByVoteAsc_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldSortedByVoteAsc_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(10)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)))

                .andExpect(jsonPath("$.items[0].id", Is.is(109)))
                .andExpect(jsonPath("$.items[1].id", Is.is(110)))
                .andExpect(jsonPath("$.items[2].id", Is.is(102)))
                .andExpect(jsonPath("$.items[3].id", Is.is(103)))
                .andExpect(jsonPath("$.items[4].id", Is.is(104)))
                .andExpect(jsonPath("$.items[5].id", Is.is(105)))
                .andExpect(jsonPath("$.items[6].id", Is.is(106)))
                .andExpect(jsonPath("$.items[7].id", Is.is(107)))
                .andExpect(jsonPath("$.items[8].id", Is.is(108)))
                .andExpect(jsonPath("$.items[9].id", Is.is(101)));
    }

    // Проверка filter nickname.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldFilterNickname_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldFilterNickname_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldFilterNickname_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .param("filter", "user_108")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(1)))
                .andExpect(jsonPath("$.items.length()", Is.is(1)))

                .andExpect(jsonPath("$.items[0].id", Is.is(108)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user108@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 108")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(300)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(104)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU4")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 4")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(105)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj5")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 5")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(106)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP6")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 6")));
    }

    // Проверка filter email.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldFilterEmail_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldFilterEmail_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldFilterEmail_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .param("filter", "user110@mail.ru")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(1)))
                .andExpect(jsonPath("$.items.length()", Is.is(1)))

                .andExpect(jsonPath("$.items[0].id", Is.is(110)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user110@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 110")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(100)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 1")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 3")));
    }

    // Проверка filter fullName.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldFilterFullName_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldFilterFullName_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldFilterFullName_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .param("filter", "User 103")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(1)))
                .andExpect(jsonPath("$.items.length()", Is.is(1)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user103@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 103")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(8000)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 1")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 3")));
    }

    // Проверка сортировки по сумме голосов с filter (nickname) несколько значений.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldPaginationFilterNickname_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldPaginationFilterNickname_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldPaginationFilterNickname_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .param("filter", "admin_")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(4)))
                .andExpect(jsonPath("$.items.length()", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[2].id", Is.is(105)))
                .andExpect(jsonPath("$.items[3].id", Is.is(104)));
    }

    // Проверка сортировки по сумме голосов с filter (email) несколько значений.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldPaginationFilterEmail_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldPaginationFilterEmail_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldPaginationFilterEmail_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .param("filter", "adminmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(4)))
                .andExpect(jsonPath("$.items.length()", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[2].id", Is.is(105)))
                .andExpect(jsonPath("$.items[3].id", Is.is(104)));
    }

    // Проверка сортировки по сумме голосов с filter (fullName) несколько значений.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldPaginationFilterFullName_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldPaginationFilterFullName_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldPaginationFilterFullName_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .param("filter", "Admin ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(4)))
                .andExpect(jsonPath("$.items.length()", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[2].id", Is.is(105)))
                .andExpect(jsonPath("$.items[3].id", Is.is(104)));
    }

    // Пользователь передает не существующее значение в filter.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldPaginationEmpty_whenFilterEmpty/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldPaginationEmpty_whenFilterEmpty/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldPaginationEmpty_whenFilterEmpty() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .param("filter", "Иван Иванов")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getToken("user101@mail.ru", "user101"))
                )
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.totalResultCount", Is.is(0)))
                .andExpect(jsonPath("$.items.length()", Is.is(0)));
    }


    //Смена пароля
    @Test
    @Sql(scripts = "/script/TestUserResourceController/changePassword/Before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/changePassword/After.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void changePassword() throws Exception {

        mockMvc.perform(patch("/api/user/change/password")
                        .param("password", "46xEPoAOu")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk());

    }
    //Получение всех вопросов авторизированного пользователя
    //идеальный вариант, есть глобальный чат, получаем все сообщения, включающие текст "essa", сверяем порядок создания
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getAllUserProfileQuestionDtoById/Before.sql")
    @Sql(scripts = "/script/TestUserResourceController/getAllUserProfileQuestionDtoById/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllUserProfileQuestionDtoById() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/profile/questions")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isOk());

    }

    //Получение всех удаленных вопросов в виде UserProfileQuestionDto
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getAllUserProfileQuestionDtoByUserIdIsDelete/Before.sql")
    @Sql(scripts = "/script/TestUserResourceController/getAllUserProfileQuestionDtoByUserIdIsDelete/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllUserProfileQuestionDtoByUserIdIsDelete() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/profile/delete/questions")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user101@mail.ru", "user101")))
                .andDo(print())
                .andExpect(status().isOk());

    }


    //Получение всех закладок в профиле пользователя в виде BookMarksDto
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getAllBookMarksInUserProfile/Before.sql")
    @Sql(scripts = "/script/TestUserResourceController/getAllBookMarksInUserProfile/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllBookMarksInUserProfile() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/profile/bookmarks")
                        .contentType("application/json")
                        .header("Authorization",
                                "Bearer " + getToken("user100@mail.ru", "user100")))
                .andDo(print())
                .andExpect(status().isOk());

    }


}
