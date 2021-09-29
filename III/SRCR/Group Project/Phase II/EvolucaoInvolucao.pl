%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Sistema de Representacao de conhecimento e raciocinio
% Trabalho Prático - 1ª fase


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% EVOLUCAO

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Definicoes iniciais

:- op( 900,xfy,'::' ).  %- criação de um novo operador para o prolog

%conhecimento interdito
:- op(900,xfy,:~:).

%conhecimento impreciso
:- op(900,xfy,:-:).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado que permite a evolucao do conhecimento

evolucao(Termo) :- solucoes(Invariante,+Termo::Invariante,Lista), 
				   insercao(Termo), 
				   teste(Lista).
					
evolucao(Termo, positivo) :- solucoes(Invariante,+Termo::Invariante,Lista), 
                             insercao(Termo), 
                             teste(Lista),
							 idInsertTermo(Termo).
                    
evolucao(Termo, negativo) :- solucoes(Invariante,+(-Termo)::Invariante,Lista), 
                             insercao(-Termo), 
                             teste(Lista),
					     	 idInsertTermo(Termo).
                            
evolucao(Termo, impreciso) :- solucoes(Invariante,+Termo:-:Invariante,ListaImpreciso),
                              insercao(excecao(Termo)),
							  teste(ListaImpreciso).
							
evolucao(Termo, incerto) :- solucoes(Invariante,+Termo::Invariante,Lista),
							solucoes(Invariante,+Termo:~:Invariante,ListaImpreciso), 
                            insercao(Termo),
							teste(Lista),
							teste(ListaImpreciso).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% ---------------- EVOLUCAO CONHECIMENTO INCERTO -------------

% -------------------------- UTENTE --------------------------

% Caso Nome incerto
evolucao(utente(Id,SS,Nome_Incerto,Data,Email,Telefone,Morada,Profissao,Doenca,Centro), incerto, nome) :-
    evolucao(utente(Id,SS,Nome_Incerto,Data,Email,Telefone,Morada,Profissao,Doenca,Centro), incerto),
    insercao(excecao(utente(I,S,N,D,E,T,M,P,LD,C)) :-
                     utente(I,S,Nome_Incerto,D,E,T,M,P,LD,C)),
	insercao(incerto(utente(Id))).

% Caso Morada incerto
evolucao(utente(Id,SS,Nome,Data,Email,Telefone,Morada_Incerto,Profissao,Doenca,Centro), incerto, morada) :-
    evolucao(utente(Id,SS,Nome,Data,Email,Telefone,Morada_Incerto,Profissao,Doenca,Centro), incerto),
    insercao((excecao(utente(I,S,N,D,E,T,M,P,LD,C)) :-
                      utente(I,S,N,D,E,T,Morada_Incerto,P,LD,C))),
	insercao(incerto(utente(Id))).

% Caso Email incerto
evolucao(utente(Id,SS,Nome,Data,Email_Incerto,Telefone,Morada,Profissao,Doenca,Centro), incerto, email) :-
    evolucao(utente(Id,SS,Nome,Data,Email_Incerto,Telefone,Morada,Profissao,Doenca,Centro), incerto),
    insercao((excecao(utente(I,S,N,D,E,T,M,P,LD,C)) :-
                    utente(I,S,N,D,Email_Incerto,T,M,P,LD,C))),
	insercao(incerto(utente(Id))).

% -------------------- CENTRO SAÚDE -----------------------

% Nome incerto
evolucao(centro_saude(ID,Nome_Incerto,Morada,Telefone,Email), incerto, nome) :-
    evolucao(centro_saude(ID,Nome_Incerto,Morada,Telefone,Email), incerto),
    insercao(excecao(centro_saude(I,N,M,T,E)) :-
                     centro_saude(I,Nome_Incerto,M,T,E)),
	insercao(incerto(centro_saude(ID))).

% Morada Incerto
evolucao(centro_saude(ID,Nome,Morada_Incerto,Telefone,Email), incerto, morada) :-
    evolucao(centro_saude(ID,Nome,Morada_Incerto,Telefone,Email), incerto),
    insercao(excecao(centro_saude(I,N,M,T,E)) :-
                     centro_saude(I,N,Morada_Incerto,T,E)),
	insercao(incerto(centro_saude(ID))).

% Email incerto
evolucao(centro_saude(ID,Nome,Morada,Telefone,Email_Incerto), incerto, email) :-
    evolucao(centro_saude(ID,Nome,Morada,Telefone,Email_Incerto), incerto),
    insercao(excecao(centro_saude(I,N,M,T,E)) :-
                     centro_saude(I,N,M,T,Email_Incerto)),
	insercao(incerto(centro_saude(ID))).

% ---------------------- STAFF -------------------------

% Caso nome incerto
evolucao(staff(Ids,Idc,Nome_Incerto,Email), incerto, nome) :-
    evolucao(staff(Ids,Idc,Nome_Incerto,Email), incerto),
    insercao((excecao(staff(Is,Ic,N,E)) :-
                    staff(Is,Ic,Nome_Incerto,E))),
	insercao(incerto(staff(Ids))).

% Caso email incerto
evolucao(staff(Ids,Idc,Nome,Email_Incerto), incerto, email) :-
    evolucao(staff(Ids,Idc,Nome,Email_Incerto), incerto),
    insercao((excecao(staff(Is,Ic,N,E)) :-
                    staff(Is,Ic,N,Email_Incerto))),
	insercao(incerto(staff(Ids))).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% ---------------- EVOLUCAO CONHECIMENTO IMPRECISO -------------

% -------------------------- UTENTE --------------------------

% Insercao de conhecimento imperciso - morada
evolucao(utente(Id,SS,Nome,Data,Email,Telefone,Morada_Impreciso,Profissao,Doenca,Centro), impreciso, morada) :-
	solucoes(Id, impreciso(utente(Id)), S), 
	nao(membro(Id, S)),
	evolucao(utente(Id,SS,Nome,Data,Email,Telefone,Morada_Impreciso,Profissao,Doenca,Centro),impreciso),
	insercao(impreciso(utente(Id))).
	
evolucao(utente(Id,SS,Nome,Data,Email,Telefone,Morada_Impreciso,Profissao,Doenca,Centro), impreciso, morada) :-
	solucoes(Id, impreciso(utente(Id)), S), 
	membro(Id, S),
	evolucao(utente(Id,SS,Nome,Data,Email,Telefone,Morada_Impreciso,Profissao,Doenca,Centro),impreciso).
		
% -------------------- CENTRO SAÚDE -----------------------

% Insercao de conhecimento imperciso - email
evolucao(centro_saude(ID,Nome,Morada,Telefone,Email_Impreciso), impreciso, email) :-
	solucoes(ID,impreciso(centro_saude(ID)),S),
	nao(membro(ID,S)),
	evolucao(centro_saude(ID,Nome,Morada,Telefone,Email_Impreciso),impreciso),
	insercao(impreciso(centro_saude(ID))).
	
evolucao(centro_saude(ID,Nome,Morada,Telefone,Email_Impreciso), impreciso, email) :-
	solucoes(ID,impreciso(centro_saude(ID)),S),
	membro(ID,S),
	evolucao(centro_saude(ID,Nome,Morada,Telefone,Email_Impreciso),impreciso).

% ---------------------- STAFF -------------------------

% Insercao de conhecimento imperciso - email
evolucao(staff(ID,Idc,Nome,Email_Impreciso), impreciso, email) :-
	solucoes(ID,impreciso(staff(ID)),S),
	nao(membro(ID,S)),
    evolucao((staff(ID,Idc,Nome,Email_Impreciso)),impreciso),
	insercao(impreciso(staff(Id))).
	
evolucao(staff(ID,Idc,Nome,Email_Impreciso), impreciso, email) :-
	solucoes(ID,impreciso(staff(ID)),S),
	membro(ID,S),
    evolucao((staff(ID,Idc,Nome,Email_Impreciso)),impreciso).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% ---------------- EVOLUCAO CONHECIMENTO INTERDITO -------------

% -------------------------- UTENTE --------------------------

% Caso email interdito  
evolucao(utente(Id,SS,Nome,Data,Email_Interdito,Telefone,Morada,Profissao,Doenca,Centro), interdito, email) :-
    evolucao(utente(Id,SS,Nome,Data,Email_Interdito,Telefone,Morada,Profissao,Doenca,Centro), incerto),
    insercao(excecao(utente(I,S,N,D,E,T,M,P,LD,C)) :-
                        utente(I,S,N,D,Email_Interdito,T,M,P,LD,C)),
    insercao(nulo(Email_Interdito)),
	insercao(interdito(utente(Id))).

% -------------------- CENTRO SAÚDE -----------------------

% Caso email interdito    
evolucao(centro_saude(ID,Nome,Morada,Telefone,Email_Interdito), interdito, email) :-
    evolucao(centro_saude(ID,Nome,Morada,Telefone,Email_Interdito), incerto),
    insercao(excecao(centro_saude(I,N,M,T,E)) :-
                     centro_saude(I,N,M,T,Email_Interdito)),
    insercao(nulo(Email_Interdito)),
	insercao(interdito(centro_saude(ID))).

% ---------------------- STAFF -------------------------

% Caso email interdito
evolucao(staff(Ids,Idc,Nome,Email_Interdito), interdito, email) :-
    evolucao(staff(Ids,Idc,Nome,Email_Interdito), incerto),
    insercao((excecao(staff(Is,Ic,N,E)) :-
                    staff(Is,Ic,N,Email_Interdito))),
    insercao((nulo(Email_Interdito))),
	insercao(interdito(staff(Ids))).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% INVOLUCAO

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado que permite a involucao do conhecimento
					
involucao(Termo, positivo) :- Termo,
							solucoes(Invariante,-Termo::Invariante,Lista), 
                            remocao(Termo), 
                            teste(Lista),
							idRemoveTermo(Termo).

involucao(Termo, negativo) :- -Termo,
							solucoes(Invariante,-(-Termo)::Invariante,Lista), 
                            remocao(-Termo), 
                            teste(Lista),
							idRemoveTermo(Termo).

involucao(Termo, impreciso) :- solucoes(Invariante,-Termo::Invariante,Lista), 
                            remocao(excecao(Termo)), 
                            teste(Lista).

involucao(Termo, incerto) :- Termo,
							solucoes(Invariante,-Termo::Invariante,Lista), 
                            remocao(Termo), 
                            teste(Lista).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% ---------------- INVOLUCAO CONHECIMENTO INCERTO -------------

% -------------------------- UTENTE --------------------------

% Caso numero Nome incerto
involucao(utente(Id,SS,Nome_Incerto,Data,Email,Telefone,Morada,Profissao,Doenca,Centro), incerto, nome) :-
    involucao(utente(Id,SS,Nome_Incerto,Data,Email,Telefone,Morada,Profissao,Doenca,Centro), incerto),
    remocao((excecao(utente(I,S,N,D,E,T,M,P,LD,C)) :-
                    utente(I,S,Nome_Incerto,D,E,T,Morada,P,LD,C))),
	remocao(incerto(utente(Id))).

% Caso numero Email incerto
involucao(utente(Id,SS,Nome,Data,Email_Incerto,Telefone,Morada,Profissao,Doenca,Centro), incerto, email) :-
    involucao(utente(Id,SS,Nome,Data,Email_Incerto,Telefone,Morada,Profissao,Doenca,Centro), incerto),
    remocao((excecao(utente(I,S,N,D,E,T,M,P,LD,C)) :-
                    utente(I,S,Nome,D,Email_Incerto,T,Morada,P,LD,C))),
	remocao(incerto(utente(Id))).
                    
% Caso numero Morada incerto
involucao(utente(Id,SS,Nome,Data,Email,Telefone,Morada_Incerto,Profissao,Doenca,Centro), incerto, morada) :-
    involucao(utente(Id,SS,Nome,Data,Email,Telefone,Morada_Incerto,Profissao,Doenca,Centro), incerto),
    remocao((excecao(utente(I,S,N,D,E,T,M,P,LD,C)) :-
                    utente(I,S,Nome,D,E,T,Morada_Incerto,P,LD,C))),
	remocao(incerto(utente(Id))).

% -------------------- CENTRO SAÚDE -----------------------

% Nome incerto
involucao(centro_saude(ID,Nome_Incerto,Morada,Telefone,Email), incerto, nome) :-
    involucao(centro_saude(ID,Nome_Incerto,Morada,Telefone,Email), incerto),
    remocao((excecao(centro_saude(I,N,M,T,E)) :-
                     centro_saude(I,Nome_Incerto,Morada,T,E))),
	remocao(incerto(centro_saude(ID))).

% Morada Incerto
involucao(centro_saude(ID,Nome,Morada_Incerto,Telefone,Email), incerto, morada) :-
    involucao(centro_saude(ID,Nome,Morada_Incerto,Telefone,Email), incerto),
    remocao((excecao(centro_saude(I,N,M,T,E)) :-
                     centro_saude(I,Nome,Morada_Incerto,T,E))),
	remocao(incerto(centro_saude(ID))).

% Email incerto
involucao(centro_saude(ID,Nome,Morada,Telefone,Email_Incerto), incerto, email) :-
    involucao(centro_saude(ID,Nome,Morada,Telefone,Email_Incerto), incerto),
    remocao(excecao(centro_saude(I,N,M,T,E)) :-
                     centro_saude(I,Nome,M,T,Email_Incerto)),
	remocao(incerto(centro_saude(ID))).

% ---------------------- STAFF -------------------------
%staff(Ids,Idc,Nome,Email)

% Caso nome incerto
involucao(staff(Ids,Idc,Nome_Incerto,Email), incerto, nome) :-
    involucao(staff(Ids,Idc,Nome_Incerto,Email), incerto),
    remocao((excecao(staff(Is,Ic,N,E)) :-
                    staff(Is,Ic,Nome_Incerto,Email))),
	remocao(incerto(staff(Ids))).

% Caso email incerto
involucao(staff(Ids,Idc,Nome,Email_Incerto), incerto, email) :-
    involucao(staff(Ids,Idc,Nome,Email_Incerto), incerto),
    remocao((excecao(staff(Is,Ic,N,E)) :-
                    staff(Is,Ic,Nome,Email_Incerto))),
	remocao(incerto(staff(Ids))).
	

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% ---------------- INVOLUCAO CONHECIMENTO IMPRECISO -------------

% -------------------------- UTENTE --------------------------

involucao(utente(Id,SS,Nome,Data,Email,Telefone,Morada_Impreciso,Profissao,Doenca,Centro), impreciso, morada) :-
	solucoes((Id,M), excecao(utente(Id,_,_,_,_,_,_,_,_,_)), L),
	comprimento(L,N),
    N > 1,
	involucao(utente(Id,SS,Nome,Data,Email,Telefone,Morada_Impreciso,Profissao,Doenca,Centro),impreciso).

% Remocao de conhecimento impreciso - morada
involucao(utente(Id,SS,Nome,Data,Email,Telefone,Morada_Impreciso,Profissao,Doenca,Centro), impreciso, morada) :-
	solucoes((Id,M), excecao(utente(Id,_,_,_,_,_,_,_,_,_)), L),
	comprimento(L,N),
    N == 1,
	involucao(utente(Id,SS,Nome,Data,Email,Telefone,Morada_Impreciso,Profissao,Doenca,Centro),impreciso),
    remocao(impreciso(utente(Id))).

% -------------------- CENTRO SAÚDE -----------------------

% Remocao de conhecimento impreciso - email

involucao(centro_saude(ID,Nome,Morada,Telefone,Email_Impreciso), impreciso, email) :-
	solucoes((ID,E), excecao(centro_saude(ID,_,_,_,_)), L),
	comprimento(L,N),
    N > 1,
	involucao(centro_saude(ID,Nome,Morada,Telefone,Email_Impreciso), impreciso).
    
involucao(centro_saude(ID,Nome,Morada,Telefone,Email_Impreciso), impreciso, email) :-
	solucoes((ID,E), excecao(centro_saude(ID,_,_,_,_)), L),
	comprimento(L,N),
    N == 1,
	involucao(centro_saude(ID,Nome,Morada,Telefone,Email_Impreciso), impreciso),
    remocao(impreciso(centro_saude(ID))).

% ---------------------- STAFF -------------------------

% Remocao de conhecimento impreciso - email
involucao(staff(Ids,Idc,Nome,Email_Impreciso), impreciso, email) :-
	solucoes((Ids,E), excecao(staff(Ids,_,_,_)), L),
	comprimento(L,N),
    N > 1,
    involucao((staff(Ids,Idc,Nome,Email_Impreciso)),impreciso).

involucao(staff(Ids,Idc,Nome,Email_Impreciso), impreciso, email) :-
	solucoes((Ids,E), excecao(staff(Ids,_,_,_)), L),
	comprimento(L,N),
    N == 1,
    involucao((staff(Ids,Idc,Nome,Email_Impreciso)),impreciso),
    remocao(impreciso(staff(Ids))).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% ---------------- INVOLUCAO CONHECIMENTO INTERDITO -------------

% -------------------------- UTENTE --------------------------

% Caso email interdito  
involucao(utente(Id,SS,Nome,Data,Email_interdito,Telefone,Morada,Profissao,Doenca,Centro), interdito, email) :-
    involucao(utente(Id,SS,Nome,Data,Email_interdito,Telefone,Morada,Profissao,Doenca,Centro), incerto),
    remocao(excecao(utente(IdUt,S,N,D,E,T,M,P,LD,C)) :-
                    utente(IdUt,S,Nome,D,Email_interdito,T,M,P,LD,C)),
    remocao((nulo(Email_interdito))),
	remocao(interdito(utente(Id))).

% -------------------- CENTRO SAÚDE -----------------------

% Caso email interdito    
involucao(centro_saude(ID,Nome,Morada,Telefone,Email_Interdito), interdito, email) :-
    involucao(centro_saude(ID,Nome,Morada,Telefone,Email_Interdito), incerto),
    remocao(excecao(centro_saude(I,N,M,T,E)) :-
                    centro_saude(I,Nome,M,T,Email_Interdito)),
    remocao(nulo(Email_Interdito)),
	remocao(interdito(centro_saude(ID))).

% ---------------------- STAFF -------------------------

% Caso email interdito
involucao(staff(Ids,Idc,Nome,Email_Interdito), interdito, email) :-
    involucao(staff(Ids,Idc,Nome,Email_Interdito), incerto),
    remocao(excecao(staff(Is,Ic,N,E)) :-
                    staff(Is,Ic,Nome,Email_Interdito)),
    remocao((nulo(Email_Interdito))),
	remocao(interdito(staff(Ids))).