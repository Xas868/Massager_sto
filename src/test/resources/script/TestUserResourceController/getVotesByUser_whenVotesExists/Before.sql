INSERT INTO role (id, name) VALUES (101, 'ROLE_ADMIN'),
                                   (102, 'ROLE_USER');

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (101, 'I am test user', 'Moscow', 'user101@mail.ru', 'User 101', '/images/noUserAvatar.png', false, true, DATE (NOW()),
        null, null, null, 'user_101', '$2a$10$fyHD1Ut8.knrIds5S7/ee.Q5S5c.NQ3sr8k0YzUXsitEbGK.a7JZi', DATE (NOW()),
        102),

       (102, 'I am test user', 'Moscow', 'user102@mail.ru', 'User 102', '/images/noUserAvatar.png', false, true, DATE (NOW()),
        null, null, null, 'user_102', '$2a$10$fyHD1Ut8.knrIds5S7/ee.Q5S5c.NQ3sr8k0YzUXsitEbGK.a7JZi', DATE (NOW()),
        101),

       (103, 'I am test user', 'Moscow', 'user103@mail.ru', 'User 103', '/images/noUserAvatar.png', false, true, DATE (NOW()),
        null, null, null, 'user_103', '$2a$10$EMnv6R2rQeKo1ih75/vr2OW.WKVDHVIvtQ/50w4brxSjBWOi.gMqS', DATE (NOW()),
        101),

       (104, 'I am test user', 'Moscow', 'user104@mail.ru', 'User 104', '/images/noUserAvatar.png', false, true, DATE (NOW()),
        null, null, null, 'user_104', '$2a$10$TTYhwHF1QXxMV5UekMMuhudbNtVtJUwytwY4p4iaOmC0Ob4Lo0Yp.', DATE (NOW()),
        101),

       (105, 'I am test user', 'Moscow', 'user105@mail.ru', 'User 105', '/images/noUserAvatar.png', false, true, DATE (NOW()),
        null, null, null, 'user_105', '$2a$10$Dhv/NrypItLBzxl0ZEr/euSDZ5p6gs1qlQSI7SCrWggi8kCFLWBw6', DATE (NOW()),
        101),

       (106, 'I am test user', 'Moscow', 'user106@mail.ru', 'User 106', '/images/noUserAvatar.png', false, true, DATE (NOW()),
        null, null, null, 'user_106', '$2a$10$6FqiClazINIE4C.Te46ah.njD84Uh92M0ThrIFf2FswBrDjk9Sofa', DATE (NOW()),
        102),

       (107, 'I am test user', 'Moscow', 'user107@mail.ru', 'User 107', '/images/noUserAvatar.png', false, true, DATE (NOW()),
        null, null, null, 'user_107', '$2a$10$xRacvUEmfsRdsP8aBy1/Y.d9j3c4u7wInOEKb5hQlQSgsgT6tnw8W', DATE (NOW()),
        102),

       (108, 'I am test user', 'Moscow', 'user108@mail.ru', 'User 108', '/images/noUserAvatar.png', false, true, DATE (NOW()),
        null, null, null, 'user_108', '$2a$10$xZPFM9fGuVNOTpM1Amcyf.XYsm27ghtyycsHQXSqhC3YkUIkwmFL2', DATE (NOW()),
        102),

       (109, 'I am test user', 'Moscow', 'user109@mail.ru', 'User 109', '/images/noUserAvatar.png', false, true, DATE (NOW()),
        null, null, null, 'user_109', '$2a$10$A4XPUipJCy98CZgxOtFEtOyMBhOoPxVyAUYxXFHhVYThyV79Xwh62', DATE (NOW()),
        102),

       (110, 'I am test user', 'Moscow', 'user110@mail.ru', 'User 110', '/images/noUserAvatar.png', false, true, DATE (NOW()),
        null, null, null, 'user_110', '$2a$10$xrlkNgwqtuCR5dszNG/yEOp5QffdvpAxYD.gCY70YVi4uogJYdQVu', DATE (NOW()),
        102);

INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (101, 'What do you think about question 101?', false, DATE (NOW()), DATE (NOW()), 'Question 101', 101),
       (102, 'What do you think about question 102?', false, DATE (NOW()), DATE (NOW()), 'Question 102', 102),
       (103, 'What do you think about question 103?', false, DATE (NOW()), DATE (NOW()), 'Question 103', 103),
       (104, 'What do you think about question 104?', false, DATE (NOW()), DATE (NOW()), 'Question 104', 104);

INSERT INTO answer (id, date_accept_time, html_body, is_deleted,
                    is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id, moderator_id)
VALUES (101, DATE (NOW()), 'Answer 1', false, false, false, DATE (NOW()), DATE (NOW()), 101, 101, null),
       (102, DATE (NOW()), 'Answer 2', false, false, false, DATE (NOW()), DATE (NOW()), 102, 102, null),
       (103, DATE (NOW()), 'Answer 3', false, false, false, DATE (NOW()), DATE (NOW()), 103, 103, null),
       (104, DATE (NOW()), 'Answer 4', false, false, false, DATE (NOW()), DATE (NOW()), 104, 104, null);



INSERT INTO votes_on_answers (id, persist_date, vote, answer_id, user_id)
VALUES (121, DATE (NOW()), 'UP_VOTE', 101, 101),
       (122, DATE (NOW()), 'UP_VOTE', 101, 101),
       (123, DATE (NOW()), 'DOWN_VOTE', 101, 101);


INSERT INTO votes_on_questions (id, persist_date, vote, question_id, user_id)
VALUES (121, DATE (NOW()), 'UP_VOTE', 101, 101),
       (122, DATE (NOW()), 'UP_VOTE', 101, 101),
       (123, DATE (NOW()), 'DOWN_VOTE', 101, 101);
