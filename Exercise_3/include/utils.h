#ifndef UTILS_H
#define UTILS_H
/*
 * Author: Maxsuel F. de Almeida <maxsuelfa@usp.br>
 * Author: Rafael Pereira Golveia <rafael.p.gouveia@usp.br>
 */
#include <stdio.h>

/* Auxiliar Defines */
#define MINIMUN_DEGREE 25
#define PAGE_SIZE 1024
#define GARBAGE '$'
#define INITIALIZER -1
#define BTREE_PATH "../files/b_tree.dat"

/* Defines to indicate Erros */
#define ERROR 0
#define SUCCESS 1

/* Defining the bool type */
typedef short bool;
#define TRUE 1
#define FALSE 0

/* Struct for the records */
typedef struct record
{
    int key;
    long RRN;
} record_t;

FILE *open_file(char *, char *);
long get_end_of_file_RRN(FILE *);
#endif
