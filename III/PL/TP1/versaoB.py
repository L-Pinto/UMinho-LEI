import re

def parseList(y, res, i):

    if y.group(2) == "":
        lst1 = re.sub(r'\(', "[", res[i])
        lst2 = re.sub(r'\)', "]", lst1)
        print("\t\t"+ '"'+ y.group(1) + '"'+ ": " +lst2, end="")
    else:
        index = re.sub(r'\(', "",res[i])
        index2 = re.sub(r'\)', "", index)
        values = re.split(r',', index2)
        
        if y.group(2) == "sum":
        
            add = 0
            for value in values:
                number = int(value)
                if(isinstance(number,int)):
                    add += number
                

            print("\t\t"+ '"'+ y.group(1) + "_" + y.group(2) + '"'+ ": " + str(add), end="")
        elif y.group(2) == "max":

            numbers = []
            for value in values:
                number = int(value)
                numbers.append(number)

            print("\t\t"+ '"'+ y.group(1)+ "_" + y.group(2) + '"'+ ": " +str(max(numbers)), end="")
        elif y.group(2) == "min":

            numbers = []
            for value in values:
                number = int(value)
                numbers.append(number)

            print("\t\t"+ '"'+y.group(1) + "_" + y.group(2) +'"'+ ": " + + str(min(numbers)), end="")
        elif y.group(2) == "avg":

            add = 0
            for value in values:
                number = int(value)
                add += number
            print("\t\t"+ '"'+y.group(1)+ "_" + y.group(2) + '"'+ ": " +str(add/len(values)), end="")
        else: # caso nao exista nenhuma opçao
            lst1 = re.sub(r'\(', "[", res[i])
            lst2 = re.sub(r'\)', "]", lst1)
            print("\t\t"+ '"'+ y.group(1) + '"'+ ": " +lst2, end="")


f = open("csv_test_relatorio.csv", "r")
first_line = f.readline()
fields = re.split(r';',first_line)
print("[\n")
content = f.readlines()
limit = len(content)

for count,line in enumerate(content):
    res = re.split(r';', line)
    print("\t{")
    i=0
    length = len(fields)
    
    for field in fields:
        field = field.strip("\n")
        y = re.search(r'(\w+)\*(\w*)',field)

        if y: # caso existam listas 
            parseList(y,res,i)
        else: # caso nao existam listas
            res[i] = res[i].strip("\n")
            print("\t\t"+ '"' + field + '"' + " : " + '"'+res[i]+ '"', end="")

        i+=1
        if i != length:
            print(",\n")
        else :
            print("\n")

    if count == (limit-1):
        print("\t}\n")
    else:
        print("\t},\n")

print("]\n")