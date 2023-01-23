create sequence if not exists block_chat_user_list_seq start 1 increment 1;

alter sequence block_chat_user_list_seq owner to postgres;

create table block_chat_user_list (
     id int8 not null,
     profile_id int8,
     blocked_id int8,
     persist_date timestamp,
     primary key (id)
 );

 alter table block_chat_user_list
     add constraint block_chat_user_list_user_profile_fk
         foreign key (profile_id)
             references user_entity;

 alter table block_chat_user_list
     add constraint block_chat_user_list_user_blocked_fk
         foreign key (blocked_id)
             references user_entity;