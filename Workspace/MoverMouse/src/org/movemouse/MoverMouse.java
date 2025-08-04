package org.movemouse;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MoverMouse extends JFrame implements Runnable, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Robot robot;
	JButton botonIniciar = new JButton("Iniciar");
	JButton botonAgregar = new JButton("Agregar");
	JButton botonEliminar = new JButton("Eliminar");
	JCheckBox click = new JCheckBox("Click");
	JPanel panel = new JPanel();
	boolean banderaHilo = false;
	DefaultTableModel tableModel = new DefaultTableModel();
	JTable tabla;
	Thread hilo;
	boolean tableValida;

	public MoverMouse() {
		this.setSize(280, 280);
		// this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setAlwaysOnTop(true);
		this.botonIniciar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
		this.tabla = new JTable(tableModel);
		this.tabla.setRowSelectionAllowed(false);
		this.tableModel.addColumn("Posici\u00f3n X");
		this.tableModel.addColumn("Posici\u00f3n Y");
		// Se agregan elementos al panel
		this.panel.setLayout(new FlowLayout());
		this.panel.add(botonAgregar);
		this.panel.add(botonEliminar);
		this.panel.add(click);
		// se agregan componentes al jframe
		this.add(botonIniciar, BorderLayout.NORTH);
		this.add(new JScrollPane(tabla), BorderLayout.CENTER);
		this.add(panel, BorderLayout.SOUTH);
		//se hace visible el jframe
		this.setVisible(true);
		// se agregan listeners
		this.botonIniciar.addMouseListener(this);
		this.botonIniciar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == "Iniciar") {
					tableValida = validarTabla(tableModel);
					if (tableModel.getRowCount() == 0) {
						botonIniciar.setText("Pausar");
						iniciarHilo();
					} else if (tableModel.getRowCount() > 0 && tableValida == true) {
						botonIniciar.setText("Pausar");
						iniciarHilo();
					}

				} else {
					detenerHilo();
					botonIniciar.setText("Iniciar");
				}

			}
		});

		this.botonAgregar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.insertRow(tableModel.getRowCount(), new Object[] { "", "" });
			}
		});

		this.botonEliminar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tableModel.getRowCount() > 0) {
					tableModel.removeRow(tableModel.getRowCount() - 1);

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
		new MoverMouse();
		new ObtenerPosicionMouse().iniciarHilo();
		      
	}

	@Override
	public void run() {

		while (banderaHilo) {
			try {
				if (tableModel.getRowCount() == 0) {
					Thread.sleep(90000);
					if (banderaHilo == true) {
						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
						int x = (int) (Math.random() * screenSize.width) + 1;
						int y = (int) (Math.random() * screenSize.height) + 1;
						robot.mouseMove(x, y);
					}
				} else if (tableModel.getRowCount() > 0 && tableValida == true) {
					for (int i = 0; i < tableModel.getRowCount(); i++) {
						Thread.sleep(5000);
						if (banderaHilo == true) {
							int x = Integer.parseInt(tableModel.getValueAt(i, 0).toString());
							int y = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
							robot.mouseMove(x, y);
							if (click.isSelected() == true) {
								robot.mousePress(InputEvent.getMaskForButton(MouseEvent.BUTTON1));
								Thread.sleep(10);
								robot.mouseRelease(InputEvent.getMaskForButton(MouseEvent.BUTTON1));
								Thread.sleep(10);
							}
						}

					}
				}
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

	public void detenerHilo() {
		banderaHilo = false;
	}

	public boolean validarTabla(DefaultTableModel model) {
		String mensaje = "";
		boolean valido = false;
		if (model.getRowCount() > 0) {
			for (int i = 0; i < model.getRowCount(); i++) {
				String x = (String) model.getValueAt(i, 0);
				String y = (String) model.getValueAt(i, 1);
				if (x.isEmpty() || y.isEmpty()) {
					mensaje += "Ninguno de las columnas (x , y) en la fila " + (i + 1) + " pueden estar vacios\n";
				}
				if (isNumeric(x) == false || isNumeric(y) == false) {
					mensaje += "Los valores de la columna (x , y) en la   fila " + (i + 1) + " deben ser numericos\n";
				}
			}
			if (!mensaje.isEmpty()) {
				JOptionPane.showMessageDialog(this, mensaje);
			} else {
				valido = true;
				JOptionPane pane = new JOptionPane("Iniciando.....", JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = pane.createDialog(this, "Inciando");
				dialog.setModal(false);
				dialog.setVisible(true);
				new Timer(1500, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.setVisible(false);
					}
				}).start();

			}
		}
		return valido;
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}


	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {}
	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {		
		try {
			tabla.getCellEditor().stopCellEditing();
		} catch (Exception ex) {

		}
	}
	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {}
	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {}
	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {}

}
