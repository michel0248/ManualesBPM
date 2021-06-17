import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.bind.DatatypeConverter;




public class ServiciosRest {
	
	HttpURLConnection conn;
	public String serviceRestGet(String uri, String parametro, String username,String password) {
		String salida = "";
		URL url = null;
	  
		try {

			String userpass = username + ":" + password;
			String basicAuth = "Basic "+ new String(DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8")));

			// agrega el ? en caso de que no lo tenga al inicio
			if (parametro != null && !parametro.isEmpty()) {
				char valor = parametro.charAt(0);
				if (valor == '?') {
					parametro = remplaceSpecialCaracters(parametro);
				} else {
					parametro = "?" + remplaceSpecialCaracters(parametro);
				}
			}

			url = new URL(remplaceSpecialCaracters(uri) + parametro);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type","application/json");
			conn.setRequestProperty("Charset", "utf-8");
			conn.setRequestProperty("Authorization", basicAuth);
			conn.setRequestProperty("Accept", "*/*");
			//salida = String.valueOf(conn.getResponseCode());

			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String output;

			while ((output = br.readLine()) != null) {
				salida += output;
			}
			
			conn.disconnect();

		} catch (IOException e) {
			salida = "{\"Error\": \"Service Rest Get " + e.toString()+"\"}";
		}
		
		return salida;

	}
    
	public String serviceRestPost(String uri, String urlParameters) {
		String salida = "";
		try {
			URL url = new URL(remplaceSpecialCaracters(uri));
			urlParameters = urlParameters.replaceAll("'", "\"");
			byte[] postData = urlParameters.getBytes("UTF-8"); 
			CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setInstanceFollowRedirects(false);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");	
			conn.setRequestProperty("Charset", "utf-8");
			conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
			conn.setUseCaches(false);
			
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.write(postData);
			
			BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
		
			while ((line = bf.readLine()) != null) {
				salida += line;
			}

			conn.disconnect();
			
		} catch (IOException e) {
			salida = "{\"Error\": \"Service Rest Post " + e.toString()+"\"}";
		}

	    
		return salida;
	}

	private String remplaceSpecialCaracters(String cadena) {
		cadena = cadena.replaceAll(" ", "%20");
		cadena = cadena.replaceAll("&quot;", "%27");
		cadena = cadena.replaceAll("'", "%27");
		cadena = cadena.replaceAll("\"", "%27");
		cadena = cadena.replaceAll("&apos;", "%27");
		return cadena;
	}

	/*public static void main(String args[]) {
		ServiciosRest f = new ServiciosRest();
	     System.out.println(f.serviceRestGet("https://lab04bpmpc:9443/rest/bpm/wle/v1/task/84813", "?parts=all","deadmin", "deadmin"));
		//System.out.println(f.serviceRestGet("http://dummy.restapiexample.com/api/v1/employees", "","", ""));
		//System.out.println(f.serviceRestPost("http://www.httpbin.org/post", "{'name':'test','salary':'123','age':'23'}"));
	}*/

}