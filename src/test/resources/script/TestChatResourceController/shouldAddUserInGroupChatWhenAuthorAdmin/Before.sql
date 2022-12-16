INSERT INTO role (id, name)
VALUES (100, 'ROLE_USER'),
       (103, 'ROLE_ADMIN');


INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (110, 'I am test user', 'Moscow', 'user110@mail.ru', 'user110@mail.ru', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user110@mail.ru', '$2a$12$81xa7RV0S3Bq414iVudVseAhJhUBM9AvGbn4Mg2rNVS9Ldw8LgG92', now(), 100);

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (111, 'I am test user', 'Moscow', 'user111@mail.ru', 'user111@mail.ru', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user111@mail.ru', '$2a$12$81xa7RV0S3Bq414iVudVseAhJhUBM9AvGbn4Mg2rNVS9Ldw8LgG92', now(), 100);

        INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (190, 'I am test user', 'Moscow', 'user190@mail.ru', 'user190@mail.ru', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user190@mail.ru', '$2a$12$81xa7RV0S3Bq414iVudVseAhJhUBM9AvGbn4Mg2rNVS9Ldw8LgG92', now(), 103);


INSERT INTO chat (id, chat_type, persist_date, is_global)
VALUES (120, 1, now(), false);

INSERT INTO group_chat (chat_id, title, author_id, image)
VALUES (120, 'group name chat', 190, 'no image');

INSERT INTO groupchat_has_users (chat_id, user_id)
VALUES (120,190), (120, 111)

