package org.pdf.test;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;



public class AbrirPDFConPassword {
    public static void main(String[] args) {
        generarCombinaciones("", 4);
    }


	public static void generarCombinaciones(String prefijo, int longitud) {
		if (longitud == 0) {
			Decrypt(prefijo);
			return;
		}
		for (int i = 0; i <= 9; i++) {
			generarCombinaciones(prefijo + i, longitud - 1);
		}
	}
	
	public static void Decrypt(String pass) {
		String rutaPDF = "C:\\ManualesBPM\\Workspace\\PDFDecryptor\\EstadoCuenta.pdf"; // Cambia por la ruta real
		String password = pass; // Contraseña del PDF

		try {
			// Cargar el documento con la contraseña
			PDDocument documento = PDDocument.load(new File(rutaPDF), password);

			if (!documento.isEncrypted()) {
				System.out.println("El PDF no está protegido.");
			} else {
				System.out.println("PDF abierto correctamente con la contraseña." +pass);
			}

			// Aquí puedes hacer algo con el documento, por ejemplo, mostrar número de
			// páginas
			System.out.println("Número de páginas: " + documento.getNumberOfPages());

			// Cerrar el documento
			documento.close();
		} catch (Exception e) {
			System.err.println("Error al abrir el PDF: " + e.getMessage());
		}
	}


}
