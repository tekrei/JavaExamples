package net.tekrei.ds;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CalcServerFrame extends JFrame {

	private JTextField txtPort;
	private JButton btnStart;
	private JButton btnStop;

	ServerThread st;

	private static final long serialVersionUID = 1L;

	public CalcServerFrame() {
		super();
		initialize();
	}

	private void initialize() {
		this.setSize(new Dimension(367, 225));
		this.setTitle("CalcServer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new GridLayout(0, 2));

		this.getContentPane().add(new JLabel("Port:"));
        			txtPort = new JTextField("9999");
		this.getContentPane().add(txtPort);
			btnStart = new JButton("START");
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						// start a new thread for listening
						st = new ServerThread();
						// start the thread
						st.start();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
					}
				}
			});
        
		this.getContentPane().add(btnStart);
			btnStop = new JButton("STOP");
			btnStop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (st != null) {
						// Stop server thread
						st.stopServer();
					}
					st = null;
				}
			});
		this.getContentPane().add(btnStop);

		this.setVisible(true);
	}

	public static void main(String[] args) {
		new CalcServerFrame();
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

		public void stopServer() {
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
			switch(islem.getOperationType()){
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
} // @jve:decl-index=0:visual-constraint="10,10"
