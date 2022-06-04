#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "file_management.h"
#include "b_tree.h"

#define DB_PATH "reg.dat"

#define STRING_LENGHT 100
#define SIZE_ENTRY sizeof(int) + (3*STRING_LENGHT+2)*sizeof(char) + sizeof(float)

#define EXIT -1
#define INSERT 0
#define SEARCH 1
#define UPDATE 2
#define NO_OPERATION -2
#define EXIT_FAILURE 1

#define MAX_LENGHT_STRING 1
#define QUOTATION_MARKS '"'
#define COMMA ','
#define ENDL '\n'
#define ENDS '\0'
#define TRUE 1

typedef struct _student {
    int nUSP;
    char name[STRING_LENGHT], surname[STRING_LENGHT], course[STRING_LENGHT];
    float grade;
}student;

void insert_student(student newEntry){
    FILE* db;
    long int newEntryRRN, newVacantSlotRRN;

    if(search_bTree(newEntry.nUSP) != ENTRY_NOT_FOUND){
        printf("O Registro ja existe!\n");
    }

    db = fopen(DB_PATH,"rb+");
    if (db == NULL){
        exit(EXIT_FAILURE);
    }

    fread(&newEntryRRN, sizeof(long int), 1, db);
    fseek(db, newEntryRRN * SIZE_ENTRY, SEEK_SET);

    fwrite(&(newEntry.nUSP), sizeof(int), 1, db);
    fwrite(newEntry.name, (STRING_LENGHT + 1)*sizeof(char), 1, db); //provavelmente vai dar pau
    fwrite(newEntry.surname, (STRING_LENGHT + 1)*sizeof(char), 1, db);
    fwrite(newEntry.course, (STRING_LENGHT + 1)*sizeof(char), 1, db);
    fwrite(&(newEntry.grade), sizeof(float), 1, db);

    fseek(db, sizeof(long int), SEEK_SET);
    newVacantSlotRRN = newEntryRRN + 1;
    fwrite(&newVacantSlotRRN, sizeof(long int), 1, db);

    add_b_tree(newEntry.nUSP,newEntryRRN);

    fclose(db);
}

int get_op() {
    char nameOP[7];

    scanf("%s", nameOP);

    if(strcmp(nameOP, "exit") == 0) {
        return EXIT;
    }
    else if(strcmp(nameOP, "insert") == 0) {
        return INSERT;
    }
    else if(strcmp(nameOP, "search") == 0) {
        return SEARCH;
    }
    else if(strcmp(nameOP, "update") == 0) {
        return UPDATE;
    }
    return NO_OPERATION;
}

void select_op(int nOP) {
    int selectedKey;
    student *input;

    input = (student*)malloc(sizeof(student));

    switch(nOP){
        case INSERT:
            *input = get_full_insertion_input();
            //print_fields(input,1);
            //insert_student(*input);
            break;

        case SEARCH:
            scanf("%d", &selectedKey);
            //input = search_student(selectedKey);
            if(input != NULL){
                print_fields(input, 1);
            }
            break;

        case UPDATE:
            *input = get_full_insertion_input();
            //update_entry(*input);
            break;

        case EXIT:
            break;
        default:
            // Message asking for a valid operation
            break;
    }
}

char* get_insertion_field(int *err){
    char buffer, *word;
    int charCounter;

    charCounter = 0;
    word = (char*)malloc((STRING_LENGHT + 1)*sizeof(char));
    if (word == NULL){
        exit(EXIT_FAILURE);
    }

    while(TRUE) {
        buffer = getc(stdin);

        if(buffer == QUOTATION_MARKS){
            continue;
        }

        if(buffer == COMMA || buffer == ENDL || buffer == EOF) {
            /*while(charCounter < STRING_LENGHT){
                word[charCounter] = ' ';
            }
            word[STRING_LENGHT] = ENDS;*/
            word[charCounter] = ENDS;
            return word;
        }

        else {
            word[charCounter] = buffer;
            charCounter++;
        }
        if(charCounter == STRING_LENGHT){
            word[STRING_LENGHT] = ENDS;
            *err = MAX_LENGHT_STRING;
            return word;
        }
    }
}

student get_full_insertion_input() {
    student input;
    char *word;
    int err;

    word = get_insertion_field(&err);
    input.nUSP = atoi(word);

    word = get_insertion_field(&err);
    strcpy(input.name,word);

    word = get_insertion_field(&err);
    strcpy(input.surname,word);

    word = get_insertion_field(&err);
    strcpy(input.course,word);

    word = get_insertion_field(&err);
    input.grade = atof(word);

    return input;
}

void print_fields(student *arrStudents, int nFields){
    int i;

    for(i = 0; i < nFields; i++){
        printf("-------------------------------\n");

        printf("USP number: %d\nName: %s\nSurname: %s\nCourse: %s\nTest grade: %.2f\n",\
                arrStudents[i].nUSP, arrStudents[i].name , arrStudents[i].surname,\
                arrStudents[i].course, arrStudents[i].grade);
        printf("-------------------------------\n");
    }
}

void create_files(){
    FILE* db;
    long int aux;

    db = fopen(DB_PATH,"wb+");
    if (db == NULL){
        exit(EXIT_FAILURE);
    }
    aux = 1;
    fwrite(&aux, sizeof(long int), 1 , db);

    fclose(db);
}
