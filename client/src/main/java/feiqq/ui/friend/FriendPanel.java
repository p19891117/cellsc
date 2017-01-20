package feiqq.ui.friend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import feiqq.bean.Category;
import feiqq.bean.Message;
import feiqq.bean.User;
import feiqq.socket.client.Client;
import feiqq.ui.common.CategoryNode;
import feiqq.ui.common.Emoticon;
import feiqq.ui.common.MyOptionPane;
import feiqq.ui.common.MyScrollBarUI;
import feiqq.ui.common.MyTabComponent;
import feiqq.ui.common.MyTreeUI;
import feiqq.ui.frame.AddFriendWindow;
import feiqq.ui.frame.ChatRoom;
import feiqq.ui.frame.ChatRoomPanel;
import feiqq.util.Constants;
import feiqq.util.PictureUtil;
import feiqq.util.StringUtil;

public class FriendPanel extends JPanel {

	private JTree jTree;
	private DefaultTreeModel model;
	private DefaultMutableTreeNode root;
	private JScrollPane jScrollPane;
	private JTextField textField;
	private static Color inColor = new Color(254, 224, 109); 
	private static Color selectColor = new Color(249, 184, 87);
	
	private Client selfClient;
	
	
	public FriendPanel(Client client) {
		this.selfClient = client;
		
		setLayout(new BorderLayout());
		
		root = new DefaultMutableTreeNode();
		model = new DefaultTreeModel(root);
		selfClient.setBuddyRoot(root);// 放到client核心类，添加分组用到
		selfClient.setBuddyModel(model);// 放到client核心类，刷新好友UI用到
		
		// 加载数据
		loadJTree();
		
		jTree = new JTree(model);
		jTree.setUI(new MyTreeUI()); // 自定义UI
		jTree.setCellRenderer(new FriendNodeRenderer());// 自定义节点渲染器
		jTree.setRootVisible(false);// 隐藏根节点
		jTree.setToggleClickCount(1);// 点击次数
		jTree.setInvokesStopCellEditing(true);// 修改节点文字之后生效
		selfClient.setBuddyTree(jTree); // 放到client核心类，方便处理
		
		jScrollPane = new JScrollPane();
		jScrollPane.setBorder(null);
		jScrollPane.setViewportView(jTree);
		jScrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		// 屏蔽横向滚动条
		jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(297, 23));
		textField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		jTree.setCellEditor(new DefaultCellEditor(textField));
		
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					TreePath path = jTree.getSelectionPath();
					Object object = path.getLastPathComponent();
					if (jTree.isEditable()) {
						if (!"".equals(textField.getText())) {
							// 将更改过后的数据放到数据库
							Message msg = new Message();
							msg.setType(Constants.EDIT_USER_CATE_MSG);
							CategoryNode cate = (CategoryNode) object;
							msg.setContent(cate.category.getId() + Constants.LEFT_SLASH + textField.getText());
							selfClient.sendMsg(msg);
							cate.nickName.setText(textField.getText());
							cate.category.setName(textField.getText());
							model.reload(cate);
						}
						jTree.stopEditing();
						jTree.setEditable(false);
					}
				}
			}
		});
		
		jTree.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				TreePath path = jTree.getPathForLocation(e.getX(), e.getY());
				if (null != path) {
					Object object = path.getLastPathComponent();
					if (object instanceof FriendNode) {
						for (int i = 0; i < root.getChildCount(); i++) {
							Object category = root.getChildAt(i);
							((CategoryNode)category).categoryContent.setBackground(Color.WHITE);
							model.reload((CategoryNode)category);
							for (int j = 0; j < root.getChildAt(i).getChildCount(); j++) {
								Object friend = root.getChildAt(i).getChildAt(j);
								if (((FriendNode)friend).userContent.getBackground() != selectColor) {
									if (friend == ((FriendNode)object)) {
										((FriendNode)friend).userContent.setBackground(inColor);
									} else {
										((FriendNode)friend).userContent.setBackground(Color.WHITE);
									}
									model.reload(((FriendNode)friend));
								}
							}
						}
					}
					if (object instanceof CategoryNode) {
						for (int i = 0; i < root.getChildCount(); i++) {
							Object category = root.getChildAt(i);
							if (category == ((CategoryNode)object)) {
								((CategoryNode)category).categoryContent.setBackground(inColor);
							} else {
								((CategoryNode)category).categoryContent.setBackground(Color.WHITE);
							}
							model.reload((CategoryNode)category);
							for (int j = 0; j < root.getChildAt(i).getChildCount(); j++) {
								Object friend = root.getChildAt(i).getChildAt(j);
								if (((FriendNode)friend).userContent.getBackground() != selectColor) {
									((FriendNode)friend).userContent.setBackground(Color.WHITE);
									model.reload(((FriendNode)friend));
								}
							}
						}
					}
				}
			}
		});
		
		jTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				for (int i = 0; i < root.getChildCount(); i++) {
					Object category = root.getChildAt(i);
					((CategoryNode)category).categoryContent.setBackground(Color.WHITE);
					model.reload(((CategoryNode)category));
					for (int j = 0; j < root.getChildAt(i).getChildCount(); j++) {
						Object friend = root.getChildAt(i).getChildAt(j);
						((FriendNode)friend).userContent.setBackground(Color.WHITE);
						model.reload(((FriendNode)friend));
					}
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// 监听鼠标左键
				if (e.getButton() == MouseEvent.BUTTON1) {
					TreePath path = jTree.getSelectionPath();
					if (null == path) {
						return;
					}
					// path中的node节点（path不为空，这里基本不会空）
					Object object = path.getLastPathComponent();
					if (e.getClickCount() == 1) {
						if (jTree.isEditable()) {
							if (!"".equals(textField.getText())) {
							// 将更改过后的数据放到数据库
							Message msg = new Message();
							msg.setType(Constants.EDIT_USER_CATE_MSG);
							CategoryNode cate = (CategoryNode) object;
							// 如何得到修改后的文字呢
							msg.setContent(cate.category.getId() + Constants.LEFT_SLASH + textField.getText());
							selfClient.sendMsg(msg);
								cate.nickName.setText(textField.getText());
								cate.category.setName(textField.getText());
								model.reload(cate);
							}
							jTree.stopEditing();
							jTree.setEditable(false);
						}
					}
					if (e.getClickCount() == 2) {
						// 好友node节点（区分群组）
						if (object instanceof FriendNode) {
							// 从根节点开始获取所有节点（第一层全部是分组）
							for (int i = 0; i < root.getChildCount(); i++) {
								// 鼠标滑入好友node节点时，需要将分组上面的颜色恢复
								Object category = root.getChildAt(i);
								((CategoryNode)category).categoryContent.setBackground(Color.WHITE);
								model.reload(((CategoryNode)category));// 刷新UI
								// 这一层全部是好友node节点
								for (int j = 0; j < root.getChildAt(i).getChildCount(); j++) {
									Object friend = root.getChildAt(i).getChildAt(j);
									// 不是鼠标选中的那个
									if (friend != ((FriendNode)object)) {
										((FriendNode)friend).userContent.setBackground(Color.WHITE);
									} else {
										((FriendNode)object).userContent.setBackground(selectColor);
										// 开启聊天窗口
										Message message = null;
										User user = ((FriendNode)object).friend;
										// TODO 代码冗余，记得来改
										ChatRoom room = selfClient.getRoom() == null ? 
												ChatRoom.getInstance(selfClient) : selfClient.getRoom();
										// 相应好友的panel没打开
										if (!selfClient.tabMap.containsKey(user.getNickName())) {
											room.setTitle(selfClient.getUser().getNickName() + " - " + user.getNickName());
											room.titleLabel.setText(selfClient.getUser().getNickName() + " - " + user.getNickName());
											ChatRoomPanel pane = new ChatRoomPanel(selfClient, selfClient.getUser(), user);
											room.tabbedPane.addTab(user.getNickName(), null, pane, user.getNickName());
											// 重绘过的tab页签
											room.tabbedPane.setTabComponentAt(room.tabbedPane.indexOfTab(user.getNickName()), 
													new MyTabComponent(selfClient.getUser().getNickName(), user.getNickName(), room, selfClient));
											int index = room.tabbedPane.indexOfTab(user.getNickName());
											room.tabbedPane.setSelectedIndex(index);
											// 将队列里面的消息显示在面板上
											if (selfClient.msgQueMap.size() > 0) {
												try {
													while ((message = selfClient.msgQueMap.get(user.getNickName()).poll()) != null) {
														StyledDocument doc = pane.historyTextPane.getStyledDocument();
														// 名称、日期
														SimpleAttributeSet nameSet = getAttributeSet(true, null);
														doc.insertString(doc.getLength(), StringUtil.createSenderInfo(message.getSenderName()), nameSet);
														SimpleAttributeSet contentSet = getAttributeSet(false, message);
														// 缩进
														StyleConstants.setLeftIndent(contentSet, 10);
														// 此处开始缩进
														doc.setParagraphAttributes(doc.getLength(), doc.getLength(), contentSet, true);
														// 正文
														// 文字或者图文混合
														if (!StringUtil.isEmpty(message.getContent())) {
															// 记录下这行消息插入的光标在哪里
															// 将光标放置到消息的最后
															pane.position = doc.getLength();
															doc.insertString(doc.getLength(), message.getContent(), contentSet);
															if (!StringUtil.isEmpty(message.getImageMark()) && message.getImageMark().split("/").length > 0) {
																for (String str : message.getImageMark().split("/")) {
																	int imgIndex = Integer.valueOf(str.substring(str.indexOf("|")+1));// 图片的位置（下标）
																	pane.historyTextPane.setCaretPosition(pane.position+imgIndex);// 光标
																	String mark = str.substring(str.indexOf(")")+1, str.indexOf("|"));
																	String fileName = "/feiqq/resource/image/face/" + mark + ".gif";
																	pane.historyTextPane.insertIcon(new ImageIcon(Emoticon.class.getResource(fileName)));
																}
															}
														} else {// 文字为空，说明发送的全部是图片
															for (String str : message.getImageMark().split("/")) {
																// 此处要插入图片
																pane.historyTextPane.setCaretPosition(doc.getLength());// 光标
																String mark = str.substring(str.indexOf(")")+1, str.indexOf("|"));
																String fileName = "/feiqq/resource/image/face/" + mark + ".gif";
																pane.historyTextPane.insertIcon(new ImageIcon(Emoticon.class.getResource(fileName)));
															}
														}
														// 换行
														doc.insertString(doc.getLength(), "\n", contentSet);
														// 将缩进还原回来
														StyleConstants.setLeftIndent(contentSet, 0f);
														doc.setParagraphAttributes(doc.getLength(), doc.getLength(), contentSet, true);
													}
												} catch (BadLocationException e1) {
													e1.printStackTrace();
												}
											}
											// 将room信息返回
											selfClient.setRoom(room);
											selfClient.tabMap.put(user.getNickName(), pane);
											// 告知client，我已接受到相应好友消息
											selfClient.msgStatusMap.put(user.getNickName(), false);
//											// 告知client，下次来消息了继续闪烁
//											selfClient.threadMap.put(user.getName(), false);
										} else {
											room.setTitle(selfClient.getUser().getNickName() + " - " + user.getNickName());
											room.titleLabel.setText(selfClient.getUser().getNickName() + " - " + user.getNickName());
											int index = room.tabbedPane.indexOfTab(user.getNickName());
											room.tabbedPane.setSelectedIndex(index);
											ChatRoomPanel pane = (ChatRoomPanel) room.tabbedPane.getComponentAt(index);
											// 将队列里面的消息显示在面板上
											if (selfClient.msgQueMap.size() > 0) {
												try {
													while ((message = selfClient.msgQueMap.get(user.getNickName()).poll()) != null) {
														StyledDocument doc = pane.historyTextPane.getStyledDocument();
														// 名称、日期
														SimpleAttributeSet nameSet = getAttributeSet(true, null);
														doc.insertString(doc.getLength(), StringUtil.createSenderInfo(message.getSenderName()), nameSet);
														SimpleAttributeSet contentSet = getAttributeSet(false, message);
														// 缩进
														StyleConstants.setLeftIndent(contentSet, 10);
														// 此处开始缩进
														doc.setParagraphAttributes(doc.getLength(), doc.getLength(), contentSet, true);
														// 正文
														// 文字或者图文混合
														if (!StringUtil.isEmpty(message.getContent())) {
															// 记录下这行消息插入的光标在哪里
															// 将光标放置到消息的最后
															pane.position = doc.getLength();
															doc.insertString(doc.getLength(), message.getContent(), contentSet);
															if (!StringUtil.isEmpty(message.getImageMark()) && message.getImageMark().split("/").length > 0) {
																for (String str : message.getImageMark().split("/")) {
																	int imgIndex = Integer.valueOf(str.substring(str.indexOf("|")+1));// 图片的位置（下标）
																	pane.historyTextPane.setCaretPosition(pane.position+imgIndex);// 光标
																	String mark = str.substring(str.indexOf(")")+1, str.indexOf("|"));
																	String fileName = "/feiqq/resource/image/face/" + mark + ".gif";
																	pane.historyTextPane.insertIcon(new ImageIcon(Emoticon.class.getResource(fileName)));
																}
															}
														} else {// 文字为空，说明发送的全部是图片
															for (String str : message.getImageMark().split("/")) {
																// 此处要插入图片
																pane.historyTextPane.setCaretPosition(doc.getLength());// 光标
																String mark = str.substring(str.indexOf(")")+1, str.indexOf("|"));
																String fileName = "/feiqq/resource/image/face/" + mark + ".gif";
																pane.historyTextPane.insertIcon(new ImageIcon(Emoticon.class.getResource(fileName)));
															}
														}
														// 换行
														doc.insertString(doc.getLength(), "\n", contentSet);
														// 将缩进还原回来
														StyleConstants.setLeftIndent(contentSet, 0f);
														doc.setParagraphAttributes(doc.getLength(), doc.getLength(), contentSet, true);
													}
												} catch (BadLocationException e1) {
													e1.printStackTrace();
												}
											}
										}
									}
									model.reload(((FriendNode)friend));
								}
							}
						}
					}
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					TreePath path = jTree.getPathForLocation(e.getX(), e.getY());
					if (null != path) {
						// path中的node节点（path不为空，这里基本不会空）
						final Object object = path.getLastPathComponent();
						if (object instanceof CategoryNode) {
							JPopupMenu pm = new JPopupMenu();
							pm.setBackground(Color.WHITE);
							pm.setBorder(Constants.LIGHT_GRAY_BORDER);
							JMenuItem mit0 = new JMenuItem("添加分组");
							mit0.setOpaque(false);
							mit0.setFont(Constants.BASIC_FONT);
							JMenuItem mit1 = new JMenuItem("删除分组");
							mit1.setOpaque(false);
							mit1.setFont(Constants.BASIC_FONT);
							JMenuItem mit2 = new JMenuItem("添加好友");
							mit2.setOpaque(false);
							mit2.setFont(Constants.BASIC_FONT);
							JMenuItem mit3 = new JMenuItem("更换名称");
							mit3.setOpaque(false);
							mit3.setFont(Constants.BASIC_FONT);
							// 添加分组
							mit0.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									Message msg = new Message();
									msg.setType(Constants.ADD_USER_CATE_MSG);
									msg.setSenderId(selfClient.getUser().getId());
									// 暂时只处理好友列表
									// 一般来说这个地方报错的可能性很小
									msg.setContent(Constants.NONAME_CATE);
									selfClient.sendMsg(msg);
								}
							});
							// 删除分组
							mit1.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									Category cate = ((CategoryNode)object).category;
									if (cate.getName().equals(Constants.DEFAULT_CATE)) {
										MyOptionPane.showMessageDialog(selfClient.getMain(), "默认分组不允许删除！", "友情提示");
										return;
									}
									int res = MyOptionPane.showConfirmDialog(selfClient.getMain(), 
											"确定or取消？", "删除分组之后，分组下面的成员也会被删掉，也会将您从对方的好友列表里删除", "确定", "取消");
									if (res == Constants.YES_OPTION) {
										Message msg = new Message();
										msg.setType(Constants.DELETE_USER_CATE_MSG);
										msg.setSenderId(selfClient.getUser().getId());
										msg.setSenderName(selfClient.getUser().getNickName());
										msg.setContent(cate.getId() + Constants.LEFT_SLASH + cate.getName());
										selfClient.sendMsg(msg);
									}
									if (res == Constants.NO_OPTION) {
										return;
									}
								}
							});
							// 添加好友
							mit2.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									if (null == selfClient.getAddRriend()) {
										AddFriendWindow inst = AddFriendWindow.getInstance(selfClient, ((CategoryNode)object).category.getId(), selfClient.getUser());
										selfClient.setAddRriend(inst);
									} else {
										MyOptionPane.showMessageDialog(selfClient.getAddRriend(), "窗口重复打开不太好！", "友情提示");
										selfClient.getAddRriend().requestFocus();
									}
								}
							});
							// 重命名
							mit3.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									CategoryNode cateNode = (CategoryNode) object;
									if (cateNode.category.getName().equals(Constants.DEFAULT_CATE)) {
										MyOptionPane.showMessageDialog(selfClient.getMain(), "默认分组不允许重命名！", "友情提示");
										return;
									}
									jTree.setEditable(true);
									jTree.startEditingAtPath(new TreePath(cateNode.getPath()));
								}
							});
							pm.add(mit0);
							pm.add(mit1);
							pm.add(mit2);
							pm.add(mit3);
							pm.show(jTree, e.getX(), e.getY());
						}
						if (object instanceof FriendNode) {
							JPopupMenu pm = new JPopupMenu();
							pm.setBackground(Color.WHITE);
							pm.setBorder(Constants.LIGHT_GRAY_BORDER);
							JMenuItem mit1 = new JMenuItem("删除好友");
							mit1.setOpaque(false);
							mit1.setFont(Constants.BASIC_FONT);
							JMenuItem mit2 = new JMenuItem("测试一下");
							mit2.setOpaque(false);
							mit2.setFont(Constants.BASIC_FONT);
							JMenuItem mit3 = new JMenuItem("测试一下");
							mit3.setOpaque(false);
							mit3.setFont(Constants.BASIC_FONT);
							JMenuItem mit4 = new JMenuItem("测试一下");
							mit4.setOpaque(false);
							mit4.setFont(Constants.BASIC_FONT);
							// 删除好友
							mit1.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									int res = MyOptionPane.showConfirmDialog(selfClient.getMain(), 
											"确定or取消？", "删除好友之后，也会将您从对方的好友列表里删除", "确定", "取消");
									if (res == Constants.YES_OPTION) {
										FriendNode friendNode = (FriendNode) object;
										CategoryNode cateNode = (CategoryNode) friendNode.getParent();
										Message msg = new Message();
										msg.setType(Constants.DELETE_USER_MEMBER_MSG);
										msg.setSenderId(selfClient.getUser().getId());
										msg.setSenderName(selfClient.getUser().getNickName());
										msg.setReceiverId(friendNode.friend.getId());
										msg.setContent(cateNode.category.getId() 
												+ Constants.LEFT_SLASH + friendNode.friend.getId());
										selfClient.sendMsg(msg);
									}
									if (res == Constants.NO_OPTION) {
										return;
									}
								}
							});
							pm.add(mit1);
							pm.add(mit2);
							pm.add(mit3);
							pm.add(mit4);
							pm.show(jTree, e.getX(), e.getY());
						}
					}
				}
			}
		});
		this.add(jScrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * 加载tree数据 </br> 
	 * 第一次来的时候需要从根节点加载 </br>
	 * 添加了新的分组也要从根节点加载 </br>
	 * 分组里添加了用户就无需从根节点加载 </br>
	 */
	public void loadJTree() {
		for (Category category : selfClient.getCategoryList()) {
			CategoryNode cate = new CategoryNode(PictureUtil.getPicture("arrow_left.png"), category);
			for (Map<String, List<User>> map : selfClient.getMemberList()) {
				List<User> list = map.get(category.getId());
				if (null != list && list.size() > 0) {
					for (User friend : list) {
						FriendNode buddy = new FriendNode(PictureUtil.getPicture("avatar2.png"), friend);
						cate.add(buddy);
						// 更新client中好友节点的map，放到client中，为了方便统一调用
						selfClient.buddyNodeMap.put(friend.getNickName(), buddy);
					}
				}
			}
			root.add(cate);
			// 更新client中好友分组的map，放到client中，为了方便统一调用
			selfClient.cateNodeMap.put(cate.category.getId(), cate);
		}
	}
	
	private SimpleAttributeSet getAttributeSet(boolean isDefault, Message message) {
		SimpleAttributeSet set = new SimpleAttributeSet();
		if (isDefault) {
			StyleConstants.setBold(set, false);
			StyleConstants.setItalic(set, false);
			StyleConstants.setFontSize(set, 15);
			StyleConstants.setFontFamily(set, "宋体");
			StyleConstants.setForeground(set, Color.RED);
		} else {
			// 字体名称
			StyleConstants.setFontFamily(set, message.getFamily());
			// 字号
			StyleConstants.setFontSize(set, message.getSize());
			// 样式
			int styleIndex = message.getStyle();
			if (styleIndex == 0) {// 常规
				StyleConstants.setBold(set, false);
				StyleConstants.setItalic(set, false);
			}
			if (styleIndex == 1) {// 斜体
				StyleConstants.setBold(set, false);
				StyleConstants.setItalic(set, true);
			}
			if (styleIndex == 2) {// 粗体
				StyleConstants.setBold(set, true);
				StyleConstants.setItalic(set, false);
			}
			if (styleIndex == 3) {// 粗斜体
				StyleConstants.setBold(set, true);
				StyleConstants.setItalic(set, true);
			}
			// 字体颜色
			int foreIndex = message.getFore();
			if (foreIndex == 0) {// 黑色
				StyleConstants.setForeground(set, Color.BLACK);
			}
			if (foreIndex == 1) {// 橙色
				StyleConstants.setForeground(set, Color.ORANGE);
			}
			if (foreIndex == 2) {// 黄色
				StyleConstants.setForeground(set, Color.YELLOW);
			}
			if (foreIndex == 3) {// 绿色
				StyleConstants.setForeground(set, Color.GREEN);
			}
			// 背景颜色
			int backIndex = message.getBack();
			if (backIndex == 0) {// 白色
				StyleConstants.setBackground(set, Color.WHITE);
			}
			if (backIndex == 1) {// 灰色
				StyleConstants.setBackground(set, new Color(200, 200, 200));
			}
			if (backIndex == 2) {// 淡红
				StyleConstants.setBackground(set, new Color(255, 200, 200));
			}
			if (backIndex == 3) {// 淡蓝
				StyleConstants.setBackground(set, new Color(200, 200, 255));
			}
			if (backIndex == 4) {// 淡黄
				StyleConstants.setBackground(set, new Color(255, 255, 200));
			}
			if (backIndex == 5) {// 淡绿
				StyleConstants.setBackground(set, new Color(200, 255, 200));
			}
		}
		return set;
	}
	
}
