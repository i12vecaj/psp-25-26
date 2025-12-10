import java.util.ArrayList;
import java.util.List;

public class MainMultiArchivos {
    public static void main(String[] args) {

        String[] ficheros = {"el_quijote.txt", "el_quijote1.txt"};
        char[] vocales = {'a', 'e','i', 'o', 'u'};

        List<ContadorLetra> todasLasTareas = new ArrayList<>();
        List<Thread> todosLosHilos = new ArrayList<>();

        for(String fichero : ficheros){
            for(char vocal : vocales){
                ContadorLetra tarea = new ContadorLetra(fichero, vocal);
                todasLasTareas.add(tarea);

                Thread hilo = new Thread(tarea);
                todosLosHilos.add(hilo);
                hilo.start();
            }
        }
        for(Thread hilo : todosLosHilos){
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int total = 0;
        for(ContadorLetra tarea : todasLasTareas){
            total += tarea.getContador();
        }
        System.out.println("El total de las las letras: " + total);
    }
}
