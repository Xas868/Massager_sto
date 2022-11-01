INSERT INTO role (id, name)
VALUES (100, 'ROLE_USER');

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (102, 'I am test user', 'Moskow', 'user102@mail.ru', 'User 102', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user_102', '$2a$10$twa4u5HMjrO5wGbOTQxHeu1vyF9InNfYYXipblquQ.q46xEPoAO.u', now(), 100);

INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (102, 'Some question?', false, now(), now(), 'Question 101', 102);

INSERT INTO tag (id, description, name, persist_date)
VALUES (100, 'des tag', 'tag 100', now());

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (102, 100);

INSERT INTO related_tag (id, child_tag, main_tag)
VALUES (100,100,100);

INSERT INTO tag_tracked (id, persist_date, tracked_tag_id, user_id)
VALUES (100, now(), 100, 102);