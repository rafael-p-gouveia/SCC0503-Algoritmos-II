/*
 * Functions manipulate the student's database.
 * Author: Maxsuel F. de Almeida <maxsuelfa@usp.br>
 * Author: Rafael Pereira Golveia <rafael.p.gouveia@usp.br>
 */
#include <string.h>
#include "../include/database_management.h"


/** Function to get the operation to be executed.
 *  Get the database operation to be executed and
 *  return such operation.
 *
 *  @return int operation to be executed.
 **/
short get_operation(void)
{
    char operation[OPERATION_LENGTH];

    scanf("%s", operation);

    if (strcmp(operation, "insert") == 0)
    {
        return INSERT;
    }
    else if (strcmp(nameOP, "search") == 0)
    {
        return SEARCH;
    }
    else if (strcmp(nameOP, "update") == 0)
    {
        return UPDATE;
    }
    else if(strcmp(operation, "exit") == 0)
    {
        return EXIT;
    }

    return NO_OPERATION;
}





