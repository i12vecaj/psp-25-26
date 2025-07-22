#include <sys/types.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
	pid_t pid;
        int status;  

	if ( (pid=fork()) == 0 )
	{ /* hijo */
   	  printf("Soy el hijo (%d, hijo de %d)\n", getpid(),getppid());
	}
	else
	{ /* padre */
          wait(&status);
	  printf("Soy el padre (%d, hijo de %d)\n", getpid(),getppid());
	}

	return 0;
}
/*
mj@ubuntu-mj:~$ gcc ejemplo2Fork.c -o ejemplo2Fork
mj@ubuntu-mj:~$ ./ejemplo2Fork
Soy el hijo (3112, hijo de 3111)
Soy el padre (3111, hijo de 1923)
mj@ubuntu-mj:~$ ps
  PID TTY          TIME CMD
 1923 pts/0    00:00:00 bash
 3113 pts/0    00:00:00 ps
mj@ubuntu-mj:~$ 

*/
