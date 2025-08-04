import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Base64;

public class Base64EncoderApp extends JFrame {


	private static final long serialVersionUID = 1L;
	private JTextArea outputArea;

    public Base64EncoderApp() {
        setTitle("Codificador Base64");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton selectButton = new JButton("Seleccionar Archivo");
        outputArea = new JTextArea();
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);

        selectButton.addActionListener(this::handleFileSelection);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(selectButton, BorderLayout.NORTH);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        add(panel);
    }

    private void handleFileSelection(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                byte[] fileBytes = Files.readAllBytes(selectedFile.toPath());
                String mimeType = Files.probeContentType(selectedFile.toPath());
                String base64 = Base64.getEncoder().encodeToString(fileBytes);
                //System.out.println(mimeType.concat(",").concat(base64));
                outputArea.setText(base64);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Base64EncoderApp().setVisible(true);
        });
    }
}
