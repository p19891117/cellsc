package feiqq.ui.group;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import feiqq.ui.common.CategoryNode;
import feiqq.util.PictureUtil;

public class GroupNodeRenderer extends DefaultTreeCellRenderer {
	
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		if (value instanceof GroupNode) {
			return ((GroupNode)value).getView();
		}
		if (value instanceof CategoryNode) {
			if (expanded) {
				((CategoryNode)value).picture.setIcon(PictureUtil.getPicture("arrow_down.png"));
			} else {
				((CategoryNode)value).picture.setIcon(PictureUtil.getPicture("arrow_left.png"));
			}
			return ((CategoryNode)value).getView();
		}
		return this;
	}

}
