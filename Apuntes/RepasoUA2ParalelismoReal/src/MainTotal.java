public class MainTotal {

    public static void main(String[] args) {

        // Imagina que tienes 3 libros distintos
        ContadorTotal tarea1 = new ContadorTotal("el_quijote.txt");
        ContadorTotal tarea2 = new ContadorTotal("el_quijote1.txt");


        Thread h1 = new Thread(tarea1);
        Thread h2 = new Thread(tarea2);


        // Los 3 libros se leen SIMULTÁNEAMENTE
        h1.start();
        h2.start();


        try {
            h1.join();
            h2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long totalAbsoluto = tarea1.getContador() + tarea2.getContador();
        System.out.println("Suma total de caracteres de tu biblioteca: " + totalAbsoluto);
    }
}