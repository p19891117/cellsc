package feiqq.ui.group;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import feiqq.bean.Category;
import feiqq.bean.Group;
import feiqq.ui.common.CategoryNode;
import feiqq.ui.common.MyScrollBarUI;
import feiqq.ui.common.MyTreeUI;
import feiqq.util.PictureUtil;

public class GroupPanel extends JPanel {

	private JTree jTree;
	private JScrollPane jScrollPane;
	
	public GroupPanel() {
		BorderLayout borderLayout = new BorderLayout();
		this.setLayout(borderLayout);
		
		CategoryNode root1 = new CategoryNode(PictureUtil.getPicture("arrow_left.png"), new Category("我的QQ群1"));
		CategoryNode root2 = new CategoryNode(PictureUtil.getPicture("arrow_left.png"), new Category("我的QQ群2"));
		CategoryNode root3 = new CategoryNode(PictureUtil.getPicture("arrow_left.png"), new Category("我的QQ群3"));
		
		GroupNode node1 = new GroupNode(PictureUtil.getPicture("group1.png"), new Group("我们的梦想-101"));
		GroupNode node2 = new GroupNode(PictureUtil.getPicture("group1.png"), new Group("我们的梦想-102"));
		root1.add(node1);
		root1.add(node2);
		
		GroupNode node3 = new GroupNode(PictureUtil.getPicture("group1.png"), new Group("我们的梦想-201"));
		GroupNode node4 = new GroupNode(PictureUtil.getPicture("group1.png"), new Group("我们的梦想-202"));
		root2.add(node3);
		root2.add(node4);
		
		GroupNode node5 = new GroupNode(PictureUtil.getPicture("group1.png"), new Group("我们的梦想-201"));
		GroupNode node6 = new GroupNode(PictureUtil.getPicture("group1.png"), new Group("我们的梦想-202"));
		root3.add(node5);
		root3.add(node6);
		
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		final DefaultTreeModel Root = new DefaultTreeModel(root);
		root.add(root1);
		root.add(root2);
		root.add(root3);
		
		jTree = new JTree(Root);
		jTree.setUI(new MyTreeUI());
		jTree.setCellRenderer(new GroupNodeRenderer());
		jTree.setRootVisible(false);
		jTree.setToggleClickCount(1);
		
		jScrollPane = new JScrollPane();
		jScrollPane.setBorder(null);
		jScrollPane.setViewportView(jTree);
		jScrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.add(jScrollPane, BorderLayout.CENTER);
	}
	
}
