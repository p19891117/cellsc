package com.tst.cellsc.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ChatUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7451151439158725576L;
	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public ChatUI() {
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 260, 435);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
}
