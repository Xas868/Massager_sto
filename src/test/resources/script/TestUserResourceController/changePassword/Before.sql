INSERT INTO role (id, name)
VALUES (100, 'ROLE_USER');

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (100, 'I am test user', 'Moscow', 'user100@mail.ru', 'user100@mail.ru', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user100@mail.ru', '$2a$10$Z/TD5vCVG7amB2u34YrU8.Czni/8rtcIOBCVqP/wwrqwrnFT/qnh.', now(), 100);


