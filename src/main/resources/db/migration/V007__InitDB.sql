create sequence user_chat_pin_seq start 1 increment 1;

create table user_chat_pin (
                               id bigint not null,
                               chat_id bigint not null,
                               user_id bigint not null,
                               persist_date timestamp,
                               primary key (id)
);

alter table user_chat_pin
    add constraint user_chat_pin_user_fk
        foreign key (user_id)
            references user_entity;

alter table user_chat_pin
    add constraint user_chat_pin_chat_fk
        foreign key (chat_id)
            references chat;