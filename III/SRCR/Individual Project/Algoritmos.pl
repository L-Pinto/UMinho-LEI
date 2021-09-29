%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% PESQUISA NÃO INFORMADA
%--------------------------------- - - - - - - - - - -  -  -  -  -   -


% Profundidade (DFS - Depth-First Search)
% Divisao por lixo: LIXO   PAPEL   EMBALAGENS   VIDRO   ORGANICOS

resolveDFFinal(I, F, Solucao,CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito) :- 
                                                                resolveDFF(I, F, [I],SolucaoAux),
                                                                inverso([I|SolucaoAux],NovoInv),
                                                                deposito(NovoInv,Solucao, DepCamiao,IdasDeposito),        
                                                                comprimento(Solucao, L),
                                                                Visitas is L,
                                                                kilometros(Solucao,Kms),    
                                                                qntLixos(Solucao,CL,CP,CE,CV,CO),
                                                                QntTotal is CL+CP+CE+CV+CO.     % Quantidade total de lixo - todo tipo.
                                                                
resolveDFF(Estado, Final,_,[]) :- Estado == Final, !.%o prolog constroi a solução fim para o incio é por isso que aparece lista vazia na solução
resolveDFF(Estado, Final, Historico,[Move|Solucao]) :-  
                                                        adjacente(Estado,Move),
														nao(membro(Move,Historico)),
														resolveDFF(Move,Final,[Move|Historico],Solucao).                                   

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Busca Iterativa Limitada em Profundidade
% LIXO   PAPEL   EMBALAGENS   VIDRO   ORGANICOS

resolveDFLimite(I,F,Solucao,Lim, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal,IdasDeposito)  :- resolveDFFLimite(I, F, [I],SolucaoAux, Lim),
                                                                    inverso([I|SolucaoAux],NovoInv),
                                                                    deposito(NovoInv,Solucao, DepCamiao,IdasDeposito),   
                                                                    comprimento(Solucao, L),
                                                                    Visitas is L,
                                                                    kilometros(Solucao,Kms),
                                                                    qntLixos(Solucao,CL,CP,CE,CV,CO),
                                                                    QntTotal is CL+CP+CE+CV+CO.     % Quantidade total de lixo - todo tipo.

resolveDFFLimite(Estado, Final,_,[],_) :- Estado == Final, !. % o prolog constroi a solução fim para o incio é por isso que aparece lista vazia na solução
resolveDFFLimite(Estado, Final, Historico,[Move|Solucao],Lim) :- Lim > 1, % ja conta inicial
                                                        adjacente(Estado,Move),
                                                        Lim1 is Lim -1,
														nao(membro(Move,Historico)),
														resolveDFFLimite(Move,Final,[Move|Historico],Solucao, Lim1).
                                                        
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Largura (BFS - Breadth-First Search)

resolveBF(I,F,Solucao, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito)  :- resolveBFFinal(F, [[I]], SolucaoAux),
                                                        inverso(SolucaoAux,NovoInv),
                                                        deposito(NovoInv,Solucao, DepCamiao,IdasDeposito),   
                                                        comprimento(Solucao, L),
                                                        Visitas is L,
                                                        kilometros(Solucao,Kms),
                                                        qntLixos(Solucao,CL,CP,CE,CV,CO),
                                                        QntTotal is CL+CP+CE+CV+CO.     % Quantidade total de lixo - todo tipo.

resolveBFFinal( F, [[F|Visitados] |_], Path) :- inverso([F|Visitados], Path).
resolveBFFinal( F, [Visitados|Resto], Path) :-                                  % take one from front
    Visitados = [Atual|_],            
    Atual \== F,
    findall( X,
        ( adjacente2(Atual, X), nao(membro(X, Visitados)) ),
        [T|Extensao]),
    maplist( adicionaQueue(Visitados), [T|Extensao], VisitadosExtra),          % make many
    append( Resto, VisitadosExtra, NovaFila),                                  % put them at the end
    resolveBFFinal( F, NovaFila, Path).

adicionaQueue( A, B, [B|A]).

%--------------------------------- - - - - - - - - - -  -  -  -  -   - 
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% PESQUISA INFORMADA
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Gulosa

resolve_gulosa(Nodo, Solucao/Custo, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito) :-
	estima(Nodo, Estima),
	agulosa([[Nodo]/0/Estima], InvCaminho/Custo/_),
    deposito(InvCaminho,Solucao, DepCamiao,IdasDeposito),        
    comprimento(Solucao, L),
    Visitas is L,
    kilometros(Solucao,Kms),    
    qntLixos(Solucao,CL,CP,CE,CV,CO),
    QntTotal is CL+CP+CE+CV+CO.     % Quantidade total de lixo - todo tipo.
	
agulosa(Caminhos, Caminho) :-
	obtem_melhor_g(Caminhos, Caminho),
	Caminho = [Nodo|_]/_/_,goal(Nodo).
	
agulosa(Caminhos, SolucaoCaminho) :-
	obtem_melhor_g(Caminhos, MelhorCaminho),
	seleciona(MelhorCaminho, Caminhos, OutrosCaminhos), % retira caminho da lista
	expande_gulosa(MelhorCaminho, ExpCaminhos),
	append(OutrosCaminhos, ExpCaminhos, NovosCaminhos),
	agulosa(NovosCaminhos, SolucaoCaminho).


obtem_melhor_g([Caminho],Caminho) :- !.
obtem_melhor_g([Caminho1/Custo1/Est1,_/Custo2/Est2|Caminhos],MelhorCaminho) :-
            Est1 =< Est2, !,
            obtem_melhor_g([Caminho1/Custo1/Est1|Caminhos],MelhorCaminho).
obtem_melhor_g([_|Caminhos],MelhorCaminho) :- 
            obtem_melhor_g(Caminhos,MelhorCaminho).

expande_gulosa(Caminho, ExpCaminhos) :- 
	findall(NovoCaminho, adjacente3(Caminho, NovoCaminho), ExpCaminhos).
	
% evitamosCiclos
adjacente3([Nodo|Caminho]/Custo/_, [ProxNodo,Nodo|Caminho]/NovoCusto/Estimativa) :-
    adjacente2(Nodo, ProxNodo),
    distancia(Nodo, ProxNodo, PassoCusto),
    nao(membro(ProxNodo,Caminho)),
	NovoCusto is Custo + PassoCusto,
	estima(ProxNodo,Estimativa).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% A*

resolve_aestrela(Nodo, Solucao/Custo, CL/CP/CE/CV/CO, Kms,Visitas, QntTotal, IdasDeposito) :-
	estima(Nodo, Estima),
	aestrela([[Nodo]/0/Estima], InvCaminho/Custo/_),
    deposito(InvCaminho,Solucao, DepCamiao,IdasDeposito),        
    comprimento(Solucao, L),
    Visitas is L,
    kilometros(Solucao,Kms),    
    qntLixos(Solucao,CL,CP,CE,CV,CO),
    QntTotal is CL+CP+CE+CV+CO.     % Quantidade total de lixo - todo tipo.

aestrela(Caminhos, Caminho) :-
	obtem_melhor(Caminhos, Caminho),
	Caminho = [Nodo|_]/_/_,goal(Nodo).
	
aestrela(Caminhos, SolucaoCaminho) :-
	obtem_melhor(Caminhos, MelhorCaminho),
	seleciona(MelhorCaminho, Caminhos, OutrosCaminhos),
	expande_aestrela(MelhorCaminho, ExpCaminhos),
	append(OutrosCaminhos, ExpCaminhos, NovosCaminhos),
		aestrela(NovosCaminhos, SolucaoCaminho).

% custo + estimativa
% antes era so custo
obtem_melhor([Caminho],Caminho) :- !.
obtem_melhor([Caminho1/Custo1/Est1,_/Custo2/Est2|Caminhos],MelhorCaminho) :-
            (Est1 + Custo1) =< (Est2 + Custo2), !,
            obtem_melhor([Caminho1/Custo1/Est1|Caminhos],MelhorCaminho).
obtem_melhor([_|Caminhos],MelhorCaminho) :- 
            obtem_melhor(Caminhos,MelhorCaminho).

expande_aestrela(Caminho, ExpCaminhos) :- 
	findall(NovoCaminho, adjacente3(Caminho, NovoCaminho), ExpCaminhos).

