package com.tst.cellsc.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class SessionUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7799471002895482287L;
	private JPanel contentPane;
	private JButton send;
	private JTextArea input;
	JTextPane info ;
	/**
	 * Create the frame.
	 */
	public SessionUI() {
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 707, 516);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				setVisible(false);
			}

			@Override
			public void windowOpened(WindowEvent e) {
				try {
					InetAddress inet = InetAddress.getLocalHost();
					String hostname = inet.getHostName();
					String hostaddr = inet.getHostAddress();
					StringBuilder sb = new StringBuilder();
					sb.append("hostname:"+hostname+"\n\r");
					sb.append("hostaddr:"+hostaddr);
					info.setText(sb.toString());
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				
			}
			
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea view = new JTextArea();
		view.setBounds(12, 12, 483, 320);
		view.setEditable(false);
		contentPane.add(view);
		
		send = new JButton("发送");
		send.setBounds(507, 427, 77, 60);
		send.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("xx");
				String inputText = input.getText();
				view.setText(view.getText()+"\n\r"+inputText);
				input.setText(null);
			}
		});
		contentPane.add(send);
		
		input = new JTextArea();
		input.setBounds(12, 346, 483, 158);
		contentPane.add(input);
		
		info = new JTextPane();
		info.setBounds(507, 12, 181, 320);
		info.setEditable(false);
		contentPane.add(info);
	}
}
