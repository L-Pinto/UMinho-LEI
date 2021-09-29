%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Sistema de Representacao de conhecimento e raciocinio
% Trabalho Prático - 1ª fase

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Definicoes iniciais

:- op( 900,xfy,'::' ).  %- criação de um novo operador para o prolog

%conhecimento interdito
:- op(900,xfy,:~:).

%conhecimento impreciso
:- op(900,xfy,:-:).

:- dynamic utente/10.
:- dynamic staff/4.
:- dynamic centro_saude/5.
:- dynamic vacinacao_Covid/5.
:- dynamic cancela_vacina/2.
:- dynamic fase/7.
:- dynamic excecao/1.
:- dynamic nulo/1.
:- dynamic interdito/1.
:- dynamic incerto/1.
:- dynamic impreciso/1.
:- dynamic perfeito/1.
:- dynamic (-)/1.


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% INVARIANTES GLOBAIS

%--------------------------------- - - - - - - - - - -  -  -  -  -   -

%insercao de um termo positivo não pode ser negativo
+Termo :: nao(-Termo).

%insercao de um termo negativo não pode ser positivo
+(-Termo) :: nao(Termo).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% -------------------------- UTENTE --------------------------

% ------------ ADICAO DE CONHECIMENTO PERFEITO ---------------

% POSITIVO

% Invariante que permite adicionar conhecimento positivo
+utente(Id,SS,Nome,Data,Email,Telefone,Morada,Profissao,Doenca,Centro) ::( 
			integer(Id),
			integer(SS),
			atom(Nome),
			validaData(Data),
			atom(Email),
    		integer(Telefone),
			atom(Morada),
			atom(Profissao),
			validaLista(Doenca),
			integer(Centro)).

% Não permite adicionar conhecimento positivo repetido
+utente(Id,_,_,_,_,_,_,_,_,_) :: (solucoes(Id,utente(Id,_,_,_,_,_,_,_,_,_),S), 
								  comprimento( S,1 )).

% NEGATIVO

% Invariante que permite adicionar conhecimento negativo
+(-utente(Id,SS,Nome,Data,Email,Telefone,Morada,Profissao,Doenca,Centro)) :: ( 
			integer(Id),
			integer(SS),
			atom(Nome),
			validaData(Data),
			atom(Email),
   			integer(Telefone),
			atom(Morada),
			atom(Profissao),
			validaLista(Doenca),
			integer(Centro)).

% Não permite adicionar conhecimento negativo repetido
+(-utente(Id,_,_,_,_,_,_,_,_,_)) :: (solucoes(Id,-utente(Id,_,_,_,_,_,_,_,_,_),S), 
									 comprimento( S,N ), N == 2 ).

% ------------ REMOCAO DE CONHECIMENTO PERFEITO ---------------

% POSITIVO

% Invariante que permite remover conhecimento positivo
-utente(Id,SS,Nome,Data,Email,Telefone,Morada,Profissao,Doenca,Centro) ::( 
			integer(Id),
			integer(SS),
			atom(Nome),
			validaData(Data),
			atom(Email),
   			integer(Telefone),
			atom(Morada),
			atom(Profissao),
			validaLista(Doenca),
			integer(Centro)).

% Invariante que permite limitar a remocao de conhecimento positivo
-utente(Id,SS,Nome,Data,Email,Telefone,Morada,Profissao,Doenca,Centro) :: (
	solucoes(Id,(vacinacao_Covid(_,Id,_,_,_),cancela_vacina(Id,_)),S ),comprimento(S,N), N==0,
	solucoes(Id,utente(Id,SS,Nome,Data,Email,Telefone,Morada,Profissao,Doenca,Centro),S ),comprimento(S,N), N==0).
	
% NEGATIVO

% Invariante que permite remover conhecimento negativo
-(-utente(Id,SS,Nome,Data,Email,Telefone,Morada,Profissao,Doenca,Centro)) ::( 
			integer(Id),
			integer(SS),
			atom(Nome),
			validaData(Data),
			atom(Email),
    		integer(Telefone),
			atom(Morada),
			atom(Profissao),
			validaLista(Doenca),
			integer(Centro)).

% Invariante que permite limitar a remocao de conhecimento negativo
-(-utente(Id,SS,Nome,Data,Email,Telefone,Morada,Profissao,Doenca,Centro)) :: (
	solucoes(I,-utente(Id,SS,Nome,Data,Email,Telefone,Morada,Profissao,Doenca,Centro),S ),
	comprimento(S,N), N==1).

% -------------- CONHECIMENTO IMPERFEITO -----------------------

% Insercão de conhecimento imperciso
+utente(Id,_,_,_,_,_,_,_,_,_) :-: (
	nao(perfeito(utente(Id))), 
	nao(incerto(utente(Id))) 
).

% Insercão de conhecimento interdito ou incerto
+utente(Id,_,_,_,_,_,_,_,_,_) :~: (
	nao(perfeito(utente(Id))), 
	nao(impreciso(utente(Id))), 
	nao(incerto(utente(Id))) 
).

% A inserção ocorre caso não haja conhecimento impreciso repetido								
+utente(Id,SS,Nome,Data,Email,Telefone,Morada,Profissao,Doenca,Centro) :-: (
	solucoes((Id,M), excecao(utente(Id,_,_,_,_,_,Morada,_,_,_)), L),
	comprimento(L,1)).

% A inserção ocorre caso email não seja interdito
+utente(Id,_,_,_,_,_,_,_,_,_):~:(solucoes(Em,(utente(Id,_,_,_,Em,_,_,_,_,_),nulo(Em)),L),
                                 comprimento(L,N),
                                 N==0).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% -------------------- CENTRO SAÚDE -----------------------

% ---------- ADICAO DE CONHECIMENTO PERFEITO --------------

% POSITIVO

% Invariante que permite adicionar conhecimento positivo
+centro_saude(ID,Nome,Morada,Telefone,Email) :: (
    integer(ID),
    atom(Nome),
    atom(Morada),
    integer(Telefone),
    atom(Email)).
	
% Não permite adicionar conhecimento positivo repetido
+centro_saude(Id,_,_,_,_) :: (solucoes(Id,centro_saude(Id,_,_,_,_),S), 
							  comprimento( S,N ), N == 1 ).


% NEGATIVO

% Invariante que permite adicionar conhecimento negativo
+(-centro_saude(ID,Nome,Morada,Telefone,Email)) :: (
    integer(ID),
    atom(Nome),
    atom(Morada),
    integer(Telefone),
    atom(Email)).

% Não permite adicionar conhecimento negativo repetido
+(-centro_saude(Id,_,_,_,_)) :: (solucoes(Id,-centro_saude(Id,_,_,_,_),S), 
								 comprimento( S,N ), N == 2 ).

% ---------- REMOCAO DE CONHECIMENTO PERFEITO --------------

% POSITIVO

% Invariante que permite remover conhecimento positivo
-centro_saude(ID,Nome,Morada,Telefone,Email) :: (
    integer(ID),
    atom(Nome),
    atom(Morada),
    integer(Telefone),
    atom(Email)).

% Invariante que permite limitar a remocao de conhecimento positivo
-centro_Saude(IdCentro,Nome,Morada,Telefone,Email) :: (solucoes(IdCentro,(utente(_,_,_,_,_,_,_,_,_,IdCentro),staff(_,IdCentro,_,_)),S ),
													   comprimento(S,N), N==0,
								   					   solucoes(Id,centroSaude(IdCentro,Nome,Morada,Telefone,Email),S ),
													   comprimento(S,N), N==0).
													  
% NEGATIVO

% Invariante que permite remover conhecimento negativo
-(-centro_saude(ID,Nome,Morada,Telefone,Email)) :: (
    integer(ID),
    atom(Nome),
    atom(Morada),
    integer(Telefone),
    atom(Email)).

% Invariante que permite limitar a remocao de conhecimento negativo
-(-centro_Saude(IdCentro,Nome,Morada,Telefone,Email)) :: (
	solucoes(Id,-centroSaude(IdCentro,Nome,Morada,Telefone,Email),S ),
	comprimento(S,N), N==1).

% -------------- CONHECIMENTO IMPERFEITO -----------------------

% Insercão de conhecimento imperciso
+centro_saude(Id,_,_,_,_) :-: (
	nao(perfeito(centro_saude(Id))), 
	nao(incerto(centro_saude(Id))) 
).

% Insercão de conhecimento incerto ou interdito
+centro_saude(Id,_,_,_,_) :~: (
	nao(perfeito(centro_saude(Id))),
	nao(impreciso(centro_saude(Id))), 
	nao(incerto(centro_saude(Id)))
).

% A inserção ocorre caso o conhecimento a impreciso nao seja repetido
+centro_saude(ID,Nome,Morada,Telefone,Email) :-: (
	solucoes((ID,E), excecao(centro_saude(ID,_,_,_,Email)), L),
	comprimento(L,1)).

% A inserção ocorre caso numero de telefone não seja interdito
+centro_saude(ID,Nome,Morada,Telefone,Email) :~: (
	solucoes(Num,(centro_saude(ID,_,_,Num,_),nulo(Num)),L),
    comprimento(L,N),
    N==0).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% ---------------------- STAFF -------------------------

% ---------- ADICAO DE CONHECIMENTO PERFEITO -----------

% POSITIVO

% Invariante que permite adicionar conhecimento positivo
+staff(Idstaff, Idcentro, Nome, Email) :: (
    integer(Idstaff),
    integer(Idcentro),
    atom(Nome),
    atom(Email)).
	
% Não permite adicionar conhecimento positivo repetido
+staff(Id,_,_,_) :: (solucoes(Id,staff(Id,_,_,_),S), 
					 comprimento( S,N ), N == 1 ).

% NEGATIVO

% Invariante que permite adicionar conhecimento negativo
+(-staff(Idstaff, Idcentro, Nome, Email)) :: (
    integer(Idstaff),
    integer(Idcentro),
    atom(Nome),
    atom(Email)).

% Não permite adicionar conhecimento negativo repetido
+(-staff(Id,_,_,_)) :: (solucoes(Id,-staff(Id,_,_,_),S), 
						comprimento( S,N ), N == 2 ).

% ---------- REMOCAO DE CONHECIMENTO PERFEITO -----------

% POSITIVO

% Invariante que permite remover conhecimento positivo
-staff(Idstaff, Idcentro, Nome, Email) :: (
    integer(Idstaff),
    integer(Idcentro),
    atom(Nome),
    atom(Email)).

% Invariante que permite limitar a remocao de conhecimento positivo
-staff(Id, Idcentro, Nome, Email) :: (
	solucoes(Id,vacinacao_Covid(Id,_,_,_,_),S ),
	comprimento(S,N), N==0,
    solucoes(Id,staff(Id, Idcentro, Nome, Email),S ),
	comprimento(S,N), N==0).
									  
% NEGATIVO

% Invariante que permite remover conhecimento negativo
-(-staff(Idstaff, Idcentro, Nome, Email)) :: (
    integer(Idstaff),
    integer(Idcentro),
    atom(Nome),
    atom(Email)).
	
% Invariante que permite limitar a remocao de conhecimento positivo
-(-staff(Idstaff, Idcentro, Nome, Email)) :: (
	solucoes(Id,-staff(Idstaff, Idcentro, Nome, Email),S ),
	comprimento(S,N), N==1).

% -------------- CONHECIMENTO IMPERFEITO -----------------------

% Insercão de conhecimento impreciso
+staff(Ids,_,_,_) :-: (
	nao(perfeito(staff(Ids))), 
	nao(incerto(staff(Ids))) 
).

% Insercão de conhecimento incerto ou interdito
+staff(Ids,_,_,_) :~: (
	nao(perfeito(staff(Ids))), 
	nao(impreciso(staff(Ids))), 
	nao(incerto(staff(Ids))) 
).

% A inserção ocorre caso o conhecimento a inserido nao seja repetido
+staff(ID,Idcentro,Nome,Email) :-: (
	solucoes((ID,E), excecao(staff(ID,_,_,Email)), L),
	comprimento(L,1)).

% A inserção ocorre caso email não seja interdito
+staff(ID,Idcentro,Nome,Email) :~: (
	solucoes(Em,(staff(ID,_,_,Em),nulo(Em)),L),
    comprimento(L,N),
    N==0).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% ------------------ VACINACAO COVID -----------------------

% ----------- ADICAO DE CONHECIMENTO PERFEITO --------------

% POSITIVO

% Invariante que permite adicionar conhecimento positivo
+vacinacao_Covid(IDStaff,IDUtente,Data,Vacina,Toma) :: (
    integer(IDStaff),
    integer(IDUtente),
    validaData(Data),
    atom(Vacina),
    integer(Toma)).

% Limitar a insercao de novos conhecimentos positivos
+vacinacao_Covid(Staff,Id,_,Vacina,1) :: (
	solucoes(Id,utente(Id,_,_,_,_,_,_,_,_,_),S), 
	comprimento( S,N ), N == 1, %ID tem de ser de um utente
	solucoes(Id,vacinacao_Covid(_,Id,_,_,1),S), 
	comprimento( S,N ), N == 1, %Não ter tomado a primeira toma
	verificaCentro(Id,Staff)). % tem de ser vacinado no centroSaude respetivo								  
											
+vacinacao_Covid(Staff,Id,_,Vacina,2) :: (
	solucoes(Id,utente(Id,_,_,_,_,_,_,_,_,_),S), 
	comprimento( S,N ), N == 1, 
	solucoes(Id,vacinacao_Covid(_,Id,_,Vacina,1),S1), 
	comprimento( S1,N1 ), N1 == 1,
	solucoes(Id,vacinacao_Covid(_,Id,_,Vacina,2),S2), 
	comprimento( S2,N2 ), N2 == 1,
	verificaCentro(Id,Staff)).										 

% NEGATIVO

% Invariante que permite adicionar conhecimento negativo
+(-vacinacao_Covid(IDStaff,IDUtente,Data,Vacina,Toma)) :: (
    integer(IDStaff),
    integer(IDUtente),
    validaData(Data),
    atom(Vacina),
    integer(Toma)).

% Limitar a insercao de novos conhecimentos negativos
+(-vacinacao_Covid(Staff,Id,Data,Vacina,Toma)) :: (
	solucoes(Id,-vacinacao_Covid(_,Id,_,_,_),S1), 
	comprimento( S1,N1 ), N1 == 2, % Nao pode ja estar na BC
	solucoes(Id,utente(Id,_,_,_,_,_,_,_,_,_),S), 
	comprimento( S,N ), N == 1, %ID tem de ser de um utente, 
	verificaCentro(Id,Staff)). % tem de ser vacinado no centroSaude respetivo

% ----------- REMOCAO DE CONHECIMENTO PERFEITO --------------

% POSITIVO

% Invariante que permite remocao conhecimento positivo
-vacinacao_Covid(IDStaff,IDUtente,Data,Vacina,Toma) :: (
    integer(IDStaff),
    integer(IDUtente),
    validaData(Data),
    atom(Vacina),
    integer(Toma)).

% Invariante que permite limitar a remocao de conhecimento positivo
-vacinacao_Covid(IDStaff,Id,Data,Vacina,1) :: (
	solucoes(Id,vacinacao_Covid(_,Id,_,_,1),S ),
	comprimento(S,N), N==0, 
	solucoes(Id,vacinacao_Covid(_,Id,_,_,2),S1 ),
	comprimento(S1,N1), N1==0).

-vacinacao_Covid(IDStaff,Id,Data,Vacina,2) :: (
	solucoes(Id,vacinacao_Covid(_,Id,_,_,2),S),
	comprimento(S,N), N==0).

% NEGATIVO

% Invariante que permite remocao conhecimento negativo
-(-vacinacao_Covid(IDStaff,IDUtente,Data,Vacina,Toma)) :: (
    integer(IDStaff),
    integer(IDUtente),
    validaData(Data),
    atom(Vacina),
    integer(Toma)).

% Invariante que permite limitar a remocao de conhecimento negativo
-(-vacinacao_Covid(IDStaff,IDUtente,Data,Vacina,Toma)) :: (
	solucoes(Id,-vacinacao_Covid(IDStaff,IDUtente,Data,Vacina,Toma),S ),
	comprimento(S,N), N==1).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% ------------------ CANCELA VACINA -----------------------

% ----------- ADICAO DE CONHECIMENTO PERFEITO -------------

% POSITIVO

% Invariante que permite adicionar conhecimento positivo
+cancela_vacina(Id,Data) :: (integer(Id),
							 validaData(Data)).

% Limitar a insercao de conhecimento positivo
+cancela_vacina(Id,Data) :: (solucoes(Id,utente(Id,_,_,_,_,_,_,_,_,_),S), 
							 comprimento( S,N ), N == 1, 
							 solucoes(Id,cancela_vacina(Id,_),S0),
							 comprimento( S0,N0 ), N0 == 1,
							 solucoes(Id,vacinacao_Covid(_,Id,_,Vacina,1),S1), 
							 comprimento( S1,N1 ), N1 == 0,
							 solucoes(Id,vacinacao_Covid(_,Id,_,Vacina,2),S2), 
							 comprimento( S2,N2 ), N2 == 0, !).				

% NEGATIVO

% Invariante que permite adicionar conhecimento positivo	
+(-cancela_vacina(Id,Data)) :: (integer(Id),
								validaData(Data)).

% Limitar a insercao de conhecimento negativo
+(-cancela_vacina(Id,Data)) :: (solucoes(Id,utente(Id,_,_,_,_,_,_,_,_,_),S), 
								comprimento( S,N ), N == 1, % Utente existe
								solucoes(Id,-cancela_vacina(Id,_),S0), 
								comprimento( S0,N0 ), N0 == 2). % Nao pode adicionar conhecimento repetido
								
% ----------- REMOCAO DE CONHECIMENTO PERFEITO -------------

% POSITIVO

% Invariante que permite remocao conhecimento positivo
-cancela_vacina(Id,Data) :: (
	integer(Id),
	validaData(Data)).

% Invariante que permite limitar a remocao de conhecimento positivo
-cancela_vacina(Id,Data) :: (
	solucoes(Id,utente(Id,_,_,_,_,_,_,_,_,_),S), 
	comprimento( S,N ), N == 1,
	solucoes(Id,cancela_vacina(Id,Data),S1), 
	comprimento( S1,N1 ), N1 == 0).
							 
% NEGATIVO

% Invariante que permite remover conhecimento negativo
-(-cancela_vacina(Id,Data)) :: (integer(Id),
								validaData(Data)).

% Invariante que permite limitar a remocao de conhecimento negativo
-(-cancela_vacina(IdUtente,Data)) :: (
	solucoes(Id,utente(IdUtente,_,_,_,_,_,_,_,_,_),S), 
	comprimento( S,N ), N == 1,
	solucoes(Id,-cancela_vacina(IdUtente,Data),S1), 
	comprimento( S1,N1 ), N1 == 1).