package com.tst.cellsc;

import java.awt.EventQueue;

import com.tst.cellsc.ui.LoginUI;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new LoginUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
