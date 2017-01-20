package feiqq.socket.client;

import feiqq.bean.Category;
import feiqq.bean.Message;
import feiqq.bean.User;
import feiqq.ui.common.CategoryNode;
import feiqq.ui.frame.AddFriendWindow;
import feiqq.ui.frame.ChatRoom;
import feiqq.ui.frame.ChatRoomPanel;
import feiqq.ui.frame.LoginWindow;
import feiqq.ui.frame.MainWindow;
import feiqq.ui.frame.RegisterWindow;
import feiqq.ui.friend.FriendNode;
import feiqq.util.Constants;
import feiqq.util.JsonUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.awt.TrayIcon;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Description: 客户端核心，将东西都提到这个类共用，便于管理 <br/>
 * Date: 2014年11月22日 下午11:34:14 <br/>
 * 
 * @author SongFei
 * @version
 * @since JDK 1.7
 * @see
 */
public class Client {

	// 与服务器交互的channel通道
	private Channel channel;

	/** 主窗体 */
	private MainWindow main;
	/** 登陆框 */
	private LoginWindow login;
	/** 注册框 */
	private RegisterWindow register;
	/** 添加好友框 */
	private AddFriendWindow addRriend;
	/** 聊天室 */
	private ChatRoom room;
	/** 系统托盘 */
	private TrayIcon icon;
	/** 好友tree */
	private JTree buddyTree;
	/** 好友treeModel */
	private DefaultTreeModel buddyModel;
	/** 好友treeRoot */
	private DefaultMutableTreeNode buddyRoot;

	/** key：好友名称 value：tab块 */
	public Map<String, ChatRoomPanel> tabMap = new HashMap<String, ChatRoomPanel>();
	/** key：好友名称 value：node节点 */
	public Map<String, FriendNode> buddyNodeMap = new HashMap<String, FriendNode>();
	/** key：分组Id value：node节点 */
	public Map<String, CategoryNode> cateNodeMap = new HashMap<String, CategoryNode>();
	/** key：好友名称 value：消息浏览状态 （用于确定头像的闪动） */
	public Map<String, Boolean> msgStatusMap = new HashMap<String, Boolean>();
	/** key：好友名称 value：消息队列 （便于放到聊天窗中） */
	public Map<String, Queue<Message>> msgQueMap = new HashMap<String, Queue<Message>>();
	
	// 自身信息
	private User user;
	private List<Category> categoryList;
	private List<Map<String, List<User>>> memberList;

	public Client() {
		final ClientHandler clientHandler = new ClientHandler(this);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(new NioEventLoopGroup());
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 2, 0, 2));
					ch.pipeline().addLast(new LengthFieldPrepender(2, false));
					ch.pipeline().addLast(clientHandler);
				}
			});
			ChannelFuture future = bootstrap.connect(
					new InetSocketAddress(Constants.SERVER_IP, Constants.SERVER_PORT)).sync();
			// TODO 这里为什么加上了sync()方法之后，启动client类的时候会被阻塞住，导致后面的发消息都不行
			// future.channel().closeFuture().sync();
			future.channel().closeFuture();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	public void sendMsg(Message message) {
		String msg = JsonUtil.transToJson(message);
		channel.writeAndFlush(ByteBufAllocator.DEFAULT
				.buffer().writeBytes(msg.getBytes()))
				.addListener(new ClientListener());
		System.out.println("发送的消息：" + msg.getBytes().length + msg);
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public MainWindow getMain() {
		return main != null ? main : null;
	}

	public void setMain(MainWindow main) {
		this.main = main;
	}

	public RegisterWindow getRegister() {
		return register != null ? register : null;
	}

	public void setRegister(RegisterWindow register) {
		this.register = register;
	}

	public AddFriendWindow getAddRriend() {
		return addRriend != null ? addRriend : null;
	}

	public void setAddRriend(AddFriendWindow addRriend) {
		this.addRriend = addRriend;
	}

	public LoginWindow getLogin() {
		return login != null ? login : null;
	}

	public void setLogin(LoginWindow login) {
		this.login = login;
	}

	public ChatRoom getRoom() {
		return room != null ? room : null;
	}

	public void setRoom(ChatRoom room) {
		this.room = room;
	}

	public TrayIcon getIcon() {
		return icon;
	}

	public void setIcon(TrayIcon icon) {
		this.icon = icon;
	}

	public JTree getBuddyTree() {
		return buddyTree;
	}

	public void setBuddyTree(JTree buddyTree) {
		this.buddyTree = buddyTree;
	}

	public DefaultTreeModel getBuddyModel() {
		return buddyModel;
	}

	public void setBuddyModel(DefaultTreeModel buddyModel) {
		this.buddyModel = buddyModel;
	}

	public DefaultMutableTreeNode getBuddyRoot() {
		return buddyRoot;
	}

	public void setBuddyRoot(DefaultMutableTreeNode buddyRoot) {
		this.buddyRoot = buddyRoot;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Map<String, List<User>>> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Map<String, List<User>>> memberList) {
		this.memberList = memberList;
	}

}
