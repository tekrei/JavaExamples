package net.tekrei.data;

import net.tekrei.utility.HttpUtility;
import org.jsoup.Jsoup;

public class Seslisozluk implements Translator {
    private final Language[] supportedLanguages = new Language[]{
            new Language("Turkish", "turkish"),
            new Language("English", "english")
    };

    public Language[] getLanguages() {
        return supportedLanguages;
    }

    public String translate(String text, String from, String to) {
        try {
            String url = "https://www.seslisozluk.net/" + text + "-nedir-ne-demek/";
            return Jsoup.parse(HttpUtility.executeGet(url)).select(".trans-box-shadow").outerHtml();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String toString() {
        return "Seslisözlük";
    }
}
