package translator.data;

public class Language {
    private final String description;
    private final String language;

    Language(String _desc, String _lang) {
        this.description = _desc;
        this.language = _lang;
    }

    public String getLanguage() {
        return language;
    }

    public String toString() {
        return description;
    }
}
