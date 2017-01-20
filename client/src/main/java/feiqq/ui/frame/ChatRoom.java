package feiqq.ui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;

import feiqq.socket.client.Client;
import feiqq.util.Constants;
import feiqq.util.PictureUtil;

/**
 * 
 * Description: 聊天室	<br/>  
 * Date: 2014年11月26日 下午5:35:10    <br/>
 * @author   SongFei
 * @version  
 * @since    JDK 1.7
 * @see
 */
public class ChatRoom extends JFrame {
	
	/** 主面板  */
	private JPanel contentPane;
	/** 最小化按钮  */
	private JLabel minButton;
	/** 最大化按钮  */
	private JLabel exitButton;
	/** 提示信息（与***聊天中） */
	public JLabel titleLabel;
	/** 下方聊天窗体 */
	private JPanel downPanel;
	/** 窗体面板（可合并）  */
	public WebTabbedPane tabbedPane;
	/** 坐标（用于记录鼠标拖拽时，鼠标按下那一刻的坐标） */
	private Point point = new Point();
	
	private Client client;

	public static ChatRoom getInstance(Client client) {
		ChatRoom frame = new ChatRoom(client);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();
		return frame;
	}
	
	public ChatRoom(Client client) {
		super();
		initGUI();
		initListener();
		// 关闭窗口需要将所有页签清空
		this.client = client;
	}
	
	private void initGUI() {
		try {
			setSize(660, 560);
			setUndecorated(true);
//			TODO 这个API导致输入中文白屏
//			AWTUtilities.setWindowOpaque(this, false);
//			TODO 这个地方注意不要用exit，不然在任务栏关闭room的时候就会导致进程退出
//			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setIconImage(PictureUtil.getPicture("avatar2.png").getImage());
			
			// 主面板
			contentPane = new JPanel() {
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(PictureUtil.getPicture("back5.jpg").getImage(), 0, 0, null);
					this.setOpaque(false);
				}
			};
			contentPane.setLayout(null);
			contentPane.setBorder(Constants.LIGHT_GRAY_BORDER);
			setContentPane(contentPane);
			
			titleLabel = new JLabel();
			titleLabel.setFont(Constants.BASIC_FONT);
			titleLabel.setBounds(10, 0, 619, 30);
			contentPane.add(titleLabel);
			
			// 聊天窗口合并面板
			downPanel = new JPanel();
			contentPane.add(downPanel);
			downPanel.setOpaque(false);
			downPanel.setBounds(1, 40, 658, 519);
			downPanel.setLayout(new BorderLayout());
			
			minButton = new JLabel();
			contentPane.add(minButton);
			minButton.setBounds(593, 0, 31, 20);
			minButton.setIcon(PictureUtil.getPicture("minimize.png"));
			
			exitButton = new JLabel();
			contentPane.add(exitButton);
			exitButton.setBounds(621, 0, 39, 20);
			exitButton.setIcon(PictureUtil.getPicture("close.png"));

			tabbedPane = new WebTabbedPane();
			downPanel.add(tabbedPane, BorderLayout.CENTER);
			tabbedPane.setOpaque(false);
			tabbedPane.setTabbedPaneStyle(TabbedPaneStyle.attached);//不高亮边框
//	        tabbedPane.setTabStretchType(TabStretchType.always);//适应宽度
	        tabbedPane.setTopBg(new Color(240, 240, 240, 60));
	        tabbedPane.setBottomBg(new Color(255, 255, 255, 160));
	        tabbedPane.setSelectedTopBg(new Color(240, 240, 255, 50));
	        tabbedPane.setSelectedBottomBg(new Color(240, 240, 255, 50));
	        tabbedPane.setBackground(new Color(255, 255, 255, 200));
//			TODO 这个地方为什么自己打开就没问题，消息监听强行打开窗口就报错导致线程阻塞
//			tabbedPane.setUI(new MyTabbedPaneUI(tabbedPane));
		} catch (Exception e) {
			// TODO: handle exception
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
		// 任务栏右键关闭
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				// TODO 清空记录窗体数据
				client.setRoom(null);
				client.tabMap.clear();
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
				setExtendedState(JFrame.ICONIFIED);
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
				// TODO 清空记录窗体数据
				client.setRoom(null);
				client.tabMap.clear();
			}
		});
	}
	
}
