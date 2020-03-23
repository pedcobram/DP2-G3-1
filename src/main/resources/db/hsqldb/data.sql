-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','admin1',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','owner1',TRUE);
INSERT INTO authorities VALUES ('owner1','president');
INSERT INTO users(username,password,enabled) VALUES ('owner2','owner2',TRUE);
INSERT INTO authorities VALUES ('owner2','president');
INSERT INTO users(username,password,enabled) VALUES ('owner3','owner3',TRUE);
INSERT INTO authorities VALUES ('owner3','president');
INSERT INTO users(username,password,enabled) VALUES ('owner4','owner4',TRUE);
INSERT INTO authorities VALUES ('owner4','president');
INSERT INTO users(username,password,enabled) VALUES ('owner5','owner5',TRUE);
INSERT INTO authorities VALUES ('owner5','president');
INSERT INTO users(username,password,enabled) VALUES ('owner6','owner6',TRUE);
INSERT INTO authorities VALUES ('owner6','president');
INSERT INTO users(username,password,enabled) VALUES ('owner7','owner7',TRUE);
INSERT INTO authorities VALUES ('owner7','president');
INSERT INTO users(username,password,enabled) VALUES ('owner8','owner8',TRUE);
INSERT INTO authorities VALUES ('owner8','president');
INSERT INTO users(username,password,enabled) VALUES ('owner9','owner9',TRUE);
INSERT INTO authorities VALUES ('owner9','president');
-- User Rafa
INSERT INTO users(username,password,enabled) VALUES ('rafa','rafa',TRUE);
INSERT INTO authorities VALUES ('rafa','president');
-- User Pedro
INSERT INTO users(username,password,enabled) VALUES ('pedro','pedro',TRUE);
INSERT INTO authorities VALUES ('pedro','competitionAdmin');
-- User Ignacio
INSERT INTO users(username,password,enabled) VALUES ('ignacio','ignacio',TRUE);
INSERT INTO authorities VALUES ('ignacio','authenticated');
-- User Gonzalo
INSERT INTO users(username,password,enabled) VALUES ('gonzalo','gonzalo',TRUE);
INSERT INTO authorities VALUES ('gonzalo','authenticated');
-- User Manuel
INSERT INTO users(username,password,enabled) VALUES ('manuel','manuel',TRUE);
INSERT INTO authorities VALUES ('manuel','authenticated');
-- User Presidente1
INSERT INTO users(username,password,enabled) VALUES ('presidente1','presidente1',TRUE);
INSERT INTO authorities VALUES ('presidente1','president');
-- User Presidente2
INSERT INTO users(username,password,enabled) VALUES ('presidente2','presidente2',TRUE);
INSERT INTO authorities VALUES ('presidente2','president');
-- User Referee1
INSERT INTO users(username,password,enabled) VALUES ('referee1','referee1',TRUE);
INSERT INTO authorities VALUES ('referee1','referee'); 
-- User Referee2
INSERT INTO users(username,password,enabled) VALUES ('referee2','referee2',TRUE);
INSERT INTO authorities VALUES ('referee2','referee'); 

-- Competition Admin Requests 
INSERT INTO comp_Admin_Requests(id, title, description, status, username) VALUES (1, 'Test title 01', 'Test description 01', 0, 'presidente1');

-- Competition Admin
INSERT INTO competition_Admins VALUES (1, 'Pedro Manuel', 'Cobos Ramos', '22222222A', 'pedcobram@alum.us.es', '600222333', 'pedro');

-- Authenticated users
INSERT INTO authenticateds VALUES (1, 'Ignacio José', 'Rodríguez Flores', '33333333A', 'ignrodflo@alum.us.es', '600333444', 'ignacio');
INSERT INTO authenticateds VALUES (2, 'Gonzalo', 'Fernandez Jiménez', '44444444A', 'gonferjim@alum.us.es', '600444555', 'gonzalo');
INSERT INTO authenticateds VALUES (3, 'Manuel', 'Sánchez Rodríguez', '55555555A', 'mansanrod@alum.us.es', '600555666', 'manuel');


-- President users
INSERT INTO presidents VALUES (1, 'President1', 'Surname1', '11111111A', 'email@gmail.com', '100111222', 'presidente1');
INSERT INTO presidents VALUES (2, 'President2', 'Surname2', '21111111A', 'email@gmail.com', '200111222', 'presidente2');
INSERT INTO presidents VALUES (3, 'President3', 'Surname3', '31111111A', 'email@gmail.com', '300111222', 'owner1');
INSERT INTO presidents VALUES (4, 'President4', 'Surname4', '41111111A', 'email@gmail.com', '400111222', 'owner2');
INSERT INTO presidents VALUES (5, 'President5', 'Surname5', '51111111A', 'email@gmail.com', '500111222', 'owner3');
INSERT INTO presidents VALUES (6, 'President6', 'Surname6', '61111111A', 'email@gmail.com', '600111222', 'owner4');
INSERT INTO presidents VALUES (7, 'President7', 'Surname7', '71111111A', 'email@gmail.com', '600151222', 'owner5');
INSERT INTO presidents VALUES (8, 'President8', 'Surname8', '81111111A', 'email@gmail.com', '700111222', 'owner6');
INSERT INTO presidents VALUES (9, 'President9', 'Surname9', '91111111A', 'email@gmail.com', '800111222', 'owner7');
INSERT INTO presidents VALUES (10, 'Rafael', 'Liébana Fuentes', '11111111A', 'rafliefue@alum.us.es', '600111222', 'rafa');
INSERT INTO presidents VALUES (11, 'Wakka', 'Chappu', '11111112A', 'wakka@gmail.com', '600121222', 'owner8');
INSERT INTO presidents VALUES (12, 'Bickson', 'Graav', '11111122A', 'bickson@gmail.com', '602121222', 'owner9');

-- Referee users
INSERT INTO referees VALUES (1, 'referee1', 'Surname1', '11111111A', 'email1@gmail.com', '600111222', 'referee1');
INSERT INTO referees VALUES (2, 'referee2', 'Surname2', '22222222A', 'email2@gmail.com', '711222333', 'referee2');

-- Football Clubs
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (1, true, 'Sevilla Fútbol Club','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/179.png', '1889-01-25', 'Ramón Sánchez-Pizjuan', 'Seville', '44000', '150000000',1);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (2, true, 'Real Madrid Club de Fútbol','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/186.png', '1902-03-06', 'Santiago Bernabeu', 'Madrid', '81000', '600000000',2);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (3, true, 'Fútbol Club Barcelona','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/178.png', '1899-11-29', 'Camp Nou', 'Barcelona', '99300', '650000000',3);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (4, true, 'Valencia Club de Fútbol','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/191.png', '1919-03-18', 'Mestalla', 'Valencia', '48600', '200000000',4);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (5, true, 'Atlético de Madrid','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/175.png', '1903-04-26', 'Wanda Metropolitano', 'Madrid', '69000', '350000000',5);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (6, true, 'Real Betis Balompié','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/185.png', '1907-09-12', 'Benito Villamarín', 'Seville', '60000', '100000000',6);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (7, true, 'Liverpool Football Club','https://pluspng.com/img-png/logo-liverpool-fc-png-liverpool-318.png', '1892-06-03', 'Anfield', 'Liverpool', '54000', '350000000',7);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (8, true, 'Manchester City Football Club','https://upload.wikimedia.org/wikipedia/sco/thumb/e/eb/Manchester_City_FC_badge.svg/1200px-Manchester_City_FC_badge.svg.png', '1894-04-16', 'City Of Manchester Stadium', 'Manchester', '55000', '10000000',8);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (9, false, 'Chelsea Football Club','https://upload.wikimedia.org/wikipedia/sco/thumb/c/cc/Chelsea_FC.svg/1200px-Chelsea_FC.svg.png', '1905-02-19', 'Stamford Bridge', 'London', '41000', '500000000',9);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (10, false, 'Besaid Aurochs','https://www.gamerguides.com/assets/trophies/44/1831-teamwork.png', '2000-02-19', 'Besaid Island Stadium', 'Besaid Island', '40', '2000000', 11);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (11, true, 'Luca Goers','https://cdn.staticneo.com/w/finalfantasy/f/f5/GoersSymbol.png', '2000-02-18', 'Luca Stadium', 'Luca', '40000', '200000000', 12); 
 
-- Match Requests
INSERT INTO match_requests(id, title, match_date, stadium, status, football_Club1, football_Club2, creator) VALUES(1, 'Partido amistoso 1', '2020-05-11 20:30', 'Ramón Sánchez-Pizjuan', 0, 'Sevilla Fútbol Club', 'Real Betis Balompié', 'presidente1');
INSERT INTO match_requests(id, title, match_date, stadium, status, football_Club1, football_Club2, creator) VALUES(2, 'Partido amistoso 2', '2021-05-11 20:30', 'Ramón Sánchez-Pizjuan', 0, 'Sevilla Fútbol Club', 'Fútbol Club Barcelona', 'presidente1');
INSERT INTO match_requests(id, title, match_date, stadium, status, football_Club1, football_Club2, creator) VALUES(3, 'Partido amistoso 3', '2020-05-11 20:30', 'Benito Villamarín', 0, 'Real Betis Balompié', 'Sevilla Fútbol Club', 'presidente1');
INSERT INTO match_requests(id, title, match_date, stadium, status, football_Club1, football_Club2, creator) VALUES(4, 'Partido amistoso 4', '2021-05-11 20:30', 'Camp Nou', 0, 'Fútbol Club Barcelona', 'Sevilla Fútbol Club', 'presidente1');

-- Matches
INSERT INTO matches(id, title, match_date, match_status, stadium, football_Club1, football_Club2, creator) VALUES(1, 'Match title 1', '2020-05-11 20:30', 0, 'Ramón Sánchez-Pizjuan', 'Sevilla Fútbol Club', 'Real Betis Balompié', 'presidente1');
INSERT INTO matches(id, title, match_date, match_status, stadium, football_Club1, football_Club2, creator) VALUES(2, 'Match title 2', '2020-03-11 20:30', 1, 'Camp Nou', 'Fútbol Club Barcelona', 'Sevilla Fútbol Club', 'presidente1');

-- Match Referee Requests
INSERT INTO match_referee_request(id, title, status, username, match_id) VALUES(1, 'Test title', 0, 'referee1', 1);
INSERT INTO match_referee_request(id, title, status, username, match_id) VALUES(2, 'Test title', 0, 'referee2', 1);

-- Coachs
INSERT INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (1, 'Julen', 'Lopetegui', 1, '3000000', '6000000', '1968-03-29' );
INSERT INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (2, 'Zinedine', 'Zidane', 2, '6000000', '10000000', '1970-03-27' );
INSERT INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (3, 'Quique', 'Setién', 3, '2500000', '5000000', '1960-07-21' );
INSERT INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (4, 'Albert', 'Celades', 4, '1500000', '2000000', '1975-06-29' );
INSERT INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (5, 'Diego Pablo', 'Simeone', 5, '25000000', '20000000', '1975-12-21' );
INSERT INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (6, 'Rubi', 'Ferrer', 6, '1000000', '2000000', '1974-03-29' );
INSERT INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (7, 'Jurgën', 'Klopp', 7, '8000000', '10000000', '1965-01-19' );
INSERT INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (8, 'Pep', 'Guardiola', 8, '10000000', '15000000', '1974-07-07' );
INSERT INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (9, 'Frank', 'Lampard', 9, '5000000', '6000000', '1980-05-25' );

INSERT INTO coachs(id, first_name, last_name, salary, clause, birth_date) VALUES (10, 'Marcelino', 'Garcia Toral','0' ,'0' ,'1975-01-19');
INSERT INTO coachs(id, first_name, last_name, salary, clause, birth_date) VALUES (11, 'Unai', 'Emery','0' ,'0' ,'1974-07-07');
INSERT INTO coachs(id, first_name, last_name, salary, clause, birth_date) VALUES (12, 'Mauricio', 'Pochetino','0' ,'0' ,'1980-05-25');

INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (1, 'Tomas', 'Vaclik', 0, 1, '18000000', '1989-03-29');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (2, 'Diego', 'Carlos', 1, 1, '25000000', '1993-03-15');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (3, 'Fernando', 'Reges', 2, 1, '7000000', '1987-07-25');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (4, 'Lucas', 'Ocampos', 2, 1, '25000000', '1994-07-11');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (5, 'Luuk', 'De Jong', 3, 1, '12000000', '1990-08-27');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (46, 'Jesús', 'Navas', 1, 1, '4000000', '1985-11-21');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (47, 'Jules', 'Koundé', 1, 1, '25000000', '1998-11-12');

INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (6, 'Thibaut', 'Courtois', 0, 2, '55000000', '1992-05-11');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (7, 'Sergio', 'Ramos', 1, 2, '18000000', '1986-03-30');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (8, 'Toni', 'Kross', 2, 2, '60000000', '1990-01-04');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (9, 'Gareth', 'Bale', 3, 2, '40000000', '1989-07-16');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (10, 'Karim', 'Benzemá', 3, 2, '40000000', '1987-12-19');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (48, 'Luka', 'Modric', 2, 2, '15000000', '1985-09-09');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (49, 'Daniel', 'Carvajal', 1, 2, '50000000', '1992-01-11');

INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (11, 'Marc-André', 'ter Stegen', 0, 3, '90000000', '1992-04-30');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (12, 'Gerard', 'Piqué', 1, 3, '25000000', '1987-02-02');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (13, 'Frankie', 'De Jong', 2, 3, '90000000', '1997-05-12');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (14, 'Antoine', 'Griezzman', 2, 3, '120000000', '1991-03-21');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (15, 'Lionel', 'Messi', 2, 3, '140000000', '1987-06-24');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (50, 'Luis', 'Suárez', 3, 3, '40000000', '1987-01-24');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (51, 'Jordi', 'Alba', 1, 3, '50000000', '1989-03-21');

INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (16, 'Jasper', 'Cillesen', 0, 4, '18000000', '1989-04-22');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (17, 'Gabriel', 'Paulista', 1, 4, '22000000', '1990-11-26');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (18, 'Dani', 'Parejo', 2, 4, '35000000', '1989-04-16');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (19, 'Ferran', 'Torres', 2, 4, '40000000', '2000-02-29');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (20, 'Kevin', 'Gameiro', 3, 4, '8000000', '1987-05-09');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (52, 'Rodrigo', 'Moreno', 3, 4, '50000000', '1991-03-06');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (53, 'Ezequiel', 'Garay', 1, 4, '8000000', '1986-10-10');

INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (21, 'Jan', 'Oblak', 0, 5, '100000000', '1993-01-07');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (22, 'José', 'Gimenez', 1, 5, '70000000', '1995-01-20');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (23, 'Saul', 'Ñíguez', 2, 5, '90000000', '1994-11-21');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (24, 'Ángel', 'Correa', 3, 5, '35000000', '1995-03-09');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (25, 'Álvaro', 'Morata', 3, 5, '50000000', '1993-10-23');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (54, 'Joäo', 'Félix', 2, 5, '100000000', '1999-11-10');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (55, 'Stefan', 'Savic', 1, 5, '32000000', '1991-01-08');

INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (26, 'Joel', 'Robles', 0, 6, '7500000', '1990-06-17');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (27, 'Marc', 'Bartra', 1, 6, '22000000', '1991-01-15');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (28, 'Joaquin', 'Sánchez', 2, 6, '2000000', '1981-07-21');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (29, 'Nabil', 'Fekir', 2, 6, '40000000', '1993-07-18');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (30, 'Borja', 'Iglesias', 3, 6, '20000000', '1993-01-17');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (56, 'Aïssa', 'Mandi', 1, 6, '20000000', '1991-10-22');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (57, 'Andrés', 'Guardado', 2, 6, '4000000', '1986-09-28');

INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (31, 'Alisson', 'Becker', 0, 7, '90000000', '1992-10-02');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (32, 'Virgil', 'van Dijk', 1, 7, '100000000', '1991-07-08');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (33, 'Sadio', 'Mané', 2, 7, '150000000', '1992-04-10');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (34, 'Mohamed', 'Salah', 2, 7, '150000000', '1992-06-15');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (35, 'Roberto', 'Firmino', 3, 7, '90000000', '1991-10-02');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (58, 'Andrew', 'Robertson', 1, 7, '80000000', '1994-03-11');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (59, 'Fabinho', 'Tabares', 2, 7, '70000000', '1993-10-23');

INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (36, 'Ederson', 'Moraes', 0, 8, '70000000', '1993-08-17');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (37, 'Nicolas', 'Otamendi', 1, 8, '18000000', '1988-02-12');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (38, 'Bernardo', 'Silva', 2, 8, '100000000', '1994-08-10');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (39, 'Kevin', 'De Bruyne', 2, 8, '150000000', '1991-06-28');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (40, 'Sergio "Kun"', 'Agüero', 3, 8, '65000000', '1988-06-02');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (60, 'Aymeric', 'Laporte', 1, 8, '75000000', '1994-05-27');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (61, 'Raheem', 'Sterling', 2, 8, '65000000', '1994-12-08');

INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (41, 'Kepa', 'Arrizabalaga', 0, 9, '60000000', '1994-10-03');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (42, 'César', 'Azpilicueta', 1, 9, '30000000', '1989-08-28');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (43, 'NGolo', 'Kanté', 2, 9, '100000000', '1991-03-29');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (44, 'Christian', 'Pulisic', 2, 9, '60000000', '1998-09-18');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (45, 'Olivier', 'Giroud', 3, 9, '9000000', '1986-09-30');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (62, 'Andreas', 'Christensen', 1, 9, '30000000', '1996-04-10');
INSERT INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (63, 'Tammy', 'Abraham', 2, 9, '50000000', '1997-10-02');

INSERT INTO football_Players(id, first_name, last_name, position, value, birth_date) VALUES (64, 'Jorge', 'Salcedo', 2, '10000000', '1994-10-03');
INSERT INTO football_Players(id, first_name, last_name, position, value, birth_date) VALUES (65, 'Albert', 'Martin', 3, '60000000', '1992-11-06');
INSERT INTO football_Players(id, first_name, last_name, position, value, birth_date) VALUES (66, 'Sergio', 'Molina', 0, '99999999', '1984-11-06');
INSERT INTO football_Players(id, first_name, last_name, position, value, birth_date) VALUES (67, 'Pablo', 'Escobar', 3, '70000000', '1949-11-06');
INSERT INTO football_Players(id, first_name, last_name, position, value, birth_date) VALUES (68, 'Rafael', 'Cantero', 1, '11000000', '1990-11-06');

-- Football Player Contracts
INSERT into contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (1, 1, '2018-06-30', '2021-06-30', '9000000', 1, '1500000');
INSERT into contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (2, 1, '2019-06-30', '2023-06-30', '12500000', 2, '2000000');
INSERT into contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (3, 1, '2019-06-30', '2022-06-30', '4500000', 3, '2000000');
INSERT into contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (4, 1, '2019-06-30', '2023-06-30', '12500000', 4, '2500000');
INSERT into contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (5, 1, '2019-06-30', '2023-06-30', '6000000', 5, '1500000');
INSERT into contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (6, 1, '2017-06-30', '2022-06-30', '2000000', 46, '2500000');
INSERT into contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (7, 1, '2019-06-30', '2024-06-30', '12500000', 47, '1500000');

-- Football Player Statistics
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(1, 0, 0, 0, 0, null, 2019, 2020, 1);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(2, 0, 0, 0, 0, null, 2019, 2020, 2);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(3, 0, 0, 0, 0, null, 2019, 2020, 3);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(4, 0, 0, 0, 0, null, 2019, 2020, 4);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(5, 0, 0, 0, 0, null, 2019, 2020, 5);

INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(6, 0, 0, 0, 0, null, 2019, 2020, 6);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(7, 0, 0, 0, 0, null, 2019, 2020, 7);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(8, 0, 0, 0, 0, null, 2019, 2020, 8);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(9, 0, 0, 0, 0, null, 2019, 2020, 9);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(10, 0, 0, 0, 0, null, 2019, 2020, 10);

INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(11, 0, 0, 0, 0, null, 2019, 2020, 11);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(12, 0, 0, 0, 0, null, 2019, 2020, 12);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(13, 0, 0, 0, 0, null, 2019, 2020, 13);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(14, 0, 0, 0, 0, null, 2019, 2020, 14);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(15, 0, 0, 0, 0, null, 2019, 2020, 15);

INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(16, 0, 0, 0, 0, null, 2019, 2020, 16);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(17, 0, 0, 0, 0, null, 2019, 2020, 17);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(18, 0, 0, 0, 0, null, 2019, 2020, 18);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(19, 0, 0, 0, 0, null, 2019, 2020, 19);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(20, 0, 0, 0, 0, null, 2019, 2020, 20);

INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(21, 0, 0, 0, 0, null, 2019, 2020, 21);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(22, 0, 0, 0, 0, null, 2019, 2020, 22);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(23, 0, 0, 0, 0, null, 2019, 2020, 23);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(24, 0, 0, 0, 0, null, 2019, 2020, 24);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(25, 0, 0, 0, 0, null, 2019, 2020, 25);

INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(26, 0, 0, 0, 0, null, 2019, 2020, 26);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(27, 0, 0, 0, 0, null, 2019, 2020, 27);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(28, 0, 0, 0, 0, null, 2019, 2020, 28);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(29, 0, 0, 0, 0, null, 2019, 2020, 29);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(30, 0, 0, 0, 0, null, 2019, 2020, 30);

INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(31, 0, 0, 0, 0, null, 2019, 2020, 31);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(32, 0, 0, 0, 0, null, 2019, 2020, 32);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(33, 0, 0, 0, 0, null, 2019, 2020, 33);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(34, 0, 0, 0, 0, null, 2019, 2020, 34);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(35, 0, 0, 0, 0, null, 2019, 2020, 35);

INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(36, 0, 0, 0, 0, null, 2019, 2020, 36);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(37, 0, 0, 0, 0, null, 2019, 2020, 37);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(38, 0, 0, 0, 0, null, 2019, 2020, 38);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(39, 0, 0, 0, 0, null, 2019, 2020, 39);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(40, 0, 0, 0, 0, null, 2019, 2020, 40);

INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(41, 0, 0, 0, 0, null, 2019, 2020, 41);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(42, 0, 0, 0, 0, null, 2019, 2020, 42);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(43, 0, 0, 0, 0, null, 2019, 2020, 43);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(44, 0, 0, 0, 0, null, 2019, 2020, 44);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(45, 0, 0, 0, 0, null, 2019, 2020, 45);

INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(46, 0, 0, 0, 0, null, 2019, 2020, 46);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(47, 0, 0, 0, 0, null, 2019, 2020, 47);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(48, 0, 0, 0, 0, null, 2019, 2020, 48);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(49, 0, 0, 0, 0, null, 2019, 2020, 49);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(50, 0, 0, 0, 0, null, 2019, 2020, 50);

INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(51, 0, 0, 0, 0, null, 2019, 2020, 51);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(52, 0, 0, 0, 0, null, 2019, 2020, 52);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(53, 0, 0, 0, 0, null, 2019, 2020, 53);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(54, 0, 0, 0, 0, null, 2019, 2020, 54);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(55, 0, 0, 0, 0, null, 2019, 2020, 55);

INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(56, 0, 0, 0, 0, null, 2019, 2020, 56);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(57, 0, 0, 0, 0, null, 2019, 2020, 57);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(58, 0, 0, 0, 0, null, 2019, 2020, 58);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(59, 0, 0, 0, 0, null, 2019, 2020, 59);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(60, 0, 0, 0, 0, null, 2019, 2020, 60);

INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(61, 0, 0, 0, 0, null, 2019, 2020, 61);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(62, 0, 0, 0, 0, null, 2019, 2020, 62);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(63, 0, 0, 0, 0, null, 2019, 2020, 63);
INSERT INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(64, 0, 0, 0, 0, null, 2019, 2020, 64);
