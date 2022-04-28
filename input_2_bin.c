#include "input_2_bin.h"
#include <stdlib.h>
#include <string.h>


int readRegFromInput(student_t **student)
{
    char fieldId, index, bufferIndex;
    size_t bufferSize;
    float power;
    bool isDecimal;
    char *buffer;

    buffer = (char*)malloc(BUFFER_SIZE * sizeof(char));
    (*student) = (student_t*)malloc(sizeof(student_t));

    if (!(*student) || !buffer)
    {
        printf("Error in allocate memory\nThe program will be closed...");
        exit(EXIT_FAILURE);
    }

    (*student)->NUSP = 0;
    memset((*student)->fullName, 0, FULL_NAME_LENGTH);
    memset((*student)->major, 0, MAJOR_LENGTH);
    (*student)->grade = 0.0;

    fieldId = 0;
    index = 0;
    bufferIndex = 0;
    bufferSize = BUFFER_SIZE;
    power = 1.0;
    isDecimal = 0;

    if (getline(&buffer, &bufferSize, stdin) == EOF)
        return EOF;

    while (buffer[bufferIndex] != '\n' && buffer[bufferIndex] != '\0')
    {
        if (buffer[bufferIndex] != COMMA)
        {
            switch (fieldId)
            {
            case 0:
                (*student)->NUSP = 10 * (*student)->NUSP + (buffer[bufferIndex] - '0');
                break;
            case 1:
                (*student)->fullName[index] = buffer[bufferIndex];
                index++;
                break;
            case 2:
                (*student)->major[index] = buffer[bufferIndex];
                index++;
                break;
            case 3:
                if (buffer[bufferIndex] == PERIOD)
                {
                    isDecimal = 1;
                    bufferIndex++;
                }
                (*student)->grade = 10.0 * (*student)->grade + (float)(buffer[bufferIndex] - '0');
                if (isDecimal)
                    power *= 10.0;
                break;
            }
        }
        else
        {
            fieldId++;
            index = 0;
        }
        bufferIndex++;
    }

    (*student)->grade /= power;

    free(buffer);

    return 1;
}

void writeRegister(FILE* fPointer, student_t *student)
{
    fwrite(&student->NUSP, sizeof(int), 1, fPointer);
    fwrite(student->fullName, sizeof(char), FULL_NAME_LENGTH, fPointer);
    fwrite(student->major, sizeof(char), MAJOR_LENGTH, fPointer);
    fwrite(&student->grade, sizeof(float), 1, fPointer);
    fflush(fPointer);
}

student_t* readRegFromBin(FILE* fPointer, int regPosition)
{
    student_t *student;

    student = (student_t*) malloc(sizeof(student_t));
    if (!student)
    {
        printf("Error in allocate memory\nThe program will be closed...");
        exit(EXIT_FAILURE);
    }

    fseek(fPointer, (regPosition - 1) * sizeof(student_t), SEEK_SET);
    fread(&student->NUSP, sizeof(int), 1, fPointer);
    fread(student->fullName, sizeof(char), FULL_NAME_LENGTH, fPointer);
    fread(student->major, sizeof(char), MAJOR_LENGTH, fPointer);
    fread(&student->grade, sizeof(float), 1, fPointer);

    return student;
}

void showOneReg(FILE* fPointer, int regPosition)
{
    student_t *student;

    student = readRegFromBin(fPointer, regPosition);

    printf("nUSP: %d\n", student->NUSP);
    printf("Nome: %s\n", student->fullName);
    printf("Curso: %s\n", student->major);
    printf("Nota: %.2f\n", student->grade);

    free(student);
}

void showLastTen(FILE *fPointer)
{
    int fileSize;

    fileSize = getFileSize(fPointer);

    for (int i = fileSize - 9; i <= fileSize; i++)
    {
        showOneReg(fPointer, i);
        printf((i == fileSize) ? "" : "\n");
    }
}

int getFileSize(FILE* fPointer)
{
    fseek(fPointer, 0, SEEK_END);

    return ftell(fPointer) / sizeof(student_t);
}
