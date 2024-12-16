package moverFrame;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Init extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;
	JButton yes = new JButton("SI");
	JButton no = new JButton("NO");
	JLabel lbl =  new JLabel("Eres La Tonta Chona?");

	public Init() {
		setSize(150, 100);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		yes.addMouseListener(this);
		no.addMouseListener(this);
		addMouseListener(this);
		setResizable(false);
		add(yes, BorderLayout.NORTH);
		add(lbl,BorderLayout.CENTER);
		add(no, BorderLayout.SOUTH);
		setVisible(true);

	}

	public static void main(String[] args) {
		new Init();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource()== yes) {
			JOptionPane.showMessageDialog(this, "Muy bien ya lo sabiamos!");
			System.exit(EXIT_ON_CLOSE);
			
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == no) {
			int x = (int) (Math.random() * 1080) + 1;
			int y = (int) (Math.random() * 570) + 1;
			this.setLocation(x, y);
			this.repaint();
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
