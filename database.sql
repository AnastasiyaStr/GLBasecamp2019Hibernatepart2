drop database if exists office;
CREATE DATABASE IF NOT EXISTS office;
USE office;
DROP TABLE IF EXISTS worker;
CREATE TABLE IF NOT EXISTS worker (
 worker_id int(100)  AUTO_INCREMENT,
  full_name varchar(90) DEFAULT NULL,
  age int(4) DEFAULT NULL,
  availability  varchar(8),  
   department_id INT REFERENCES department (department_id),
  PRIMARY KEY (worker_id)
);


CREATE TABLE IF NOT EXISTS department (
 department_id int(100) NOT NULL AUTO_INCREMENT,
  name varchar(90) DEFAULT NULL,
 status boolean, 
  PRIMARY KEY (department_id)
);
