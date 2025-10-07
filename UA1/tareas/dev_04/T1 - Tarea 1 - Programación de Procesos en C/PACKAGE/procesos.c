#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>   
#include <sys/types.h>
#include <sys/wait.h> 

int main() {
    pid_t pid;  // Identificador del proceso
    int numero; // Variable introducida por el usuario

    // pide una variable al usuario
    printf("Introduce un número entero: ");
    if (scanf("%d", &numero) != 1) {
        fprintf(stderr, "Error: entrada no válida.\n");
        exit(EXIT_FAILURE);
    }

    // Crear proceso
    pid = fork();  

    if (pid < 0) {
        perror("Error al crear el proceso hijo");
        exit(EXIT_FAILURE);
    }

    // Código del proceso hijo
    if (pid == 0) {
        printf("\n[Hijo] PID = %d | PID padre = %d\n", getpid(), getppid());
        int resultadoHijo = numero + 4;
        printf("[Hijo] Valor original: %d → +4 = %d\n", numero, resultadoHijo);
        exit(EXIT_SUCCESS);
    } 
    // Código del proceso padre
    else {
        printf("\n[Padre] PID = %d | PID hijo = %d\n", getpid(), pid);
        int resultadoPadre = numero - 5;
        printf("[Padre] Valor original: %d → -5 = %d\n", numero, resultadoPadre);

        // Esperar a que termine el proceso hijo 
        int status;
        wait(&status);
        printf("\n[Padre] El proceso hijo ha terminado con estado %d\n", WEXITSTATUS(status));
    }

    return 0;
}
