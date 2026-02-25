/*
 * procesos.c
 *
 * Objetivo:
 *   - FR1: crear un proceso (padre + hijo) usando fork()
 *   - FR2: pedir al usuario una variable (entero)
 *   - FR3: el proceso padre restará 5 a la variable
 *   - FR4: el proceso hijo le sumará 4
 *   - FR5: mostrar todos los valores por pantalla
 *
 * Control de errores:
 *   - Validación de entrada usando fgets + strtol (detecta entrada no numérica y overflow)
 *   - Comprobación de errores en fork() y waitpid()
 *
 * Compilar:
 *   gcc -Wall -Wextra -std=c11 -pedantic -o procesos procesos.c
 *
 * Uso:
 *   ./procesos
 *
 * Autor: (pon tu nombre aquí)
 * Fecha: (pon la fecha)
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <errno.h>
#include <string.h>

#define BUF_SIZE 128

int main(void) {
    char buf[BUF_SIZE];
    char *endptr;
    long original;
    
    /* FR2: pedir al usuario una variable */
    printf("Introduce un número entero: ");
    if (fgets(buf, sizeof(buf), stdin) == NULL) {
        if (feof(stdin)) {
            fprintf(stderr, "Entrada finalizada (EOF) antes de leer un número.\n");
        } else {
            perror("Error leyendo la entrada");
        }
        return EXIT_FAILURE;
    }

    /* convertir con strtol y comprobar errores */
    errno = 0;
    original = strtol(buf, &endptr, 10);
    if (errno == ERANGE) {
        fprintf(stderr, "Número fuera de rango.\n");
        return EXIT_FAILURE;
    }
    /* saltar espacios en endptr */
    while (*endptr == ' ' || *endptr == '\t') endptr++;
    /* aceptar línea terminada en '\n' o final de string */
    if (*endptr != '\n' && *endptr != '\0') {
        fprintf(stderr, "Entrada no válida: por favor introduce un entero sin letras ni decimales.\n");
        return EXIT_FAILURE;
    }

    /* FR1: crear proceso (padre + hijo) */
    pid_t pid = fork();
    if (pid < 0) {
        /* error en fork */
        perror("fork");
        return EXIT_FAILURE;
    } else if (pid == 0) {
        /* Código del hijo */
        long hijo_val = original + 4; /* FR4 */
        /* FR5: mostrar valores (desde el hijo) */
        printf("[HIJO ] PID %d, PPID %d: valor original = %ld, valor tras sumar 4 = %ld\n",
               (int)getpid(), (int)getppid(), original, hijo_val);
        /* terminado correctamente */
        _exit(EXIT_SUCCESS);
    } else {
        /* Código del padre */
        long padre_val = original - 5; /* FR3 */

        /* esperar al hijo y controlar errores */
        int status;
        if (waitpid(pid, &status, 0) == -1) {
            perror("waitpid");
            /* seguimos de todas formas para mostrar el valor del padre */
        } else {
            if (WIFEXITED(status)) {
                int code = WEXITSTATUS(status);
                /* opcional: mostrar código de salida del hijo */
                /* printf("[PADRE] El hijo %d finalizó con código %d\n", (int)pid, code); */
                (void)code; /* para evitar warning si no se usa */
            } else if (WIFSIGNALED(status)) {
                printf("[PADRE] El hijo terminó por señal %d\n", WTERMSIG(status));
            }
        }

        /* FR5: mostrar valores (desde el padre) */
        printf("[PADRE] PID %d: valor original = %ld, valor tras restar 5 = %ld\n",
               (int)getpid(), original, padre_val);

        return EXIT_SUCCESS;
    }
}
