alter table if exists answer
    add column moderator_id int8;

alter table answer
    add constraint answer_moderator_fk
        foreign key (user_id)
            references user_entity;
