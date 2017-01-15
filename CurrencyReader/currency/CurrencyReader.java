package currency;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CurrencyReader {

	private static CurrencyReader instance = null;

	private CurrencyReader() {

	}

	public static CurrencyReader getInstance() {
		if (instance == null) {
			instance = new CurrencyReader();
		}
		return instance;
	}

	public Vector<Currency> getCurrencies() {
		Vector<Currency> items = new Vector<Currency>();
		try {
			// first create a document builder object
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			// we are going to download latest exchange rates XML file from an URL
			// it is possible to use a File URI here
			URL u = new URL("http://www.tcmb.gov.tr/kurlar/today.xml");
			//save document locally
			saveLocally(u.openStream());

			// Read document from URL and parse its XML content
			Document doc = builder.parse(u.openStream());

			// Get all elements with Currency tag
			NodeList nodes = doc.getElementsByTagName("Currency");
			// traverse them
			for (int i = 0; i < nodes.getLength(); i++) {
				// create a Currency instance for each element
				Currency currency = new Currency();
				Element element = (Element) nodes.item(i);
				// read properties of Currency element and set them to the
				// instance
				currency.setCurrencyCode(element.getAttribute("CurrencyCode"));
				currency.setKod(element.getAttribute("Kod"));
				currency.setCurrencyName(getElementValue(element, "CurrencyName"));
				currency.setIsim(getElementValue(element, "Isim"));
				currency.setBanknoteBuying(getFloat(getElementValue(element, "BanknoteBuying")));
				currency.setBanknoteSelling(getFloat(getElementValue(element, "BanknoteSelling")));
				currency.setCrossrateEURO(getFloat(getElementValue(element, "CrossrateEURO")));
				currency.setCrossrateOther(getFloat(getElementValue(element, "CrossrateOther")));
				currency.setCrossrateUSD(getFloat(getElementValue(element, "CrossrateUSD")));
				currency.setForexBuying(getFloat(getElementValue(element, "ForexBuying")));
				currency.setForexSelling(getFloat(getElementValue(element, "ForexSelling")));
				currency.setUnit(getFloat(getElementValue(element, "Unit")));
				// add currency instance to the vector
				items.add(currency);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return vector of currency items
		return items;
	}

	private void saveLocally(InputStream stream) throws IOException {
		FileWriter fos = new FileWriter("today.xml");
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		String inputLine;
		while ((inputLine = in.readLine()) != null)
		    fos.write(inputLine);
		fos.close();
		stream.close();
	}

	private String getCharacterDataFromElement(Element e) {
		try {
			Node child = e.getFirstChild();
			if (child instanceof CharacterData) {
				CharacterData cd = (CharacterData) child;
				return cd.getData();
			}
		} catch (Exception ex) {
		}
		return "";
	}

	protected float getFloat(String value) {
		if (value != null && !value.equals("")) {
			return Float.parseFloat(value);
		}
		return 0;
	}

	protected String getElementValue(Element parent, String label) {
		return getCharacterDataFromElement((Element) parent.getElementsByTagName(label).item(0));
	}
}
