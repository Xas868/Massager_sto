package com.javamentor.qa.platform.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.dto.AuthenticationRequest;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestUserResourceController extends AbstractClassForDRRiderMockMVCTests {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @Test
    //Вывод Dto по id
    @DataSet(cleanBefore = true,
            value = {
                    "dataset/testUserResourceController/roles.yml",
                    "dataset/testUserResourceController/users.yml",
                    "dataset/testUserResourceController/reputation.yml",
                    "dataset/testUserResourceController/answers.yml",
                    "dataset/testUserResourceController/questions.yml"
            },
            strategy = SeedStrategy.REFRESH)
    public void getApiUserDtoId() throws Exception {
        this.mockMvc.perform(get("/api/user/102")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "test15")))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("102"))
                .andExpect(jsonPath("$.email").value("user102@mail.ru"))
                .andExpect(jsonPath("$.fullName").value("test 15"))
                .andExpect(jsonPath("$.imageLink").value("photo"))
                .andExpect(jsonPath("$.city").value("Moscow"))
                .andExpect(jsonPath("$.reputation").value(100));
    }

    //Проверяем на не существующий id
    @Test
    @DataSet(cleanBefore = true,
            value = {
                    "dataset/testUserResourceController/roles.yml",
                    "dataset/testUserResourceController/users.yml",
                    "dataset/testUserResourceController/reputation.yml",
                    "dataset/testUserResourceController/answers.yml",
                    "dataset/testUserResourceController/questions.yml"
            },
            strategy = SeedStrategy.REFRESH)
    public void getNotUserDtoId() throws Exception {
        this.mockMvc.perform(get("/api/user/105")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user102@mail.ru", "test15")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    //Получаем новых пользователей
    @Test
    @DataSet(cleanBefore = true,
            value = {
                    "dataset/userresourcecontroller/roles.yml",
                    "dataset/userresourcecontroller/users.yml",
                    "dataset/userresourcecontroller/questions.yml",
                    "dataset/userresourcecontroller/reputations.yml"
            },
            strategy = SeedStrategy.REFRESH)
    public void shouldReturnNewUsers() throws Exception {
        //Получение всех неудалённых пользователей
        this.mockMvc.perform(get("/api/user/new?page=1")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("test15@mail.ru", "test15")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber").value("1"))
                .andExpect(jsonPath("$.totalPageCount").value("1"))
                .andExpect(jsonPath("$.totalResultCount").value("4"))
                .andExpect(jsonPath("$.itemsOnPage").value("4"))
                .andExpect(jsonPath("$.items[0].id").value("100"))
                .andExpect(jsonPath("$.items[1].id").value("101"))
                .andExpect(jsonPath("$.items[1].email").value("test15@mail.ru"))
                .andExpect(jsonPath("$.items[1].fullName").value("test 101"))
                .andExpect(jsonPath("$.items[1].imageLink").value("photo"))
                .andExpect(jsonPath("$.items[1].city").value("Moscow"))
                .andExpect(jsonPath("$.items[1].reputation").value("500"))
                .andExpect(jsonPath("$.items[2].id").value("103"))
                .andExpect(jsonPath("$.items[3].id").value("104"));

        //Получение всех пользователей со строкой "15" в имени или почте
        this.mockMvc.perform(get("/api/user/new?page=1&filter=15")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("test15@mail.ru", "test15")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber").value("1"))
                .andExpect(jsonPath("$.totalPageCount").value("1"))
                .andExpect(jsonPath("$.totalResultCount").value("2"))
                .andExpect(jsonPath("$.itemsOnPage").value("2"))
                .andExpect(jsonPath("$.items[0].id").value("101"))
                .andExpect(jsonPath("$.items[0].email").value("test15@mail.ru"))
                .andExpect(jsonPath("$.items[1].id").value("103"))
                .andExpect(jsonPath("$.items[1].fullName").value("test 103 15"));

        //Получение пользователя без репутации со строкой "103" в имени или почте
        this.mockMvc.perform(get("/api/user/new?page=1&filter=103")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("test15@mail.ru", "test15")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber").value("1"))
                .andExpect(jsonPath("$.totalPageCount").value("1"))
                .andExpect(jsonPath("$.totalResultCount").value("1"))
                .andExpect(jsonPath("$.itemsOnPage").value("1"))
                .andExpect(jsonPath("$.items[0].id").value("103"))
                .andExpect(jsonPath("$.items[0].email").value("test103@mail.ru"))
                .andExpect(jsonPath("$.items[0].fullName").value("test 103 15"))
                .andExpect(jsonPath("$.items[0].reputation").value(nullValue()));

        //Попытка получения удалённого пользователя со строкой "102" в почте
        this.mockMvc.perform(get("/api/user/new?page=1&filter=102")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("test15@mail.ru", "test15")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber").value("1"))
                .andExpect(jsonPath("$.totalPageCount").value("0"))
                .andExpect(jsonPath("$.totalResultCount").value("0"))
                .andExpect(jsonPath("$.itemsOnPage").value("0"));
    }

    //Получение неудалённых пользователей с репутацией за голоса
    @Test
    @DataSet(value = {
            "dataset/testUserResourceController/roleUser.yml",
            "dataset/testUserResourceController/users20.yml",
            "dataset/testUserResourceController/repFirst3DownVoteAndLast3UpVote.yml"
    },
            tableOrdering = {
                    "role",
                    "user_entity",
                    "reputation"
            },
            cleanBefore = true,
            strategy = SeedStrategy.INSERT)
    public void shouldReturnUsersSortedByVote() throws Exception {
        //Получение пользователей на первой странице (до 16 элементов) отсортированных по репутации
        //Пользователь с id 119 получает несколько голосов
        //Пользователи без репутации за голоса не сортируются
        mockMvc.perform(get("/api/user/vote?page=1&items=16")
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "password"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPageCount").value("2"))
                .andExpect(jsonPath("$.totalResultCount").value("20"))
                .andExpect(jsonPath("$.itemsOnPage").value("16"))
                .andExpect(jsonPath("$.items[0].id").value(119))
                .andExpect(jsonPath("$.items[1].id").value(117))
                .andExpect(jsonPath("$.items[2].id").value(118))
                .andExpect(jsonPath("$.items[3].id").value(103))
                .andExpect(jsonPath("$.items[4].id").value(104))
                .andExpect(jsonPath("$.items[5].id").value(105))
                .andExpect(jsonPath("$.items[6].id").value(106))
                .andExpect(jsonPath("$.items[7].id").value(107))
                .andExpect(jsonPath("$.items[8].id").value(108))
                .andExpect(jsonPath("$.items[9].id").value(109))
                .andExpect(jsonPath("$.items[10].id").value(110))
                .andExpect(jsonPath("$.items[11].id").value(111))
                .andExpect(jsonPath("$.items[12].id").value(112))
                .andExpect(jsonPath("$.items[13].id").value(113))
                .andExpect(jsonPath("$.items[14].id").value(114))
                .andExpect(jsonPath("$.items[15].id").value(115));

        //Получение пользователей на второй странице (до 16 элементов) отсортированных по репутации
        mockMvc.perform(get("/api/user/vote?page=2&items=16")
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "password"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPageCount").value("2"))
                .andExpect(jsonPath("$.totalResultCount").value("20"))
                .andExpect(jsonPath("$.itemsOnPage").value("4"))
                .andExpect(jsonPath("$.items[0].id").value(116))
                .andExpect(jsonPath("$.items[1].id").value(100))
                .andExpect(jsonPath("$.items[2].id").value(101))
                .andExpect(jsonPath("$.items[3].id").value(102));

        //Проверка на то, что без items на странице будет только 10 элементов
        mockMvc.perform(get("/api/user/vote?page=1")
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "password"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPageCount").value("2"))
                .andExpect(jsonPath("$.totalResultCount").value("20"))
                .andExpect(jsonPath("$.itemsOnPage").value("10"));

        //Проверка на некорректный запрос
        mockMvc.perform(get("/api/user/vote?page==")
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "password"))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

        //Получаем пользователей со строкой "11" в имени или почте
        mockMvc.perform(get("/api/user/vote?page=1&filter=11")
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "password"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPageCount").value("1"))
                .andExpect(jsonPath("$.totalResultCount").value("10"))
                .andExpect(jsonPath("$.itemsOnPage").value("10"))
                .andExpect(jsonPath("$.items[0].id").value(119))
                .andExpect(jsonPath("$.items[1].id").value(117))
                .andExpect(jsonPath("$.items[2].id").value(118))
                .andExpect(jsonPath("$.items[3].id").value(110))
                .andExpect(jsonPath("$.items[4].id").value(111))
                .andExpect(jsonPath("$.items[5].id").value(112))
                .andExpect(jsonPath("$.items[6].id").value(113))
                .andExpect(jsonPath("$.items[7].id").value(114))
                .andExpect(jsonPath("$.items[8].id").value(115))
                .andExpect(jsonPath("$.items[9].id").value(116));

        //Получаем пользователей со строкой "011" в имени или почте
        //Таких в БД нет, поэтому результат должен быть пустым
        mockMvc.perform(get("/api/user/vote?page=1&filter=011")
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "password"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPageCount").value("0"))
                .andExpect(jsonPath("$.totalResultCount").value("0"))
                .andExpect(jsonPath("$.itemsOnPage").value("0"));
    }

    //Получаем неудалённых пользователей, отсортированных по репутации
    @Test
    @DataSet(cleanBefore = true,
            value = {
                    "dataset/userresourcecontroller/roles.yml",
                    "dataset/userresourcecontroller/users_with_deleted_user.yml",
                    "dataset/userresourcecontroller/reputations.yml"
            },
            strategy = SeedStrategy.REFRESH)
    public void shouldReturnUsersSortedByRep() throws Exception {
        //Получаем неудалённых пользователей
        //Пользователь под id 102 получает репутацию дважды
        mockMvc.perform(get("/api/user/reputation?page=1&items=3")
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "password"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPageCount").value("1"))
                .andExpect(jsonPath("$.totalResultCount").value("2"))
                .andExpect(jsonPath("$.itemsOnPage").value("2"))
                .andExpect(jsonPath("$.items[0].id").value(102))
                .andExpect(jsonPath("$.items[1].id").value(100));

        //Получаем неудалённых пользователей со строкой "02" в имени или почте
        mockMvc.perform(get("/api/user/reputation?page=1&filter=02")
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "password"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPageCount").value("1"))
                .andExpect(jsonPath("$.totalResultCount").value("1"))
                .andExpect(jsonPath("$.itemsOnPage").value("1"))
                .andExpect(jsonPath("$.items[0].id").value(102))
                .andExpect(jsonPath("$.items[0].email").value("user102@mail.ru"));

        //Получаем неудалённых пользователей со строкой "03" в имени или почте
        //Таких в БД нет, поэтому результат должен быть пустым
        mockMvc.perform(get("/api/user/reputation?page=1&filter=03")
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "password"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPageCount").value("0"))
                .andExpect(jsonPath("$.totalResultCount").value("0"))
                .andExpect(jsonPath("$.itemsOnPage").value("0"));
    }

    //Проверяем изменение пароля
    @Test
    @DataSet(cleanBefore = true,
            value = {
                    "dataset/userresourcecontroller/roles.yml",
                    "dataset/userresourcecontroller/users_with_deleted_user.yml",
                    "dataset/userresourcecontroller/questions.yml",
                    "dataset/userresourcecontroller/reputations.yml"
            },
            strategy = SeedStrategy.REFRESH)
    public void shouldReturnUserWithChangedPassword() throws Exception {
        //Ставим новый пароль
        this.mockMvc.perform(patch("/api/user/change/password?password=test534")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk());

        //Заходим под новым паролем
        this.mockMvc.perform(patch("/api/user/change/password?password=anotherTest534")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "test534")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true,
            value = {
                    "dataset/testUserResourceController/shouldCacheIsUserExistByEmail/roles.yml",
                    "dataset/testUserResourceController/shouldCacheIsUserExistByEmail/users.yml"
            },
            strategy = SeedStrategy.CLEAN_INSERT)
    public void shouldCacheIsUserExistByEmail() throws Exception {

        String token101 = "Bearer " + getToken("test102@mail.ru", "user1");

        //Проверяю кэширование существующего
        assertNull(cacheManager.getCache("userExistByEmail").get("test102@mail.ru"));
        userDao.isUserExistByEmail("test102@mail.ru");
        assertNotNull(cacheManager.getCache("userExistByEmail").get("test102@mail.ru"));
        assertTrue(userDao.isUserExistByEmail("test102@mail.ru"));

        //Проверяю кэширование несуществующего
        assertNull(cacheManager.getCache("userExistByEmail").get("test100@mail.ru"));
        userDao.isUserExistByEmail("test100@mail.ru");
        assertNotNull(cacheManager.getCache("userExistByEmail").get("test100@mail.ru"));
        assertFalse(userDao.isUserExistByEmail("test100@mail.ru"));

        //Меняю пароль
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/user/change/password?password=newPassword321")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token101))
                .andDo(print())
                .andExpect(status().isOk());

        assertNull(cacheManager.getCache("userExistByEmail").get("test102@mail.ru"));

        userDao.isUserExistByEmail("test102@mail.ru");
        assertNotNull(cacheManager.getCache("userExistByEmail").get("test102@mail.ru"));

        //Удаляю по email
        userService.deleteById("test102@mail.ru");
        assertNull(cacheManager.getCache("userExistByEmail").get("test102@mail.ru"));

        //Удаляю по id с помощью Dao - ничего не должен удалять
        userDao.deleteById(103L);
        assertTrue(userService.getById(103L).isPresent());
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true,
            value = {
                    "dataset/testUserResourceController/shouldCacheIsUserExistByEmail/roles.yml",
                    "dataset/testUserResourceController/shouldCacheIsUserExistByEmail/users.yml"
            },
            strategy = SeedStrategy.CLEAN_INSERT)
    public void shouldCacheUserAfterLogin() throws Exception {

        //Проверяю кэширование до логина
        assertNull(cacheManager.getCache("userWithRoleByEmail").get("test102@mail.ru"));

        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setPassword("user1");
        authenticationRequest.setUsername("test102@mail.ru");

        mockMvc.perform(post("/api/auth/token")
                        .content(new ObjectMapper().writeValueAsString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //Проверяю кэширование после логина
        assertNotNull(cacheManager.getCache("userWithRoleByEmail").get("test102@mail.ru"));

        //Удаляю и проверяю кэширование
        userService.deleteById("test102@mail.ru");
        assertNull(cacheManager.getCache("userWithRoleByEmail").get("test102@mail.ru"));

        //Обновляю и проверяю кэширование
        userService.update(userService.getByEmail("test102@mail.ru").get());
        assertNull(cacheManager.getCache("userWithRoleByEmail").get("test102@mail.ru"));
    }

    //Получение всех удалённых вопросов авторизированного пользователя
    @Test
    @DataSet(cleanBefore = true, cleanAfter = true,
            value = {
                    "dataset/userresourcecontroller/roles.yml",
                    "dataset/userresourcecontroller/users.yml",
                    "dataset/testUserResourceController/testGetAllUserProfileQuestionDtoIsDelete/questions.yml",
                    "dataset/testUserResourceController/testGetAllUserProfileQuestionDtoIsDelete/tags.yml",
                    "dataset/testUserResourceController/testGetAllUserProfileQuestionDtoIsDelete/questions_has_tag.yml",
                    "dataset/testUserResourceController/testGetAllUserProfileQuestionDtoIsDelete/answers.yml"
            },
            strategy = SeedStrategy.CLEAN_INSERT)
    public void testGetAllUserProfileQuestionDtoIsDelete() throws Exception {
        mockMvc.perform(get("/api/user/profile/delete/questions")
                        .header(AUTHORIZATION, "Bearer " + getToken("test100@mail.ru", "test15"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        mockMvc.perform(get("/api/user/profile/delete/questions")
                        .header(AUTHORIZATION, "Bearer " + getToken("test15@mail.ru", "test15"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.[*].questionId").value(containsInRelativeOrder(103, 105, 106)))
                .andExpect(jsonPath("$.[*].listTagDto.length()").value(containsInRelativeOrder(2, 0, 3)))
                .andExpect(jsonPath("$.[*].countAnswer").value(containsInRelativeOrder(3, 1, 0)));
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true,
            value = {
                    "dataset/testUserResourceController/testGetAllBookMarksInUserProfile/roles.yml",
                    "dataset/testUserResourceController/testGetAllBookMarksInUserProfile/users.yml",
                    "dataset/testUserResourceController/testGetAllBookMarksInUserProfile/questions.yml",
                    "dataset/testUserResourceController/testGetAllBookMarksInUserProfile/tags.yml",
                    "dataset/testUserResourceController/testGetAllBookMarksInUserProfile/questions_has_tag.yml",
                    "dataset/testUserResourceController/testGetAllBookMarksInUserProfile/answers.yml",
                    "dataset/testUserResourceController/testGetAllBookMarksInUserProfile/votes_on_questions.yml",
                    "dataset/testUserResourceController/testGetAllBookMarksInUserProfile/question_viewed.yml"
            },
            strategy = SeedStrategy.CLEAN_INSERT)
    public void testGetAllBookMarksInUserProfile() throws Exception {
        String USER_TOKEN = "Bearer " + getToken("test15@mail.ru", "test15");
        mockMvc.perform(get("/api/user/profile/bookmarks")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.[*].questionId").value(containsInRelativeOrder(101, 102, 103)))
                .andExpect(jsonPath("$.[*].listTagDto.length()").value(containsInRelativeOrder(2, 2, 2)))
                .andExpect(jsonPath("$.[*].countAnswer").value(containsInRelativeOrder(3, 2, 1)))
                .andExpect(jsonPath("$.[*].countVote").value(containsInRelativeOrder(2, 1, -1)))
                .andExpect(jsonPath("$.[*].countView").value(containsInRelativeOrder(4, 1, 0)));
    }

    //Получение всех вопросов авторизированного пользователя
    @Test
    @DataSet(cleanBefore = true, cleanAfter = true,
            value = {
                    "dataset/userresourcecontroller/users.yml",
                    "dataset/userresourcecontroller/roles.yml",
                    "dataset/testUserResourceController/getAllUserProfileQuestionDtoById/questions.yml",
                    "dataset/testUserResourceController/getAllUserProfileQuestionDtoById/tags.yml",
                    "dataset/testUserResourceController/getAllUserProfileQuestionDtoById/questions_has_tag.yml",
                    "dataset/testUserResourceController/getAllUserProfileQuestionDtoById/answers.yml"
            },
            strategy = SeedStrategy.CLEAN_INSERT
    )
    public void getAllUserProfileQuestionDtoById() throws Exception {

        String USER_TOKEN_TEST1 = "Bearer " + getToken("test15@mail.ru", "test15");
        mockMvc.perform(get("/api/user/profile/questions")
                        .header(AUTHORIZATION, USER_TOKEN_TEST1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.[*].questionId").value(containsInRelativeOrder(101, 102, 103)))
                .andExpect(jsonPath("$.[*].listTagDto.length()").value(containsInRelativeOrder(2, 2, 0)))
                .andExpect(jsonPath("$.[*].countAnswer").value(containsInRelativeOrder(3, 0, 2)));

        String USER_TOKEN_TEST2 = "Bearer " + getToken("test100@mail.ru", "test15");
        mockMvc.perform(get("/api/user/profile/questions")
                        .header(AUTHORIZATION, USER_TOKEN_TEST2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
