package com.ceslopedevega.hilos;

public class SolicitaSuspender {
	private boolean suspender;
	
	public synchronized void set (boolean b)
	{
		suspender = b;
		notifyAll();
	}
	
	public synchronized void esperando() throws InterruptedException
	{
		while(suspender)
			wait();
	}
}
