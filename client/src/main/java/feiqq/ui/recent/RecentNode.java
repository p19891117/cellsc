package feiqq.ui.recent;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import feiqq.bean.User;

public class RecentNode extends DefaultMutableTreeNode {

	public Icon icon;
	public User friend;
	public JLabel picture;
	public JLabel nickName;
	public JLabel descript;
	public JPanel userContent = new JPanel();

	public RecentNode(Icon icon, User friend) {
		super();
		this.icon = icon;
		this.friend = friend;

		userContent.setLayout(null);
		userContent.setBackground(Color.WHITE);
		userContent.setPreferredSize(new Dimension(300, 50));

		picture = new JLabel();
		userContent.add(picture);
		picture.setIcon(icon);
		picture.setBounds(8, 4, 39, 42);

		nickName = new JLabel();
		userContent.add(nickName);
		nickName.setText(friend.getNickName());
		nickName.setBounds(59, 5, 132, 19);

		descript = new JLabel();
		userContent.add(descript);
		descript.setText(friend.getSignature());
		descript.setBounds(59, 28, 132, 17);
	}

	public Component getView() {
		return userContent;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public User getFriend() {
		return friend;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}

}
