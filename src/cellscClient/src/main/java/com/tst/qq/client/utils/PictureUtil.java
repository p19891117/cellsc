package com.tst.qq.client.utils;

import javax.swing.ImageIcon;

public class PictureUtil {

	public static ImageIcon getPicture(String name) {
		ImageIcon icon = new ImageIcon(PictureUtil.class.getClassLoader()
				.getResource("feiqq/resource/image/" + name));
		return icon;
	}

}
