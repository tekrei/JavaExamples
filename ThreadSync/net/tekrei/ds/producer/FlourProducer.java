package net.tekrei.ds.producer;

import net.tekrei.ds.silo.Silo;

public class FlourProducer extends Thread {
	
	Silo silo;

	public FlourProducer(Silo silo) {
		super("Flour Producer");
		this.silo = silo;
	}
	
	public void run(){
		while(true){
		    try {
		        sleep((int)(Math.random() * 100));
		    } catch (InterruptedException e) {
		    }
		    silo.put();
		}
	}
}
