
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

public class LibBarCode4j {
    
	public static void main(String[] args) {
        try {
            // Crear el generador de código de barras
            Code39Bean bean = new Code39Bean();
            final int dpi = 150;

            // Configurar el tamaño
            bean.setModuleWidth(0.2);
            bean.setWideFactor(3);
            bean.doQuietZone(false);
            bean.setFontSize(0);

            // Crear archivo de salida
            File outputFile = new File("codigo_barras_code39.png");
            OutputStream out = new FileOutputStream(outputFile);

            // Crear el canvas para la imagen
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                out, "image/png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

            // Generar el código de barras
            bean.generateBarcode(canvas, "5544920700010813");

            // Finalizar
            canvas.finish();
            out.close();

            System.out.println("Código de barras generado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
