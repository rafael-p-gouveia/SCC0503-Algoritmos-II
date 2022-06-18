#ifndef DATABASE_MANAGEMENT_H
#define DATABASE_MANAGEMENT_H
/*
 * Author: Maxsuel F. de Almeida <maxsuelfa@usp.br>
 * Author: Rafael Pereira Golveia <rafael.p.gouveia@usp.br>
 */

#define OPERATION_LENGTH 7
#define INSERT 0
#define SEARCH 1
#define UPDATE 2
#define EXIT -1
#define NO_OPERATION -2

int get_operation(void);
#endif
