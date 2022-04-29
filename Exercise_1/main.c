#include <stdio.h>
#include <stdlib.h>
#include "input_2_bin.h"

#define FILE_PATH "output.bin"

//Programa por Maxsuel Fernandes de Almeida, de nUSP  e Rafael Pereira de Gouveia, de nUSP 11800820

int main(void)
{
    student_t **student;

    student = (student_t**)malloc(sizeof(student_t*));

    FILE* fp = fopen(FILE_PATH, "wb");
    while (readRegFromInput(student) != EOF)
        writeRegister(fp, *student);
    fclose(fp);

    fp = fopen(FILE_PATH, "rb");
    showLastTen(fp);
    fclose(fp);
    
    free(*student);
    free(student);

    return 0;
}
