package dsrmi.interfaces;

public interface InformationRetrieval extends java.rmi.Remote {

    String[] getCurrencies() throws java.rmi.RemoteException;

    float convertCurrency(String kod1, String kod2, float amount)
            throws java.rmi.RemoteException;
}
