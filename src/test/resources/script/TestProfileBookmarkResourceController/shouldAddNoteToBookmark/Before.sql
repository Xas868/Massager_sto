
INSERT INTO role (id, name)
VALUES (1, 'ROLE_USER');

INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (100, 'I am test user', 'Moskow', 'user100@mail.ru', 'User 100', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_100', '$2a$10$ssxQ5kVwJ25Lda0csdKpLOHR8VfU7EiDwpIvU6lizmW8XTbYxVNbi', DATE(NOW()), 1),

       (101, 'I am test user', 'Moskow', 'user101@mail.ru', 'User 101', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_101', '$2a$10$s7z8pw9xQqMrEHmev45Ai.nCzFg.oizEFf.V5V7awF.DjBricHvYK', DATE(NOW()), 1);


INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (101, 'Some question?', false, now(), now(), 'Question 101', 100),
       (102, 'Some question?', false, now(), now(), 'Question 101', 101);


insert into bookmarks(id, question_id, user_id)
values (100, 101, 100),
       (101, 102, 101);
