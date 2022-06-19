#ifndef PAGE_H
#define PAGE_H
/*
 * Author: Maxsuel F. de Almeida <maxsuelfa@usp.br>
 * Author: Rafael Pereira Golveia <rafael.p.gouveia@usp.br>
 */
#include "../include/utils.h"

/* Defines the page (or node) of the b-tree */
typedef struct page
{
    record_t *records;
    long *children;
    int numOfKeys;
    bool isLeaf;
} page_t;

page_t *create_page(int);
page_t *read_page(long, int, FILE *);
void write_page(page_t *, long, int, FILE *);
void destroy_page(page_t *);
#endif
