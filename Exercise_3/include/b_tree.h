#ifndef B_TREE_H
#define B_TREE_H
/*
 * Author: Maxsuel F. de Almeida <maxsuelfa@usp.br>
 * Author: Rafael Pereira Golveia <rafael.p.gouveia@usp.br>
 */
#include <stdio.h>
#include "../include/page.h"

void create_b_tree(char *);
short b_tree_insert(char *, int);
void insert_nonfull(page_t *, int, long, FILE *);
void split_child(page_t *, long, int, FILE *);
record_t *b_tree_search(int);
record_t *recursion_search(page_t *, int, FILE *);
long get_root_RRN(FILE *);

#endif
