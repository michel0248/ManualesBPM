import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FrameRest extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	ServiciosRest rest = new ServiciosRest();

	JButton botonEjecutar = new JButton("Ejecutar");
	
	JLabel lblUri = new JLabel("Uri:");
	JLabel lblParams = new JLabel("Params:");
	JLabel lblUser = new JLabel("Username:");
	JLabel lblPass = new JLabel("Password:");
	JTextField txtUri = new JTextField();
	JTextField txtParams = new JTextField();
	JTextField txtUser = new JTextField();
	JTextField txtPass = new JTextField();
	JTextArea txtResponse = new JTextArea();
	JScrollPane scroll = new JScrollPane(txtResponse);
	
	JLabel lblSelectRest = new JLabel("Selecciona el tipo se servicio:");
	
	ButtonGroup grupoRadios = new ButtonGroup();
	JRadioButton radioGet = new JRadioButton("Rest Get");
	JRadioButton radioPost = new JRadioButton("Rest Post");

	public FrameRest() {
		grupoRadios.add(radioGet);
		grupoRadios.add(radioPost);
		this.setResizable(false);
	    this.setLayout(null);
		
		botonEjecutar.setBounds(320, 270, 150, 30);
		botonEjecutar.addActionListener(this);
		botonEjecutar.setEnabled(false);
		this.add(botonEjecutar);
		
		radioGet.setBounds(180, 10, 80, 30);
		radioPost.setBounds(260, 10, 80, 30);
		this.add(radioGet);
		this.add(radioPost);
		radioGet.addActionListener(this);
		radioPost.addActionListener(this);
		
		lblSelectRest.setBounds(10, 10, 180, 30);
		this.add(lblSelectRest);

		lblUri.setBounds(10, 60, 50, 30);
		this.add(lblUri);
		txtUri.setBounds(10, 90, 470, 30);
		txtUri.setEditable(false);
		this.add(txtUri);
		
		lblParams.setBounds(10, 120, 50, 30);
		this.add(lblParams);
		lblParams.setToolTipText("Para peticiones Post el json debe \nde llevar el sig formato {'param1':'dato','param2':'dato'} ");
		txtParams.setBounds(10, 150, 470, 30);
		txtParams.setEditable(false);
		this.add(txtParams);
		
		lblUser.setBounds(10, 180, 80, 30);
		this.add(lblUser);
		txtUser.setBounds(10, 210, 180, 30);
		txtUser.setEditable(false);
		this.add(txtUser);
		
		lblPass.setBounds(10, 240, 80, 30);
		this.add(lblPass);
		txtPass.setBounds(10, 270, 180, 30);
		txtPass.setEditable(false);
		this.add(txtPass);
		
		scroll.setBounds(10, 330,470, 430);
		txtResponse.setWrapStyleWord(true);
		txtResponse.setLineWrap(true);
		this.add(scroll);
		
		
		this.setSize(500, 800);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

	}

	public static void main(String[] args) {
		FrameRest f = new FrameRest();

	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource()==radioGet){
			txtResponse.setText("");
			botonEjecutar.setEnabled(true);
			txtUri.setEditable(true);
			txtParams.setEditable(true);
			txtUser.setEditable(true);
			txtPass.setEditable(true);
		}
		if(evt.getSource()==radioPost){
			txtResponse.setText("");
			botonEjecutar.setEnabled(true);
			txtUri.setEditable(true);
			txtParams.setEditable(true);
			txtUser.setEditable(false);
			txtPass.setEditable(false);
		}
		
		if (evt.getSource() == botonEjecutar) {
			txtResponse.setText("");
			if (radioGet.isSelected()) {
			   txtResponse.append(rest.serviceRestGet(txtUri.getText(), txtParams.getText(), txtUser.getText(), txtPass.getText()));
			}
			if (radioPost.isSelected()) {
				txtResponse.append(rest.serviceRestPost(txtUri.getText(), txtParams.getText()));
			}
		}

	}

}
