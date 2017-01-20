package feiqq.ui.recent;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import feiqq.bean.User;
import feiqq.ui.common.MyScrollBarUI;
import feiqq.ui.common.MyTreeUI;
import feiqq.util.PictureUtil;

public class RecentPanel extends JPanel {

	private JTree jTree;
	private JScrollPane jScrollPane;
	
	public RecentPanel() {
		BorderLayout borderLayout = new BorderLayout();
		this.setLayout(borderLayout);
		
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		final DefaultTreeModel Root = new DefaultTreeModel(root);
		
		RecentNode node0 = new RecentNode(PictureUtil.getPicture("avatar2.png"), new User("好友-101", "啊啊啊"));
		RecentNode node1 = new RecentNode(PictureUtil.getPicture("avatar2.png"), new User("好友-102", "啊啊啊"));
		RecentNode node2 = new RecentNode(PictureUtil.getPicture("avatar2.png"), new User("好友-103", "啊啊啊"));
		RecentNode node3 = new RecentNode(PictureUtil.getPicture("avatar2.png"), new User("好友-104", "啊啊啊"));
		RecentNode node4 = new RecentNode(PictureUtil.getPicture("avatar2.png"), new User("好友-105", "啊啊啊"));
		RecentNode node5 = new RecentNode(PictureUtil.getPicture("avatar2.png"), new User("好友-201", "啊啊啊"));
		RecentNode node6 = new RecentNode(PictureUtil.getPicture("avatar2.png"), new User("好友-202", "啊啊啊"));
		RecentNode node7 = new RecentNode(PictureUtil.getPicture("avatar2.png"), new User("好友-203", "啊啊啊"));
		RecentNode node8 = new RecentNode(PictureUtil.getPicture("avatar2.png"), new User("好友-204", "啊啊啊"));
		RecentNode node9 = new RecentNode(PictureUtil.getPicture("avatar2.png"), new User("好友-205", "啊啊啊"));
		root.add(node0);
		root.add(node1);
		root.add(node2);
		root.add(node3);
		root.add(node4);
		root.add(node5);
		root.add(node6);
		root.add(node7);
		root.add(node8);
		root.add(node9);
		
		jTree = new JTree(Root);
		jTree.setUI(new MyTreeUI()); // 自定义UI
		jTree.setCellRenderer(new RecentNodeRenderer());// 自定义节点渲染器
		jTree.setRootVisible(false);// 隐藏根节点
		
		jScrollPane = new JScrollPane();
		jScrollPane.setBorder(null);
		jScrollPane.setViewportView(jTree);
		jScrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.add(jScrollPane, BorderLayout.CENTER);
	}
	
}
