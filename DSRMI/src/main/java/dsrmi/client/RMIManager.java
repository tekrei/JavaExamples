package dsrmi.client;

import dsrmi.interfaces.InformationRetrieval;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIManager {

    private static RMIManager instance = null;

    private InformationRetrieval ir;

    public static RMIManager getInstance() {
        if (instance == null) {
            instance = new RMIManager();
        }
        return instance;
    }

    public void init(String host, int port) {
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            ir = (InformationRetrieval) registry.lookup("InformationRetrieval");
            System.out.println("Initialized Information Retrieval object:" + ir);
        } catch (Exception e) {
            System.out.println("An error occured:" + e);
            e.printStackTrace();
        }
    }

    public float convert(String currency1, String currency2, float value) throws RemoteException {
        return ir.convertCurrency(currency1, currency2, value);
    }

    public String[] getCurrencies() throws RemoteException {
        return ir.getCurrencies();
    }
}
