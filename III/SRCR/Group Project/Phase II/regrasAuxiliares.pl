%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Sistema de Representacao de conhecimento e raciocinio
% Trabalho Prático - 1ª fase

%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%- REGRAS AUXILIARES

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Determina fase segundo data de vacinação: DataVacinacao, Fase -> {V,F}

obtemFase(Data,1) :- fase(1,Data0,Data1,_,_,_,_), 
					 dataAntes(Data0,Data), 
					 dataAntes(Data,Data1).
obtemFase(Data,2) :- fase(2,Data0,Data1,_,_,_,_), 
					 dataAntes(Data0,Data), 
					 dataAntes(Data,Data1).
obtemFase(Data,3) :- nao(obtemFase(Data,1)),
					 nao(obtemFase(Data,2)).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Verifica se uma data vem antes da outra: Data0, Data1 -> {V,F}

dataAntes(data(Y0,M0,D0),data(Y1,M1,D1)) :- (Y0-Y1 < 0 ; 
                                             Y0-Y1 =:= 0, M0-M1 < 0;
                                             Y0-Y1 =:= 0, M0-M1 =:= 0, D0-D1 =< 0).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Confirma a fase segundo os criterios de inclusao: Fase, #IdUtente, Profissao, Doença -> {V,F}

%confirma se o utente possui uma profissao prioritaria para a fase 1
confirmaFase(1,Id,Profissao,Doenca) :- fase(1,_,_,LP,LD,ID,I), 
									   member(Profissao,LP),!.

%confirma se o utente possui idade superior ao valor base estipulado para a fase 1
confirmaFase(1,Id,Profissao,Doenca) :- fase(1,_,_,LP,LD,ID,I), 
									   idade(Id,Idade), 
									   Idade>= I, !.

%confirma se possui alguma doença estipulada para a fase 1, bem como a idade minima associada a doença
confirmaFase(1,Id,Profissao,Doenca) :- fase(1,_,_,LP,LD,ID,I),
									   idade(Id,Idade), 
									   Idade>= ID, 
									   verificaElem(LD,Doenca).

%confirma se a profissao do utente pertence à lista de profissoes prioritarias para a fase 2
confirmaFase(2,Id,Profissao,Doenca) :- fase(2,_,_,LP,LD,ID,I), 
									   nao(confirmaFase(1,Id,Profissao,Doenca)), 
									   member(Profissao,LP),!.

%confirma se o utente possui idade superior à estipulada para a fase 2
confirmaFase(2,Id,Profissao,Doenca) :- fase(2,_,_,LP,LD,ID,I), 
									   nao(confirmaFase(1,Id,Profissao,Doenca)), 
									   idade(Id,Idade), 
									   Idade>= I,!.

%confirma se possui alguma doença estipulada para a fase 2, bem como a idade minima associada a doença
confirmaFase(2,Id,Profissao,Doenca) :- fase(2,_,_,LP,LD,ID,I), 
									   nao(confirmaFase(1,Id,Profissao,Doenca)),
									   idade(Id,Idade), 
									   Idade>= ID,
									   verificaElem(LD,Doenca).

%restantes utentes que nao foram abrangidos em fases anteriores
confirmaFase(3,Id,Profissao,Doenca) :- nao(confirmaFase(2,Id,Profissao,Doenca)), 
									   nao(confirmaFase(1,Id,Profissao,Doenca)).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Confirma se a 2 dose foi tomada devidamente: Data Primeira Toma, Data Segunda Toma -> {V,F}

confirmaToma(DataVacina1,DataVacina2) :- calculaDias(DataVacina1,Res1), 
										 calculaDias(DataVacina2,Res2), 
										 Res2-Res1 >= 26 , Res2-Res1 =< 31.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Confirma os dias de uma data: Data, Dias -> {V,F}

calculaDias(data(Y,M,D),Dias):- Dias is (((Y*1461)/4)+((M*153)/5)+D).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Determina a idade de um utente: #IdUtente, Idade -> {V,F}

idade(Id,I) :- utente(Id,_,_,D,_,_,_,_,_,_), 
			   dataAtual(Key, Value), 
			   obterIdade(D,Value,I).
				
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Obtém data atual: Key, Value -> {V,F}

dataAtual(Key, Value) :- get_time(Stamp),
						 stamp_date_time(Stamp, DateTime, local),
						 date_time_value(Key, DateTime, Value).
				
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Obtém a idade de um utente atraves de duas datas: DataUtente, DataAtual -> {V,F}

obterIdade(data(Y0,_,_),date(Y1,_,_), X) :- X is Y1-Y0.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado membro: Elemento, Lista -> {V,F}

membro(X,LD) :- member(X,LD).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Verifica se existe pelo menos um elemento de uma lista na outra: ListaUtente, ListaDoenças -> {V,F}

verificaElem([X|H],LD) :- member(X,LD).
verificaElem([X|H],LD) :- verificaElem(H,LD).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :-
    Questao, !, fail.
nao( Questao ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado solucoes: Elemento, Questao, Lista -> {V,F}

solucoes(F,P,S) :- findall(F,P,S).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado comprimento: Lista, Comprimento -> {V,F}

comprimento(L,N):- length(L,N).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Verifica se o centro do Utente é o mesmo do staff: Id, Staff -> {V,F}

verificaCentro(Id,Staff) :- utente(Id,_,_,_,_,_,_,_,_,Centro), staff(Staff,Centro,_,_).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Verifica se data é valida: Data -> {V,F}

validaData(data(Ano,Mes,Dia)):- data(Ano,Mes,Dia).

data(Ano,2,Dia) :- integer(Ano), integer(Dia), Dia >=1, Dia =< 29.
data(Ano,Mes,Dia) :- integer(Ano), integer(Mes), integer(Dia), Mes >= 1, Mes =< 12, Dia >=1, Dia =< 31.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Verifica se a variavel é uma lista: Lista -> {V,F}

validaLista([]).
validaLista([H|T]).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% % Auxiliares Evolução e Involução

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Insere um termo na base de conhecimento

insercao(Termo) :- assert(Termo).
insercao(Termo) :- retract(Termo), !, fail. %- impede o backtracking (!) e falha (fail)

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Remove um termo na base de conhecimento

remocao(Termo) :- retract(Termo).
remocao(Termo) :- assert(Termo), !, fail.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Testes ao invariante: Lista -> {V,F}
teste([]).
teste([R|LR]) :- R, teste(LR).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Insere o predicado perfeito na base de conhecimento

idInsertTermo(utente(Id,_,_,_,_,_,_,_,_,_)) :- insercao(perfeito(utente(Id))).
idInsertTermo(centro_saude(Id,_,_,_,_)) :- insercao(perfeito(centro_saude(Id))).
idInsertTermo(staff(Id,_,_,_)) :- insercao(perfeito(staff(Id))).
idInsertTermo(cancela_vacina(Id,_)) :- insercao(perfeito(cancela_vacina(Id))).
idInsertTermo(vacinacao_Covid(_,Id,_,_,Toma)) :- insercao(perfeito(vacinacao_Covid(Id,Toma))).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Remove o predicado perfeito na base de conhecimento

idRemoveTermo(utente(Id,_,_,_,_,_,_,_,_,_)) :- remocao(perfeito(utente(Id))).
idRemoveTermo(centro_saude(Id,_,_,_,_)) :- remocao(perfeito(centro_saude(Id))).
idRemoveTermo(staff(Id,_,_,_)) :- remocao(perfeito(staff(Id))).
idRemoveTermo(cancela_vacina(Id,_)) :- remocao(perfeito(cancela_vacina(Id))).
idRemoveTermo(vacinacao_Covid(_,Id,_,_,Toma)) :- remocao(perfeito(vacinacao_Covid(Id,Toma))).