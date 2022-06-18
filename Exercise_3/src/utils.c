/*
 * Author: Maxsuel F. de Almeida <maxsuelfa@usp.br>
 * Author: Rafael Pereira Golveia <rafael.p.gouveia@usp.br>
 */
#include <stdlib.h>
#include "../include/utils.h"
#define PAGE_SIZE_U 4096

/** Function to open a file.
 *  Given a path, open the respective file or create one
 *  in case the file does not existe.
 *
 *  @param filePath path of the file.
 *  @param mode mode of opening.
 *  @return FILE pointer to the opened file.
 **/
FILE *open_file(char *filePath, char *mode)
{
    FILE *filePointer;

    filePointer = fopen(filePath, mode);

    if (!filePointer)
    {
        printf("Error in opening the file\nThe program will be closed...");
        exit(EXIT_FAILURE);
    }

    return filePointer;
}

/** Function to get the RRN of the end of the file.
 *  Given a file, gets its end of file RRN.
 *
 *  @param filePointer pointer to the file.
 *  @return long the RRN of the end of the file.
 **/
long get_end_of_file_RRN(FILE *filePointer)
{
    long endRRN;

    fseek(filePointer, 0, SEEK_END);
    endRRN = (long) ftell(filePointer) / PAGE_SIZE_U;

    return endRRN;
}
