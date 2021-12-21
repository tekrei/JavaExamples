package ds.producer;

import ds.silo.Silo;

public class FlourProducer extends Thread {

    private final Silo silo;

    public FlourProducer(Silo silo) {
        super("Flour Producer");
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
