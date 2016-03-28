package browser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SiteButtonPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Browser tarayici;

	public SiteButtonPanel(Browser _t) {
		tarayici = _t;
		initialize();
	}

	private void initialize() {
		JButton btn = new JButton("Add");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String ad = (String) JOptionPane.showInputDialog(tarayici, "Site adı:", "Site adı",
						JOptionPane.PLAIN_MESSAGE, null, null, null);
				String adres = (String) JOptionPane.showInputDialog(tarayici, "Site adresi:", "Site adresi",
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				// If a string was returned, say so.
				if ((ad != null) && (ad.length() > 0) && ((adres != null) && (adres.length() > 0))) {
					tarayici.addSite(new Site(ad, adres));
				}
			}
		});
		this.add(btn);
		btn = new JButton("Remove");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tarayici.removeSite();
			}
		});
		this.add(btn);
	}
}
