package dsrmi.server;

import dsrmi.data.Currency;
import dsrmi.interfaces.InformationRetrieval;
import dsrmi.reader.TCMBReader;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class InformationRetrievalImpl implements
        InformationRetrieval {

    final List<Currency> currencies = new TCMBReader().getCurrencies();

    public static void main(String[] args) {
        try {
            InformationRetrieval service = new InformationRetrievalImpl();
            InformationRetrieval stub = (InformationRetrieval) UnicastRemoteObject.exportObject(service, 0);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("InformationRetrieval", stub);
            System.out.println("Information retrieval is ready");
        } catch (Exception e) {
            System.err.println("Information retrieval exception: " + e);
            e.printStackTrace();
        }
    }

    public String[] getCurrencies() {
        System.out.printf("Sending %d currencies%n", currencies.size());
        return currencies.stream().map(Currency::getCurrencyCode).toArray(String[]::new);
    }

    public float convertCurrency(String kod1, String kod2, float amount) {
        Currency kod1Currency = getCurrency(kod1);
        Currency kod2Currency = getCurrency(kod2);
        if (kod1Currency != null && kod2Currency != null) {
            float result = (kod1Currency.getForexBuying() / kod1Currency.getUnit())
                    * amount / (kod2Currency.getForexSelling() / kod2Currency.getUnit());
            System.out.printf("%s %s is equal to %s %s%n", amount, kod1, result, kod2);
            return result;
        }
        return 0f;
    }

    private Currency getCurrency(String currencyCode) {
        for (Currency currency : currencies) {
            if (currency.getCurrencyCode().equals(currencyCode)) {
                System.out.printf("Sending currency%s%n", currency);
                return currency;
            }
        }
        return null;
    }
}
