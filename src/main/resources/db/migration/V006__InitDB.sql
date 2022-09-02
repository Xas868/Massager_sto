alter table chat
    drop column title;

alter table group_chat
    add column title varchar(255);