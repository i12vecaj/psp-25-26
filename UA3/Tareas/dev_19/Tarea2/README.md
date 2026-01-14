** Imagen de la prueba de que funciona **

<img src="https://i.imgur.com/2wZ5e7t.png" alt="Captura del terminal" img>

**Descripción del ejercicio**
- En primer lugar se presentan dos clases de java. Por un lado, "PSP_UA3_Ejercicio_2_ServidorTCP", y por otro lado, "PSP_UA3_Ejercicio_2_ClienteTCP"
- En segundo lugar, en la clase "PSP_UA3_Ejercicio_2_ServidorTCP", en primer lugar declaramos el Puerto al que se va a conectar el servidor, que posteriormente se conectará también el cliente.
- Después creamos un Servidor pasándole el puerto al que se tiene que conectar, Después con la clase "Socket", creo los dos clientes para indicarles con "accept()" para que acepten de que se conecten con el servidor
- Por último de esta clase cerramos el servidor
- En la otra clase "PSP_UA3_Ejercicio_2_ClienteTCP", declaramos dos variables, por una lado Host y por otro lado el Puerto, después creamos un Cliente1 y un Cliente2 y les pasamos el Host y el Puerto que hemos declarado antes.
- Después, mostramos el puerto local y el puerto remoto de cada cliente
- Y por último cerramos los dos clientes