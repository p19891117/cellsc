package feiqq.ui.group;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import feiqq.bean.Group;
import feiqq.util.Constants;

public class GroupNode extends DefaultMutableTreeNode {

	public Icon icon;
	public Group group;
	public JLabel picture;
	public JLabel nickName;
	public JPanel groupContent = new JPanel();

	public GroupNode(Icon icon, Group group) {
		super();
		this.icon = icon;
		this.group = group;

		groupContent.setLayout(null);
		groupContent.setBackground(Color.WHITE);
		groupContent.setPreferredSize(new Dimension(300, 25));

		picture = new JLabel();
		groupContent.add(picture);
		picture.setIcon(icon);
		picture.setBounds(6, 5, icon.getIconWidth(), icon.getIconHeight());

		nickName = new JLabel();
		groupContent.add(nickName);
		nickName.setFont(Constants.BASIC_FONT);
		nickName.setText(group.getName());
		nickName.setBounds(40, 0, 238, 28);
	}

	public Component getView() {
		return groupContent;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
