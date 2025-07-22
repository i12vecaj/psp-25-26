#include <stdio.h>
#include <stdlib.h>
void main()
{
  printf("Ejemplo de uso de system():");
  printf("\n\tListado del directorio actual y envio a un fichero:");
  printf("%d",system("ls > ficsalida"));
  printf("\n\tAbrimos con el gedit el fichero...");
  printf("%d",system("gedit ficsalida"));
  printf("\n\tEste comando es erróneo: %d",system("ged"));
  printf("\nFin de programa....\n");
}
