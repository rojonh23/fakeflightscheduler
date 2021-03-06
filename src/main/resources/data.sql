--Initial data for Roles
INSERT INTO ROLE (NAME) VALUES ('ROLE_ADMIN');
INSERT INTO ROLE (NAME) VALUES ('ROLE_USER');

--Test users
--username: admin password: admin123
INSERT INTO USER (USERNAME, PASSWORD) VALUES ('admin', '$2a$10$gO8d7M8bTj33q2qYZ9pwq.CfZnF39oiUt3B4X56q/707NaoP/FXKO');
--username: user password: user123
INSERT INTO USER (USERNAME, PASSWORD) VALUES ('user', '$2a$10$2OsSJ.MaiEgw1Q4YntxxWu4JOgDGUO4bFaP743x0DLGklR56SAF82');

--Initial Role assignment for test users
INSERT INTO USER_X_ROLE VALUES (1,1);
INSERT INTO USER_X_ROLE VALUES (2,2);


--Initial flight data for testing
INSERT INTO FLIGHT (FLIGHT_NO, DEPARTURE_PORT, ARRIVAL_PORT, DEPARTURE_TIME, ARRIVAL_TIME, AIRLINE) VALUES(CONCAT('QF', SELECT FLIGHT_SEQ.NEXTVAL FROM DUAL), 'SYD', 'MEL', PARSEDATETIME('Sat, 3 Feb 2022 03:05:06 GMT',
    'EEE, d MMM yyyy HH:mm:ss z', 'en', 'GMT'), PARSEDATETIME('Sat, 3 Feb 2022 05:05:06 GMT',
    'EEE, d MMM yyyy HH:mm:ss z', 'en', 'GMT'), 'QF');

INSERT INTO FLIGHT (FLIGHT_NO, DEPARTURE_PORT, ARRIVAL_PORT, DEPARTURE_TIME, ARRIVAL_TIME, AIRLINE) VALUES(CONCAT('KY', SELECT FLIGHT_SEQ.NEXTVAL FROM DUAL), 'SYD', 'PHL', PARSEDATETIME('Sat, 3 Feb 2022 03:05:06 GMT',
    'EEE, d MMM yyyy HH:mm:ss z', 'en', 'GMT'), PARSEDATETIME('Sat, 3 Feb 2022 05:05:06 GMT',
    'EEE, d MMM yyyy HH:mm:ss z', 'en', 'GMT'), 'KY');