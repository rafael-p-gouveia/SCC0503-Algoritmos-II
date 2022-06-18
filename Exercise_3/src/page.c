/* Definition of the page ADT.
 * Author: Maxsuel F. de Almeida <maxsuelfa@usp.br>
 * Author: Rafael Pereira Golveia <rafael.p.gouveia@usp.br>
 */
#include <stdlib.h>
#include "../include/page.h"

/** Constructor of the page type.
 *  Given the maximum number of keys that the page can hold
 *  creates an empty page.
 *
 *  @param maxNumOfKeys the maximum number of keys that the
 *  page can hold.
 *  @return page_t pointer to the created page.
 **/
page_t *create_page(int maxNumOfKeys)
{
    page_t *page;

    page = (page_t *)malloc(sizeof(page_t));

    if (!page)
    {
        printf("Error in allocate memory for the page\n");
        printf("The program will be closed...");
        exit(EXIT_FAILURE);
    }

    page->records = (record_t *)malloc(maxNumOfKeys * sizeof(record_t));
    page->children = (long *)malloc((maxNumOfKeys + 1) * sizeof(long));

    if (!page->records || !page->children)
    {
        printf("Error in allocate memory for the page\n");
        printf("The program will be closed...");
        exit(EXIT_FAILURE);
    }

    for (int i = 0; i < maxNumOfKeys; ++i)
    {
        page->records[i].key = INITIALIZER;
    }

    return page;
}

/** Function to read a page from disc.
 *  Given the RRN of a page, reads it from the disc.
 *
 *  @param RRN the RRN of the page to be read.
 *  @param maxNumOfKeys the maximum number of keys that the
 *  page can hold.
 *  @param filePointer pointer to the file where the page will
 *  be read from.
 *
 *  @return page_t the page read.
 **/
page_t *read_page(long RRN, int maxNumOfKeys, FILE *filePointer)
{
    page_t *page;

    page = create_page(maxNumOfKeys);

    fseek(filePointer, RRN * PAGE_SIZE, SEEK_SET);

    for (int i = 0; i < maxNumOfKeys; i++)
    {
        fread(&(page->records[i].key), sizeof(page->records[i].key), 1\
              , filePointer);
    }

    for (int i = 0; i < maxNumOfKeys; i++)
    {
        fread(&(page->records[i].RRN), sizeof(page->records[i].RRN), 1\
              , filePointer);
    }

    for (int i = 0; i <= maxNumOfKeys; i++)
    {
        fread(&(page->children[i]), sizeof(page->children[i]), 1\
              , filePointer);
    }

    fread(&(page->numOfKeys), sizeof(page->numOfKeys), 1, filePointer);
    fread(&(page->isLeaf), sizeof(page->isLeaf), 1, filePointer);

    return page;
}

/** Function to write a page in a file.
 *  Given a page, its RRN and a pointer to a file, writes the
 *  page in such file.
 *
 *  @param page pointer to the page to be written.
 *  @param maxNumOfKeys the maximum number of keys that the
 *  page can hold.
 *  @param filePointer pointer to the file where the page will
 *  be written.
 *
 *  @return void.
 **/
void write_page(page_t *page, long RRN, int maxNumOfKeys, FILE *filePointer)
{
    int freeSpace;
    char garbage;

    fseek(filePointer, RRN * PAGE_SIZE, SEEK_SET);

    for (int i = 0; i < maxNumOfKeys; i++)
    {
        fwrite(&(page->records[i].key), sizeof(page->records[i].key), 1\
               , filePointer);
    }

    for (int i = 0; i < maxNumOfKeys; i++)
    {
        fwrite(&(page->records[i].RRN), sizeof(page->records[i].RRN), 1\
               , filePointer);
    }

    for (int i = 0; i <= maxNumOfKeys; i++)
    {
        fwrite(&(page->children[i]), sizeof(page->children[i]), 1\
               , filePointer);
    }

    fwrite(&(page->numOfKeys), sizeof(page->numOfKeys), 1, filePointer);
    fwrite(&(page->isLeaf), sizeof(page->isLeaf), 1, filePointer);

    freeSpace\
        = PAGE_SIZE\
          - maxNumOfKeys * sizeof(int)\
          - maxNumOfKeys * sizeof(long)\
          - (maxNumOfKeys + 1) * sizeof(long)\
          - sizeof(int) - sizeof(bool);

    garbage = GARBAGE;

    // Writes empty space in the file
    fwrite(&garbage, sizeof(garbage), freeSpace, filePointer);
}

/** Destructor of the page type.
 *
 *  Free all memory allocated for the page.
 *
 *  @param page page to be freed.
 **/
void destroy_page(page_t *page)
{
    free(page->records);
    free(page->children);
    free(page);
}
