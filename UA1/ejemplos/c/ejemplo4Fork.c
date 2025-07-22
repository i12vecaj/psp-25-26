#include <sys/types.h>
#include <unistd.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
	pid_t pid1, pid2;
	int status1, status2;

	if ( (pid1=fork()) == 0 )
	{ /* hijo (1a generacion) = padre */
		if ( (pid2=fork()) == 0 )
		{ /* hijo (2a generacion)  = nieto */
			printf("Soy el nieto (%d, hijo de %d)\n",
getpid(), getppid());
		}
		else
		{ /* padre (2a generacion) = padre */
			
wait(&status2);
			printf("Soy el padre (%d, hijo de %d)\n",
getpid(), getppid());
		}
	}
	else
	{ /* padre (1a generacion) = abuelo */
		wait(&status1);
		printf("Soy el abuelo (%d, hijo de %d)\n", getpid(),
getppid());
	}

	return 0;
}
/*
mj@ubuntu-mj:~$ gcc ejemplo4Fork.c -o  ejemplo4Fork
mj@ubuntu-mj:~$ ./ejemplo4Fork
Soy el nieto (3161, hijo de 3160)
Soy el padre (3160, hijo de 3159)
Soy el abuelo (3159, hijo de 2920)
mj@ubuntu-mj:~$ ps
  PID TTY          TIME CMD
 2920 pts/1    00:00:00 bash
 3162 pts/1    00:00:00 ps
mj@ubuntu-mj:~$ 

*/


