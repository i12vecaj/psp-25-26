//#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>

void main(void)
{
   pid_t id_proceso;
   pid_t id_padre;

   id_proceso = getpid();
   id_padre = getppid();

   printf("Identificador de este proceso: %d\n", id_proceso);
   printf("Identificador del proceso padre: %d\n", id_padre);
}
/*
mj@ubuntu-mj:~$ gcc ejemploPadres.c -o ejemploPadres
mj@ubuntu-mj:~$ ./ejemploPadres
Identificador de este proceso: 2833
Identificador del proceso padre: 1923
mj@ubuntu-mj:~$ ps
  PID TTY          TIME CMD
 1923 pts/0    00:00:00 bash
 2834 pts/0    00:00:00 ps
mj@ubuntu-mj:~$ 

*/
