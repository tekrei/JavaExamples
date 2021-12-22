package translator.data;

import java.util.Vector;

public class TranslatorFactory {
    public static Vector<Translator> getTranslators() {
        Vector<Translator> translators = new Vector<>();
        translators.add(new Tureng());
        translators.add(new Seslisozluk());
        return translators;
    }
}
