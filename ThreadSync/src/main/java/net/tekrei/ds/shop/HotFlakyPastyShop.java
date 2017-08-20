package net.tekrei.ds.shop;

import net.tekrei.ds.silo.Silo;

public class HotFlakyPastyShop extends Thread {

    private final Silo flourSilo;
    private final Silo oilSilo;

    public HotFlakyPastyShop(Silo osilo, Silo fsilo) {
        super("Hot Flaky Pasty");
        this.flourSilo = fsilo;
        this.oilSilo = osilo;
    }

    public void run() {
        while (true) {
            try {
                sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //1 unit oil
            oilSilo.get();
            //1 unit flour
            flourSilo.get();
        }
    }
}
