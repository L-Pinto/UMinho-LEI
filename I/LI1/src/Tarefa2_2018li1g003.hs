-- | Este módulo define funções comuns da Tarefa 2 do trabalho prático.
module Tarefa2_2018li1g003 where

import LI11819

-- * Testes

-- | Testes unitários da Tarefa 2.
--
-- Cada teste é um triplo (/identificador do 'Jogador'/,/'Jogada' a efetuar/,/'Estado' anterior/).
testesT2 :: [(Int,Jogada,Estado)]
testesT2 = [(0,(Movimenta C),Estado (omapa) [(Jogador (1,1) D 5 5 5),(Jogador (9,1) B 5 5 5),(Jogador (1,8) C 5 5 5)] [DisparoLaser 1 (2,2) C,DisparoCanhao 0 (2,2) B ]  ) , (0,(Dispara Choque),Estado (omapa2) [(Jogador (1,1) D 5 5 5),(Jogador (9,1) B 5 5 5),(Jogador (1,8) C 5 5 5)] [DisparoChoque 0 5 ,DisparoLaser 1 (2,2) C,DisparoCanhao 0 (2,2) B ,DisparoCanhao 0 (13,4) B ] ),(1,(Dispara Canhao),est3),(2,(Dispara Choque),est4)]
-- | 'Mapa' colocado deste modo para facilitar leitura de 'testesT2'.
omapa=[[Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel]]
-- | 'Mapa'2 que também faz parte do 'testesT2'.
omapa2=[[Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Bloco Indestrutivel,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel]]
--testesT2 =[(0,(Dispara Choque),Estado (omapa) [(Jogador (1,1) D 5 5 5),(Jogador (9,1) B 5 5 5),(Jogador (1,8) C 5 5 5)] [DisparoChoque 0 5 ,DisparoLaser 1 (2,2) C,DisparoCanhao 0 (2,2) B ,DisparoCanhao 0 (13,4) B ] ) ]
-- | 'Mapa'3 que também faz parte do 'testesT2'.
omapa3=[[Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Destrutivel,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel]]
-- | 'Mapa'4 que também faz parte do 'testesT2'.
omapa4=[[Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Bloco Destrutivel,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Destrutivel,Vazia,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel,Bloco Destrutivel,Vazia,Vazia,Vazia,Bloco Destrutivel,Bloco Destrutivel,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Vazia,Vazia,Bloco Indestrutivel,Bloco Destrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Vazia,Vazia,Vazia,Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel,Bloco Indestrutivel,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Vazia,Bloco Indestrutivel],[Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel,Bloco Indestrutivel]]
-- | est3 é um estado de jogo.
est3=(Estado (omapa3) [(Jogador (1,1) D 5 5 5),(Jogador (1,4) E 5 5 5)] [DisparoLaser 1 (4,4) B,DisparoCanhao 0 (6,2) B ,DisparoChoque 0 5 ] ) 
-- | est4 é um estado de jogo.
est4=(Estado (omapa4) [(Jogador (1,1) B 5 5 5),(Jogador (16,16) B 5 5 5),(Jogador (1,13) C 5 5 5)] [DisparoLaser 1 (2,2) C,DisparoCanhao 0 (14,16) B ] ) 


-- * funções principais da Tarefa 2.
--
-- | Efetua uma jogada.
jogada :: Int -- ^ O identificador do 'Jogador' que efetua a jogada.
       -> Jogada -- ^ A 'Jogada' a efetuar.
       -> Estado -- ^ O 'Estado' anterior.
       -> Estado -- ^ O 'Estado' resultante após o jogador efetuar a jogada.
jogada a b e@(Estado i o p)  
 |b == (Movimenta C) =if (vidasJogador(auxSelpos a o) <=0 ) then (Estado i o p) else if (direcaoJogador (auxSelpos a o ) /= C ) then Estado i (substituiJogador a (mudaDirecaoJog b (sofredisparo (auxSelpos a o) (verificaParedeC  a (auxSelpos a o) (Estado i o p) )))  o ) p else if (verificaChoques a (auxSelpos a o) e==False) then (verificaParedeC  a (auxSelpos a o) (Estado i o p) ) else e
 |b == (Movimenta B) =if (vidasJogador(auxSelpos a o) <=0 )then (Estado i o p) else if (direcaoJogador (auxSelpos a o ) /= B ) then Estado i (substituiJogador a (mudaDirecaoJog b (sofredisparo (auxSelpos a o) (verificaParedeB  a (auxSelpos a o) (Estado i o p) )))  o ) p  else if (verificaChoques a (auxSelpos a o) e==False) then (verificaParedeB  a (auxSelpos a o) (Estado i o p) ) else e
 |b == (Movimenta E) =if (vidasJogador(auxSelpos a o) <=0 )then (Estado i o p) else if (direcaoJogador (auxSelpos a o ) /= E ) then Estado i (substituiJogador a (mudaDirecaoJog b (sofredisparo (auxSelpos a o) (verificaParedeE  a (auxSelpos a o) (Estado i o p) )))  o ) p  else if (verificaChoques a (auxSelpos a o) e==False) then (verificaParedeE  a (auxSelpos a o) (Estado i o p) ) else e
 |b == (Movimenta D) =if (vidasJogador(auxSelpos a o) <=0 )then (Estado i o p) else if (direcaoJogador (auxSelpos a o ) /= D ) then Estado i (substituiJogador a (mudaDirecaoJog b (sofredisparo (auxSelpos a o) (verificaParedeD  a (auxSelpos a o) (Estado i o p) )))  o ) p  else if (verificaChoques a (auxSelpos a o) e==False) then (verificaParedeD  a (auxSelpos a o) (Estado i o p) ) else e
 |b == (Dispara Canhao) = if( vpd (auxSelpos a o) b==False ) then (Estado i o p) else jogadorDispara a Canhao (auxSelpos a o) (Estado i o p) 
 |b == (Dispara Laser) = if(vpd (auxSelpos a o) b)==False then (Estado i o p) else (jogadorDispara a Laser (removeTiro b (auxSelpos a o) ) (Estado i (substituiJogador a (removeTiro b (auxSelpos a o) ) o ) p))
 |b == (Dispara Choque) = if(vpd (auxSelpos a o) b)==False then (Estado i o p) else (jogadorDispara a Choque (removeTiro b (auxSelpos a o) )  (Estado i (substituiJogador a (removeTiro b (auxSelpos a o) ) o ) p)) 
  
-- ^ <<blob:null/54a26751-40b9-4abd-8758-c61496a9202a>>

-- | Funcao auxiliar que dado uma 'Jogada' (do tipo Movimenta C) muda o 'Jogador'.
verificaParedeC :: Int->Jogador->Estado->Estado
verificaParedeC a  (Jogador (x,y) t l b c) (Estado i h p) 
 | l > 0 && (encpm (x-1,y) i) == Vazia && (encpm (x-1,y+1) i) == Vazia && (t == C) && (verificaTank(Jogador (x,y) t a b c) (Estado i h p) == False ) = (Estado i (substituiJogador a (Jogador (x-1,y) C l b c) h ) p) --verifica tanks
 | l > 0 && (encpm (x-1,y) i) == Vazia && (encpm (x-1,y+1) i) == Vazia && (t /= C) = (Estado i (substituiJogador a (Jogador (x,y) C l b c) h ) p) --verifica tanks
 | otherwise = (Estado i h p)

-- | Funcao auxiliar que dado uma 'Jogada' (do tipo Movimenta B) muda o 'Jogador'.
verificaParedeB :: Int->Jogador->Estado->Estado
verificaParedeB a  (Jogador (x,y) t l b c) (Estado i h p) 
 | l > 0 && (encpm (x+2,y) i) == Vazia && (encpm (x+2,y+1) i) == Vazia && (t == B) &&  (verificaTank(Jogador (x,y) t a b c) (Estado i h p) == False ) = (Estado i (substituiJogador a (Jogador (x+1,y) B l b c) h ) p) --verifica tanks
 | l > 0 &&(encpm (x+2,y) i) == Vazia && (encpm (x+2,y+1) i) == Vazia && (t /= B) = (Estado i (substituiJogador a (Jogador (x,y) B l b c) h ) p) --verifica tanks
 | otherwise = (Estado i h p) 

-- | Funcao auxiliar que dado uma 'Jogada' (do tipo Movimenta E) muda o 'Jogador'.
verificaParedeE :: Int->Jogador->Estado->Estado
verificaParedeE a  (Jogador (x,y) t l b c) (Estado i h p) 
 | l > 0 && (encpm (x,y-1) i) == Vazia && (encpm (x+1,y-1) i) == Vazia && (t == E) && (verificaTank(Jogador (x,y) t a b c) (Estado i h p) == False ) =(Estado i (substituiJogador a (Jogador (x,y-1) E l b c) h ) p) --verifica tanks
 | l > 0 && (encpm (x,y-1) i) == Vazia && (encpm (x+1,y-1) i) == Vazia && (t /= E) = (Estado i (substituiJogador a (Jogador (x,y) E l b c) h ) p) --verifica tanks
 | otherwise = (Estado i h p)

-- | Funcao auxiliar que dado uma 'Jogada' (do tipo Movimenta D) muda o 'Jogador'.
verificaParedeD :: Int->Jogador->Estado->Estado
verificaParedeD a  (Jogador (x,y) t l b c) (Estado i h p) 
 | l > 0 && (encpm (x,y+2) i) == Vazia && (encpm (x+1,y+2) i) == Vazia && (t == D) && (verificaTank(Jogador (x,y) t a b c) (Estado i h p) == False ) =(Estado i (substituiJogador a (Jogador (x,y+1) D l b c) h ) p) --verifica tanks
 | l > 0 && (encpm (x,y+2) i) == Vazia && (encpm (x+1,y+2) i) == Vazia && (t /= D) = (Estado i (substituiJogador a (Jogador (x,y) D l b c) h ) p) --verifica tanks
 | otherwise = (Estado i h p)

--Direita    Esquerda   Cima     Baixo
--(x,y+2)   (x,y-1)    (x-1,y)   (x+2,y)
--(x+1,y+2) (x+1,y-1)  (x-1,y+1) (x+2,y+1)

-- | Devolve o elemento numa dada 'Posicao' de um 'Mapa'.
encpm::Posicao->Mapa->Peca
encpm (x,y) (h:hs) = head(snd(splitAt y (head(snd(splitAt x (h:hs) ))) ) )

-- | Funcao que dado a 'Posicao' e uma lista de jogadores retorna o 'Jogador' nessa 'Posicao'.Se nao existir cria-se um por defeito na 'Posicao' (1,1) com 'Direcao' inicial Cima, 5 vidas,3 lasers e 3 choques.
auxSelpos::Int->[Jogador]->Jogador
auxSelpos _ [] = (Jogador (1,1) C 5 3 3) 
auxSelpos 0 (h:t) = h
auxSelpos b (h:t) = auxSelpos (b-1) t


-- | Esta funcao substitui um 'Jogador' na lista dado o indice onde se encontra.
substituiJogador::Int ->Jogador->[Jogador]->[Jogador]
substituiJogador _ b [] = []
substituiJogador 0 b (h:t)= (b:t)
substituiJogador a b (h:t)=[h]++ substituiJogador (a-1) b t

removeJogador::Int ->[Jogador]->[Jogador]
removeJogador a []= [] 
removeJogador a (h:hs) 
 |a==0 = hs
 |otherwise = [h] ++ removeJogador (a-1) hs

-- | Verifica se o 'Jogador' chocou contra um 'Disparo' e caso seja verdade retira 1 vida ao 'Jogador'.
posicaoDisparoo::Jogador->Disparo->Jogador
posicaoDisparoo (Jogador (x,y) t a b c) d |( d==(DisparoCanhao (jogadorDisparo d) (posicaoDisparo d) (direcaoDisparo d) ) || (d==DisparoLaser (jogadorDisparo d) (posicaoDisparo d) (direcaoDisparo d))) && (x,y) ==(posicaoDisparo d) = (Jogador (x,y) t (a-1) b c) 
                                          |otherwise = (Jogador (x,y) t a b c) 
                                          
-- | Verifica se um 'Jogador' sofre um 'Disparo' e tira a vida ao mesmo.
sofredisparo::Jogador->Estado->Jogador
sofredisparo a (Estado i o []) = a
sofredisparo a (Estado i o (h:hs)) =a

-- | Funcao que, dado o id do 'Jogador' e o 'Jogador', cria um disparo.
criaDisparo::Int->Jogador-> Arma->Disparo
criaDisparo id (Jogador (x,y) C a b c) Canhao =DisparoCanhao id (x-1,y) C
criaDisparo id (Jogador (x,y) B a b c) Canhao =DisparoCanhao id (x+1,y) B
criaDisparo id (Jogador (x,y) E a b c) Canhao =DisparoCanhao id (x,y-1) E
criaDisparo id (Jogador (x,y) D a b c) Canhao =DisparoCanhao id (x,y+1) D
criaDisparo id (Jogador (x,y) C a b c) Laser =DisparoLaser id (x-1,y) C
criaDisparo id (Jogador (x,y) B a b c) Laser =DisparoLaser id (x+1,y) B
criaDisparo id (Jogador (x,y) E a b c) Laser =DisparoLaser id (x,y-1) E
criaDisparo id (Jogador (x,y) D a b c) Laser =DisparoLaser id (x,y+1) D
criaDisparo id (Jogador (x,y) t a b c) Choque =DisparoChoque id tempoDisparoChoque

-- | Variavel que decide o tempo 'Choque' (segundo enunciado).
tempoDisparoChoque=5

-- | 'substituiJogador' a 'sofredisparo' o.
jogadorDispara::Int->Arma->Jogador->Estado->Estado
jogadorDispara id arm jog (Estado i o [])=(Estado i o [(criaDisparo id jog arm)])
jogadorDispara id arm jog (Estado i o (h:t))=(Estado i o (criaDisparo id jog arm:h:t) ) 

-- | remove tiro ao 'Jogador'.
removeTiro::Jogada->Jogador->Jogador
removeTiro (Dispara Canhao) jog = jog
removeTiro (Dispara Choque) jog = jog{choquesJogador= (choquesJogador jog -1)}
removeTiro (Dispara Laser) jog = jog{lasersJogador= (lasersJogador jog -1)}

-- | Dá-nos a lista de 'Jogador'es vivos.
morteJogador:: [Jogador]->[Jogador]
morteJogador [] = []
morteJogador (h:hs) | vidasJogador h == 0 = morteJogador hs
                    | otherwise = h:morteJogador hs
-- | Funcao auxiliar para aplicar a funcao 'morteJogador' a um 'Estado' .Retorna o 'Estado' resultante.
verificaMortes::Estado->Estado
verificaMortes (Estado i h p) = (Estado i (morteJogador h) p)

-- | Dada uma 'Jogada' e um 'Jogador', retorna um 'Jogador' com nova direção.     
mudaDirecaoJog::Jogada->Jogador->Jogador
mudaDirecaoJog (Movimenta C) (Jogador (x,y) t a b c) = (Jogador (x,y) C a b c)
mudaDirecaoJog (Movimenta B) (Jogador (x,y) t a b c) = (Jogador (x,y) B a b c)
mudaDirecaoJog (Movimenta E) (Jogador (x,y) t a b c) = (Jogador (x,y) E a b c)
mudaDirecaoJog (Movimenta D) (Jogador (x,y) t a b c) = (Jogador (x,y) D a b c)
mudaDirecaoJog _ (Jogador (x,y) t a b c) = (Jogador (x,y) t a b c)

-- | 'verificaTank' verifica, dado um 'Jogador' e um 'Estado', se tem outro jogador nas redondezas.
verificaTank :: Jogador->Estado->Bool
verificaTank (Jogador (x,y) t a b c) (Estado i [] p) = False
verificaTank (Jogador (x,y) C a b c) (Estado i (h:hs) p) = let (x1,y1)=posicaoJogador h in (if ((x1==x-2 && y1==y)||(x1==x-2 && y1==y+1)||(x1==x-2 && y1==y-1)) && (vidasJogador h > 0) then True else (verificaTank (Jogador (x,y) C a b c) (Estado i hs p)) )       
verificaTank (Jogador (x,y) B a b c) (Estado i (h:hs) p) = let (x1,y1)=posicaoJogador h in (if ((x1==x+2 && y1==y)||(x1==x+2 && y1==y+1)||(x1==x+2 && y1==y-1)) && (vidasJogador h > 0) then True else (verificaTank (Jogador (x,y) B a b c) (Estado i hs p)) )
verificaTank (Jogador (x,y) E a b c) (Estado i (h:hs) p) = let (x1,y1)=posicaoJogador h in (if ((x1==x && y1==y-2)||(x1==x+1 && y1==y-2)||(x1==x-1 && y1==y-2)) && (vidasJogador h > 0) then True else (verificaTank (Jogador (x,y) E a b c) (Estado i hs p)) )
verificaTank (Jogador (x,y) D a b c) (Estado i (h:hs) p) = let (x1,y1)=posicaoJogador h in (if ((x1==x && y1==y+2)||(x1==x+1 && y1==y+2)||(x1==x-1 && y1==y+2)) && (vidasJogador h > 0) then True else (verificaTank (Jogador (x,y) D a b c) (Estado i hs p)) )

-- | 'verificaChoques' verifica se um 'Jogador' esta imobilizado por 'Choque'.
-- = Se for 'False' o jogador nao esta imobilizado caso contrario esta imobilizado.
verificaChoques ::Int-> Jogador->Estado->Bool
verificaChoques q (Jogador (x,y) t a b c) (Estado i o []) = False
verificaChoques q (Jogador (x,y) t a b c) (Estado i [] p) = False
verificaChoques q (Jogador (x,y) t a b c) e@(Estado i o p) = if(areaChoque2 (x,y) (removeJogador q o) ==(Jogador (5000,500) C 2 2 2))then False else auxSelpos (jogDisposi 0 (areaChoque2 (x,y) (removeJogador q o)) o) o `elem` ajudaChoque (tipoDisparoChoque p) e

-- |Dado um 'Jogador' danos o seu id!
jogDisposi::Int->Jogador->[Jogador]->Int
jogDisposi _ a [] = 0
jogDisposi n a (h:hs)=if (a==h)then n else jogDisposi (n+1) a hs

-- | Danos a lista de 'Jogador'es que dipararam. 
ajudaChoque::[Int]->Estado->[Jogador]
ajudaChoque [] _ = []
ajudaChoque (h:hs) e@(Estado i o p) = [auxSelpos h o]++ajudaChoque hs e
-- | Área de posições que quando ocupadas são afetadas por 'disparoChoque' que afetam tanks.
areaChoque2 :: Posicao->[Jogador]->Jogador
areaChoque2 (x,y) [] = (Jogador (5000,500) C 2 2 2) -- Possivel andar
areaChoque2 (x1,y1) (h:hs) = if ((x1-fst(posicaoJogador h))^2)<=9 && ((y1-snd(posicaoJogador h))^2)<=9 then h else (areaChoque2 (x1,y1) hs)


-- | De uma lista de 'Disparo's retêm apenas os 'disparoChoque' que, por sua vez, mostrarão a identificação dos 'Jogador'es que usaram 'disparoChoque'.
tipoDisparoChoque :: [Disparo] -> [Int] 
tipoDisparoChoque [] = []
tipoDisparoChoque (h@(DisparoChoque a b) :hs) = a:tipoDisparoChoque hs
tipoDisparoChoque ( _ : hs) = tipoDisparoChoque hs  

-- |Indica as posições dos 'Jogador'es do mapa
posicoesJog::[Jogador]->[Posicao]
posicoesJog [] = []
posicoesJog (h:hs)=[posicaoJogador h]++ posicoesJog hs

-- | Verifica se um 'Disparo' bate contra algum 'Jogador'(True-bate. False-não bate).
vTankDispL :: Disparo->Estado->Bool
vTankDispL  (DisparoLaser a (x,y) C) (Estado i [] p) = False
vTankDispL  (DisparoLaser a (x,y) B) (Estado i [] p) = False
vTankDispL  (DisparoLaser a (x,y) E) (Estado i [] p) = False
vTankDispL  (DisparoLaser a (x,y) D) (Estado i [] p) = False
vTankDispL  (DisparoLaser a (x,y) C) (Estado i (h@(Jogador (x1,y1) t l k j):hs) p) = let (x2,y2)=ateLaserDispC (x,y) (Estado i (h:hs)  p)  in (if (x1<=x || x1 >=x2) && (y1==x) then True else  (vTankDispL (DisparoLaser a (x,y) C) (Estado i hs p) ) )
vTankDispL  (DisparoLaser a (x,y) B) (Estado i (h@(Jogador (x1,y1) t l k j):hs) p) = let (x2,y2)=ateLaserDispB (x,y) (Estado i (h:hs)  p)  in (if (x1>=x || x1 <=x2) && (y1==x) then True else  (vTankDispL (DisparoLaser a (x,y) B) (Estado i hs p) ) )
vTankDispL  (DisparoLaser a (x,y) E) (Estado i (h@(Jogador (x1,y1) t l k j):hs) p) = let (x2,y2)=ateLaserDispE (x,y) (Estado i (h:hs)  p)  in (if (y1<=y || y1 >=y2) && (x1==x) then True else  (vTankDispL (DisparoLaser a (x,y) E) (Estado i hs p) ) )
vTankDispL  (DisparoLaser a (x,y) D) (Estado i (h@(Jogador (x1,y1) t l k j):hs) p) = let (x2,y2)=ateLaserDispD (x,y) (Estado i (h:hs)  p)  in (if (y1>=y || y1 <=y2) && (x1==x) then True else  (vTankDispL (DisparoLaser a (x,y) D) (Estado i hs p) ) )

-- | verifica se foi efetuado um 'disparoCanhao'.
vTankDispCo :: Disparo->Estado->Bool
vTankDispCo  (DisparoCanhao a (x,y) c) (Estado i [] p)= False
vTankDispCo  (DisparoCanhao a (x,y) c) (Estado i (h:hs) p) = let (x1,y1)=posicaoJogador h in (if ( x1==x && y==y1 ) then True else vTankDispCo  (DisparoCanhao a (x,y) c) (Estado i (hs) p) )

-- | verifica se os 'Disparo's bateram contra algo e remove-os.
verificaDisparosT::Estado->[Disparo]
verificaDisparosT (Estado i h [] ) = [] 
verificaDisparosT (Estado i h (p@(DisparoCanhao g k q ):ps)) = if(vTankDispCo p (Estado i h (ps)) ==True) then verificaDisparosT (Estado i h ps) else [p]++verificaDisparosT(Estado i h ps)
verificaDisparosT (Estado i h (p@(DisparoLaser g k q ):ps)) = if (vTankDispL p (Estado i h (ps)) ==True) then verificaDisparosT (Estado i h ps) else [p]++verificaDisparosT(Estado i h ps)
verificaDisparosT (Estado i h (p@(DisparoChoque g k ):ps))=[p]++verificaDisparosT(Estado i h ps) 

-- | Remove um 'Disparo' quando  bate contra algum objeto.É aplicavel a um estado e retorna o estado final.
verificaDisparoFinal::Estado->Estado
verificaDisparoFinal (Estado i h o )=Estado i h (verificaDisparosT (Estado i h o) )

-- | Verifica se o 'Jogador' pode disparar.
vpd :: Jogador->Jogada->Bool
vpd  jog j | j==(Dispara Choque) && (choquesJogador jog)>0  && (vidasJogador jog > 0)= True
           | j==(Dispara Laser) && (lasersJogador jog)>0 &&(vidasJogador jog > 0)= True
           | j==(Dispara Canhao) && (vidasJogador jog > 0) = True
           | otherwise = False

-- | Devolve 'Posicao' final em um 'laser' com direção para cima.
ateLaserDispC :: Posicao->Estado->Posicao
ateLaserDispC (x1,y1) (Estado i o s) | (encpm (x1,y1) i /=(Bloco Indestrutivel) && encpm (x1,y1+1) i /=(Bloco Indestrutivel) ) =  ateLaserDispC (x1-1,y1) (Estado i o s)
ateLaserDispC (x1,y1) (Estado i o s) | (encpm (x1,y1) i == (Bloco Indestrutivel) || encpm (x1,y1+1) i ==(Bloco Indestrutivel) ) =(x1,y1)  

-- | Devolve 'Posicao' final em um 'laser' com direção para baixo.
ateLaserDispB :: Posicao->Estado->Posicao
ateLaserDispB (x1,y1) (Estado i o s) | (encpm (x1+1,y1) i /=(Bloco Indestrutivel) && encpm (x1+1,y1+1) i /=(Bloco Indestrutivel) ) =  ateLaserDispB (x1+1,y1) (Estado i o s)
ateLaserDispB (x1,y1) (Estado i o s) | (encpm (x1+1,y1) i == (Bloco Indestrutivel) || encpm (x1+1,y1+1) i ==(Bloco Indestrutivel) ) =(x1,y1)  

-- | Devolve 'Posicao' final em um 'laser' com direção para a esquerda.
ateLaserDispE :: Posicao->Estado->Posicao
ateLaserDispE (x1,y1) (Estado i o s) | (encpm (x1+1,y1) i /=(Bloco Indestrutivel) && encpm (x1,y1) i /=(Bloco Indestrutivel) ) =  ateLaserDispE (x1,y1-1) (Estado i o s)
ateLaserDispE (x1,y1) (Estado i o s) | (encpm (x1+1,y1) i == (Bloco Indestrutivel) || encpm (x1,y1) i ==(Bloco Indestrutivel) ) =(x1,y1)  

-- | Devolve 'Posicao' final em um 'laser' com direção para a direita.
ateLaserDispD :: Posicao->Estado->Posicao
ateLaserDispD (x1,y1) (Estado i o s) | (encpm (x1,y1+1) i /=(Bloco Indestrutivel) && encpm (x1+1,y1+1) i /=(Bloco Indestrutivel) ) =  ateLaserDispD (x1,y1+1) (Estado i o s)
ateLaserDispD (x1,y1) (Estado i o s) | (encpm (x1,y1+1) i == (Bloco Indestrutivel) || encpm (x1+1,y1+1) i ==(Bloco Indestrutivel) ) =(x1,y1)  


-- |verifica se um jogador tem um disparo laser ativo .
dispLaseactiv::Int->Jogador->[Disparo]->Bool
dispLaseactiv a jog  []= False
dispLaseactiv a jog  (h:hd) = if (h== (DisparoLaser a (posicaoDisparo h) (direcaoDisparo h)) ) then True else dispLaseactiv a jog  hd 

