class ua2tarea1fr2runnable implements Runnable{
    public void run(){
        //Utilizo syncronized y le paso mi clase
        synchronized (ua2tarea1fr2.class){

            //Utilizo el cont referenciandolo mediante la otra clase
            ua2tarea1fr2.cont = ua2tarea1fr2.cont + 1000;
            //NO OLVIDAR JUAN JOSÉ
            //En la interzaf debo usar Thread. no getId() directamente
            System.out.println("Hilo: " + Thread.currentThread().getId());
            System.out.println("Contador: " + ua2tarea1fr2.cont);
        }

    }
}
