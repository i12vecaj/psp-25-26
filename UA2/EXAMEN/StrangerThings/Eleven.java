/* Productor / Consumidor – “Stranger Things”

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
Debes entregar el ejercicio, dentro de UD2, en tu rama, carpeta "EXAMEN" ->  carpeta "Stranger Things". */

package StrangerThings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Eleven {
    private final String nombre;
    private final List<Portal> listaPortal;
    private final Random random;
    private final AtomicInteger nextVasoId = new AtomicInteger(0);

    public Eleven(String nombre) {
        this.nombre = nombre;
        this.listaPortal = new ArrayList<>();
        this.random = new Random();

        System.out.println("\nPersonaje: " + this.nombre + " preparando portales");
        for (int i = 0; i < 3; i++) {
            int tipoAleatorio = random.nextInt(2);
            Portal nuevoPortal = new Portal("Portal " + nextVasoId.getAndIncrement());
            listaPortal.add(nuevoPortal);
        }
        System.out.println("------------------------------------------\n");
    
    }
    public synchronized Portal AbrirPortal() {
        String Magica = Thread.currentThread().getName();
        System.out.println(this.nombre + " abre un portal para " + Magica);

        while (listaPortal.isEmpty()) {
            System.out.println(this.nombre + " .El Camarero " + Magica + " no tiene vasos listos");
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println("Error: Hilo cliente interrumpido mientras esperaba vaso.");
                Thread.currentThread().interrupt();
                return null;
            }
        }

        int indiceAleatorio = random.nextInt(listaPortal.size());
        Portal portalServido = listaPortal.remove(indiceAleatorio);

        System.out.println(Magica + " recibe " + portalServido.toString() + " | Portales restantes: " + listaPortal.size());
        return portalServido;
    }

    // FR4:
    public synchronized void CerrarPortal(Portal portal) {
        String clienteNombre = Thread.currentThread().getName();
        listaPortal.add(portal);
        System.out.println(clienteNombre + " devuelve " + portal.toString() + " | Portales disponibles: " + listaPortal.size());
        notifyAll();
    }
}
