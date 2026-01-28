![lope_logo](https://www.ceslopedevega.com/wp-content/uploads/2020/03/pruebalogo.svg_.png)

# UA3 - Examen 1 -Creación de un Char con TCP
## 11/01/2023

Una situiación típica de un servidor que atiende a múltiples clientes es un servidor de [CHAT](https://es.wikipedia.org/wiki/Chat).

Vamos a construir uno sencillo que pueda atender a varios clientes a la vez, cada cliente será atendido en un hilo de ejecución; en ese hilo se recibirán sus mensajes y se enviarán al resto de miembros del chat.

### ServidorChat

El PROGRAMA SERVIDOR **ServidorChat** define el número máximo de conexiones que admite e irá controlando los clientes, que actualmente estén conectados, para ello utiliza un objeto de la clase *ComunHilos* que será compartido por todos los hilos.

### HiloServidorChat

El HILO **HiloServidorChat** se encarga de recibir y enviar los mensajes de los clientes del chat.  En el constructor se recibe el socket creado y el objeto compartido por todos los hilos.  Se crea el flujo de entrada desde el que se leen los mensajes que el cliente de chat envía.

En el método **run()** lo primero que hacemos es enviar todos los mensajes que hay actualmente en el chat al programa cliente para quer los visualice en pantalla.  Esto se hace en el método EnviarMensajesaTodos().

A continuación se hace un bucle while en el que se recibe lo que el cliente escribe en el chat.  Cuando un cliente finaliza, envía un asterisco al servidor de chat, entonces se sale del bucle while, ya que termina el proceso del client, de esta manera se controlan las conexiones actuales.

El texto que el cliente esdcribe en el chat se añade al atributo *mensajes* del objeto comaprtido para poder enviar la conversación a todos los clientes, el método **EnviarMensajesaTodos()** se encargará de ello.  Después del bucle while se cierra el socket del cliente.

El método *EnviarMensajesaTodos()* envía el texto del atributo *mensajes* del objeto compartido a todos los sockets conectados que no hayan cerrado su conexión con el servidor, para ello se usa el array de sockets, de esta manera todos ven la conversación.  Será necesario abrir un stream de escritura a cada socket y escribir el texto.

Desde el hilo servidor se muestra en consola los clientes que actualmente hay conectados, por ejemplo, se muestra esta saalida cuando hay 3 clientes conectados:
```
Servidor iniciado...
NUMERO DE CONEXIONES ACTUALES: 1
NUMERO DE CONEXIONES ACTUALES: 2
NUMERO DE CONEXIONES ACTUALES: 3
```

### ComunHilos

La CLASE **ComunHilos** compartida por todos los hilos tiene los atributos comentados antertiormente y métodos para dar valor y obtener el valor de los atributos (gettes y setters).  Los métodos definidos como *synchronized* permitirán que dos o más hilos no interfieran en el estado de los atributos.

 Este objeto tiene los siguientes atributos:
* *int CONEXIONES*: almacena el número de conexiones de clientes. Cada vez que se coencta un cliente sumamos 1 a este atributo y lo usamos como índice para ir llenadno el array de sockets con los clientes que se van conectando. el máximo de conexiones permitidas lo indica el atributo *MAXIMO*.
*  *int ACTUALES*: almacena el número de clientes conectados en este momento.  Cada vez que se desconecta un cliente se resta uno a este atributo. Valor inicial 0.
*  *Socket tabla[]=new Socket[MAXIMO]*: Array que almacena los sockets de los clientes que se conectan.  Usaremos el array para tener control de los clientes y así poder enviarles la conversación del chat cada vez que uno envía algún mensaje.
*  *String mensajes*: contiene los mensajes del chat.

```
Esta clase se proporciona al completo.
```

### ClienteChat

Desde el PROGRAMA **ClienteChat** se realizan las siguientes funciones:
* En primer lugar se pide al usuario el nombre a utilizar en el chat.
* Se crea un socket al servidor de chat en el puerto pactado con el servidor (44444).  Si todo va bien, el servidor asignará un hilo al cliente y en el cliente se mostrará la conversación que hay hasta el momento. Si no se puede establecer la coonexión se visualizará un mensaje de error.
* El cliente puede escribir sus mensajes y pulsar el botón enviar, automáticamente el mensaje será enviado a todos los clientes del chat.
* El botón salir finaliza la conexión del cliente al chat, enviando un * al servidor para que este sepa que va a finalziar la conexión.

Dentro del método **run()** el cliente lee lo que el hilo servidor le manda (los mensajes del chat) para mostrarlos.  Esto se realiza en un proceso repetitivo que termina cuando el usuario pulsa el botón salir, que cambiará el valor de la variable *repetir* a *false*. para que finalice el bucle.

En el método main() se pide el nombre de usuario, se realiza la conexión al aservidor, se crea un opbjeto ClienteChat, se muestra la pantalla y se lanza el hilo cliente.  Un ejemplo de ejecución es el siguiente
```
java ClienteChat
Introduce tu nombre o nick: pacs
CONEXION DEL CLIENTE CHAT: pacs

 > Entra en el Chat ... pacs
 > Entra en el Chat ... pacs

 > Entra en el Chat ... adsdas
adsdas > asd
adsdas > asd
adsdas > asd
*
 > Abandona el Chat ... pacs
*
```

### Evaluación

Dentro del código, vais a encontrar 10 comentarios como el siguiente
```
/* RELLENAR */
```
Simplemente sustituid dichos comentarios por código funcional para que el chat funcione.  Por cada comentario correctamente corrregido: **1 punto**.

| FICHERO | TIPO| COMENTARIOS |
|-|-|-|
| ClienteChat.java|Programa (interfaz runnable)|3|
| ComunHilos.java |Clase       |0|
| HiloServidorChat.java| Hilo|3|
| ServidorChat.java | Programa|4|

Si un fichero no compila se pierden **TODOS** los puntos correspondientes a ese fichero.

Para evaluar, una vez haya integrado todas las ramas con GIT simplemente ejecutaré esta secuencia:
```
javac *.java
java ServidorChat
java ClienteChat
java ClienteChat
```
Y haré una prueba básica del sistema.  Si hay errores de compilación ya sabéis como se evalúa ... *do the math*.

### Notas

Para ejecutar el servidor de chat se necesita que las clases java ServidorChat, HiloServidorChat y ComunHilos estén en la misma carpeta. El programa ClienteChat puede estar en cualquier otra carpeta pero por comodidad lo mantendremos en la misma.

Si queremos ejecutar clientes y servidores en diferentes máquinas es necesario especificar en el programa cliente, en la creación del socket la IP de la máquina donde se ejecuta el servidor de chat


## Referencias

- Bibliografía Recomendada:
  - **Programación de servicios y procesos - Técnico Superior en DAM.** *Mª Jesús Ramos Martín. Editorial Garceta. 2ª Edición. 2018. ISBN: 978-84-1728-931-7.*