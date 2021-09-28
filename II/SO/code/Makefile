COMPILER = gcc

FLAGS = -O2 -Wall

all: argus argusd

argus: argus.o
	$(COMPILER) -o $@ $<
	
argusd: argusd.o
	$(COMPILER) -o $@ $<

%.o: %.c
	$(COMPILER) -c -o $@ $< $(FLAGS)

clean:
	rm -rf argus argusd *.o log* fifoSC fifoCS