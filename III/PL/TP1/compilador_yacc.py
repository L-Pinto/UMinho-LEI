# calculadora_yacc.py
#
# Trabalho Prático 2

import ply.yacc as yacc
import sys
#get the token map form the lexer. this is important
from calculadora_lex import tokens 
from calculadora_lex import literals 

# Production rules -> Regras Producao

f = open("output.txt","w")


def p_Programa(p):
    "Programa : Declaracoes Comandos Funcoes"
    p[0] = p[1] + p[2] + p[3]


def p_Funcoes_Empty(p):
    "Funcoes : "
    p[0] = ""

def p_Funcoes_Funcoes(p):
   "Funcoes : Funcoes Funcao"
   p[0] = p[1] + "jump endFunction\n" + p[2] + "endFunction:\n"


def p_Declaracoes_Empty(p):
    "Declaracoes :  "
    p[0] = ""

def p_Declaracoes_Declaracao(p):
    "Declaracoes : Declaracoes Declaracao"
    p[0] = p[1] + p[2]


def p_Comandos_empty(p):
    "Comandos : "
    p[0] = ""

def p_Comandos_Comando(p):
    "Comandos : Comandos Comando"
    p[0] = p[1] + p[2]


def p_Comando_Atribuicao(p):
    "Comando : Atribuicao"
    p[0] = p[1]

def p_Comando_Condicao(p):
    "Comando : Condicao"
    p[0] = p[1]

def p_Comando_While(p):
    "Comando : While"
    p[0] = p[1]

def p_Comando_Print(p):
   "Comando : Print"
   p[0] = p[1]


# DECLARAR variáveis do tipo inteiro -----------------------------------------------------------
# regra de produçao para a leitura

def p_Declaracao_id(p):
    "Declaracao : INT id"
    n = len(p.parser.registers)
    p.parser.registers.update({p[2]: n})
    p[0] = "PUSHI 0" + "\n"
    
def p_Declaracao_atrib(p):
    "Declaracao : INT id '=' Exp"
    n = len(p.parser.registers)
    p.parser.registers.update({p[2]: n})
    p[0] = p[4] + "\n"

def p_Declaracao_read(p):
    "Declaracao : INT id '=' READ"
    n = len(p.parser.registers)
    p.parser.registers.update({p[2]: n})
    p[0] = "PUSHI 0\n" + "READ\n" + "ATOI\n" + "STOREG " + str(n) + "\n"


def p_Declaracao_funcao(p):
    "Declaracao : INT id '=' NOME"
    
    n = len(p.parser.registers)
    p.parser.registers.update({p[2]:n})

    prog = len(p.parser.funcao)
    p.parser.funcao.update({p[4]: prog})

    tam = len(p.parser.registers)
    p.parser.registers.update({p[4]:tam})

    p[0] = "PUSHI 0\n" + "PUSHI 0\n" + "jump subProgram" + str(prog) +"\n" + "final" + str(prog) +":\n" +"PUSHG " + str(tam) +"\n" + "STOREG " + str(n) + "\n"

# ATRIBUICAO  -----------------------------------------------------------

# regra de produçao para a atribuicao do valor de expressoes numericas a variaveis
def p_Atribuicao(p):
    "Atribuicao : id '=' Exp "
    n = p.parser.registers.get(p[1])
    if(n==None):
        p[0] = "ERRO: ID sem definicao\n"
    else:
        p[0] = p[3] + "STOREG " + str(n) + "\n"

def p_Atribuicao_read(p):
    "Atribuicao : id '=' READ "
    n = p.parser.registers.get(p[1])
    if(n==None):
        p[0] = "ERRO: ID sem defenicao\n"
        p.parser.s
    else:
        p[0] = "READ\n" + "ATOI\n" + "STOREG " + str(n) + "\n"

def p_Atribuicao_funcao(p):
    "Atribuicao : id '=' NOME"

    n = p.parser.registers.get(p[1])

    if(n==None):
        p[0] = "ERRO: ID sem defenicao\n"
    else:
        prog = len(p.parser.funcao)
        p.parser.funcao.update({p[3]:prog})

        tam = len(p.parser.registers)
        p.parser.registers.update({p[3]:tam})

        p[0] = "PUSHI 0\n" + "jump subProgram" + str(prog) +"\n" + "final" + str(prog) +":\n" + "PUSHG " + str(tam) + "\n" + "STOREG " + str(n) + "\n"

# DECLARAR funcoes de retorno de inteiros -----------------------------------------
def p_Funcao(p):
    "Funcao : NOME '{' Programa RETURN Exp '}' "

    n = p.parser.registers.get(p[1])
    prog = p.parser.funcao.get(p[1])
    
    p[0] = "subProgram" + str(prog) +":\n" + p[3] + p[5] + "STOREG " + str(n) + "\n" + "jump final" + str(prog) + "\n" + "endProgram" + str(prog) +":\n"

# PRINT de uma expressão -----------------------------------------
def p_Print(p):
    "Print : PRINT '(' Exp ')'"
    #string = '"\n"'
    p[0] = p[3] + "WRITEI\n"

def p_Print_string(p):
    "Print : PRINT '(' STRING ')'"
    #string = '"\n"'
    p[0] = "PUSHS " + p[3] + "\nWRITES\n"       

# CONDICAO com if -----------------------------------------
def p_Condicao_If(p):
    "Condicao : IF '(' Comparacoes ')' Corpo"
    n = p.parser.statement
    p.parser.statement = n + 1
    p[0] = "ifstmnt" + str(n) + ":\n" + p[3] + "jz endif" + str(n) +"\n" + p[5] + "endif" + str(n) + ":\n"

def p_Condicao_IfElse(p):
    "Condicao : IF '(' Comparacoes ')' Corpo ELSE Corpo" 
    n = p.parser.statement
    p.parser.statement = n + 1
    p[0] = "ifstmnt" + str(n) + ":\n" + p[3] + "jz endif" + str(n) +"\n" + p[5] + "jump end" + str(n) + "\n" + "endif" + str(n) + ":\n" + p[7] + "end" + str(n) + ":\n"


# WHILE -----------------------------------------
def p_While(p):
    "While : WHILE '(' Comparacoes ')' Corpo"
    n = p.parser.loop
    p.parser.loop = n + 1
    p[0] = "repeat" + str(n) + ":\n" + p[3] + "jz endRepeat" + str(n) +"\n" + p[5] + "jump repeat" + str(n) + "\n" + "endRepeat" + str(n) + ":\n"


# CORPO -----------------------------------------
def p_Corpo_Comando(p):
    "Corpo : Comando"
    p[0] = p[1]

def p_Corpo_Comandos(p):
    "Corpo : '{' Comandos '}'"
    p[0] = p[2] 


# COMPARACOES -----------------------------------------
def p_Comparacoes_Conj(p):
    "Comparacoes : Comparacao AND Comparacao"
    p[0] = p[1] + p[3] + "MUL\n"

def p_Comparacoes_Disj(p):
    "Comparacoes : Comparacao OR Comparacao"
    p[0] = p[1] + p[3] + "ADD\n" + p[1] + p[3] + "MUL\n" + "SUB\n"

def p_Comparacoes_Comparacao(p):
    "Comparacoes : Comparacao"
    p[0] = p[1]

def p_Comparacoes_NotComparacao(p):
    "Comparacoes : '!' Comparacao"
    p[0] = p[2] + "NOT\n"

# COMPARACAO -----------------------------------------
def p_Comparacao_Simples(p):
    "Comparacao : Factor SimbRelacional Factor"
    p[0] = p[1] + p[3] + p[2]

def p_Comparacao_Composta(p):
    "Comparacao : '(' Comparacoes ')'"
    p[0] = p[2]
    
# SIMBOLOS RELACIONAIS -----------------------------------------
def p_SimbRelacional_EQ(p):
    "SimbRelacional : EQ"
    p[0] = "EQUAL\n"

def p_SimbRelacional_DIF(p):
    "SimbRelacional : DIF"
    p[0] = "EQUAL\nNOT\n"

def p_SimbRelacional_INF(p):
    "SimbRelacional : '<'"
    p[0] = "INF\n"

def p_SimbRelacional_INFEQ(p):
    "SimbRelacional : INFEQ"
    p[0] = "INFEQ\n"

def p_SimbRelacional_SUPEQ(p):
    "SimbRelacional : SUPEQ"
    p[0] = "SUPEQ\n"

def p_SimbRelacional_SUP(p):
    "SimbRelacional : '>'"
    p[0] = "SUP\n"

# EXP -----------------------------------------
# regra de produçao para um termo
def p_Exp_termo(p):
    "Exp : Termo"
    p[0] = p[1]

# regra de produçao para uma operaçao de soma
def p_Exp_add(p):
    "Exp : Exp '+' Termo"
    p[0] = p[1] + p[3] + "ADD\n"

# regra de produçao para uma operaçao de subtraçao
def p_Exp_sub(p):
    "Exp : Exp '-' Termo"
    p[0] = p[1] + p[3] + "SUB\n" 

# TERMO -----------------------------------------
# regra de produçao para uma operaçao de multiplicaçao

def p_Termo_mod(p):
    "Termo : Termo '%' Factor"
    p[0] = p[1] + p[3] + "MOD\n"

def p_Termo_mul(p):
    "Termo : Termo '*' Factor"
    p[0] = p[1] + p[3] + "MUL\n"

# regra de produçao para uma operaçao de divisao
def p_Termo_div(p):
    "Termo : Termo '/' Factor"
    if (p[3]!=0):
        p[0] = p[1] + p[3] + "DIV\n" 
    else:
        print("Erro: Divisao por 0, a continuar com 0")
        p[0] = "ERRO"   

# regra de produçao para um factor
def p_Termo_factor(p):
    "Termo : Factor"
    p[0] = p[1]

# FACTOR -----------------------------------------
# regra de produçao para um group de um factor
def p_Factor_group(p):
    "Factor : '(' Exp ')'"
    p[0] = p[2]

# regra de produçao para um num de um factor
def p_Factor_num(p):
    "Factor : num"
    p[0] = "PUSHI " + p[1] + "\n"

# regra de produçao para um id de um factor
def p_Factor_id(p):
    "Factor : id"
    n = p.parser.registers.get(p[1])
    p[0] = "PUSHG " + str(n) + "\n"

# TRATAMENTO DE ERROS -----------------------------------------
# Se nao cai nas regras anteriores, cai nesta regra
def p_error(p):
    print('Erro sintatico: ', p)
    parser.success = False

# Build the parser
parser = yacc.yacc()

# My state -> manutenção do estado da stack 
#durante a execução do programa
parser.registers = {}
parser.funcao = {}
parser.statement = 0
parser.loop = 0

# Read line from input and parse it
for linha in sys.stdin:
    parser.success = True
    res = parser.parse(linha)
    if parser.success:
        print(res)
        f.write(res)

    else:
        print("Frase invalida...Corrija e tente novamente!")
f.close()
