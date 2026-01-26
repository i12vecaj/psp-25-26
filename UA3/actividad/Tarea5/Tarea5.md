# Tarea 5 – Programación de Sockets 5

## Objetivo
Enviar un objeto entre un cliente y un servidor para que el servidor realice cálculos y devuelva el objeto modificado.

## Solución

### Clase Numeros
Se define una clase `Numeros` que implementa `Serializable` y contiene:
- Un número entero
- El cuadrado del número
- El cubo del número

Incluye constructor vacío, constructor con parámetros, getters y setters.

### Servidor
El servidor:
1. Espera la conexión del cliente.
2. Recibe un objeto `Numeros` mediante `ObjectInputStream`.
3. Calcula el cuadrado y el cubo del número.
4. Devuelve el objeto actualizado al cliente.

### Cliente
El cliente:
1. Solicita un número entero por consola.
2. Comprueba que el número sea válido.
3. Envía el objeto al servidor.
4. Recibe el objeto calculado.
5. Muestra los resultados por pantalla.

## Control de errores
- Número negativo
- Servidor no iniciado
- Errores de entrada/salida
- Excepciones de serialización

## Conceptos utilizados
- Serialización de objetos
- Comunicación TCP
- Flujos de objetos
- Manejo de excepciones

## Resultado
Se realiza correctamente el envío y recepción de objetos entre procesos, cumpliendo el modelo cliente/servidor.
