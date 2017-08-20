package net.tekrei.ds.silo;

public class Silo {
    //make the producers wait when it exceedes to 20 unit
    private final String what;
    private int unit;

    public Silo(String what) {
        super();
        this.what = what;
    }

    public synchronized void get() {
        // we don't have any product at hand
        while (unit == 0) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        unit--;
        System.out.println(Thread.currentThread().getName() + " getting " + this.what);
        notify();
    }

    public synchronized void put() {
        while (unit == 20) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        unit++;
        System.out.println(Thread.currentThread().getName() + " putting " + this.what);
        notify();
    }

    public String toString() {
        return what + ":" + unit;
    }
}
