CREATE TABLE report(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
description VARCHAR(50),
type INT,
user INT NOT NULL,
street INT NOT NULL,
date DATETIME,
FOREIGN KEY (type) REFERENCES report_type(id),
FOREIGN KEY (user) REFERENCES user(id),
FOREIGN KEY (street) REFERENCES street(id) ON DELETE CASCADE
);
--;;
insert into report(description,type,user,street,date) values
('Usporen saobracaj od hotela Zire.',2,1,2,'2019-02-01 16:26:13'),
('',5,2,3,'2019-02-23 15:07:20'),
('Zatvoren deo ulice od Megatrenda.',4,1,3,'2019-02-23 15:07:42'),
('Zatvorena ulica u pravcu Novog Beograda',6,2,3,'2019-03-09 13:56:39'),
('test',5,1,3,'2019-03-17 12:37:45');
