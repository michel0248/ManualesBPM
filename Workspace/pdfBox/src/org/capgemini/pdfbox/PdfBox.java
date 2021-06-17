package org.capgemini.pdfbox;


import java.io.File;
import java.io.IOException;

import javax.xml.bind.DatatypeConverter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import teamworks.TWList;
import teamworks.TWObjectFactory;

public class PdfBox {

	public TWList obtenerFormulario(String base64) throws Exception   {
		byte[] pdfFile = DatatypeConverter.parseBase64Binary(base64.contains(",") ? base64.split(",")[1] : base64);
		TWList datos = TWObjectFactory.createList();
		PDDocument documento = PDDocument.load(pdfFile);
		if(!documento.isEncrypted()){
			PDDocumentCatalog catalogo = documento.getDocumentCatalog();
			PDAcroForm formulario = catalogo.getAcroForm();

			for (PDField pdfField : formulario.getFields()) {
				datos.addArrayData(pdfField.getPartialName() + "-"+ pdfField.getValueAsString());

			}
		}

		return datos;
	}
	
	public String obtenerTexto(String base64) throws IOException{
		PDDocument document = PDDocument.load(new File("doc1.pdf"));
		String texto = "Sin datos";
		if (!document.isEncrypted()) {

            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);
            PDFTextStripper pdfTS = new PDFTextStripper();
            String textoPdf = pdfTS.getText(document);
           
			// borra espacios en blanco
            String lines[] = textoPdf.split("\\r?\\n\\ ");
            if(textoPdf.length()!=0){texto = "";}
            for (String line : lines) {
                texto += line;
            }
		}
		return texto;
	}

	public static void main(String args[]) throws Exception {
		PdfBox p = new PdfBox();
		System.out.println(p.obtenerTexto(""));
	}

}
