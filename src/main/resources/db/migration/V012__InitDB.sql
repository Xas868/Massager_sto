alter table bookmarks
    add column note text;

create table group_bookmark
(
    id bigserial not null unique primary key,
    title varchar(255) not null,
    user_id bigint references user_entity(id)
);

create table bookmark_has_group
(
    group_bookmark_id bigint references group_bookmark (id),
    book_marks_id       bigint references bookmarks (id),
    CONSTRAINT pk_group_bookmark primary key (group_bookmark_id, book_marks_id)
);