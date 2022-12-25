create sequence if not exists group_bookmark_seq start 1 increment 1;

create table group_bookmark
(
    id bigint not null unique primary key default nextval('group_bookmark_seq'),
    title varchar(255) not null,
    user_id bigint not null references user_entity(id)
);

create table bookmark_has_group
(
    group_bookmark_id bigint not null references group_bookmark (id),
    book_marks_id bigint not null references bookmarks (id),
    CONSTRAINT pk_group_bookmark primary key (group_bookmark_id, book_marks_id)
);