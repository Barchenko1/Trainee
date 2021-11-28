
DROP TABLE IF  EXISTS ROLE;
DROP TABLE IF EXISTS USER;

CREATE TABLE ROLE (
                      ROLEID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                      NAME VARCHAR(255) NOT NULL
);

CREATE TABLE USER (
                      USERID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                      LOGIN VARCHAR(255) NOT NULL,
                      PASSWORD VARCHAR(255) NOT NULL,
                      EMAIL VARCHAR(255) NOT NULL,
                      FIRSTNAME VARCHAR(255) NULL,
                      LASTNAME VARCHAR(255) NULL,
                      BIRTHDAY DATE NOT NULL,
                      ROLEID BIGINT NOT NULL,
                      foreign key (ROLEID) references ROLE (ROLEID) on delete CASCADE
);

insert into ROLE (NAME) values ('ADMIN');
insert into ROLE (NAME) values ('USER');

insert into USER (USERID, LOGIN, PASSWORD, EMAIL, FIRSTNAME, LASTNAME, BIRTHDAY, ROLEID)
values (1, 'admin', 'admin', 'admin@gmail.com', 'admin', 'admin', '1996-04-27', 1);




