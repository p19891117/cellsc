package feiqq.thread;

import java.awt.TrayIcon;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import feiqq.socket.client.Client;
import feiqq.ui.common.CategoryNode;
import feiqq.ui.friend.FriendNode;
import feiqq.util.PictureUtil;

public class FlashThread extends Thread {
	
	private Client client;
	private String senderName;
	
	public FlashThread(Client client, String senderName) {
		this.client = client;
		this.senderName = senderName;
	}
	
	public void run() {
		try {
			DefaultTreeModel treeModel = client.getBuddyModel();
			FriendNode node = client.buddyNodeMap.get(senderName);
			CategoryNode parentNode = (CategoryNode) node.getParent();
			TreePath path = new TreePath(parentNode.getPath());
			String parentName = parentNode.nickName.getText();
			TrayIcon icon = client.getIcon();
			
			// TODO 这里有点儿别扭，以后记得改，好冗杂
			while (client.msgStatusMap.get(senderName)) {
				// 节点已展开就不再闪烁父节点
				if (client.getBuddyTree().isExpanded(path)) {
					node.picture.setBounds(9, 5, 40, 43);
					icon.setImage(new ImageIcon("").getImage());
					Thread.sleep(600);
					treeModel.reload(node);
					
					node.picture.setBounds(8, 4, 39, 42);
					icon.setImage(PictureUtil.getPicture("qq_icon.png").getImage());
					Thread.sleep(600);
					treeModel.reload(node);
				} else {
					parentNode.nickName.setText("");
					icon.setImage(new ImageIcon("").getImage());
					Thread.sleep(600);
					treeModel.reload(parentNode);
					
					parentNode.nickName.setText(parentName);
					icon.setImage(PictureUtil.getPicture("qq_icon.png").getImage());
					Thread.sleep(600);
					treeModel.reload(parentNode);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
