-- Esquema: "Armazem"
USE `armazem` ;

--
-- Permissão para fazer operações de remoção de dados.
SET SQL_SAFE_UPDATES = 0;

--
-- Povamento da tabela "corredor"
INSERT INTO Corredor
	(codCorredor, zona, ocupacao, capacidadeTotal)
    VALUES
    (0,'rececao', 0, 10),
    (1,'armazenamento', 3, 5),
    (2,'armazenamento', 2, 5);
    
INSERT INTO Robot
	(codRobot, ocupado, codQRPalete, percurso)
    VALUES
    ('A', false, null, null),  
    ('B', false, null, null),
    ('C', false, null, null);

INSERT INTO QRCode
	(codQRCode, perecivel, descricao)
    VALUES
    ('A', true, 'douradinho da Iglo'),
    ('B', true, 'iogurte grego da Oikos'),
    ('C', false, 'massa fusili da Milaneza'),
    ('D', false, 'arroz agulha da Cigala');
    
INSERT INTO Localizacao
	(prateleira, zona)
    VALUES
    (0,'rececao'),
    (1,'armazenamento'),
    (2,'armazenamento'),
    (3,'armazenamento'),
    (4,'armazenamento'),
    (5,'armazenamento'),
    (6,'armazenamento'),
    (7,'armazenamento'),
    (8,'armazenamento'),
    (9,'armazenamento'),
    (10,'armazenamento'),
    (11,'robot'),
    (12,'entrega');
    
INSERT INTO Palete
	(codPalete, estado, codQRCode,localizacao)
    VALUES
    ('B1','armazenamento', 'B', 1),
    ('B2','armazenamento', 'B', 2),
    ('C1','armazenamento', 'C', 3),
    ('D1','armazenamento', 'D', 6),
    ('A1','armazenamento', 'A', 7);

    