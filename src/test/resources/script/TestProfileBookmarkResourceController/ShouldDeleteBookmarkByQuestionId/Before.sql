INSERT INTO role (id, name) VALUES
    (100, 'ROLE_USER'),
    (101, 'ROLE_USER');


INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id) VALUES
                         (100, 'I am test user', 'Moskow', 'user100@mail.ru', 'User 100', '/images/noUserAvatar.png', false, true, DATE(NOW()),
                         null, null, null, 'user_100', '$2a$10$ssxQ5kVwJ25Lda0csdKpLOHR8VfU7EiDwpIvU6lizmW8XTbYxVNbi', DATE(NOW()), 100),
                        (101, 'I am test user', 'Moskow', 'user101@mail.ru', 'User 101', '/images/noUserAvatar.png', false, true, DATE(NOW()),
                         null, null, null, 'user_101', '$2a$10$fyHD1Ut8.knrIds5S7/ee.Q5S5c.NQ3sr8k0YzUXsitEbGK.a7JZi', DATE(NOW()), 100);


INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id) VALUES
                        (100, 'What do you think about question 100?', false, DATE(NOW()), DATE(NOW()), 'Question 100', 100),
                        (101, 'What do you think about question 101?', false, DATE(NOW()), DATE(NOW()), 'Question 101', 101);



insert into bookmarks(question_id, user_id, note)
values (100, 100, 'note 1'),
       (101, 101, 'note 2');