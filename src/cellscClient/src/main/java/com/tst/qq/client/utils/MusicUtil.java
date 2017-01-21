package com.tst.qq.client.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class MusicUtil {

	public static void playMsgMusic() {
//		new MusicThread("msg.wav").start();
		new MusicThread("fadeIn.wav").start();
	}
	
	public static void playSysMusic() {
		new MusicThread("system.wav").start();
	}

}

/** 播放音乐 */
class MusicThread extends Thread {

	private String name;
	
	public MusicThread(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		try {
			File file = new File("src/feiqq/resource/music/" + name);
			InputStream is = new FileInputStream(file);
			AudioStream as = new AudioStream(is);
			AudioPlayer.player.start(as);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
