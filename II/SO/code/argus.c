#include "argus.h"

int main(int argc, char* argv[])
{    
    int cs = open(FIFO_CS, O_WRONLY);
    int sc = open(FIFO_SC, O_RDONLY);
    
    int n,i,max = argc, loop_READ;
    char buffer[MAX_BUFFER] = "";
    char buff[MAX_BUFFER] = "";
    
    char opcao;

    char loop_temp_buffer[2][25];

    if (argc < 2 ) {
        char buffLoop[MAX_BUFFER];
        if(write(1,"argus$ ", strlen("argus$ "))<0) exit(0);
        while((loop_READ = read(0,buffLoop,MAX_BUFFER)) > 0) { 
            char* command;
            char* argumentos;

            command = strtok(buffLoop," ");
            argumentos = strtok(NULL,"\n");

            if (strcmp(command,"tempo-inatividade") == 0 ) {
                strcpy(loop_temp_buffer[0],"-i");
                strcpy(loop_temp_buffer[1], argumentos);
            }
            else if (strcmp(command,"tempo-execucao") == 0 ) {
                strcpy(loop_temp_buffer[0],"-m");
                strcpy(loop_temp_buffer[1], argumentos);
            }
            else if (strcmp(command,"executar") == 0 ) 
            {
                strcpy(loop_temp_buffer[0],"-e");
             
                if(strstr(argumentos,"'") == NULL) {
                	strcpy(loop_temp_buffer[0],"-z");
                    if(write(1,"Comando invalido\n",strlen("Comando invalido\n"))<0)break;
                    break;
                }

                argumentos = strtok(argumentos+1,"'");
                strcpy(loop_temp_buffer[1], argumentos);
            }
            else if (strcmp(strtok(buffLoop,"\n"),"listar") == 0) strcpy(loop_temp_buffer[0],"-l");
            else if (strcmp(command,"terminar") == 0 ) {
                strcpy(loop_temp_buffer[0],"-t");

                if(argumentos == NULL) {
                	strcpy(loop_temp_buffer[0],"-z");
                    if(write(1,"Comando invalido\n",strlen("Comando invalido\n"))<0)break;
                    break;
                }

                strcpy(loop_temp_buffer[1], argumentos);
            }
            else if (strcmp(strtok(buffLoop,"\n"),"historico") == 0) strcpy(loop_temp_buffer[0],"-r");
            else if (strcmp(strtok(buffLoop,"\n"),"ajuda") == 0) strcpy(loop_temp_buffer[0],"-h");
            else if (strcmp(command,"output") == 0 ) {
                strcpy(loop_temp_buffer[0],"-o");

                if(argumentos == NULL) {
                	strcpy(loop_temp_buffer[0],"-z");
                    if(write(1,"Comando invalido\n",strlen("Comando invalido\n"))<0)break;
                    break;
                }

                strcpy(loop_temp_buffer[1], argumentos);
            }
            else if(write(1,"Comando invalido\n",strlen("Comando invalido\n"))<0)break; 

        break;
        } 
    }

    if (argc < 2) {
    	opcao = loop_temp_buffer[0][1]; 
    }   
    else {
    	opcao = argv[1][1];
    	if(opcao != 'l' && opcao != 'r' && opcao != 'h' && argc == 2) {
    		if(write(1,"Comando invalido\n",strlen("Comando invalido\n"))<0) exit(0);
    		opcao = 'z';
    		argv[1] = "-z";
    	} 
    }

    switch(opcao){
        case 'i' : max = 2; break;
        case 'm' : max = 2; break;
        case 'e' : max = 2; break;
        case 'l' : max = 1; break;
        case 't' : max = 2; break;
        case 'r' : max = 1; break;
        case 'h' : max = 1; break;
        case 'o' : max = 2; break;
        case 'z' : max = 1; break;
        default: break;
    }

    for (i = 1; i <= max; i++) {
        if (argc < 2) strcat(buff,loop_temp_buffer[i-1]);
        else strcat(buff,argv[i]); 
    }

    if(write(cs,buff,MAX_BUFFER)<0) exit(0);

//------------------------------------------------------------------//

    int log1 = open(LOG,O_RDWR,0666);
    int nr_b = 0,r = 0; 
    char* numero = "";

    while((n = read(sc,buffer,MAX_BUFFER))>0)
    {
       if(opcao == 'e') {
            if (r == 0) {
                numero = strdup(strtok(buffer,"#"));
                n-=strlen(numero);
                r = 1;
                lseek(log1,0,SEEK_END); 
                write(log1,buffer+strlen(numero),n);
                write(1,buffer+strlen(numero),n);
                nr_b += n;

            } else if(r == 1) {
                lseek(log1,0,SEEK_END); 
                if(write(log1,buffer,n)<0)break;
                if(write(1,buffer,n)<0)break;
                nr_b += n;
            }

       } else if (n > 0) if(write(1,buffer,n)<0)break;

    }

    if (opcao == 'e') {
        int logIdx = open(LOG_IDX,O_RDWR,0666);
        char buffLog[MAX_BUFFER];
        int nr_bytes = 0;
        int nr_read;

        lseek(logIdx,0,SEEK_SET);
        
        lseek(log1,0,SEEK_SET);
        while((nr_read = read(log1,buffLog,MAX_BUFFER))>0) {
            nr_bytes += nr_read;
        }

        nr_bytes -= nr_b; //nr_b -> nr bytes que ele escreveu

        char bytes[100];
    
        sprintf(bytes," %d %d | ",nr_bytes,nr_b); //nr_bytes -> onde ele começa a escrever
        strcat(numero,bytes);

        lseek(logIdx,0,SEEK_END);

        if(write(logIdx,numero,strlen(numero))<0) exit(0);
        close(logIdx);
    
    }
    
    close(log1);
    close(sc);
    close(cs);
    return 0;
}