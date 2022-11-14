INSERT INTO role (id, name)
VALUES (100, 'ROLE_USER');

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (100, 'I am test user', 'Moscow', 'user100@mail.ru', 'user100@mail.ru', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user100@mail.ru', '$2a$10$Z/TD5vCVG7amB2u34YrU8.Czni/8rtcIOBCVqP/wwrqwrnFT/qnh.', now(), 100);

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (101, 'I am test user', 'Moscow', 'user101@mail.ru', 'user101@mail.ru', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user101@mail.ru', '$2a$10$Z/TD5vCVG7amB2u34YrU8.Czni/8rtcIOBCVqP/wwrqwrnFT/qnh.', now(), 100);

INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (100, 'Some question?', false, now(), now(), 'Question 100', 100);

INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (101, 'Some question?', false, now(), now(), 'Question 101', 100);

INSERT INTO comment (id, comment_type, last_redaction_date, persist_date, text, user_id)
VALUES (100, 1, now(), '2022-10-03T00:00:00', 'Comment 100', 100);

INSERT INTO comment (id, comment_type, last_redaction_date, persist_date, text, user_id)
VALUES (101, 1, now(), '2022-10-02T00:00:00', 'Comment 101', 100);

INSERT INTO comment (id, comment_type, last_redaction_date, persist_date, text, user_id)
VALUES (102, 1, now(), '2022-10-01T00:00:00', 'Comment 102', 100);

INSERT INTO comment (id, comment_type, last_redaction_date, persist_date, text, user_id)
VALUES (103, 1, now(), '2022-10-04T00:00:00', 'Comment 103', 100);

INSERT INTO comment (id, comment_type, last_redaction_date, persist_date, text, user_id)
VALUES (104, 1, now(), '2022-10-05T00:00:00', 'Comment 104', 100);

INSERT INTO comment_question (comment_id, question_id)
VALUES (100, 100);

INSERT INTO comment_question (comment_id, question_id)
VALUES (101, 101);

INSERT INTO comment_question (comment_id, question_id)
VALUES (102, 100);

INSERT INTO comment_question (comment_id, question_id)
VALUES (103, 101);

INSERT INTO comment_question (comment_id, question_id)
VALUES (104, 100);

INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id)
VALUES (100, 9800, now(), 0, null, 100, 100, null);

INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id)
VALUES (101, 5500, now(), 0, null, 100, 100, null);

INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id)
VALUES (102, 10000, now(), 0, null, 101, 101, null);


