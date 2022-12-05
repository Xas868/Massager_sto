insert into role (id, name) values
    (101, 'ROLE_USER'),
    (102, 'ROLE_ADMIN');


insert into user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id) values
    (121, 'I am test user', 'Moskow', 'user121@mail.ru', 'User 121', '/images/noUserAvatar.png', false, true, date(NOW()),
    null, null, null, 'user_121', '$2a$10$OaWrEgKJzULQ7ku6/YV2Guq1AmE7TjsXWtrUCP6G.2oIRulMjvl.O', DATE(NOW()), 102),

    (122, 'I am test user', 'Moskow', 'user122@mail.ru', 'User 122', '/images/noUserAvatar.png', false, true, DATE(NOW()),
    null, null, null, 'user_122', '$2a$10$xtAUaonuWEY5V5JN8tnLC.pBbgR.07pEN6T5gD0K7SDZyuwg.P726', DATE(NOW()), 101);


insert into chat (id, chat_type, persist_date, is_global) values
    (101, 1, date(NOW()), false);


insert into group_chat (chat_id, title, author_id, image) values
    (101, 'GroupChat 101', 121, 'image 101');


insert into groupchat_has_users (chat_id, user_id) values
    (101, 121);