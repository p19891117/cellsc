package feiqq.ui.frame;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import feiqq.bean.Message;
import feiqq.socket.client.Client;
import feiqq.ui.common.MyOptionPane;
import feiqq.util.Constants;
import feiqq.util.PictureUtil;
import feiqq.util.StringUtil;

// TODO 逆天所在JDialog
public class LoginWindow extends JDialog {
	/** 主面板 */
	private JPanel content;
	/** 最小化按钮 */
	private JLabel minButton;
	/** 退出按钮 */
	private JLabel exitButton;
	/** 头像 */
	private JLabel pictureLabel;
	/** 账号 */
	private JTextField userNameField;
	/** 密码 */
	private JPasswordField passWordField;
	/** 保存密码Box */
	private JLabel savePassCheckBox;
	/** 保存密码Txt */
	private JLabel savePassLabel;
	/** 自动登录Box */
	private JLabel autoLoginCheckBox;
	/** 自动登录Txt */
	private JLabel autoLoginLabel;
	/** 登陆按钮 */
	private JLabel loginButton;
	/** 注册账号 */
	private JLabel registerLabel;
	/** 找回密码 */
	private JLabel findPassLabel;
	/** 坐标（拖动记录） */
	private Point point = new Point();
	
	private boolean isSavePass;
	private boolean isAutoLogin;
	private TrayIcon icon;
	private SystemTray tray;
	private Client client;
	
	
	public static LoginWindow getInstance(Client client) {
		LoginWindow inst = new LoginWindow(client);
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);
		return inst;
	}
	
	public LoginWindow(Client client) {
		super();
		this.client = client;
		initGUI();
		initTrayIcon();
		initListener();
	}

	private void initGUI() {
		try {
			setSize(397, 300);
			setUndecorated(true);
//			TODO 这个API导致输入中文白屏
//			AWTUtilities.setWindowOpaque(this, false);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			
			// 主面板
			content = new JPanel() {
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(PictureUtil.getPicture("back5.jpg").getImage(), 0, 0, null);
					this.setOpaque(false);
				}
			};
			content.setLayout(null);
			content.setBorder(Constants.GRAY_BORDER);
			getContentPane().add(content, BorderLayout.CENTER);
			
			// 头像区域
			pictureLabel = new JLabel();
			content.add(pictureLabel);
			pictureLabel.setBounds(21, 125, 90, 90);
			pictureLabel.setBorder(Constants.LIGHT_GRAY_BORDER);
			pictureLabel.setIcon(PictureUtil.getPicture("picture1.jpg"));
		
			// 账号
			userNameField = new JTextField();
			content.add(userNameField);
			userNameField.setBounds(121, 126, 183, 30);
			userNameField.setBorder(Constants.LIGHT_GRAY_BORDER);
				
			// 密码
			passWordField = new JPasswordField();
			content.add(passWordField);
			passWordField.setBounds(121, 158, 183, 30);
			passWordField.setBorder(Constants.LIGHT_GRAY_BORDER);
		
			// 最小化按钮
			minButton = new JLabel();
			content.add(minButton);
			minButton.setBounds(330, 0, 30, 20);
			minButton.setIcon(PictureUtil.getPicture("minimize.png"));
		
			// 关闭按钮
			exitButton = new JLabel();
			content.add(exitButton);
			exitButton.setBounds(358, 0, 40, 20);
			exitButton.setIcon(PictureUtil.getPicture("close.png"));
		
			// 保存密码Box
			savePassCheckBox = new JLabel();
			content.add(savePassCheckBox);
			savePassCheckBox.setBounds(121, 195, 20, 22);
			savePassCheckBox.setIcon(PictureUtil.getPicture("buxuanzhong.png"));
		
			// 保存密码Txt
			savePassLabel = new JLabel("保存密码");
			content.add(savePassLabel);
			savePassLabel.setFont(Constants.BASIC_FONT);
			savePassLabel.setBounds(147, 192, 54, 24);
		
			// 自动登录Box
			autoLoginCheckBox = new JLabel();
			content.add(autoLoginCheckBox);
			autoLoginCheckBox.setBounds(226, 195, 20, 22);
			autoLoginCheckBox.setIcon(PictureUtil.getPicture("buxuanzhong.png"));
		
			// 自动登录Txt
			autoLoginLabel = new JLabel("自动登录");
			content.add(autoLoginLabel);
			autoLoginLabel.setFont(Constants.BASIC_FONT);
			autoLoginLabel.setBounds(251, 192, 52, 24);
		
			// 登陆按钮
			loginButton = new JLabel();
			content.add(loginButton);
			loginButton.setBounds(118, 238, 186, 40);
			loginButton.setIcon(PictureUtil.getPicture("login_button.png"));
			
			// 注册账号
			registerLabel = new JLabel("注册账号");
			content.add(registerLabel);
			registerLabel.setFont(Constants.BASIC_FONT);
			registerLabel.setBounds(314, 129, 62, 24);
			
			// 找回密码
			findPassLabel = new JLabel("找回密码");
			content.add(findPassLabel);
			findPassLabel.setFont(Constants.BASIC_FONT);
			findPassLabel.setBounds(314, 160, 62, 27);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// TODO 这个地方可以考虑，tray就只要一个，在主窗体出来之后换掉其上面的监听
	private void initTrayIcon() {
		if (SystemTray.isSupported()) {
			try {
				tray = SystemTray.getSystemTray();
				icon = new TrayIcon(PictureUtil.getPicture("/qq_icon.png").getImage(), "FEIQQ");
				icon.setImageAutoSize(true); //自动适应大小
				icon.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if (e.getButton() == MouseEvent.BUTTON1) {
							setVisible(true);
							// 获取焦点
							requestFocus();
						}
					}
				});
				
				PopupMenu pm = new PopupMenu();
				MenuItem mit = new MenuItem("Exit");
				mit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tray.remove(icon);
						System.exit(0);
					}
				});
				pm.add(mit);
				icon.setPopupMenu(pm);
				tray.add(icon);
				// 放到client类中
				client.setIcon(icon);
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		// 最小化按钮事件
		minButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				minButton.setIcon(PictureUtil.getPicture("minimize.png"));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				minButton.setIcon(PictureUtil.getPicture("minimize_active.png"));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				setVisible(false);
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
				tray.remove(icon);
				System.exit(0);
			}
		});
		// 账号输入框焦点事件
		userNameField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				userNameField.setBorder(Constants.LIGHT_GRAY_BORDER);
			}
			@Override
			public void focusGained(FocusEvent e) {
				userNameField.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
			}
		});
		userNameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
			}
		});
		// 密码输入框焦点事件
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
		passWordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
			}
		});
		// 保存密码复选框事件
		savePassCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isSavePass) {
					savePassCheckBox.setIcon(PictureUtil.getPicture("buxuanzhong.png"));
					isSavePass = false;
					autoLoginCheckBox.setIcon(PictureUtil.getPicture("buxuanzhong.png"));
					isAutoLogin = false;
				} else {
					savePassCheckBox.setIcon(PictureUtil.getPicture("xuanzhong.png"));
					isSavePass = true;
				}
			}
		});
		// 保存密码字体事件
		savePassLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isSavePass) {
					savePassCheckBox.setIcon(PictureUtil.getPicture("buxuanzhong.png"));
					isSavePass = false;
					autoLoginCheckBox.setIcon(PictureUtil.getPicture("buxuanzhong.png"));
					isAutoLogin = false;
				} else {
					savePassCheckBox.setIcon(PictureUtil.getPicture("xuanzhong.png"));
					isSavePass = true;
				}
			}
		});
		// 自动登录复选框事件
		autoLoginCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isAutoLogin) {
					autoLoginCheckBox.setIcon(PictureUtil.getPicture("buxuanzhong.png"));
					isAutoLogin = false;
				} else {
					autoLoginCheckBox.setIcon(PictureUtil.getPicture("xuanzhong.png"));
					isAutoLogin = true;
					savePassCheckBox.setIcon(PictureUtil.getPicture("xuanzhong.png"));
					isSavePass = true;
				}
			}
		});
		// 自动登录字体事件
		autoLoginLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isAutoLogin) {
					autoLoginCheckBox.setIcon(PictureUtil.getPicture("buxuanzhong.png"));
					isAutoLogin = false;
				} else {
					autoLoginCheckBox.setIcon(PictureUtil.getPicture("xuanzhong.png"));
					isAutoLogin = true;
					savePassCheckBox.setIcon(PictureUtil.getPicture("xuanzhong.png"));
					isSavePass = true;
				}
			}
		});
		// 登陆按钮事件
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				loginButton.setIcon(PictureUtil.getPicture("login_button.png"));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				loginButton.setIcon(PictureUtil.getPicture("login_active.png"));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				login();
			}
		});
		// 注册账号鼠标事件
		registerLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				registerLabel.setText("\u6ce8\u518c\u8d26\u53f7");
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				registerLabel.setText("<html><u>\u6ce8\u518c\u8d26\u53f7</u><html>");
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (null == client.getRegister()) {					
					RegisterWindow inst = RegisterWindow.getInstance(client);
					client.setRegister(inst);
				} else {
					MyOptionPane.showMessageDialog(client.getLogin(), "窗口重复打开不太好哦！", "友情提示");
					client.getRegister().requestFocus();
				}
			}
		});
		// 找回密码鼠标事件
		findPassLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				findPassLabel.setText("\u627e\u56de\u5bc6\u7801");
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				findPassLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				findPassLabel.setText("<html><u>\u627e\u56de\u5bc6\u7801</u><html>");
			}
		});
	}
	
	// 登录
	private void login() {
		String name = userNameField.getText();
		String pass = String.valueOf(passWordField.getPassword());
		if (StringUtil.isEmpty(name)) {
			MyOptionPane.showMessageDialog(client.getLogin(), "请输入账号！", "友情提示");
			return;
		}
		if (StringUtil.isEmpty(pass)) {
			MyOptionPane.showMessageDialog(client.getLogin(), "请输入密码！", "友情提示");
			return;
		}
		String str = name + Constants.LEFT_SLASH + pass;
		// 登录服务器
		client.sendMsg(new Message(Constants.LOGIN_MSG, str));
	}

}
