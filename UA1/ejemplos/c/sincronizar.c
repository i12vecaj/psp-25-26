#include <stdio.h>
#include <signal.h>
#include <stdlib.h>
#include <fcntl.h>
// To avoid WARNINGS
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

int contador_padre = 0;
int contador_hijo = 0;

// Gestion de senyales en proceso padre       
void gestion_padre (int segnal)
{
  contador_padre++;
  printf ("\t...Padre recibe senyal..%d (contador padre: %d) \n", segnal, contador_padre);
}

// Gestion de senyales en proceso hijo        */
void gestion_hijo (int segnal)
{
  contador_hijo++;
  printf ("\t...Hijo recibe senyal..%d (contador hijo: %d) \n", segnal, contador_hijo);
}

int main ()
{
    int iterations = 3;
    int pid_padre, pid_hijo;

    pid_padre = getpid ();
    pid_hijo = fork ();		// Creamos el Proceso Hijo

    switch (pid_hijo)
    {
        case -1:
          printf ("Error al crear el proceso hijo...\n");
          exit (-1);
          
        case 0:			// Proceso Hijo
            // tratamiento de la senyal en proceso hijo
            signal (SIGUSR1, gestion_hijo);
            while (contador_hijo < iterations)
            {			// bucle infinito             
                sleep(1);
                kill (pid_padre, SIGUSR1);	// Proceso Hijo envía senyal al Proceso Padre
                printf ("Proceso Hijo esperando senyal ...");
                pause();		// Proceso Hijo espera hasta que llegue una senyal de respuesta
            }
            break;
            
        default:			// Proceso Padre 
            // tratamiento de la senyal en proceso padre
            signal (SIGUSR1, gestion_padre);
            while (contador_padre < iterations)
            {
                printf ("Proceso Padre esperando senyal ... ");
                pause();		// Proceso Padre espera hasta recibir una senyal del hijo
                sleep(1);
                kill (pid_hijo, SIGUSR1);	// Proceso Padre envía senyal al Proceso Hijo
            }
            break;
    }
    
    return 0;
}

 

 


