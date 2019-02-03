CREATE TABLE report_type(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(20)
);
--;;
INSERT INTO report_type(name) VALUE ('Incidents');
--;;
INSERT INTO report_type(name) VALUE ('Slow traffic');
--;;
INSERT INTO report_type(name) VALUE ('Roadworks');
--;;
INSERT INTO report_type(name) VALUE ('Road closed');
--;;