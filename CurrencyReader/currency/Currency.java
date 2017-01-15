package currency;
public class Currency {
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

	public void setBanknoteBuying(float banknoteBuying) {
		this.banknoteBuying = banknoteBuying;
	}

	public float getBanknoteSelling() {
		return banknoteSelling;
	}

	public void setBanknoteSelling(float banknoteSelling) {
		this.banknoteSelling = banknoteSelling;
	}

	public float getCrossrateEURO() {
		return crossrateEURO;
	}

	public void setCrossrateEURO(float crossrateEURO) {
		this.crossrateEURO = crossrateEURO;
	}

	public float getCrossrateOther() {
		return crossrateOther;
	}

	public void setCrossrateOther(float crossrateOther) {
		this.crossrateOther = crossrateOther;
	}

	public float getCrossrateUSD() {
		return crossrateUSD;
	}

	public void setCrossrateUSD(float crossrateUSD) {
		this.crossrateUSD = crossrateUSD;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
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

	public float getUnit() {
		return unit;
	}

	public void setUnit(float unit) {
		this.unit = unit;
	}
	
	public String toString(){
		return currencyCode;
	}
}
