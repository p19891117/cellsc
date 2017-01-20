package feiqq.ui.common;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;

import org.sexydock.tabs.jhrome.JhromeTabBorderAttributes;
import org.sexydock.tabs.jhrome.JhromeTabbedPaneUI;

/**
 * 漂亮倒是漂亮，可惜用不了，哎。。。
 * @author Administrator
 *
 */
public class MyTabbedPaneUI extends JhromeTabbedPaneUI {

	public MyTabbedPaneUI(JTabbedPane tabbedPane) {
		JhromeTabBorderAttributes.SELECTED_BORDER.topColor = new Color(255, 255, 255, 120);
        JhromeTabBorderAttributes.SELECTED_BORDER.bottomColor = new Color(255, 255, 255, 0);
        JhromeTabBorderAttributes.SELECTED_BORDER.shadowColor = new Color( 55 , 55 , 55 , 50 );
        JhromeTabBorderAttributes.SELECTED_BORDER.outlineColor = new Color( 55 , 55 , 55 , 100);
        
        JhromeTabBorderAttributes.UNSELECTED_BORDER.topColor = new Color(255, 255, 255, 40);
        JhromeTabBorderAttributes.UNSELECTED_BORDER.bottomColor = new Color(255, 255, 255, 0);
        JhromeTabBorderAttributes.UNSELECTED_BORDER.shadowColor = new Color( 55 , 55 , 55 , 20 );
        JhromeTabBorderAttributes.UNSELECTED_BORDER.outlineColor = new Color( 55 , 55 , 55 , 100 );
        
        JhromeTabBorderAttributes.UNSELECTED_ROLLOVER_BORDER.topColor = new Color( 255 , 255 , 255 , 160);
        JhromeTabBorderAttributes.UNSELECTED_ROLLOVER_BORDER.bottomColor = new Color( 255 , 255 , 255 , 50);
		
		// 页签关闭按钮
		tabbedPane.putClientProperty(JhromeTabbedPaneUI.TAB_CLOSE_BUTTONS_VISIBLE, true);
		// 新增页签按钮
		tabbedPane.putClientProperty(JhromeTabbedPaneUI.NEW_TAB_BUTTON_VISIBLE, true);
		// 窗体边框
		tabbedPane.putClientProperty(JhromeTabbedPaneUI.CONTENT_PANEL_BORDER, BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	}
	
}
