//fifoescribe2.c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main()
{
  int fp;
  char saludo[] = "Un saludo!!!\n";
  fp = open("FIFO2", 1);
 
  if(fp == -1) {
    printf("ERROR AL ABRIR EL FICHERO...");
    exit(1);
  } 
  printf("Mandando información al FIFO...\n");
  write(fp,saludo, strlen(saludo));
  close(fp);   
  return 0; 
}
