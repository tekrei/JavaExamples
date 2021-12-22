package ds.producer;

import ds.silo.Silo;

public class OilProducer extends Thread {

    private final Silo silo;

    public OilProducer(Silo silo) {
        super("Oil Producer");
        this.silo = silo;
    }

    public void run() {
        while (true) {
            try {
                sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            silo.put();
        }
    }
}
