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

INSERT INTO USER(ID, USER_NAME, PASSWORD, ROLES)
VALUES (1, 'user', 'pass', 'USER');


