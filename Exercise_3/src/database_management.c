/*
 * Functions manipulate the student's database.
 * Author: Maxsuel F. de Almeida <maxsuelfa@usp.br>
 * Author: Rafael Pereira Golveia <rafael.p.gouveia@usp.br>
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../include/database_management.h"

#define STUDENT_RECORD_SIZE sizeof(int)\
    + 3 * NAME_LENGTH * sizeof(char) + sizeof(double)

/** Function to create a database file.
 *  Given a path, creates a file to hold
 *  the students' data with such path.
 *
 *  @param filePath path to the file.
 **/
void create_database(char *filePath)
{
    FILE *filePointer;
    filePointer = open_file(filePath, "wb");

    fclose(filePointer);
}
/** Function to get the operation to be executed.
 *  Get the database operation to be executed and
 *  return such operation.
 *
 *  @return int operation to be executed.
 **/
int get_operation(void)
{
    char operation[OPERATION_LENGTH];

    scanf("%s", operation);

    if (strcmp(operation, "insert") == 0)
    {
        return INSERT;
    }
    else if (strcmp(operation, "search") == 0)
    {
        return SEARCH;
    }
    else if (strcmp(operation, "update") == 0)
    {
        return UPDATE;
    }
    else if (strcmp(operation, "exit") == 0)
    {
        return EXIT;
    }

    return NO_OPERATION;
}


/** Function to read a student record from the standart input.
 *  Read a student record from the std and returns it.
 *
 *  @return student_t record read.
 **/
student_t *get_record_from_input(void)
{
    student_t *record;

    record = (student_t *)malloc(sizeof(student_t));

    if (!record)
    {
        printf("Error in allocate memory\n");
        printf("The program will be closed...");
        exit(EXIT_FAILURE);
    }

    record->NUSP = atoi(get_field());
    strcpy(record->name, get_field());
    strcpy(record->surname, get_field());
    strcpy(record->major, get_field());
    record->grade = (float)atof(get_field());

    return record;
}

/** Function to read each field of a student record from the standart input.
 *  Read one field from the stdin per calling.
 *
 *  @return char field read.
 **/
char *get_field(void)
{
    char buffer;
    int index;
    char *field;


    field = (char *)malloc(FIELD_SIZE * sizeof(char));

    if (!field)
    {
        printf("Error in allocate memory\n");
        printf("The program will be closed...");
        exit(EXIT_FAILURE);
    }

    memset(field, 0, FIELD_SIZE);
    index = 0;

    do
    {
        buffer = fgetc(stdin);

        if (buffer == QUOTATION_MARK)
        {
            continue;
        }

        if(buffer == COMMA)
        {
            strcat(field, "\0");
            break;
        }

        field[index] = buffer;
        index++;
    }
    while (buffer != COMMA\
            && buffer != ENDL\
            && buffer != ENDS);
    
    return field;
}

/** Function to read a student record from the database.
 *  Given a student record RRN, reads such
 *  record from the database.
 *
 *  @param RRN the RRN of such record.
 *  @param filePath path to the database file.
 *
 *  @return student_t the record read.
 **/
student_t *read_record(long RRN, char *filePath)
{
    FILE *filePointer;
    student_t *studentReg;

    studentReg = (student_t *)malloc(sizeof(student_t));
    if (!studentReg)
    {
        printf("Error in allocate memory\n");
        printf("The program will be closed...");
        exit(EXIT_FAILURE);
    }

    filePointer = open_file(filePath, "rb+");

    fseek(filePointer, RRN * STUDENT_RECORD_SIZE, SEEK_SET);

    fread(&studentReg->NUSP, sizeof(studentReg->NUSP), 1, filePointer);
    fread(studentReg->name, sizeof(studentReg->name), 1, filePointer);
    fread(studentReg->surname, sizeof(studentReg->surname), 1, filePointer);
    fread(studentReg->major, sizeof(studentReg->major), 1, filePointer);
    fread(&studentReg->grade, sizeof(studentReg->grade), 1, filePointer);

    fclose(filePointer);

    return studentReg;
}

/** Function to write a student record in the database.
 *  Given a student record and its RRN, writes such
 *  record in the database.
 *
 *  @param studentReg the student record.
 *  @param RRN the RRN of such record.
 *  @param filePointer pointer to the database file.
 **/
void write_record(student_t *studentReg, long RRN, FILE *filePointer)
{
    fseek(filePointer, RRN * STUDENT_RECORD_SIZE, SEEK_SET);

    fwrite(&studentReg->NUSP, sizeof(studentReg->NUSP), 1, filePointer);
    fwrite(studentReg->name, sizeof(char), NAME_LENGTH, filePointer);
    fwrite(studentReg->surname, sizeof(char), NAME_LENGTH, filePointer);
    fwrite(studentReg->major, sizeof(char), NAME_LENGTH, filePointer);
    fwrite(&studentReg->grade, sizeof(studentReg->grade), 1, filePointer);
}

/** Function to insert a stundent record in the database.
 *  Given a student record and the path for the database,
 *  writes such record in the database.
 *
 *  @param student student record.
 *  @param filePath path to the database fiel.
 *
 *  @return record_t struct containing the key and the RRN of
 *  the added student.
 **/
record_t *database_insert(student_t *student, char *filePath)
{
    FILE *filePointer;
    record_t *record;
    long studentRRN;

    filePointer = open_file(filePath, "rb+");
    fseek(filePointer, 0, SEEK_END);

    studentRRN = ftell(filePointer) / STUDENT_RECORD_SIZE;

    write_record(student, studentRRN, filePointer);

    fclose(filePointer);

    record = (record_t *)malloc(sizeof(record_t));
    if (!record)
    {
        printf("Error in allocate memory\n");
        printf("The program will be closed...");
        exit(EXIT_FAILURE);
    }
    record->key = student->NUSP;
    record->RRN = studentRRN;

    return record;
}

/** Function to update a student record.
 *  Given a student record, its RRN and the path for
 *  the database, update such record in the database.
 *
 *  @param student student record.
 *  @param RRN RRN of such record.
 *  @param path path to the database file.
 **/
void database_update(student_t *student, long RRN, char *filePath)
{
    FILE *filePointer;

    filePointer = open_file(filePath, "rb+");
    write_record(student, RRN, filePointer);

    fclose(filePointer);
}

/** Function to print a student record.
 *  Given a student record, print its fields.
 *
 *  @param student record to be printed.
 **/
void print_record(student_t *student)
{
    printf("-------------------------------\n");
    printf("nUSP: %d\n", student->NUSP);
    printf("Nome: %s\n", student->name);
    printf("Sobrenome: %s\n", student->surname);
    printf("Curso: %s\n", student->major);
    printf("Nota: %.2lf\n", student->grade);
    printf("-------------------------------\n");
}
