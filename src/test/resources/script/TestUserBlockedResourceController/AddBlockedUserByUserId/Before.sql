/*
 *  Table: role
 */
INSERT INTO role (id, name)
VALUES (1, 'ROLE_USER');

/*
 *  Table: user_entity
 */
INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES  (100, 'I am test user', 'Moskow', 'user100@mail.ru', 'User 100', '/images/noUserAvatar.png', false, true,
         DATE(NOW()),
         null, null, null, 'user100', '$2a$10$ssxQ5kVwJ25Lda0csdKpLOHR8VfU7EiDwpIvU6lizmW8XTbYxVNbi', DATE(NOW()), 1),
        (101, 'I am test user', 'Moskow', 'user101@mail.ru', 'User 101', '/images/noUserAvatar.png', false, true,
         DATE(NOW()),
         null, null, null, 'user101', '$2a$10$T9iiDty2DUhZov9RkAzQT.7Fnid1t.NBTQX4iSD8hoW5esWsz7tYe', DATE(NOW()), 1),
        (102, 'I am test user', 'Moskow', 'user102@mail.ru', 'User 102', '/images/noUserAvatar.png', false, true,
         DATE(NOW()),
         null, null, null, 'user102', '$2a$10$dSCiwIh0Xz3uYn5wvF/NKu8gGdW5zi0cIjxvepyJgVM.KwNZWRE6y', DATE(NOW()), 1),
        (103, 'I am test user', 'Moskow', 'user103@mail.ru', 'User 103', '/images/noUserAvatar.png', false, true,
         DATE(NOW()),
         null, null, null, 'user103', '$2a$10$liIaAUzcT9OWcrPp.tW.du5Ac3j6WYxz2LrS1yeQcffQRFheaE6Rm', DATE(NOW()), 1),
        (104, 'I am test user', 'Moskow', 'user104@mail.ru', 'User 104', '/images/noUserAvatar.png', false, true,
         DATE(NOW()),
         null, null, null, 'user104', '$2a$10$l0oWcbfJ/fTMw8Qt30OIM.QHNjp1IrJ.Y/.4RYv.s41BAg0etdBRq', DATE(NOW()), 1),
        (105, 'I am test user', 'Moskow', 'user105@mail.ru', 'User 105', '/images/noUserAvatar.png', false, true,
         DATE(NOW()),
         null, null, null, 'user105', '$2a$10$bAZnXSmUZrCGXtqPopa.z.WkaMYf8A9nqB5Cp7dFRre/75jQKxMv.', DATE(NOW()), 1);

/*
 *  Table: block_chat_user_list
 */
INSERT INTO block_chat_user_list (id, profile_id, blocked_id, persist_date)
VALUES (100, 101, 102, DATE(NOW())),
       (101, 101, 103, DATE(NOW())),
       (102, 101, 104, DATE(NOW()));
