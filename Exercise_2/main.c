/******************************************************************************
 * Author: Rafael Pereira de Gouveia                                          *
 * NUSP: 11800820                                                             *
 *                                                                            *
 * Author: Maxsuel Fernandes de Almeida                                       *
 * NUSP: 11801028                                                             *
 *                                                                            *
 ******************************************************************************/

#include "manage_file.h"
#include <stdio.h>


int main(){
    int nOP;

    create_files();

    do{
        nOP = get_op();
        select_op(nOP);
    }while(nOP != -1);
    return 0;
}
