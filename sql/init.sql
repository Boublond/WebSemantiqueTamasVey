DROP DATABASE la_base;
CREATE DATABASE la_base;
USE la_base;

CREATE USER 'user'@'localhost'
  IDENTIFIED BY 'password';


GRANT ALL ON la_base.dico TO 'user'@'localhost';

CREATE TABLE dico
(
    id INT PRIMARY KEY NOT NULL,
    mot VARCHAR(100),
    doc VARCHAR(20),
    balise VARCHAR(20)
);