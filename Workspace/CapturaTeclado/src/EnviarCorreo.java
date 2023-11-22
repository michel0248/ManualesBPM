import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class EnviarCorreo {

	public EnviarCorreo(byte[] img,String host) {

		try {
			Properties propiedades = new Properties();
			propiedades.setProperty("mail.smtp.host", "smtp.gmail.com");
			propiedades.setProperty("mail.smtp.starttls.enable", "true");
			propiedades.setProperty("mail.smtp.port", "587");
			propiedades.setProperty("mail.smtp.auth", "true");

			Session sesion = Session.getDefaultInstance(propiedades);

			String correo_emisor = "";
			String password_emisior = "";
			
			String correo_receptor =  "";

			
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			String fechaActual = df.format(new Date());
			String asunto = host+" - "+fechaActual;

			// adjunta texto en formato html
			BodyPart texto = new MimeBodyPart();
			texto.setContent("<b>Hola Imagen del Dia</> "+fechaActual, "text/html");
			// adjunta imagen al correo
			BodyPart imagen = new MimeBodyPart();
			
			ByteArrayDataSource bds = new ByteArrayDataSource(img, "image/jpeg");
			imagen.setDataHandler(new DataHandler(bds)); 
			imagen.setFileName("Imagen.jpg");
			
			MimeMultipart partes = new MimeMultipart();
			partes.addBodyPart(texto);
			partes.addBodyPart(imagen);
			
			MimeMessage mensaje = new MimeMessage(sesion);
			mensaje.setFrom(new InternetAddress(correo_emisor));
			mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(correo_receptor));
			mensaje.setSubject(asunto);
			mensaje.setContent(partes);
			
			Transport transporte = sesion.getTransport("smtp");
			transporte.connect(correo_emisor, password_emisior);
			transporte.sendMessage(mensaje, mensaje.getRecipients(Message.RecipientType.TO));
			transporte.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
