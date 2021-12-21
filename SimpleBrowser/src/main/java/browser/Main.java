package browser;
/*
 * October, 2009
 */

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;

// The Simple Web Browser.
// Source : http://www.java-tips.org/java-se-tips/javax.swing/how-to-create-a-simple-browser-in-swing-3.html
public class Main extends JFrame implements HyperlinkListener {

    private final SitePanel sitePanel;

    // These are the buttons for iterating through the page list.
    private final JButton backButton;
    private final JButton forwardButton;

    // Page location text field.
    private final JTextField locationTextField;

    // Editor pane for displaying pages.
    private final JEditorPane displayEditorPane;

    // Browser's list of pages that have been visited.
    private final ArrayList<String> pageList = new ArrayList<>();

    // Constructor for Mini Web Browser.
    private Main() {
        // Set application title.
        super("Mini Web Browser");

        // Set window size.
        setSize(1024, 768);

        // Handle closing events.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set up file menu.
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenuItem fileExitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        fileExitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(fileExitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Set up button panel.
        JPanel browserButtonPanel = new JPanel();
        backButton = new JButton("< Back");
        backButton.addActionListener(e -> actionBack());
        backButton.setEnabled(false);
        browserButtonPanel.add(backButton);
        forwardButton = new JButton("Forward >");
        forwardButton.addActionListener(e -> actionForward());
        forwardButton.setEnabled(false);
        browserButtonPanel.add(forwardButton);
        locationTextField = new JTextField(35);
        locationTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    actionGo();
                }
            }
        });
        browserButtonPanel.add(locationTextField);
        JButton goButton = new JButton("GO");
        goButton.addActionListener(e -> actionGo());
        browserButtonPanel.add(goButton);

        // Set up page display.
        displayEditorPane = new JEditorPane();
        displayEditorPane.setContentType("text/html");
        displayEditorPane.setEditable(false);
        displayEditorPane.addHyperlinkListener(this);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(browserButtonPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(displayEditorPane),
                BorderLayout.CENTER);
        sitePanel = new SitePanel(this);
        getContentPane().add(new JScrollPane(sitePanel), BorderLayout.WEST);

        SiteButtonPanel siteButtonPanel = new SiteButtonPanel(this);
        getContentPane().add(siteButtonPanel, BorderLayout.SOUTH);
    }

    // Run the Mini Browser.
    public static void main(String[] args) {
        Main browser = new Main();
        browser.setVisible(true);
    }

    // Go back to the page viewed before the current page.
    private void actionBack() {
        URL currentUrl = displayEditorPane.getPage();
        int pageIndex = pageList.indexOf(currentUrl.toString());
        try {
            showPage(new URL(pageList.get(pageIndex - 1)), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Go forward to the page viewed after the current page.
    private void actionForward() {
        URL currentUrl = displayEditorPane.getPage();
        int pageIndex = pageList.indexOf(currentUrl.toString());
        try {
            showPage(new URL(pageList.get(pageIndex + 1)), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load and show the page specified in the location text field.
    private void actionGo() {
        go(locationTextField.getText());
    }

    void go(String url) {
        URL verifiedUrl = verifyUrl(url);
        if (verifiedUrl != null) {
            showPage(verifiedUrl, true);
        } else {
            showError("Invalid URL");
        }
    }

    // Show dialog box with error message.
    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    // Verify URL format.
    private URL verifyUrl(String url) {
        // Only allow HTTP URLs.
        if (!url.toLowerCase().startsWith("http://"))
            return null;

        // Verify format of URL.
        URL verifiedUrl;
        try {
            verifiedUrl = new URL(url);
        } catch (Exception e) {
            return null;
        }

        return verifiedUrl;
    }

    /*
     * Show the specified page and add it to the page list if specified.
     */
    private void showPage(URL pageUrl, boolean addToList) {
        // Show hour glass cursor while crawling is under way.
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            // Get URL of page currently being displayed.
            URL currentUrl = displayEditorPane.getPage();

            // Load and display specified page.
            displayEditorPane.setPage(pageUrl);

            // Get URL of new page being displayed.
            URL newUrl = displayEditorPane.getPage();

            // Add page to list if specified.
            if (addToList) {
                int listSize = pageList.size();
                if (listSize > 0) {
                    int pageIndex = pageList.indexOf(currentUrl.toString());
                    if (pageIndex < listSize - 1) {
                        for (int i = listSize - 1; i > pageIndex; i--) {
                            pageList.remove(i);
                        }
                    }
                }
                pageList.add(newUrl.toString());
            }

            // Update location text field with URL of current page.
            locationTextField.setText(newUrl.toString());

            // Update buttons based on the page being displayed.
            updateButtons();
        } catch (Exception e) {
            // Show error messsage.
            showError("Unable to load page");
        } finally {
            // Return to default cursor.
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /*
     * Update back and forward buttons based on the page being displayed.
     */
    private void updateButtons() {
        if (pageList.size() < 2) {
            backButton.setEnabled(false);
            forwardButton.setEnabled(false);
        } else {
            URL currentUrl = displayEditorPane.getPage();
            int pageIndex = pageList.indexOf(currentUrl.toString());
            backButton.setEnabled(pageIndex > 0);
            forwardButton.setEnabled(pageIndex < (pageList.size() - 1));
        }
    }

    // Handle hyperlink's being clicked.
    public void hyperlinkUpdate(HyperlinkEvent event) {
        HyperlinkEvent.EventType eventType = event.getEventType();
        if (eventType == HyperlinkEvent.EventType.ACTIVATED) {
            if (event instanceof HTMLFrameHyperlinkEvent) {
                HTMLFrameHyperlinkEvent linkEvent = (HTMLFrameHyperlinkEvent) event;
                HTMLDocument document = (HTMLDocument) displayEditorPane
                        .getDocument();
                document.processHTMLFrameHyperlinkEvent(linkEvent);
            } else {
                showPage(event.getURL(), true);
            }
        }
    }

    void addSite(Database.Site site) {
        try {
            Database.getInstance().siteEkle(site, sitePanel.getKategori());
            sitePanel.refreshList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void removeSite() {
        try {
            Database.getInstance().siteSil(sitePanel.getSiteURL());
            sitePanel.refreshList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}