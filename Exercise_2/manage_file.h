#ifndef MANAGE_FILE
#define MANAGE_FILE

// Struct to hold the students' information
typedef struct _student student;

//function receives input from user, indicating the desired operation, and returns respective code.
int get_op();

//function calls the functions to execute the desire operation after receiving another parameter, if needed.
void select_op(int nOP);
//function reads and parses one field of information and returns it as string. Set the passed pointer with the size of the returned string
char* get_insertion_field(int *err, int *sizeFields);
//function mounts a student struct from the user inputs and returns it.
student get_full_insertion_input();
//function prints the data from an array of student structs.
void print_fields(student *arrStudents, int nFields);
//function returns a pointer to a student struct with the data from the student with the nUSP passed as parameter. Prints appropriate message if fails.
student* search_student(int key);
//insert student data entry in the database.
void insert_student(student input);
//marks student entry from the database for deletion. The data gets inaccessible immediately.
void delete_entry(int key);
//searches index for one entry with the nUSP passed as parameter. If succedes, returns the it's rrn and assigns the passed pointer with the db offset stored in the index entry. Returns -1 if it fails.
int find_entry_index(int key, int *respectiveOffset);
//create the index and db files needed for the system to operate.
void create_files();
#endif // MANAGE_FILE
