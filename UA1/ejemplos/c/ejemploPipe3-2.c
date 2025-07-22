#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
int main(void)
{
//informacion fluye de hijo a padre
  int fd[2];
  pid_t pid;

 char saludoHijo[]="Hola papi.\0";
 char buffer[80];

  pipe(fd);   //creo pipe
  pid=fork(); //creo proceso 

 switch(pid) {
   case -1 : //ERROR
           printf("NO SE HA PODIDO CREAR HIJO...");
           exit(-1);           
   case 0 : //HIJO
            //HIJO ENVIA
            printf("\tEl HIJO envia algo al pipe.\n");          
            close(fd[0]);
            write(fd[1], saludoHijo, strlen(saludoHijo)); //escribo en pipe
            break;   
   default : //PADRE                          
            wait(NULL); //espero al proceso hijo
            //PADRE RECIBE
            close(fd[1]);//cierra el descriptor de salida
            read(fd[0], buffer, sizeof(buffer));//leo el pipe
            printf("El PADRE recibe algo del pipe: %s\n", buffer);            
            break;
 }  
 return 0;
}
/*
administrador@ubuntu1:~$ gcc ejemploPipe3-2.c -o ejemploPipe3-2
administrador@ubuntu1:~$ ./ejemploPipe3-2
	El HIJO envia algo al pipe.
El PADRE recibe algo del pipe: Hola papi.
administrador@ubuntu1:~$ 



*/

