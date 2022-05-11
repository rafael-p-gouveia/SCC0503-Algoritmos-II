all : prog

run :
	./prog

main.o : main.c
	gcc -o main.o -c main.c

manage_file.o : manage_file.c
	gcc -o manage_file.o -c manage_file.c

prog : main.o manage_file.o
	gcc -o prog main.o manage_file.o

clean :
	rm *.o
	rm prog