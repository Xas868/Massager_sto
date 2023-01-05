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
    null, null, null, 'user_102', '$2a$10$fyHD1Ut8.knrIds5S7/ee.Q5S5c.NQ3sr8k0YzUXsitEbGK.a7JZi', '2021-11-13 17:55:55.461886', 101),

    (103, 'I am test user', 'Moscow', 'user103@mail.ru', 'User 103', '/images/noUserAvatar.png', false, true, DATE(NOW()),
     null, null, null, 'user_103', '$2a$10$EMnv6R2rQeKo1ih75/vr2OW.WKVDHVIvtQ/50w4brxSjBWOi.gMqS', '2022-11-12 17:55:55.461886', 101),

    (104, 'I am test user', 'Moscow', 'user104@mail.ru', 'User 104', '/images/noUserAvatar.png', false, true, DATE(NOW()),
     null, null, null, 'user_104', '$2a$10$TTYhwHF1QXxMV5UekMMuhudbNtVtJUwytwY4p4iaOmC0Ob4Lo0Yp.', '2022-10-12 17:55:55.461886', 101),

    (105, 'I am test user', 'Moscow', 'user105@mail.ru', 'User 105', '/images/noUserAvatar.png', false, true, DATE(NOW()),
     null, null, null, 'user_105', '$2a$10$Dhv/NrypItLBzxl0ZEr/euSDZ5p6gs1qlQSI7SCrWggi8kCFLWBw6', '2022-05-12 17:55:55.461886', 101),

    (106, 'I am test user', 'Moscow', 'user106@mail.ru', 'User 106', '/images/noUserAvatar.png', false, true, DATE(NOW()),
     null, null, null, 'user_106', '$2a$10$6FqiClazINIE4C.Te46ah.njD84Uh92M0ThrIFf2FswBrDjk9Sofa', '2022-11-12 17:25:55.461886', 102),

    (107, 'I am test user', 'Moscow', 'user107@mail.ru', 'User 107', '/images/noUserAvatar.png', false, true, DATE(NOW()),
     null, null, null, 'user_107', '$2a$10$xRacvUEmfsRdsP8aBy1/Y.d9j3c4u7wInOEKb5hQlQSgsgT6tnw8W', '2022-11-12 18:55:55.461886', 102),

    (108, 'I am test user', 'Moscow', 'user108@mail.ru', 'User 108', '/images/noUserAvatar.png', false, true, DATE(NOW()),
     null, null, null, 'user_108', '$2a$10$xZPFM9fGuVNOTpM1Amcyf.XYsm27ghtyycsHQXSqhC3YkUIkwmFL2', '2022-11-12 07:55:55.461886', 102),

    (109, 'I am test user', 'Moscow', 'user109@mail.ru', 'User 109', '/images/noUserAvatar.png', false, true, DATE(NOW()),
     null, null, null, 'user_109', '$2a$10$A4XPUipJCy98CZgxOtFEtOyMBhOoPxVyAUYxXFHhVYThyV79Xwh62', '2018-11-12 17:55:55.461886', 102),

    (110, 'I am test user', 'Moscow', 'user110@mail.ru', 'User 110', '/images/noUserAvatar.png', false, true, DATE(NOW()),
     null, null, null, 'user_110', '$2a$10$xrlkNgwqtuCR5dszNG/yEOp5QffdvpAxYD.gCY70YVi4uogJYdQVu', '2021-01-10 17:55:55.461886', 102);

/*
 *  Table: question
 */
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id) VALUES
    (101, 'What do you think about question 101?', false, DATE(NOW()), DATE(NOW()), 'Question 101', 101),
    (102, 'What do you think about question 102?', false, DATE(NOW()), DATE(NOW()), 'Question 102', 102),
    (103, 'What do you think about question 103?', false, DATE(NOW()), DATE(NOW()), 'Question 103', 103),
    (104, 'What do you think about question 104?', false, DATE(NOW()), DATE(NOW()), 'Question 104', 104),
    (105, 'What do you think about question 105?', false, DATE(NOW()), DATE(NOW()), 'Question 105', 105),
    (106, 'What do you think about question 106?', false, DATE(NOW()), DATE(NOW()), 'Question 106', 106),
    (107, 'What do you think about question 107?', false, DATE(NOW()), DATE(NOW()), 'Question 107', 107),
    (108, 'What do you think about question 108?', false, DATE(NOW()), DATE(NOW()), 'Question 108', 108);

/*
 *  Table: bookmarks
    Test user: user101
    Total count of bookmarks: 3
 */

INSERT INTO bookmarks (id, question_id, user_id) VALUES
    (101, 101, 101),
    (102, 102, 101),
    (103, 103, 101),
    (104, 104, 102),
    (105, 105, 103),
    (106, 106, 106),
    (107, 107, 107),
    (108, 108, 108);



/*
 *  Table: tag
 */
INSERT INTO tag (id, description, name, persist_date) VALUES
    (101, 'Description of tag 1', 'vfOxMU1', '2022-11-12 22:09:06.639083'),
    (102, 'Description of tag 2', 'iThKcj2', '2022-11-12 22:09:06.639579'),
    (103, 'Description of tag 3', 'LTGDJP3', '2022-11-12 22:09:06.639579'),

    (104, 'Description of tag 4', 'vfOxMU4', '2022-11-12 22:09:06.639083'),
    (105, 'Description of tag 5', 'iThKcj5', '2022-11-12 22:09:06.639579'),
    (106, 'Description of tag 6', 'LTGDJP6', '2022-11-12 22:09:06.639579'),

    (107, 'Description of tag 7', 'vfOxMU7', '2022-11-12 22:09:06.639083'),
    (108, 'Description of tag 8', 'iThKcj8', '2022-11-12 22:09:06.639579'),
    (109, 'Description of tag 9', 'LTGDJP9', '2022-11-12 22:09:06.639579'),

    (110, 'Description of tag 10', 'vfOxMU10', '2022-11-12 22:09:06.639083'),
    (111, 'Description of tag 11', 'iThKcj11', '2022-11-12 22:09:06.639579'),
    (112, 'Description of tag 12', 'LTGDJP12', '2022-11-12 22:09:06.639579');

/*
 *  Table: question_has_tag
 */
INSERT INTO question_has_tag (question_id, tag_id) VALUES
    (101, 101),
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
INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id) VALUES
    (101, 1000, DATE(NOW()), 2, null, 101, 101, null),
    (102, 900, DATE(NOW()), 3, null, 102, 102, null),
    (103, 8000, DATE(NOW()), 2, null, 103, 103, null),
    (104, 700, DATE(NOW()), 3, null, 104, 104, null),
    (105, 600, DATE(NOW()), 2, null, 105, 101, null),
    (106, 500, DATE(NOW()), 3, null, 106, 102, null),
    (107, 400, DATE(NOW()), 2, null, 107, 103, null),
    (108, 300, DATE(NOW()), 3, null, 108, 104, null),
    (109, 2000, DATE(NOW()), 2, null, 109, 101, null),
    (110, 100, DATE(NOW()), 3, null, 110, 102, null);

