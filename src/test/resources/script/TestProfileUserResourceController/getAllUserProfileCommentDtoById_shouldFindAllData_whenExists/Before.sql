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

    (102, 'I am test user', 'Moscow', 'user102@mail.ru', 'User 102', '/images/noUserAvatar.png', false, true, DATE(NOW()),
    null, null, null, 'user_102', '$2a$10$fyHD1Ut8.knrIds5S7/ee.Q5S5c.NQ3sr8k0YzUXsitEbGK.a7JZi', '2021-11-13 17:55:55.461886', 101);

/*
 *  Table: question
 */
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id) VALUES
    (101, 'What do you think about question 101?', false, DATE(NOW()), DATE(NOW()), 'Question 101', 101),
    (102, 'What do you think about question 102?', false, DATE(NOW()), DATE(NOW()), 'Question 102', 102),
    (103, 'What do you think about question 103?', false, DATE(NOW()), DATE(NOW()), 'Question 103', 101),
    (104, 'What do you think about question 104?', false, DATE(NOW()), DATE(NOW()), 'Question 104', 102);

/*
 *  Table: answer
 */
INSERT INTO answer (id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date,
                    update_date, question_id, user_id, moderator_id) VALUES
    (101, DATE(NOW()), 'Answer 101', false, false, false, DATE(NOW()), DATE(NOW()), 101, 101, null),
    (102, DATE(NOW()), 'Answer 102', false, false, false, DATE(NOW()), DATE(NOW()), 102, 102, null),
    (103, DATE(NOW()), 'Answer 103', false, false, false, DATE(NOW()), DATE(NOW()), 103, 102, null),
    (104, DATE(NOW()), 'Answer 104', false, false, false, DATE(NOW()), DATE(NOW()), 104, 102, null);


/*
 *  Table: comment
 */

INSERT INTO comment (id, comment_type, last_redaction_date, persist_date, text, user_id) VALUES
    (101, 0, DATE(NOW()), DATE(NOW()), 'Random CommentAnswer 1', 101),
    (102, 0, DATE(NOW()), DATE(NOW()), 'Random CommentAnswer 2', 101),
    (103, 1, DATE(NOW()), DATE(NOW()), 'Random CommentQuestion 1', 101),
    (104, 1, DATE(NOW()), DATE(NOW()), 'Random CommentQuestion 2', 101);

/*
 *  Table: comment_answer
 */

INSERT INTO comment_answer (comment_id, answer_id) VALUES
    (101, 101),
    (102, 102);

/*
 *  Table: comment_question
 */

INSERT INTO comment_question (comment_id, question_id) VALUES
    (103, 101),
    (104, 102);