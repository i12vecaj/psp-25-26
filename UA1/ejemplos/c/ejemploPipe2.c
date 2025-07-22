#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <unistd.h>
int main()
{
 int  descF[2];
 char buffer[30];
 pid_t pid;

 pipe(descF);
 
 pid=fork(); 

 switch(pid) {
   case -1 : //ERROR
           printf("NO SE HA PODIDO CREAR HIJO...");
           exit(-1);
           break;
   case 0 : //HIJO
           printf("El HIJO escribe en el pipe...\n");
           write(descF[1], "Hola papi", 10);          
            break;   
   default : //PADRE
           wait(NULL);
           printf("El PADRE lee del pipe...\n");
           read(descF[0], buffer, 10);
           printf("\tMensaje leido: %s\n",buffer);
           
           break;
 }
}
/*
//con wait(NULL) antes
administrador@ubuntu1:~$ gcc ejemploPipe1.c -o ejemploPipe1
administrador@ubuntu1:~$ ./ejemploPipe1
El HIJO escribe en el pipe...
El PADRE lee del pipe...
	Mensaje leido: Hola papi
administrador@ubuntu1:~$ 



*/
