#ifndef DATABASE_MANAGEMENT_H
#define DATABASE_MANAGEMENT_H
/*
 * Author: Maxsuel F. de Almeida <maxsuelfa@usp.br>
 * Author: Rafael Pereira Golveia <rafael.p.gouveia@usp.br>
 */
#include "../include/utils.h"

/* Defining constants */
#define OPERATION_LENGTH 7
#define INSERT 0
#define SEARCH 1
#define UPDATE 2
#define EXIT -1
#define NO_OPERATION -2
#define FIELD_SIZE 100
#define NAME_LENGTH 25
#define COMMA ','
#define ENDL '\n'
#define ENDS '\0'
#define EMPTY_SPACE ' '
#define QUOTATION_MARK '\"'

/* Student type to hold the student record */
typedef struct student
{
    int NUSP;
    char name[NAME_LENGTH];
    char surname[NAME_LENGTH];
    char major[NAME_LENGTH];
    float grade;
} student_t;

void create_database(char *);
int get_operation(void);
student_t *get_record_from_input(void);
char *get_field(void);
student_t *read_record(long, char *);
void write_record(student_t *, long, FILE *);
record_t *database_insert(student_t *, char*);
void database_update(student_t *, long, char *);
void print_record(student_t *student);
#endif
