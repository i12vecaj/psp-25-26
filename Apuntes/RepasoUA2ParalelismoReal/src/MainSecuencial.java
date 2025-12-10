public class MainSecuencial {

    private static final String RUTA_FICHERO = "el_quijote.txt";

    public static void main(String[] args) {

        System.out.println("--- Inicio del conteo SECUENCIAL (Uno detrás de otro) ---");
        long tiempoInicio = System.currentTimeMillis();

        // 1. Preparamos los obreros (reutilizamos tu clase ContadorLetra)
        ContadorLetra tareaA = new ContadorLetra(RUTA_FICHERO, 'a');
        ContadorLetra tareaE = new ContadorLetra(RUTA_FICHERO, 'e');
        ContadorLetra tareaI = new ContadorLetra(RUTA_FICHERO, 'i');
        ContadorLetra tareaO = new ContadorLetra(RUTA_FICHERO, 'o');
        ContadorLetra tareaU = new ContadorLetra(RUTA_FICHERO, 'u');

        // 2. EJECUCIÓN SECUENCIAL
        // Fíjate: NO creamos new Thread(). NO usamos start().
        // Llamamos a run() directamente como si fuera un método normal.

        tareaA.run(); // El Main se queda aquí leyendo todo el libro buscando A.
        tareaE.run(); // Cuando termina con la A, empieza con la E.
        tareaI.run(); // ... luego la I
        tareaO.run(); // ... luego la O
        tareaU.run(); // ... finalmente la U

        // 3. Recogemos resultados
        // No hace falta join(), porque al ser secuencial, si llegamos aquí es que todo ha terminado.
        int totalVocales = tareaA.getContador() + tareaE.getContador() +
                tareaI.getContador() + tareaO.getContador() +
                tareaU.getContador();

        long tiempoFin = System.currentTimeMillis();

        System.out.println("TOTAL VOCALES: " + totalVocales);
        System.out.println("Tiempo total: " + (tiempoFin - tiempoInicio) + " ms");
    }
}