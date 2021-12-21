package ds.producer;

import ds.silo.Silo;

public class SugarProducer extends Thread {

    private final Silo silo;

    public SugarProducer(Silo silo) {
        super("Sugar Producer");
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
