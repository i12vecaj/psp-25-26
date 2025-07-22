#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

void main(void)
{
   pid_t pid;
   int status;
  
   pid = fork();
   switch(pid)
   {
      case -1:   /* error del fork() */
         perror("fork");
         break;
      case 0:    /* proceso hijo */
        printf("Este es el Proceso Hijo %d; padre = %d \n", getpid(), getppid());
         break;
      default:   /* padre */
        wait(&status);
        printf("Este es el Proceso Padre %d;  Mi padre es = %d \n", getpid(), getppid());
   }
}
/*
mj@ubuntu-mj:~$ gcc ejemplo3Fork.c -o  ejemplo3Fork
mj@ubuntu-mj:~$ ./ejemplo3Fork
Este es el Proceso Hijo 3130; padre = 3129 
Este es el Proceso Padre 3129;  Mi padre es = 2920 
mj@ubuntu-mj:~$ sh
$ exit
mj@ubuntu-mj:~$ ps
  PID TTY          TIME CMD
 2920 pts/1    00:00:00 bash
 3132 pts/1    00:00:00 ps
mj@ubuntu-mj:~$ 

*/
