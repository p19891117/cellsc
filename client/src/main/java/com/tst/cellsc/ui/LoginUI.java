package com.tst.cellsc.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7559014878658025846L;
	private JPanel contentPane;
	private JTextField usernameT;
	private JTextField passwordT;
	private JLabel passwordL;
	public void init(){
		setResizable(false);
		setTitle("登录界面");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 290);
		//setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	public void initPane(){
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	public void initUsernameT(){
		usernameT = new JTextField();
		usernameT.setBounds(114, 63, 181, 38);
		contentPane.add(usernameT);
		usernameT.setColumns(10);
	}
	public void initPasswordT(){
		passwordT = new JTextField();
		passwordT.setBounds(114, 136, 181, 38);
		contentPane.add(passwordT);
		passwordT.setColumns(10);
	}
	/**
	 * Create the frame.
	 */
	public LoginUI() {
		init();
		initPane();
		initUsernameT();
		initPasswordT();
		JLabel usernameL = new JLabel("账户");
		usernameL.setBounds(59, 63, 37, 38);
		contentPane.add(usernameL);
		
		passwordL = new JLabel("密码");
		passwordL.setBounds(59, 136, 47, 38);
		contentPane.add(passwordL);
		
		JButton loginB = new JButton("登录");
		loginB.setBounds(307, 70, 72, 23);
		contentPane.add(loginB);
		loginB.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//判断账号，密码				
				new ChatUI();
				setVisible(false);
			}
			
		});
		
		JButton registerB = new JButton("注册");
		registerB.setBounds(307, 143, 72, 23);
		contentPane.add(registerB);
	}
}
