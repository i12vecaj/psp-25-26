public class ProcessSimulator {
    public static void main(String[] args) throws Exception {
        String[] comandos = {
            "java -cp bin Script1",
            "java -cp bin Script2", 
            "java -cp bin Script3"
        };

        java.io.File logFile = new java.io.File("logs\\resultados_multiprocesos.txt");
        logFile.getParentFile().mkdirs();

        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(logFile))) {

            long t1 = ejecutarSecuencial(comandos);
            writer.write("Secuencial: " + t1 + " ms\n");

            long t2 = ejecutarParalelo(comandos);
            writer.write("Paralelo: " + t2 + " ms\n");
            
        }
    }

    private static long ejecutarSecuencial(String[] comandos) throws Exception {
        long inicio = System.currentTimeMillis();
        System.out.println("Secuencialmente");
        
        for (String cmd : comandos) {
            java.lang.ProcessBuilder pb = new java.lang.ProcessBuilder(cmd.split(" "));
            pb.directory(new java.io.File("."));
            Process p = pb.start();      
            p.waitFor();     
        }
        
        long tiempo = System.currentTimeMillis() - inicio;
        System.out.println("Secuencial: " + tiempo);
        return tiempo;
    }

    private static long ejecutarParalelo(String[] comandos) throws Exception {
        long inicio = System.currentTimeMillis();
        
        java.util.List<Process> procesos = new java.util.ArrayList<>();
        
        for (String cmd : comandos) {
            java.lang.ProcessBuilder pb = new java.lang.ProcessBuilder(cmd.split(" "));
            pb.directory(new java.io.File("."));            
            procesos.add(pb.start());
        }
        
        for (int i = 0; i < procesos.size(); i++) {
            Process p = procesos.get(i);
            p.waitFor();
        }
        
        long tiempo = System.currentTimeMillis() - inicio;
        System.out.println("Paralelo: " + tiempo + " ms");
        return tiempo;
    }
}
