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
