Productor / Consumidor – “Stranger Things”

En Hawkins, el Laboratorio está analizando criaturas del Upside Down.
Para ello, debes implementar el patrón Productor/Consumidor:

El Productor será Eleven, que usa sus poderes para cerrar portales que se abren aleatoriamente.

Cada vez que cierra uno, genera un “evento” (por ejemplo: Demogorgon detectado, Portal inestable, etc.) y lo deposita en un buffer compartido.

El Consumidor será el Laboratorio de Hawkins, que recoge estos eventos del buffer y los procesa.

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

Requisitos

Crea un buffer limitado (por ejemplo, de tamaño 5).

Implementa las clases Productor y Consumidor, cada una como un hilo.

Usa wait(), notify() o notifyAll() para gestionar:

El productor espera si el buffer está lleno.

El consumidor espera si el buffer está vacío.

Muestra por pantalla los mensajes clave:

Eleven genera un evento.
El Laboratorio procesa un evento.
Esperas por buffer lleno/vacío.

El programa debe finalizar después de producir y consumir un número determinado de eventos (por ejemplo, 20).

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

Debes entregar el ejercicio, dentro de UD2, en tu rama, carpeta "EXAMEN" ->  carpeta "Stranger Things".
