INSERT INTO role (id, name)
VALUES (100, 'ROLE_USER');

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (102, 'I am test user', 'Moskow', 'user102@mail.ru', 'User 102', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user_102', '$2a$10$twa4u5HMjrO5wGbOTQxHeu1vyF9InNfYYXipblquQ.q46xEPoAO.u', now(), 100);

INSERT INTO chat (id, chat_type, persist_date, is_global)
VALUES (101, 1, now(), false);

INSERT INTO message (id, last_redaction_date, message, persist_date, chat_id, user_sender_id)
VALUES (101, now(), 'Test message 101', now(), 101, 102);

INSERT INTO message (id, last_redaction_date, message, persist_date, chat_id, user_sender_id)
VALUES (102, now(), 'Test message 102', now(), 101, 102);

INSERT INTO message (id, last_redaction_date, message, persist_date, chat_id, user_sender_id)
VALUES (103, now(), 'Test message 103', now(), 101, 102);

INSERT INTO message (id, last_redaction_date, message, persist_date, chat_id, user_sender_id)
VALUES (104, now(), 'Test text 104', now(), 101, 102);