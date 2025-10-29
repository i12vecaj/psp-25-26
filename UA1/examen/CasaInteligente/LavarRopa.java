class LavarRopa implements Runnable {
    @Override
    public void run() {
        System.out.println("[Lavar ropa]Comenzando tarea");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[Lavar ropa] Tarea interrumpida.");
        }
        System.out.println("[Lavar ropa] Tarea finalizada.");
    }
}