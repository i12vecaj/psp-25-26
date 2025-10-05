public class ProcessSimulator {

    public static void main(String[] args) throws Exception {
        String[] comandos = {
                "java Script1",
                "java Script2",
                "java Script3"
        };

        long inicioParalelo = System.currentTimeMillis();
        Thread t1 = new Thread(() -> ejecutar(comandos[0]));
        Thread t2 = new Thread(() -> ejecutar(comandos[1]));
        Thread t3 = new Thread(() -> ejecutar(comandos[2]));

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        long finParalelo = System.currentTimeMillis();

        long inicioSecuencial = System.currentTimeMillis();
        for (String cmd : comandos) {
            ejecutar(cmd);
        }
        long finSecuencial = System.currentTimeMillis();

        try (java.io.FileWriter fw = new java.io.FileWriter("logs/resultados_multiproceso.txt", true)) {
            fw.write("Tiempo paralelo: " + (finParalelo - inicioParalelo) + " ms\n");
            fw.write("Tiempo secuencial: " + (finSecuencial - inicioSecuencial) + " ms\n\n");
        }
    }

    static void ejecutar(String comando) {
        try {
            Process p = Runtime.getRuntime().exec(comando);
            p.waitFor();
        } catch (Exception e) {
            System.out.println("Error ejecutando: " + comando);
        }
    }
}
