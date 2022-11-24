INSERT INTO role (id, name)
VALUES (100, 'ROLE_USER');

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (102, 'I am test user', 'Moskow', 'user102@mail.ru', 'User 102', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user_102', '$2a$10$twa4u5HMjrO5wGbOTQxHeu1vyF9InNfYYXipblquQ.q46xEPoAO.u', now(), 100);

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (103, 'I am test user 2', 'Samara', 'user103@mail.ru', 'User 103', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user_103', '$2a$10$twa4u5HMjrO5wGbOTQxHeu1vyF9InNfYYXipblquQ.q46xEPoAO.u', now(), 100);

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (104, 'I am test user 3', 'Kazan', 'user104@mail.ru', 'User 104', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user_104', '$2a$10$twa4u5HMjrO5wGbOTQxHeu1vyF9InNfYYXipblquQ.q46xEPoAO.u', now(), 100);

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (105, 'I am test user 5', 'Sochi', 'user105@mail.ru', 'User 105', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user_105', '$2a$10$twa4u5HMjrO5wGbOTQxHeu1vyF9InNfYYXipblquQ.q46xEPoAO.u', now(), 100);

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (106, 'I am test user 6', 'Sochi', 'user106@mail.ru', 'User 106', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user_106', '$2a$10$twa4u5HMjrO5wGbOTQxHeu1vyF9InNfYYXipblquQ.q46xEPoAO.u', now(), 100);

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (107, 'I am test user 7', 'Sochi', 'user107@mail.ru', 'User 107', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user_107', '$2a$10$twa4u5HMjrO5wGbOTQxHeu1vyF9InNfYYXipblquQ.q46xEPoAO.u', now(), 100);

INSERT INTO chat (id, chat_type, is_global, persist_date)
VALUES (101, 1, false, now());

INSERT INTO chat (id, chat_type, is_global, persist_date)
VALUES (102, 1, false, now());

INSERT INTO chat (id, chat_type, is_global, persist_date)
VALUES (103, 1, false, now());

INSERT INTO chat (id, chat_type, is_global, persist_date)
VALUES (104, 1, false, now());

INSERT INTO group_chat (title, chat_id, author_id, image)
VALUES ('chatTest', 101, 103, '/images/chatAvatar');

INSERT INTO group_chat (title, chat_id, author_id, image)
VALUES ('chatTest', 102, 105, '/images/chatAvatar');

INSERT INTO group_chat (title, chat_id, author_id, image)
VALUES ('chatTest', 103, 106, '/images/chatAvatar');

INSERT INTO group_chat (title, chat_id, author_id, image)
VALUES ('chatTest', 104, 102, '/images/chatAvatar');

INSERT INTO groupchat_has_users (chat_id, user_id)
VALUES (101, 102);

INSERT INTO groupchat_has_users (chat_id, user_id)
VALUES (101, 103);

INSERT INTO groupchat_has_users (chat_id, user_id)
VALUES (101, 104);

INSERT INTO groupchat_has_users (chat_id, user_id)
VALUES (102, 105);

INSERT INTO groupchat_has_users (chat_id, user_id)
VALUES (102, 106);

INSERT INTO groupchat_has_users (chat_id, user_id)
VALUES (102, 107);

INSERT INTO groupchat_has_users (chat_id, user_id)
VALUES (103, 106);

INSERT INTO groupchat_has_users (chat_id, user_id)
VALUES (103, 102);

INSERT INTO groupchat_has_users (chat_id, user_id)
VALUES (103, 104);

INSERT INTO groupchat_has_users (chat_id, user_id)
VALUES (104, 102);

INSERT INTO groupchat_has_users (chat_id, user_id)
VALUES (104, 106);

INSERT INTO groupchat_has_users (chat_id, user_id)
VALUES (104, 103);