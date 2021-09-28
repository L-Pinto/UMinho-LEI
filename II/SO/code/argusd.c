#include "argus.h"

int maximoExc = 0;
int inatividade = 0;
int files_count;
int atual = 0;
int* pids;
int estado[MAX_BUFFER];
int processos[MAX_BUFFER];

void timeout_handler(int signum)  {
    for (int i = 0; i < files_count; i++) {
		if(pids[i] > 0) {
			kill(pids[i],SIGTERM);
		}
    }
}

void usr1_handler(int signal) {
	if(inatividade != 0) alarm(inatividade);
}

void filhoPai_handler(int signal){
	int status;
	wait(&status);
	estado[WEXITSTATUS(status)] = 1;
}

int execCommand(char * arg) {
	int i = 0;
	char* commands[100];
	
	char* command;

	command = strtok(arg," ");

	while(command != NULL){
		commands[i] = command;
		command = strtok(NULL," ");
		i++;
	}

	commands[i] = NULL;

	int res = execvp(commands[0],commands);

	return res;
}

int execPipe(char * arg, int descritor) {
	char* args[100];
	int i = 0;
	int pid;
	int stat;

	char* command;

	command = strtok(arg,"|");

	while(command != NULL) {
		args[i] = command;
		command = strtok(NULL,"|");
		i++;
    }

	int pipes[i-1][2];

	files_count = i;
	int inc = 0;
	pids = malloc(sizeof(int) * files_count);

	if(signal(SIGUSR1,usr1_handler) == SIG_ERR){
		 perror("urs1 handler");
	}//inatividade

	if(signal(SIGALRM, timeout_handler) == SIG_ERR) {
        perror("timeout handler");
    }//tempo maximo de execução

	if(maximoExc != 0) alarm(maximoExc);

	if (i == 1) 
    {
		switch(pid = fork()) 
        {
			case -1: 
				perror("fork");
				return -1;
			case 0:
				dup2(descritor,1);
				close(descritor);
				execCommand(args[0]);
				_exit(0);
			default:
				if (kill(pid,SIGUSR1) < 0) {
                    perror("SIGUSR1");
				}
				pids[inc++] = pid;
				waitpid(pid,&stat,0);
				break;
		}
	} else { 
		for(int c = 0; c < i; c++) 
        {
			if (c == 0) { //primeiro processo
				if(pipe(pipes[c])<0) exit(0);
				switch(pid = fork()) {
					case 0: 
						close(pipes[c][0]); 
						dup2(pipes[c][1],1); 
						close(pipes[c][1]);

						close(descritor);

						execCommand(args[c]);
                        _exit(0);
						break;
					case -1: 
						perror("fork 1º processo");
						return -1;
					default: 
						if (kill(pid,SIGUSR1) < 0) {
                        	perror("SIGUSR1");
						}
						pids[inc++] = pid;
						close(pipes[c][1]);
						waitpid(pid,&stat,0);
						break;
				}		
			
			} else if (c == i - 1) { //ultimo processo
				switch(pid = fork()) {
					case 0:
						dup2(pipes[c-1][0],0);
						close(pipes[c-1][0]);

						dup2(descritor,1);
						close(descritor);

						execCommand(args[c]);
						_exit(0);
					case -1:
						perror("Fork ultimo processo");
						return -1;
					default: 
						if (kill(pid,SIGUSR1) < 0) {
                    		perror("SIGUSR1");
						}
						pids[inc++] = pid;
						close(pipes[c-1][0]);
						waitpid(pid,&stat,0);
						break;	
				}
			
			} else {
				if(pipe(pipes[c])<0) exit(0);
				switch(pid = fork()) {
					case -1:
						perror("fork processos intermedios");
						return -1;
					case 0:
						close(pipes[c][0]); 
                        dup2(pipes[c][1],1); 
                        close(pipes[c][1]); 

                        dup2(pipes[c-1][0],0);
                        close(pipes[c-1][0]);

                        execCommand(args[c]);
                        _exit(0);
					default:
						if (kill(pid,SIGUSR1) < 0) {
							perror("SIGUSR1");
						}
						pids[inc++] = pid;
						close(pipes[c][1]);
						close(pipes[c-1][0]);
						waitpid(pid,&stat,0);
						break;	
				}		
			}
		}
	}

	free(pids);
		
	return 0;
}


int main(int argc, char* argv[])
{
	mkfifo(FIFO_CS, 0640);
	int log = open(LOG,O_CREAT | O_RDWR | O_TRUNC, 0666);
    int log_idx = open(LOG_IDX,O_CREAT | O_RDWR | O_TRUNC, 0666);
	char* tarefas[MAX_BUFFER];
	char buffer[MAX_BUFFER];
	int pid, count = 0;

	signal(SIGCHLD, filhoPai_handler);

       
    while(1)
    {
        mkfifo(FIFO_SC, 0640);
        int fd = open(FIFO_CS,O_RDONLY);
        int descritor = open(FIFO_SC,O_WRONLY);  

        if(read(fd,buffer,MAX_BUFFER)<0) break;

        switch (buffer[1])
        {
            case 'i':{
                char msgi[100];
                if(strtol(buffer+2,NULL,10) > 0) {
                	inatividade = (int)strtol(buffer+2,NULL,10);
					sprintf(msgi,"Tempo maximo de execução atualizado para %d\n", inatividade);
                	if(write(descritor,msgi,strlen(msgi))<0)break;
            	} else {
            		if(write(descritor,"Número inválido\n",strlen("Número inválido\n"))<0)break;
            	}

                break;
            }
            case 'm':{
				char msgm[100];
				if(strtol(buffer+2,NULL,10) > 0) {
                	maximoExc = (int)strtol(buffer+2,NULL,10);
					sprintf(msgm,"Tempo maximo de execução atualizado para %d\n", maximoExc);
                	if(write(descritor,msgm,strlen(msgm))<0)break;
            	} else {
            		if(write(descritor,"Número inválido\n",strlen("Número inválido\n"))<0)break;
            	}

                break;
            }
            case 'e': {
				count++;
				char nr_tarefa[30];

				sprintf(nr_tarefa,"PLog%d#",count);
				if(write(descritor,nr_tarefa,strlen(nr_tarefa))<0) break; //envia para o cliente o nº da tarefa

				sprintf(nr_tarefa,"nova tarefa #%d\n",count);
				if(write(descritor,nr_tarefa,strlen(nr_tarefa))<0) break;

				tarefas[atual] = strdup(buffer+2);

				estado[atual] = 0;

				if((pid = fork()) == 0)
				{
					int processo = atual;
					execPipe(buffer+2,descritor);
					_exit(processo); //nº de tarefa que foi terminada
				}
				processos[atual] = pid; //para a opção -t
				atual++;
                break;
            }
			case 'h':{
				if(write(descritor,"Ajuda!\n",8)<0) break; 
                if(write(descritor,"tempo-inactividade segs\n",25)<0)break; 
                if(write(descritor,"tempo-execucao segs\n",21)<0) break;
                if(write(descritor,"executar p1 | p2 ... | pn\n",27)<0) break;
                if(write(descritor,"listar\n",8)<0) break;
                if(write(descritor,"terminar numTarefa\n",20)<0)break;
                if(write(descritor,"historico\n",11)<0)break;
                if(write(descritor,"output numTarefa\n",18)<0)break;
				break;
			}
			case 'l':{
				int count=0;
				int i;
            	for(i = 0; i < atual; i++)
    			{
					if(estado[i]==0) 
					{
						char brr[MAX_BUFFER] = "";
						sprintf(brr,"#%d: %s\n", i+1,tarefas[i]);
						if(write(descritor,brr,strlen(brr))<0) break; 
						count++;
					}		
    			}
				if(count == 0) if(write(descritor,"Não existem tarefas em execução\n",strlen("Não existem tarefas em execução\n"))<0)break;
				break;
			}
			case 'r':{
            	int count=0;
				int i;
            	for(i = 0; i < atual; i++)
    			{
					if(estado[i]==1) 
					{
						char brr[MAX_BUFFER] = "";
						sprintf(brr,"#%d: %s\n", i+1,tarefas[i]);
						if(write(descritor,brr,strlen(brr))<0)break; 
						count++;
					}		
    			}
				if(count == 0) if(write(descritor,"Não existem histórico de execução\n",strlen("Não existem histórico de execução\n"))<0)break;
				break;
			}
			case 't':{
                int trf = (int)strtol(buffer+2,NULL,10);
                if(trf>0 && trf <= atual) 
                {
                    if(estado[trf-1]==1){
						if(write(descritor, "Tarefa já terminada\n", strlen("Tarefa já terminada\n"))<0)break;
					} 
                    else{
                        estado[trf-1] = 1;
                        kill(processos[trf-1],SIGKILL);
                        if(write(descritor, "Tarefa terminada\n", strlen("Tarefa terminada\n"))<0)break;
                    }
                }
                else if(write(descritor, "Tarefa inválida\n", strlen("Tarefa inválida\n"))<0)break;
                break;
            }
			case 'o' :  {
				int nr_processo = (int)strtol(buffer+2,NULL,10); 
				char * nr_process_log = malloc(sizeof(char*));
				char * process;
				char bufferLog[2048];
				int fd_log_idx = open(LOG_IDX,O_RDWR,0666);

				lseek(fd_log_idx,0,SEEK_SET);

				if(read(fd_log_idx,bufferLog,2048)<0)break;

				sprintf(nr_process_log,"PLog%d",nr_processo);

				if(strstr(bufferLog,nr_process_log) == NULL) {
					if(write(descritor,"Não existe Output!\n",strlen("Não existe Output!\n"))<0)break;
					break;
				}

				process = strtok(bufferLog,"|"); 


				while(strstr(process,nr_process_log) == NULL) {
					process = strdup(strtok(NULL,"|"));
				}

				strtok(process," ");
				int inicio = (int) strtol(strtok(NULL," "),NULL,10);

				int bytes_wr = (int) strtol(strtok(NULL," "),NULL,10);

				if (bytes_wr == 0) break;

				int log1 = open(LOG,O_RDWR);
				char buffer_wr[bytes_wr];

				lseek(log1,inicio,SEEK_SET);

				if(read(log1,buffer_wr,bytes_wr)<0) break;

				if(write(descritor,buffer_wr,bytes_wr)<0)break;
				
				close(fd_log_idx);
				close(log1);
				break;
			}
            default:
                break;
        }
        close(descritor);
        close(fd);
		close(log);
		unlink(FIFO_SC);

    }
	close(log_idx);
	return 0;  
}