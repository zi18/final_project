use bank;
drop database bank;
create database bank;
use bank;
create table users(
	username VARCHAR(50) NOT NULL primary key,
	password VARCHAR(100) NOT NULL,
	enabled TINYINT NOT NULL DEFAULT 1,
	name varchar (50),
	date_birth datetime,
	primary_address varchar (50),
	mailing_address varchar (50),
	hashed_key varchar (50)
);
create table authorities
(
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

create table accounts(
    id INT AUTO_INCREMENT,
	balance decimal(10,3),
    secret_key varchar(50),
	primary_owner  varchar(50),
	secondary_owner  varchar(50),
	minimum_balance decimal(10,3),
	penalty_fee decimal(10,3),
	monthly_maintenance_fee decimal(10,3),
	creation_date datetime,
	status varchar (50),
	interest_rate decimal(10,3),
	credit_limit decimal(10,3),
    account_type varchar(50),
	interest_added_date datetime,
  PRIMARY KEY(id),
  foreign key (primary_owner) references users(username)
);

insert into users values ("zineb", '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', true, "Zineb", "1988-12-08 14:15:31", "bac de roda", "£222", "asdf");
INSERT INTO authorities (username, authority) values ('zineb', 'ADMIN');
insert into users values ("maria", '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', true, "Maria", "1988-12-08 14:15:31", "bac de roda", "£222", "asdf");
INSERT INTO authorities (username, authority) values ('maria', 'ACCOUNT_HOLDER');
insert into users values ("emma", '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', true, "Emma", "1988-12-08 14:15:31", "bac de roda", "£222", "asdf");
INSERT INTO authorities (username, authority) values ('luisa', 'THIRD_PARTY');
