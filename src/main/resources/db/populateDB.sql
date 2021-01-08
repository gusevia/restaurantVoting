DELETE
FROM user_roles;
DELETE
FROM DISHES;
DELETE
FROM VOTES;
DELETE
FROM restaurants;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('VoteUser', 'vote_user@mail.ru', '{noop}password'),
       ('TestUser', 'test_user@mail.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('USER', 100001),
       ('USER', 100002),
       ('ADMIN', 100003),
       ('USER', 100003);

INSERT INTO restaurants(name)
VALUES ('Семифреддо'),
       ('Пушкин'),
       ('Жеральдин');

INSERT INTO DISHES(NAME, PRICE, RESTAURANT_ID, DATE)
VALUES ('Салат новогодний', 500, 100004, '2020-12-31');
INSERT INTO DISHES(NAME, PRICE, RESTAURANT_ID)
VALUES ('Салат Мимоза', 300, 100004),
       ('Суп куриный', 300, 100004),
       ('Плов', 400, 100004),
       ('Компот', 150, 100005);

INSERT INTO VOTES(USER_ID, RESTAURANT_ID, DATE)
VALUES (100000, 100004, '2020-12-31'),
       (100000, 100004, now()),
       (100001, 100005, now());
