#TP_Individual.pl
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% SIST. REPR. CONHECIMENTO E RACIOCINIO - MiEI/3
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Definicoes iniciais
:- set_prolog_flag(answer_write_options, [max_depth(0)]).
:- set_prolog_flag( discontiguous_warnings,off ).
:- set_prolog_flag( single_var_warnings,off ).

:- style_check(-singleton).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Includes
:- include('BaseConhecimento.pl').
:- include('Algoritmos.pl').
:- include('RegrasAuxiliares.pl').

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Programacao em logica 
% Resolução de problemas de pesquisa (TP Individual)

%---------------------------------  dados do problema ---------
% Heuristica de distancia de qualquer ponto de recolha ao despejo
% capacidade camião : 15000 litros 
% garagem 
% deposito

%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% QUERIES
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Seletiva


% Profundidade (DFS - Depth-First Search)
% Divisao por lixo: LIXO   PAPEL   EMBALAGENS   VIDRO   ORGANICOS
% Limitado por Tipo de lixo

% Teste Melhor e Mais Detalhado : resolveDFFinal(355, 921, Solucao,CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito).

resolveDFSeletivo(I, F,Tipo, Solucao, QntLixo, Kms, NrVisitas, IdasDeposito) :-
                                            Tipo=='lixo',
                                            resolveDFFinal(I, F, Solucao, QntLixo/CP/CE/CV/CO, Kms,NrVisitas,QntTotal, IdasDeposito).
resolveDFSeletivo(I, F,Tipo, Solucao, QntLixo, Kms, NrVisitas, IdasDeposito) :-
                                            Tipo=='papel',
                                            resolveDFFinal(I, F, Solucao,CL/QntLixo/CE/CV/CO, Kms,NrVisitas,QntTotal, IdasDeposito).
resolveDFSeletivo(I, F,Tipo, Solucao, QntLixo, Kms, NrVisitas, IdasDeposito) :-
                                            Tipo=='embalagens',
                                            resolveDFFinal(I, F, Solucao,CL/CP/QntLixo/CV/CO, Kms,NrVisitas,QntTotal, IdasDeposito).
resolveDFSeletivo(I, F,Tipo, Solucao, QntLixo, Kms, NrVisitas, IdasDeposito) :-
                                            Tipo=='vidro',
                                            resolveDFFinal(I, F, Solucao,CL/CP/CE/QntLixo/CO, Kms,NrVisitas,QntTotal, IdasDeposito).
resolveDFSeletivo(I, F,Tipo, Solucao, QntLixo, Kms, NrVisitas, IdasDeposito) :-
                                            Tipo=='organicos',
                                            resolveDFFinal(I, F, Solucao,CL/CP/CE/CV/QntLixo, Kms,NrVisitas,QntTotal, IdasDeposito).
resolveDFSeletivo(I, F,Tipo, Solucao, QntLixo, Kms, NrVisitas, IdasDeposito) :-
                                            Tipo=='indiferenciado',
                                            resolveDFFinal(I, F, Solucao,CL/CP/CE/CV/CO, Kms,NrVisitas,QntLixo, IdasDeposito).

% Busca Iterativa Limitada em Profundidade
% Divisao por lixo: LIXO   PAPEL   EMBALAGENS   VIDRO   ORGANICOS
% Limitado por Tipo de lixo

% Teste Melhor e Mais Detalhado : resolveDFLimite(355,921,Solucao,20, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal,IdasDeposito).

resolveDFLimiteSeletivo(I, F, Lim, Tipo, Solucao, QntLixo, Kms,  NrVisitas, IdasDeposito) :-
                                            Tipo=='lixo',
                                            resolveDFLimite(I, F, Solucao, Lim, QntLixo/CP/CE/CV/CO, Kms, NrVisitas, QntTotal, IdasDeposito).
resolveDFLimiteSeletivo(I, F, Lim, Tipo, Solucao, QntLixo, Kms,  NrVisitas, IdasDeposito) :-
                                            Tipo=='papel',
                                            resolveDFLimite(I, F, Solucao, Lim, CL/QntLixo/CE/CV/CO, Kms, NrVisitas, QntTotal, IdasDeposito).
resolveDFLimiteSeletivo(I, F, Lim, Tipo, Solucao, QntLixo, Kms,  NrVisitas, IdasDeposito) :-
                                            Tipo=='embalagens',
                                            resolveDFLimite(I, F, Solucao, Lim, CL/CP/QntLixo/CV/CO, Kms, NrVisitas, QntTotal, IdasDeposito).
resolveDFLimiteSeletivo(I, F, Lim, Tipo, Solucao, QntLixo, Kms,  NrVisitas, IdasDeposito) :-
                                            Tipo=='vidro',
                                            resolveDFLimite(I, F, Solucao, Lim, CL/CP/CE/QntLixo/CO, Kms, NrVisitas, QntTotal, IdasDeposito).
resolveDFLimiteSeletivo(I, F, Lim, Tipo, Solucao, QntLixo, Kms,  NrVisitas, IdasDeposito) :-
                                            Tipo=='organicos',
                                            resolveDFLimite(I, F, Solucao, Lim, CL/CP/CE/CV/QntLixo, Kms, NrVisitas, QntTotal, IdasDeposito).
resolveDFLimiteSeletivo(I, F, Lim, Tipo, Solucao, QntLixo, Kms,  NrVisitas, IdasDeposito) :-
                                            Tipo=='indiferenciado',
                                            resolveDFLimite(I, F, Solucao, Lim, CL/CP/CE/CV/CO, Kms, NrVisitas,QntLixo, IdasDeposito).


% Largura (BFS - Breadth-First Search)
% Divisao por lixo: LIXO   PAPEL   EMBALAGENS   VIDRO   ORGANICOS
% Limitado por Tipo de lixo

% Teste : resolveBFSeletivo(651, 662,lixo, Solucao, QntLixo, Kms, Visitas).
% Teste Melhor e Mais Detalhado : resolveBF(651,662,Solucao, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal).

resolveBFSeletivo(I, F,Tipo, Solucao, QntLixo, Kms, Visitas, IdasDeposito) :-
                                            Tipo=='lixo',
                                            resolveBF(I,F,Solucao, QntLixo/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito).
resolveBFSeletivo(I, F,Tipo, Solucao, QntLixo, Kms, Visitas, IdasDeposito) :-
                                            Tipo=='papel',
                                            resolveBF(I,F,Solucao, CL/QntLixo/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito).
resolveBFSeletivo(I, F,Tipo, Solucao, QntLixo, Kms, Visitas, IdasDeposito) :-
                                            Tipo=='embalagens',
                                            resolveBF(I,F,Solucao, CL/CP/QntLixo/CV/CO, Kms,Visitas, QntTotal, IdasDeposito).
resolveBFSeletivo(I, F,Tipo, Solucao, QntLixo, Kms, Visitas, IdasDeposito) :-
                                            Tipo=='vidro',
                                            resolveBF(I,F,Solucao, CL/CP/CE/QntLixo/CO, Kms,Visitas, QntTotal, IdasDeposito).
resolveBFSeletivo(I, F,Tipo, Solucao, QntLixo, Kms, Visitas, IdasDeposito) :-
                                            Tipo=='organicos',
                                            resolveBF(I,F,Solucao, CL/CP/CE/CV/QntLixo, Kms,Visitas, QntTotal, IdasDeposito).
resolveBFSeletivo(I, F,Tipo, Solucao, QntLixo, Kms, Visitas, IdasDeposito) :-
                                            Tipo=='indiferenciado',
                                            resolveBF(I,F,Solucao, CL/CP/CE/CV/CO, Kms,Visitas, QntLixo, IdasDeposito).

% gulosa 
% Divisao por lixo: LIXO   PAPEL   EMBALAGENS   VIDRO   ORGANICOS
% Limitado por Tipo de lixo 
resolveGulosaSeletivo(Nodo, Tipo, Solucao/Custo, QntLixo, Kms, Visitas, IdasDeposito) :-                                        
                                            Tipo=='lixo',
                                            resolve_gulosa(Nodo, Solucao/Custo, QntLixo/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito).
resolveGulosaSeletivo(Nodo, Tipo, Solucao/Custo, QntLixo, Kms, Visitas, IdasDeposito) :-                                        
                                            Tipo=='papel',
                                            resolve_gulosa(Nodo, Solucao/Custo, CL/QntLixo/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito).
resolveGulosaSeletivo(Nodo, Tipo, Solucao/Custo, QntLixo, Kms, Visitas, IdasDeposito) :-                                        
                                            Tipo=='embalagens',
                                            resolve_gulosa(Nodo, Solucao/Custo, CL/CP/QntLixo/CV/CO, Kms,Visitas, QntTotal, IdasDeposito).
resolveGulosaSeletivo(Nodo, Tipo, Solucao/Custo, QntLixo, Kms, Visitas, IdasDeposito) :-                                        
                                            Tipo=='vidro',
                                            resolve_gulosa(Nodo, Solucao/Custo, CL/CP/CE/QntLixo/CO, Kms,Visitas, QntTotal, IdasDeposito).
resolveGulosaSeletivo(Nodo, Tipo, Solucao/Custo, QntLixo, Kms, Visitas, IdasDeposito) :-                                        
                                            Tipo=='organicos',
                                            resolve_gulosa(Nodo, Solucao/Custo, CL/CP/CE/CV/QntLixo, Kms,Visitas, QntTotal, IdasDeposito).
resolveGulosaSeletivo(Nodo, Tipo, Solucao/Custo, QntLixo, Kms, Visitas, IdasDeposito) :-                                        
                                            Tipo=='indiferenciado',
                                            resolve_gulosa(Nodo, Solucao/Custo, CL/CP/CE/CV/CO, Kms,Visitas, QntLixo, IdasDeposito).     

% A* 
% Divisao por lixo: LIXO   PAPEL   EMBALAGENS   VIDRO   ORGANICOS
% Limitado por Tipo de lixo 
resolveAEstrelaSeletivo(Nodo, Tipo, Solucao/Custo, QntLixo, Kms,Visitas, IdasDeposito) :-
                                            Tipo=='lixo',
                                            resolve_aestrela(Nodo, Solucao/Custo, QntLixo/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito).
resolveAEstrelaSeletivo(Nodo, Tipo, Solucao/Custo, QntLixo, Kms,Visitas, IdasDeposito) :-
                                            Tipo=='papel',
                                            resolve_aestrela(Nodo, Solucao/Custo, CL/QntLixo/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito).
resolveAEstrelaSeletivo(Nodo, Tipo, Solucao/Custo, QntLixo, Kms,Visitas, IdasDeposito) :-
                                            Tipo=='embalagens',
                                            resolve_aestrela(Nodo, Solucao/Custo, CL/CP/QntLixo/CV/CO, Kms,Visitas, QntTotal, IdasDeposito).
resolveAEstrelaSeletivo(Nodo, Tipo, Solucao/Custo, QntLixo, Kms,Visitas, IdasDeposito) :-
                                            Tipo=='vidro',
                                            resolve_aestrela(Nodo, Solucao/Custo, CL/CP/CE/QntLixo/CO, Kms,Visitas, QntTotal, IdasDeposito).
resolveAEstrelaSeletivo(Nodo, Tipo, Solucao/Custo, QntLixo, Kms,Visitas, IdasDeposito) :-
                                            Tipo=='organicos',
                                            resolve_aestrela(Nodo, Solucao/Custo, CL/CP/CE/CV/QntLixo, Kms,Visitas, QntTotal, IdasDeposito).
resolveAEstrelaSeletivo(Nodo, Tipo, Solucao/Custo, QntLixo, Kms,Visitas, IdasDeposito) :-
                                            Tipo=='indiferenciado',
                                            resolve_aestrela(Nodo, Solucao/Custo, CL/CP/CE/CV/CO, Kms,Visitas, QntLixo, IdasDeposito).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%  Escolher o circuito mais rápido (usando o critério da distância);

% Teste : menorDistanciaDF(843,921,Resultado).
menorDistanciaDF(I, F,Resultado) :- 
                         findall((Solucao,Kms),resolveDFFinal(I, F, Solucao, C, Kms,Visitas,QntTotal, IdasDeposito),L),
                         minimo(L,Resultado).

% Teste : menorDistanciaDFLimitado(355,227,20,Resultado).
menorDistanciaDFLimitado(I,F,Lim,Resultado) :- 
                        findall((Solucao,Kms),resolveDFLimite(I, F, Solucao, Lim, C, Kms,Visitas,QntTotal, IdasDeposito),L),
                        minimo(L,Resultado).

% Teste : menorDistanciaBF(355,333,Resultado).
menorDistanciaBF(I,F,Resultado) :- 
                        findall((Solucao,Kms),resolveBF(I,F,Solucao, C, Kms,Visitas, QntTotal, IdasDeposito),L),
                        minimo(L,Resultado).

% Teste : menorDistanciaGulosa(355,Caminho/Distancia,CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito).
% o destino tem de ser estipulado exemplo goal(965)
menorDistanciaGulosa(Nodo, Caminho/Distancia,CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito) :-
                                            resolve_gulosa(Nodo, Caminho/Distancia,CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito).

% Teste : menorDistanciaAestrela(355,Caminho/Distancia, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito).
% o destino tem de ser estipulado exemplo goal(965)
menorDistanciaAestrela(Nodo, Caminho/Distancia, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito) :- 
                                            resolve_aestrela(Nodo, Caminho/Distancia, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%  Circuitos com mais pontos de recolha

%  Teste : maisPontoRecolhaDF(843, 921,Resultado).
maisPontoRecolhaDF(I, F,Resultado) :- findall((Solucao,Visitas),
                                    resolveDFFinal(I, F, Solucao, C, Kms,Visitas,QntTotal, IdasDeposito),
                                    L),
                                    maisPR(L,Resultado).

% Teste : maisPontoRecolhaDFLimitado(651,662,10,Resultado). Testar com limite 4,6,8 por exemplo
maisPontoRecolhaDFLimitado(I,F,Lim,Resultado) :- findall((Solucao,Visitas),
                                                resolveDFLimite(I, F, Solucao, Lim, C, Kms,Visitas,QntTotal, IdasDeposito),
                                                L),
                                                maisPR(L,Resultado).

% Teste : maisPontoRecolhaBF(651,662,Resultado).
maisPontoRecolhaBF(I,F,Resultado) :- findall((Solucao,Visitas),resolveBF(I,F,Solucao, C, Kms,Visitas, QntTotal, IdasDeposito),L),
                                    maisPR(L,Resultado).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%  Escolher o circuito mais eficiente

% Eficiencia Por Quantidade de Lixo Recolhido

% Teste :  maisLixoDF(843, 921, Resultado).
maisLixoDF(I, F, Resultado) :- findall((Solucao,QntTotal,IdasDeposito),
                        resolveDFFinal(I, F, Solucao, C, Kms,Visitas,QntTotal, IdasDeposito),
                        L),
                        maisQnt(L,Resultado).

% Teste : maisLixoDFLimitado(355,921,10,Resultado).
maisLixoDFLimitado(I,F,Lim,Resultado) :- findall((Solucao,QntTotal,IdasDeposito),
                        resolveDFLimite(I, F, Solucao, Lim, C, Kms,Visitas,QntTotal, IdasDeposito),
                        L),
                        maisQnt(L,Resultado).

% Teste : maisLixoBF(355,333,Resultado).
% maisLixoBF(I,F,Resultado) :- findall((Solucao,QntTotal,IdasDeposito),
%                         resolveBF(I,F,Solucao, C, Kms,Visitas, QntTotal, IdasDeposito),L),
%                         maisQnt(L,Resultado).

%---------------------------------

% Eficiencia Por Quantidade de Lixo e Idas ao Deposito

% Teste :  maisLixoIdasDepositoDF(843, 921, Resultado).
maisLixoIdasDepositoDF(I, F, Resultado) :- findall((Solucao,QntTotal,IdasDeposito),
                        resolveDFFinal(I, F, Solucao, C, Kms,Visitas,QntTotal, IdasDeposito),L),
                        maisQntE(L,Resultado).

% Teste : maisLixoIdasDepositoDFLimitado(355,921,10,Resultado).
maisLixoIdasDepositoDFLimitado(I,F,Lim,Resultado) :- findall((Solucao,QntTotal,IdasDeposito),
                        resolveDFLimite(I, F, Solucao, Lim, C, Kms,Visitas,QntTotal, IdasDeposito),L),
                        maisQntE(L,Resultado).

% Teste : maisLixoIdasDepositoBF(355,333,Resultado).
% maisLixoIdasDepositoBF(I,F,Resultado) :- findall((Solucao,QntTotal,IdasDeposito),
%                       resolveBF(I,F,Solucao, C, Kms,Visitas, QntTotal, IdasDeposito),L),
%                       maisQntE(L,Resultado).

%---------------------------------

% Eficiencia Por Quantidade de Lixo Recolhido e Kms percorridos

% Teste :  maisLixoKmsDF(843, 921, Resultado).
maisLixoKmsDF(I, F, Resultado) :- findall((Solucao,QntTotal,Kms),
                        resolveDFFinal(I, F, Solucao, C, Kms,Visitas,QntTotal, IdasDeposito),L),
                        maisQntKm(L,Resultado).

% Teste : maisLixoKmsDFLimitado(355,921,10,Resultado).
maisLixoKmsDFLimitado(I,F,Lim,Resultado) :- findall((Solucao,QntTotal,Kms),
                        resolveDFLimite(I, F, Solucao, Lim, C, Kms,Visitas,QntTotal, IdasDeposito),L),
                        maisQntKm(L,Resultado).

% Teste : maisLixoKmsBF(355,333,Resultado).
% maisLixoKmsBF(I,F,Resultado) :- findall((Solucao,QntTotal,Kms),
%                         resolveBF(I,F,Solucao, C, Kms,Visitas, QntTotal, IdasDeposito),L),
%                         maisQntKm(L,Resultado).
