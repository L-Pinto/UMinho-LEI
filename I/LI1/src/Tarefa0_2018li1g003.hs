-- | Este módulo define funções genéricas sobre vetores e matrizes, que serão úteis na resolução do trabalho prático.
module Tarefa0_2018li1g003 where

import LI11819

-- * Funções não-recursivas.

-- | Um 'Vetor' é uma 'Posicao' em relação à origem.
type Vetor = Posicao
-- ^ <<http://oi64.tinypic.com/mhvk2x.jpg vetor>>

-- exemplos de vetores.
vetor1=(2,3) :: Vetor
vetor2=(4,1) :: Vetor 

-- ** Funções sobre vetores

-- *** Funções gerais sobre 'Vetor'es.

-- | Soma dois 'Vetor'es.
somaVetores :: Vetor -> Vetor -> Vetor
somaVetores (l1,c1) (l2,c2)= (l1+l2,c1+c2)

-- | Subtrai dois 'Vetor'es.
subtraiVetores :: Vetor -> Vetor -> Vetor
subtraiVetores (l1,c1) (l2,c2) = (l1-l2,c1-c2)

-- | Multiplica um escalar por um 'Vetor'.
multiplicaVetor :: Int -> Vetor -> Vetor
multiplicaVetor a (l1,c1)=(a*l1,a*c1) 

-- | Roda um 'Vetor' 90º no sentido dos ponteiros do relógio, alterando a sua direção sem alterar o seu comprimento (distânciaa à origem).
--
-- <<http://oi65.tinypic.com/2j5o268.jpg rodaVetor>>
rodaVetor :: Vetor -> Vetor
rodaVetor (l1,c1) = (c1,-l1)

-- | Espelha um 'Vetor' na horizontal (sendo o espelho o eixo vertical).
--
-- <<http://oi63.tinypic.com/jhfx94.jpg inverteVetorH>>
inverteVetorH :: Vetor -> Vetor
inverteVetorH (l1,c1)= (l1,-c1)

-- | Espelha um 'Vetor' na vertical (sendo o espelho o eixo horizontal).
--
-- <<http://oi68.tinypic.com/2n7fqxy.jpg inverteVetorV>>
inverteVetorV :: Vetor -> Vetor
inverteVetorV (l1,c1)= (-l1,c1)
                    

-- *** Funções do trabalho sobre 'Vetor'es.

-- | Devolve um 'Vetor' unitário (de comprimento 1) com a 'Direcao' dada.
direcaoParaVetor :: Direcao -> Vetor
direcaoParaVetor C = (-1,0) 
direcaoParaVetor B = (1,0)
direcaoParaVetor E = (0,-1)
direcaoParaVetor D = (0,1)

-- ** Funções sobre listas

-- *** Funções gerais sobre listas.
--
-- Funções não disponíveis no 'Prelude', mas com grande utilidade.

-- | Verifica se o indice pertence à lista.
eIndiceListaValido :: Int -> [a] -> Bool
eIndiceListaValido a h |(length h) <= a=False
                       |a<0 = False
                       |otherwise = True


-- ** Funções sobre matrizes.

-- *** Funções gerais sobre matrizes.

-- | Uma matriz é um conjunto de elementos a duas dimensões.
--
-- Em notação matemática, é geralmente representada por:
--
-- <<https://upload.wikimedia.org/wikipedia/commons/d/d8/Matriz_organizacao.png matriz>>
type Matriz a = [[a]]

-- | Calcula a dimensão de uma matriz.
dimensaoMatriz :: Matriz a -> Dimensao
dimensaoMatriz [[]] = (0,0)
dimensaoMatriz [] =(0,0)
dimensaoMatriz (h:t) |(length h <=0)=(0,0)
                     |otherwise=( (length (t) )+1,(length h) )

-- | Verifica se a posição pertence à matriz.
ePosicaoMatrizValida :: Posicao -> Matriz a -> Bool 
ePosicaoMatrizValida (l1,c1) a = let (k,l) = dimensaoMatriz(a) in (if (l1<k && c1<l) && (l1>=0 && c1>=0)  then True  else False )

-- | Verifica se a posição está numa borda da matriz.
eBordaMatriz :: Posicao -> Matriz a -> Bool

--eBordaMatriz c a |(ePosicaoMatrizValida c a == False) = False 
eBordaMatriz (_,0) a = True
eBordaMatriz (0,_) a = True 
eBordaMatriz (l,b) a= let (h,t) = dimensaoMatriz(a) in ( if ( l==(h-1) || b==(t-1) ) && (l>=0 && b>=0) then True else False)


-- *** Funções do trabalho sobre matrizes.

-- | Converte um 'Tetromino' (orientado para cima) numa 'Matriz' de 'Bool'.
--
-- <<http://oi68.tinypic.com/m8elc9.jpg tetrominos>>
tetrominoParaMatriz :: Tetromino -> Matriz Bool
tetrominoParaMatriz I = [[False,True,False,False],[False,True,False,False],[False,True,False,False],[False,True,False,False]]
tetrominoParaMatriz J = [[False,True,False],[False,True,False],[True,True,False]]
tetrominoParaMatriz L = [[False,True,False],[False,True,False],[False,True,True]]
tetrominoParaMatriz O = [[True,True],[True,True]]
tetrominoParaMatriz S = [[False,True,True],[True,True,False],[False,False,False]]
tetrominoParaMatriz T = [[False,False,False],[True,True,True],[False,True,False]]
tetrominoParaMatriz Z = [[True,True,False],[False,True,True],[False,False,False]]


-- * Funções recursivas.

-- ** Funções sobre listas.
--
-- Funções não disponíveis no 'Prelude', mas com grande utilidade.

-- | Devolve o elemento num dado índice de uma lista.
encontraIndiceLista :: Int -> [a] -> a
encontraIndiceLista  0 (h:t) = h
encontraIndiceLista  i (h:t) = encontraIndiceLista (i-1) t

-- | Modifica um elemento num dado índice.
--
-- __NB:__ Devolve a própria lista se o elemento não existir.
atualizaIndiceLista :: Int -> a -> [a] -> [a]
atualizaIndiceLista _ e [] = [] 
atualizaIndiceLista 0 e (h:t) = (e:t)
atualizaIndiceLista i e (h:t) = [h] ++ atualizaIndiceLista (i-1) e t

-- ** Funções sobre matrizes.

-- | Roda uma 'Matriz' 90º no sentido dos ponteiros do relógio.
--
-- <<http://oi68.tinypic.com/21deluw.jpg rodaMatriz>>
rodaMatriz :: Matriz a -> Matriz a
rodaMatriz [] = []
rodaMatriz ([]:_) = []
rodaMatriz h = [aux h] ++ rodaMatriz (tt h)

-- | Funções auxilares para a função 'rodaMatriz'.

-- | Inverte a ordem dos elementos da primeira coluna de uma matriz transformando-a, assim, em uma linha para a 'rodaMatriz'.
aux:: Matriz a -> [a]
aux [] = []
aux ([]:_) = []
aux (h:t) =  aux t ++ [head h] 

-- | Remove a primeira coluna de uma matriz e devolve uma nova matriz, isto é, sem a respetiva coluna.
tt:: Matriz a -> Matriz a
tt [] = []
tt ([h])= [tail h]
tt (h:t)= [tail h]++ tt t

-- | Inverte uma 'Matriz' na horizontal.
--
-- <<http://oi64.tinypic.com/iwhm5u.jpg inverteMatrizH>>
inverteMatrizH :: Matriz a -> Matriz a
inverteMatrizH []= []
inverteMatrizH (h:hs) =[reverse h] ++inverteMatrizH hs

-- | Inverte uma 'Matriz' na vertical.
--
-- <<http://oi64.tinypic.com/11l563p.jpg inverteMatrizV>>
inverteMatrizV :: Matriz a -> Matriz a
inverteMatrizV []=[]
inverteMatrizV (h:t)= inverteMatrizV t ++ [h]

-- | Cria uma nova 'Matriz' com o mesmo elemento.
criaMatriz :: Dimensao -> a -> Matriz a
criaMatriz (0,n) p = []
criaMatriz (m,n) p = [linha (m,n) p]++criaMatriz (m-1,n) p

-- | Cria uma linha com o mesmo elemento.   
linha:: Dimensao -> a -> [a]
linha (p,0) a = []
linha (p,x) a = [a] ++ linha (p,x-1) a

-- | Devolve o elemento numa dada 'Posicao' de uma 'Matriz'.
encontraPosicaoMatriz :: Posicao -> Matriz a -> a
encontraPosicaoMatriz (0,y) (h:hs)= procuraLinha (1,y) h
encontraPosicaoMatriz (x,y) (h:hs) = encontraPosicaoMatriz (x-1,y) hs

-- | Devolve o elemento numa dada 'Posicao' de uma linha.
procuraLinha:: Posicao -> [a]-> a 
procuraLinha (a,0) (h:hs) = h
procuraLinha (a,b) (h:hs) =  procuraLinha (a,b-1) hs

-- | Modifica um elemento numa dada 'Posicao' de uma 'Matriz'.
--
-- __NB:__ Devolve a própria 'Matriz' se o elemento não existir.

atualizaPosicaoMatriz :: Posicao -> a -> Matriz a -> Matriz a
atualizaPosicaoMatriz (a,b) c (h:hs) | (ePosicaoMatrizValida (a,b) (h:hs) == False) = []
                                     | (a==0) = (modificaLinha (1,b) c h ):hs
                                     | otherwise = [h] ++ atualizaPosicaoMatriz (a-1,b) c hs

-- | Modifica um elemento numa dada 'Posicao' de uma linha.  
modificaLinha:: Posicao -> a -> [a] -> [a] 
modificaLinha (a,0) c (h:hs) = (c:hs)
modificaLinha (a,b) c (h:hs) = [h]++ modificaLinha (a,b-1) c hs
