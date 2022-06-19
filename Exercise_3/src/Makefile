CC=gcc
IDIR=../include
CFLAGS=-I$(IDIR)

ODIR=obj

_DEPS=page.h utils.h b_tree.h database_management.h
DEPS=$(patsubst %, $(IDIR)/%, $(_DEPS))

_OBJ = main.o page.o utils.o b_tree.o database_management.o
OBJ=$(patsubst %, $(ODIR)/%, $(_OBJ))

all: b_tree_exe

$(ODIR)/%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

b_tree_exe: $(OBJ)
	$(CC) -o $@ $^ $(CFLAGS)

run:
	./b_tree_exe



