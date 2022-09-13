package com.javamentor.qa.platform.service.util;

import com.javamentor.qa.platform.models.dto.ChatDto;

public class Comparators {
    public static int isPinAndLastMessageDateComparator(ChatDto chat1, ChatDto chat2) {
        if (chat1.isChatPin()) {
            if (chat2.isChatPin()) {
                return chat2.getPersistDateTimeLastMessage().compareTo(chat1.getPersistDateTimeLastMessage());
            } else {
                return -1;
            }
        } else if (chat2.isChatPin()) {
            return 1;
        }
        return chat2.getPersistDateTimeLastMessage().compareTo(chat1.getPersistDateTimeLastMessage());
    }
}
