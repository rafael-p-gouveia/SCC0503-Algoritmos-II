all: warmup

input_2_bin.o: input_2_bin.c
	gcc -o input_2_bin.o -c input_2_bin.c

main.o: main.c
	gcc -o main.o -c main.c

warmup: main.o input_2_bin.o
	gcc -o warmup main.o input_2_bin.o

run:
	./warmup


