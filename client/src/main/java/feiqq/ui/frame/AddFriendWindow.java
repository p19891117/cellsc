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
import javax.swing.JTextField;

import feiqq.bean.Message;
import feiqq.bean.User;
import feiqq.socket.client.Client;
import feiqq.ui.common.MyOptionPane;
import feiqq.util.Constants;
import feiqq.util.PictureUtil;
import feiqq.util.StringUtil;

public class AddFriendWindow extends JDialog {

	private JPanel contentPanel;
	private JLabel addLabel;
	private JTextField textField;
	private JLabel okButton;
	private JLabel quitButton;
	private JLabel exitButton;
	
	private Point point = new Point();
	private Client client;
	private User self;
	private String cateId;
	
	public static AddFriendWindow getInstance(Client client, String cateId, User self) {
		AddFriendWindow inst = new AddFriendWindow(client, cateId, self);
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);
		return inst;
	}

	public AddFriendWindow(Client client, String cateId, User self) {
		initGUI();
		initListener();
		this.self = self;
		this.client = client;
		this.cateId = cateId;
	}
	
	private void initGUI() {
		setSize(300, 180);
		setUndecorated(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		contentPanel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(PictureUtil.getPicture("back4.jpg").getImage(), 0, 0, null);
				this.setOpaque(false);
			}
		};
		contentPanel.setLayout(null);
		contentPanel.setBorder(Constants.LIGHT_GRAY_BORDER);
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		addLabel = new JLabel("对方账号（昵称）:");
		addLabel.setBounds(7, 55, 114, 35);
		addLabel.setFont(Constants.BASIC_FONT);
		contentPanel.add(addLabel);
		
		textField = new JTextField();
		textField.setBounds(112, 58, 180, 30);
		contentPanel.add(textField);
		
		okButton = new JLabel("添加", JLabel.CENTER);
		contentPanel.add(okButton);
		okButton.setOpaque(false);
		okButton.setBorder(null);
		okButton.setFont(Constants.BASIC_FONT);
		okButton.setBounds(45, 123, 114, 33);
		okButton.setBackground(new Color(240, 245, 240, 60));
		
		quitButton = new JLabel("取消", JLabel.CENTER);
		contentPanel.add(quitButton);
		quitButton.setOpaque(false);
		quitButton.setBorder(null);
		quitButton.setFont(Constants.BASIC_FONT);
		quitButton.setBounds(140, 123, 114, 33);
		quitButton.setBackground(new Color(240, 245, 240, 60));
		
		exitButton = new JLabel();
		exitButton.setBounds(261, 0, 40, 20);
		contentPanel.add(exitButton);
		exitButton.setIcon(PictureUtil.getPicture("close.png"));
	}
	
	private void initListener() {
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
				client.setAddRriend(null);
			}
		});
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				textField.setBorder(Constants.LIGHT_GRAY_BORDER);
			}
			@Override
			public void focusGained(FocusEvent e) {
				textField.setBorder(Constants.ORANGE_BORDER);
			}
		});
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				okButton.setBorder(null);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				okButton.setBorder(Constants.LIGHT_GRAY_BORDER);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				String account = textField.getText();
				if (StringUtil.isEmpty(account)) {
					MyOptionPane.showMessageDialog(client.getAddRriend(), "请输入账号或昵称！", "友情提示");
					return;
				}
				Message message = new Message();
				message.setSenderId(self.getId());
				message.setSenderName(self.getNickName());
				message.setType(Constants.REQUEST_ADD_MSG);
				message.setContent(cateId + Constants.LEFT_SLASH + account);
				client.sendMsg(message);
			}
		});
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				quitButton.setBorder(null);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				quitButton.setBorder(Constants.LIGHT_GRAY_BORDER);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				dispose();
				client.setAddRriend(null);
			}
		});
	}
	
}
