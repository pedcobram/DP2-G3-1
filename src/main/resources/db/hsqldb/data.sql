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
INSERT INTO authorities VALUES ('pedro','authenticated');
-- User Ignacio
INSERT INTO users(username,password,enabled) VALUES ('ignacio','ignacio',TRUE);
INSERT INTO authorities VALUES ('ignacio','authenticated');
-- User Gonzalo
INSERT INTO users(username,password,enabled) VALUES ('gonzalo','gonzalo',TRUE);
INSERT INTO authorities VALUES ('gonzalo','authenticated');
-- User Manuel
INSERT INTO users(username,password,enabled) VALUES ('manuel','manuel',TRUE);
INSERT INTO authorities VALUES ('manuel','authenticated');

INSERT INTO authenticateds VALUES (1, 'Rafael', 'Liébana Fuentes', '11111111A', 'rafliefue@alum.us.es', '600111222', 'rafa');
INSERT INTO authenticateds VALUES (2, 'Pedro Manuel', 'Cobos Ramos', '22222222A', 'pedcobram@alum.us.es', '600222333', 'pedro');
INSERT INTO authenticateds VALUES (3, 'Ignacio José', 'Rodríguez Flores', '33333333A', 'ignrodflo@alum.us.es', '600333444', 'ignacio');
INSERT INTO authenticateds VALUES (4, 'Gonzalo', 'Fernandez Jiménez', '44444444A', 'gonferjim@alum.us.es', '600444555', 'gonzalo');
INSERT INTO authenticateds VALUES (5, 'Manuel', 'Sánchez Rodríguez', '55555555A', 'mansanrod@alum.us.es', '600555666', 'manuel');

INSERT INTO presidents VALUES (1, 'President1', 'Surname1', '11111111A', 'email@gmail.com', '100111222', 'owner1');
INSERT INTO presidents VALUES (2, 'President2', 'Surname2', '21111111A', 'email@gmail.com', '200111222', 'owner1');
INSERT INTO presidents VALUES (3, 'President3', 'Surname3', '31111111A', 'email@gmail.com', '300111222', 'owner1');
INSERT INTO presidents VALUES (4, 'President4', 'Surname4', '41111111A', 'email@gmail.com', '400111222', 'owner1');
INSERT INTO presidents VALUES (5, 'President5', 'Surname5', '51111111A', 'email@gmail.com', '500111222', 'owner1');
INSERT INTO presidents VALUES (6, 'President6', 'Surname6', '61111111A', 'email@gmail.com', '600111222', 'owner1');
INSERT INTO presidents VALUES (7, 'President7', 'Surname7', '71111111A', 'email@gmail.com', '600151222', 'owner1');
INSERT INTO presidents VALUES (8, 'President8', 'Surname8', '81111111A', 'email@gmail.com', '700111222', 'owner1');
INSERT INTO presidents VALUES (9, 'President9', 'Surname9', '91111111A', 'email@gmail.com', '800111222', 'owner1');

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

INSERT INTO football_Clubs(id,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (1, 'Sevilla Fútbol Club','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/179.png', '1889-01-25', 'Ramón Sánchez-Pizjuan', 'Seville', '44000', 'Julen Lopetegui', '150000000',1);
INSERT INTO football_Clubs(id,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (2, 'Real Madrid Club de Fútbol','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/186.png', '1902-03-06', 'Santiago Bernabeu', 'Madrid', '81000', 'Zinedine Zidane', '600000000',2);
INSERT INTO football_Clubs(id,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (3, 'Fútbol Club Barcelona','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/178.png', '1899-11-29', 'Camp Nou', 'Barcelona', '99300', 'Quique Setién', '650000000',3);
INSERT INTO football_Clubs(id,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (4, 'Valencia Club de Fútbol','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/191.png', '1919-03-18', 'Mestalla', 'Valencia', '48600', 'Albert Celades', '200000000',4);
INSERT INTO football_Clubs(id,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (5, 'Atlético de Madrid','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/175.png', '1903-04-26', 'Wanda Metropolitano', 'Madrid', '69000', 'Diego Pablo Simeone', '350000000',5);
INSERT INTO football_Clubs(id,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (6, 'Real Betis Balompié','https://e00-marca.uecdn.es/assets/sports/logos/football/png/144x144/185.png', '1907-09-12', 'Benito Villamarín', 'Seville', '60000', 'Rubi', '350000000',6);
INSERT INTO football_Clubs(id,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (7, 'Liverpool Football Club','https://pluspng.com/img-png/logo-liverpool-fc-png-liverpool-318.png', '1892-06-03', 'Anfield', 'Liverpool', '54000', 'Jürgen Klopp', '350000000',7);
INSERT INTO football_Clubs(id,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (8, 'Manchester City Football Club','https://upload.wikimedia.org/wikipedia/sco/thumb/e/eb/Manchester_City_FC_badge.svg/1200px-Manchester_City_FC_badge.svg.png', '1894-04-16', 'City Of Manchester Stadium', 'Manchester', '55000', 'Pep Guardiola', '550000000',8);
INSERT INTO football_Clubs(id,name,crest,foundation_date,stadium,city,fans,coach,money,president_id) VALUES (9, 'Chelsea Football Club','https://upload.wikimedia.org/wikipedia/sco/thumb/c/cc/Chelsea_FC.svg/1200px-Chelsea_FC.svg.png', '1905-02-19', 'Stamford Bridge', 'London', '41000', 'Frank Lampard', '500000000',9);



