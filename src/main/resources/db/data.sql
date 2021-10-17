INSERT INTO ADDRESS(STREET, POSTAL_CODE, TOWN)
VALUES ('teststraat 1', '1000XX', 'Gotham'),
       ('teststraat 2', '2000YY', 'Chicago'),
       ('teststraat 3', '3000ZZ', 'Groningen'),
       ('teststraat 4', '4000AA', 'Amsterdam'),
       ('teststraat 5', '5000BB', 'Berlin');

INSERT INTO STUDENT (FIRST_NAME, LAST_NAME, GENDER, AGE, EMAIL, ADDRESS_ID)
VALUES ('Peppa', 'Pig', 'OTHER', '5', 'peppa@outlook.com', 1),
       ('Little', 'Princess', 'FEMALE', '4', 'princess@yahoo.com', 2),
       ('James', 'Last', 'MALE', '32', 'james_last@gmail.com', 3),
       ('Adous', 'Huxsley', 'MALE', '62', 'huxsley@gmail.com', 4),
       ('Charlie', 'Chaplin', 'MALE', '42', 'chapling@gmail.com', 5);

INSERT INTO FACULTY(NAME)
VALUES ('Psychology'),
       ('Engineering'),
       ('Science');

INSERT INTO TEACHER(FIRST_NAME, LAST_NAME, FACULTY_ID)
VALUES ('Thomas', 'Edison', 2),
       ('Albert', 'Einstein', 3),
       ('Niels', 'Bohr', 3),
       ('Sigmund', 'Freud', 1);


INSERT INTO ROLE(ID, ROLE_NAME)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_SUPER_ADMIN'),
       (3, 'ROLE_USER'),
       (4, 'ROLE_MANAGER');

INSERT INTO USER(ID, FULL_NAME, USER_NAME, PASSWORD)
VALUES (1, 'James Last', 'james@yahoo.com', '$2a$10$NhgBt.LpGX8d7dVhBtDs6eofdxEQtRcROOm7qx6zM62EjD02upU6y'),
       (2, 'Cindy Crawford', 'cindy@hotmail.com', '$2a$10$NhgBt.LpGX8d7dVhBtDs6eofdxEQtRcROOm7qx6zM62EjD02upU6y'),
       (3, 'Tony Choco', '123@yahoo.com', '$2a$10$NhgBt.LpGX8d7dVhBtDs6eofdxEQtRcROOm7qx6zM62EjD02upU6y'),
       (4, 'Tesssa de Loo', 'deloo123@gmail.com', '$2a$10$NhgBt.LpGX8d7dVhBtDs6eofdxEQtRcROOm7qx6zM62EjD02upU6y');

INSERT INTO USER_ROLES(USER_ID, ROLES_ID)
VALUES (1, 1),
       (1, 2),
       (2, 4),
       (3, 3),
       (3, 4),
       (4, 1);
