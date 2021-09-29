%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Sistema de Representacao de conhecimento e raciocinio
% Trabalho Prático - 1ª fase

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Definicoes iniciais

:- op( 900,xfy,'::' ). %- criação de um novo operador para o prolog
:- dynamic utente/10.
:- dynamic staff/4.
:- dynamic centro_saude/5.
:- dynamic vacinacao_Covid/5.
:- dynamic cancela_vacina/2.


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% INVARIANTES

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Estrutural para o perdicado utente que permite identificar os tipos das variaveis

+utente(Id,SS,Nome,Data,Email,Telefone,Morada,Profissao,Doenca,Centro) ::( 
			integer(Id),
			integer(SS),
			atom(Nome),
			validaData(Data),
			atom(Email),
			atom(Profissao),
			validaLista(Doenca),
			integer(Centro)).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Estrutural para o predicado utente que permite limitar a insercao de novos conhecimentos

+utente(Id,_,_,_,_,_,_,_,_,_) :: (solucoes(Id,utente(Id,_,_,_,_,_,_,_,_,_),S), comprimento( S,N ), N == 1).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Estrutural para o predicado centro_saude que permite identificar os tipos das variaveis

+centro_saude(ID,Nome,Morada,Telefone,Email) :: (
    integer(ID),
    atom(Nome),
    atom(Morada),
    integer(Telefone),
    atom(Email)).
	
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Estrutural para o predicado centro_saude que permite limitar a insercao de novos conhecimentos

+centro_saude(Id,_,_,_,_) :: (solucoes(Id,centro_saude(Id,_,_,_,_),S), comprimento( S,N ), N == 1 ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Estrutural para o predicado staff que permite identificar os tipos das variaveis

+staff(Idstaff, Idcentro, Nome, Email) :: (
    integer(Idstaff),
    integer(Idcentro),
    atom(Nome),
    atom(Email)).
	
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Estrutural para o predicado staff que permite limitar a insercao de novos conhecimentos

+staff(Id,_,_,_) :: (solucoes(Id,staff(Id,_,_,_),S), comprimento( S,N ), N == 1 ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Estrutural para o vacinacao_Covid staff que permite identificar os tipos das variaveis

+vacinacao_Covid(IDStaff,IDUtente,Data,Vacina,Toma) :: (
    integer(IDStaff),
    integer(IDUtente),
    validaData(Data),
    atom(Vacina),
    integer(Toma)).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% % Invariante Estrutural para o predicado vacinacao_Covid que permite limitar a insercao de novos conhecimentos

+vacinacao_Covid(Staff,Id,_,Vacina,1) :: (solucoes(Id,utente(Id,_,_,_,_,_,_,_,_,_),S), comprimento( S,N ), N == 1, %ID tem de ser de um utente
											solucoes(Id,vacinacao_Covid(_,Id,_,_,1),S), comprimento( S,N ), N == 1, %Não ja ter tomado a primeira toma
											verificaCentro(Id,Staff)). % tem de ser vacinado no centroSaude respetivo
											
+vacinacao_Covid(Staff,Id,_,Vacina,2) :: (solucoes(Id,utente(Id,_,_,_,_,_,_,_,_,_),S), comprimento( S,N ), N == 1, 
											solucoes(Id,vacinacao_Covid(_,Id,_,Vacina,1),S1), comprimento( S1,N1 ), N1 == 1,
											solucoes(Id,vacinacao_Covid(_,Id,_,Vacina,2),S2), comprimento( S2,N2 ), N2 == 1,
											verificaCentro(Id,Staff)).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Estrutural para o predicado cancela_vacina que permite identificar os tipos das variaveis

+cancela_vacina(Id,Data) :: (integer(Id),
							validaData(Data)).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Estrutural para o predicado cancela_vacina que permite limitar a insercao de novos conhecimentos

+cancela_vacina(Id,Data) :: (solucoes(Id,utente(Id,_,_,_,_,_,_,_,_,_),S), comprimento( S,N ), N == 1, 
						solucoes(Id,cancela_vacina(Id,_),S0), comprimento( S0,N0 ), N0 == 1,
						solucoes(Id,vacinacao_Covid(_,Id,_,Vacina,1),S1), comprimento( S1,N1 ), N1 == 0,
						solucoes(Id,vacinacao_Covid(_,Id,_,Vacina,2),S2), comprimento( S2,N2 ), N2 == 0).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Referencial para o predicado cancela_vacina que permite identificar os tipos das variaveis - na remocao

-cancela_vacina(Id,Data) :: (integer(Id),
							validaData(Data)).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Invariante Referencial para o predicado cancela_vacina que permite limitar a remocao de novos conhecimentos

-cancela_vacina(Id,Data) :: (solucoes(Id,utente(Id,_,_,_,_,_,_,_,_,_),S), comprimento( S,N ), N == 1,
						solucoes(Id,cancela_vacina(Id,Data),S1), comprimento( S1,N1 ), N1 == 0).
						