//fifocrea.c
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>

int main(void)
{
  int fp;  
  int p, bytesleidos;
  char saludo[] = "Un saludo!!!\n", buffer[10];
 
  p=mkfifo("FIFO2", S_IFIFO|0666);//permiso de lectura y escritura

  if (p==-1) {
      printf("HA OCURRIDO UN ERROR...\n");
      exit(0); 
  }
  
  while(1) {  
   fp = open("FIFO2", 0);  
   bytesleidos= read(fp, buffer, 1); 
   printf("OBTENIENDO Información...");
   while (bytesleidos!=0){      
       printf("%1c", buffer[0]);    //leo un caracter 
       bytesleidos= read(fp, buffer, 1);//leo otro byte
   }
   close(fp);  
  }
  return(0);
}

