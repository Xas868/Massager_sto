INSERT INTO role (id, name)
VALUES (101, 'ROLE_USER');

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (103, 'I am test user2', 'Peter', 'user103@mail.ru', 'User 103', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user_103', '$2a$10$twa4u5HMjrO5wGbOTQxHeu1vyF9InNfYYXipblquQ.q46xEPoAO.u', now(), 101);

INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (103, 'Some question 2?', false, now(), now(), 'Question 103', 103);

INSERT INTO tag (id, description, name, persist_date)
VALUES (101, 'des tag 2', 'tag 101', now());

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (103, 101);

INSERT INTO related_tag (id, child_tag, main_tag)
VALUES (101,101,101);

INSERT INTO tag_tracked (id, persist_date, tracked_tag_id, user_id)
VALUES (101, now(), 101, 103);