#include "manage_file.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#define DEFAULT_WORD_SIZE 50
#define MAX_WORD_SIZE 200
#define INDEX_PATH "index.dat"
#define DB_PATH "regs.dat"

typedef struct _student{
    int nUSP,sizeString[3];
    char *name,*surname,*course;
    float grade;
}student;

int get_op(){
    char nameOP[7];

    scanf("%s",nameOP);
    if(strcmp(nameOP,"exit") == 0){
        return -1;
    }
    else if(strcmp(nameOP,"insert") == 0){
        return 0;
    }
    else if(strcmp(nameOP,"search") == 0){
        return 1;
    }
    else if(strcmp(nameOP,"delete") == 0){
        return 2;
    }
    return -2;
}

void select_op(int nOP){
    int selectedKey;
    student *input;

    input = (student*)malloc(sizeof(student));
    switch(nOP){
        case 0:
            *input = get_full_insertion_input();
            //print_fields(input,1);
            insert_student(*input);
            break;
        case 1:
            scanf("%d",&selectedKey);
            input = search_student(selectedKey);
            if(input != NULL){
                print_fields(input,1);
            }
            break;
        case 2:
            scanf("%d",&selectedKey);
            delete_entry(selectedKey);
            break;
        case -1:
            break;
        default:
            //mensagem pedindo para inserir um operador valido
            break;
    }
}


char* get_insertion_field(int *err, int *sizeField){
    char buffer, *word;
    int charCounter;

    charCounter = 0;
    word = (char*)malloc((DEFAULT_WORD_SIZE)*sizeof(char));
    while(1){
        buffer = getc(stdin);
        if(buffer == ',' || buffer == '\n' || buffer == EOF){
            word[charCounter] = '\0';
            word = (char*)realloc(word,(charCounter + 1)*sizeof(char)); //free unneeded memory
            *sizeField = charCounter + 1; //set pointer with the size of the string
            return word;

        }
        else{
            word[charCounter] = buffer;
            charCounter++;
        }

        if(charCounter%DEFAULT_WORD_SIZE == 0){
            word = (char*)realloc(word,(charCounter + DEFAULT_WORD_SIZE)*sizeof(char)); //allocate more memory chunks, if needed
        }
        if(charCounter == MAX_WORD_SIZE-1){ //prevents user from overflowing memory.
            *err = 1;
            word[charCounter+1] = '\0';
            *sizeField = MAX_WORD_SIZE;
            return word;
        }
    }
}

student get_full_insertion_input(){
    student input;
    char *word;
    int err,sizeField;

    word = get_insertion_field(&err,&sizeField);
    input.nUSP = atoi(word);

    word = get_insertion_field(&err,&sizeField);
    input.name = word;
    input.sizeString[0] = sizeField;

    word = get_insertion_field(&err,&sizeField);
    input.surname = word;
    input.sizeString[1] = sizeField;

    word = get_insertion_field(&err,&sizeField);
    input.course = word;
    input.sizeString[2] = sizeField;

    word = get_insertion_field(&err,&sizeField);
    input.grade = atof(word);

    return input;
}

void insert_student(student input){
    FILE* index;
    FILE* db;
    int desiredSize,offsetDB,endIndexRRN,emptySlotRRN,aux,nexFirstOfStack;

    if(find_entry_index(input.nUSP,&aux) != -1){
        printf("O Registro ja existe!\n");
        return;
    }

    desiredSize = 4*sizeof(int) + input.sizeString[0] + input.sizeString[1] + input.sizeString[2] + sizeof(float);
    db = fopen(DB_PATH,"rb+");

    fread(&offsetDB,sizeof(int),1,db); //get end of file byte offset.
    fseek(db,offsetDB,SEEK_SET); //set the cursor at the end of the file.

    fwrite(&(input.nUSP),sizeof(int),1,db);
    fwrite(&(input.sizeString[0]),sizeof(int),1,db);
    fwrite(input.name,input.sizeString[0],1,db);
    fwrite(&(input.sizeString[1]),sizeof(int),1,db);
    fwrite(input.surname,input.sizeString[1],1,db);
    fwrite(&(input.sizeString[2]),sizeof(int),1,db);
    fwrite(input.course,input.sizeString[2],1,db);
    fwrite(&(input.grade),sizeof(float),1,db);

    fseek(db,0,SEEK_SET);
    aux = offsetDB + desiredSize;
    fwrite(&aux,sizeof(int),1,db); //uptade the header with the new end of file byte offset.

    index = fopen(INDEX_PATH,"rb+");
    fread(&emptySlotRRN,sizeof(int),1,index); //check the header to see if there are empty slots.
    if(emptySlotRRN != -1){ //if so
        fseek(index,(2*(emptySlotRRN+1)+1)*sizeof(int),SEEK_SET); //go to the data column of the first one.
        fread(&nexFirstOfStack,sizeof(int),1,index);
        fseek(index,0,SEEK_SET);
        fwrite(&nexFirstOfStack,sizeof(int),1,index); //update the header/top of the stack.
        fseek(index,2*(emptySlotRRN+1)*sizeof(int),SEEK_SET); //set the cursor in the empty slot.
    }
    else{//if not
        fread(&endIndexRRN,sizeof(int),1,index); //check the reader for the end of file rrn.
        fseek(index,sizeof(int),SEEK_SET);
        aux = endIndexRRN + 1;
        fwrite(&aux,sizeof(int),1,index);//update header
        fseek(index,2*(endIndexRRN+1)*sizeof(int),SEEK_SET); //set cursor to the empty slot.
    }
    fwrite(&(input.nUSP),sizeof(int),1,index); //write entry in the index for the new student .
    fwrite(&offsetDB,sizeof(int),1,index);

    fclose(index);
    fclose(db);
}

void delete_entry(int key){
    int dbOffset,rrnExclusion,rrnEmptySlot,aux;
    FILE *index, *db;

    index = fopen(INDEX_PATH,"rb+");
    db = fopen(DB_PATH,"rb+");

    rrnExclusion = find_entry_index(key,&dbOffset); //check if the entry, so that it can be deleted.
    if(rrnExclusion == -1){
        return;
    }
    fread(&rrnEmptySlot,sizeof(int),1,index);//get top of empty slots stack from the index header
    fseek(index,2*(rrnExclusion+1)*sizeof(int),SEEK_SET);
    aux = -1;
    fwrite(&aux,sizeof(int),1,index); //set the deleted entry's key in the index to -1, since it is a forbidden value for nUSPs.
    fwrite(&rrnEmptySlot,sizeof(int),1,index); //link the new empty slot to the rest of the stack by passing the old first element's rrn.
    fseek(index,0,SEEK_SET);
    fwrite(&rrnExclusion,sizeof(int),1,index);//update header so that the newly deleted entry becomes the new top of the stack

    fseek(db,dbOffset,SEEK_SET);
    fwrite(&aux,sizeof(int),1,db); //set's the key of the entry in the db to -1, so that it's now logically marked for deletion.

    fclose(index);
    fclose(db);
}

int find_entry_index(int key, int *respectiveOffset){
    FILE* index;

    int freadReturn,bufferKey,bufferOffset,entryRRN;

    index = fopen(INDEX_PATH,"rb+");
    entryRRN = 0;

    fseek(index,2*sizeof(int),SEEK_SET);//skip the header.
    do{
        freadReturn = fread(&bufferKey,sizeof(int),1,index);
        fread(&bufferOffset,sizeof(int),1,index);
        if(bufferKey == key){
            *respectiveOffset = bufferOffset;
            fclose(index);
            return entryRRN;
        }
        entryRRN++;
    }while(freadReturn == 1);

    fclose(index);
    return -1;
}



student* search_student(int key){
    FILE* db;

    int respectiveOffset,bufferSizeString;
    student *retval;

    db = fopen(DB_PATH,"rb+");

    if(find_entry_index(key,&respectiveOffset) != -1){ //if the entry exist, respectiveOffset gets loaded with the byte offset corresponding to the desired entry in the db file.

        retval = (student*)malloc(sizeof(student));

        fseek(db, respectiveOffset,SEEK_SET);

        fread(&retval->nUSP,sizeof(int),1,db);
        fread(&bufferSizeString,sizeof(int),1,db);
        retval->sizeString[0] = bufferSizeString;
        retval->name = (char*)malloc(bufferSizeString*sizeof(char));
        fread(retval->name,bufferSizeString*sizeof(char),1,db);

        fread(&bufferSizeString,sizeof(int),1,db);
        retval->sizeString[1] = bufferSizeString;
        retval->surname = (char*)malloc(bufferSizeString*sizeof(char));
        fread(retval->surname,bufferSizeString*sizeof(char),1,db);

        fread(&bufferSizeString,sizeof(int),1,db);
        retval->sizeString[2] = bufferSizeString;
        retval->course = (char*)malloc(bufferSizeString*sizeof(char));
        fread(retval->course,bufferSizeString*sizeof(char),1,db);

        fread(&retval->grade,sizeof(float),1,db);
        return retval;
    }
    else{
        printf("Registro nao encontrado!\n");
    }

    return NULL;
}

void print_fields(student *arrStudents, int nFields){
    int i;

    for(i = 0; i < nFields; i++){
        printf("-------------------------------\nUSP number: %d\nName: %s\nSurname: %s\nCourse: %s\nTest grade: %.2f\n-------------------------------\n",arrStudents[i].nUSP,arrStudents[i].name,arrStudents[i].surname,arrStudents[i].course,arrStudents[i].grade);
    }
}

FILE* open_file(char path[], char mode[]){
    FILE* retval;

    retval = fopen(path,mode);
    if(retval == NULL){
        printf("Failed to open file...\n");
        exit(1);
    }
    return retval;
}


void create_files(){
    int val;
    FILE *db, *index;

    db = fopen(DB_PATH,"wb+");
    val = sizeof(int);
    fwrite(&val,sizeof(int),1,db); //db file contains one int that indicates the end of file byte offset as a header.
    index = fopen(INDEX_PATH,"wb+");
    val = -1;
    fwrite(&val,sizeof(int),1,index);
    val = 2*sizeof(int);
    fwrite(&val,sizeof(int),1,index); //index file contains 2 ints as header, the first is the beginning of a stack of empty slots, while the second is the rrn of the end of file
    fclose(db);
    fclose(index);
}
