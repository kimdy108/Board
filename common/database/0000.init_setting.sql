DROP DATABASE IF EXISTS `board`;
CREATE DATABASE `board`;

grant all privileges on board.* to boarduser@'localhost' identified by 'boardPassword';
grant all privileges on board.* to boarduser@'127.0.0.1' identified by 'boardPassword';
flush privileges;
