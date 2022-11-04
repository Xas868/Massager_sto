INSERT INTO role (id, name)
VALUES (102, 'ROLE_USER');

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (104, 'I am test user 3', 'Samara', 'user104@mail.ru', 'User 104', '/images/noUserAvatar.png', false, true, now(),
        null, null, null, 'user_104', '$2a$10$twa4u5HMjrO5wGbOTQxHeu1vyF9InNfYYXipblquQ.q46xEPoAO.u', now(), 102);

INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (104, 'Some question 3?', false, now(), now(), 'Question 104', 104);

INSERT INTO tag (id, description, name, persist_date)
VALUES (102, 'des tag 3', 'tag 102', now());

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (104, 102);

INSERT INTO related_tag (id, child_tag, main_tag)
VALUES (102, 102, 102);

INSERT INTO tag_tracked (id, persist_date, tracked_tag_id, user_id)
VALUES (102, now(), 102, 104);