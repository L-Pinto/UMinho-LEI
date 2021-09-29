FLAGS= -ansi -std=c99 -w -Wall -Wextra -O2 -D_GNU_SOURCE 
PKG = `pkg-config --cflags --libs glib-2.0`
COMPILER=gcc
INCLUDES=include
SRC=src

program:	
	make catClientes
	make catProdutos
	make factGlobal
	make gestFilial
	make interface
	make leitura
	make navController
	make navModel 
	make navView
	make queries
	make view
	make main
	make allprogs
	make clean
	
catClientes: $(SRC)/catClientes.c $(INCLUDES)/catClientes.h
	$(COMPILER) $(FLAGS) -o catClientes.o $(PKG) -c $(SRC)/catClientes.c

catProdutos: $(SRC)/catProdutos.c $(INCLUDES)/catProdutos.h
	$(COMPILER) $(FLAGS) -o catProdutos.o $(PKG) -c $(SRC)/catProdutos.c
	
factGlobal: $(SRC)/factGlobal.c $(INCLUDES)/factGlobal.h
	$(COMPILER) $(FLAGS) -o factGlobal.o $(PKG) -c $(SRC)/factGlobal.c

gestFilial: $(SRC)/gestFilial.c $(INCLUDES)/gestFilial.h
	$(COMPILER) $(FLAGS) -o gestFilial.o $(PKG) -c $(SRC)/gestFilial.c	

interface: $(SRC)/interface.c $(INCLUDES)/interface.h
	$(COMPILER) $(FLAGS) -o interface.o $(PKG) -c $(SRC)/interface.c

leitura: $(SRC)/leitura.c $(INCLUDES)/leitura.h
	$(COMPILER) $(FLAGS) -o leitura.o $(PKG) -c $(SRC)/leitura.c

navController: $(SRC)/navController.c $(INCLUDES)/navController.h
	$(COMPILER) $(FLAGS) -o navController.o $(PKG) -c $(SRC)/navController.c

navModel: $(SRC)/navModel.c $(INCLUDES)/navModel.h
	$(COMPILER) $(FLAGS) -o navModel.o $(PKG) -c $(SRC)/navModel.c

navView: $(SRC)/navView.c $(INCLUDES)/navView.h
	$(COMPILER) $(FLAGS) -o navView.o $(PKG) -c $(SRC)/navView.c

queries: $(SRC)/queries.c $(INCLUDES)/queries.h
	$(COMPILER) $(FLAGS) -o queries.o $(PKG) -c $(SRC)/queries.c

view: $(SRC)/view.c $(INCLUDES)/view.h
	$(COMPILER) $(FLAGS) -o view.o $(PKG) -c $(SRC)/view.c

main: $(SRC)/main.c interface.o view.o navController.o
	$(COMPILER) $(FLAGS) -o main.o $(PKG) -c $(SRC)/main.c	

allprogs: $(SRC)/main.c main.o catClientes.o catProdutos.o factGlobal.o gestFilial.o interface.o leitura.o navController.o navModel.o navView.o queries.o view.o
	$(COMPILER) $(FLAGS) catClientes.o catProdutos.o factGlobal.o gestFilial.o interface.o leitura.o navController.o navModel.o navView.o queries.o view.o main.o $(PKG) -o program

memcheck:
	make program
	valgrind --leak-check=yes ./program

clean:
	rm -rf *.o 

run: 
	make program
	./program	

