package org.camera.video;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import java.io.File;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;
import com.github.sarxos.webcam.WebcamResolution;

public class DeteccionMovimiento implements WebcamMotionListener {
	Webcam webcam ;
	public DeteccionMovimiento() {
		
		webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		WebcamMotionDetector detector = new WebcamMotionDetector(webcam);
		detector.setInterval(500);
		detector.addMotionListener(this);
		detector.start();

	}

	@Override
	public void motionDetected(WebcamMotionEvent wme) {
		System.out.println("Se Detecto un Movimiento");
		try {
			BufferedImage image = webcam.getImage();
			SimpleDateFormat sf = new SimpleDateFormat("ddMMyyyy hh-mm-ss");
			ImageIO.write(image, "PNG", new File("Movimiento"+sf.format(new Date())+".png"));
		} catch (IOException e) {
		e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		new DeteccionMovimiento();
		System.in.read(); // mantiene el programa abierto
	}
}