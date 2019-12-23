package org.sofftek.movemouse;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.AWTException;
import java.awt.Font;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoverMouse extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Robot robot;
	JButton botonIniciar = new JButton("Iniciar");
	boolean banderaHilo = false;

	public MoverMouse() {
		this.setSize(200, 200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.botonIniciar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
		this.add(botonIniciar);
		this.botonIniciar.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == "Iniciar") {
					iniciarHilo();
					botonIniciar.setText("Pausar");
				} else {
					detenerHilo();
					botonIniciar.setText("Iniciar");
				}

			}
		});

		try {
			robot = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}

	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MoverMouse mouse = new MoverMouse();

	}

	@Override
	public void run() {

		while (banderaHilo) {
			try {
				int x = (int) (Math.random() * 1300) + 1;
				int y = (int) (Math.random() * 500) + 1;
				robot.mouseMove(x, y);
				Thread.sleep(5000);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void iniciarHilo() {
		Thread hilo = new Thread(this);
		hilo.start();
		banderaHilo = true;
	}

	public void detenerHilo() {
		banderaHilo = false;
	}

}
