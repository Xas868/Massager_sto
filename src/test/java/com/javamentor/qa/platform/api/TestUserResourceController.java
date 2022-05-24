package com.javamentor.qa.platform.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.dto.AuthenticationRequest;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    @Autowired
    private CacheManager cacheManager;

    @Test
    //Вывод Dto по id без тегов
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
                .andExpect(jsonPath("$.reputation").value(100))
                .andExpect(jsonPath("$.listTagDto").isEmpty());
    }

    @Test
    /*
    Вычисление количества ответов аутентифицированного юзера.
    У юзера test1 3 ответа, 2 из которых сделаны менее, чем неделю назад.
     */
    @DataSet (cleanBefore = true,
            value = {
                    "dataset/testUserResourceController/getCountAnswersPerWeekByUserId/answers.yml",
                    "dataset/testUserResourceController/getCountAnswersPerWeekByUserId/user.yml",
                    "dataset/testUserResourceController/getCountAnswersPerWeekByUserId/role.yml",
                    "dataset/testUserResourceController/getCountAnswersPerWeekByUserId/questions.yml"
            },
            strategy = SeedStrategy.REFRESH)
    public void testGetCountAnswersPerWeekByUserId() throws Exception {
        String USER_TOKEN = "Bearer " + getToken("test1@mail.ru", "test1");
        mockMvc.perform(get("/api/user/profile/question/week")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("2"));
    }

    @Test
    //Вывод Dto по id с топ 3 тегами
    // У User100 2 ответа под вопросом с тегом 106 и 5 вопросов с тегом 106 - итого, популярность: 7
    //           2 ответа под вопросом с тегом 103 и 4 вопроса с тегом 103 - итого, популярность: 6
    //           2 ответа под вопросом с тегом 104 и 4 вопроса с тегом 104 - итого, популярность: 6
    // Тег 106 - 1-е место, тег 103, тег 104 - 2-е и 3-е соответственно (популярность одинакова => сортировка по id)
    @DataSet(cleanBefore = true,
            value = {
                    "dataset/testUserResourceController/getApiUserDtoIdWithTop3Tags/roles.yml",
                    "dataset/testUserResourceController/getApiUserDtoIdWithTop3Tags/users5.yml",
                    "dataset/testUserResourceController/getApiUserDtoIdWithTop3Tags/reputations5.yml",
                    "dataset/testUserResourceController/getApiUserDtoIdWithTop3Tags/answers20.yml",
                    "dataset/testUserResourceController/getApiUserDtoIdWithTop3Tags/tags8.yml"
            },
            strategy = SeedStrategy.CLEAN_INSERT)
    public void getApiUserDtoIdWithTop3Tags() throws Exception {
        this.mockMvc.perform(get("/api/user/100")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + getToken("user100@mail.ru", "test15")))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("100"))
                .andExpect(jsonPath("$.email").value("user100@mail.ru"))
                .andExpect(jsonPath("$.fullName").value("test 1"))
                .andExpect(jsonPath("$.imageLink").value("photo"))
                .andExpect(jsonPath("$.city").value("Moscow"))
                .andExpect(jsonPath("$.reputation").value(1000))
                .andExpect(jsonPath("$.listTagDto[*].id").value(containsInRelativeOrder(106, 103, 104)));
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
        userService.deleteByName("test102@mail.ru");
        assertNull(cacheManager.getCache("userExistByEmail").get("test102@mail.ru"));

        //Удаляю по id с помощью Dao - ничего не должен удалять
        userService.deleteById(103L);
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
        userService.deleteByName("test102@mail.ru");
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
                    "dataset/testUserResourceController/testGetAllBookMarksInUserProfile/question_viewed.yml",
                    "dataset/testUserResourceController/testGetAllBookMarksInUserProfile/bookmarks.yml"
            },
            strategy = SeedStrategy.CLEAN_INSERT)
    public void testGetAllBookMarksInUserProfile() throws Exception {
        //закладки user с id 101 вопросы 101, 102, 103
        String USER_TOKEN = "Bearer " + getToken("test15@mail.ru", "test15");
        mockMvc.perform(get("/api/user/profile/bookmarks")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.[*].questionId").value(containsInRelativeOrder(101, 102, 103)))
                .andExpect(jsonPath("$.[*].listTagDto.length()").value(containsInRelativeOrder(1, 3, 2)))
                .andExpect(jsonPath("$.[*].countAnswer").value(containsInRelativeOrder(3, 2, 1)))
                .andExpect(jsonPath("$.[*].countVote").value(containsInRelativeOrder(2, 1, -1)))
                .andExpect(jsonPath("$.[*].countView").value(containsInRelativeOrder(4, 1, 0)));

        //закладки user с id 102 вопросы 101, 102, 103
        String USER_TOKEN_USERID_102 = "Bearer " + getToken("test102@mail.ru", "test15");
        mockMvc.perform(get("/api/user/profile/bookmarks")
                        .header(AUTHORIZATION, USER_TOKEN_USERID_102)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.[*].questionId").value(containsInRelativeOrder(101, 102, 103)))
                .andExpect(jsonPath("$.[*].listTagDto.length()").value(containsInRelativeOrder(1, 3, 2)))
                .andExpect(jsonPath("$.[*].countAnswer").value(containsInRelativeOrder(3, 2, 1)))
                .andExpect(jsonPath("$.[*].countVote").value(containsInRelativeOrder(2, 1, -1)))
                .andExpect(jsonPath("$.[*].countView").value(containsInRelativeOrder(4, 1, 0)));

        //закладки user с id 103 вопросы 104, 105, 106
        String USER_TOKEN_USERID_103 = "Bearer " + getToken("test103@mail.ru", "test15");
        mockMvc.perform(get("/api/user/profile/bookmarks")
                        .header(AUTHORIZATION, USER_TOKEN_USERID_103)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.[*].questionId").value(containsInRelativeOrder(104, 105, 106)))
                .andExpect(jsonPath("$.[*].listTagDto.length()").value(containsInRelativeOrder(0, 0, 0)))
                .andExpect(jsonPath("$.[*].countAnswer").value(containsInRelativeOrder(0, 0, 0)))
                .andExpect(jsonPath("$.[*].countVote").value(containsInRelativeOrder(1, 1, 0)))
                .andExpect(jsonPath("$.[*].countView").value(containsInRelativeOrder(0, 0, 0)));
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

    // Получение списка из топ 10 пользователей за неделю с наибольшим количеством вопросов по убыванию.
    // Все ответы в этом датасете в рамках одной недели, проверка только сортировки
    // У User 112 - 5 ответов, голосов за ответы - (-6) - но т.к. ответов наибольшее число среди всех - он первый.
    // У User 105, User 102, User 108, User 114 - по 4 ответа, полученные голоса за эти ответы +5, 0, -2, -2.
    // т.к. User 108, User 114 имеют равное число и ответов, и голосов, они отсортированы по id
    @Test
    @DataSet(cleanBefore = true, cleanAfter = true,
            value = {
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/users.yml",
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/roles.yml",
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/questions.yml",
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/answers.yml",
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/votes_on_answers.yml",
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/reputations.yml"
            },
            strategy = SeedStrategy.CLEAN_INSERT)
    public void testOnlySortGetTop10UsersRankedByNumberOfQuestions() throws Exception {
        String USER_TOKEN = "Bearer " + getToken("user100@mail.ru", "test15");
        mockMvc.perform(get("/api/user/top10/week")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(containsInRelativeOrder(112, 105, 102, 108, 114, 109, 101, 106, 100, 107)));
    }

    // Получение списка из топ 10 пользователей за неделю с наибольшим количеством вопросов по убыванию.
    // У User 112 4 ответа из 5 (108, 110, 115, 116) - даны более чем одну неделю назад, то есть он должен выйти из топ10,
    // т.к. у 10 и 11 места по 2 ответа.
    // У User 105, User 102, User 108, User 114 - по 4 ответа, полученные голоса за эти ответы +5, 0, -2, -2.
    // Т.к. User 108, User 114 имеют равное число и ответов, и голосов, они отсортированы по id
    @Test
    @DataSet(cleanBefore = true, cleanAfter = true,
            value = {
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/users.yml",
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/roles.yml",
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/questions.yml",
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/answers_4_ans_out_of_week.yml",
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/votes_on_answers.yml",
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/reputations.yml"
            },
            strategy = SeedStrategy.CLEAN_INSERT)
    public void testGetTop10UsersForWeekRankedByNumberOfQuestions() throws Exception {
        String USER_TOKEN = "Bearer " + getToken("user100@mail.ru", "test15");
        mockMvc.perform(get("/api/user/top10/week")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(containsInRelativeOrder(105, 102, 108, 114, 109, 101, 106, 100, 107, 113)));
    }

    // Получение списка из топ 10 пользователей за неделю с наибольшим количеством вопросов по убыванию.
    // Четыре ответа, которые User 112 давал - удалены (isDeleted = true). Ожидается потеря топ1 по количеству ответов.
    @Test
    @DataSet(cleanBefore = true, cleanAfter = true,
            value = {
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/users.yml",
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/roles.yml",
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/questions.yml",
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/answers_deleted_ans.yml",
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/votes_on_answers.yml",
                    "dataset/testUserResourceController/testGetTop10UsersForWeekRankedByNumberOfQuestions/reputations.yml"
            },
            strategy = SeedStrategy.CLEAN_INSERT)
    public void testGetTop10UsersForWeekDeletedAnswersRankedByNumberOfQuestions() throws Exception {
        String USER_TOKEN = "Bearer " + getToken("user100@mail.ru", "test15");
        mockMvc.perform(get("/api/user/top10/week")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(containsInRelativeOrder(105, 102, 108, 114, 109, 101, 106, 100, 107, 113)));
    }
}
