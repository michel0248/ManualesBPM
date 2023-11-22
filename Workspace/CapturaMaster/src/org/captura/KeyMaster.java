package org.captura;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import com.tulskiy.keymaster.common.*;

public class KeyMaster {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		final Provider tomarCaptura = Provider.getCurrentProvider(true);
		final Provider generarGif = Provider.getCurrentProvider(true);
//	    Runtime.getRuntime().addShutdownHook(new Thread("shutdown-hook") {
//	        @Override
//	        public void run() {
//	            provider.reset();
//	            provider.stop();
//	        }

		tomarCaptura.register(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK), key -> {
			System.out.println("Genera");

		});

		generarGif.register(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), key -> {
			System.out.println("Captura");

		});

	}

}
