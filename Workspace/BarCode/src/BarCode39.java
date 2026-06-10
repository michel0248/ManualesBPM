import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.Barcode39;
import com.lowagie.text.Image;

import java.io.FileOutputStream;

public class BarCode39 {
    public static void main(String[] args) {
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("code39_sin_texto.pdf"));
            document.open();

            // Crear el código de barras
            Barcode39 barcode = new Barcode39();
            barcode.setCode("5544920700010813");

            // Ocultar el texto debajo del código
            barcode.setFont(null); // Esto elimina el texto visible
            barcode.setBarHeight(38f);
            barcode.setX(1.5f);
            // Convertir a imagen
            Image barcodeImage = barcode.createImageWithBarcode(writer.getDirectContent(), null, null);
            barcodeImage.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
            // Agregar al documento
            document.add(barcodeImage);
            document.close();

            System.out.println("PDF generado con código de barras sin texto.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
