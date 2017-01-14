package net.tekrei.ds.shop;

import net.tekrei.ds.silo.Silo;

public class HalvahShop extends Thread{
	
	Silo oilSilo;
	Silo flourSilo;
	Silo sugarSilo;

	public HalvahShop(Silo osilo,Silo fsilo,Silo ssilo) {
		super("Halvah");
		this.flourSilo = fsilo;
		this.oilSilo = osilo;
		this.sugarSilo = ssilo;
	}
	
	public void run(){
		while(true){
		    try {
		        sleep((int)(Math.random() * 100));
		    } catch (InterruptedException e) {
		    }
		    //2 unit oil
		    oilSilo.get();
		    oilSilo.get();
		    //1 unit flour
		    flourSilo.get();
		    //3 unit sugar
		    sugarSilo.get();
		    sugarSilo.get();
		    sugarSilo.get();
		}
	}

}
