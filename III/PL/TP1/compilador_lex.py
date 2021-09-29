# calculadora_lex.py
#
# Trabalho Prático 2
# 

import ply.lex as lex

tokens = ['num','id','INT','IF','EQ','DIF','AND', 'OR','SUPEQ','INFEQ', 'ELSE', 'WHILE','READ','PRINT','NOME', 'RETURN', 'STRING']

literals = ['(', ')', '+', '-', '*', '/', '=', '<', '>', '{', '}', '!','[',']', '%', '"']

t_EQ = r'=='
t_DIF = r'!='
t_AND = r'&&'
t_OR = r'\|\|'
t_SUPEQ = r'>='
t_INFEQ = r'<='


def t_PRINT(t):
    r'print'
    return t  

def t_RETURN(t):
    r'return'
    return t  

def t_READ(t):
    r'read\(\)'
    return t  

def t_WHILE(t):
    r'while'
    return t  

def t_INT(t):
    r'int'
    return t 

def t_IF(t):
    r'if'
    return t   

def t_ELSE(t):
    r'else'
    return t

def t_NOME(t):
    r'[A-Za-z]+\(\)'
    return t 

def t_STRING(t):
    r'\"[A-Za-z]+(:)?\"'
    return t   

def t_id(t):
    r'[a-z]'
    return t   

def t_num(t):
    r'(\-)?\d+'
    return t 

  

t_ignore = " \t\n\r"

# se tiver error a nivel lexico
def t_error(t):
    print('Carater ilegal: ', t.value[0]) # acusamos caracter
    t.lexer.skip(1) # e avancamos

#build the lexer
lexer = lex.lex()
