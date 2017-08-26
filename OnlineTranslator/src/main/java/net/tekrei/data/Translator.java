package net.tekrei.data;

public interface Translator {

    String translate(String text, String from, String to);

    Language[] getLanguages();
}
