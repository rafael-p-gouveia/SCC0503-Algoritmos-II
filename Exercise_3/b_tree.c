#include "b_tree.h"
#include <stdlib.h>

#define BTREE_PATH "bTree.dat"
#define EMPTY_TREE_RRN -1
#define PAGESIZE 4096
#define KEY_NUMBER
#define EMPTY_SLOT -1

typedef struct _tree_page{
    int key[KEY_NUMBER];
    long int RRN[KEY_NUMBER], child[KEY_NUMBER + 1];
    short int isLeaf;
}tree_page;

int find_key_position(int key, int *arrayKeys, int *isInNode){
    int i;

    i = 0;
    *isInNode = 0;
    while(i < KEY_NUMBER && arrayKeys[i] != EMPTY_SLOT){
        if(arrayKeys[i] == key){
            *isInNode = 1;
            return i;
        }
        else{
            if(arrayKeys[i] > key){
                return i;
            }
            i++;
        }
    }
    return i;
}

long int recursion_search(int key, long int nodeRRN, FILE* bTree){ //talvez mude depois pra pegar soh o necessario
    tree_page currentNode;//ta tudo na mesma pagina, entao um seek soh deve bastar, de qlqr forma
    int keyPosition, isInNode;
    long int nextNodeRRN;

    fseek(bTree,nodeRRN*PAGESIZE,SEEK_SET);
    fread(currenNode.key,sizeof(int),KEY_NUMBER,bTree);
    fread(currentNode.RRN,sizeof(long int),KEY_NUMBER,bTree);
    fread(currentNode.child,sizeof(long int),KEY_NUMBER+1,bTree);
    fread(&(currentNode.isLeaf),sizeof(short int),1,bTree);

    keyPosition = find_key_position(key,currentNode.key,&isInNode);
    if(isInNode){
        return currentNode.RRN[keyPosition];
    }
    else{
        nextNodeRRN = currentNode.child[keyPosition];
        if(nextNodeRRN == EMPTY_SLOT){ //poderia substituir por if currentNode.isLeaf == 1
            return ENTRY_NOT_FOUND;
        }
        else{
            return recursion_search(key,nextNodeRRN,bTree);
        }
    }
}

long int search_bTree(int key){
    FILE* bTree;
    long int nodeRRN, retval;

    bTree = fopen(BTREE_PATH,"rb+");
    if(bTree == NULL){
        exit(EXIT_FAILURE);
    }

    fread(&RRN, sizeof(long int), 1, bTree);
    if(nodeRRN == EMPTY_TREE_RRN){
        return ENTRY_NOT_FOUND;
    }

    retval = recursion_search(key,nodeRRN,bTree);
    fclose(bTree);

    return retval;
}

void create_b_tree(){
    FILE* bTree;
    long int aux;

    bTree = fopen(BTREE_PATH,"wb+");
    if(bTree == NULL){
        exit(EXIT_FAILURE);
    }

    aux = EMPTY_TREE_RRN;
    fwrite(&aux,sizeof(long int),1,bTree);
}

