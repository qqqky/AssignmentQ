PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE SPECIALISTS( ID INTEGER PRIMARY KEY, name TEXT, last_name TEXT, username TEXT, password TEXT);
INSERT INTO SPECIALISTS VALUES(1,'Amy','Jones','Amy','12345');
INSERT INTO SPECIALISTS VALUES(2,'Betty','Smith','Betty','12345');
INSERT INTO SPECIALISTS VALUES(3,'Edward','Miller','Edward','12345');
INSERT INTO SPECIALISTS VALUES(4,'Emma','Moore','Emma','12345');
INSERT INTO SPECIALISTS VALUES(5,'Gary','Clark','Gary','12345');
CREATE TABLE CUSTOMERS( specialist_id INTEGER, cust_name TEXT, cust_last_name TEXT, visit_date DATE, visit_time TIME);
CREATE TABLE TIMETABLE_01( visit_time TIME PRIMARY KEY,name TEXT, last_name TEXT, res_code TEXT, status TEXT, registration_date DATE);
INSERT INTO TIMETABLE_01 VALUES(25200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(26100000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(27000000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(27900000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(28800000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(29700000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(30600000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(31500000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(32400000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(33300000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(34200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(35100000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(36000000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(36900000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(37800000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(38700000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(39600000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(40500000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(41400000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(42300000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(43200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(44100000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(45000000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(45900000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(46800000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(47700000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(48600000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(49500000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(50400000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(51300000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(52200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_01 VALUES(53100000,'','','','FREE',1617656400000);
CREATE TABLE TIMETABLE_02( visit_time TIME PRIMARY KEY,name TEXT, last_name TEXT, res_code TEXT, status TEXT, registration_date DATE);
INSERT INTO TIMETABLE_02 VALUES(25200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(26100000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(27000000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(27900000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(28800000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(29700000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(30600000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(31500000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(32400000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(33300000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(34200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(35100000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(36000000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(36900000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(37800000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(38700000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(39600000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(40500000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(41400000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(42300000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(43200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(44100000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(45000000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(45900000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(46800000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(47700000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(48600000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(49500000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(50400000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(51300000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(52200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_02 VALUES(53100000,'','','','FREE',1617656400000);
CREATE TABLE TIMETABLE_03( visit_time TIME PRIMARY KEY,name TEXT, last_name TEXT, res_code TEXT, status TEXT, registration_date DATE);
INSERT INTO TIMETABLE_03 VALUES(25200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(26100000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(27000000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(27900000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(28800000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(29700000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(30600000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(31500000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(32400000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(33300000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(34200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(35100000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(36000000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(36900000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(37800000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(38700000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(39600000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(40500000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(41400000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(42300000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(43200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(44100000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(45000000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(45900000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(46800000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(47700000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(48600000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(49500000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(50400000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(51300000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(52200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_03 VALUES(53100000,'','','','FREE',1617656400000);
CREATE TABLE TIMETABLE_04( visit_time TIME PRIMARY KEY,name TEXT, last_name TEXT, res_code TEXT, status TEXT, registration_date DATE);
INSERT INTO TIMETABLE_04 VALUES(25200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(26100000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(27000000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(27900000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(28800000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(29700000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(30600000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(31500000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(32400000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(33300000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(34200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(35100000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(36000000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(36900000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(37800000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(38700000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(39600000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(40500000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(41400000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(42300000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(43200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(44100000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(45000000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(45900000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(46800000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(47700000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(48600000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(49500000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(50400000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(51300000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(52200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_04 VALUES(53100000,'','','','FREE',1617656400000);
CREATE TABLE TIMETABLE_05( visit_time TIME PRIMARY KEY,name TEXT, last_name TEXT, res_code TEXT, status TEXT, registration_date DATE);
INSERT INTO TIMETABLE_05 VALUES(25200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(26100000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(27000000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(27900000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(28800000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(29700000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(30600000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(31500000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(32400000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(33300000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(34200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(35100000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(36000000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(36900000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(37800000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(38700000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(39600000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(40500000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(41400000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(42300000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(43200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(44100000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(45000000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(45900000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(46800000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(47700000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(48600000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(49500000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(50400000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(51300000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(52200000,'','','','FREE',1617656400000);
INSERT INTO TIMETABLE_05 VALUES(53100000,'','','','FREE',1617656400000);
COMMIT;