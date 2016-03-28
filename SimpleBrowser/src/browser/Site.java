package browser;
public class Site {
	public String isim;
	public String url;

	public Site(String _isim, String _url) {
		isim = _isim;
		url = _url;
	}
	
	public String toString(){
		return isim;
	}
}
