package net.tekrei.ds.producer;

import net.tekrei.ds.silo.Silo;

public class SugarProducer extends Thread {
	
	Silo silo;

	public SugarProducer(Silo silo) {
		super("Sugar Producer");
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
