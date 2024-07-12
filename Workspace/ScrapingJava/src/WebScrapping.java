import java.io.IOException;
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

 
public class WebScrapping {
 
 public static void main(String[] args) {
 
 Document doc;
 try {
 
	//obtener todas las imágenes
	 doc = Jsoup.connect("https://www.mercadolibre.com.mx/")
	 .userAgent("Mozilla")
	 .get();
	 System.out.println(doc.getElementsByClass(""));	 

 
 } catch (IOException e) {
 e.printStackTrace();
 }
 
 }
 
}