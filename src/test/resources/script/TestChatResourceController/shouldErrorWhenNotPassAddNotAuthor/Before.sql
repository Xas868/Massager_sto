insert into role (id, name) values
    (101, 'ROLE_USER');


insert into user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id) values
    (101, 'I am test user', 'Moskow', 'user101@mail.ru', 'User 101', '/images/noUserAvatar.png', false, true, date(NOW()),
    null, null, null, 'user_101', '$2a$10$hl61sc4Ic/znptF.bwbkv.sNvGX9rr7J32DYbAUkcvn.qDOaUE1Aa', DATE(NOW()), 101),

    (112, 'I am test user', 'Moskow', 'user112@mail.ru', 'User 112', '/images/noUserAvatar.png', false, true, DATE(NOW()),
    null, null, null, 'user_112', '$2a$10$i3ToS4GhmbS1P/SZ8lxSn.K4uSbPRFCG4pgPVi2hxt.hiIGu5MJLu', DATE(NOW()), 101),

    (103, 'I am test user', 'Moskow', 'user103@mail.ru', 'User 103', '/images/noUserAvatar.png', false, true, DATE(NOW()),
     null, null, null, 'user_103', '$2a$10$gTQLHxdPUSuWyd7XcOtpN.XeQ8sCSFluKyRLeFxCSMvRZixJRtBqS', DATE(NOW()), 101);


insert into chat (id, chat_type, persist_date, is_global) values
    (101, 1, date(NOW()), false);


insert into group_chat (chat_id, title, author_id, image) values
    (101, 'GroupChat 101', 101, 'image 101');


insert into groupchat_has_users (chat_id, user_id) values
    (101, 101);