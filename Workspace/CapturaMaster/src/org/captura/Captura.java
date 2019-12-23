package org.captura;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Captura extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static boolean banderaHilo;
	int numberFrame;
	ArrayList<BufferedImage> archivosFrames;

	static public BufferedImage captureScreen(int Index) throws Exception {
		//Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(1366,728);
		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(screenRectangle);
		image = resize(image, 800, 500);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "png", out);
		ByteArrayInputStream bais = new ByteArrayInputStream(out.toByteArray());
		out.close();
		return ImageIO.read(bais);
	}
	
	public static BufferedImage resize(BufferedImage bufferedImage, int newW, int newH) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage bufim = new BufferedImage(newW, newH, bufferedImage.getType());
        Graphics2D g = bufim.createGraphics();
        //g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return bufim;
    }

	public Thread InicializarHilo() {
		Thread hilo = new Thread(this);
		hilo.setPriority(Thread.MIN_PRIORITY);
		return hilo;
	}

	public void generaGif(ArrayList<BufferedImage> archivos) {
		try {

			if (archivos.size() > 1) {
				// obtiene el primer frame de el arreglo de BufferedImage
				BufferedImage firstImage = archivos.get(0);

				// crea la ruta donde se guardara el gif
				ImageOutputStream output = new FileImageOutputStream(new File("file.gif"));

				// Crea la secuencia con intervalo de 1 seg
				GifSequenceWriter writer = new GifSequenceWriter(output, firstImage.getType(), 500 , true);

				// guarda la primera imagen en el gif
				writer.writeToSequence(firstImage);
				for (int i = 1; i < archivos.size() - 1; i++) {
					BufferedImage nextImage = archivos.get(i);
					writer.writeToSequence(nextImage);
				}

				writer.close();
				output.close();
				System.out.println("[ Captura finalizada ] " + archivos.size() + " Frames");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Captura() {
		JButton boton = new JButton("Iniciar");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.add(boton);
		boton.setBounds(180, 100, 120, 40);
		boton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == "Iniciar") {
					numberFrame = 0;
					archivosFrames = new ArrayList<BufferedImage>();
					banderaHilo = true;
					InicializarHilo().start();
					boton.setText("Detener");

				} else {
					banderaHilo = false;
					generaGif(archivosFrames);
					boton.setText("Iniciar");
				}
			}
		});

	}

	@Override
	public void run() {
		while (banderaHilo) {
			try {
				Thread.sleep(1000);
				archivosFrames.add(Captura.captureScreen(numberFrame));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) {
		new Captura().setVisible(true);
	}

}
