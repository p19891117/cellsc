package feiqq.ui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import feiqq.bean.Message;
import feiqq.socket.client.Client;
import feiqq.ui.common.MyOptionPane;
import feiqq.ui.common.MyScrollBarUI;
import feiqq.util.Constants;
import feiqq.util.PictureUtil;
import feiqq.util.StringUtil;
import feiqq.util.ValidateUtil;

public class RegisterWindow  extends JDialog {
	/** 主面板 */
	private JPanel content;
	/** 退出按钮 */
	private JLabel exitButton;
	
	private JLabel okButton;
	private JLabel quitButton;

	/** 账号Txt */
	private JLabel userNameLabel;
	/** 昵称Txt */
	private JLabel nickNameLabel;
	/** 密码Txt */
	private JLabel passWordLabel;
	/** 确认密码Txt */
	private JLabel repeatPassLabel;
	/** 签名Txt */
	private JLabel signatureLabel;

	/** 账号 */
	private JTextField userNameField;
	/** 昵称 */
	private JTextField nickNameField;
	/** 密码 */
	private JPasswordField passWordField;
	/** 确认密码 */
	private JPasswordField repeatPassField;
	/** 签名 */
	private JTextArea signatureArea;
	private JScrollPane signatureScroll;
	
	private Point point = new Point();
	private Client client;
	
	public static RegisterWindow getInstance(Client client) {
		RegisterWindow inst = new RegisterWindow(client);
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);
		return inst;
	}
	
	public RegisterWindow(Client client) {
		super();
		initGUI();
		initListener();
		this.client = client;
	}
	
	private void initGUI() {
		try {
			setSize(397, 300);
			setUndecorated(true);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			
			content = new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(PictureUtil.getPicture("back5.jpg").getImage(), 0, 0, null);
					this.setOpaque(false);
				}
			};
			content.setLayout(null);
			getContentPane().add(content, BorderLayout.CENTER);
			content.setBorder(Constants.GRAY_BORDER);
		
			exitButton = new JLabel();
			content.add(exitButton);
			exitButton.setBounds(358, 0, 40, 20);
			exitButton.setIcon(PictureUtil.getPicture("close.png"));

			nickNameLabel = new JLabel("昵      称：");
			content.add(nickNameLabel);
			nickNameLabel.setFont(Constants.BASIC_FONT);
			nickNameLabel.setBounds(30, 33, 70, 20);
			
			nickNameField = new JTextField();
			content.add(nickNameField);
			nickNameField.setBounds(110, 30, 240, 28);
			nickNameField.setBorder(Constants.LIGHT_GRAY_BORDER);
			
			userNameLabel = new JLabel("账      号");
			content.add(userNameLabel);
			userNameLabel.setFont(Constants.BASIC_FONT);
			userNameLabel.setBounds(30, 73, 70, 20);
			
			userNameField = new JTextField();
			content.add(userNameField);
			userNameField.setBounds(110, 70, 240, 28);
			userNameField.setBorder(Constants.LIGHT_GRAY_BORDER);
		
			passWordLabel = new JLabel("密      码");
			content.add(passWordLabel);
			passWordLabel.setFont(Constants.BASIC_FONT);
			passWordLabel.setBounds(30, 113, 70, 20);
			
			passWordField = new JPasswordField();
			content.add(passWordField);
			passWordField.setBounds(110, 110, 240, 28);
			passWordField.setBorder(Constants.LIGHT_GRAY_BORDER);
		
			repeatPassLabel = new JLabel("确认密码");
			content.add(repeatPassLabel);
			repeatPassLabel.setFont(Constants.BASIC_FONT);
			repeatPassLabel.setBounds(30, 153, 70, 20);

			repeatPassField = new JPasswordField();
			content.add(repeatPassField);
			repeatPassField.setBounds(110, 150, 240, 28);
			repeatPassField.setBorder(Constants.LIGHT_GRAY_BORDER);
		
			signatureLabel = new JLabel("备      注");
			content.add(signatureLabel);
			signatureLabel.setFont(Constants.BASIC_FONT);
			signatureLabel.setBounds(30, 193, 70, 20);
		
			signatureScroll = new JScrollPane();
			content.add(signatureScroll);
			signatureArea = new JTextArea();
			signatureArea.setTabSize(2);//TAB键多少位 
			signatureArea.setLineWrap(true);//激活自动换行功能
			signatureArea.setWrapStyleWord(true);//激活断行不断字功能
			signatureScroll.setBorder(Constants.LIGHT_GRAY_BORDER);
			signatureScroll.setViewportView(signatureArea);
			signatureScroll.getVerticalScrollBar().setUI(new MyScrollBarUI());
			signatureScroll.setBounds(110, 190, 240, 60);
		
			okButton = new JLabel("确定", JLabel.CENTER);
			content.add(okButton);
			okButton.setFont(Constants.BASIC_FONT);
			okButton.setBorder(null);
			okButton.setBounds(110, 260, 80, 30);
			okButton.setOpaque(false);
			okButton.setBackground(new Color(240, 245, 240, 60));
		
			quitButton = new JLabel("取消", JLabel.CENTER);
			content.add(quitButton);
			quitButton.setFont(Constants.BASIC_FONT);
			quitButton.setBorder(null);
			quitButton.setBounds(220, 260, 80, 30);
			quitButton.setOpaque(false);
			quitButton.setBackground(new Color(240, 245, 240, 60));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initListener() {
		// 主窗体事件
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				point.x = e.getX();
				point.y = e.getY();
			}
		});
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
			}
		});
		// 确定按钮事件
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				okButton.setBorder(null);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				okButton.setBorder(Constants.GRAY_BORDER);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				register();
			}
		});
		// 取消按钮事件
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				quitButton.setBorder(null);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				quitButton.setBorder(Constants.GRAY_BORDER);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				dispose();
				client.setRegister(null);
			}
		});
		// 退出按钮事件
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(PictureUtil.getPicture("close.png"));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(PictureUtil.getPicture("close_active.png"));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				dispose();
				client.setRegister(null);
			}
		});
		// 输入框焦点事件
		nickNameField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				nickNameField.setBorder(Constants.LIGHT_GRAY_BORDER);
			}
			@Override
			public void focusGained(FocusEvent e) {
				nickNameField.setBorder(Constants.ORANGE_BORDER);
			}
		});
		userNameField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				userNameField.setBorder(Constants.LIGHT_GRAY_BORDER);
			}
			@Override
			public void focusGained(FocusEvent e) {
				userNameField.setBorder(Constants.ORANGE_BORDER);
			}
		});
		passWordField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				passWordField.setBorder(Constants.LIGHT_GRAY_BORDER);
			}
			@Override
			public void focusGained(FocusEvent e) {
				passWordField.setBorder(Constants.ORANGE_BORDER);
			}
		});
		repeatPassField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				repeatPassField.setBorder(Constants.LIGHT_GRAY_BORDER);
			}
			@Override
			public void focusGained(FocusEvent e) {
				repeatPassField.setBorder(Constants.ORANGE_BORDER);
			}
		});
		signatureArea.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				signatureScroll.setBorder(Constants.LIGHT_GRAY_BORDER);
			}
			@Override
			public void focusGained(FocusEvent e) {
				signatureScroll.setBorder(Constants.ORANGE_BORDER);
			}
		});
	}
	
	// 注册
	private void register() {
		String nick = nickNameField.getText();
		String name = userNameField.getText();
		String pass = String.valueOf(passWordField.getPassword());
		String rept = String.valueOf(repeatPassField.getPassword());
		String sign = signatureArea.getText();
		// 验证
		if (StringUtil.isEmpty(nick)) {
			MyOptionPane.showMessageDialog(client.getRegister(), "需要一个帅气的昵称！", "友情提示");
			return;
		}
		if (nick.length() > 10) {
			MyOptionPane.showMessageDialog(client.getRegister(), "昵称长度不可以超过10位！", "友情提示");
			nickNameField.setText(nick.substring(0, 10));
			return;
		}
		if (StringUtil.isEmpty(name)) {
			MyOptionPane.showMessageDialog(client.getRegister(), "没有输入账号！", "友情提示");
			return;
		}
		if (!ValidateUtil.isNumber(name)) {
			MyOptionPane.showMessageDialog(client.getRegister(), "账号必须为数字！", "友情提示");
			return;
		}
		if (name.length() > 10) {
			MyOptionPane.showMessageDialog(client.getRegister(), "账号长度不可以超过10位！", "友情提示");
			userNameField.setText(name.substring(0, 10));
			return;
		}
		if (StringUtil.isEmpty(pass)) {
			MyOptionPane.showMessageDialog(client.getRegister(), "没有输入密码！", "友情提示");
			return;
		}
		if (pass.length() > 10) {
			MyOptionPane.showMessageDialog(client.getRegister(), "密码长度不可以超过10位！", "友情提示");
			passWordField.setText(pass.substring(0, 10));
			return;
		}
		if (StringUtil.isEmpty(rept)) {
			MyOptionPane.showMessageDialog(client.getRegister(), "还需要确认密码！", "友情提示");
			return;
		}
		if (rept.length() > 10) {
			MyOptionPane.showMessageDialog(client.getRegister(), "确认密码也不可以超过10位！", "友情提示");
			repeatPassField.setText(rept.substring(0, 10));
			return;
		}
		if (!StringUtil.isEqual(pass, rept)) {
			MyOptionPane.showMessageDialog(client.getRegister(), "两次密码输入不一致！", "友情提示");
			return;
		}
		if (StringUtil.isEmpty(sign)) {
			int res = MyOptionPane.showConfirmDialog(client.getRegister(), "确定or取消？", "来一个帅气的签名吧！", "确定", "取消");
			if (res == Constants.NO_OPTION) {
				return;
			}
		}
		if (sign.length() > 50) {
			MyOptionPane.showMessageDialog(client.getRegister(), "签名长度不可以超过50位！", "友情提示");
			signatureArea.setText(sign.substring(0, 50));
			return;
		}
		// 登录服务器
		String str = nick + Constants.LEFT_SLASH + name + Constants.LEFT_SLASH + pass + Constants.LEFT_SLASH + sign;
		client.sendMsg(new Message(Constants.REGISTER_MSG, str));
	}

}
