drop schema if exists `securityDemo`;
create schema `securityDemo`;
use `securityDemo`;

create table users(
  userid varchar(32) NOT NULL primary key,
  email varchar(255) default NULL COMMENT 'email',
  password varchar(255) default NULL COMMENT 'password');




