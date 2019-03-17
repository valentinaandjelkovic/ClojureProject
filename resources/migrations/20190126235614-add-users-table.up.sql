CREATE TABLE user
(id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
 first_name VARCHAR(30) NOT NULL,
 last_name VARCHAR(30) NOT NULL,
 username VARCHAR(30) NOT NULL UNIQUE,
 email VARCHAR(30) NOT NULL,
 password VARCHAR(300) NOT NULL,
 admin BOOLEAN
 );
--;;
insert into user (first_name,last_name,username,email,password,admin) values
('Test','Test','test123','test@hotmail.com','$2a$11$4dEtYgSeaiEggNHZbKy/lOtwKlbKj6TWvSIRoEjEGVX7vee/7nII6',1),
('Valentina','Andjelkovic','valentina','valentina@gmail.com','$2a$11$upH/69MOoQsPwnMZTa4Z0./GU4qFqynoNsNZBH4aqeYjTDFgHQjJq',0);

