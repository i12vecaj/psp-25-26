public class Buffer {
    private int bufferCount = 0;
    private final int CAP_MAX = 5;

    public synchronized void detectEvent(){
        while (bufferCount == CAP_MAX){
            try {
                System.out.println(Thread.currentThread().getName() + " Suspendido, Buffer Lleno, Total: " + bufferCount);
                wait();
            }catch (InterruptedException e){
                System.out.println(Thread.currentThread().getName() + " Interrumpido");
            }
        }
        bufferCount++;
        System.out.println(Thread.currentThread().getName() + " Evento Depositado en el Buffer, Total: " + bufferCount);
        notifyAll();
    }

    public synchronized void processEvent(){
        while (bufferCount == 0){
            try {
                System.out.println(Thread.currentThread().getName() + " Suspendido, Buffer Vacio, Total: " + bufferCount);
                wait();
            }catch (InterruptedException e){
                System.out.println(Thread.currentThread().getName() + " Interrumpido");
            }
        }
        bufferCount--;
        System.out.println(Thread.currentThread().getName() + " Evento Procesado, Buffer Total: " + bufferCount);
        notifyAll();
    }
}
