CREATE DATABASE la_base;
USE la_base;

CREATE USER 'user'@'localhost'
  IDENTIFIED BY 'password';

CREATE TABLE dico
(
    id INT PRIMARY KEY NOT NULL,
    mot VARCHAR(100),
    num integer
);