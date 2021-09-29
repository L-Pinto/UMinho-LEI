%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Sistema de Representacao de conhecimento e raciocinio
% Trabalho Prático - 1ª fase

%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% BASE DE CONHECIMENTO
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% utente: #Idutente, Nº Segurança_Social, Nome, Data_Nasc, Email, Telefone, Morada, Profissão, [Doenças_Crónicas], #CentroSaúde -> {V,F}

utente(1,1234,'alice',data(1950,2,12), 'alice@gmail.com',935852315, 'Praceta Conde Arnoso 22 2635-240 FRANCOS', padeiro,[asma,bronquite], 1). %fase2 
utente(2,1234,'paulo',data(2000,5,12), 'paulo@gmail.com',934209315, 'Rua Portas Água 2 6120-772 MATADOURO', carteiro,[rinite], 1). %fase3
utente(3,1234,'josefa',data(1926,3,20), 'josefa@gmail.com',930052314, 'Largo Prazeres 34 6100-689 QUINTA DO ROSÁRIO', peixeiro,[diabetes], 1).
utente(4,1234,'tiago',data(1993,3,22), 'tiagoaf@gamil.com',965850111, 'R Capela 118 2410-433 LEIRIA', informatico,[], 1). %fase3
utente(5,1234,'juliana',data(1965,5,23), 'juliana@gamil.com',960350178, 'Estrada Logo Deus 110 3720-568 IGREJA', medico,[], 1).
utente(6,1234,'alice almeida rodrigues',data(2000,8,19), 'alicearod@gamil.com',936950825, 'R Portela 94 3600-202 CASTRO DAIRE', militar,[], 1). %fase1
utente(7,1234,'miguel carvalho',data(1994,9,4), 'miguelcarvalho@gamil.com',935621465, 'R Alto Eira 110 2745-758 QUELUZ', policia,[], 1). %fase1
utente(8,1234,'diana batista',data(2003,3,27), 'dianabati@gamil.com',965836215, 'R Nossa Senhora Fátima 61 3400-444 GALIZES', estudante,[osteoartrite], 1). 
utente(9,1234,'pedro albino',data(1961,1,3), 'albinopedro@gamil.com',925856215, 'R Tomé B Queirós 118 4525-221 CANEDO', carpinteiro,[obesidade, 'doenca coronaria'], 1). %fase1
utente(10,1234,'albertina maria',data(1959,8,24), 'mariaalbertina@gamil.com',925856565, 'Quinta Beloura 21 2714-521 PORTELA', costureira,[diabetes], 1). %fase2
utente(11,1234,'rita abreu',data(1954,2,10), 'ritaabreu@gamil.com',965256565, 'R Cimo Povo 47 4615-355 BORBA DE GODIM', farmaceutica,[], 1). %fase2
utente(12,1234,'alberto andrade',data(1929,9,30), 'andradealberto@gamil.com',965216165, 'Avenida Almirante Reis 81 2580-467 CARREGADO', reformado,[], 1). %fase1
utente(13,1234,'aline correia rodrigues',data(1969,5,19), 'jalberto@gamil.com',216950825, 'R Pescador Bacalhoeiro 78 4495-441 PÓVOA DE VARZIM', informatico,[dpoc], 1). %fase1
utente(14,1234,'Roberto Miguel',data(1974,5,19), 'robMig@gmail.com',216950825, 'R São Vicente 75 2070-580 VALE DA PINTA', staff,[], 1).
utente(15,1234,'Ana Tomas',data(1972,6,19), 'anaTom@gmail.com',216950825, 'R Luís Camões 18 5210-263 PARADELA', staff,[], 1).
utente(16,1234,'Patricia Filipa',data(1969,5,30), 'pf@gmail.com',216950825, 'R São Bento 52 4765-714 OLIVEIRA SÃO MATEUS', staff,[], 2).
utente(17,1234,'Joana Sousa',data(1968,2,22), 'js@gmail.com',216950825, 'Rua Longuinha 65 2620-418 RAMADA', staff,[], 2).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% centro_saude: #Idcentro, Nome, Morada, Telefone, Email -> {V,F}

centro_saude(1,centroSaude1, 'Largo Prazeres 41 6150-114 CASAL DA RIBEIRA', 251423567, 'centro1Saude@gmail.com').
centro_saude(2,centroSaude2, 'R Casal Borga 103 2150-065 QUINTA DA BROA', 250763183, 'centro2Saude@gmail.com').

%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
% staff: #Idstaff, #Idcentro, Nome, email -> {V,F}

staff(1,1, 'Roberto Miguel','robMig@gmail.com').
staff(2,1,'Ana Tomas','anaTom@gmail.com').
staff(3,2, 'Patricia Filipa','pf@gmail.com').
staff(4,2,'Joana Sousa','js@gmail.com').

%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
% vacinação_Covid: #Staf, #utente, Data, Vacina, Toma -> {V,F}

vacinacao_Covid(1,5,data(2021,2,10),astrazeneca,1).
vacinacao_Covid(1,8,data(2021,3,13),pfizer,1).
vacinacao_Covid(1,13,data(2021,3,13),pfizer,1).
vacinacao_Covid(1,13,data(2021,4,10),pfizer,2).
vacinacao_Covid(1,3,data(2021,2,19),astrazeneca,1).
vacinacao_Covid(2,3,data(2021,2,20),astrazeneca,2).
vacinacao_Covid(2,14,data(2021,1,14),pfizer,1).
vacinacao_Covid(1,15,data(2021,1,10),pfizer,1).
vacinacao_Covid(4,16,data(2021,1,22),pfizer,1).
vacinacao_Covid(3,17,data(2021,1,20),pfizer,1).

%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
% fase: #fase, dataInicio, dataFim, lista profissoes, lista doenças, idadeDoenca, idade -> {V,F}

fase(1,data(2020,12,11),data(2021,4,11), 
	[medico, enfermeiro, militar, 'auxiliar lar', 'auxiliar saude', policia, staff],
	['insuficiencia cardiaca', 'doenca coronaria', 'insuficiencia renal', dpoc],
	50,80).
	
fase(2,data(2021,4,12),data(2021,7,12), 
	[professor],
	[diabetes, neoplasia, 'doenca renal cronica', 'insuficiencia hepatica','hipertensao arterial',obesidade],
	50,65).
	
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% EXTRAS

%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
% cancela_vacina: #utente, DataCancelamento -> {V,F}

cancela_vacina(15,data(2021,1,9)).
cancela_vacina(1,data(2021,2,10)).
