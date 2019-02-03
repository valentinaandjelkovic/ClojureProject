CREATE TABLE report(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
description VARCHAR(50),
type INT,
user INT NOT NULL,
street INT NOT NULL,
date DATETIME,
FOREIGN KEY (type) REFERENCES report_type(id),
FOREIGN KEY (user) REFERENCES user(id),
FOREIGN KEY (street) REFERENCES street(id)
);

