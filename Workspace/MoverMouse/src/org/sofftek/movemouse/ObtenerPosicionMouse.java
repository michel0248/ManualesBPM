package org.sofftek.movemouse;

import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.Point;


public class ObtenerPosicionMouse implements Runnable {
	Thread hilo;
	static boolean banderaHilo = false;
	static Point posicionMouse;
	Frame [] frame = Frame.getFrames();
	public void run() {
		
		while (banderaHilo) {
			try {
				Thread.sleep(10);
				posicionMouse = MouseInfo.getPointerInfo().getLocation();
				frame[0].setTitle("X: "+posicionMouse.x+" Y: "+posicionMouse.y);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void iniciarHilo() {
		hilo = new Thread(this);
		banderaHilo = true;
		hilo.start();
		
	}

	public static void detenerHilo() {
		banderaHilo = false;
	}

	

}
