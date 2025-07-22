#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
void main() {
  pid_t pid, Hijo_pid;
  pid = fork();

  if (pid == -1 ) //Ha ocurrido un error 
  {
    printf("No se ha podido crear el proceso hijo...");
    exit(-1);       
  }
  if (pid == 0 )  //Nos encontramos en Proceso hijo 
  {        
    printf("Soy el proceso hijo \n\t Mi PID es %d, El PID de mi padre es: %d.\n",  
            getpid(), getppid() );	
  }
  else    //Nos encontramos en Proceso padre 
  { 
   Hijo_pid = wait(NULL); //espera la finalización del proceso hijo
   printf("Soy el proceso padre:\n\t Mi PID es %d, El PID de mi padre es: %d.\n\t Mi hijo: %d terminó.\n",  getpid(), getppid(), pid);          
   }
   exit(0);
}

/*
mj@mj-ubuntu12:~/Escritorio/ProgramasC$ gcc ejemplo1Fork.c -o ejemplo1Fork
mj@mj-ubuntu12:~/Escritorio/ProgramasC$ ./ejemplo1Fork
Soy el proceso hijo 
	 Mi PID es 4037, El PID de mi padre es: 4036.
Soy el proceso padre:
	 Mi PID es 4036, El PID de mi padre es: 3586.
	 Mi hijo: 4037 terminó.
mj@mj-ubuntu12:~/Escritorio/ProgramasC$ ps
  PID TTY          TIME CMD
 3586 pts/2    00:00:01 bash
 4044 pts/2    00:00:00 ps
mj@mj-ubuntu12:~/Escritorio/ProgramasC$ 


*/
