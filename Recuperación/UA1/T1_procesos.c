#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

int main(void)
{
    int variable;
    pid_t pid;
    int estado_hijo;

    printf("Introduce un numero entero: ");
    fflush(stdout);

    /*
     * Control de errores de entrada:
     * scanf debe leer exactamente un entero. Si no lo consigue, el programa
     * termina de forma controlada y avisa al usuario.
     */
    if (scanf("%d", &variable) != 1) {
        fprintf(stderr, "Error: debes introducir un numero entero valido.\n");
        return EXIT_FAILURE;
    }

    /*
     * fork() crea un nuevo proceso. A partir de aqui existen dos procesos:
     * - El proceso padre, que recibe como resultado el PID del hijo.
     * - El proceso hijo, que recibe como resultado 0.
     *
     * Ambos procesos tienen una copia independiente de la variable. Por eso,
     * cuando el hijo suma 4, no cambia la variable del padre; y cuando el padre
     * resta 5, no cambia la variable del hijo.
     */
    pid = fork();

    if (pid < 0) {
        perror("Error al crear el proceso hijo con fork");
        return EXIT_FAILURE;
    }

    if (pid == 0) {
        int resultado_hijo = variable + 4;

        printf("\n[HIJO]\n");
        printf("Valor inicial recibido: %d\n", variable);
        printf("Operacion realizada: %d + 4\n", variable);
        printf("Resultado del hijo: %d\n", resultado_hijo);

        return EXIT_SUCCESS;
    }

    /*
     * El padre espera al hijo para evitar que quede como proceso zombie y para
     * mostrar una salida mas ordenada por pantalla.
     */
    if (waitpid(pid, &estado_hijo, 0) == -1) {
        perror("Error al esperar al proceso hijo");
        return EXIT_FAILURE;
    }

    if (!WIFEXITED(estado_hijo) || WEXITSTATUS(estado_hijo) != EXIT_SUCCESS) {
        fprintf(stderr, "Error: el proceso hijo no termino correctamente.\n");
        return EXIT_FAILURE;
    }

    {
        int resultado_padre = variable - 5;

        printf("\n[PADRE]\n");
        printf("Valor inicial recibido: %d\n", variable);
        printf("Operacion realizada: %d - 5\n", variable);
        printf("Resultado del padre: %d\n", resultado_padre);
    }

    /*
     * Respuesta a "Entiendes lo que esta pasando?":
     * Si. Tras fork(), padre e hijo ejecutan el mismo programa, pero cada uno
     * trabaja con su propia copia de la memoria. Por eso se obtienen dos
     * resultados distintos a partir del mismo valor inicial.
     */
    return EXIT_SUCCESS;
}
