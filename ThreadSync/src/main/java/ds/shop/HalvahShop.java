package ds.shop;

import ds.silo.Silo;

public class HalvahShop extends Thread {

    private final Silo oilSilo;
    private final Silo flourSilo;
    private final Silo sugarSilo;

    public HalvahShop(Silo osilo, Silo fsilo, Silo ssilo) {
        super("Halvah");
        this.flourSilo = fsilo;
        this.oilSilo = osilo;
        this.sugarSilo = ssilo;
    }

    public void run() {
        while (true) {
            try {
                sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
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
