#ifndef INPUT_2_BIN_H
#define INPUT_2_BIN_H
#include<stdio.h>

#define FULL_NAME_LENGTH 50
#define MAJOR_LENGTH 50
#define COMMA ','
#define PERIOD '.'
#define BUFFER_SIZE 126

/* Defining the boolean type */
typedef unsigned char bool;
#define TRUE 1
#define FALSE 0

// Struct to hold the students' information
typedef struct
{
    int NUSP;
    char fullName[FULL_NAME_LENGTH];
    char major[MAJOR_LENGTH];
    float grade;
} student_t;

/* Read the informations of a student from the input and store
 * these informations in a student_t struct */
int readRegFromInput(student_t**);

/* Read the informations of a student from a binary file and 
 * return these informations in a student_t struct*/
student_t* readRegFromBin(FILE*, int);

/* Write the register in a binary file */
void writeRegister(FILE* , student_t*);


/* Show the last ten register from the binary file */
void showLastTen(FILE*);

/* Show just one register from the binary file*/
void showOneReg(FILE*, int);

/* Get the size of the file in terms of registers*/
int getFileSize(FILE*);
#endif
