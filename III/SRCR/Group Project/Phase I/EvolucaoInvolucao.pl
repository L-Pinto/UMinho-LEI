%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Sistema de Representacao de conhecimento e raciocinio
% Trabalho Prático - 1ª fase

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Definicoes iniciais

:- op( 900,xfy,'::' ).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% EVOLUCAO E INVOLUCAO

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado que permite a evolucao do conhecimento

evolucao( Termo ) :- solucoes(Invariante,+Termo::Invariante,Lista), 
					insercao(Termo), 
					teste(Lista).
	
insercao(Termo) :- assert(Termo).
insercao(Termo) :- retract(Termo), !, fail. %- impede o backtracking (!) e falha (fail)



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensão do predicado que permite a involucao do conhecimento

involucao(Termo) :- Termo,
					solucoes(Invariante,-Termo::Invariante,Lista), 
					remocao(Termo), 
					teste(Lista).
					
remocao(Termo) :- retract(Termo).
remocao(Termo) :- assert(Termo), !, fail.


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Testes ao invariante: Lista -> {V,F}

teste([]).
teste([R|LR]) :- R, teste(LR).
