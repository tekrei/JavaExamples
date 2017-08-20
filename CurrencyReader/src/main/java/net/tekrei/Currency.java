package net.tekrei;

class Currency {
    private String kod;
    private String currencyCode;
    private String isim;
    private String currencyName;
    private float unit;
    private float forexBuying;
    private float forexSelling;
    private float banknoteBuying;
    private float banknoteSelling;
    private float crossrateUSD;
    private float crossrateOther;
    private float crossrateEURO;

    public float getBanknoteBuying() {
        return banknoteBuying;
    }

    void setBanknoteBuying(float banknoteBuying) {
        this.banknoteBuying = banknoteBuying;
    }

    public float getBanknoteSelling() {
        return banknoteSelling;
    }

    void setBanknoteSelling(float banknoteSelling) {
        this.banknoteSelling = banknoteSelling;
    }

    public float getCrossrateEURO() {
        return crossrateEURO;
    }

    void setCrossrateEURO(float crossrateEURO) {
        this.crossrateEURO = crossrateEURO;
    }

    float getCrossrateOther() {
        return crossrateOther;
    }

    void setCrossrateOther(float crossrateOther) {
        this.crossrateOther = crossrateOther;
    }

    float getCrossrateUSD() {
        return crossrateUSD;
    }

    void setCrossrateUSD(float crossrateUSD) {
        this.crossrateUSD = crossrateUSD;
    }

    String getCurrencyCode() {
        return currencyCode;
    }

    void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    float getForexBuying() {
        return forexBuying;
    }

    void setForexBuying(float forexBuying) {
        this.forexBuying = forexBuying;
    }

    float getForexSelling() {
        return forexSelling;
    }

    void setForexSelling(float forexSelling) {
        this.forexSelling = forexSelling;
    }

    public String getIsim() {
        return isim;
    }

    void setIsim(String isim) {
        this.isim = isim;
    }

    public String getKod() {
        return kod;
    }

    void setKod(String kod) {
        this.kod = kod;
    }

    float getUnit() {
        return unit;
    }

    void setUnit(float unit) {
        this.unit = unit;
    }

    public String toString() {
        return currencyCode;
    }
}
