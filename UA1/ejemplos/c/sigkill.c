#include <stdio.h>
#include <signal.h>
#include <stdlib.h>
#include <fcntl.h>
// To avoid WARNINGS
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdbool.h>

int contador_padre = 0;
bool end = false;

// Gestion de senyales en proceso padre       
void gestion_padre (int segnal)
{
    contador_padre++;    
    switch(segnal)
    {
        case SIGUSR1:
            printf ("\t...Padre recibe senyal SIGUSR1 (%d) (contador padre: %d)", segnal, contador_padre);
            break;
          
        case SIGTERM:
            printf ("\t...Padre recibe senyal SIGTERM (%d) (contador padre: %d)", segnal, contador_padre);
            printf ("\n\n\t\t\tOh no! It's TIME TO DIE!\n\n");            
            end=true;
            break;
            
        default: // JIC
            printf ("\tPadre recibe senyal ... %d (contador padre: %d) \n", segnal, contador_padre);
            break;
    }
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
            for(int i=0; i<iterations; i++)
            {
                sleep(2);
                kill (pid_padre, SIGUSR1);	// Proceso Hijo envía senyal SIGUSR1 al Proceso Padre
            }
            printf ("\n\t\t\tFather ... Your time has reached to the end ...\n\n");
            kill (pid_padre, SIGTERM);	// Proceso Hijo envía senyal SIGKILL al Proceso Padre
            sleep(2);
            break;
            
        default:			// Proceso Padre 
            // tratamiento de la senyal en proceso padre
	    signal (SIGUSR1, gestion_padre);
            signal (SIGTERM, gestion_padre);
            
	    // Bucle Infinito hasta recibir SIGTERM ...	
            while (!end)
            {
                printf ("\nProceso Padre esperando senyal ... ");
                pause();		// Proceso Padre espera hasta recibir una senyal del hijo
            }
            printf ("\t\t\t ... I am going to die ...\n\n");
            break;
    }
    
    return 0;
}

