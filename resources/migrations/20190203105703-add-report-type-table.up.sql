CREATE TABLE report_type(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(20),
  image VARCHAR(50)
);
--;;
insert  into report_type(name,image) values
('Incidents','/img/incidents.png'),
('Slow traffic','/img/slow.png'),
('Roadworks','/img/roadworks.png'),
('Road closed','/img/roadclose.png'),
('Congestion','/img/congestion.png'),
('Line closed','/img/lineclosed.png');