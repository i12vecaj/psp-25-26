T2 - Tarea 2 - Programación y sincronización de hilos en Java 2
FR4
Nombre: Alberto Nieto Lozano

Al quitar synchronized, los hilos acceden al saldo a la vez sin ningún tipo de 
coordinación. Esto provoca solapación.

Como consecuencia, el saldo final suele ser incorrecto, porque algunos ingresos 
se pierden cuando varios hilos leen y escriben el mismo valor al mismo tiempo.

Con synchronized, se garantiza que solo un hilo modifica el saldo cada vez, 
evitando interferencias y manteniendo un resultado coherente.
