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

public class LectorLogsMain extends JFrame {

	private static final long serialVersionUID = 1L;

	JButton botonFile;

	public LectorLogsMain() {
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
				String constroversias = "Fecha\tHora\tTipo\tNombre\n";
				JFileChooser file = new JFileChooser();
				file.showOpenDialog(file);
				File archivo = file.getSelectedFile();
				if (archivo != null) {
					FileReader archivoCargado;
					try {
						archivoCargado = new FileReader(archivo);
						BufferedReader lee = new BufferedReader(archivoCargado);
						int contadorFraudes = 0;
						int contadorControversias = 0;
						while ((aux = lee.readLine()) != null) {
							if (aux.contains("FP_AbonoEnLinea.InicadorFraudes")) {
								fraudes += aux.substring(0, 10) + "\t" + aux.substring(11, 19) + "\t"
										+ (contadorFraudes % 2 == 0 ? "Request" : "Response") + "\t"
										+ aux.substring(aux.indexOf("FP"), (aux.indexOf("]", aux.indexOf("FP"))))
										+ "\n";
								contadorFraudes++;
							}

							if (aux.contains("FP_AbonoEnLinea.InicadorControversias")) {
								constroversias += aux.substring(0, 10) + "\t" + aux.substring(11, 19) + "\t"
										+ (contadorControversias % 2 == 0 ? "Request" : "Response") + "\t"
										+ aux.substring(aux.indexOf("FP"), (aux.indexOf("]", aux.indexOf("FP"))))
										+ "\n";
								contadorControversias++;
							}
						}
						File fileSave = new File(archivo.getPath());
						if (contadorFraudes != 0) {
							FileWriter saveFraudes = new FileWriter(fileSave + "fraudes.txt");
							saveFraudes.write(fraudes);
							saveFraudes.close();
						} else {
							JOptionPane.showMessageDialog(null, "No se enconraron registros de Fraudes", "Informacion",
									JOptionPane.INFORMATION_MESSAGE);
						}
						if (contadorControversias != 0) {
							fileSave = new File(archivo.getPath());
							FileWriter saveControversias = new FileWriter(fileSave + "controversias.txt");
							saveControversias.write(constroversias);
							saveControversias.close();
						} else {
							JOptionPane.showMessageDialog(null, "No se encontraron registros de Controversias",
									"Informacion", JOptionPane.INFORMATION_MESSAGE);
						}
						if (contadorFraudes != 0 || contadorControversias != 0) {
							JOptionPane.showMessageDialog(null, "Exito", "Informacion",
									JOptionPane.INFORMATION_MESSAGE);
						}

						lee.close();

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "No tiene formato de log Correcto", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}

		});

	}

	public static void main(String[] args) {
		LectorLogsMain main = new LectorLogsMain();
	}

}
