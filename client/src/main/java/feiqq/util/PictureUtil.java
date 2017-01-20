package feiqq.util;

import javax.swing.ImageIcon;

public class PictureUtil {

	public static ImageIcon getPicture(String name) {
		ImageIcon icon = new ImageIcon(PictureUtil.class.getClassLoader()
				.getResource("feiqq/resource/image/" + name));
		return icon;
	}

}
