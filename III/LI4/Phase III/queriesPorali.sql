USE `porali`;

SELECT Password FROM passageiro
	WHERE email = 'jcc@acacia.pt';
    
INSERT INTO passageiro (Email, Password, Avaliacao, Codigo_Utilizador, Nome) VALUES ('ana@gamail','1234', 0, '56464', 'ana');

SELECT * FROM passageiro;


DELETE FROM passageiro WHERE Email='outro@email.com'
;

SELECT N.IDNotificacao, N.Nome, A.ativada 
    FROM ativa AS A
            JOIN passageiro AS P ON A.UserEmail = P.Email
            JOIN notificacao AS N ON A.NotificacaoID = N.IDNotificacao
            WHERE email = 'jcc@acacia.pt';
            
SELECT V.IDViagem, V.Hora_inicio, V.Hora_Fim, V.Local_chegada, V.Local_partida, V.Preco
    FROM efetua AS E
            JOIN passageiro AS P ON E.UserEmail = P.Email
            JOIN viagem AS V ON E.ViagemID = V.IDViagem
            WHERE email = 'jcc@acacia.pt';
            
SELECT V.Codigo, V.Redimida, V.Validade
    FROM viagem_gratis AS V
            JOIN passageiro AS P ON V.UserEmail = P.Email
            WHERE email = 'jcc@acacia.pt';
            
SELECT * FROM viagem_gratis;
            
UPDATE viagem_gratis set Redimida=1 WHERE UserEmail='jcc@acacia.pt';

UPDATE ativa set Ativada=@Ativada WHERE Codigo=@Codigo AND UserEmail=@UserEmail;


-- SELECT * FROM passageiro WHERE Codigo_Utilizador = 1223;