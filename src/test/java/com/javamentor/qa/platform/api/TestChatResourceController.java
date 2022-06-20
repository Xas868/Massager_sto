package com.javamentor.qa.platform.api;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.AbstractClassForDRRiderMockMVCTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class TestChatResourceController extends AbstractClassForDRRiderMockMVCTests {
    @Test
    @DataSet (
            value = {
                    "dataset/ChatResourceController/roles.yml",
                    "dataset/ChatResourceController/users.yml",
                    "dataset/ChatResourceController/group_chat.yml",
                    "dataset/ChatResourceController/chat.yml",
                    "dataset/ChatResourceController/messagesAreEmpty.yml"
            }
    )
    public void testGetGroupChatDtoByIdMustReturnGroupChatWithId101AndEmptyMessages() throws Exception {
        String USER_TOKEN = "Bearer " + getToken("test101@mail.ru", "test101");
        mockMvc.perform(get("/api/user/chat/group/101")
                .header(AUTHORIZATION, USER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("101"))
                .andExpect(jsonPath("$.messages.totalResultCount").value(0))
                .andExpect(jsonPath("$.messages.itemsOnPage").value(0))
                .andExpect(jsonPath("$.messages.items.length()").value(0));
    }

    @Test
    @DataSet (
            value = {
                    "dataset/ChatResourceController/roles.yml",
                    "dataset/ChatResourceController/users.yml",
                    "dataset/ChatResourceController/group_chat.yml",
                    "dataset/ChatResourceController/chat.yml",
                    "dataset/ChatResourceController/messages5.yml"
            }
    )
    public void testGetGroupChatDtoByIdMustReturnGroupChatWithId101AndFiveMessages() throws Exception {
        String USER_TOKEN = "Bearer " + getToken("test101@mail.ru", "test101");
        mockMvc.perform(get("/api/user/chat/group/101")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("101"))
                .andExpect(jsonPath("$.messages.totalResultCount").value(5))
                .andExpect(jsonPath("$.messages.itemsOnPage").value(5))
                .andExpect(jsonPath("$.messages.items.length()").value(5));
    }

    @Test
    @DataSet (
            value = {
                    "dataset/ChatResourceController/roles.yml",
                    "dataset/ChatResourceController/users.yml",
                    "dataset/ChatResourceController/group_chat.yml",
                    "dataset/ChatResourceController/chat.yml",
                    "dataset/ChatResourceController/messages25.yml"
            }
    )
    public void testGetGroupChatDtoByIdMustReturnGroupChatWithId101AndFirstPageOfThreeAvailable() throws Exception {
        String USER_TOKEN = "Bearer " + getToken("test101@mail.ru", "test101");
        mockMvc.perform(get("/api/user/chat/group/101")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("101"))
                .andExpect(jsonPath("$.messages.totalResultCount").value(25))
                .andExpect(jsonPath("$.messages.totalPageCount").value(3))
                .andExpect(jsonPath("$.messages.currentPageNumber").value(1))
                .andExpect(jsonPath("$.messages.itemsOnPage").value(10))
                .andExpect(jsonPath("$.messages.items.length()").value(10))
                .andExpect(jsonPath("$.messages.items[0].id").value(125))
                .andExpect(jsonPath("$.messages.items[9].id").value(116));
    }

    @Test
    @DataSet (
            value = {
                    "dataset/ChatResourceController/roles.yml",
                    "dataset/ChatResourceController/users.yml",
                    "dataset/ChatResourceController/group_chat.yml",
                    "dataset/ChatResourceController/chat.yml",
                    "dataset/ChatResourceController/messages25.yml"
            }
    )
    public void testGetGroupChatDtoByIdMustReturnGroupChatWithId101AndThirdPageOfThreeAvailable() throws Exception {
        String USER_TOKEN = "Bearer " + getToken("test101@mail.ru", "test101");
        mockMvc.perform(get("/api/user/chat/group/101?currentPage=3")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("101"))
                .andExpect(jsonPath("$.messages.totalResultCount").value(25))
                .andExpect(jsonPath("$.messages.totalPageCount").value(3))
                .andExpect(jsonPath("$.messages.currentPageNumber").value(3))
                .andExpect(jsonPath("$.messages.itemsOnPage").value(5))
                .andExpect(jsonPath("$.messages.items.length()").value(5))
                .andExpect(jsonPath("$.messages.items[0].id").value(105))
                .andExpect(jsonPath("$.messages.items[4].id").value(101));
    }

    @Test
    @DataSet (
            value = {
                    "dataset/ChatResourceController/roles.yml",
                    "dataset/ChatResourceController/users.yml",
                    "dataset/ChatResourceController/group_chat.yml",
                    "dataset/ChatResourceController/chat.yml",
                    "dataset/ChatResourceController/messages25.yml"
            }
    )
    public void testGetGroupChatDtoByIdMustReturnGroupChatWithId101AndFirstPageWithItemsOnPageValueIsFifteen() throws Exception {
        String USER_TOKEN = "Bearer " + getToken("test101@mail.ru", "test101");
        mockMvc.perform(get("/api/user/chat/group/101?itemsOnPage=15")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("101"))
                .andExpect(jsonPath("$.messages.totalResultCount").value(25))
                .andExpect(jsonPath("$.messages.totalPageCount").value(2))
                .andExpect(jsonPath("$.messages.currentPageNumber").value(1))
                .andExpect(jsonPath("$.messages.itemsOnPage").value(15))
                .andExpect(jsonPath("$.messages.items.length()").value(15))
                .andExpect(jsonPath("$.messages.items[0].id").value(125))
                .andExpect(jsonPath("$.messages.items[14].id").value(111));
    }

    @Test
    @DataSet (
            value = {
                    "dataset/ChatResourceController/roles.yml",
                    "dataset/ChatResourceController/users.yml",
                    "dataset/ChatResourceController/group_chat.yml",
                    "dataset/ChatResourceController/chat.yml",
                    "dataset/ChatResourceController/messages25.yml"
            }
    )
    public void testGetGroupChatDtoByIdMustReturnGroupChatWithId101AndSecondPageWithItemsOnPageValueIsFifteen() throws Exception {
        String USER_TOKEN = "Bearer " + getToken("test101@mail.ru", "test101");
        mockMvc.perform(get("/api/user/chat/group/101?itemsOnPage=15&currentPage=2")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("101"))
                .andExpect(jsonPath("$.messages.totalResultCount").value(25))
                .andExpect(jsonPath("$.messages.totalPageCount").value(2))
                .andExpect(jsonPath("$.messages.currentPageNumber").value(2))
                .andExpect(jsonPath("$.messages.itemsOnPage").value(10))
                .andExpect(jsonPath("$.messages.items.length()").value(10))
                .andExpect(jsonPath("$.messages.items[0].id").value(110))
                .andExpect(jsonPath("$.messages.items[9].id").value(101));
    }

    @Test
    @DataSet (
            value = {
                    "dataset/ChatResourceController/roles.yml",
                    "dataset/ChatResourceController/users.yml",
                    "dataset/ChatResourceController/group_chat.yml",
                    "dataset/ChatResourceController/chat.yml",
                    "dataset/ChatResourceController/messages25.yml"
            }
    )
    public void testGetGroupChatDtoByIdWithIncorrectChatId() throws Exception {
        String USER_TOKEN = "Bearer " + getToken("test101@mail.ru", "test101");
        mockMvc.perform(get("/api/user/chat/group/102")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }
}
