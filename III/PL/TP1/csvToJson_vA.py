import sys
import re

f = open("output3.json","w+")
f.write("[\n")

csv = open("csv_test_relatorio.csv")
first_line = csv.readline() 

header = re.split(r';',first_line) 

indiceList = [] 
option = []

for i in range(len(header)):
    r = re.search(r'\*',header[i])
    if r:
        indiceList.append(i)

for i in indiceList:
    lista = re.split(r'\*',header[i])
    op = re.sub(r'\n',r'',lista[-1]) 
    if op == '': option.append("null") 
    else: option.append(op) 

CSVFile = csv.readlines()
CSVLen = len(CSVFile)


for c,linha in enumerate(CSVFile): 
    res = re.split(r';',linha)

    f.write("\t {\n")

    for i in range(len(res)):

        if i in indiceList:  # caso seja uma lista
            listStr = re.findall(r'(\d+)',res[i]) # procurar numeros

            listNr = [int(nr) for nr in listStr] # converter lista de string para numeros

            campo = re.split(r'\*', header[i])   
            
            ind = indiceList.index(i) # para saber qual o indice das opçoes que contem a operaçao a aplicar
            if len(listStr) != 0:

                if option[ind] == "sum":
                    value = sum(listNr)
                elif option[ind] == "avg":
                    value = sum(listNr) / len(listNr)
                elif option[ind] == "min":
                    value = min(listNr)
                elif option[ind] == "max":
                    value = max(listNr)
                else: # se nao houver operaçao, mantem se a lista 
                    value = listNr

                if (option[ind] != "null"): # caso existam opçoes
                    s = '\t\t"' + campo[0] + "_" + option[ind] + '": ' + str(value) 
                else :  # caso nao existam opçoes
                    s = '\t\t"' + campo[0] + '": ' + str(value) 
            else: # tratamento de casos de erro (quando nao é uma lista de numeros)
                res[i] = re.sub(r'[()]',r'',res[i])
                auxStr = re.split(r',',res[i])
                auxStr = re.sub(r'\'',r'"',str(auxStr)) 
                s = '\t\t"' + campo[0] + '": ' + auxStr
        else:  # caso seja outro campo (Ex:nome)
            header[i] = re.sub(r'\n',r'',header[i])
            res[i] = re.sub(r'\n',r'',res[i])
            s = '\t\t"' + header[i] + '": "' + res[i] + '"' 
        if i != len(res) - 1:
            s = s + ', '
        f.write(s + '\n')
    f.write("\t },\n") if c != CSVLen - 1 else f.write("\t }\n")
f.write("]\n")
