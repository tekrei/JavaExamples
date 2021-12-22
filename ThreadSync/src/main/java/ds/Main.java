package ds;

import ds.producer.FlourProducer;
import ds.producer.OilProducer;
import ds.producer.SugarProducer;
import ds.shop.HalvahShop;
import ds.shop.HotFlakyPastyShop;
import ds.silo.Silo;

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
