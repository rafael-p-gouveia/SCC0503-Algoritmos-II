#ifndef FILE_MANAGEMENT
#define FILE_MANAGEMENT

typedef struct _student student;

int get_op();
void select_op(int nOP);
char* get_insertion_field(int *err);
student get_full_insertion_input();
void print_fields(student *arrStudents, int nFields);
void insert_student(student newEntry);

void create_files();

#endif // FILE_MANAGEMENT
