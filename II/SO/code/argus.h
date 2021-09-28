#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <string.h>
#include <sys/wait.h>
#include <unistd.h>
#include <fcntl.h>
#include <limits.h>
#include <stdlib.h>


#define LOG "log"
#define LOG_IDX "log.idx"
#define FIFO_SC "fifoSC"
#define FIFO_CS "fifoCS"
#define MAX_BUFFER 1024

