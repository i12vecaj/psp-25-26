public class Laboratorio_Hawkins extends Thread {
    private Buffer buffer;

    public Laboratorio_Hawkins(Buffer b){
        buffer=b;
    }

    @Override
    public void run() {
         for(int i=0; i < 20; i++){
        buffer.consumir();
       }
    }
    }

