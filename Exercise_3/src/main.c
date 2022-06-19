/**
 * Main function.
 * Author: Maxsuel F. de Almeida <maxsuelfa@usp.br>
 * Author: Rafael Pereira Golveia <rafael.p.gouveia@usp.br>
 **/
#include <stdlib.h>
#include "../include/page.h"
#include "../include/utils.h"
#include "../include/b_tree.h"
#include "../include/database_management.h"

#define DATABASE_PATH "../files/db.dat"

int main(void)
{
    FILE *database;
    int operation, key;
    student_t *student;
    record_t *record;
    page_t *page;

    create_database(DATABASE_PATH);
    create_b_tree(BTREE_PATH);

    while ((operation = get_operation()) != EXIT)
    {
        switch (operation)
        {
        case INSERT:
            student = get_record_from_input();
            record = b_tree_search(student->NUSP);

            if (record)
            {
                printf("O Registro ja existe!\n");
            }
            else
            {
                record = database_insert(student, DATABASE_PATH);
                b_tree_insert(BTREE_PATH, record);
            }
            break;
        case SEARCH:
            scanf("%d", &key);
            record = b_tree_search(key);

            if (!record)
            {
                printf("Registro nao encontrado!\n");
            }
            else
            {
                student = read_record(record->RRN, DATABASE_PATH);
                print_record(student);
            }
            break;
        case UPDATE:
            student = get_record_from_input();
            record = b_tree_search(student->NUSP);

            if (!record)
            {
                printf("Registro nao encontrado!\n");
            }
            else
            {
                database_update(student, record->RRN, DATABASE_PATH);
            }
            break;
        case EXIT: break;
        }
    }

    // Free record if it was allocated
    if(record)
    {
        free(record);
    }

    // Free student if it was allocated
    if(student)
    {
        free(student);
    }

    return 0;
}
