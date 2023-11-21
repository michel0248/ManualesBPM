
//import java.io.File;
import java.io.IOException;
import javax.xml.bind.DatatypeConverter;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import teamworks.TWList;
import teamworks.TWObjectFactory;

public class PdfBoxV2 {

	public TWList obtenerFormulario(final String base64) throws Exception   {
		byte[] pdfFile = DatatypeConverter.parseBase64Binary(base64.contains(",") ? base64.split(",")[1] : base64);
		//PDDocument documento = PDDocument.load(new File("docCSC1.pdf"));
		TWList datos = TWObjectFactory.createList();
		PDDocument documento = Loader.loadPDF(pdfFile);
		if(!documento.isEncrypted()){
			PDDocumentCatalog catalogo = documento.getDocumentCatalog();
			PDAcroForm formulario = catalogo.getAcroForm();

			for (PDField pdfField : formulario.getFields()) {
				datos.addArrayData(pdfField.getValueAsString());
				//System.out.println(pdfField.getMappingName()+"|"+pdfField.getValueAsString());
				

			}
		}

		return datos;
		//return null;
	}
	
	public String obtenerTexto(final String base64) throws IOException{
		byte[] pdfFile = DatatypeConverter.parseBase64Binary(base64.contains(",") ? base64.split(",")[1] : base64);
		//PDDocument documento = PDDocument.load(new File("docCSC1.pdf"));
		PDDocument documento = Loader.loadPDF(pdfFile);
		String texto = "Sin datos";
		if (!documento.isEncrypted()) {

            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);
            PDFTextStripper pdfTS = new PDFTextStripper();
            String textoPdf = pdfTS.getText(documento);
           
			// borra espacios en blanco
            String lines[] = textoPdf.split("\\r?\\n\\ ");
            if(textoPdf.length()!=0){
            	texto = "";
            }
            for (String line : lines) {
                texto += line;
            }
            System.out.println(texto);
		}
		return texto;
		
	}
	/*public static void main(String args[]) throws Exception {
		PdfBoxV2 p = new PdfBoxV2();
	}*/

}