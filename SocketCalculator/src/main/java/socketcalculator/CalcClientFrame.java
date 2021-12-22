package socketcalculator;

import socketcalculator.Operation.OperationType;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Client of calculator with Swing based GUI
 */
public class CalcClientFrame extends JFrame {
    private JTextField txtNumber1 = null;
    private JTextField txtNumber2 = null;
    private JLabel lblResult = null;
    private JComboBox<Operation.OperationType> cmbOperation = null;
    private JTextField txtServer = null;
    private JTextField txtPort = null;

    /**
     * Constructor
     */
    private CalcClientFrame() {
        super();
        // initialize the UI
        initialize();
    }

    public static void main(String[] args) {
        new CalcClientFrame();
    }

    /**
     * This method initializes UI
     */
    private void initialize() {
        // window size
        this.setSize(300, 200);
        // add UI components
        this.setContentPane(getJContentPane());
        // Title of window
        this.setTitle("CalcClient");
        // Exit on close
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Show window
        this.setVisible(true);
    }

    /**
     * Add UI components to a JPanel and return it as content pane
     */
    private JPanel getJContentPane() {
        JPanel jContentPane = new JPanel();
        jContentPane.setLayout(new GridLayout(0, 2));
        // server
        jContentPane.add(new JLabel("Server:"));
        txtServer = new JTextField("localhost");
        jContentPane.add(txtServer);
        // port
        jContentPane.add(new JLabel("Port:"));
        txtPort = new JTextField("9999");
        jContentPane.add(txtPort);
        // first number
        jContentPane.add(new JLabel("1st number:"));
        txtNumber1 = new JTextField();
        txtNumber1.setBounds(new Rectangle(15, 15, 271, 16));
        jContentPane.add(txtNumber1);
        // second number
        jContentPane.add(new JLabel("2nd number:"));
        txtNumber2 = new JTextField();
        txtNumber2.setBounds(new Rectangle(15, 45, 271, 16));
        jContentPane.add(txtNumber2);
        // operations
        cmbOperation = new JComboBox<>(Operation.OperationType.values());
        cmbOperation.setBounds(new Rectangle(15, 75, 271, 24));
        jContentPane.add(cmbOperation);
        // calculate button
        JButton btnCalculate = new JButton();
        btnCalculate.setBounds(new Rectangle(75, 105, 167, 31));
        btnCalculate.setText("Calculate");
        //Alt+h also triggers the button
        btnCalculate.setMnemonic('c');
        btnCalculate.addActionListener(e -> calculate());
        jContentPane.add(btnCalculate);
        // result
        lblResult = new JLabel();
        lblResult.setText("Result:");
        jContentPane.add(lblResult);
        return jContentPane;
    }

    private void calculate() {
        try {
            // Create socket for connection to the server
            Socket socket = new Socket(txtServer.getText(), Integer.parseInt(txtPort.getText()));
            // Client request are stored in a Operation object
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            // and sent to the server over created socket
            out.writeObject(new Operation((OperationType) cmbOperation.getSelectedItem(),
                    Integer.parseInt(txtNumber1.getText()),
                    Integer.parseInt(txtNumber2.getText())));
            out.flush();
            // read result object from server
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Operation operation = (Operation) in.readObject();
            // write result to the label
            lblResult.setText("Result : " + operation.getResult());
            out.close();
            in.close();
            // close socket
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
