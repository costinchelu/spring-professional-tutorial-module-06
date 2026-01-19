drop table if exists authorities;
drop table if exists users;
drop table if exists employee;
drop table if exists department;
drop table if exists customer;

create table users(
    username varchar(50) not null primary key,
    password varchar(500) not null,
    enabled boolean not null
);

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);

create table employee (
                          id int primary key,
                          first_name varchar(100) not null,
                          last_name varchar(100) not null,
                          phone_number varchar(20),
                          address varchar(255),
                          cubicle_no varchar(20)
);

create table department (
                            id int primary key,
                            code varchar(10) not null,
                            name varchar(100) not null,
                            country varchar(50)
);

create table customer (
                          id int primary key,
                          code varchar(10) not null,
                          first_name varchar(100) not null,
                          last_name varchar(100) not null,
                          address varchar(255)
);
