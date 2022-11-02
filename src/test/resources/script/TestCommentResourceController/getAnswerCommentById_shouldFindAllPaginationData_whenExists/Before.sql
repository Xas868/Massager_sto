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
    (100, 'What do you think about question 100?', false, DATE(NOW()), DATE(NOW()), 'Question 100', 100);

/*
 *  Table: answer
 */
INSERT INTO answer (id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date,
                        update_date, question_id, user_id, moderator_id) VALUES
    (100, DATE(NOW()), 'Answer 102', false, false, false, DATE(NOW()), DATE(NOW()), 100, 101, null);

/*
 *  Table: comment
 */
INSERT INTO comment (id, comment_type, last_redaction_date, persist_date, text, user_id) VALUES
    (100, 1, DATE(NOW()), DATE(NOW()), 'Comment 100', 100),
    (101, 1, DATE(NOW()), DATE(NOW()), 'Comment 101', 101),
    (102, 1, DATE(NOW()), DATE(NOW()), 'Comment 102', 100),
    (103, 1, DATE(NOW()), DATE(NOW()), 'Comment 103', 101),
    (104, 1, DATE(NOW()), DATE(NOW()), 'Comment 104', 100),
    (105, 1, DATE(NOW()), DATE(NOW()), 'Comment 105', 101),
    (106, 1, DATE(NOW()), DATE(NOW()), 'Comment 106', 100),
    (107, 1, DATE(NOW()), DATE(NOW()), 'Comment 107', 101),
    (108, 1, DATE(NOW()), DATE(NOW()), 'Comment 108', 100),
    (109, 1, DATE(NOW()), DATE(NOW()), 'Comment 109', 101);

/*
 *  Table: comment_answer
 */
INSERT INTO comment_answer (comment_id, answer_id) VALUES
    (100, 100),
    (101, 100),
    (102, 100),
    (103, 100),
    (104, 100),
    (105, 100),
    (106, 100),
    (107, 100),
    (108, 100),
    (109, 100);

/*
 *  Table: reputation
 */
INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id) VALUES
    (100, 1000, DATE(NOW()), 1, 100, 100, null, null),
    (101, 900, DATE(NOW()), 1, 100, 101, null, null),
    (102, 800, DATE(NOW()), 1, 100, 100, null, null);