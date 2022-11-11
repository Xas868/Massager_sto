alter table group_chat
    add column image varchar(255);

alter table chat
drop column image;
