#include <unistd.h>
#include <stdio.h>
#include <errno.h>
void main() {
  printf ("Lista de procesos\n");
  if (execl ("/bin/ps", "ps", "-f", (char *)NULL) < 0)
      printf("Error en exec %d\n", errno);    
  else
      printf ("Fin de la lista de procesos\n");
}
