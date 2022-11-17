/*
 *  Table: role
 */
INSERT INTO role (id, name) VALUES
    (101, 'ROLE_ADMIN'),
    (102, 'ROLE_USER');

/*
 *  Table: user_entity
 */
INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id) VALUES
    (101, 'I am test user', 'Moscow', 'user101@mail.ru', 'User 101', '/images/noUserAvatar.png', false, true, DATE(NOW()),
    null, null, null, 'user_101', '$2a$10$fyHD1Ut8.knrIds5S7/ee.Q5S5c.NQ3sr8k0YzUXsitEbGK.a7JZi', '2022-11-13 17:55:55.461886', 102),

    (102, 'I am test user', 'Moscow', 'adminmail102@mail.ru', 'Admin 102', '/images/noUserAvatar.png', false, true, DATE(NOW()),
    null, null, null, 'admin_102', '$2a$10$fyHD1Ut8.knrIds5S7/ee.Q5S5c.NQ3sr8k0YzUXsitEbGK.a7JZi', '2021-11-13 17:55:55.461886', 101);
