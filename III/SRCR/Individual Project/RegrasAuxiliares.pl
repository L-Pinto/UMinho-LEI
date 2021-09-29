%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Sistema de Representacao de conhecimento e raciocinio
% Trabalho Individual

%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%- REGRAS AUXILIARES

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado membro: Elemento, Lista -> {V,F}

membro(X,LD) :- member(X,LD).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Verifica se existe pelo menos um elemento de uma lista na outra: ListaA, ListaB -> {V,F}

verificaElem([X|H],LD) :- membro(X,LD).
verificaElem([X|H],LD) :- verificaElem(H,LD).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado solucoes: Elemento, Questao, Lista -> {V,F}

solucoes(F,P,S) :- findall(F,P,S).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado comprimento: Lista, Comprimento -> {V,F}

comprimento(L,N):- length(L,N).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Verifica se a variavel é uma lista: Lista -> {V,F}

validaLista([]).
validaLista([H|T]).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do meta-predicado nao: Questao -> {V,F}

nao( Questao ) :-
    Questao, !, fail.
nao( Questao ).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Calcula o inverso de uma lista: Lista -> {V,F}

inverso(Xs, Ys) :-
    inverso(Xs, [], Ys).

inverso([], Xs, Xs).
inverso([X|Xs], Ys, Zs) :- inverso(Xs, [X|Ys], Zs).
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Seleciona um elemento de uma lista  -> {V,F}

seleciona(E,[E|Xs],Xs).
seleciona(E,[X|Xs],[X|Ys]) :- seleciona(E,Xs,Ys).


%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Regras Auxiliares QUERIES    
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicado que calcula o caminho com mais pontos de recolha
% maisPR : Lista , Elemento -> {V,F}

maisPR([E],E).

maisPR([(S,Nr)|H],(S,Nr)) :- 
    maisPR(H,(S1,Nr1)), 
    Nr > Nr1.

maisPR([(S,Nr)|H],(S1,Nr1)) :- 
    maisPR(H,(S1,Nr1)), 
    Nr1 >= Nr.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicado que cálcula o elemento minimo de uma lista
% minimo : Lista , Elemento -> {V,F}
minimo([(P,X)],(P,X)).
minimo([(Px,X)|L], (Py,Y)) :- minimo(L,(Py,Y)), X > Y.
minimo([(Px,X)|L], (Px,X)) :- minimo(L,(Py,Y)), X =< Y.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicado que calcula o caminho com mais lixo recolhido
% maisQnt : Lista , Elemento -> {V,F}

maisQnt([E],E).

maisQnt([(S,Qnt,Nr)|H],(S,Qnt,Nr)) :- 
    maisQnt(H,(S1,Qnt1,Nr1)), 
    Qnt > Qnt1.

maisQnt([(S,Qnt,Nr)|H],(S1,Qnt1,Nr1)) :- 
    maisQnt(H,(S1,Qnt1,Nr1)), 
    Qnt1 >= Qnt.

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicado que calcula o caminho com mais lixo recolhido por idas ao deposito
% maisQntE : Lista , Elemento -> {V,F}

maisQntE([E],E).

maisQntE([(S,Qnt,Nr)|H],(S,Qnt,Nr)) :- 
    maisQntE(H,(S1,Qnt1,Nr1)), 
    (Qnt/Nr) > (Qnt1/Nr1).

maisQntE([(S,Qnt,Nr)|H],(S1,Qnt1,Nr1)) :- 
    maisQntE(H,(S1,Qnt1,Nr1)), 
    (Qnt1/Nr1) >= (Qnt/Nr).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Predicado que calcula o caminho com mais lixo recolhido por Kms percorridos
% maisQntKm : Lista , Elemento -> {V,F}

maisQntKm([E],E).
maisQntKm([(S,Qnt,Km)|H],(S,Qnt,Km)) :- maisQntKm(H,(S1,Qnt1,Km1)), 
                                        (Qnt/Km) > (Qnt1/Km1).
maisQntKm([(S,Qnt,Km)|H],(S1,Qnt1,Km1)) :- maisQntKm(H,(S1,Qnt1,Km1)), 
                                           (Qnt1/Km1) >= (Qnt/Km).


%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% % Regras Auxiliares Algoritmos 
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% deposito
goal(965).
%---------------------------------

% predicado adjacente para grafos unidirecional

adjacente(Nodo,ProxNodo) :-
    ponto(_,_,Nodo,_,Vizinhos,_,_,_,_,_),
    membro(ProxNodo,Vizinhos).
% adjacente(Nodo,ProxNodo) :-
%    ponto(_,_,ProxNodo,_,Vizinhos,_,_,_,_,_),
%    membro(Nodo,Vizinhos).  

% predicado adjacente para grafos bidirecional

adjacente2(Nodo,ProxNodo) :-
    ponto(_,_,Nodo,_,Vizinhos,_,_,_,_,_),
    membro(ProxNodo,Vizinhos).
adjacente2(Nodo,ProxNodo) :-
    ponto(_,_,ProxNodo,_,Vizinhos,_,_,_,_,_),
    membro(Nodo,Vizinhos).  

% calcula kilometros de um percurso
kilometros([H],0).
kilometros([H,B|T],Kms) :- distancia(H, B, Dist),
                           kilometros([B|T],KmAux),
                           Kms is KmAux+Dist.

% estima distancia entre nodo e goal/deposito definido
estima(Nodo, Dist) :-
    goal(D),
    ponto(Lat2,Lon2,D,_,_,_,_,_,_,_),
    ponto(Lat1,Lon1,Nodo,_,_,_,_,_,_,_),
    P is 0.017453292519943295,
    A is (0.5 - cos((Lat2 - Lat1) * P) / 2 + cos(Lat1 * P) * cos(Lat2 * P) * (1 - cos((Lon2 - Lon1) * P)) / 2),
    Dist is (12742 * asin(sqrt(A))).

% estima distancia entre dois nodos
distancia(Nodo, NodoT, Dist) :-
    ponto(Lat2,Lon2,Nodo,_,_,_,_,_,_,_),
    ponto(Lat1,Lon1,NodoT,_,_,_,_,_,_,_),
    P is 0.017453292519943295,
    A is (0.5 - cos((Lat2 - Lat1) * P) / 2 + cos(Lat1 * P) * cos(Lat2 * P) * (1 - cos((Lon2 - Lon1) * P)) / 2),
    Dist is (12742 * asin(sqrt(A))).

                           
% calcula quantidade de lixo de um percurso
qntLixos([],0,0,0,0,0).
qntLixos([H|T],CL,CP,CE,CV,CO) :- ponto(_,_,H,_,_,CLP,CPP,CEP,CVP,COP),
                                  qntLixos(T,CLAux,CPAux,CEAux,CVAux,COAux),
                                  CL is CLAux+CLP,    % Quantidade lixo tipo - geral
                                  CP is CPAux+CPP,    % Quantidade lixo tipo - papel e cartao
                                  CE is CEAux+CEP,    % Quantidade lixo tipo - embalagens
                                  CV is CVAux+CVP,    % Quantidade lixo tipo - vidro
                                  CO is COAux+COP.    % Quantidade lixo tipo - organio   

% apresenta solucao com numero de idas ao deposito
deposito(Percurso,Solucao, DepCamiao,Idas) :-
    goal(D),
    depositoAux(Percurso,[D],Solucao,DepCamiao,Idas).

depositoAux([],Solucao,Solucao,0,1). 
depositoAux([H|Tail],Aux,Solucao,Carga,Idas) :-
    goal(D),
    ponto(_,_,H,_,_,Q1,Q2,Q3,Q4,Q5), 
    depositoAux(Tail,Lista,Solucao,CargaAux,IdasAux),
    ( CargaAux+Q1+Q2+Q3+Q4+Q5 =< 14000 -> Lista = [H|Aux],
      Carga is CargaAux+Q1+Q2+Q3+Q4+Q5, 
      Idas is IdasAux;
      Lista = [D,H|Aux], Carga is 0 , Idas is IdasAux + 1).  

%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% % Regras Auxiliares Algoritmos  - MEDICAO PERFORMANCE
%---------------------------------
% Depth First - Estatisticas de Memoria utilizada e Tempo de execucao. 
statisticsDF(I, F, Solucao,CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito,Mem) :-
    statistics(global_stack, [Used1,Free1]),
    time(resolveDFFinal(I, F, Solucao,CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito)),
    statistics(global_stack, [Used2,Free2]),
    Mem is Used2 - Used1.
%---------------------------------
% % Depth First Limitado- Estatisticas de Memoria utilizada e Tempo de execucao. 
statisticsDFLimitado(I,F,Solucao,Lim, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal,IdasDeposito,Mem) :-
    statistics(global_stack, [Used1,Free1]),
    time(resolveDFLimite(I,F,Solucao,Lim, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal,IdasDeposito)),
    statistics(global_stack, [Used2,Free2]),
    Mem is Used2 - Used1.
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Breadth First - Estatisticas de Memoria utilizada e Tempo de execucao.
statisticsBF(I,F,Solucao, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito,Mem) :-
    statistics(global_stack, [Used1,Free1]),
    time(resolveBF(I,F,Solucao, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito)),
    statistics(global_stack, [Used2,Free2]),
    Mem is Used2 - Used1.
%---------------------------------
% Gulosa - Estatisticas de Memoria utilizada e Tempo de execucao. 
statisticsGulosa(Nodo, Solucao/Custo, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito,Mem) :-
    statistics(global_stack, [Used1,Free1]),
    time(resolve_gulosa(Nodo, Solucao/Custo, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito)),
    statistics(global_stack, [Used2,Free2]),
    Mem is Used2 - Used1.
%---------------------------------
% A estrela- Estatisticas de Memoria utilizada e Tempo de execucao. 
statisticsAEstrela(Nodo, Solucao/Custo, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito, Mem) :-
    statistics(global_stack, [Used1,Free1]),
    time(resolve_aestrela(Nodo, Solucao/Custo, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito)),
    statistics(global_stack, [Used2,Free2]),
    Mem is Used2 - Used1.
      