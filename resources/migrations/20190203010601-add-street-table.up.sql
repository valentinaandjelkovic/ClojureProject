CREATE TABLE street
(id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(30) NOT NULL,
 community VARCHAR(50),
 city INT,
 FOREIGN KEY (city) REFERENCES city(id)
 );
 --;;
 INSERT INTO street (name, community, city) VALUES ('Bezanijska', 'Zemun', 1);
 --;;
 INSERT INTO street (name, community, city) VALUES ('Dimitrija Tucovica', 'Zvezdara', 1);
 --;;
 INSERT INTO street (name, community, city) VALUES ('Goce Delceva', 'Novi Beograd', 1);
 --;;
 INSERT INTO street (name, community, city) VALUES ('Bulevar Arsenija Carnojevica', 'Novi Beograd', 1);
 --;;
 INSERT INTO street (name, community, city) VALUES ('Bulevar oslobodjenja', 'Vozdovac', 1);

