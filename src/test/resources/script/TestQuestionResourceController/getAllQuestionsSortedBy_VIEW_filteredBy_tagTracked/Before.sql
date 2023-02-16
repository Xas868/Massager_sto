/*
 *  Table: role
 */
INSERT INTO role (id, name)
VALUES (101, 'ROLE_ADMIN'),
       (102, 'ROLE_USER');

/*
 *  Table: user_entity
 */
INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES  (100, 'I am test user', 'Moskow', 'user100@mail.ru', 'User 100', '/images/noUserAvatar.png', false, true,
         DATE(NOW()),
         null, null, null, 'user100', '$2a$10$ssxQ5kVwJ25Lda0csdKpLOHR8VfU7EiDwpIvU6lizmW8XTbYxVNbi', DATE(NOW()), 102),
        (101, 'I am test user', 'Moskow', 'user101@mail.ru', 'User 101', '/images/noUserAvatar.png', false, true,
         DATE(NOW()),
         null, null, null, 'user101', '$2a$10$T9iiDty2DUhZov9RkAzQT.7Fnid1t.NBTQX4iSD8hoW5esWsz7tYe', DATE(NOW()), 102),
        (102, 'I am test user', 'Moskow', 'user102@mail.ru', 'User 102', '/images/noUserAvatar.png', false, true,
         DATE(NOW()),
         null, null, null, 'user102', '$2a$10$dSCiwIh0Xz3uYn5wvF/NKu8gGdW5zi0cIjxvepyJgVM.KwNZWRE6y', DATE(NOW()), 102),
        (103, 'I am test user', 'Moskow', 'user103@mail.ru', 'User 103', '/images/noUserAvatar.png', false, true,
         DATE(NOW()),
         null, null, null, 'user103', '$2a$10$liIaAUzcT9OWcrPp.tW.du5Ac3j6WYxz2LrS1yeQcffQRFheaE6Rm', DATE(NOW()), 102),
        (104, 'I am test user', 'Moskow', 'user104@mail.ru', 'User 104', '/images/noUserAvatar.png', false, true,
         DATE(NOW()),
         null, null, null, 'user104', '$2a$10$l0oWcbfJ/fTMw8Qt30OIM.QHNjp1IrJ.Y/.4RYv.s41BAg0etdBRq', DATE(NOW()), 102),
        (105, 'I am test user', 'Moskow', 'user105@mail.ru', 'User 105', '/images/noUserAvatar.png', false, true,
         DATE(NOW()),
         null, null, null, 'user105', '$2a$10$bAZnXSmUZrCGXtqPopa.z.WkaMYf8A9nqB5Cp7dFRre/75jQKxMv.', DATE(NOW()), 102);




/*
 *  Table: question
 */
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (101, 'What do you think about question 101?', false, DATE(NOW()), CURRENT_TIMESTAMP, 'Question 101', 105),
       (102, 'What do you think about question 102?', false, DATE(NOW()), CURRENT_TIMESTAMP, 'Question 102', 101),
       (103, 'What do you think about question 103?', false, DATE(NOW()), CURRENT_TIMESTAMP, 'Question 103', 104),
       (104, 'What do you think about question 104?', false, DATE(NOW()), CURRENT_TIMESTAMP, 'Question 104', 101);

/*
 *  Table: answer
 */
INSERT INTO answer (id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date,
                    update_date, question_id, user_id, moderator_id)
VALUES (101, DATE(NOW()), 'Answer 101', false, false, false, DATE(NOW()), DATE(NOW()), 101, 103, null),
       (102, DATE(NOW()), 'Answer 102', false, false, false, DATE(NOW()), DATE(NOW()), 102, 104, null),
       (103, DATE(NOW()), 'Answer 103', false, false, false, DATE(NOW()), DATE(NOW()), 103, 101, null),
       (104, DATE(NOW()), 'Answer 104', false, false, false, DATE(NOW()), DATE(NOW()), 104, 102, null),
       (105, DATE(NOW()), 'Answer 105', false, false, false, DATE(NOW()), DATE(NOW()), 101, 105, null);

/*
 *  Table: tag
 */
INSERT INTO tag (id, description, name, persist_date)
VALUES (101, 'Description of tag 1', 'vfOxMU1', '2022-11-12 22:09:06.639083'),
       (102, 'Description of tag 2', 'iThKcj2', '2022-11-12 22:09:06.639579'),
       (103, 'Description of tag 3', 'LTGDJP3', '2022-11-12 22:09:06.639579'),

       (104, 'Description of tag 4', 'vfOxMU4', '2022-11-12 22:09:06.639083'),
       (105, 'Description of tag 5', 'iThKcj5', '2022-11-12 22:09:06.639579'),
       (106, 'Description of tag 6', 'LTGDJP6', '2022-11-12 22:09:06.639579'),

       (107, 'Description of tag 7', 'vfOxMU7', '2022-11-12 22:09:06.639083'),
       (108, 'Description of tag 8', 'iThKcj8', '2022-11-12 22:09:06.639579'),
       (109, 'Description of tag 9', 'LTGDJP9', '2022-12-12 22:09:06.639579'),

       (110, 'Description of tag 10', 'vfOxMU10', '2022-12-12 22:09:06.639083'),
       (111, 'Description of tag 11', 'iThKcj11', '2023-01-12 22:09:06.639579'),
       (112, 'Description of tag 12', 'LAST_GDJP12', '2023-01-12 22:09:06.639579');

/*
 *  Table: question_has_tag
 */
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (101, 101),
       (101, 102),
       (101, 103),

       (102, 104),
       (102, 105),
       (102, 106),

       (103, 107),
       (103, 108),
       (103, 109),

       (104, 110),
       (104, 111),
       (104, 112);

/*
 *  Table: reputation
 */
INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id)
VALUES (101, 10, DATE(NOW()), 2, null, 101, 101, null),
       (102, 10, DATE(NOW()), 3, null, 102, 102, null),
       (103, 10, DATE(NOW()), 2, null, 103, 103, null),
       (104, 10, DATE(NOW()), 3, null, 104, 104, null),
       (105, 10, DATE(NOW()), 2, null, 105, 101, null),
       (106, 10, DATE(NOW()), 3, null, 103, 102, null),
       (107, 10, DATE(NOW()), 2, null, 104, 103, null),
       (108, 10, DATE(NOW()), 3, null, 101, 104, null),
       (109, 10, DATE(NOW()), 2, null, 105, 101, null),
       (110, 10, DATE(NOW()), 3, null, 101, 102, null);

/*
 *  Table: votes_on_questions
 */

INSERT INTO votes_on_questions (id, persist_date, vote, question_id, user_id)
VALUES (101, DATE(NOW()), 'DOWN_VOTE', 101, 101),
       (102, DATE(NOW()), 'UP_VOTE', 101, 101),
       (103, DATE(NOW()), 'UP_VOTE', 102, 101),
       (104, DATE(NOW()), 'UP_VOTE', 102, 101),
       (105, DATE(NOW()), 'DOWN_VOTE', 103, 101);

/*
 *  Table: votes_on_questions
 */

INSERT INTO question_viewed (id, persist_date, question_id, user_id)
VALUES (101, DATE(NOW()), 101, 101),
       (102, DATE(NOW()), 101, 101),
       (103, DATE(NOW()), 102, 103),
       (104, DATE(NOW()), 103, 104),
       (105, DATE(NOW()), 102, 101);


/*
 *  Table: tag_tracked
 */


INSERT INTO tag_tracked (id, persist_date, tracked_tag_id, user_id)
VALUES (101, DATE(NOW()), 110, 101),
       (102, DATE(NOW()), 105, 101);
