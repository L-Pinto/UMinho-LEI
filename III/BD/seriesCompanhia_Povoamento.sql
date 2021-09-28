-- Esquema: "seriescompanhia"
USE `seriescompanhia` ;

--
-- Permissão para fazer operações de remoção de dados.
SET SQL_SAFE_UPDATES = 0;

--

-- Povamento da tabela "cartao"
INSERT INTO cartao
	(IDCartao, cartao)
    VALUES
    (1, 12335),
    (2, 65546),
	(3, 33333),
    (4, 44444),
	(5, 55555),
    (6, 66666),
	(7, 77777),
    (8, 88888),
    
    (9, 99999),
    (10, 10101)
    ;
    
-- Povoamento da tabela "Utilizador"
INSERT INTO utilizador
	(IDUtilizador, nome, localidade, rua, codigoPostal, pais, email, cartao)
	VALUES 
		(1, 'João da Costa e Campos', 'Braga', 'Rua das Adegas Felizes, 12, 1ª Cave', '4710-035', 'Portugal', 'jcc@acacia.pt',1),
		(2, 'Josefina Vivida da Paz', 'Lisboa', 'Av dos Castros Reais, 122, 3º Andar', '4520-980', 'Portugal', 'josefina@acacia.pt',2),
        (3, 'Joaquim da Silva', 'Porto', 'Rua das Pancadas, 3, 4 Andarº', '4200-135', 'Portugal', 'jds@acacia.pt',3),
		(4, 'António Ramalhos', 'Aveiro', 'Av das Rosas, 122, 1º Andar', '2860-310', 'Portugal', 'jr@acacia.pt',4),
        (5, 'Anastasia Batista', 'Coimbra', 'Praceta dos Tufos, 1513, 2º Piso', '4010-235', 'Portugal', 'ab@acacia.pt',5),
		(6, 'Deolinda Pereira Cardoso', 'Faro', 'Rua das Pombas, 77, 2º Esq', '4444-777', 'Portugal', 'dpc@acacia.pt',6),
        (7, 'Duarte Macedo da Rocha', 'Leiria', 'Edifio Capital do Bouco, 121, 1º ', '4010-912', 'Portugal', 'dmdr@acacia.pt',7),
		(8, 'Fernando Botelho Lopes', 'Viseu', 'Urbanização das Penedas, 61, 4º', '4919-990', 'Portugal', 'fbl@acacia.pt',8),
        
        (9, 'Ana Lu Bretice Renoir', 'Marseille', 'Pont de Lion, 12, 3º', '1234-567', 'France', 'albr@acacia.fr',9),
	    (10, 'John Marco Holmes', 'London', 'Baker Street, 121, 1º', '5651-127', 'UK', 'jmh@acacia.en',10)
	;

-- Povoamento da tabela "servico"
INSERT INTO servico
	(IDServico, preco)
    VALUES
    (1,55.90)
    ;
    
-- Povamento da tabela "subscreve"
INSERT INTO subscreve
	(DataInicio, DataFim, preco, utilizador, servico)
    VALUES
    ('2019-06-20', '2020-06-20', 46.90, 1,1),
    ('2019-07-29', '2020-07-29', 46.90, 2,1),
    ('2019-08-14', '2020-08-14', 46.90, 3,1),
    ('2019-10-19', '2020-10-19', 46.90, 4,1),
    ('2019-12-27', '2020-12-27', 46.90, 5,1),
    ('2020-02-28', '2021-02-28', 46.90, 6,1),
    ('2020-03-20', '2021-03-20', 46.90, 7,1),
    ('2020-04-10', '2021-04-10', 55.90, 8,1),
    ('2020-04-17', '2021-04-17', 55.90, 9,1),
    ('2020-05-13', '2021-05-13', 55.90, 10,1)
    ;
    
-- Povamento da tabela "companhia"
INSERT INTO companhia
	(IDCompanhia, nome, comissao, dataFundacao)
    VALUES
    (1, 'BBC', 8000, '1922-10-18'),
    (2, 'HBO', 12000, '1972-11-08'),
    (3, 'SYFY', 5000, '1992-09-24'),
    (4, 'Netflix', 30000, '1997-08-27'),
    (5, 'NBC', 8000, '1926-11-15')
    ;

-- Povamento da tabela "genero"
INSERT INTO genero
	(IDGenero, genero)
    VALUES
    (1, 'drama'),
    (2,'fantasia'),
    (3, 'acao'),
    (4, 'crime'),
    (5, 'comedia')
    ;

-- Povamento da tabela "serie"
INSERT INTO serie
	(IDSerie, nome, avaliacao, classificacaoEtaria, premio, idioma, genero, servico, companhia)
    VALUES
    (1, 'Peaky Blinders', 8.8, 18, 7, 'ingles', 4, 1,1),
    (2, 'Game of Thrones', 9.3, 18, 269, 'ingles', 3, 1,2),
    (3, 'The Magicians', 7.6, 16, 1, 'ingles', 2, 1,3),
    (4, 'La casa de Papel', 8.4, 16, 28,'espanhol', 3,1,4),
	(5, 'Lufifer', 8.2, 14, 0,'ingles', 4,1,4),
    (6, 'Baby', 6.8, 16, 0, 'italiano', 1,1,4), 
    (7, 'The boys',8.7, 16, 1, 'ingles', 3,1,2),
    (8, 'Brooklyn Nine-Nine',8.4, 14, 1,'ingles', 5,1,5)
    ;

-- Povoamento da tabela "assiste"
INSERT INTO assiste
	(favorita, utilizador, serie)
    VALUES
    (true, 1,3),
    (true, 1,5),
    (false, 1,1),
    (false, 1,7),
    
    (true,2,4),
    (true,2,6),
    (false,2,2),
    (false,2,8),
    
    (true,3,1),
    (true,3,2),
    (true,3,4),
    (false,3,6),
    (false,3,7),
    
    (true,4,2),
    (true,4,5),
    (true,4,6),
    (false,4,1),
    (false,4,3),
    (false,4,7),
    (false,4,8),
    
    (true,5,3),
    (false,5,2),
    (false,5,5),
    
	(true,6,2),
    (true,6,4),
    (true,6,6),
    (false,6,7),
    (false,6,8),
    
    (false,7,1),
	(false,7,2),
    (false,7,3),
	(false,7,5),
	(false,7,7),
    
    (true,8,2),
    (true,8,8),
    (false,8,4),
    
    (true,9,1),
    (true,9,3),
    (true,9,5),
    (false,9,6),
    (false,9,8),
    
    (true,10,2),
    (true,10,3),
    (true,10,5),
    (true,10,7)
    ;

-- Povamento da tabela "ator"
INSERT INTO ator
	(IDAtor, nome, dataNascimento, nacionalidade, genero)
    VALUES
    (1, 'Cillian Murphy', '1976-05-25', 'irlandesa', 'masculino'),
    (2, 'Emilia Clarke', '1986-10-23', 'inglesa', 'feminino'),
    (3, 'Stella Maeve Johnston ', '1989-11-14', 'norte-americana', 'feminino'),
	(4, 'Kit Harington', '1986-12-26', 'inglesa', 'masculino'),
    (5, 'Peter Dinklage','1969-05-11', 'inglesa', 'masculino'),
    (6, 'Álvaro Morte','1975-02-23', 'espanhol', 'masculino'),
    (7, 'Úrsula Corberó','1989-08-11', 'espanhol', 'feminino'),
    (9, 'Tom Ellis',' 1978-11-17', 'ingles', 'masculino'),
    (10, 'Lauren German','1978-11-29', 'norte-americana', 'feminino'),
    (11, 'Benedetta Porcaroli','1998-06-11', 'italiana', 'feminino'),
    (12, 'Alice Pagani','1998-02-19', 'italiana', 'feminino'),
    (13, 'Karl Urban','1972-06-07', 'australiano', 'masculino'),
    (14, 'Jack Henry Quaid','1992-04-24', 'norte-americano', 'masculino'),
    (15, 'Antony Starr','1975-10-25', 'australiano', 'masculino'),
	(16, 'Andy Samberg','1978-08-18', 'norte-americano', 'masculino'),
    (17, 'Melissa Fumero','1982-08-19', 'norte-americano', 'feminino'),
    (18, 'Andre Braugher','1962-07-01', 'norte-americano', 'masculino'),
    (19, 'Lena Headey','1973-10-03', 'inglesa', 'feminino')
    ;
    
-- Povoamento da tabela possui
INSERT INTO possui
	(serie, ator)
    VALUES
    (1,1),
    (2,2),
    (2,4),
    (2,5),
    (2,19),
    (3,3),
    (4,6),
    (4,7),
    (5,9),
    (5,10),
    (6,11),
    (6,12),
    (7,13),
    (7,14),
    (7,15),
    (8,16),
    (8,17),
    (8,18)
    ;

INSERT INTO criador
	(IDCriador, nome, dataNascimento, genero, nacionalidade)
    VALUES
    (21, 'David Benioff', '1970-09-23', 'masculino', 'americano'),
    (11, 'Steven Knight', '1959-03-22', 'masculino', 'ingles'),
    (31, 'Sera Gamble', '1983-09-20', 'feminino', 'ingles'),
	(22, 'D. B. Weiss', '1971-04-23', 'masculino', 'americano'),
	(41, 'Álex Pina', '1967-06-23', 'masculino', 'espanhol'),
	(51, 'Tom Kapinos', '1969-07-12', 'masculino', 'americano'),
	(61, 'Antonio Le Fosse', '1952-03-22', 'masculino', 'italiano'),
	(71, 'Eric Kripke', '1974-04-24', 'masculino', 'americano'),
	(81, 'Dan Goor', '1975-04-28', 'masculino', 'americano')
    ;
    
-- Povoamento da tabela "cria"
INSERT INTO cria
	(serie,criador)
    VALUES
    (1,11),
    (2,21),
    (2,22),
    (3,31),
	(4,41),
	(5,51),
	(6,61),
	(7,71),
	(8,81)
    ;

-- Povoamento da tabela legenda
INSERT INTO legendas
	(IDLegenda, legendas)
    VALUES
    (1,'ingles'),
    (2,'portugues')
    -- (3,'espanhol')
    ;
    

-- Povamento da tabela "temporada"
INSERT INTO temporada
	(IDTemporada, numero, ano, serie)
    VALUES
    (11,1,2013,1),
    (12,2,2014,1),
    (21,1,2011,2),
    (22,2,2012,2),
    (31,1,2015,3),
    (41,1,2017,4),
    (51,1,2019,5),
    (61,1,2018,6),
    (62,2,2019,6),
    (63,3,2020,6),
    (71,1,2019,7),
    (72,2,2020,7),
    (87,7,2020,8)
    ;


-- Povamento da tabela "episodio"
INSERT INTO episodio
	(IDEpisodio, nome, avaliacao, numero, duracao, legendas, temporada)
    VALUES
	(111,'Episódio #1.1', 8.2, 1, 50, 1,11),
	(112,'Episódio #1.2', 8.4, 2, 50, 1,11),
	(113,'Episódio #1.3', 8.3, 3, 50, 1,11),
	(114,'Episódio #1.4', 8.7, 4, 50, 1,11),
	(115,'Episódio #1.5', 9.0, 5, 50, 1,11),
	(116,'Episódio #1.6', 9.2, 6, 50, 1,11),
	(121,'Episódio #2.1', 8.6, 1, 50, 1,11),
	(122,'Episódio #2.2', 8.5, 2, 50, 1,11),
	(123,'Episódio #2.3', 8.7, 3, 50, 1,11),
	(124,'Episódio #2.4', 8.6, 4, 50, 1,11),
	(125,'Episódio #2.5', 8.8, 5, 50, 1,11),
	(126,'Episódio #2.6', 9.6, 6, 50, 1,11),
    (211,'Winter Is Coming', 9.1, 1, 55, 2,21),
    (212,'The Kingsroad', 8.8, 2, 55, 2,21),
    (213,'Lord Snow', 8.7, 3, 55, 2,21),
    (214,'Cripples, Bastards, and Broken Things', 8.8, 4, 55, 2,21),
    (215,'The Wolf and the Lion', 9.1, 5, 55, 2,21),
    (216,'A Golden Crown', 9.2, 6, 55, 2,21),
    (217,'You Win or You Die', 9.2, 7, 55, 2,21),
    (218,'The Pointy End', 9.0, 8, 55, 2,21),
    (219,'Baelor', 9.6, 9, 55, 2,21),
    (2110,'Fire and Blood', 9.5, 10, 55, 2,21),
    (221,'The North Remembers', 8.8, 1, 55, 2,22),
    (222,'The Night Lands', 8.5, 2, 55, 2,22),
    (223,'What Is Dead May Never Die', 8.8, 3, 55, 2,22),
    (224,'Garden of Bones', 8.8, 4, 55, 2,22),
    (225,'The Ghost of Harrenhal', 8.8, 5, 55, 2,22),
    (226,'The Old Gods and the New', 9.1, 6, 55, 2,22),
    (227,'A Man Without Honor', 8.9, 7, 55, 2,22),
    (228,'The Prince of Winterfell', 8.8, 8, 55, 2,22),
    (229,'Blackwater', 9.7, 9, 55, 2,22),
    (2210,'Valar Morghulis', 9.4, 10, 55, 2,22),
    (311,'Unauthorized Magic', 8.0, 1, 48, 2,31),
    (312,'The Source of Magic', 7.6, 2, 48, 2,31),
    (313,'Consequences of Advanced Spellcasting', 7.7, 3, 48, 2,31),
    (314,'The World in the Walls', 8.3, 4, 48, 2,31),
    (315,'Mendings, Major and Minor', 7.9, 5, 48, 2,31),
    (316,'Impractical Applications', 8.2, 6, 48, 2,31),
    (317,'The Mayakovsky Circumstance', 8.1, 7, 48, 2,31),
    (318,'The Strangled Heart', 8.1, 8, 48, 2,31),
    (319,'The Writing Room', 8.6, 9, 48, 2,31),
    (3110,'Homecoming', 8.1, 10, 48, 2,31),
    (3111,'Remedial Battle Magic', 8.0, 11, 48, 2,31),
    (3112,'Thirty-Nine Graves', 8.2, 12, 48, 2,31),
    (3113,'Have You Brought Me Little Cakes', 8.6, 13, 48, 2,31),
    (411,'Efectuar lo acordado', 8.3, 1, 45, 2,41),
    (412,'Imprudencias letales', 8.4, 2, 45, 2,41),
    (413,'Errar al disparar', 8.2, 3, 45, 2,41),
    (414,'Caballo de Troya', 8.3, 4, 45, 2,41),
    (415,'El día de la marmota', 8.4, 5, 45, 2,41),
    (416,'La cálida Guerra Fría', 8.3, 6, 45, 2,41),
    (417,'Refrigerada inestabilidad', 8.4, 7, 45, 2,41),
    (418,'Tú lo has buscado', 8.2, 8, 45, 2,41),
    (419,'El que la sigue la consigue', 8.7, 9, 45, 2,41),
    (511,'Pilot', 8.8, 1, 40, 2,51),
    (512,'Lucifer, Stay. Good Devil.', 8.2, 2, 40, 2,51),
    (513,'The Would-Be Prince of Darkness', 8.2, 3, 40, 2,51),
    (514,'Manly Whatnots', 8.6, 4, 40, 2,51),
    (515,'Sweet Kicks', 8.1, 5, 40, 2,51),
    (516,'Favorite Son', 8.8, 6, 40, 2,51),
    (517,'Wingman', 8.7, 7, 40, 2,51),
    (518,'Et Tu, Doctor?', 8.3, 8, 40, 2,51),
    (519,'A Priest Walks Into a Bar', 9.1, 9, 40, 2,51),
    (5110,'Pops', 8.4, 10, 40, 2,51),
    (5111,'St. Lucifer', 8.8, 11, 40, 2,51),
    (5112,'#TeamLucifer', 9.1, 12, 40, 2,51),
    (5113,'Take Me Back to Hell', 9.2, 13, 40, 2,51),
    (611,'Superpoteri', 6.9, 1, 30, 2,61),
    (612,'Burattino', 7.1, 2, 30, 2,61),
    (613,'#Friendzone', 7.2, 3, 30, 2,61),
    (614,'Emma', 7.2, 4, 30, 2,61),
    (615,'L ultimo scatto', 7.2, 5, 30, 2,61),
    (616,'#Love', 7.3, 6, 30, 2,61),
    (621,'#justagame', 7.6, 1, 30, 2,62),
    (622,'Rilancio', 7.9, 2, 30, 2,62),
    (623,'Fantasmi', 7.9, 3, 30, 2,62),
    (625,'Vicolo cieco', 7.9, 5, 30, 2,62),
    (626,'Baby', 8.1, 6, 30, 2,62),
    (631,'San Valentino', 7.6, 1, 30, 2,63),
    (632,'26 aprile 1915', 7.7, 2, 30, 2,63),
    (633,'Esprimi un desiderio', 8.0, 3, 30, 2,63),
    (634,'Niente più segreti', 7.6, 4, 30, 2,63),
    (635,'100 giorni', 7.7, 5, 30, 2,63),
    (636,'Oltre l acquario', 8.0, 6, 30, 2,63),
    (711,'The Name of the Game', 8.8, 1, 54, 2,71),
    (712,'Cherry', 8.6, 2, 54, 2,71),
    (713,'Get Some', 8.5, 3, 54, 2,71),
    (714,'The Female of the Species', 8.8, 4, 54, 2,71),
    (715,'Good for the Soul', 8.5, 5, 54, 2,71),
    (716,'The Innocents', 8.3, 6, 54, 2,71),
    (717,'The Self-Preservation Society', 8.9, 7, 54, 2,71),
    (718,'You Found Me', 9.1, 8, 54, 2,71),
    (721,'The Big Ride', 8.3, 1, 54, 2,72),
    (722,'Proper Preparation and Planning', 7.9, 2, 54, 2,72),
    (723,'Over the Hill with the Swords of a Thousand Men', 9.1, 3, 54, 2,72),
    (724,'Nothing Like It in the World', 8.1, 4, 54, 2,72),
    (725,'We Gotta Go Now', 8.5, 5, 54, 2,72),
    (726,'The Bloody Doors Off', 9.1, 6, 54, 2,72),
    (727,'Butcher, Baker, Candlestick Maker', 9.1, 7, 54, 2,72),
    (728,'What I Know', 9.5, 7, 54, 2,72),
    (871,'Manhunter', 8.2, 1, 32, 2,87),
    (872,'Captain Kim', 8.4, 2, 32, 2,87),
    (873,'Pimemento', 8.6, 3, 32, 2,87),
    (874,'The Jimmy Jab Games II', 8.0, 4, 32, 2,87),
    (875,'Debbie', 7.4, 5, 32, 2,87),
    (876,'Trying', 8.0, 6, 32, 2,87),
    (877,'Ding Dong', 8.8, 7, 32, 2,87),
    (878,'The Takeback', 8.3, 8, 32, 2,87),
    (879,'Dillman', 8.8, 9, 32, 2,87),
    (8710,'Admiral Peralta', 8.0, 10, 32, 2,87),
    (8711,'Valloweaster', 8.4, 11, 32, 2,87),
    (8712,'Ransom', 8.9, 12, 32, 2,87),
    (8713,'Lights Out', 9.3, 13, 32, 2,87)
    ;
    

    

