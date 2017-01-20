package com.tst.cellsc.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
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
		setBounds(100, 100, 329, 642);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 27, 307, 603);
		JPanel friend = new JPanel();
		friend.setVisible(true);
		tabbedPane.addTab("好友", friend);
		friend.setLayout(null);
		int y = 12;
		for(int x =1;x<=10;x++){
			JLabel lblNewLabel = new JLabel("好友"+x);
			lblNewLabel.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					new SessionUI();
				}
				
			});
			lblNewLabel.setBounds(10, y, 63, 13);
			y = y+15;
			friend.add(lblNewLabel);
		}
		tabbedPane.addTab("群组", new JPanel());
		contentPane.add(tabbedPane);
	}
}
