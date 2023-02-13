/*
 *  Table: role
 */
INSERT INTO role (id, name)
VALUES (1, 'ROLE_USER');

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
 *  Table: chat
 */
INSERT INTO chat (id, chat_type, persist_date, is_global)
VALUES
    (100, 1, DATE(NOW()), false),
    (101, 1, DATE(NOW()), false),
    (102, 1, DATE(NOW()), false),
    (103, 1, DATE(NOW()), false),
    (104, 1, DATE(NOW()), false),
    (105, 1, DATE(NOW()), false),
    (106, 1, DATE(NOW()), false),
    (107, 1, DATE(NOW()), false),
    (108, 1, DATE(NOW()), false),
    (109, 1, DATE(NOW()), false),
    (110, 1, DATE(NOW()), false),
    (111, 1, DATE(NOW()), false),
    (112, 1, DATE(NOW()), false),
    (113, 1, DATE(NOW()), false);
/*
 *  Table: block_chat_user_list
 */
INSERT INTO block_chat_user_list (id, profile_id, blocked_id, persist_date)
VALUES (100, 102, 102, DATE(NOW())),
       (101, 103, 103, DATE(NOW())),
       (102, 103, 104, DATE(NOW())),
       (103, 100, 101, DATE(NOW()));


/*
 *  Table: single_chat
 */
INSERT INTO single_chat (chat_id, use_two_id, user_one_id, user_one_is_deleted,  user_two_is_deleted)
VALUES (100, 100, 102, false, false),
       (102, 100, 103, false, false),
       (103, 100, 104, false, false),
       (104, 100, 105, false, false),
       (105, 101, 100, false, false),
       (106, 101, 102, false, false),
       (107, 101, 103, false, false),
       (108, 101, 104, false, false),
       (109, 101, 105, false, false),
       (110, 102, 100, false, false),
       (111, 102, 101, false, false),
       (112, 102, 103, false, false),
       (113, 102, 104, false, false);
