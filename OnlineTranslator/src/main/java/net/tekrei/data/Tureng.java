package net.tekrei.data;

import net.tekrei.utility.HttpUtility;
import org.jsoup.Jsoup;

public class Tureng implements Translator {
    private final Language[] supportedLanguages = new Language[]{
            new Language("English", "english"),
            new Language("French", "french"),
            new Language("German", "german"),
            new Language("Turkish", "turkish")
    };

    public Language[] getLanguages() {
        return supportedLanguages;
    }

    public String translate(String text, String from, String to) {
        try {
            String url;
            if (from.equals("english")) {
                url = "http://www.tureng.com/en/" + to + "-" + from + "/" + text;
            } else {
                url = "http://www.tureng.com/en/" + from + "-" + to + "/" + text;
            }
            return Jsoup.parse(HttpUtility.executeGet(url)).select(".tureng-searchresults-col-left").outerHtml();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String toString() {
        return "Tureng";
    }
}
