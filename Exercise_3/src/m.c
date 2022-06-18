/**
 * Main function.
 * Author: Maxsuel F. de Almeida <maxsuelfa@usp.br>
 * Author: Rafael Pereira Golveia <rafael.p.gouveia@usp.br>
 **/
#include <stdlib.h>
#include "../include/page.h"
#include "../include/utils.h"
#include "../include/b_tree.h"

int main(void)
{
    page_t *p;
    page_t *q;

    FILE *f;

    create_b_tree("../files/b_tree.dat");

    //f = open_file("../files/b_tree.dat", "rb+");
    //fseek(f, 0, SEEK_SET);

    //long a;

    //fread(&a, sizeof(a), 1, f);

    //printf("%ld\n", a);
    b_tree_insert("../files/b_tree.dat", 100);
    b_tree_insert("../files/b_tree.dat", 6);
    b_tree_insert("../files/b_tree.dat", 56);
    b_tree_insert("../files/b_tree.dat", 16);
    b_tree_insert("../files/b_tree.dat", 8);
    b_tree_insert("../files/b_tree.dat", 60);
    b_tree_insert("../files/b_tree.dat", 150);
    b_tree_insert("../files/b_tree.dat", 160);
    b_tree_insert("../files/b_tree.dat", 15);
    b_tree_insert("../files/b_tree.dat", 32);
    b_tree_insert("../files/b_tree.dat", 30);
    b_tree_insert("../files/b_tree.dat", 1000);

    /**f = open_file("../files/b_tree.dat", "rb");



    for (int i = 1; i <= 12; i++)
    {
        printf("pag %d:\n", i);
        p = read_page(i, 3, f);
        for (int i = 0; i < p->numOfKeys; i++)
        {
            printf("%d\n", p->records[i].key);
        }
        printf("numkeys=%d\n", p->numOfKeys);
        printf("leaf=%d\n", p->isLeaf);
        destroy_page(p);
    }
    */

    record_t *r, *s;

    r = b_tree_search(160);
    if (r)
        printf("key = %d\n", r->key);
    else
        printf("Key not found\n");

    s = b_tree_search(5);
    if (s)
        printf("key = %d\n", s->key);
    else
        printf("Key not found\n");

    /*long a = get_root_RRN(f);

    p = read_page(a, 3, f);

    for (int i = 0; i < p->numOfKeys; i++)
    {
        printf("%d\n", p->records[i].key);
    }
    */
    /* p = read_page(0, 3, f);
     fclose(f);
     for (int i = 0; i < 3; i++)
     {
         printf("%d\n", p->records[i].key);
     }

     printf("%d\n", p->numOfKeys);
     */
    /*
    q = create_page(3);

    for (int i = 0; i < 3; i++)
    {
        q->records[i].key = 9;
        q->records[i].RRN = 123;
    }

    for (int i = 0; i <= 3; i++)
    {
        q->children[i] = 345;
    }

    q->numOfKeys = 3;
    q->isLeaf = 1;

    f = open_file("../files/test.dat", "wb+");
    write_page(q, 0, 3, f);

    for (int i = 0; i < 3; i++)
    {
        q->records[i].key = 2;
        q->records[i].RRN = 111;
    }

    for (int i = 0; i <= 3; i++)
    {
        q->children[i] = 111;
    }

    q->numOfKeys = 4;
    q->isLeaf = 1;


    write_page(q, 1, 3, f);
    fclose(f);

    f = open_file("../files/test.dat", "rb+");

    p = read_page(1, 3, f);
    fclose(f);

    for (int i = 0; i < 3; i++)
    {
        printf("%d\n", p->records[i].key);
    }

    for (int i = 0; i < 3; i++)
    {
        printf("%d\n", p->records[i].RRN);
    }

    for (int i = 0; i <= 3; i++)
    {
        printf("%ld\n", p->children[i]);
    }

    printf("%d\n", p->numOfKeys);
    printf("%d\n", p->isLeaf);
    */
    return 0;
}
