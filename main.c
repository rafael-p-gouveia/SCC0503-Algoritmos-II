#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define PATH "arquivo.txt"
#define N_CAMPOS_DESEJADO 10

typedef struct _aluno{
    int nUSP;
    char nome[50],curso[50];
    float nota;
}aluno;

FILE* abreArquivo(char path[],char modo[]){
    FILE* retval;

    retval = fopen(path,modo);
    if(retval == NULL){
        printf("Falha ao abrir arquivo");
        exit(1);
    }

    return retval;
}

aluno lerEntrada(int *acabou){
    char buffer,word[50];
    int i,j;
    static aluno input;

    i = 0;
    j=0;
    *acabou = 0;
    //1,Winter Y. Hodges,Engenharia da Computacao,5
    while(1){
        buffer = getc(stdin); //ler um caracter
        if (buffer == ','){ //acabou coluna
            word[i] = '\0'; //finaliza string
            i = 0;//comeca proximo campo do comeco
            if(j == 0){
                input.nUSP = (int)atoi(word);
            }
            else if(j == 1){
                strcpy(input.nome,word);
            }
            else{
                strcpy(input.curso,word);
            }
            j++;
        }
        else if(buffer == '\n'|| buffer == EOF || buffer == '$'){ //ultimo campo da linha ou do input como um todo
            word[i] = '\0';
            i = 0;
            input.nota = atof(word);
            j = 0;
            if(buffer == EOF || buffer == '$'){
                *acabou = 1;
            }
            break;
        }
        else{
            word[i] = buffer;
            i++;
        }
    }

    //printf("%d %s %s %f\n\n\n",input.nUSP, input.nome, input.curso, input.nota);
}

int main(){
    FILE* arquivo;
    int *acabou;
    aluno *arrAlunos;

    acabou = (int*)malloc(sizeof(int));
    if(acabou == NULL){
         printf("Falha ao alocar memoria...");
         exit(1);
    }

    *acabou = 0;
    arquivo = abreArquivo(PATH,"ab");
    while(*acabou == 0){
        armazenaEntrada(lerEntrada(acabou),arquivo);
    }
    fclose(arquivo);

    arquivo = abreArquivo(PATH,"rb");
    //arrAlunos = lerUltimasEntradas(N_CAMPOS_DESEJADO,arquivo); func que le as ultimas 10 entradas
    //imprimeCampos(arrAlunos,N_CAMPOS_DESEJADO); func que imprime uma array de alunos
    //fclose(arquivo);

    return 0;
}