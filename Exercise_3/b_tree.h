#ifndef B_TREE
#define B_TREE

#define ENTRY_NOT_FOUND (long int)-1
long int search_bTree(int key);
long int recursion_search(int key, long int nodeRRN, FILE* bTree);
int find_key_position(int key, int *arrayKeys, int numberOfKeys, int *isInNode);
void add_b_tree(int key, long int RRN);
void create_b_tree();

#endif // B_TREE
