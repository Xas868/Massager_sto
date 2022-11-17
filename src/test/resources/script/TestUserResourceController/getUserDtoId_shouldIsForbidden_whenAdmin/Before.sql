/*
 *  Table: role
 */
INSERT INTO role (id, name) VALUES
    (120, 'ROLE_ADMIN'),
    (121, 'ROLE_USER');

/*
 *  Table: user_entity
 */
INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id) VALUES
    (120, 'I am test user', 'Moskow', 'user120@mail.ru', 'User 120', '/images/noUserAvatar.png', false, true, DATE(NOW()),
    null, null, null, 'user_120', '$2a$10$O0vsxL8sEIdM0w0bp0Cg2OTaPm3UP7xTiGVrn2Wz9pg3mjRv9gKp.', DATE(NOW()), 120),

    (121, 'I am test user', 'Moskow', 'user121@mail.ru', 'User 121', '/images/noUserAvatar.png', false, true, DATE(NOW()),
    null, null, null, 'user_121', '$2a$10$IxYWysCEve9f2Ra1IfFhxuxjUiIWvC5RZcghDuLFwaIPmhbDImC2a', DATE(NOW()), 121);