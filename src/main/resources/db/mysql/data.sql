
-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT IGNORE INTO users(username,password,enabled) VALUES ('admin1','admin1',TRUE) ON DUPLICATE KEY UPDATE username=username;
INSERT IGNORE INTO authorities(username, authority) VALUES ('admin1','admin') ON DUPLICATE KEY UPDATE username=username;
-- One owner user, named owner1 with passwor 0wn3r
INSERT IGNORE INTO users(username,password,enabled) VALUES ('owner1','owner1',TRUE) ON DUPLICATE KEY UPDATE username=username;
INSERT IGNORE INTO authorities(username, authority) VALUES ('owner1','president') ON DUPLICATE KEY UPDATE username=username;

INSERT IGNORE INTO users(username,password,enabled) VALUES ('owner2','owner2',TRUE) ON DUPLICATE KEY UPDATE username=username;
INSERT IGNORE INTO authorities(username, authority) VALUES ('owner2','president') ON DUPLICATE KEY UPDATE username=username;

INSERT IGNORE INTO users(username,password,enabled) VALUES ('owner3','owner3',TRUE) ON DUPLICATE KEY UPDATE username=username;
INSERT IGNORE INTO authorities(username, authority) VALUES ('owner3','president') ON DUPLICATE KEY UPDATE username=username;

INSERT IGNORE INTO users(username,password,enabled) VALUES ('owner4','owner4',TRUE) ON DUPLICATE KEY UPDATE username=username;
INSERT IGNORE INTO authorities(username, authority) VALUES ('owner4','president') ON DUPLICATE KEY UPDATE username=username;

INSERT IGNORE INTO users(username,password,enabled) VALUES ('owner5','owner5',TRUE) ON DUPLICATE KEY UPDATE username=username;
INSERT IGNORE INTO authorities(username, authority) VALUES ('owner5','president') ON DUPLICATE KEY UPDATE username=username;

INSERT IGNORE INTO users(username,password,enabled) VALUES ('owner6','owner6',TRUE) ON DUPLICATE KEY UPDATE username=username;
INSERT IGNORE INTO authorities(username, authority) VALUES ('owner6','president') ON DUPLICATE KEY UPDATE username=username;

INSERT IGNORE INTO users(username,password,enabled) VALUES ('owner7','owner7',TRUE);
INSERT IGNORE INTO authorities(username, authority) VALUES ('owner7','president');

INSERT IGNORE INTO users(username,password,enabled) VALUES ('owner8','owner8',TRUE);
INSERT IGNORE INTO authorities(username, authority) VALUES ('owner8','president');

INSERT IGNORE INTO users(username,password,enabled) VALUES ('owner9','owner9',TRUE);
INSERT IGNORE INTO authorities(username, authority) VALUES ('owner9','president');
-- User Rafa
INSERT IGNORE INTO users(username,password,enabled) VALUES ('rafa','rafa',TRUE);
INSERT IGNORE INTO authorities(username, authority) VALUES ('rafa','president');
-- User Pedro
INSERT IGNORE INTO users(username,password,enabled) VALUES ('pedro','pedro',TRUE);
INSERT IGNORE INTO authorities(username, authority) VALUES ('pedro','competitionAdmin');
-- User Ignacio
INSERT IGNORE INTO users(username,password,enabled) VALUES ('ignacio','ignacio',TRUE);
INSERT IGNORE INTO authorities(username, authority) VALUES ('ignacio','authenticated');
-- User Gonzalo
INSERT IGNORE INTO users(username,password,enabled) VALUES ('gonzalo','gonzalo',TRUE);
INSERT IGNORE INTO authorities(username, authority) VALUES ('gonzalo','authenticated');
-- User Manuel
INSERT IGNORE INTO users(username,password,enabled) VALUES ('manuel','manuel',TRUE);
INSERT IGNORE INTO authorities(username, authority) VALUES ('manuel','authenticated');
-- User Presidente1
INSERT IGNORE INTO users(username,password,enabled) VALUES ('presidente1','presidente1',TRUE);
INSERT IGNORE INTO authorities(username, authority) VALUES ('presidente1','president');
-- User Presidente2
INSERT IGNORE INTO users(username,password,enabled) VALUES ('presidente2','presidente2',TRUE);
INSERT IGNORE INTO authorities(username, authority) VALUES ('presidente2','president');
-- User Referee1
INSERT IGNORE INTO users(username,password,enabled) VALUES ('referee1','referee1',TRUE);
INSERT IGNORE INTO authorities(username, authority) VALUES ('referee1','referee'); 
-- User Referee2
INSERT IGNORE INTO users(username,password,enabled) VALUES ('referee2','referee2',TRUE);
INSERT IGNORE INTO authorities(username, authority) VALUES ('referee2','referee'); 

-- Competition Admin Requests 
INSERT IGNORE INTO comp_admin_requests(id, title, description, status, username) VALUES (1, 'Test title 01', 'Test description 01', 0, 'gonzalo');
-- Referee Requests 
INSERT IGNORE INTO referee_Requests(id, title, description, status, username) VALUES (1, 'Test title 01', 'Test description 01', 0, 'gonzalo');

-- President Requests 
INSERT IGNORE INTO president_Requests(id, title, description, status, username) VALUES (1, 'Test title 01', 'Test description 01', 0, 'gonzalo');

-- Competition Admin
INSERT IGNORE INTO competition_admins(id, first_name, last_name, telephone, email, dni, username) VALUES (1, 'Pedro Manuel', 'Cobos Ramos', '22222222A', 'pedcobram@alum.us.es', '600222333', 'pedro');

-- Authenticated users
INSERT IGNORE INTO authenticateds(id, first_name, last_name, dni, email, telephone, username) VALUES (1, 'Ignacio José', 'Rodríguez Flores', '33333333A', 'ignrodflo@alum.us.es', '600333444', 'ignacio');
INSERT IGNORE INTO authenticateds(id, first_name, last_name, dni, email, telephone, username) VALUES (2, 'Gonzalo', 'Fernandez Jiménez', '44444444A', 'gonferjim@alum.us.es', '600444555', 'gonzalo');
INSERT IGNORE INTO authenticateds(id, first_name, last_name, dni, email, telephone, username) VALUES (3, 'Manuel', 'Sánchez Rodríguez', '55555555A', 'mansanrod@alum.us.es', '600555666', 'manuel');

-- President users
INSERT IGNORE INTO presidents(id, first_name, last_name, dni, email, telephone, username) VALUES (1, 'President1', 'Surname1', '11111111A', 'email@gmail.com', '100111222', 'presidente1');
INSERT IGNORE INTO presidents(id, first_name, last_name, dni, email, telephone, username) VALUES (2, 'President2', 'Surname2', '21111111A', 'email@gmail.com', '200111222', 'presidente2');
INSERT IGNORE INTO presidents(id, first_name, last_name, dni, email, telephone, username) VALUES (3, 'President3', 'Surname3', '31111111A', 'email@gmail.com', '300111222', 'owner1');
INSERT IGNORE INTO presidents(id, first_name, last_name, dni, email, telephone, username) VALUES (4, 'President4', 'Surname4', '41111111A', 'email@gmail.com', '400111222', 'owner2');
INSERT IGNORE INTO presidents(id, first_name, last_name, dni, email, telephone, username) VALUES (5, 'President5', 'Surname5', '51111111A', 'email@gmail.com', '500111222', 'owner3');
INSERT IGNORE INTO presidents(id, first_name, last_name, dni, email, telephone, username) VALUES (6, 'President6', 'Surname6', '61111111A', 'email@gmail.com', '600111222', 'owner4');
INSERT IGNORE INTO presidents(id, first_name, last_name, dni, email, telephone, username) VALUES (7, 'President7', 'Surname7', '71111111A', 'email@gmail.com', '600151222', 'owner5');
INSERT IGNORE INTO presidents(id, first_name, last_name, dni, email, telephone, username) VALUES (8, 'President8', 'Surname8', '81111111A', 'email@gmail.com', '700111222', 'owner6');
INSERT IGNORE INTO presidents(id, first_name, last_name, dni, email, telephone, username) VALUES (9, 'President9', 'Surname9', '91111111A', 'email@gmail.com', '800111222', 'owner7');
INSERT IGNORE INTO presidents(id, first_name, last_name, dni, email, telephone, username) VALUES (10, 'Rafael', 'Liébana Fuentes', '11111111A', 'rafliefue@alum.us.es', '600111222', 'rafa');
INSERT IGNORE INTO presidents(id, first_name, last_name, dni, email, telephone, username) VALUES (11, 'Wakka', 'Chappu', '11111112A', 'wakka@gmail.com', '600121222', 'owner8');
INSERT IGNORE INTO presidents(id, first_name, last_name, dni, email, telephone, username) VALUES (12, 'Bickson', 'Graav', '11111122A', 'bickson@gmail.com', '602121222', 'owner9');

-- Referee users
INSERT IGNORE INTO referees(id, first_name, last_name, dni, email, telephone, username) VALUES (1, 'referee1', 'Surname1', '11111111A', 'email1@gmail.com', '600111222', 'referee1');
INSERT IGNORE INTO referees(id, first_name, last_name, dni, email, telephone, username) VALUES (2, 'referee2', 'Surname2', '22222222A', 'email2@gmail.com', '711222333', 'referee2');

-- Football Clubs
INSERT IGNORE INTO football_clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (1, true, 'Sevilla Fútbol Club','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/179.png', '1889-01-25', 'Ramón Sánchez-Pizjuan', 'Seville', '44000', '150000000',1);
INSERT IGNORE INTO football_clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (2, true, 'Real Madrid Club de Fútbol','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/186.png', '1902-03-06', 'Santiago Bernabeu', 'Madrid', '81000', '600000000',2);
INSERT IGNORE INTO football_clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (3, true, 'Fútbol Club Barcelona','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/178.png', '1899-11-29', 'Camp Nou', 'Barcelona', '99300', '650000000',3);
INSERT IGNORE INTO football_clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (4, true, 'Valencia Club de Fútbol','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/191.png', '1919-03-18', 'Mestalla', 'Valencia', '48600', '200000000',4);
INSERT IGNORE INTO football_clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (5, true, 'Atlético de Madrid','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/175.png', '1903-04-26', 'Wanda Metropolitano', 'Madrid', '69000', '350000000',5);
INSERT IGNORE INTO football_clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (6, true, 'Real Betis Balompié','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/185.png', '1907-09-12', 'Benito Villamarín', 'Seville', '60000', '100000000',6);
INSERT IGNORE INTO football_clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (7, true, 'Liverpool Football Club','https://pluspng.com/img-png/logo-liverpool-fc-png-liverpool-318.png', '1892-06-03', 'Anfield', 'Liverpool', '54000', '350000000',7);
INSERT IGNORE INTO football_clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (8, true, 'Manchester City Football Club','https://upload.wikimedia.org/wikipedia/sco/thumb/e/eb/Manchester_City_FC_badge.svg/1200px-Manchester_City_FC_badge.svg.png', '1894-04-16', 'City Of Manchester Stadium', 'Manchester', '55000', '10000000',8);
INSERT IGNORE INTO football_clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (9, false, 'Chelsea Football Club','https://upload.wikimedia.org/wikipedia/sco/thumb/c/cc/Chelsea_FC.svg/1200px-Chelsea_FC.svg.png', '1905-02-19', 'Stamford Bridge', 'London', '41000', '500000000',9);
INSERT IGNORE INTO football_clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (10, false, 'Besaid Aurochs','https://www.gamerguides.com/assets/trophies/44/1831-teamwork.png', '2000-02-19', 'Besaid Island Stadium', 'Besaid Island', '40', '2000000', 11);
INSERT IGNORE INTO football_clubs(id,status,name,crest,foundation_date,stadium,city,fans,money,president_id) VALUES (11, true, 'Luca Goers','https://cdn.staticneo.com/w/finalfantasy/f/f5/GoersSymbol.png', '2000-02-18', 'Luca Stadium', 'Luca', '40000', '200000000', 12); 
 

-- Match Requests 

INSERT IGNORE INTO match_requests(id, title, match_date, stadium, status, football_Club1, football_Club2, creator) VALUES(1, 'Partido amistoso 1', '2020-05-11 20:30', 'Ramón Sánchez-Pizjuan', 0, 'Sevilla Fútbol Club', 'Real Betis Balompié', 'presidente1');
INSERT IGNORE INTO match_requests(id, title, match_date, stadium, status, football_Club1, football_Club2, creator) VALUES(2, 'Partido amistoso 2', '2021-05-11 20:30', 'Ramón Sánchez-Pizjuan', 0, 'Sevilla Fútbol Club', 'Fútbol Club Barcelona', 'presidente1');
INSERT IGNORE INTO match_requests(id, title, match_date, stadium, status, football_Club1, football_Club2, creator) VALUES(3, 'Partido amistoso 3', '2020-05-11 20:30', 'Benito Villamarín', 0, 'Real Betis Balompié', 'Sevilla Fútbol Club', 'presidente6');
INSERT IGNORE INTO match_requests(id, title, match_date, stadium, status, football_Club1, football_Club2, creator) VALUES(4, 'Partido amistoso 4', '2021-05-11 20:30', 'Camp Nou', 0, 'Fútbol Club Barcelona', 'Sevilla Fútbol Club', 'presidente3');

INSERT IGNORE INTO match_requests(id, title, match_date, stadium, status, football_Club1, football_Club2, creator) VALUES(5, 'Partido amistoso 5', '2024-05-11 20:30', 'Mestalla', 0, 'Valencia Club de Fútbol', 'Sevilla Fútbol Club', 'owner2');
INSERT IGNORE INTO match_requests(id, title, match_date, stadium, status, football_Club1, football_Club2, creator) VALUES(6, 'Partido amistoso 6', '2025-05-11 20:30', 'Anfield', 0, 'Liverpool Football Club', 'Sevilla Fútbol Club', 'owner5');
 

-- Matches
INSERT IGNORE INTO matches(id, title, match_date, match_status, stadium, football_Club1, football_Club2, referee, creator) VALUES(0, 'Match title 0', '2023-05-11 20:30', 0, 'Ramón Sánchez-Pizjuan', 'Sevilla Fútbol Club', 'Real Betis Balompié', 'referee2', 'presidente1');
INSERT IGNORE INTO matches(id, title, match_date, match_status, stadium, football_Club1, football_Club2, creator) VALUES(10, 'Match title 1', '2020-05-11 20:30', 0, 'Ramón Sánchez-Pizjuan', 'Sevilla Fútbol Club', 'Real Betis Balompié', 'presidente1');
INSERT IGNORE INTO matches(id, title, match_date, match_status, stadium, football_Club1, football_Club2, referee, creator) VALUES(2, 'Match title 2', '2020-03-11 20:30', 1, 'Camp Nou', 'Fútbol Club Barcelona', 'Sevilla Fútbol Club', 'referee1', 'presidente3');
INSERT IGNORE INTO matches(id, title, match_date, match_status, stadium, football_Club1, football_Club2, creator) VALUES(3, 'Match title 3', '2021-05-11 20:30', 0, 'Ramón Sánchez-Pizjuan', 'Sevilla Fútbol Club', 'Real Betis Balompié', 'presidente1');

 
-- Match Referee Requests
INSERT IGNORE INTO match_referee_request(id, title, status, username, match_id) VALUES(1, 'Test title 1', 0, 'referee1', 3);
INSERT IGNORE INTO match_referee_request(id, title, status, username, match_id) VALUES(2, 'Test title 2', 0, 'referee2', 3);  
INSERT IGNORE INTO match_referee_request(id, title, status, username, match_id) VALUES(3, 'Test title 3', 0, 'referee1', 10);

        

-- Contract Commercial
INSERT IGNORE INTO contract_commercial(id,end_contract_clause,end_date,start_date,money,publicity) VALUES (1, 2000000,'2023-01-01','2020-01-01',100000,'https://logosmarcas.com/wp-content/uploads/2018/03/Nike-logo.png');
INSERT IGNORE INTO contract_commercial(id,end_contract_clause,end_date,start_date,money,publicity) VALUES (2, 2000000,'2024-01-01','2020-01-01',100000,'https://upload.wikimedia.org/wikipedia/commons/thumb/2/20/Adidas_Logo.svg/1200px-Adidas_Logo.svg.png');
INSERT IGNORE INTO contract_commercial(id,end_contract_clause,end_date,start_date,money,publicity) VALUES (3, 100000,'2024-01-01','2020-01-01',50000,'https://logosmarcas.com/wp-content/uploads/2018/03/PUMA-s%C3%ADmbolo.png');
INSERT IGNORE INTO contract_commercial(id,end_contract_clause,end_date,start_date,money,publicity) VALUES (4, 120000,'2025-01-01','2020-01-01',60000,'https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Burger_King_Logo.svg/1200px-Burger_King_Logo.svg.png');
INSERT IGNORE INTO contract_commercial(id,end_contract_clause,end_date,start_date,money,publicity) VALUES (5, 1000000000,'2024-01-01','2020-01-01',90000,'https://logodownload.org/wp-content/uploads/2019/12/riot-games-logo.png');


-- Coachs 

INSERT IGNORE INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (1, 'Julen', 'Lopetegui', 1, '3000000', '6000000', '1968-03-29' );
INSERT IGNORE INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (2, 'Zinedine', 'Zidane', 2, '6000000', '10000000', '1970-03-27' );
INSERT IGNORE INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (3, 'Quique', 'Setién', 3, '2500000', '5000000', '1960-07-21' );
INSERT IGNORE INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (4, 'Albert', 'Celades', 4, '1500000', '2000000', '1975-06-29' );
INSERT IGNORE INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (5, 'Diego Pablo', 'Simeone', 5, '25000000', '20000000', '1975-12-21' );
INSERT IGNORE INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (6, 'Rubi', 'Ferrer', 6, '1000000', '2000000', '1974-03-29' );
INSERT IGNORE INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (7, 'Jurgën', 'Klopp', 7, '8000000', '10000000', '1965-01-19' );
INSERT IGNORE INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (8, 'Pep', 'Guardiola', 8, '10000000', '15000000', '1974-07-07' );
INSERT IGNORE INTO coachs(id, first_name, last_name, football_Clubs_id, salary, clause, birth_date) VALUES (9, 'Frank', 'Lampard', 9, '5000000', '6000000', '1980-05-25' );

INSERT IGNORE INTO coachs(id, first_name, last_name, salary, clause, birth_date) VALUES (10, 'Marcelino', 'Garcia Toral','0' ,'0' ,'1975-01-19');
INSERT IGNORE INTO coachs(id, first_name, last_name, salary, clause, birth_date) VALUES (11, 'Unai', 'Emery','0' ,'0' ,'1974-07-07');
INSERT IGNORE INTO coachs(id, first_name, last_name, salary, clause, birth_date) VALUES (12, 'Mauricio', 'Pochetino','0' ,'0' ,'1980-05-25');

-- Football Players
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (1, 'Tomas', 'Vaclik', 0, 1, '18000000', '1989-03-29');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (2, 'Diego', 'Carlos', 1, 1, '25000000', '1993-03-15');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (3, 'Fernando', 'Reges', 2, 1, '7000000', '1987-07-25');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (4, 'Lucas', 'Ocampos', 2, 1, '25000000', '1994-07-11');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (5, 'Luuk', 'De Jong', 3, 1, '12000000', '1990-08-27');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (46, 'Jesús', 'Navas', 1, 1, '4000000', '1985-11-21');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (47, 'Jules', 'Koundé', 1, 1, '25000000', '1998-11-12');

INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (6, 'Thibaut', 'Courtois', 0, 2, '55000000', '1992-05-11');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (7, 'Sergio', 'Ramos', 1, 2, '18000000', '1986-03-30');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (8, 'Toni', 'Kross', 2, 2, '60000000', '1990-01-04');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (9, 'Gareth', 'Bale', 3, 2, '40000000', '1989-07-16');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (10, 'Karim', 'Benzemá', 3, 2, '40000000', '1987-12-19');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (48, 'Luka', 'Modric', 2, 2, '15000000', '1985-09-09');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (49, 'Daniel', 'Carvajal', 1, 2, '50000000', '1992-01-11');

INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (11, 'Marc-André', 'ter Stegen', 0, 3, '90000000', '1992-04-30');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (12, 'Gerard', 'Piqué', 1, 3, '25000000', '1987-02-02');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (13, 'Frankie', 'De Jong', 2, 3, '90000000', '1997-05-12');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (14, 'Antoine', 'Griezzman', 2, 3, '120000000', '1991-03-21');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (15, 'Lionel', 'Messi', 2, 3, '140000000', '1987-06-24');
INSERT IGNORE INTO football_Players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (50, 'Luis', 'Suárez', 3, 3, '40000000', '1987-01-24');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (51, 'Jordi', 'Alba', 1, 3, '50000000', '1989-03-21');

INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (16, 'Jasper', 'Cillesen', 0, 4, '18000000', '1989-04-22');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (17, 'Gabriel', 'Paulista', 1, 4, '22000000', '1990-11-26');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (18, 'Dani', 'Parejo', 2, 4, '35000000', '1989-04-16');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (19, 'Ferran', 'Torres', 2, 4, '40000000', '2000-02-29');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (20, 'Kevin', 'Gameiro', 3, 4, '8000000', '1987-05-09');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (52, 'Rodrigo', 'Moreno', 3, 4, '50000000', '1991-03-06');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (53, 'Ezequiel', 'Garay', 1, 4, '8000000', '1986-10-10');

INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (21, 'Jan', 'Oblak', 0, 5, '100000000', '1993-01-07');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (22, 'José', 'Gimenez', 1, 5, '70000000', '1995-01-20');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (23, 'Saul', 'Ñíguez', 2, 5, '90000000', '1994-11-21');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (24, 'Ángel', 'Correa', 3, 5, '35000000', '1995-03-09');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (25, 'Álvaro', 'Morata', 3, 5, '50000000', '1993-10-23');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (54, 'Joäo', 'Félix', 2, 5, '100000000', '1999-11-10');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (55, 'Stefan', 'Savic', 1, 5, '32000000', '1991-01-08');

INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (26, 'Joel', 'Robles', 0, 6, '7500000', '1990-06-17');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (27, 'Marc', 'Bartra', 1, 6, '22000000', '1991-01-15');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (28, 'Joaquin', 'Sánchez', 2, 6, '2000000', '1981-07-21');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (29, 'Nabil', 'Fekir', 2, 6, '40000000', '1993-07-18');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (30, 'Borja', 'Iglesias', 3, 6, '20000000', '1993-01-17');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (56, 'Aïssa', 'Mandi', 1, 6, '20000000', '1991-10-22');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (57, 'Andrés', 'Guardado', 2, 6, '4000000', '1986-09-28');

INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (31, 'Alisson', 'Becker', 0, 7, '90000000', '1992-10-02');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (32, 'Virgil', 'van Dijk', 1, 7, '100000000', '1991-07-08');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (33, 'Sadio', 'Mané', 2, 7, '150000000', '1992-04-10');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (34, 'Mohamed', 'Salah', 2, 7, '150000000', '1992-06-15');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (35, 'Roberto', 'Firmino', 3, 7, '90000000', '1991-10-02');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (58, 'Andrew', 'Robertson', 1, 7, '80000000', '1994-03-11');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (59, 'Fabinho', 'Tabares', 2, 7, '70000000', '1993-10-23');

INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (36, 'Ederson', 'Moraes', 0, 8, '70000000', '1993-08-17');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (37, 'Nicolas', 'Otamendi', 1, 8, '18000000', '1988-02-12');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (38, 'Bernardo', 'Silva', 2, 8, '100000000', '1994-08-10');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (39, 'Kevin', 'De Bruyne', 2, 8, '150000000', '1991-06-28');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (40, 'Sergio "Kun"', 'Agüero', 3, 8, '65000000', '1988-06-02');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (60, 'Aymeric', 'Laporte', 1, 8, '75000000', '1994-05-27');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (61, 'Raheem', 'Sterling', 2, 8, '65000000', '1994-12-08');

INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (41, 'Kepa', 'Arrizabalaga', 0, 9, '60000000', '1994-10-03');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (42, 'César', 'Azpilicueta', 1, 9, '30000000', '1989-08-28');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (43, 'NGolo', 'Kanté', 2, 9, '100000000', '1991-03-29');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (44, 'Christian', 'Pulisic', 2, 9, '60000000', '1998-09-18');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (45, 'Olivier', 'Giroud', 3, 9, '9000000', '1986-09-30');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (62, 'Andreas', 'Christensen', 1, 9, '30000000', '1996-04-10');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, football_Clubs_id, value, birth_date) VALUES (63, 'Tammy', 'Abraham', 2, 9, '50000000', '1997-10-02');

INSERT IGNORE INTO football_players(id, first_name, last_name, position, value, birth_date) VALUES (64, 'Jorge', 'Salcedo', 2, '10000000', '1994-10-03');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, value, birth_date) VALUES (65, 'Albert', 'Martin', 3, '60000000', '1992-11-06');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, value, birth_date) VALUES (66, 'Sergio', 'Molina', 0, '99999999', '1984-11-06');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, value, birth_date) VALUES (67, 'Pablo', 'Escobar', 3, '70000000', '1949-11-06');
INSERT IGNORE INTO football_players(id, first_name, last_name, position, value, birth_date) VALUES (68, 'Rafael', 'Cantero', 1, '11000000', '1990-11-06');

-- Football Player Contracts
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (1, 1, '2018-06-30', '2021-06-30', '9000000', 1, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (2, 1, '2019-06-30', '2023-06-30', '12500000', 2, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (3, 1, '2019-06-30', '2022-06-30', '4500000', 3, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (4, 1, '2019-06-30', '2023-06-30', '12500000', 4, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (5, 1, '2019-06-30', '2023-06-30', '6000000', 5, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (6, 1, '2017-06-30', '2022-06-30', '2000000', 46, '1600000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (7, 1, '2019-06-30', '2024-06-30', '12500000', 47, '1500000');

INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (8, 2, '2018-06-30', '2021-06-30', '9000000', 6, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (9, 2, '2019-06-30', '2023-06-30', '12500000', 7, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (10, 2, '2019-06-30', '2022-06-30', '4500000', 8, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (11, 2, '2019-06-30', '2023-06-30', '12500000', 9, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (12, 2, '2019-06-30', '2023-06-30', '6000000', 10, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (13, 2, '2017-06-30', '2022-06-30', '2000000', 48, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (14, 2, '2019-06-30', '2024-06-30', '12500000', 49, '1500000');

INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (15, 3, '2018-06-30', '2021-06-30', '9000000', 11, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (16, 3, '2019-06-30', '2023-06-30', '12500000', 12, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (17, 3, '2019-06-30', '2022-06-30', '4500000', 13, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (18, 3, '2019-06-30', '2023-06-30', '12500000', 14, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (19, 3, '2019-06-30', '2023-06-30', '6000000', 15, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (20, 3, '2017-06-30', '2022-06-30', '2000000', 50, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (21, 3, '2019-06-30', '2024-06-30', '12500000', 51, '1500000');

INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (22, 4, '2018-06-30', '2021-06-30', '9000000', 16, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (23, 4, '2019-06-30', '2023-06-30', '12500000', 17, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (24, 4, '2019-06-30', '2022-06-30', '4500000', 18, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (25, 4, '2019-06-30', '2023-06-30', '12500000', 19, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (26, 4, '2019-06-30', '2023-06-30', '6000000', 20, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (27, 4, '2017-06-30', '2022-06-30', '2000000', 52, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (28, 4, '2019-06-30', '2024-06-30', '12500000', 53, '1500000');

INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (29, 5, '2018-06-30', '2021-06-30', '9000000', 21, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (30, 5, '2019-06-30', '2023-06-30', '12500000', 22, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (31, 5, '2019-06-30', '2022-06-30', '4500000', 23, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (32, 5, '2019-06-30', '2023-06-30', '12500000', 24, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (33, 5, '2019-06-30', '2023-06-30', '6000000', 25, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (34, 5, '2017-06-30', '2022-06-30', '2000000', 54, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (35, 5, '2019-06-30', '2024-06-30', '12500000', 55, '1500000');

INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (36, 6, '2018-06-30', '2021-06-30', '9000000', 26, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (37, 6, '2019-06-30', '2023-06-30', '12500000', 27, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (38, 6, '2019-06-30', '2022-06-30', '4500000', 28, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (39, 6, '2019-06-30', '2023-06-30', '12500000', 29, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (40, 6, '2019-06-30', '2023-06-30', '6000000', 30, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (41, 6, '2017-06-30', '2022-06-30', '2000000', 56, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (42, 6, '2019-06-30', '2024-06-30', '12500000', 57, '1500000');


INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (43, 7, '2018-06-30', '2021-06-30', '9000000', 31, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (44, 7, '2019-06-30', '2023-06-30', '12500000', 32, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (45, 7, '2019-06-30', '2022-06-30', '4500000', 33, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (46, 7, '2019-06-30', '2023-06-30', '12500000', 34, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (47, 7, '2019-06-30', '2023-06-30', '6000000', 35, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (48, 7, '2017-06-30', '2022-06-30', '2000000', 58, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (49, 7, '2019-06-30', '2024-06-30', '12500000', 59, '1500000');

INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (50, 8, '2018-06-30', '2021-06-30', '9000000', 36, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (51, 8, '2019-06-30', '2023-06-30', '12500000', 37, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (52, 8, '2019-06-30', '2022-06-30', '4500000', 38, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (53, 8, '2019-06-30', '2023-06-30', '12500000', 39, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (54, 8, '2019-06-30', '2023-06-30', '6000000', 40, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (55, 8, '2017-06-30', '2022-06-30', '2000000', 60, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (56, 8, '2019-06-30', '2024-06-30', '12500000', 61, '1500000');

INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (57, 9, '2018-06-30', '2021-06-30', '9000000', 41, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (58, 9, '2019-06-30', '2023-06-30', '12500000', 42, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (59, 9, '2019-06-30', '2022-06-30', '4500000', 43, '2000000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (60, 9, '2019-06-30', '2023-06-30', '12500000', 44, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (61, 9, '2019-06-30', '2023-06-30', '6000000', 45, '1500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (62, 9, '2017-06-30', '2022-06-30', '2000000', 62, '2500000');
INSERT IGNORE INTO contract_player(id, football_Clubs_id, start_date, end_date, end_contract_clause ,football_Players_id, salary) VALUES (63, 9, '2019-06-30', '2024-06-30', '12500000', 63, '1500000');

-- Football Player Statistics
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(1, 0, 0, 0, 0, 0, 2019, 2020, 1);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(2, 0, 0, 0, 0, 0, 2019, 2020, 2);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(3, 0, 0, 0, 0, 0, 2019, 2020, 3);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(4, 0, 0, 0, 0, 0, 2019, 2020, 4);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(5, 0, 0, 0, 0, 0, 2019, 2020, 5);

INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(6, 0, 0, 0, 0, 0, 2019, 2020, 6);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(7, 0, 0, 0, 0, 0, 2019, 2020, 7);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(8, 0, 0, 0, 0, 0, 2019, 2020, 8);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(9, 0, 0, 0, 0, 0, 2019, 2020, 9);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(10, 0, 0, 0, 0, 0, 2019, 2020, 10);

INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(11, 0, 0, 0, 0, 0, 2019, 2020, 11);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(12, 0, 0, 0, 0, 0, 2019, 2020, 12);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(13, 0, 0, 0, 0, 0, 2019, 2020, 13);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(14, 0, 0, 0, 0, 0, 2019, 2020, 14);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(15, 0, 0, 0, 0, 0, 2019, 2020, 15);

INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(16, 0, 0, 0, 0, 0, 2019, 2020, 16);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(17, 0, 0, 0, 0, 0, 2019, 2020, 17);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(18, 0, 0, 0, 0, 0, 2019, 2020, 18);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(19, 0, 0, 0, 0, 0, 2019, 2020, 19);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(20, 0, 0, 0, 0, 0, 2019, 2020, 20);

INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(21, 0, 0, 0, 0, 0, 2019, 2020, 21);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(22, 0, 0, 0, 0, 0, 2019, 2020, 22);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(23, 0, 0, 0, 0, 0, 2019, 2020, 23);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(24, 0, 0, 0, 0, 0, 2019, 2020, 24);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(25, 0, 0, 0, 0, 0, 2019, 2020, 25);

INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(26, 0, 0, 0, 0, 0, 2019, 2020, 26);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(27, 0, 0, 0, 0, 0, 2019, 2020, 27);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(28, 0, 0, 0, 0, 0, 2019, 2020, 28);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(29, 0, 0, 0, 0, 0, 2019, 2020, 29);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(30, 0, 0, 0, 0, 0, 2019, 2020, 30);

INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(31, 0, 0, 0, 0, 0, 2019, 2020, 31);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(32, 0, 0, 0, 0, 0, 2019, 2020, 32);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(33, 0, 0, 0, 0, 0, 2019, 2020, 33);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(34, 0, 0, 0, 0, 0, 2019, 2020, 34);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(35, 0, 0, 0, 0, 0, 2019, 2020, 35);

INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(36, 0, 0, 0, 0, 0, 2019, 2020, 36);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(37, 0, 0, 0, 0, 0, 2019, 2020, 37);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(38, 0, 0, 0, 0, 0, 2019, 2020, 38);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(39, 0, 0, 0, 0, 0, 2019, 2020, 39);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(40, 0, 0, 0, 0, 0, 2019, 2020, 40);

INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(41, 0, 0, 0, 0, 0, 2019, 2020, 41);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(42, 0, 0, 0, 0, 0, 2019, 2020, 42);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(43, 0, 0, 0, 0, 0, 2019, 2020, 43);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(44, 0, 0, 0, 0, 0, 2019, 2020, 44);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(45, 0, 0, 0, 0, 0, 2019, 2020, 45);

INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(46, 0, 0, 0, 0, 0, 2019, 2020, 46);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(47, 0, 0, 0, 0, 0, 2019, 2020, 47);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(48, 0, 0, 0, 0, 0, 2019, 2020, 48);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(49, 0, 0, 0, 0, 0, 2019, 2020, 49);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(50, 0, 0, 0, 0, 0, 2019, 2020, 50);

INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(51, 0, 0, 0, 0, 0, 2019, 2020, 51);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(52, 0, 0, 0, 0, 0, 2019, 2020, 52);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(53, 0, 0, 0, 0, 0, 2019, 2020, 53);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(54, 0, 0, 0, 0, 0, 2019, 2020, 54);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(55, 0, 0, 0, 0, 0, 2019, 2020, 55);

INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(56, 0, 0, 0, 0, 0, 2019, 2020, 56);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(57, 0, 0, 0, 0, 0, 2019, 2020, 57);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(58, 0, 0, 0, 0, 0, 2019, 2020, 58);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(59, 0, 0, 0, 0, 0, 2019, 2020, 59);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(60, 0, 0, 0, 0, 0, 2019, 2020, 60);

INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(61, 0, 0, 0, 0, 0, 2019, 2020, 61);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(62, 0, 0, 0, 0, 0, 2019, 2020, 62);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(63, 0, 0, 0, 0, 0, 2019, 2020, 63);
INSERT IGNORE INTO football_player_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id) VALUES(64, 0, 0, 0, 0, 0, 2019, 2020, 64);

-- Match Records

INSERT IGNORE INTO match_record(id, title, status, season_end, season_start, result, match_id) VALUES(0, 'title 0', 0, '2020', '2019', 'result', 0);
INSERT IGNORE INTO match_record(id, title, status, season_end, season_start, result, match_id) VALUES(1, 'title 1', 0, '2020', '2019', 'result', 2);



-- Football Player Match Statistics
INSERT IGNORE INTO football_player_match_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id, match_record) VALUES (1, 0, 0, 0, 0, null, 2019, 2020, 1, 1);

--Fan Records
INSERT IGNORE INTO fan(id, credit_card_number, cvv, expiration_date, vip, club_id, user_id) VALUES (1, null, null, null, false, 1, 1);

--Competitions
INSERT IGNORE INTO competitions(id, name, description, type, reward, status, creator) VALUES (1, 'Premier League', 'Torneo donde los equipos participantes jugarán todos contra todos.', 0, 10000000, false, 'pedro' );
INSERT IGNORE INTO competitions(id, name, description, type, reward, status, creator) VALUES (2, 'La Liga', 'Torneo donde los equipos participantes jugarán todos contra todos.', 0, 10000000, true , 'pedro' );
INSERT IGNORE INTO competitions(id, name, description, type, reward, status, creator) VALUES (3, 'Copa del Rey', 'Torneo donde los equipos participantes jugarán rondas eliminatorias', 1, 10000000, true, 'pedro' );
INSERT IGNORE INTO competitions(id, name, description, type, reward, status) VALUES (4, 'Prueba', 'prueba', 0, 10000000, true );
INSERT IGNORE INTO competition_clubs(competition_id, clubs) VALUES (2, 'Sevilla Fútbol Club');
INSERT IGNORE INTO competition_clubs(competition_id, clubs) VALUES (2, 'Manchester City Football Club');
INSERT IGNORE INTO competition_clubs(competition_id, clubs) VALUES (2, 'Liverpool Football Club');
INSERT IGNORE INTO competition_clubs(competition_id, clubs) VALUES (2, 'Real Betis Balompié');

INSERT IGNORE INTO competition_clubs(competition_id, clubs) VALUES (1, 'Sevilla Fútbol Club');
INSERT IGNORE INTO competition_clubs(competition_id, clubs) VALUES (1, 'Manchester City Football Club');
INSERT IGNORE INTO competition_clubs(competition_id, clubs) VALUES (1, 'Liverpool Football Club');
INSERT IGNORE INTO competition_clubs(competition_id, clubs) VALUES (1, 'Real Betis Balompié');

INSERT IGNORE INTO competition_clubs(competition_id, clubs) VALUES (3, 'Real Betis Balompié');
INSERT IGNORE INTO competition_clubs(competition_id, clubs) VALUES (3, 'Sevilla Fútbol Club');
INSERT IGNORE INTO competition_clubs(competition_id, clubs) VALUES (3, 'Manchester City Football Club');
INSERT IGNORE INTO competition_clubs(competition_id, clubs) VALUES (3, 'Liverpool Football Club');
INSERT IGNORE INTO competition_clubs(competition_id, clubs) VALUES (3, 'Valencia Club de Fútbol');
INSERT IGNORE INTO competition_clubs(competition_id, clubs) VALUES (3, 'Fútbol Club Barcelona');
INSERT IGNORE INTO competition_clubs(competition_id, clubs) VALUES (3, 'Atlético de Madrid');
INSERT IGNORE INTO competition_clubs(competition_id, clubs) VALUES (3, 'Real Madrid Club de Fútbol');

--Calendary

INSERT IGNORE INTO calendary(id, competition_id) VALUES (1, 2);

--Jornadas

INSERT IGNORE INTO jornada(id, name, calendary_id) VALUES (1, 'Jornada 1', 1);
INSERT IGNORE INTO jornada(id, name, calendary_id) VALUES (2, 'Jornada 2', 1);
INSERT IGNORE INTO jornada(id, name, calendary_id) VALUES (3, 'Jornada 3', 1);
INSERT IGNORE INTO jornada(id, name, calendary_id) VALUES (4, 'Jornada 4', 1);
INSERT IGNORE INTO jornada(id, name, calendary_id) VALUES (5, 'Jornada 5', 1);
INSERT IGNORE INTO jornada(id, name, calendary_id) VALUES (6, 'Jornada 6', 1);

--MatchesJornadas

INSERT IGNORE INTO matches(id, title, match_date, match_status, stadium, football_Club1, football_Club2, creator, jornada_id) VALUES(4, 'Jornada 1 de la Liga', '2020-08-11 20:30', 1, 'Ramón Sánchez-Pizjuan', 'Sevilla Fútbol Club', 'Real Betis Balompié', 'pedro', 1);

INSERT IGNORE INTO match_record(id, title, status, season_end, season_start, result, match_id) VALUES(6, 'Partido de Liga', 0, '2020', '2019', '3-0', 4);
INSERT IGNORE INTO football_player_match_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id, match_record) VALUES (2, 1, 2, 1, 0, null, 2019, 2020, 4, 6);
INSERT IGNORE INTO football_player_match_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id, match_record) VALUES (3, 1, 1, 2, 1, null, 2019, 2020, 3, 6);
INSERT IGNORE INTO football_player_match_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id, match_record) VALUES (4, 0, 0, 1, 0, 0, 2019, 2020, 1, 6);
INSERT IGNORE INTO football_player_match_statistic(id, assists, goals, yellow_cards, red_cards, received_goals, season_start, season_end, football_player_id, match_record) VALUES (5, 0, 0, 0, 0, 3, 2019, 2020, 26, 6);


-- Player Transfer Requests
INSERT IGNORE INTO player_transfer_request(id, offer, contract_time, status, player_id, contract_id, club_id) VALUES(0, 2000000, 1, 0, 1, 1, '2');
INSERT IGNORE INTO player_transfer_request(id, offer, contract_time, status, player_id, contract_id, club_id) VALUES(1, 2000000, 2, 0, 14, 18, '1');

-- Coach Transfer Requests
INSERT IGNORE INTO coach_transfer_request(id, offer, status, my_coach, requested_coach) VALUES(0, 1000000, 0, 2, 7);

--Rounds
INSERT IGNORE INTO round(id, name, competition_id) VALUES (1, 'Cuartos de final',3);
INSERT IGNORE INTO round(id,name) VALUES (2, 'prueba');

--Matches for Round
INSERT IGNORE INTO matches(id, title, match_date, match_status, stadium, football_Club1, football_Club2, referee,round_id, creator) VALUES (5, 'Partido de Cuartos de final', '2023-05-11 20:30', 1, 'Ramón Sánchez-Pizjuan', 'Sevilla Fútbol Club', 'Real Betis Balompié', 'referee2',1, 'presidente1');
INSERT IGNORE INTO matches(id, title, match_date, match_status, stadium, football_Club1, football_Club2, referee,round_id, creator) VALUES (6, 'Partido de Cuartos de final', '2020-05-11 20:30', 1, 'Ramón Sánchez-Pizjuan', 'Valencia Club de Fútbol', 'Atlético de Madrid','referee1',1, 'presidente1');
INSERT IGNORE INTO matches(id, title, match_date, match_status, stadium, football_Club1, football_Club2, referee,round_id, creator) VALUES (7, 'Partido de Cuartos de final', '2020-05-11 20:30', 1, 'Camp Nou', 'Manchester City Football Club', 'Liverpool Football Club', 'referee1',1, 'presidente3');
INSERT IGNORE INTO matches(id, title, match_date, match_status, stadium, football_Club1, football_Club2, referee,round_id, creator) VALUES (8, 'Partido de Cuartos de final', '2021-05-11 20:30', 0, 'Ramón Sánchez-Pizjuan', 'Real Madrid Club de Fútbol', 'Fútbol Club Barcelona','referee1',1, 'presidente1');


--Match Records for Round
INSERT IGNORE INTO match_record(id, title, status, season_end, season_start, result, winner, match_id) VALUES(2, 'title 1', 1, '2020', '2019', '1-0','Sevilla Fútbol Club', 5);
INSERT IGNORE INTO match_record(id, title, status, season_end, season_start, result, winner, match_id) VALUES(3, 'title 1', 1, '2020', '2019', '3-2','Valencia Club de Fútbol', 6);
INSERT IGNORE INTO match_record(id, title, status, season_end, season_start, result, winner, match_id) VALUES(4, 'title 1', 1, '2020', '2019', '3-4','Liverpool Football Club', 7);
INSERT IGNORE INTO match_record(id, title, status, season_end, season_start, result, winner, match_id) VALUES(5, 'title 1', 0, '2020', '2019', '0-2','Fútbol Club Barcelona', 8);

-- Match Date Change Request
INSERT IGNORE INTO MATCH_DATE_CHANGE_REQUESTS(id, new_date, reason, request_creator, status, title, match_id) VALUES(1, '2023-08-11 20:30:00', 'qqq', 'presidente1', 0, 'Match title 0', 0);
INSERT IGNORE INTO MATCH_DATE_CHANGE_REQUESTS(id, new_date, reason, request_creator, status, title, match_id) VALUES(2, '2021-12-11 20:30:00', 'aaa', 'presidente2', 0, 'Partido de Cuartos de final', 8);

