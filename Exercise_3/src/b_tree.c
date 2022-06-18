/*
 * Functions to create and manipulate a B-TREE.
 * Author: Maxsuel F. de Almeida <maxsuelfa@usp.br>
 * Author: Rafael Pereira Golveia <rafael.p.gouveia@usp.br>
 */
#include <stdlib.h>
#include "../include/b_tree.h"
#include "../include/page.h"
#include "../include/utils.h"

/* Auxiliar defines */
#define EMPTY_SLOT -1
#define EMPTY_TREE_RRN -1
#define LONG_SIZE sizeof(long)
#define MINIMUN_DEGREE 10



/** Function to create a b-tree.
 *  Create a b-tree containing just an empty page.
 *
 *  @param btreePath path to the b-tree file.
 **/
void create_b_tree(char *bTreePath)
{
    page_t *newPage;
    long rootRRN;
    char garbage;
    size_t freeSpace;
    FILE *bTree;

    newPage = create_page(2 * MINIMUN_DEGREE - 1);
    newPage->isLeaf = TRUE;
    newPage->numOfKeys = 0;

    rootRRN = 1;
    garbage = GARBAGE;
    freeSpace = PAGE_SIZE - LONG_SIZE;

    bTree = open_file(bTreePath, "wb");

    // Write the header of the b-tree file.
    fseek(bTree, 0, SEEK_SET);
    fwrite(&rootRRN, LONG_SIZE, 1, bTree);
    fwrite(&garbage, sizeof(char), freeSpace, bTree);

    // Write an empty page in the b-tree file.
    write_page(newPage, 1, 2 * MINIMUN_DEGREE - 1, bTree);

    fclose(bTree);
}

/** Function to insert a key in the b-tree.
 *  Given a key, inserts it in the b-tree.
 *
 *  @param filePath path of the b-tree file.
 *  @param key the key to be inserted.
 *  @return short 1 if success and 0 otherwise.
 **/
short b_tree_insert(char *filePath, int key)
{
    FILE *filePointer;
    page_t *rootPage;
    long rootRRN;

    // Read the root page.
    filePointer = open_file(filePath, "rb+");
    rootRRN = get_root_RRN(filePointer);
    rootPage = read_page(rootRRN, 2 * MINIMUN_DEGREE - 1, filePointer);

    if (rootPage->numOfKeys == 2 * MINIMUN_DEGREE - 1)
    {
        page_t *newRootPage;
        newRootPage = create_page(2 * MINIMUN_DEGREE - 1);

        newRootPage->isLeaf = FALSE;
        newRootPage->numOfKeys = 0;
        newRootPage->children[0] = rootRRN;


        // Writes the RRN of the new root in the header of the file.
        fseek(filePointer, 0, SEEK_END);
        rootRRN = ftell(filePointer) / PAGE_SIZE;
        fseek(filePointer, 0, SEEK_SET);
        fwrite(&rootRRN, LONG_SIZE, 1, filePointer);

        split_child(newRootPage, rootRRN, 0, filePointer);
        insert_nonfull(newRootPage, key, rootRRN, filePointer);

        destroy_page(newRootPage);
    }
    else
    {
        insert_nonfull(rootPage, key, rootRRN, filePointer);
    }

    destroy_page(rootPage);
    fclose(filePointer);

    return SUCCESS;
}

/** Function to insert a key in a nonfull page of the b-tree.
 *  Given a key, inserts it in a nonfull page of the b-tree.
 *
 *  @param currentPage page where the key will be inserted.
 *  @param key the key to be inserted.
 *  @param currPageRRN the RRN of the page.
 *  @param filePointe pointer to the b-tree file.
 **/
void insert_nonfull(page_t* currentPage, int key, long currPageRRN\
                    , FILE *filePointer)
{
    page_t *nextPage;
    int index;

    index = currentPage->numOfKeys - 1;

    if (currentPage->isLeaf)
    {
        while (index >= 0 && key < currentPage->records[index].key)
        {
            currentPage->records[index + 1].key = currentPage->records[index].key;
            index--;
        }

        currentPage->records[index + 1].key = key;
        currentPage->numOfKeys++;

        write_page(currentPage, currPageRRN, 2 * MINIMUN_DEGREE - 1\
                   , filePointer);
    }
    else
    {
        while (index >= 0 && key < currentPage->records[index].key)
            index--;
        index++;
        nextPage = read_page(currentPage->children[index], 2 * MINIMUN_DEGREE - 1, filePointer);

        if (nextPage->numOfKeys == 2 * MINIMUN_DEGREE - 1)
        {
            split_child(currentPage, currPageRRN, index, filePointer);

            if (key > currentPage->records[index].key)
            {
                index++;
                destroy_page(nextPage);
                nextPage = read_page(currentPage->children[index]\
                                     , 2 * MINIMUN_DEGREE - 1, filePointer);
            }
        }


        insert_nonfull(nextPage, key, currentPage->children[index]\
                       , filePointer);

        destroy_page(nextPage);
    }
}

/** Functio to split a full page.
 *  Given a nonfull page and the index for a full child,
 *  splits this child into two pages and promotes the middle
 *  key of the full child.
 *
 *  @param parentPage the parent of the page to be splited.
 *  @param parentRRN the RRN of the parent page.
 *  @param childIndex the index of the child to be splited.
 *  @param filePointer pointer to the b-tree file.
 **/
void split_child(page_t *parentPage, long parentRRN, int childIndex\
                 , FILE *filePointer)
{
    page_t *childrenPages[2];
    int auxIndex;
    long auxRRN;


    childrenPages[0] = read_page(parentPage->children[childIndex]\
                                 , 2 * MINIMUN_DEGREE - 1, filePointer);

    childrenPages[1] = create_page(2 * MINIMUN_DEGREE - 1);

    childrenPages[1]->isLeaf = childrenPages[0]->isLeaf;
    childrenPages[1]->numOfKeys = MINIMUN_DEGREE - 1;

    for (int i = 0; i < MINIMUN_DEGREE - 1; ++i)
    {
        auxIndex = i + MINIMUN_DEGREE;
        childrenPages[1]->records[i].key\
            = childrenPages[0]->records[auxIndex].key;
    }

    if (!childrenPages[0]->isLeaf)
    {
        for (int i = 0; i < MINIMUN_DEGREE; ++i)
        {
            auxIndex = i + MINIMUN_DEGREE;
            childrenPages[1]->children[i] = childrenPages[0]->children[auxIndex];
        }
    }
    childrenPages[0]->numOfKeys = MINIMUN_DEGREE - 1;

    for (int i = parentPage->numOfKeys; i >= childIndex + 1; --i)
    {
        parentPage->children[i + 1] = parentPage->children[i];
    }

    for (int i = parentPage->numOfKeys - 1; i >= childIndex; --i)
    {
        parentPage->records[i + 1].key = parentPage->records[i].key;
    }

    parentPage->records[childIndex].key\
        = childrenPages[0]->records[MINIMUN_DEGREE - 1].key;
    parentPage->numOfKeys++;

    // Get the RRN where the new child page will be written.
    auxRRN = get_end_of_file_RRN(filePointer) + 1;

    // Write the parent page in disc.
    parentPage->children[childIndex + 1] = auxRRN;
    write_page(parentPage, parentRRN, 2 * MINIMUN_DEGREE - 1, filePointer);

    // Write one of the children page in the disc.
    write_page(childrenPages[0], parentPage->children[childIndex]\
               , 2 * MINIMUN_DEGREE - 1, filePointer);

    // Write the other chilndren page in the disc.
    //auxRRN = get_end_of_file_RRN(filePointer);
    write_page(childrenPages[1], auxRRN, 2 * MINIMUN_DEGREE - 1, filePointer);

}

/** Funciton to search for a key in the b-tree.
 *  Given a key, search for it in the b-tree file.
 *  In case of success, return the record containing the key,
 *  otherwise, return NULL.
 *
 *  @param key the key to be searched.
 *  @return record_t the record cotaining the key.
 **/
record_t *b_tree_search(int key)
{
    FILE *filePointer;
    page_t *rootPage;
    record_t * record;
    long rootRRN;

    filePointer = open_file(BTREE_PATH, "rb");

    rootRRN = get_root_RRN(filePointer);
    rootPage = read_page(rootRRN, 2 * MINIMUN_DEGREE - 1, filePointer);

    record = recursion_search(rootPage, key, filePointer);

    fclose(filePointer);
    destroy_page(rootPage);

    return record;
}

/** Function to search for a key in the b-tree.
 *  Given a key, search recursively for it in the b-tree file.
 *  In case of success, return the record containing the key,
 *  otherwise, return NULL.
 *
 *  @param currentPage the current page where the key is being
 *  searched.
 *  @param key the key to be searched.
 *  @param filePointer pointer to the b-tree file.
 *  @return record_t the record cotaining the key.
 **/
record_t *recursion_search(page_t *currentPage, int key, FILE *filePointer)
{
    page_t *nextPage;
    record_t *record;
    int index;

    index = 0;

    while (index <= currentPage->numOfKeys - 1\
            && key > currentPage->records[index].key)
    {
        index++;
    }

    if (index <= currentPage->numOfKeys - 1\
            && key == currentPage->records[index].key)
    {
        record = (record_t *)malloc(sizeof(record_t));
        record->key = currentPage->records[index].key;
        record->RRN = currentPage->records[index].RRN;
        return record;
    }
    else if (currentPage->isLeaf)
    {
        return NULL;
    }
    else
    {
        nextPage = read_page(currentPage->children[index]\
                             , 2 * MINIMUN_DEGREE - 1, filePointer);

        return recursion_search(nextPage, key, filePointer);
    }
}

/** Function to get the RRN of the root.
 *  Given the pointer to the b-tree file, this function
 *  gets the RRN of the b-tree's root.
 *
 *  @param bTree pointer to the b-tree file.
 *  @return long the RRN of the root.
 **/
long get_root_RRN(FILE *bTree)
{
    long rootRRN;

    fseek(bTree, 0, SEEK_SET);
    fread(&rootRRN, LONG_SIZE, 1, bTree);

    return rootRRN;
}
