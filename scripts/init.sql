DROP DATABASE IF EXISTS crypticcat_db;

CREATE DATABASE crypticcat_db;

USE crypticcat_db;

CREATE TABLE role(
	id bigint NOT NULL AUTO_INCREMENT,
	name varchar(50) NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE user(
	id bigint NOT NULL AUTO_INCREMENT,
	username varchar(255) NOT NULL,
	password char(80) NOT NULL,
	email varchar(255) NOT NULL,
	enabled boolean NOT NULL DEFAULT true,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	#birth_date date NOT NULL,
	created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	
	PRIMARY KEY (id)
);


CREATE TABLE `users_roles` (
  	user_id bigint NOT NULL,
  	role_id bigint NOT NULL,
  	
  	PRIMARY KEY (user_id,role_id),
  
  	KEY FK_ROLE_idx (role_id),
  	FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO role (name) VALUES ('ROLE_USER');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_MODERATOR');

SELECT * FROM role r;
SELECT * FROM user u;
SELECT * FROM users_roles ur;









