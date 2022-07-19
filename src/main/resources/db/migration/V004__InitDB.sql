alter table if exists single_chat
    add column user_one_is_deleted boolean not null,
    add column user_two_is_deleted boolean not null;

alter table if exists single_chat alter column user_one_is_deleted set default false;
alter table if exists single_chat alter column user_two_is_deleted set default false;