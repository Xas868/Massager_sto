/*
 *  Table: role
 */
INSERT INTO role (id, name)
VALUES (100, 'ROLE_USER');

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
        null, null, null, 'user_101', '$2a$10$fyHD1Ut8.knrIds5S7/ee.Q5S5c.NQ3sr8k0YzUXsitEbGK.a7JZi', DATE(NOW()), 100),

       (120, 'I am test user', 'Moskow', 'user120@mail.ru', 'User 120', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_120', '$2a$12$7wzYir/6IUMdX9OBbEP1fui1vX0nfRDHlYJNACxbiefhkei.8uUna', DATE(NOW()), 100);

/*
 *  Table: question
 */
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (105, 'What do you think about question 100?', false, DATE(NOW()), DATE(NOW()), 'Question 105', 101),
       (100, 'What do you think about question 100?', false, DATE(NOW()), DATE(NOW()), 'Question 100', 101),
       (101, 'What do you think about question 101?', false, DATE(NOW()), DATE(NOW()), 'Question 101', 101),
       (102, 'What do you think about question 102?', false, DATE(NOW()), DATE(NOW()), 'Question 102', 101),
       (103, 'What do you think about question 103?', false, DATE(NOW()), DATE(NOW()), 'Question 103', 101),
       (104, 'What do you think about question 104?', false, DATE(NOW()), DATE(NOW()), 'Question 104', 101);

INSERT INTO answer (id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date,
                    update_date, question_id, user_id, moderator_id)
VALUES
    (101, CURRENT_TIMESTAMP, 'Answer 102', false, false, false, CURRENT_TIMESTAMP, DATE(NOW()), 101, 101, null),
    (104, CURRENT_TIMESTAMP, 'Answer 102', false, false, false, CURRENT_TIMESTAMP + interval '1 hour', DATE(NOW()), 100, 101, null),
    (100, CURRENT_TIMESTAMP, 'Answer 102', false, false, false, CURRENT_TIMESTAMP + interval '1 minute', DATE(NOW()), 100, 101, null),
    (105, CURRENT_TIMESTAMP, 'Answer 102', false, false, false, CURRENT_TIMESTAMP + interval '2 minute', DATE(NOW()), 103, 101, null),
    (102, CURRENT_TIMESTAMP, 'Answer 102', false, false, false, CURRENT_TIMESTAMP + interval '3 minute', DATE(NOW()), 100, 101, null),
    (107, CURRENT_TIMESTAMP, 'Answer 107', false, false, false, CURRENT_TIMESTAMP + interval '4 minute', DATE(NOW()), 100, 101, null),
    (108, CURRENT_TIMESTAMP, 'Answer 108', false, false, false, CURRENT_TIMESTAMP + interval '5 minute', DATE(NOW()), 100, 101, null),
    (109, CURRENT_TIMESTAMP, 'Answer 109', false, false, false, CURRENT_TIMESTAMP + interval '6 minute', DATE(NOW()), 100, 101, null),
    (110, CURRENT_TIMESTAMP, 'Answer 110', false, false, false, CURRENT_TIMESTAMP + interval '7 minute', DATE(NOW()), 102, 101, null),
    (111, CURRENT_TIMESTAMP, 'Answer 111', false, false, false, CURRENT_TIMESTAMP + interval '8 minute', DATE(NOW()), 102, 101, null),
    (112, CURRENT_TIMESTAMP, 'Answer 112', false, false, false, CURRENT_TIMESTAMP + interval '9 minute', DATE(NOW()), 102, 101, null),
    (113, CURRENT_TIMESTAMP, 'Answer 113', false, false, false, CURRENT_TIMESTAMP + interval '10 minute', DATE(NOW()), 102, 101, null),
    (114, CURRENT_TIMESTAMP, 'Answer 114', false, false, false, CURRENT_TIMESTAMP + interval '11 minute', DATE(NOW()), 102, 101, null),
    (115, CURRENT_TIMESTAMP, 'Answer 115', false, false, false, CURRENT_TIMESTAMP + interval '12 minute', DATE(NOW()), 102, 101, null),
    (116, CURRENT_TIMESTAMP, 'Answer 116', false, false, false, CURRENT_TIMESTAMP + interval '13 minute', DATE(NOW()), 102, 101, null),
    (117, CURRENT_TIMESTAMP, 'Answer 117', false, false, false, CURRENT_TIMESTAMP + interval '14 minute', DATE(NOW()), 102, 101, null),
    (118, CURRENT_TIMESTAMP, 'Answer 118', false, false, false, CURRENT_TIMESTAMP + interval '15 minute', DATE(NOW()), 102, 101, null),
    (119, CURRENT_TIMESTAMP, 'Answer 119', false, false, false, CURRENT_TIMESTAMP + interval '16 minute', DATE(NOW()), 102, 101, null),
    (120, CURRENT_TIMESTAMP, 'Answer 120', false, false, false, CURRENT_TIMESTAMP + interval '17 minute', DATE(NOW()), 102, 101, null),
    (121, CURRENT_TIMESTAMP, 'Answer 121', false, false, false, CURRENT_TIMESTAMP + interval '18 minute', DATE(NOW()), 102, 101, null),
    (122, CURRENT_TIMESTAMP, 'Answer 122', false, false, false, CURRENT_TIMESTAMP + interval '19 minute', DATE(NOW()), 102, 101, null);

/*
 *  Table: tag
 */
INSERT INTO tag (id, description, name, persist_date)
VALUES (100, 'Description of tag 1', 'vfOxMU1', '2022-11-12 22:09:06.639083'),
       (101, 'Description of tag 2', 'iThKcj2', '2022-11-12 22:09:06.639579'),
       (103, 'Description of tag 3', 'iThKcj3', '2022-11-12 22:09:06.639579'),
       (104, 'Description of tag 4', 'iThKcj4', '2022-11-12 22:09:06.639579'),
       (102, 'Description of tag 3', 'LTGDJP3', '2022-11-12 22:09:06.639579');

insert into tag_tracked(id, persist_date, tracked_tag_id, user_id)
values
    (1, CURRENT_TIMESTAMP, 100, 101),
    (2, CURRENT_TIMESTAMP, 101, 101),
    (3, CURRENT_TIMESTAMP, 102, 101),
    (4, CURRENT_TIMESTAMP, 103, 101),
    (5, CURRENT_TIMESTAMP, 104, 100);

/*
 *  Table: question_has_tag
 */
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (100, 100),
       (100, 101),
       (105, 104),
       (101, 101),
       (101, 100),
       (101, 102),
       (101, 103),
       (100, 102);
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
       (24, DATE(NOW()), 'DOWN_VOTE', 105, 101);

insert into votes_on_answers(id, persist_date, vote, answer_id, user_id)
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
       (24, DATE(NOW()), 'DOWN_VOTE', 104, 101),
       (25, DATE(NOW()), 'DOWN_VOTE', 104, 101),
       (26, DATE(NOW()), 'DOWN_VOTE', 104, 101),
       (28, DATE(NOW()), 'DOWN_VOTE', 113, 101),
       (27, DATE(NOW()), 'DOWN_VOTE', 104, 101);



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
       (14, DATE(NOW()), 102, 101),
       (15, DATE(NOW()), 102, 101),
       (13, DATE(NOW()), 105, 101);

