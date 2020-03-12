-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities VALUES ('vet1','veterinarian');
-- User Rafa
INSERT INTO users(username,password,enabled) VALUES ('rafa','rafa',TRUE);
INSERT INTO authorities VALUES ('rafa','authenticated');
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
INSERT INTO comp_Admin_Requests(id, title, description, status, username) VALUES (1, 'Test title 01', 'Test description 01', 0, 'rafa');

-- Competition Admin
INSERT INTO competition_Admins VALUES (1, 'Pedro Manuel', 'Cobos Ramos', '22222222A', 'pedcobram@alum.us.es', '600222333', 'pedro');

-- Authenticated users
INSERT INTO authenticateds VALUES (1, 'Rafael', 'Liébana Fuentes', '11111111A', 'rafliefue@alum.us.es', '600111222', 'rafa');
INSERT INTO authenticateds VALUES (3, 'Ignacio José', 'Rodríguez Flores', '33333333A', 'ignrodflo@alum.us.es', '600333444', 'ignacio');
INSERT INTO authenticateds VALUES (4, 'Gonzalo', 'Fernandez Jiménez', '44444444A', 'gonferjim@alum.us.es', '600444555', 'gonzalo');
INSERT INTO authenticateds VALUES (5, 'Manuel', 'Sánchez Rodríguez', '55555555A', 'mansanrod@alum.us.es', '600555666', 'manuel');

-- President users
INSERT INTO presidents VALUES (1, 'President1', 'Surname1', '11111111A', 'email@gmail.com', '100111222', 'presidente1');
INSERT INTO presidents VALUES (2, 'President2', 'Surname2', '21111111A', 'email@gmail.com', '200111222', 'presidente2');
INSERT INTO presidents VALUES (3, 'President3', 'Surname3', '31111111A', 'email@gmail.com', '300111222', 'owner1');
INSERT INTO presidents VALUES (4, 'President4', 'Surname4', '41111111A', 'email@gmail.com', '400111222', 'owner1');
INSERT INTO presidents VALUES (5, 'President5', 'Surname5', '51111111A', 'email@gmail.com', '500111222', 'owner1');
INSERT INTO presidents VALUES (6, 'President6', 'Surname6', '61111111A', 'email@gmail.com', '600111222', 'owner1');
INSERT INTO presidents VALUES (7, 'President7', 'Surname7', '71111111A', 'email@gmail.com', '600151222', 'owner1');
INSERT INTO presidents VALUES (8, 'President8', 'Surname8', '81111111A', 'email@gmail.com', '700111222', 'owner1');
INSERT INTO presidents VALUES (9, 'President9', 'Surname9', '91111111A', 'email@gmail.com', '800111222', 'owner1');

-- Referee users
INSERT INTO referees VALUES (1, 'referee1', 'Surname1', '11111111A', 'email1@gmail.com', '600111222', 'referee1');
INSERT INTO referees VALUES (2, 'referee2', 'Surname2', '22222222A', 'email2@gmail.com', '711222333', 'referee2');

INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

-- Football Clubs
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (1, true, 'Sevilla Fútbol Club','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/179.png', '1889-01-25', 'Ramón Sánchez-Pizjuan', 'Seville', '44000', 'Julen Lopetegui', '150000000',1);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (2, true, 'Real Madrid Club de Fútbol','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/186.png', '1902-03-06', 'Santiago Bernabeu', 'Madrid', '81000', 'Zinedine Zidane', '600000000',2);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (3, true, 'Fútbol Club Barcelona','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/178.png', '1899-11-29', 'Camp Nou', 'Barcelona', '99300', 'Quique Setién', '650000000',3);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (4, true, 'Valencia Club de Fútbol','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/191.png', '1919-03-18', 'Mestalla', 'Valencia', '48600', 'Albert Celades', '200000000',4);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (5, true, 'Atlético de Madrid','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/175.png', '1903-04-26', 'Wanda Metropolitano', 'Madrid', '69000', 'Diego Pablo Simeone', '350000000',5);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (6, true, 'Real Betis Balompié','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/185.png', '1907-09-12', 'Benito Villamarín', 'Seville', '60000', 'Rubi', '350000000',6);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (7, true, 'Liverpool Football Club','https://pluspng.com/img-png/logo-liverpool-fc-png-liverpool-318.png', '1892-06-03', 'Anfield', 'Liverpool', '54000', 'Jürgen Klopp', '350000000',7);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (8, true, 'Manchester City Football Club','https://upload.wikimedia.org/wikipedia/sco/thumb/e/eb/Manchester_City_FC_badge.svg/1200px-Manchester_City_FC_badge.svg.png', '1894-04-16', 'City Of Manchester Stadium', 'Manchester', '55000', 'Pep Guardiola', '550000000',8);
INSERT INTO football_Clubs(id,status,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (9, true, 'Chelsea Football Club','https://upload.wikimedia.org/wikipedia/sco/thumb/c/cc/Chelsea_FC.svg/1200px-Chelsea_FC.svg.png', '1905-02-19', 'Stamford Bridge', 'London', '41000', 'Frank Lampard', '500000000',9);

-- Match Requests
INSERT INTO match_requests(id, title, match_date, stadium, status, football_Club1, football_Club2) VALUES(1, 'Partido amistoso 1', '2020-05-11 20:30', 'Ramón Sánchez-Pizjuan', 0, 'Sevilla Fútbol Club', 'Real Betis Balompié');
INSERT INTO match_requests(id, title, match_date, stadium, status, football_Club1, football_Club2) VALUES(2, 'Partido amistoso 2', '2021-05-11 20:30', 'Ramón Sánchez-Pizjuan', 0, 'Sevilla Fútbol Club', 'Fútbol Club Barcelona');
INSERT INTO match_requests(id, title, match_date, stadium, status, football_Club1, football_Club2) VALUES(3, 'Partido amistoso 3', '2020-05-11 20:30', 'Benito Villamarín', 0, 'Real Betis Balompié', 'Sevilla Fútbol Club' );
INSERT INTO match_requests(id, title, match_date, stadium, status, football_Club1, football_Club2) VALUES(4, 'Partido amistoso 4', '2021-05-11 20:30', 'Santiago Bernabeu', 0, 'Fútbol Club Barcelona', 'Sevilla Fútbol Club');

-- Matches
INSERT INTO matches(id, title, match_date, match_status, stadium, football_Club1, football_Club2) VALUES(1, 'Match title 1', '2020-05-11 20:30', 0, 'Sevilla Fútbol Club', 'Sevilla Fútbol Club', 'Real Betis Balompié');
INSERT INTO matches(id, title, match_date, match_status, stadium, football_Club1, football_Club2) VALUES(2, 'Match title 2', '2020-03-11 20:30', 2, 'Santiago Bernabeu', 'Fútbol Club Barcelona', 'Sevilla Fútbol Club');

-- Match Referee Requests
INSERT INTO match_referee_request(id, title, status, username, match_id) VALUES(1, 'Test title', 0, 'referee1', 1);
INSERT INTO match_referee_request(id, title, status, username, match_id) VALUES(2, 'Test title', 0, 'referee2', 1);

-- Football Players
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

INSERT INTO football_Players(id, first_name, last_name, position, value, birth_date) VALUES (64, 'Jorge', 'Salcedo', 0, '10000000', '1994-10-03');

