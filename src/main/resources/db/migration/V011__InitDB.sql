create table group_chat_moderator (
     chat_id int8 not null,
     moderator_id int8 not null,
     primary key (chat_id, moderator_id)
);

alter table group_chat_moderator
    add constraint group_chat_moderator_user_fk
        foreign key (moderator_id)
            references user_entity;

alter table group_chat_moderator
    add constraint group_chat_moderator_chat_fk
        foreign key (chat_id)
            references group_chat;