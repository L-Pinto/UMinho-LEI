
{- | Este módulo define funções comuns da Tarefa 6 do trabalho prático.
 = Tarefa 6 - Comprimir o estado do jogo
 
 == Introdução

O principal desafio desta Tarefa era conseguir implementar um bot no qual tivesse uma AI(inteligência artificial).
As estratégias usada foi dividir este processo em duas partes, a parte da defesa e a parte do ataque.
Para isso implementamos as seguintes funções da Tarefa6.
 
 == Discussão e Conclusão

Os resultados obtidos nesta Tarefa ,foram alcançados através da função principal 'bot'.
A ordem como chamamos as funções é muito importante ,uma vez que,devido a isso o 'bot' em certos estados do poderia ser mais eficar se trocarmos a ordem dessas funções) .
Com base na eficácia individual,ou seja,todas as funções chamadas na função 'bot' podemos constatar um bom funcionamento das mesmas, pois foram testadas ao pormenor individualmente.
-}

module Tarefa6_2018li1g003 where

import LI11819
import Tarefa2_2018li1g003
import Tarefa4_2018li1g003


-- | Define um ro'bot' capaz de jogar autonomamente o jogo.
bot :: Int          -- ^ O identificador do 'Jogador' associado ao ro'bot'.
    -> Estado       -- ^ O 'Estado' para o qual o ro'bot' deve tomar uma decisão.
    -> Maybe Jogada -- ^ Uma possível 'Jogada' a efetuar pelo ro'bot'.
bot a e 
 |vidasJogador(vejog a e) >0 && conterAtack a e == Nothing && atacataca a e ==Nothing && mToataca a e ==Nothing && detjogador a e ==Nothing && defmov a e==Nothing = dichoprev a e 
 |vidasJogador(vejog a e) >0 && conterAtack a e == Nothing && atacataca a e ==Nothing && mToataca a e ==Nothing && defmov a e==Nothing = detjogador a e 
 |vidasJogador(vejog a e) >0 && conterAtack a e == Nothing && atacataca a e ==Nothing && (mToataca a e ==Just(Dispara Laser) && lasersJogador(vejog a e)<=0 ) && defmov a e==Nothing = detjogador a e 
 |vidasJogador(vejog a e) >0 && conterAtack a e == Nothing && atacataca a e ==Nothing && defmov a e==Nothing = mToataca a e 
 |vidasJogador(vejog a e) >0 && conterAtack a e == Nothing && dj4 a e ==False = defmov a e
 |vidasJogador(vejog a e) >0 && conterAtack a e == Nothing   = atacataca a e
 |vidasJogador(vejog a e) >0 = conterAtack a e 
 |otherwise = Nothing




-- | Dado o Identificador do 'Jogador', retorna o 'Jogador'.
vejog::Int->Estado->Jogador
vejog a e@(Estado i (h:hs) p) = (!!) (h:hs) a

-- * FUNCOES DE ATAQUE
-- | Função usada para contar um 'Bloco Destrutivel'.
ind::Peca->Int
ind (Bloco Destrutivel) = 1
ind _ = 0

-- | Esta função conta o número de 'Bloco Destrutivel' de um 'Mapa'
contaBlocosDest::Estado->Int
contaBlocosDest (Estado [] o p)= 0
contaBlocosDest (Estado (x:xs) o p)=sum(map ind x)+contaBlocosDest (Estado xs o p)

-- | Quando um 'Jogador' está a uma distância menor que 3 blocos da-lhe um 'Choque'!
dichoprev::Int->Estado->Maybe Jogada
dichoprev a e@(Estado i [] p) = Nothing
dichoprev a e@(Estado i (x:xs) p) = 
 let (x1,y1)=posicaoJogador(vejog a e) 
     (x2,y2)= posicaoJogador x
  in (if (x1==x2 && y1==y2 && vidasJogador(vejog a e)>0 ) 
       then dichoprev a (Estado i xs p) 
      else if ( sqrt( fromIntegral((x2-x1)^2 +(y2-y1)^2))<=4  && vidasJogador x >0)
       then Just (Dispara Choque)
      else dichoprev a (Estado i xs p)) 
--(x2-3<=x1 && y1==y2) || (x2==x1 && y2-3==y1) || (x2+3==x1 && y1==y2) || (x2==x1 && y2+3==y1) ||(x2+3==x1 && y2+3==x1)--colocar menores e maiores

-- | Conta quantos pontos é possivel na 'Posicao' atual ('Bloco Destrutivel' e 'Jogador'es Destruidos, calcula se vale a pena 'Dispara'r um 'Laser' (prob>=5). Caso seja >=1 apenas 'Dispara Canhao'!)  
diLprev::Int->Estado->Maybe Jogada
diLprev a e@(Estado i o p) = 
 let d=direcaoJogador (vejog a e)
     (x,y)=posicaoJogador (vejog a e)
     lsd=lasersJogador(vejog a e)
      in(if lsd>0 &&(d==C) && (contaBDL (lpaplC (DisparoLaser a (x,y) C) (ateLaserDispC1 (x,y) e)) e + acertaJog a e)>= 5 then Just(Dispara Laser) 
         else if (d==C) && (contaBDL (lpaplC (DisparoLaser a (x,y) C) (ateLaserDispC1 (x,y) e)) e + acertaJog a e)>= 1 then Just(Dispara Canhao) 
         else if lsd>0 &&(d==B) && (contaBDL (lpaplB (DisparoLaser a (x,y) B) (ateLaserDispB1 (x,y) e)) e + acertaJog a e)>= 5 then Just(Dispara Laser) 
         else if (d==B) && (contaBDL (lpaplB (DisparoLaser a (x,y) B) (ateLaserDispB1 (x,y) e)) e + acertaJog a e)>= 1  then Just(Dispara Canhao) 
         else if lsd>0 &&(d==E) && (contaBDL (lpaplE (DisparoLaser a (x,y) E) (ateLaserDispE1 (x,y) e)) e + acertaJog a e)>= 5 then Just(Dispara Laser) 
         else if (d==E) && (contaBDL (lpaplE (DisparoLaser a (x,y) E) (ateLaserDispE1 (x,y) e)) e + acertaJog a e)>= 1  then Just(Dispara Canhao) 
         else if lsd>0 &&(d==D) && (contaBDL (lpaplD (DisparoLaser a (x,y) D) (ateLaserDispD1 (x,y) e)) e + acertaJog a e)>= 5  then Just(Dispara Laser) 
         else if (d==D) && (contaBDL (lpaplD (DisparoLaser a (x,y) D) (ateLaserDispD1 (x,y) e)) e + acertaJog a e)>= 1  then Just(Dispara Canhao) 
         else Nothing)

-- | Calcula se um 'Laser' acerta em um ou mais 'Jogador'es, retorna a pontuação.
acertaJog::Int->Estado->Int
acertaJog a e@(Estado i o p) =
     let d=direcaoJogador (vejog a e)
         (x,y)=posicaoJogador (vejog a e)
         in(if d==C then (length (vTankDispL1 (DisparoLaser a (x,y) C) e ) )*4 
            else if d==B then (length (vTankDispL1 (DisparoLaser a (x,y) B) e ) )*4 
            else if d==E then (length (vTankDispL1 (DisparoLaser a (x,y) E) e ) )*4 
            else if d==D then (length (vTankDispL1 (DisparoLaser a (x,y) D) e ) )*4  
            else 0)

-- | Conta os 'Bloco Destrutivel' de uma lista(lista esta que é supostamente por onde o 'Laser' vai passar se for 'Dispara'do),e retorna o número de pontos
contaBDL::[Posicao]->Estado->Int
contaBDL [] _ = 0
contaBDL (h:hs) e = if (encpm h (mapaEstado e)) == (Bloco Destrutivel) then 1+ contaBDL hs e else contaBDL hs e

-- |Dá-nos a melhor 'Direcao' para 'Dipara'r
dirToAtack::Int->Estado->Maybe Direcao
dirToAtack a e@(Estado i o p)
 |diLprev a (Estado i (substituiJogador a (mudaDirecaoJog (Movimenta C) (vejog a e)) o) p) ==Just (Dispara Laser) = Just C
 |diLprev a (Estado i (substituiJogador a (mudaDirecaoJog (Movimenta B) (vejog a e)) o) p) ==Just (Dispara Laser) = Just B
 |diLprev a (Estado i (substituiJogador a (mudaDirecaoJog (Movimenta E) (vejog a e)) o) p) ==Just (Dispara Laser) = Just E
 |diLprev a (Estado i (substituiJogador a (mudaDirecaoJog (Movimenta D) (vejog a e)) o) p) ==Just (Dispara Laser) = Just D
 |diLprev a (Estado i (substituiJogador a (mudaDirecaoJog (Movimenta C) (vejog a e)) o) p) ==Just (Dispara Canhao) = Just C
 |diLprev a (Estado i (substituiJogador a (mudaDirecaoJog (Movimenta B) (vejog a e)) o) p) ==Just (Dispara Canhao) = Just B
 |diLprev a (Estado i (substituiJogador a (mudaDirecaoJog (Movimenta E) (vejog a e)) o) p) ==Just (Dispara Canhao) = Just E
 |diLprev a (Estado i (substituiJogador a (mudaDirecaoJog (Movimenta D) (vejog a e)) o) p) ==Just (Dispara Canhao) = Just D 
 |otherwise = Nothing


 -- Dado o tipo 'Maybe Direcao' a retorna a 'Direcao'(Só vai ser chamada quando não dá 'Nothing').

-- |Dado um 'Maybe Direcao' converte para 'Direcao'.Garantimos que esta função só é usada quando o input é diferente de 'Nothing'.
normaltoMaybe::Maybe Direcao->Direcao
normaltoMaybe Nothing = C
normaltoMaybe (Just C) = C
normaltoMaybe (Just B) = B
normaltoMaybe (Just E) = E
normaltoMaybe (Just D) = D

-- | 'Movimenta' um 'Jogador' com base na melhor 'Direcao' para  atacar. 
atacataca::Int->Estado->Maybe Jogada
atacataca a e 
 |(dirToAtack a e /= Nothing) && (direcaoJogador (vejog a e) == normaltoMaybe(dirToAtack a e)) = (diLprev a e)
 |(dirToAtack a e /= Nothing) && (direcaoJogador (vejog a e) /= normaltoMaybe(dirToAtack a e)) = Just(Movimenta (normaltoMaybe(dirToAtack a e)) )
 |otherwise =Nothing



-- | Dá-nos todos os 'DisparoCanhao' de um 'Jogador'
allDipC::Int->Estado->[Disparo]
allDipC a (Estado i o [])= []
allDipC a (Estado i o (h@(DisparoLaser e r t):hs))=allDipC a (Estado i o hs)
allDipC a (Estado i o (h@(DisparoChoque r t):hs))=allDipC a (Estado i o hs)
allDipC a e@(Estado i o (h@(DisparoCanhao b r t):hs))=if a == b then [h]++allDipC a (Estado i o hs) else allDipC a (Estado i o hs)


-- | Deteta os 2 'DisparoCanhao' dados no input estão seguidos.
d2seg::Disparo->Disparo->Bool
d2seg (DisparoCanhao a (x1,y1) c) (DisparoCanhao d (x2,y2) f) 
 |c/=f =False
 |c==f && (x2==x1-1 || x1==x2-1)  && (y2==y1) =True
 |c==f && (x2==x1+1 || x1==x2+1)  && (y2==y1) =True
 |c==f && (y2==y1+1 || y1==y2+1)  && (x2==x1) =True
 |c==f && (y2==y1-1 || y1==y2-1)  && (x2==x1) =True
 |otherwise = False
d2seg _ _= False

-- |Deteta os 2 'DisparoCanhao' numa lista de ['Disparo'],e para isso usamos a função 'd2seg'.
is2Canhao::Disparo->[Disparo]->[Bool]
is2Canhao d []= []
is2Canhao d (h:hs) = filter (\x->x==True) ([d2seg d h]++is2Canhao d hs)

-- | is2Canhao (head(allDipC 0 e)) (allDipC 0 e)
movJog::Estado->Direcao->Jogador->Jogador
movJog e C j@(Jogador (x,y) t a b c)= if detetaP1 0 (x,y) e==True then (Jogador (x-1,y) C a b c) else j
movJog e B j@(Jogador (x,y) t a b c)= if detetaP1 1 (x,y) e==True then (Jogador (x+1,y) B a b c) else j
movJog e E j@(Jogador (x,y) t a b c)= if detetaP1 2 (x,y) e==True then (Jogador (x,y-1) E a b c) else j
movJog e D j@(Jogador (x,y) t a b c)= if detetaP1 3 (x,y) e==True then (Jogador (x,y+1) D a b c) else j

-- | Deteta se é possivel mover para 'C'ima ,'B'aixo,'E'squerda,'D'ireita.
detetaP1::Int->Posicao->Estado->Bool
detetaP1 i (x,y) e@(Estado (h:hs) o p) =
 if (encpm (x-1,y) (h:hs) )==Vazia && (encpm (x-1,y+1) (h:hs) )==Vazia && i==0 then True
 else if (encpm (x+2,y) (h:hs) )==Vazia && (encpm (x+2,y+1) (h:hs) )==Vazia && i==1 then True
 else if (encpm (x,y-1) (h:hs) )==Vazia && (encpm (x+1,y-1) (h:hs) )==Vazia && i==2 then True
 else if (encpm (x,y+2) (h:hs) )==Vazia && (encpm (x+1,y+2) (h:hs) )==Vazia && i==3 then True
 else False
-- | Dada uma ['Maybe Jogada'] escolhe a melhor 'Jogada' com preferência de 'Disparo's
melhorJogada::[Maybe Jogada]-> Maybe Jogada
melhorJogada [] = Nothing
melhorJogada p = let a =filter (\x ->(x ==Just(Dispara Laser ))) p 
                     m =filter (\x ->(x ==Just(Dispara Canhao))) p 
                     c =filter (\x ->(x /=Nothing)) p 
                 in (if head a==Just(Dispara Laser )then Just(Dispara Laser ) 
                    else if head m==Just(Dispara Canhao) then Just(Dispara Canhao) 
                    else if c /=[] then head c 
                    else Nothing)

-- | 'Movimenta' com base na melhor 'Jogada' para fazer mais pontos.
mToataca::Int->Estado->Maybe Jogada
mToataca a e@(Estado i o p) =  
 let j1=movJog e C (vejog a e)
     j2=movJog e B (vejog a e)
     j3=movJog e E (vejog a e)
     j4=movJog e D (vejog a e)
     u=(atacataca a (Estado i (substituiJogador a j1 o) p))
     d=(atacataca a (Estado i (substituiJogador a j2 o) p))
     l=(atacataca a (Estado i (substituiJogador a j3 o) p))
     r=(atacataca a (Estado i (substituiJogador a j4 o) p))
 in(if auxFra[u,d,l,r]==True then Nothing else melhorJogada  (auxmelhor (detetaPoss 0 (posicaoJogador (vejog a e)) e) [u,d,l,r] ) )

-- | ve a melhor jogada.
auxmelhor::[Maybe Jogada]->[Maybe Jogada]->[Maybe Jogada]
auxmelhor _ [] = []
auxmelhor [] _ = []
auxmelhor (h:hs) (h1:hs1)
 |h==Nothing =[h]++ auxmelhor hs hs1
 |h/=Nothing =[h1]++ auxmelhor hs hs1

-- | Testa se a lista de ['Maybe Jogada'] é constituida só por 'Nothing's.
auxFra::[Maybe Jogada]->Bool
auxFra [] = True
auxFra (h:hs)= if h==Nothing then auxFra hs else False

{-
--  Operador XOR.
xor::Bool -> Bool -> Bool
xor  x y =(x||y) && not(x&&y)

--  Aplica o operador XOR a uma ['Bool'].
xorL::[Bool]->Bool
xorL [] = False
xorL [a]= False
xorL (h:x:hs)=if xor h x==True then True else xorL (x:hs)
-}



-- * Funções de DEFESA
-- | Se um 'DisparoCanhao' estiver a 1 ou 2 bloco de distância e o 'bot' estiver na 'Direcao' oposta à do 'Disparo', 'Dispara Canhao'!!!!
conterAtack::Int->Estado->Maybe Jogada
conterAtack a e@(Estado i o []) = Nothing
conterAtack a e@(Estado i o (h@(DisparoChoque ca cb):hs) )=conterAtack a (Estado i o hs) 
conterAtack a e@(Estado i o (h:hs)) =
 let (x1,y1)=posicaoJogador (vejog a e)
     d1 = direcaoJogador (vejog a e)
     (x2,y2)=posicaoDisparo h
     d2 = direcaoDisparo h
     in (if (d1==B && d2==C &&  sqrt( fromIntegral((x2-x1)^2 +(y2-y1)^2 ))<=3 )then Just(Dispara Canhao)
         else if (d1==C && d2==B && sqrt( fromIntegral((x2-x1)^2 +(y2-y1)^2))<=3 )then Just(Dispara Canhao)
         else if (d1==E && d2==D && sqrt( fromIntegral((x2-x1)^2 +(y2-y1)^2))<=3 )then Just(Dispara Canhao)
         else if (d1==D && d2==E && sqrt( fromIntegral((x2-x1)^2 +(y2-y1)^2))<=3 )then Just(Dispara Canhao)
         else conterAtack a (Estado i o hs) )

-- | Deteta se um 'Jogador' está a 4 blocos de distância e se isso se verificar retorna 'True'.
dj4::Int->Estado->Bool
dj4 a e@(Estado i [] p )= False
dj4 a e@(Estado i (h:hs) p ) =
 let (x1,y1)=posicaoJogador (vejog a e)
     (x2,y2)=posicaoJogador h 
     vds=vidasJogador(vejog a e)
     in(if sqrt( fromIntegral((x2-x1)^2 +(y2-y1)^2 ))<=4 then True else dj4 (a-1) (Estado i hs p) )

-- | Detetar se é possivel 'Movimenta'r para uma 'Posicao'
detetaPoss::Int->Posicao->Estado->[Maybe Jogada]
detetaPoss i (x,y) e@(Estado (h:hs) o p) =
 if (encpm (x-1,y) (h:hs) )==Vazia && (encpm (x-1,y+1) (h:hs) )==Vazia && i==0 then [Just(Movimenta C)] ++ detetaPoss (i+1)(x,y) e
 else if (encpm (x+2,y) (h:hs) )==Vazia && (encpm (x+2,y+1) (h:hs) )==Vazia && i==1 then [Just(Movimenta B)] ++ detetaPoss (i+1)(x,y) e
 else if (encpm (x,y-1) (h:hs) )==Vazia && (encpm (x+1,y-1) (h:hs) )==Vazia && i==2 then [Just(Movimenta E)] ++ detetaPoss (i+1)(x,y) e
 else if (encpm (x,y+2) (h:hs) )==Vazia && (encpm (x+1,y+2) (h:hs) )==Vazia && i==3 then [Just(Movimenta D)] ++ detetaPoss (i+1)(x,y) e
 else [Nothing] 

-- | 'Movimenta' o 'bot' se não exitir nenhum 'Disparo' a 3 blocos do 'Jogador'.
defmov::Int->Estado->Maybe Jogada--n contorna obstaculos
defmov a e@(Estado i o []) = let ape=filter (\x->x /=Nothing)(detetaPoss 0 (posicaoJogador (vejog a e)) e) in(if ape==[] then Nothing else (head ape))
defmov a e@(Estado i o (h@(DisparoChoque ca cb):hs) )= defmov a (Estado i o hs)
defmov a e@(Estado i o (h:hs) )=
 let (x1,y1)=posicaoJogador (vejog a e)
     (x2,y2)=posicaoDisparo h
     dd=jogadorDisparo h
     in(if sqrt( fromIntegral((x2-x1)^2 +(y2-y1)^2 ))>=3 && (dd/=a)  then defmov a (Estado i o hs) else Nothing)

-- | Deteta um 'Jogador' e 'Dispara' se estiver a menos de 4 blocos.
detjogador::Int->Estado->Maybe Jogada
detjogador a e@(Estado i [] p)= Nothing
detjogador a e@(Estado i (h:hs) p) = 
 let (x1,y1)=posicaoJogador (vejog a e)
     (x2,y2)=posicaoJogador h 
     in(if (direcaoJogador (vejog a e) ==C) && (y1==y2) && (x2<x1) && sqrt( fromIntegral((x2-x1)^2 +(y2-y1)^2 ))<=4 then Just(Dispara Canhao)
        else if (direcaoJogador (vejog a e) ==B) && (y1==y2) && (x2>x1) && sqrt( fromIntegral((x2-x1)^2 +(y2-y1)^2 ))<=4 then Just(Dispara Canhao)
        else if (direcaoJogador (vejog a e) ==E) && (x1==x2) && (y2<y1) && sqrt( fromIntegral((x2-x1)^2 +(y2-y1)^2 ))<=4 then Just(Dispara Canhao)
        else if (direcaoJogador (vejog a e) ==D) && (x1==x2) && (y2>y1) && sqrt( fromIntegral((x2-x1)^2 +(y2-y1)^2 ))<=4 then Just(Dispara Canhao)
        else if (y1==y2) || (x1==x2) then Just(Movimenta (direcaoJogador h) )
        else detjogador a (Estado i hs p) )






