public class Eleven extends Thread {
    private Buffer buffer;

    public Eleven(Buffer b){
        buffer = b;
    }

    @Override
    public void run() {
       for(int i=0; i < 20; i++){
        buffer.producir("Portal inestable");
       }
    }
}
