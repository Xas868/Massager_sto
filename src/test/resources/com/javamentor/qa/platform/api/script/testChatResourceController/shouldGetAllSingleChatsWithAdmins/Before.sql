INSERT INTO role (id, name)
VALUES (100, 'ROLE_USER');

INSERT INTO role (id, name)
VALUES (101, 'ROLE_ADMIN');

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (100, 'I am test user', 'Moscow', 'user100@mail.ru', 'user100@mail.ru', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user100@mail.ru', '$2a$10$Z/TD5vCVG7amB2u34YrU8.Czni/8rtcIOBCVqP/wwrqwrnFT/qnh.', now(), 100);

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (101, 'I am test user', 'Moscow', 'user101@mail.ru', 'user101@mail.ru', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user101@mail.ru', '$2a$10$Z/TD5vCVG7amB2u34YrU8.Czni/8rtcIOBCVqP/wwrqwrnFT/qnh.', now(), 101);

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (102, 'I am test user', 'Moscow', 'user102@mail.ru', 'user102@mail.ru', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user102@mail.ru', '$2a$10$Z/TD5vCVG7amB2u34YrU8.Czni/8rtcIOBCVqP/wwrqwrnFT/qnh.', now(), 101);

INSERT INTO chat (id, chat_type, persist_date, is_global)
VALUES (100, 0, now(), false);

INSERT INTO chat (id, chat_type, persist_date, is_global)
VALUES (101, 0, now(), false);

INSERT INTO chat (id, chat_type, persist_date, is_global)
VALUES (102, 0, now(), false);

INSERT INTO single_chat (chat_id, use_two_id, user_one_id, user_one_is_deleted, user_two_is_deleted)
VALUES (100, 101, 100, false, false);

INSERT INTO single_chat (chat_id, use_two_id, user_one_id, user_one_is_deleted, user_two_is_deleted)
VALUES (101, 101, 102, false, false);

INSERT INTO single_chat (chat_id, use_two_id, user_one_id, user_one_is_deleted, user_two_is_deleted)
VALUES (102, 102, 100, false, false);

INSERT INTO message (id, message, persist_date, last_redaction_date, chat_id, user_sender_id)
VALUES (100, 'message_from_chat_100_and_user_100', '2022-10-03T00:00:00', now(), 100, 100);

INSERT INTO message (id, message, persist_date, last_redaction_date, chat_id, user_sender_id)
VALUES (101, 'message_from_chat_101_and_user_101', '2022-10-02T00:00:00', now(), 101, 101);

INSERT INTO message (id, message, persist_date, last_redaction_date, chat_id, user_sender_id)
VALUES (102, 'message_from_chat_102_and_user_102', '2022-10-01T00:00:00', now(), 102, 102);
