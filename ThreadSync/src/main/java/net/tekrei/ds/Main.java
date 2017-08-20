package net.tekrei.ds;

import net.tekrei.ds.producer.FlourProducer;
import net.tekrei.ds.producer.OilProducer;
import net.tekrei.ds.producer.SugarProducer;
import net.tekrei.ds.shop.HalvahShop;
import net.tekrei.ds.shop.HotFlakyPastyShop;
import net.tekrei.ds.silo.Silo;

public class Main {

    public static void main(String[] args) {
        Silo sugarSilo = new Silo("Sugar");
        Silo oilSilo = new Silo("Oil");
        Silo flourSilo = new Silo("Flour");

        new SugarProducer(sugarSilo).start();
        new OilProducer(oilSilo).start();
        new FlourProducer(flourSilo).start();
        new HotFlakyPastyShop(oilSilo, flourSilo).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new HalvahShop(oilSilo, flourSilo, sugarSilo).start();

        while (true) {
            System.out.println(oilSilo.toString() + " " + flourSilo.toString() + " " + sugarSilo.toString());
        }
    }
}
