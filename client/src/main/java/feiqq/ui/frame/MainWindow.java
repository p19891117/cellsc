package feiqq.ui.frame;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.alee.laf.tabbedpane.TabStretchType;
import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;

import feiqq.bean.User;
import feiqq.socket.client.Client;
import feiqq.ui.friend.FriendPanel;
import feiqq.ui.group.GroupPanel;
import feiqq.ui.recent.RecentPanel;
import feiqq.util.Constants;
import feiqq.util.PictureUtil;

/**
 * 
 * Description: 主窗体	<br/>  
 * Date: 2014年11月28日 上午11:03:12    <br/>
 * @author   SongFei
 * @version  
 * @since    JDK 1.7
 * @see
 */
// TODO 亮瞎了我的24K钛合金眼啊，frame、window接连败阵，小小的dialog竟然撑起一片天
public class MainWindow extends JDialog {
	/** 主面板 */
	private JPanel content;
	/** 基本信息面板 */
	private JPanel baseInfo;
	/** 好友信息面板 */
	private JPanel userInfo;
	/** 搜索框面板 */
	private JPanel searchInfo;
	
	/** 标记（左上角） */
	private JLabel productInfo;
	/** 头像 */
	private JLabel picture;
	/** 签名 */
	private JLabel signature;
	/** 昵称 */
	private JLabel nickName;
	
	/** 分类面板（联系人、群组、会话） */
	private WebTabbedPane typeInfo;
	
	/** 搜索框 */
	private JTextField searchText;
	/** 搜索按钮 */
	private JLabel searchButton;
	/** 联系人面板 */
	private FriendPanel friendPanel;
	/** 群组面板 */
	private GroupPanel groupPanel;
	/** 会话面板 */
	private RecentPanel recentPanel;
	/** 最小化按钮 */
	private JLabel minButton;
	/** 皮肤按钮 */
	private JLabel skinButton;
	/** 退出按钮 */
	private JLabel exitButton;
	/** 面板默认背景色 */
	private static Color defaultBgColor = Color.WHITE;
	/** 坐标（用于记录鼠标拖拽时，鼠标按下那一刻的坐标） */
	private Point point = new Point();
	
	private User user;
	private Client client;
	private TrayIcon icon;
	private SystemTray tray;
	
	
	public static MainWindow getInstance(Client client) {
		MainWindow inst = new MainWindow(client);
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);
		inst.requestFocus();
		return inst;
	}
	
	public MainWindow(Client client) {
		super();
		// 声明变量接受，而不在构造方法里传参（考虑到界面有可能再次用到对象里面的值）
		this.client = client;
		this.user = client.getUser();
		initGUI();
		initTrayIcon();
		initListener();
	}

	private void initGUI() {
		try {
//			//TODO JWindow不需要这些属性，如此犀利牛叉以前竟然没发现
//			//模拟QQ任务栏无图标而系统托盘有，Jframe搞死搞残搞不出来，这个自带属性，哈哈
			//TODO 高兴的太早，jwindow竟然获得不了系统焦点，我嘞个去
			setSize(300, 649);
			setAlwaysOnTop(true);
			setUndecorated(true);
//			TODO 这个API导致输入中文白屏
//			不知道是不是jdk版本太高的问题，之前用1.6.45的好像没出现过这种问题
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
			getContentPane().add(content, BorderLayout.CENTER);
			
			// 基本信息面板
			baseInfo = new JPanel();
			content.add(baseInfo);
			baseInfo.setLayout(null);
			baseInfo.setOpaque(false);
			baseInfo.setBounds(0, 0, 300, 118);
			baseInfo.setBorder(Constants.GRAY_BORDER);
			
			productInfo = new JLabel();
			baseInfo.add(productInfo);
			productInfo.setBounds(8, 0, 45, 20);
			productInfo.setText("FEIQQ");
		
			picture = new JLabel();
			baseInfo.add(picture);
			picture.setBounds(7, 33, 66, 66);
			picture.setBorder(Constants.GRAY_BORDER);
			picture.setIcon(new ImageIcon(PictureUtil.getPicture("wolffy.jpg")
					.getImage().getScaledInstance(65, 65, Image.SCALE_DEFAULT)));
		
			nickName = new JLabel();
			baseInfo.add(nickName);
			nickName.setFont(Constants.BASIC_FONT2);
			nickName.setText(user.getNickName());
			nickName.setBounds(80, 25, 156, 32);
		
			signature = new JLabel();
			baseInfo.add(signature);
			signature.setFont(Constants.BASIC_FONT);
			signature.setText(user.getSignature());
			signature.setToolTipText(user.getSignature());
			signature.setBounds(80, 59, 156, 32);
		
			skinButton = new JLabel();
			baseInfo.add(skinButton);
			skinButton.setBounds(205, 2, 33, 18);
			skinButton.setIcon(PictureUtil.getPicture("skin.png"));
		
			minButton = new JLabel();
			baseInfo.add(minButton);
			minButton.setBounds(233, 0, 31, 20);
			minButton.setIcon(PictureUtil.getPicture("minimize.png"));
		
			exitButton = new JLabel();
			baseInfo.add(exitButton);
			exitButton.setBounds(261, 0, 39, 20);
			exitButton.setIcon(PictureUtil.getPicture("close.png"));
			
			// 搜索面板
			searchInfo = new JPanel();
			content.add(searchInfo);
			searchInfo.setLayout(null);
			searchInfo.setOpaque(false);
			searchInfo.setBounds(0, 117, 300, 32);
			searchInfo.setBorder(Constants.GRAY_BORDER);
			
			searchText = new JTextField();
			searchText.setText(Constants.SPACE + Constants.SEARCH_TXT);
			searchInfo.add(searchText);
			searchText.setOpaque(false);
			searchText.setFocusable(false);
			searchText.setBounds(1, 1, 253, 30);
			searchText.setBorder(BorderFactory.createEmptyBorder());
		
			searchButton = new JLabel();
			searchInfo.add(searchButton);
			searchButton.setBounds(254, 1, 45, 30);
			searchButton.setOpaque(false);
			searchButton.setBackground(defaultBgColor);
			searchButton.setIcon(PictureUtil.getPicture("search_icon.png"));
			
			// 好友、联系人、会话面板
			userInfo = new JPanel();
			content.add(userInfo);
			userInfo.setLayout(new BorderLayout());
			userInfo.setOpaque(false);
			userInfo.setBounds(0, 148, 300, 500);
			userInfo.setBackground(defaultBgColor);
			
			// 类型面板（好友、联系人、会话）
			typeInfo = new WebTabbedPane();
			userInfo.add(typeInfo, BorderLayout.CENTER);
			typeInfo.setOpaque(false);
			typeInfo.setTabbedPaneStyle(TabbedPaneStyle.attached);//不高亮边框
			typeInfo.setTabStretchType(TabStretchType.always);//适应宽度
			typeInfo.setTopBg(new Color(240, 240, 240, 60));
	        typeInfo.setBottomBg(new Color(255, 255, 255, 160));
	        typeInfo.setSelectedTopBg(new Color(240, 240, 255, 50));
	        typeInfo.setSelectedBottomBg(new Color(240, 240, 255, 50));
	        typeInfo.setBackground(new Color(255, 255, 255, 200));
			typeInfo.setBorder(Constants.GRAY_BORDER);
			
			// 好友列表
			friendPanel = new FriendPanel(client);
			typeInfo.addTab(null, new ImageIcon(PictureUtil.getPicture("tab_boddy.png")
					.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT)), friendPanel);

			// 群组列表
			groupPanel = new GroupPanel();
			typeInfo.addTab(null, new ImageIcon(PictureUtil.getPicture("tab_group.png")
					.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT)), groupPanel);

			// 会话列表（最近联系人）
			recentPanel = new RecentPanel();
			typeInfo.addTab(null, new ImageIcon(PictureUtil.getPicture("tab_recent.png")
					.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT)), recentPanel);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void initTrayIcon() {
		if (SystemTray.isSupported()) {
			try {
				tray = SystemTray.getSystemTray();
				icon = new TrayIcon(PictureUtil.getPicture("/qq_icon.png").getImage(), user.getNickName());
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
		// 头像区域事件（边框变色）
		picture.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				picture.setBorder(Constants.LIGHT_GRAY_BORDER);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				picture.setBorder(Constants.ORANGE_BORDER);
			}
		});
		// 换肤按钮事件
		skinButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				skinButton.setIcon(PictureUtil.getPicture("skin.png"));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				skinButton.setIcon(PictureUtil.getPicture("skin_active.png"));
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
		// 搜索框鼠标事件
		searchText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (Constants.SEARCH_TXT.equals(searchText.getText().trim())) {
					searchText.setOpaque(true);
					searchText.setFocusable(true);
					searchText.requestFocus();
					searchText.setText("");
					searchText.updateUI();
					
					searchButton.setOpaque(true);
					searchButton.updateUI();
				}
			}
		});
		// 搜索框焦点事件
		searchText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				searchText.setOpaque(false);
				searchText.setFocusable(false);
				searchText.setText(Constants.SPACE + Constants.SEARCH_TXT);
				searchText.updateUI();
				
				searchButton.setOpaque(false);
				searchButton.updateUI();
			}
		});
	}
	
}
