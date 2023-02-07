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
    (100, 'I am test user', 'Moscow', 'user100@mail.ru', 'User 100', '/images/noUserAvatar.png', false, true, now(),
    null, null, null, 'user_100', '$2a$10$Z/TD5vCVG7amB2u34YrU8.Czni/8rtcIOBCVqP/wwrqwrnFT/qnh.', now(), 100),

    (101, 'I am test user', 'Moskow', 'user101@mail.ru', 'User 101', '/images/noUserAvatar.png', false, true, DATE(NOW()),
     null, null, null, 'user_101', '$2a$10$fyHD1Ut8.knrIds5S7/ee.Q5S5c.NQ3sr8k0YzUXsitEbGK.a7JZi', DATE(NOW()), 100),

    (102, 'I am test user', 'Moskow', 'user102@mail.ru', 'User 102', '/images/noUserAvatar.png', false, true, now(),
    null, null, null, 'user_102', '$2a$10$twa4u5HMjrO5wGbOTQxHeu1vyF9InNfYYXipblquQ.q46xEPoAO.u', now(), 100);

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
    (100, '2022-12-02 10:49:13.515771', 'Answer 100', false, false, false, '2022-12-02 10:49:13.515771', DATE(NOW()), 100, 100, null),
    (101, '2022-12-03 10:49:13.515771', 'Answer 101', false, false, false, '2022-12-03 10:49:13.515771', DATE(NOW()), 100, 101, null),
    (102, '2022-12-04 10:49:13.515771', 'Answer 102', false, false, false, '2022-12-04 10:49:13.515771', DATE(NOW()), 100, 102, null);

/*
 *  Table: votes_on_answers
 */
INSERT INTO votes_on_answers (id, persist_date, vote, answer_id, user_id) VALUES
                             (100, DATE(NOW()), 'UP_VOTE', 100, 100),
                             (101, DATE(NOW()), 'DOWN_VOTE', 101, 101),
                             (102, DATE(NOW()), 'DOWN_VOTE', 102, 102),
                             (103, DATE(NOW()), 'UP_VOTE', 102, 102),
                             (104, DATE(NOW()), 'DOWN_VOTE', 102, 102),
                             (105, DATE(NOW()), 'DOWN_VOTE', 102, 102);

/*
 *  Table: comment
 */
INSERT INTO comment (id, comment_type, last_redaction_date, persist_date, text, user_id) VALUES
                    (100, 0, '2023-01-26 00:00:00', '2023-01-26 00:00:00', 'Comment 100', 100),
                    (101, 0, '2023-01-26 00:00:00', '2023-01-26 00:00:00', 'Comment 101', 101),
                    (102, 0, '2023-01-26 00:00:00', '2023-01-26 00:00:00', 'Comment 102', 102);

/*
 *  Table: comment_answer
 */
INSERT INTO comment_answer (comment_id, answer_id) VALUES
                           (100, 100),
                           (101, 101),
                           (102, 102);

/*
 *  Table: reputation
 */
INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id) VALUES
                       (100, 1000, DATE(NOW()), 1, 100, 100, 100, null),
                       (101, 2000, DATE(NOW()), 1, 101, 101, 100, null),
                       (102, 4000, DATE(NOW()), 1, 102, 102, 100, null);