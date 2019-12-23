package org.softtek.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GeneraTxtTest extends JFrame {

	private static final long serialVersionUID = 1L;

	JButton botonFile;

	public GeneraTxtTest() {
		this.setLayout(null);
		this.setSize(300, 300);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.botonFile = new JButton("Seleccionar Log");
		this.botonFile.setBounds(10, 10, 150, 30);
		this.add(botonFile);
		this.botonFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String aux;
				String fraudes = "Fecha\tHora\tTipo\tNombre\n";
				JFileChooser file = new JFileChooser();
				file.showOpenDialog(file);
				File archivo = file.getSelectedFile();
				if (archivo != null) {
					FileReader archivoCargado;
					try {
						archivoCargado = new FileReader(archivo);
						BufferedReader lee = new BufferedReader(archivoCargado);

						while ((aux = lee.readLine()) != null) {
							if(aux.length()>10) {
								fraudes += aux.substring(0, 5) + "\t" + aux.substring(0, 5)+aux.substring(0,10)+"\n";
							}
							

						}
						File fileSave = new File(archivo.getPath());

						FileWriter saveFraudes = new FileWriter(fileSave + "fraudes.txt");
						saveFraudes.write(fraudes);
						saveFraudes.close();
						lee.close();
						JOptionPane.showMessageDialog(null, "Termino");

					} catch (Exception ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null, "No tiene formato de log Correcto", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}

		});

	}

	public static void main(String[] args) {
		GeneraTxtTest main = new GeneraTxtTest();
	}

}
