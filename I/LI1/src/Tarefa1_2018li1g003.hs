-- | Este módulo define funções comuns da Tarefa 1 do trabalho prático.
module Tarefa1_2018li1g003 where

import LI11819
import Tarefa0_2018li1g003

-- * Testes

-- | Testes unitários da Tarefa 1.
--
-- Cada teste é uma sequência de 'Instrucoes'.
testesT1 :: [Instrucoes]
testesT1 = [[Move C,Move C,Move D,Move D,Move D,Move D,Move E,Move E,Move B,MudaParede,MudaTetromino,MudaTetromino,MudaTetromino,Desenha,Move D,Move D,Move D,Move D,Desenha,Move B,Move B,Move B,Move D,Move D,Move B,Move B,Move E,Move E,Move E,Move E,Move C,MudaParede,Move C,Desenha,Roda,Move E,Move E,MudaTetromino,MudaTetromino,Move E,Move E,MudaTetromino,Move B,Move B,Move B,Move C,Move D,MudaParede,Move B,Move E,Desenha,Move D,Move D,Move D,Move D,Move D,Move D,Move C,Move E,Desenha,Move E,Move C,Move E,Move B,Move B,Move E,Move D,MudaParede,MudaTetromino,Move B,Desenha],[Roda,MudaTetromino,Move E,Move C,Move D,Move D,Move B,Desenha,Move E,Move E,Move E,Move B,Move B,Move D,Move D,Move D,Move D,Move D,Roda,Roda,MudaTetromino,MudaTetromino,MudaParede,Move B,Desenha,Move E,Move E,Move E,Move E,Move E,Desenha,Move B,Move B,Move D,Move D,Move D,Move B,Move C,Move E,Roda,MudaTetromino,Move B,Desenha],[Roda,Roda,Move B,Move D,Move D,Move D,Move B,Move C,Move E,Move E,MudaParede,Desenha,Move B,Move B,Move B,Move B,Move B,Move B,Move B,Move B,Desenha,Move D,Move D,Move D,Move D,Move D,Move D,Move C,Move C,Move C,Move C,Move C,Move E,Move B,Move E,MudaParede,MudaTetromino,Roda,Desenha],[Roda,Roda,Move B,Move D,Move D,Move D,Move B,Move C,Move E,Move E,MudaParede,Desenha,Move B,Move B,Move B,Move B,Move B,Move B,Move B,Move B,Desenha,Move D,Move D,Move D,Move D,Move D,Move D,Move C,Move C,Move C,Move C,Move C,Move E,Move B,Move E,MudaParede,MudaTetromino,Roda,Desenha,Move D,Move B,Move B,Roda,Roda,Move D,Move D,Move D,Move C,Move C,Move C,Move C,Move E,Move E,MudaTetromino,Roda,Move E,Move E,Desenha,Move B,Move B,Move B,Move B,Move B,Roda,Roda,Move C,Desenha,Move D,Move D,Move D,MudaParede,Move C,MudaParede,MudaParede,Roda,Roda,Roda,Roda,Roda,Roda,Roda,MudaTetromino,MudaTetromino,Move C,Move C,Move E,Move C,Move C,Move C,Move C,Desenha,Move B,Move B,Move B,Move B,Move B,Move B,Move B,Move B,Move B,Move E,Move E,Move E],tes2,tes3,tes4]
tes2=[Move B,Move B,Move B,Move E,Move E,Move E,Move E,Move E,Move C,MudaParede,Roda,Move D,Desenha,Roda,Roda,Move C,Move C,MudaTetromino,MudaTetromino,MudaTetromino,Move D,Move B,Move B,Move B,Move B,Desenha,Move B,Move B,Move B,Move B,Move B,Move D,Move D,Move D,Move D,Move D,Move D,Move D,Move D,Move D,Move C,Move C,Move E,Desenha,Move E,Move E,Move E,Move E,MudaTetromino,MudaParede,Move C,Move B,Move B,Move E,Desenha,Move C,Move C,Move C,Move C,Roda,Move B,Move C,Move C,MudaParede,Move B,Move D,Move C,Move C,Move C,Move C,MudaTetromino,Move C,Move E,Move C,Desenha,Move B,Move B,Move B,MudaTetromino,Desenha,Move B,Move B,Move B,Move B,Move B,Move B,Move C,MudaTetromino,Desenha,Move C,Move C,Move C,Desenha,Move B,Move B,Move B,Move E,Move E,Move E,Move E,Move E,Move E,Move E,Move E,Move E,Move B,Move B,Move B,Move B,Move B,Move C,Move C,Move C,Move D,MudaParede,Roda,Roda,MudaTetromino,MudaTetromino,Move D,MudaTetromino,Move B,Desenha,Move D,Move D,Move D,Desenha,Move B,Move B,Move B,Move D,Move D,Move D,Move D,Move D,Move D,Move D,Move C,Desenha]
tes3=[Move B,Move B,Move B,Move E,Move E,Move E,Move E,Move E,Move C,MudaParede,Roda,Move D,Desenha,Roda,Roda,Move C,Move C,MudaTetromino,MudaTetromino,MudaTetromino,Move D,Move B,Move B,Move B,Move B,Desenha,Move B,Move B,Move B,Move B,Move B,Move D,Move D,Move D,Move D,Move D,Move D,Move D,Move D,Move D,Move C,Move C,Move E,Desenha,Move E,Move E,Move E,Move E,MudaTetromino,MudaParede,Move C,Move B,Move B,Move E,Desenha,Move C,Move C,Move C,Move C,Roda,Move B,Move C,Move C,MudaParede,Move B,Move D,Move C,Move C,Move C,Move C,MudaTetromino,Move C,Move E,Move C,Desenha,Move B,Move B,Move B,MudaTetromino,Desenha,Move B,Move B,Move B,Move B,Move B,Move B,Move C,MudaTetromino,Desenha,Move C,Move C,Move C,Desenha,Move B,Move B,Move B,Move E,Move E,Move E,Move E,Move E,Move E,Move E,Move E,Move E,Move B,Move B,Move B,Move B,Move B,Move C,Move C,Move C,Move D,MudaParede,Roda,Roda,MudaTetromino,MudaTetromino,Move D,MudaTetromino,Move B,Desenha,Move D,Move D,Move D,Desenha,Move B,Move B,Move B,Move D,Move D,Move D,Move D,Move D,Move D,Move D,Move C,Desenha]
tes4=[Roda,Desenha,Move B,Move B,Move B,Move E,Move E,Move E,Move E,Desenha,Move D,Move D,Roda,MudaParede,Desenha,Move C,Move C,Move C,Move C,Move E,Move E,Move E,Move E,Move E,Move B,Move B,Move E,Move E,Move C,Desenha,MudaTetromino,Move B,Move B,Move B,MudaTetromino,Move B,Move D,Move D,Move D,Move C,Move C,Move C,Move D,Move D,Roda,Move C,Move C,Desenha]

-- * Funções principais da Tarefa 1.
-- 
-- ** 'instrucao'
-- | Aplica uma 'Instrucao' num 'Editor'.
--
--    * 'Move' - move numa dada 'Direcao'.
--
--    * 'MudaTetromino' - seleciona a 'Peca' seguinte (usar a ordem léxica na estrutura de dados),
--       sem alterar os outros parâmetros.
--
--    * 'MudaParede' - muda o tipo de 'Parede'.
--
--    * 'Desenha' - altera o 'Mapa' para incluir o 'Tetromino' atual, sem alterar os outros parâmetros.
--
-- __NB:__ Deve assumir que o cursor e tetrominós cabem __sempre__ dentro das bordas do 'Mapa', e que o mapa __nunca__ necessita de ser redimensionado.
instrucao :: Instrucao -- ^ A 'Instrucao' a aplicar.
          -> Editor    -- ^ O 'Editor' anterior.
          -> Editor   -- ^ O 'Editor' resultante após aplicar a 'Instrucao'.
instrucao a (Editor (x,y) r e w q) 
                                  | (a == MudaTetromino) = (Editor (x,y) r (auxTetromino(e)) w q)
                                  | (a == Move C) = (Editor (x-1,y) r e w q)
                                  | (a == Move B) = (Editor (x+1,y) r e w q)
                                  | (a == Move D) = (Editor (x,y+1) r e w q)
                                  | (a == Move E) = (Editor (x,y-1) r e w q)
                                  | (a==Roda) = (Editor (x,y) (auxMudaDir(r)) e w q)
                                  | (a==MudaParede) && (w==Indestrutivel) = (Editor (x,y) r e (Destrutivel) q)
                                  | (a==MudaParede) && (w==Destrutivel) = (Editor (x,y) r e (Indestrutivel) q)
                                  | (a==Desenha) && (e==I) = desenharMapaI (Editor (x,y) r e w q)
                                  | (a==Desenha) && (e==J) = desenharMapaJ (Editor (x,y) r e w q)
                                  | (a==Desenha) && ((e==L)|| (e==O)) = desenharMapaLO (Editor (x,y) r e w q)
                                  | (a==Desenha) && (e==S) = desenharMapaS (Editor (x,y) r e w q)
                                  | (a==Desenha) && (e==T) = desenharMapaT (Editor (x,y) r e w q)
                                  | (a==Desenha) && (e==Z) = desenharMapaZ (Editor (x,y) r e w q)

-- | Dado um 'Tetromino' a função 'auxTetromino' indica, com base na ordem léxica na estrutura de dados, o próximo 'Tetromino'.
auxTetromino :: Tetromino -> Tetromino
auxTetromino I = J 
auxTetromino J = L
auxTetromino L = O
auxTetromino O = S
auxTetromino S = T
auxTetromino T = Z
auxTetromino Z = I

-- | A função 'auxMudaDir' serve de auxiliar para mudar a 'Direcao' do 'Tetromino'.
auxMudaDir:: Direcao->Direcao
auxMudaDir C = D
auxMudaDir D = B
auxMudaDir B = E
auxMudaDir E = C

-- | A função 'dimensaoMatriz1' calcula a 'Dimensao' de um 'Mapa'.
dimensaoMatriz1 :: Mapa  -> Dimensao
dimensaoMatriz1 [[]] = (0,0)
dimensaoMatriz1 [] =(0,0)
dimensaoMatriz1 (h:t) |(length h <=0)=(0,0)
                      |otherwise=( (length (t) )+1,(length h) )

-- | A função 'ePosicaoMatrizValida1' verifica se a 'Posicao' pertence ao 'Mapa'.
ePosicaoMatrizValida1 :: Posicao -> Mapa  -> Bool 
ePosicaoMatrizValida1 (l1,c1) a = let (k,l) = dimensaoMatriz1(a) 
                                  in (if (l1<k && c1<l) && (l1>=0 && c1>=0)  then True  else False )

-- | A função 'modificaLinhaM' modifica uma 'Peca' na 'Posicao' indicada.
modificaLinhaM:: Posicao -> Peca -> [Peca] -> [Peca]
modificaLinhaM (a,0) c (h:hs) = (c:hs)
modificaLinhaM (a,b) c (h:hs) = [h] ++ modificaLinhaM (a,b-1) c hs

-- | A função 'atualizaPosicaoMatrizM' atualiza uma 'Peca' numa 'Posicao' do 'Mapa'.
atualizaPosicaoMatrizM :: Posicao -> Peca -> Mapa -> Mapa 
atualizaPosicaoMatrizM (a,b) c (h:hs) | (ePosicaoMatrizValida1 (a,b) (h:hs) == False) = []
                                      | (a==0) = (modificaLinhaM (1,b) c h ):hs
                                      | otherwise = [h] ++ atualizaPosicaoMatrizM (a-1,b) c hs

-- | Dada um lista de posições a função 'mudaMApa' muda as mesmas para a 'Peca' dada no parametro da função, atualizando o mapa.
mudaMApa::[Posicao] ->Peca-> Mapa ->Mapa
mudaMApa [] p m =m
mudaMApa (h:t) p m =mudaMApa t p (atualizaPosicaoMatrizM h p m)

-- | Função que recebe um 'Editor' e 'Desenha' um 'Mapa' para o 'Tetromino' I. 
desenharMapaI::Editor->Editor
desenharMapaI ( Editor (x,y) r e w q )
                                   | (e==I) && (r==C) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y+1),(x+1,y+1),(x+2,y+1),(x+3,y+1)]) (Bloco w) q))
                                   | (e==I) && (r==D) = (Editor (x,y) r e w (mudaMApa
                                   ([(x+1,y),(x+1,y+1),(x+1,y+2),(x+1,y+3)]) (Bloco w) q))
                                   | (e==I) && (r==B) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y+2),(x+1,y+2),(x+2,y+2),(x+3,y+2)]) (Bloco w) q))
                                   | (e==I) && (r==E) = (Editor (x,y) r e w (mudaMApa
                                   ([(x+2,y),(x+2,y+1),(x+2,y+2),(x+2,y+3)]) (Bloco w) q))

-- | Função que recebe um 'Editor' e 'Desenha' um 'Mapa' para o 'Tetromino' J. 
desenharMapaJ::Editor->Editor
desenharMapaJ ( Editor (x,y) r e w q )
                                   | (e==J) && (r==C) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y+1),(x+1,y+1),(x+2,y),(x+2,y+1)]) (Bloco w) q))
                                   | (e==J) && (r==D) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y),(x+1,y),(x+1,y+1),(x+1,y+2)]) (Bloco w) q))
                                   | (e==J) && (r==B) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y+1),(x,y+2),(x+1,y+1),(x+2,y+1)]) (Bloco w) q))
                                   | (e==J) && (r==E) = (Editor (x,y) r e w (mudaMApa
                                   ([(x+1,y),(x+1,y+1),(x+1,y+2),(x+2,y+2)]) (Bloco w) q))
                                   
-- | Função que recebe um 'Editor' e 'Desenha' um 'Mapa' para o 'Tetromino' L ou para o 'Tetromino' O.
desenharMapaLO::Editor->Editor
desenharMapaLO ( Editor (x,y) r e w q ) 
                                   | (e==L) && (r==C) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y+1),(x+1,y+1),(x+2,y+1),(x+2,y+2)]) (Bloco w) q))
                                   | (e==L) && (r==D) = (Editor (x,y) r e w (mudaMApa
                                   ([(x+1,y),(x+1,y+1),(x+1,y+2),(x+2,y)]) (Bloco w) q))
                                   | (e==L) && (r==B) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y),(x,y+1),(x+1,y+1),(x+2,y+1)]) (Bloco w) q))
                                   | (e==L) && (r==E) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y+2),(x+1,y),(x+1,y+1),(x+1,y+2)]) (Bloco w) q))
                                   | (e==O) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y),(x,y+1),(x+1,y),(x+1,y+1)]) (Bloco w) q))
                                   
-- | Função que recebe um 'Editor' e 'Desenha' um 'Mapa' para o 'Tetromino' S. 
desenharMapaS::Editor->Editor
desenharMapaS ( Editor (x,y) r e w q) 
                                   | (e==S) && (r==C) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y+1),(x,y+2),(x+1,y),(x+1,y+1)]) (Bloco w) q))
                                   | (e==S) && (r==D) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y+1),(x+1,y+1),(x+1,y+2),(x+2,y+2)]) (Bloco w) q))
                                   | (e==S) && (r==B) = (Editor (x,y) r e w (mudaMApa
                                   ([(x+1,y+1),(x+1,y+2),(x+2,y),(x+2,y+1)]) (Bloco w) q))
                                   | (e==S) && (r==E) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y),(x+1,y),(x+1,y+1),(x+2,y+1)]) (Bloco w) q))

-- | Função que recebe um 'Editor' e 'Desenha' um 'Mapa' para o 'Tetromino' T. 
desenharMapaT::Editor->Editor
desenharMapaT ( Editor (x,y) r e w q) 
                                   | (e==T) && (r==C) = (Editor (x,y) r e w (mudaMApa
                                   ([(x+1,y),(x+1,y+1),(x+1,y+2),(x+2,y+1)]) (Bloco w) q))
                                   | (e==T) && (r==D) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y+1),(x+1,y),(x+1,y+1),(x+2,y+1)]) (Bloco w) q))
                                   | (e==T) && (r==B) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y+1),(x+1,y),(x+1,y+1),(x+1,y+2)]) (Bloco w) q))
                                   | (e==T) && (r==E) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y+1),(x+1,y+1),(x+1,y+2),(x+2,y+1)]) (Bloco w) q))

-- | Função que recebe um 'Editor' e 'Desenha' um 'Mapa' para o 'Tetromino' Z.
desenharMapaZ::Editor->Editor
desenharMapaZ ( Editor (x,y) r e w q)
                                   | (e==Z) && (r==C) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y),(x,y+1),(x+1,y+1),(x+1,y+2)]) (Bloco w) q))
                                   | (e==Z) && (r==D) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y+2),(x+1,y+1),(x+1,y+2),(x+2,y+1)]) (Bloco w) q))
                                   | (e==Z) && (r==B) = (Editor (x,y) r e w (mudaMApa
                                   ([(x+1,y),(x+1,y+1),(x+2,y+1),(x+2,y+2)]) (Bloco w) q))
                                   | (e==Z) && (r==E) = (Editor (x,y) r e w (mudaMApa
                                   ([(x,y+1),(x+1,y),(x+1,y+1),(x+2,y)]) (Bloco w) q))                                               
-----------------------------------------------------------------------------------------------------------------

-- ** 'instrucoes'

-- | Aplica uma sequência de 'Instrucoes' num 'Editor'.
--
-- __NB:__ Deve chamar a função 'instrucao'.
instrucoes :: Instrucoes -- ^ As 'Instrucoes' a aplicar.
           -> Editor     -- ^ O 'Editor' anterior.
           -> Editor     -- ^ O 'Editor' resultante após aplicar as 'Instrucoes'.
instrucoes [] b =b
instrucoes (h:t) b = instrucoes t ( instrucao h b )
-----------------------------------------------------------------------------------------------------------------------------------------

-- ** 'mapaInicial'

-- | Cria um 'Mapa' inicial com 'Parede's nas bordas e o resto vazio.
mapaInicial :: Dimensao -- ^ A 'Dimensao' do 'Mapa' a criar.
            -> Mapa     -- ^ O 'Mapa' resultante com a 'Dimensao' dada.
mapaInicial (a,b) = mudaMApa (reverse (selPOS (a,b) (mapaPOSx (a,b))) ) (Bloco Indestrutivel) (mapaVazio (a,b))  --let (h:t)=mapaVazio ()

-- | Verifica se a 'Posicao' está numa borda do 'Mapa'.
eBordaMatriz1 :: Posicao -> Mapa -> Bool
--eBordaMatriz c a |(ePosicaoMatrizValida c a == False) = False 
eBordaMatriz1 (_,0) a = True
eBordaMatriz1 (0,_) a = True 
eBordaMatriz1 (l,b) a = let (h,t) = dimensaoMatriz1(a) in ( if ( l==(h-1) || b==(t-1) ) && (l>=0 && b>=0) then True else False)

-- | Funcao auxiliar que coloca uma linha de 'Peca's como 'Vazia'.
colocaVazia ::Int->[Peca]
colocaVazia 0 = []
colocaVazia 1 = [Vazia]
colocaVazia a = [Vazia] ++ colocaVazia (a-1)

-- | Dada uma 'Dimensao' cria um 'Mapa' todo vazio.
mapaVazio :: Dimensao->Mapa
mapaVazio (0,_)= []
mapaVazio (a,b) = [colocaVazia b] ++ mapaVazio ((a-1),b)

-- | Função que calcula as posições de uma linha.
mapaPOSy::Dimensao->[Posicao]
mapaPOSy (a,0) = []
mapaPOSy (a,b) = [(a,b-1)] ++ mapaPOSy (a,b-1) 

-- | Cria as posições de um 'Mapa' dado uma 'Dimensao'.
mapaPOSx :: Dimensao->[Posicao]
mapaPOSx (0,b)= []
mapaPOSx (a,b)= mapaPOSy(a-1,b) ++ mapaPOSx (a-1,b)

-- | Seleciona as posições das bordas e retorna essas posições.
selPOS :: Dimensao->[Posicao] ->[Posicao]
selPOS a []= []
selPOS a (h:t) = if (eBordaMatriz1 h (mapaVazio a))  then [h] ++ selPOS a t
                                                     else selPOS a t
----------------------------------------------------------------------------------------------------------------------------------------------------

-- ** 'editorInicial'

-- | Cria um 'Editor' inicial.
--
-- __NB:__ Deve chamar as funções 'mapaInicial', 'dimensaoInicial', e 'posicaoInicial'.
editorInicial :: Instrucoes  -- ^ Uma sequência de 'Instrucoes' de forma a poder calcular a  'dimensaoInicial' e a 'posicaoInicial'.
              -> Editor      -- ^ O 'Editor' inicial, usando a 'Peca' 'I' 'Indestrutivel' voltada para 'C'.
editorInicial [] = Editor (1,1) C I Indestrutivel (mapaInicial (6,6))
editorInicial a =  Editor (posicaoInicial a) C I Indestrutivel (mapaInicial (dimensaoInicial a)) 

-------------------------------------------------------------------------------------------------------------------------------------------------------

-- ** 'constroi'

-- | Constrói um 'Mapa' dada uma sequência de 'Instrucoes'.

-- __NB:__ Deve chamar as funções 'Instrucoes' e 'editorInicial'.
constroi :: Instrucoes -- ^ Uma sequência de 'Instrucoes' dadas a um 'Editor' para construir um 'Mapa'.
         -> Mapa       -- ^ O 'Mapa' resultante.
constroi [] = getMapa (editorInicial [])
constroi is = getMapa (instrucoes is (editorInicial is))

-- | Esta função retorna o mapa de um editor.
getMapa :: Editor -> Mapa
getMapa (Editor (x,y) r e w q) = q




