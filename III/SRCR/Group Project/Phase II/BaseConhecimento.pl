%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Sistema de Representacao de conhecimento e raciocinio
% Trabalho Prático - 1ª fase

%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% BASE DE CONHECIMENTO
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% utente: #Idutente, Nº Segurança_Social, Nome, Data_Nasc, Email, Telefone, Morada, Profissão, [Doenças_Crónicas], #CentroSaúde -> {V,F}

% Conhecimento perfeito
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

perfeito(utente(1)).
perfeito(utente(2)).
perfeito(utente(3)).
perfeito(utente(4)).
perfeito(utente(5)).
perfeito(utente(6)).
perfeito(utente(7)).
perfeito(utente(8)).
perfeito(utente(9)).
perfeito(utente(10)).
perfeito(utente(11)).
perfeito(utente(12)).
perfeito(utente(13)).
perfeito(utente(14)).
perfeito(utente(15)).
perfeito(utente(16)).
perfeito(utente(17)).

%--- Conhecimento perfeito negativo ---
-utente(18,1234,'Caio Barbosa Santos',data(1943,11,8), 'CaioBarbosaSantos@armyspy.com',271976473, 'R Pombal 77 6320-382 SABUGAL', empresaria,[Malaria], 1).

perfeito(utente(18)).

%--- Conhecimento impreciso ---
%numero impreciso 
excecao(utente(19,1234,'Gabriela Costa Pinto',data(1991,2,5), 'PretaDoCabeloCacheado@armyspy.com',220154873, 'R Âncora 74 4400-474 VILA NOVA DE GAIA', 'assistente de bordo',[gravidez], 2)).
excecao(utente(19,1234,'Gabriela Costa Pinto',data(1991,2,5), 'PretaDoCabeloCacheado@armyspy.com',220154873, 'R Âncora 74 4400-474 PORTO', 'assistente de bordo',[gravidez], 2)).
impreciso(utente(19)).

%--- Conhecimento incerto ---
utente(21,1234,'Sandra Barros Sousa',data(1973,7,25), 'SandraDoCabeloFrisado@teleworm.us',212451162, morada_Incerta, 'professora universitária',[], 1).
excecao(utente(Id,SS,Nome,Data,Email,Telefone,_,Profissao,Doenca,Centro)) :-
	utente(Id,SS,Nome,Data,Email,Telefone,morada_Incerta,Profissao,Doenca,Centro).
incerto(utente(21)).

%--- Conhecimento interdito ---
utente(20,1234,'Vitor Dias Pinto',data(1996,1,10), email_Interdito,244512346, 'R Goa 67 2420-204 CRASTO', 'paraquedista',[], 1).
excecao(utente(ID,SS,Nome,Data,_,Telefone,Morada,Profissao,Doenca,Centro)) :- 
	utente(ID,SS,Nome,Data,email_Interdito,Telefone,Morada,Profissao,Doenca,Centro).
nulo(email_Interdito).
interdito(utente(20)).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% centro_saude: #Idcentro, Nome, Morada, Telefone, Email -> {V,F}

%--- Conhecimento perfeito ---
centro_saude(1,centroSaude1, 'Largo Prazeres 41 6150-114 CASAL DA RIBEIRA', 251423567, 'centro1Saude@gmail.com').
centro_saude(2,centroSaude2, 'R Casal Borga 103 2150-065 QUINTA DA BROA', 250763183, 'centro2Saude@gmail.com').
perfeito(centro_saude(1)).
perfeito(centro_saude(2)).

%--- Conhecimento perfeito negativo ---
-centro_saude(4,centroSaude4, 'Avenida República 3 4740-305 ESPOSENDE', 273129445, 'centro4Saude@gmail.com').
perfeito(centro_saude(4)).

%--- Conhecimento impreciso ---
excecao(centro_saude(5,centroSaude5, 'R Nossa Senhora Fátima 89 3330-107 RELVA DA MÓ', 273452452, 'centro@gmail.com')).
excecao(centro_saude(5,centroSaude5, 'R Nossa Senhora Fátima 89 3330-107 RELVA DA MÓ', 273452452, 'centro5Saude@gmail.com')).
impreciso(centro_saude(5)).

%--- Conhecimento incerto ---
centro_saude(6,centro_Incerto, 'R Casal Borga 50 2200-097 ABRANTES', 273499959, 'centro6Saude@gmail.com').
excecao(centro_saude(ID,_,Morada,Telefone,Email)) :- centro_saude(ID,centro_Incerto,Morada,Telefone,Email).
incerto(centro_saude(6)).

%--- Conhecimento interdito ---
centro_saude(3,centroSaude3, 'Quinta Beloura 21 2714-521 PORTELA', 32425256, email_Interdito).
excecao(centro_saude(ID,Nome,Morada,Telefone,_)) :- centro_saude(ID,Nome,Morada,Telefone,email_Interdito).
nulo(email_Interdito).
interdito(centro_saude(3)).

%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
% staff: #Idstaff, #Idcentro, Nome, email -> {V,F}

%--- Conhecimento perfeito ---
staff(1,1, 'Roberto Miguel','robMig@gmail.com').
staff(2,1,'Ana Tomas','anaTom@gmail.com').
staff(3,2, 'Patricia Filipa','pf@gmail.com').
staff(4,2,'Joana Sousa','js@gmail.com').

perfeito(staff(1)).
perfeito(staff(2)).
perfeito(staff(3)).
perfeito(staff(4)).

%--- Conhecimento perfeito negativo ---
-staff(6,3, 'André Santos Cavalcanti','andCaval@gmail.com').
perfeito(staff(6)).

%--- Conhecimento impreciso --- email impreciso
excecao(staff(7,6, 'Luana Barros Oliveira','LuluBarros@gmail.com')).
excecao(staff(7,6, 'Luana Barros Oliveira','luanaBarrosOliveira@gmail.com')).
impreciso(staff(7)).

%--- Conhecimento incerto --- Nao se sabe o nome do staff
staff(20,2,nome_Incerto,'tania@gmail.com').
excecao(staff(Ids,Idc,_,E)) :- staff(Ids,Idc,nome_Incerto,E).
incerto(staff(20)).

%--- Conhecimento interdito --- o email é interdito - não podemos aceder o email
staff(8,5, 'Kauê Araujo Santos',email_Interdito).
excecao(staff(Ids,Idc,N,_)) :- staff(Ids,Idc,N,email_Interdito).
nulo(email_Interdito).
interdito(staff(8)).
 
%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
% vacinação_Covid: #Staf, #utente, Data, Vacina, Toma -> {V,F}

%--- Conhecimento perfeito ---

vacinacao_Covid(1,8,data(2021,3,13),pfizer,1).
vacinacao_Covid(2,13,data(2021,3,13),pfizer,1).
vacinacao_Covid(1,13,data(2021,4,10),pfizer,2).
vacinacao_Covid(1,3,data(2021,2,19),astrazeneca,1).
vacinacao_Covid(4,16,data(2021,1,22),pfizer,1).
vacinacao_Covid(3,7,data(2021,1,20),pfizer,1).
vacinacao_Covid(2,3,data(2021,2,20),astrazeneca,2).
vacinacao_Covid(2,14,data(2021,1,14),pfizer,1).
vacinacao_Covid(1,15,data(2021,1,10),astrazeneca,1).

%--- Conhecimento perfeito negativo ---
-vacinacao_Covid(1,2,data(2021,4,10),pfizer,1).
perfeito(vacinacao_Covid(2,1)).

% id e toma
perfeito(vacinacao_Covid(8,1)).
perfeito(vacinacao_Covid(13,2)).
perfeito(vacinacao_Covid(3,1)).
perfeito(vacinacao_Covid(16,1)).
perfeito(vacinacao_Covid(7,1)).
perfeito(vacinacao_Covid(3,1)).
perfeito(vacinacao_Covid(14,1)).
perfeito(vacinacao_Covid(15,1)).

%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
% fase: #fase, dataInicio, dataFim, lista profissoes, lista doenças, idadeDoenca, idade -> {V,F}

%--- Conhecimento perfeito ---

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

%--- Conhecimento perfeito ---
cancela_vacina(15,data(2021,1,9)).
cancela_vacina(1,data(2021,2,10)).
perfeito(cancela_vacina(15)).
perfeito(cancela_vacina(1)).


%--- Conhecimento perfeito negativo ---
-cancela_vacina(19,data(2021,1,30)).
perfeito(cancela_vacina(19)).
