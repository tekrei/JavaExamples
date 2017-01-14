package net.tekrei.ds.producer;

import net.tekrei.ds.silo.Silo;

public class OilProducer extends Thread {
	
	Silo silo;

	public OilProducer(Silo silo) {
		super("Oil Producer");
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
