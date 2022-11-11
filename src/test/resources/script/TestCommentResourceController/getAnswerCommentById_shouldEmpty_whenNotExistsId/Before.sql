/*
 *  Table: role
 */
INSERT INTO role (id, name) VALUES
    (100, 'ROLE_USER');

/*
 *  Table: user_entity
 */
INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id) VALUES
    (100, 'I am test user', 'Moskow', 'user100@mail.ru', 'User 100', '/images/noUserAvatar.png', false, true, DATE(NOW()),
    null, null, null, 'user_100', '$2a$10$ssxQ5kVwJ25Lda0csdKpLOHR8VfU7EiDwpIvU6lizmW8XTbYxVNbi', DATE(NOW()), 100);