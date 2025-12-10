package examenMou;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


class VasoCerveza {
    
    private int id;    
    private int tipo;
    public VasoCerveza(int id, int tipo) {
        this.id = id;
        this.tipo = tipo;
        System.out.println("[VasoCerveza] Vaso creado - ID: " + id + ", Tipo: " + getTipoString());
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
  
    public double getLitros() {
        return tipo == 0 ? 0.284 : 0.568;
    }
    
    public String getTipoString() {
        return tipo == 0 ? "Media Pinta" : "Pinta";
    }
    
    @Override
    public String toString() {
        return "VasoCerveza[ID=" + id + ", Tipo=" + getTipoString() + " (" + getLitros() + "L)]";
    }
}

class Camarero {
    private String nombre;
    private List<VasoCerveza> listaVasos;
    private Random random;
    private static int contadorVasos = 0;
    public Camarero(String nombre) {
        this.nombre = nombre;
        this.listaVasos = new ArrayList<>();
        this. random = new Random();
        
        System.out.println("Camarero: " + nombre + " ha comenzado su turno");
        
        for (int i = 0; i < 3; i++) {
            int tipoAleatorio = random.nextInt(2); // 0 o 1
            VasoCerveza vaso = new VasoCerveza(contadorVasos++, tipoAleatorio);
            listaVasos.add(vaso);
        }
        
        System.out.println("[Camarero] " + nombre + " ha preparado " + listaVasos.size() + " vasos iniciales");
        contarVasos();
    }
    
    public synchronized VasoCerveza servirCerveza() throws InterruptedException {
        try {
            while (listaVasos.isEmpty()) {
                System.out.println("[Camarero] " + nombre + " no tiene vasos disponibles.  Esperando...");
                wait();
            }
            
            int indiceAleatorio = random.nextInt(listaVasos.size());
            VasoCerveza vaso = listaVasos. remove(indiceAleatorio);
            
            System.out.println("Camarero: " + nombre + " sirve " + vaso + " (Vasos restantes: " + listaVasos.size() + ")");
            
            return vaso;
            
        } catch (Exception e) {
            System.err.println("Error al servir cerveza: " + e.getMessage());
            throw e;
        }
    }

    public synchronized void devolverCerveza(VasoCerveza vaso) {
        try {
            if (vaso == null) {
                throw new IllegalArgumentException("El vaso no puede ser null");
            }
            listaVasos.add(vaso);
            System.out. println("[Camarero] " + nombre + " recibe " + vaso + " (Vasos disponibles: " + listaVasos.size() + ")");
            notifyAll();
            
        } catch (Exception e) {
            System.err.println("[ERROR Camarero] Error al devolver cerveza: " + e.getMessage());
        }
    }

    public synchronized void contarVasos() {
        System.out.println("[Camarero] " + nombre + " cuenta vasos disponibles: " + listaVasos.size());
        for (VasoCerveza vaso : listaVasos) {
            System.out.println("  - " + vaso);
        }
    }

    public String getNombre() {
        return nombre;
    }
}

class HilosClientes extends Thread {
    private Camarero camarero;
    private double totalLitrosBebidos;
    private Random random;

    public HilosClientes(String nombre, Camarero camarero) {
        super(nombre); // Asignar nombre al hilo
        this.camarero = camarero;
        this.totalLitrosBebidos = 0.0;
        this.random = new Random();
        System.out.println("[Cliente] " + nombre + " ha llegado al bar");
    }

    @Override
    public void run() {
        try {
            System.out.println("[Cliente] " + getName() + " está ejecutándose");
            while (true) {
                try {
                    
                    System.out.println("[Cliente] " + getName() + " pide una cerveza");
                    VasoCerveza vaso = camarero.servirCerveza();
                    
                    if (vaso == null) {
                        System. err.println("[ERROR Cliente] " + getName() + " recibió un vaso nulo");
                        continue;
                    }
                    
                    System. out.println("[Cliente] " + getName() + " está bebiendo " + vaso. getTipoString() + " (" + vaso.getLitros() + "L)");
                    Thread.sleep(random.nextInt(500) + 300); 
                    
                    totalLitrosBebidos += vaso.getLitros();
                    System.out. println("[Cliente] " + getName() + " ha terminado.  Total bebido: " + String.format("%.3f", totalLitrosBebidos) + "L");
                    
                    
                    System.out.println("[Cliente] " + getName() + " devuelve el vaso");
                    camarero.devolverCerveza(vaso);
                    
                    
                    int tiempoEspera = random.nextInt(751) + 250; 
                    System.out.println("[Cliente] " + getName() + " espera " + tiempoEspera + "ms antes de pedir otra");
                    Thread.sleep(tiempoEspera);
                    
                } catch (InterruptedException e) {
                    System. err.println("[ERROR Cliente] " + getName() + " fue interrumpido: " + e.getMessage());
                    break;
                } catch (Exception e) {
                    System.err.println("[ERROR Cliente] " + getName() + " encontró un error: " + e. getMessage());
                    e.printStackTrace();
                }
            }
            
        } catch (Exception e) {
            System. err.println("[ERROR FATAL Cliente] " + getName() + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out. println("[Cliente] " + getName() + " ha salido del bar.  Total bebido: " + String.format("%.3f", totalLitrosBebidos) + "L");
        }
    }

    public double getTotalLitrosBebidos() {
        return totalLitrosBebidos;
    }
}

public class App {

    public static void main(String[] args) {
        System.out.println("=". repeat(80));
        System.out.println("EXAMEN - 2º DAM - PSP - UA2 - Programación Multihilo");
        System.out.println("Simulación del Bar de Moe");
        System.out.println("=".repeat(80));
        
        try {
            // Crear el camarero Mou
            Camarero mou = new Camarero("Mou");
            
            System.out.println("\n" + "=".repeat(80));
            System.out.println("Creando clientes...");
            System.out.println("=".repeat(80) + "\n");
            
            // Crear los clientes
            HilosClientes homer = new HilosClientes("Homer", mou);
            HilosClientes barney = new HilosClientes("Barney", mou);
            HilosClientes carl = new HilosClientes("Carl", mou);
            HilosClientes lenny = new HilosClientes("Lenny", mou);
            HilosClientes lurleen = new HilosClientes("Lurleen", mou);
            
            System. out.println("\n" + "=".repeat(80));
            System.out. println("Iniciando simulación...");
            System.out.println("=".repeat(80) + "\n");
            
            // Iniciar los hilos de los clientes
            homer.start();
            barney.start();
            carl.start();
            lenny.start();
            lurleen.start();
            
            // Esperar un tiempo para ver la ejecución (en un caso real, podría ser infinito)
            Thread.sleep(10000); // 10 segundos de simulación
            
            System.out.println("\n" + "=".repeat(80));
            System.out.println("Deteniendo simulación...");
            System.out.println("=".repeat(80));
            
            // Interrumpir los hilos
            homer.interrupt();
            barney. interrupt();
            carl.interrupt();
            lenny.interrupt();
            lurleen.interrupt();
            
            // Esperar a que terminen
            homer.join(1000);
            barney.join(1000);
            carl. join(1000);
            lenny.join(1000);
            lurleen.join(1000);
            
            System.out.println("\n" + "=".repeat(80));
            System.out.println("Resumen final:");
            System.out.println("=".repeat(80));
            System.out.println("Homer bebió: " + String.format("%.3f", homer.getTotalLitrosBebidos()) + "L");
            System.out.println("Barney bebió: " + String. format("%.3f", barney.getTotalLitrosBebidos()) + "L");
            System.out.println("Carl bebió: " + String.format("%.3f", carl.getTotalLitrosBebidos()) + "L");
            System.out.println("Lenny bebió: " + String.format("%. 3f", lenny.getTotalLitrosBebidos()) + "L");
            System.out.println("Lurleen bebió: " + String.format("%. 3f", lurleen.getTotalLitrosBebidos()) + "L");
            System.out.println("=".repeat(80));
            
            mou.contarVasos();
            
        } catch (InterruptedException e) {
            System.err.println("[ERROR] Aplicación interrumpida: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("[ERROR FATAL] Error en la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n¡Simulación finalizada!");
    }
}