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
VALUES (100, 'I am first test user', 'Moskow', 'user100@mail.ru', 'User 100', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_100', '$2a$10$ssxQ5kVwJ25Lda0csdKpLOHR8VfU7EiDwpIvU6lizmW8XTbYxVNbi', DATE(NOW()), 100),

       (101, 'I am second test user', 'Moskow', 'user101@mail.ru', 'User 101', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_101', '$2a$10$fyHD1Ut8.knrIds5S7/ee.Q5S5c.NQ3sr8k0YzUXsitEbGK.a7JZi', DATE(NOW()), 100);


/*
 *  Table: question
 */
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (105, 'What do you think about question 100?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '1  minute',
        'Question 105', 101),
       (100, 'What do you think about question 100?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '2  minute',
        'Question 100', 101),
       (101, 'What do you think about question 101?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '3  minute',
        'Question 101', 101),
       (102, 'What do you think about question 102?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '4  minute',
        'Question 102', 101),
       (103, 'What do you think about question 103?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '5  minute',
        'Question 103', 101),
       (104, 'What do you think about question 104?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '6  minute',
        'Question 104', 101),
       (106, 'What do you think about question 106?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '7  minute',
        'Question 106', 101),
       (107, 'What do you think about question 107?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '8  minute',
        'Question 107', 101),
       (108, 'What do you think about question 108?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '9  minute',
        'Question 108', 101),
       (109, 'What do you think about question 109?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '10  minute',
        'Question 109', 101),
       (110, 'What do you think about question 110?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '11  minute',
        'Question 110', 101),
       (111, 'What do you think about question 111?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '12  minute',
        'Question 111', 101),
       (112, 'What do you think about question 112?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '13  minute',
        'Question 112', 101),
       (113, 'What do you think about question 113?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '14  minute',
        'Question 113', 101),
       (114, 'What do you think about question 114?', false, DATE(NOW()), CURRENT_TIMESTAMP + interval '1 hour',
        'Question 120', 101);

/*
 *  Table: answer
 */
INSERT INTO answer (id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date,
                    update_date, question_id, user_id, moderator_id)
VALUES (18, DATE(NOW()), 'Answer 102', false, false, false, DATE(NOW()), DATE(NOW()), 101, 101, null);

/*
 *  Table: reputation
 */
INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id)
VALUES (100, 10, DATE(NOW()), 2, 18, 100, 108, null),
       (101, 10, DATE(NOW()), 4, 18, 101, 106, null),
       (102, -5, DATE(NOW()), 3, 18, 101, 109, null),
       (103, 10, DATE(NOW()), 4, 18, 100, 107, null),
       (104, -5, DATE(NOW()), 5, 18, 100, 111, null),
       (105, 10, DATE(NOW()), 4, 18, 101, 108, null),
       (106, -5, DATE(NOW()), 3, 18, 100, 108, null),
       (107, -5, DATE(NOW()), 5, 18, 101, 108, null),
       (108, 10, DATE(NOW()), 4, 18, 101, 106, null),
       (109, -5, DATE(NOW()), 3, 18, 101, 109, null),
       (110, 10, DATE(NOW()), 4, 18, 100, 107, null),
       (111, -5, DATE(NOW()), 5, 18, 100, 111, null),
       (112, 10, DATE(NOW()), 4, 18, 101, 108, null),
       (113, -5, DATE(NOW()), 3, 18, 100, 108, null),
       (114, -5, DATE(NOW()), 5, 18, 101, 108, null),
       (115, 10, DATE(NOW()), 4, 18, 101, 106, null),
       (116, -5, DATE(NOW()), 3, 18, 101, 109, null),
       (117, 10, DATE(NOW()), 4, 18, 100, 107, null),
       (118, -5, DATE(NOW()), 5, 18, 100, 111, null),
       (119, 10, DATE(NOW()), 4, 18, 101, 108, null),
       (120, -5, DATE(NOW()), 3, 18, 100, 108, null),
       (121, -5, DATE(NOW()), 5, 18, 101, 108, null),
       (122, 10, DATE(NOW()), 4, 18, 101, 106, null),
       (123, -5, DATE(NOW()), 3, 18, 101, 109, null),
       (124, 10, DATE(NOW()), 4, 18, 100, 107, null),
       (125, 10, DATE(NOW()), 2, 18, 101, 108, null);


