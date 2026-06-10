import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;

public class Code39Manual {
    // Mapa de caracteres CODE39 (simplificado)
    private static final Map<Character, String> CODE39_MAP = new HashMap<>();

    static {
        CODE39_MAP.put('0', "101001101101");
        CODE39_MAP.put('1', "110100101011");
        CODE39_MAP.put('2', "101100101011");
        CODE39_MAP.put('3', "110110010101");
        CODE39_MAP.put('4', "101001101011");
        CODE39_MAP.put('5', "110100110101");
        CODE39_MAP.put('6', "101100110101");
        CODE39_MAP.put('7', "101001011011");
        CODE39_MAP.put('8', "110100101101");
        CODE39_MAP.put('9', "101100101101");
        CODE39_MAP.put('A', "110101001011");
        CODE39_MAP.put('B', "101101001011");
        CODE39_MAP.put('C', "110110100101");
        CODE39_MAP.put('D', "101011001011");
        CODE39_MAP.put('E', "110101100101");
        CODE39_MAP.put('F', "101101100101");
        CODE39_MAP.put('G', "101010011011");
        CODE39_MAP.put('H', "110101001101");
        CODE39_MAP.put('I', "101101001101");
        CODE39_MAP.put('J', "101011001101");
        CODE39_MAP.put('*', "100101101101"); // Start/Stop
        // Agrega más caracteres si lo necesitas
    }

    public static void main(String[] args) throws Exception {
        String data = "5544920700010813"; // CODE39 requiere * al inicio y fin
        StringBuilder pattern = new StringBuilder();

        for (char c : data.toCharArray()) {
            String code = CODE39_MAP.get(c);
            if (code == null) throw new IllegalArgumentException("Carácter no soportado: " + c);
            pattern.append(code).append("0"); // espacio entre caracteres
        }

        int barWidth = 2;
        int height = 100;
        int width = pattern.length() * barWidth;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.BLACK);
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '1') {
                g.fillRect(i * barWidth, 0, barWidth, height);
            }
        }

        g.dispose();
        ImageIO.write(image, "png", new File("code39_manual.png"));
        System.out.println("Código de barras generado sin librerías externas.");
    }
}
