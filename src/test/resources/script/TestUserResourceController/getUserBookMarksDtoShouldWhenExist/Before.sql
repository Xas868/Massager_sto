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
    null, null, null, 'user_100', '$2a$10$ssxQ5kVwJ25Lda0csdKpLOHR8VfU7EiDwpIvU6lizmW8XTbYxVNbi', DATE(NOW()), 100),

    (101, 'I am test user', 'Moskow', 'user101@mail.ru', 'User 101', '/images/noUserAvatar.png', false, true, DATE(NOW()),
    null, null, null, 'user_101', '$2a$10$fyHD1Ut8.knrIds5S7/ee.Q5S5c.NQ3sr8k0YzUXsitEbGK.a7JZi', DATE(NOW()), 100);

/*
 *  Table: question
 */
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id) VALUES
    (100, 'What do you think about question 100?', false, DATE(NOW()), DATE(NOW()), 'Question 100', 100),
    (101, 'What do you think about question 101?', false, DATE(NOW()), DATE(NOW()), 'Question 101', 101);

/*
 *  Table: tag
 */
INSERT INTO tag (id, description, name, persist_date) VALUES
    (100, 'Description of tag 1', 'vfOxMU1', '2022-11-12 22:09:06.639083'),
    (101, 'Description of tag 2', 'iThKcj2', '2022-11-12 22:09:06.639579'),
    (102, 'Description of tag 3', 'LTGDJP3', '2022-11-12 22:09:06.639579');

/*
 *  Table: question_has_tag
 */
INSERT INTO question_has_tag (question_id, tag_id) VALUES
    (100, 100),
    (100, 101),
    (100, 102);
/*
 *  Table: reputation
 */
INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id) VALUES
    (100, 1000, DATE(NOW()), 1, null, 100, 100, null),
    (101, 900, DATE(NOW()), 1, null, 101, 100, null),
    (103, 800, DATE(NOW()), 1, null, 100, 100, null);


insert into bookmarks(question_id, user_id, note)
values (100, 100, 'note 1'),
       (101, 100, 'note 2');
