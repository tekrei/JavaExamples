package net.tekrei.db2pojo;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Db2Pojo extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public Db2Pojo() {
		super();
		initialize();
	}

	private void initialize() {
        this.setSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("DB2POJO");
        this.setContentPane(new Db2PojoPanel());
        this.setVisible(true);
	}

	public static void main(String[] args){
		new Db2Pojo();
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
