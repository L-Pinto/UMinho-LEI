-- Query 1) Identificar qual o top N das série mais vista.
DELIMITER $$
CREATE PROCEDURE topNSeriesMaisVistas (IN topN INT)
BEGIN
SELECT S.nome, count(A.utilizador) AS nrUtilizadores
	FROM assiste AS A 
		JOIN serie AS S ON S.IDSerie = A.serie
		GROUP BY A.serie 
			ORDER BY nrUtilizadores DESC
			LIMIT topN;
END $$    
-- CALL topNSeriesMaisVistas (10);

-- Query 2) Identificar qual o top N de series com melhor classificação.          
DELIMITER $$
CREATE PROCEDURE topNSerieMelhorClassificada(IN topN INT)
BEGIN  
SELECT nome, avaliacao
	FROM serie
		ORDER BY avaliacao DESC
			LIMIT topN;
END $$

-- Query 3) O sistema deverá ser capaz de identificar quais as companhias que forneceram mais séries.
DELIMITER $$
CREATE PROCEDURE MaioresCompanhias()
BEGIN  
SELECT C.nome, count(S.companhia) AS nrSeries
	FROM serie AS S 
		JOIN companhia AS C ON C.IDCompanhia = S.companhia
			 GROUP BY S.companhia 
			 ORDER BY nrSeries DESC ;
END $$


-- Query 4) O sistema deverá ser capaz de apresentar todas as series e o seu numero de premios.
DELIMITER $$
CREATE PROCEDURE TopSeriesPremios()
BEGIN  
SELECT S.nome, S.premio 
	FROM serie AS S
		ORDER BY S.premio DESC;
END $$

            
-- Query 5) O sistema deverá ser capaz de identificar qual é top N dos utilizadores que vê mais séries.
DELIMITER $$
CREATE PROCEDURE NrSeriesPorUtilizador(IN topN INT)
BEGIN  
SELECT U.nome , count(A.serie) AS nrSeries
 FROM assiste AS A 
	JOIN utilizador AS U ON U.IDUtilizador = A.utilizador
		GROUP BY A.utilizador
			ORDER BY nrSeries DESC
				LIMIT topN;
END $$


-- Query 6) O sistema deverá ser capaz de identificar as séries de um determinado idioma.
DELIMITER $$
CREATE PROCEDURE ProcuraSeriesNacionalidade(IN idioma VARCHAR(65))
BEGIN  
SELECT S.idioma, S.nome
	FROM serie AS S
		WHERE S.idioma = idioma; -- exemplo: ingles, italiano, espanhol
END $$


-- Query 7) O sistema deverá ser capaz de identificar séries cujos episódios possuem legendas de um determinado idioma. 
DELIMITER $$
CREATE PROCEDURE ProcuraSeriesTraduzidas(IN legenda VARCHAR(65))
BEGIN  
SELECT S.nome, COUNT(serie) AS Episodios_Legendados, L.legendas
	FROM serie AS S
		JOIN temporada AS T ON T.serie = S.IDSerie
        JOIN episodio AS E ON T.IDTemporada = E.temporada
        JOIN legendas AS L ON L.IDLegenda = E.legendas
			WHERE L.legendas = legenda  -- exemplo: ingles, portugues, espanhol
				GROUP BY S.nome;
END $$


-- Query 8) O sistema deverá ser capaz de identificar quais as séries apropriadas para uma determinada faixa etária.
DELIMITER $$
CREATE PROCEDURE ConsultaSeriesIdade(IN idade INT)
BEGIN  
SELECT S.nome , S.classificacaoEtaria
	FROM serie AS S
		WHERE S.classificacaoEtaria <= idade; -- exemplo: 12, 14, 16, 18
END $$


-- Query 9) O sistema deverá ser capaz de identificar quais as séries favoritas de um utilizador. 
DELIMITER $$
CREATE PROCEDURE SeriesFavoritasUtilizador(IN idUtilizador INT)
BEGIN  
SELECT U.nome, S.nome AS SeriesFavoritas
	FROM assiste AS A
			JOIN serie AS S ON A.serie = S.IDSerie
            JOIN utilizador AS U ON A.utilizador = U.IDUtilizador
            WHERE A.favorita = 1 
					&& A.utilizador = idUtilizador; -- exemplo: 1,..,10
END $$


-- Query 10) O sistema deverá ser capaz de identificar qual o numero de utilizadores com conta ativa.
DELIMITER $$
CREATE PROCEDURE UtilizadoresAtivos()
BEGIN 
SELECT U.nome, S.DataInicio, S.DataFim
	FROM subscreve AS S
    JOIN utilizador AS U ON U.IDUtilizador = S.utilizador
		WHERE S.DataFim >= current_date();
END $$


-- Query 11) O sistema deverá ser capaz de identificar qual a faturação total.
DELIMITER $$
CREATE PROCEDURE FaturacaoTotal()
BEGIN 
SELECT SUM(S.preco) AS Faturação
	FROM subscreve AS S
		GROUP BY S.servico;
END $$

/*
SELECT S.nome, COUNT(*)
FROM serie as S, possui as P
	WHERE S.IDSerie = P.serie 
	GROUP BY S.nome
    ORDER BY COUNT(P.ator) DESC
    LIMIT 10;
    
SELECT S.nome, COUNT(*)
FROM serie as S, temporada as T
WHERE S.IDSerie = T.serie
GROUP BY S.IDSerie

SELECT COUNT(*)
FROM serie as S

SELECT U.nome
FROM utilizador as U
*/
