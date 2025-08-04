package org.softtek.multitherad;

public class MultiThreads {

	public static void main(String[] args) {
		
		Thread hilo1 =  new Thread(new Hilo1());
		Thread hilo2 =  new Thread(new Hilo2());
		hilo1.start();
		hilo2.start();

	}

}

class Hilo1 implements Runnable {

	int contador = 0;
	@Override
	public void run() {
		while (contador != 50) {
			try {
				Thread.sleep(1);
				System.out.println("Hilo 1->".concat(String.valueOf(contador)));
				contador++;
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
		
		}

	}

}

class Hilo2 implements Runnable {
	int contador = 0;
	@Override
	public void run() {
		while (contador != 50) {
			try {
				Thread.sleep(1);
				System.out.println("Hilo 2->".concat(String.valueOf(contador)));
				contador++;
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
		
		}

	}

}
