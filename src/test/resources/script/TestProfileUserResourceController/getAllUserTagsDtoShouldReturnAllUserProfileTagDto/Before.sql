/*
 *  Table: role
 */
INSERT INTO role (id, name)
VALUES (100, 'ROLE_USER'),
       (101, 'ROLE_USER');

/*
 *  Table: user_entity
 */
INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (100, 'I am test user', 'Moskow', 'user100@mail.ru', 'User 100', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_100', '$2a$10$ssxQ5kVwJ25Lda0csdKpLOHR8VfU7EiDwpIvU6lizmW8XTbYxVNbi', DATE(NOW()), 100),

       (101, 'I am test user', 'Moskow', 'user101@mail.ru', 'User 101', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_101', '$2a$10$fyHD1Ut8.knrIds5S7/ee.Q5S5c.NQ3sr8k0YzUXsitEbGK.a7JZi', DATE(NOW()), 100);

/*
 *  Table: question
 */
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (105, 'What do you think about question 100?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '1  minute', 'Question 105', 101),
       (100, 'What do you think about question 100?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '2  minute', 'Question 100', 101),
       (101, 'What do you think about question 101?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '3  minute', 'Question 101', 101),
       (102, 'What do you think about question 102?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '4  minute', 'Question 102', 101),
       (103, 'What do you think about question 103?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '5  minute', 'Question 103', 101),
       (104, 'What do you think about question 104?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '6  minute', 'Question 104', 101),
       (106, 'What do you think about question 106?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '7  minute', 'Question 106', 101),
       (107, 'What do you think about question 107?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '8  minute', 'Question 107', 101),
       (108, 'What do you think about question 108?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '9  minute', 'Question 108', 101),
       (109, 'What do you think about question 109?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '10  minute', 'Question 109', 101),
       (110, 'What do you think about question 110?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '11  minute', 'Question 110', 101),
       (111, 'What do you think about question 111?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '12  minute', 'Question 111', 101),
       (112, 'What do you think about question 112?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '13  minute', 'Question 112', 101),
       (113, 'What do you think about question 113?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '14  minute', 'Question 113', 101),
       (114, 'What do you think about question 114?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '1 hour', 'Question 120', 101);

/*
 *  Table: tag
 */
INSERT INTO tag (id, description, name, persist_date)
VALUES (100, 'Description of tag 1', 'vfOxMU1', '2022-11-12 22:09:06.639083'),
       (101, 'Description of tag 2', 'iThKcj2', '2022-11-12 22:09:06.639579'),
       (103, 'Description of tag 3', 'iThKcj3', '2022-11-12 22:09:06.639579'),
       (104, 'Description of tag 4', 'iThKcj4', '2022-11-12 22:09:06.639579'),
       (102, 'Description of tag 3', 'LTGDJP3', '2022-11-12 22:09:06.639579'),
       (114, 'Description of tag 14', 'QWERT5', '2022-11-12 22:09:06.639579');

/*
 *  Table: question_has_tag
 */
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (100, 100),
       (100, 101),
       (105, 104),
       (101, 101),
       (100, 102),
       (114, 102),
       (103, 101),
       (111, 101);

/*
 *  Table: reputation
 */
INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id)
VALUES (100, 1000, DATE(NOW()), 1, null, 100, 100, null),
       (101, 900, DATE(NOW()), 1, null, 101, 100, null),
       (103, 800, DATE(NOW()), 1, null, 100, 100, null);

insert into votes_on_questions(id, persist_date, vote, question_id, user_id)
values (1, DATE(NOW()), 'UP_VOTE', 100, 101),
       (2, DATE(NOW()), 'UP_VOTE', 100, 101),
       (3, DATE(NOW()), 'UP_VOTE', 100, 101),
       (4, DATE(NOW()), 'UP_VOTE', 100, 101),
       (5, DATE(NOW()), 'UP_VOTE', 100, 101),
       (6, DATE(NOW()), 'UP_VOTE', 100, 101),
       (7, DATE(NOW()), 'UP_VOTE', 100, 101),
       (8, DATE(NOW()), 'UP_VOTE', 100, 101),
       (9, DATE(NOW()), 'DOWN_VOTE', 100, 101),
       (10, DATE(NOW()), 'DOWN_VOTE', 100, 101),
       (11, DATE(NOW()), 'DOWN_VOTE', 100, 101),
       (12, DATE(NOW()), 'DOWN_VOTE', 100, 101),
       (13, DATE(NOW()), 'UP_VOTE', 101, 101),
       (14, DATE(NOW()), 'UP_VOTE', 101, 101),
       (15, DATE(NOW()), 'UP_VOTE', 101, 101),
       (16, DATE(NOW()), 'UP_VOTE', 101, 101),
       (17, DATE(NOW()), 'UP_VOTE', 101, 101),
       (18, DATE(NOW()), 'UP_VOTE', 101, 101),
       (19, DATE(NOW()), 'UP_VOTE', 101, 101),
       (20, DATE(NOW()), 'UP_VOTE', 101, 101),
       (21, DATE(NOW()), 'DOWN_VOTE', 101, 101),
       (22, DATE(NOW()), 'DOWN_VOTE', 101, 101),
       (23, DATE(NOW()), 'DOWN_VOTE', 101, 101),
       (24, DATE(NOW()), 'DOWN_VOTE', 105, 101),
       (29, DATE(NOW()), 'DOWN_VOTE', 114, 101),
       (28, DATE(NOW()), 'DOWN_VOTE', 114, 101),
       (30, DATE(NOW()), 'DOWN_VOTE', 103, 101),
       (31, DATE(NOW()), 'DOWN_VOTE', 103, 101),
       (32, DATE(NOW()), 'DOWN_VOTE', 103, 101),
       (63, DATE(NOW()), 'UP_VOTE', 111, 101),
       (61, DATE(NOW()), 'UP_VOTE', 111, 101),
       (60, DATE(NOW()), 'UP_VOTE', 111, 101);



insert into question_viewed(id, persist_date, question_id, user_id)
values (1, DATE(NOW()), 100, 101),
       (2, DATE(NOW()), 100, 101),
       (3, DATE(NOW()), 100, 101),
       (4, DATE(NOW()), 100, 101),
       (5, DATE(NOW()), 100, 101),
       (6, DATE(NOW()), 100, 101),
       (7, DATE(NOW()), 100, 101),
       (8, DATE(NOW()), 100, 101),
       (9, DATE(NOW()), 100, 101),
       (10, DATE(NOW()), 100, 101),
       (11, DATE(NOW()), 100, 101),
       (12, DATE(NOW()), 101, 101),
       (13, DATE(NOW()), 105, 101),
       (16, DATE(NOW()), 114, 101),
       (17, DATE(NOW()), 114, 101),
       (20, DATE(NOW()), 103, 101),
       (21, DATE(NOW()), 103, 101),
       (51, DATE(NOW()), 111, 101),
       (50, DATE(NOW()), 111, 101);


INSERT INTO answer (id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date,
                    update_date, question_id, user_id, moderator_id) VALUES
    (100, DATE(NOW()), 'Answer 102', false, false, false, DATE(NOW()), DATE(NOW()), 101, 101, null),
    (101, DATE(NOW()), 'Answer 102', false, false, false, DATE(NOW()), DATE(NOW()), 100, 101, null),
    (104, DATE(NOW()), 'Answer 102', false, false, false, DATE(NOW()), DATE(NOW()), 105, 101, null),
    (105, DATE(NOW()), 'Answer 102', false, false, false, DATE(NOW()), DATE(NOW()), 105, 101, null),
    (102, DATE(NOW()), 'Answer 102', false, false, false, DATE(NOW()), DATE(NOW()), 100, 101, null),
    (103, DATE(NOW()), 'Answer 102', false, false, false, DATE(NOW()), DATE(NOW()), 114, 101, null),
    (111, DATE(NOW()), 'Answer 102', false, false, false, DATE(NOW()), DATE(NOW()), 114, 101, null),
    (109, DATE(NOW()), 'Answer 102', false, false, false, DATE(NOW()), DATE(NOW()), 114, 101, null),
    (112, DATE(NOW()), 'Answer 102', false, false, false, DATE(NOW()), DATE(NOW()), 103, 101, null),
    (113, DATE(NOW()), 'Answer 102', false, false, false, DATE(NOW()), DATE(NOW()), 103, 101, null),
    (121, DATE(NOW()), 'Answer 102', false, false, false, DATE(NOW()), DATE(NOW()), 111, 101, null),
    (120, DATE(NOW()), 'Answer 102', false, false, false, DATE(NOW()), DATE(NOW()), 111, 101, null);

