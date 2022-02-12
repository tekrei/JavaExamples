package socketcalculator;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame {

    private static final long serialVersionUID = 1L;
    private ServerThread st;
    private JTextField txtPort;

    private Server() {
        super();
        initialize();
    }

    public static void main(String[] args) {
        new Server();
    }

    private void initialize() {
        this.setSize(new Dimension(367, 225));
        this.setTitle("CalcServer");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new GridLayout(0, 2));

        this.getContentPane().add(new JLabel("Port:"));
        txtPort = new JTextField("9999");
        this.getContentPane().add(txtPort);
        JButton btnStart = new JButton("START");
        btnStart.addActionListener(e -> {
            try {
                // start a new thread for listening
                st = new ServerThread();
                // start the thread
                st.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
            }
        });

        this.getContentPane().add(btnStart);
        JButton btnStop = new JButton("STOP");
        btnStop.addActionListener(e -> {
            if (st != null) {
                // Stop server thread
                st.stopServer();
            }
            st = null;
        });
        this.getContentPane().add(btnStop);

        this.setVisible(true);
    }

    class ServerThread extends Thread {

        private ServerSocket ss;

        private boolean isOpen = true;

        /**
         * Baslat dugmesine basilinca calisacak olan baslangic metodu
         */
        public void run() {
            try {
                System.out.println("Starting server!");
                // create server socket for listening
                ss = new ServerSocket(Integer.parseInt(txtPort.getText()));
                // it will always listen as long as it is not terminated
                isOpen = true;

                while (isOpen) {
                    try {
                        // wait for client connection
                        Socket socket = ss.accept();
                        // Iopen stream for reading request
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        // get request as Operation class
                        Operation request = calculate((Operation) in.readObject());
                        //log client connection
                        System.out.println("Client connected from " + socket.getInetAddress().getHostName());
                        // return result as Operation object
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        out.writeObject(request);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void stopServer() {
            try {
                // close the socket
                ss.close();
                // don't wait for connections anymore
                isOpen = false;
                // stop thread
                this.interrupt();
                System.out.println("Server terminated!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Operation calculate(Operation islem) {
            switch (islem.getOperationType()) {
                case ADD:
                    islem.setResult(islem.getNumber1() + islem.getNumber2());
                    break;
                case SUBTRACT:
                    islem.setResult(islem.getNumber1() - islem.getNumber2());
                    break;
                case DIVIDE:
                    islem.setResult((float) islem.getNumber1() / (float) islem.getNumber2());
                    break;
                case MULTIPLY:
                    islem.setResult(islem.getNumber1() * islem.getNumber2());
                    break;
            }
            return islem;
        }
    }
}