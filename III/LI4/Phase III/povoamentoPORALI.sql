-- Esquema: "seriescompanhia"
USE `porali` ;
--
-- Permissão para fazer operações de remoção de dados.
SET SQL_SAFE_UPDATES = 0;

INSERT INTO passageiro
	(Email, Password, Avaliacao, Codigo, Nome)
	VALUES 
		('jcc@acacia.pt', '1234',5,'x123','João da Costa e Campos'),
        ('amaro@acacia.pt', '999',5,'24242','Sebastião Pinheiro')
	;
    
INSERT INTO notificacao
	(Nome, IDNotificacao)
	VALUES 
		('Proxima Paragem',1),
        ('Fim de Viagem',2),
        ('Inicio de Viagem',3)
	;
    
INSERT INTO ativa
	(UserEmail, NotificacaoID, Ativada)
	VALUES 
		('jcc@acacia.pt',1,1),
        ('jcc@acacia.pt',2,0),
        ('jcc@acacia.pt',3,1)
	;
    

INSERT INTO viagem
	(IDViagem, Hora_inicio,Hora_Fim,Local_chegada,Local_partida,Preco)
	VALUES 
		(3242,'2021-02-23 18:20:00','2021-02-23 18:35:00','Universidade Minho','Estação',1.50)
	;
    
INSERT INTO efetua
	(UserEmail, ViagemID)
	VALUES 
		('jcc@acacia.pt',3242)
	;
    
    
INSERT INTO viagem_gratis
	(Codigo, Redimida,Validade,UserEmail)
	VALUES 
		(1222,0,'2021-09-11','jcc@acacia.pt')
	;
    
