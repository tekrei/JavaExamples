package browser;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SitePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JComboBox<String> cmbCategory;
	private JList<Site> lstSites;
	private DefaultListModel<Site> listModel;
	private Browser browser;

	public SitePanel(Browser _browser) {
		browser = _browser;
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(createCmbKategori(), BorderLayout.NORTH);
		this.add(createLstSiteler(), BorderLayout.CENTER);
	}

	private JList<Site> createLstSiteler() {
		listModel = new DefaultListModel<Site>();
		lstSites = new JList<Site>(listModel);
		lstSites.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		lstSites.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String url = getSiteURL();
				if (url != null)
					browser.go(url);
			}
		});
		return lstSites;
	}

	public void refreshList() {
		this.populateList(getKategori());
	}

	private void populateList(int kategori) {
		try {
			ArrayList<Site> siteler = Database.getInstance().getSiteler(kategori);
			listModel.removeAllElements();
			for (Site site : siteler) {
				listModel.addElement(site);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JComboBox<String> createCmbKategori() {
		cmbCategory = new JComboBox<String>();
		try {
			ArrayList<String> kategoriler = Database.getInstance().getKategoriler();
			for (String string : kategoriler) {
				cmbCategory.addItem(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cmbCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				populateList(getKategori());
			}
		});

		return cmbCategory;
	}

	public int getKategori() {
		return cmbCategory.getSelectedIndex() + 1;
	}

	public String getSiteURL() {
		return ((Site) lstSites.getSelectedValue()).url;
	}
}
