package com.ceslopedevega.hilos;

public class HiloSimple1 extends Thread {
	private int id;
	private int iter;
	
	public HiloSimple1 (int id, int iter)
	{
		this.id = id;
		this.iter = iter;
	}

    public void run(){

        System.out.println("EL HILO NUMERO: " + id + "º" + "\nNUMERO DE INTERACIONES: " + iter);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
