//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static final String RUTA_FICHERO = "el_quijote.txt";

    public static void main(String[] args) {

        System.out.println("Hilos en pararlelo");

        long tiempoInicio = System.currentTimeMillis();

        //Creacion de hilos
        ContadorLetra hiloA = new ContadorLetra(RUTA_FICHERO, 'a');
        ContadorLetra hilaE = new ContadorLetra(RUTA_FICHERO, 'e');
        ContadorLetra hiloI = new ContadorLetra(RUTA_FICHERO, 'i');
        ContadorLetra hiloO = new ContadorLetra(RUTA_FICHERO, 'o');
        ContadorLetra hiloU = new ContadorLetra(RUTA_FICHERO, 'u');

        //Creacion de hilos

        Thread tareaA = new Thread(hiloA);
        Thread tareaE = new Thread(hilaE);
        Thread tareaI = new Thread(hiloI);
        Thread tareaO = new Thread(hiloO);
        Thread tareaU = new Thread(hiloU);

        //Arrancamos el Hilo
        tareaA.start();
        tareaE.start();
        tareaI.start();
        tareaO.start();
        tareaU.start();

        //Esperamos que todos los hilos termine
        try {
            tareaA.join();
            tareaE.join();
            tareaI.join();
            tareaO.join();
            tareaU.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        int totalVocales = hiloA.getContador() +  hilaE.getContador() + hiloI.getContador() + hiloO.getContador()+ hiloU.getContador();
        long tiempoFinal = System.currentTimeMillis();
        System.out.println("Total de vocales: " + totalVocales);
        System.out.println("Total de vocales A: " + hiloA.getContador());
        System.out.println("Total de vocales E: " + hilaE.getContador());
        System.out.println("Total de vocales I: " + hiloI.getContador());
        System.out.println("Total de vocales O: " + hiloO.getContador());
        System.out.println("Total de vocales U: " + hiloU.getContador());
        System.out.println("El tiempo total es: " + (tiempoFinal - tiempoInicio));

    }
}