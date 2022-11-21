INSERT INTO role (id, name)
VALUES (100, 'ROLE_USER');

INSERT INTO role (id, name)
VALUES (101, 'ROLE_ADMIN');

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (102, 'I am test user', 'Moskow', 'user102@mail.ru', 'User 102', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user_102', '$2a$10$twa4u5HMjrO5wGbOTQxHeu1vyF9InNfYYXipblquQ.q46xEPoAO.u', now(), 100);

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (103, 'I am test user 2', 'Samara', 'user103@mail.ru', 'User 103', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user_103', '$2a$10$twa4u5HMjrO5wGbOTQxHeu1vyF9InNfYYXipblquQ.q46xEPoAO.u', now(), 101);

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (104, 'I am test user 3', 'Kazan', 'user104@mail.ru', 'User 104', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user_104', '$2a$10$twa4u5HMjrO5wGbOTQxHeu1vyF9InNfYYXipblquQ.q46xEPoAO.u', now(), 100);

INSERT INTO chat (id, chat_type, is_global, persist_date)
VALUES (101, 1, true, now());

INSERT INTO group_chat (title, chat_id, author_id, image)
VALUES ('chatTest', 101, 102, '/images/chatAvatar');

INSERT INTO groupchat_has_users (chat_id, user_id)
VALUES (101, 102);

INSERT INTO groupchat_has_users (chat_id, user_id)
VALUES (101, 103);

INSERT INTO groupchat_has_users (chat_id, user_id)
VALUES (101, 104);