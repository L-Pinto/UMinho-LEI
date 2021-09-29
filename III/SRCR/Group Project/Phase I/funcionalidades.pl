%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Sistema de Representacao de conhecimento e raciocinio
% Trabalho Prático - 1ª fase

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Definicoes iniciais
:- set_prolog_flag(answer_write_options, [max_depth(0)]).
:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).

:- style_check(-singleton).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Includes
:- include('BaseConhecimento.pl').
:- include('regrasAuxiliares.pl').
:- include('EvolucaoInvolucao.pl').
:- include('Invariantes.pl').

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% FUNCIONALIDADES
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Total de Vacinados - com 2 doses: Lista -> {V,F}

totalVacinados(L) :- solucoes((Id,X),vacinado(Id,X),L).

vacinado(Id,X) :- utente(Id,_,X,_,_,_,_,_,_,_),
					vacinacao_Covid(_,Id,_,_,1),
					vacinacao_Covid(_,Id,_,_,2).
	
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Total Não Vacinados - sem nenhuma dose: Lista -> {V,F}

totalNaoVacinados(L) :- solucoes((Id,X),naoVacinado(Id,X),L).

naoVacinado(Id,X) :- utente(Id,_,X,_,_,_,_,_,_,_),
						nao(vacinacao_Covid(_,Id,_,_,_)).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Utentes com vacinas indevidas: Lista -> {V,F}

vacinasIndevidas(L) :- solucoes((Id,X),utenteIndevido(Id,X),L).

%- caso seja vacinado na fase errada
utenteIndevido(Id,X):- utente(Id,_,X,Data,_,_,_,Profissao,Doenca,_),
						vacinacao_Covid(_,Id,DataVacina1,_,1),
						obtemFase(DataVacina1,Fase),
						nao(confirmaFase(Fase,Id,Profissao,Doenca)).
						
%- caso seja vacinado fora do intervalo tempo
utenteIndevido(Id,X):- utente(Id,_,X,Data,_,_,_,Profissao,Doenca,_),
						vacinacao_Covid(_,Id,DataVacina1,_,1),
						vacinacao_Covid(_,Id,DataVacina2,_,2),
						nao(confirmaToma(DataVacina1,DataVacina2)).

%- caso tenha cancelado a vacina e depois tomou a 1 dose
utenteIndevido(Id,X):- utente(Id,_,X,_,_,_,_,_,_,_),
						cancela_vacina(Id,DataCancela),
						vacinacao_Covid(_,Id,DataVacina1,_,1),
						dataAntes(DataCancela,DataVacina1).

%- caso tenha tomado a 1 dose cancelado a vacina e depois tomou 2 dose			
utenteIndevido(Id,X):- utente(Id,_,X,_,_,_,_,_,_,_),
						cancela_vacina(Id,DataCancela),
						vacinacao_Covid(_,Id,DataVacina2,_,2),
						dataAntes(DataCancela,DataVacina2).
					

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Utentes candidatos ás fases: Lista -> {V,F}

utentesCandidatos(Fase,L) :- solucoes((Id,X),candidatos(Fase,X,Id),L).
candidatos(Fase,X,Id) :- utente(Id,_,X,Data,_,_,_,Profissao,Doenca,_),
							nao(cancela_vacina(Id,DataCancela)),
							nao(vacinacao_Covid(_,Id,_,_,_)),
							confirmaFase(Fase,Id,Profissao,Doenca).
							
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% utentes candidatos á segunda toma - so com 1 dose: Lista -> {V,F}

utentesSegundaToma(L) :- solucoes((Id,X),candidatosSegunda(X,Id),L).
candidatosSegunda(X,Id) :- utente(Id,_,X,Data,_,_,_,Profissao,Doenca,_),
							vacinacao_Covid(_,Id,_,_,1), 
							nao(vacinacao_Covid(_,Id,_,_,2)).
						

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Sistema de inferencia: Questão, ValorLógico -> {V,F}

si(Questao,verdadeiro) :- Questao.
si(Questao, falso) :- nao(Questao).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% EXTRAS

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Vacinas dadas num centro de saude: Centro, Lista -> {V,F}

vacinasCentro(Centro, L) :- solucoes((Id,X),vacinaCentroUtente(Centro,X,Id),L).

vacinaCentroUtente(Centro,X,Id) :- utente(Id,_,X,Data,_,_,_,_,_,_), 
									vacinacao_Covid(IdStaff,Id,_,_,_), 
									staff(IdStaff,Centro,_,_).
									
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Numero de vacinas dadas num centro de saude: Centro, Numero -> {V,F}

nrVacinasCentro(Centro, N) :- solucoes((Id,X),
								vacinaCentroUtente(Centro,X,Id),L), 
								comprimento(L,N).
								
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Utentes que nao querem ser vacinados: Lista -> {V,F}

vacinasCanceladas(L) :- solucoes((Id,X),utenteCancelado(X,Id),L).

utenteCancelado(X,Id) :- cancela_vacina(Id,DataCancela), utente(Id,_,X,_,_,_,_,_,_,_).

