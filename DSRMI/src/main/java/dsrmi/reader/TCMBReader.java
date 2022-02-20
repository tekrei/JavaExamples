package dsrmi.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dsrmi.data.Currency;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TCMBReader {

    public static void main(String[] args) {
        new TCMBReader().getCurrencies();
    }

    public List<Currency> getCurrencies() {
        System.out.println("Retrieving the currencies");
        try {
            XmlMapper mapper = XmlMapper.builder()
                    .defaultUseWrapper(false)
                    .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .build();

            return mapper.readValue(new URL("https://www.tcmb.gov.tr/kurlar/today.xml"), new TypeReference<List<Currency>>() {
                    })
                    .stream()
                    .filter(item -> item.getCurrencyCode() != null)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
