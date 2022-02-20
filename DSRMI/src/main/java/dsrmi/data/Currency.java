package dsrmi.data;

public class Currency implements java.io.Serializable {
    private float crossOrder;
    private float unit;
    private String isim;
    private String kod;
    private String CurrencyName;
    private String currencyCode;
    private float forexBuying;
    private float forexSelling;
    private float banknoteBuying;
    private float banknoteSelling;

    public float getCrossOrder() {
        return crossOrder;
    }

    public void setCrossOrder(float crossOrder) {
        this.crossOrder = crossOrder;
    }

    public float getUnit() {
        return unit;
    }

    public void setUnit(float unit) {
        this.unit = unit;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getCurrencyName() {
        return CurrencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.CurrencyName = currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public float getForexBuying() {
        return forexBuying;
    }

    public void setForexBuying(float forexBuying) {
        this.forexBuying = forexBuying;
    }

    public float getForexSelling() {
        return forexSelling;
    }

    public void setForexSelling(float forexSelling) {
        this.forexSelling = forexSelling;
    }

    public float getBanknoteBuying() {
        return banknoteBuying;
    }

    public void setBanknoteBuying(float banknoteBuying) {
        this.banknoteBuying = banknoteBuying;
    }

    public float getBanknoteSelling() {
        return banknoteSelling;
    }

    public void setBanknoteSelling(float banknoteSelling) {
        this.banknoteSelling = banknoteSelling;
    }

    @Override
    public String toString() {
        return String.format("Currency{CurrencyName='%s', currencyCode='%s', forexBuying=%s, forexSelling=%s}", CurrencyName, currencyCode, forexBuying, forexSelling);
    }
}
