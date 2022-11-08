alter table group_chat
    add column image varchar(255);

alter table chat
drop column image;

alter table if exists group_chat
    add column author_id int8;

alter table group_chat
    add constraint group_chat_author_id
        foreign key (author_id)
            references user_entity;