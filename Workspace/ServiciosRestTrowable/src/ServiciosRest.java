import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

//import javax.xml.bind.DatatypeConverter;

public class ServiciosRest {
	
	HttpURLConnection conn;

	public String serviceRestGet(final String uri, final String parametros, final String username,final String password) throws IOException {
		String salida = "";
		URL url = null;
		
			String parametro = parametros;
			String userpass = username + ":" + password;
			String basicAuth = "Basic "+  Base64.getEncoder().encodeToString(userpass.getBytes("UTF-8"));
			System.out.println(basicAuth);
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

			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String output;

			while ((output = br.readLine()) != null) {
				salida += output;
			}

			conn.disconnect();

	

		return salida;

	}

	public String serviceRestPost(final String uri,final String urlParameter) throws IOException {
		String salida = "";
			String urlParameters = urlParameter;
			URL url = new URL(remplaceSpecialCaracters(uri));
			urlParameters = urlParameters.replaceAll("'", "\"");
			byte[] postData = urlParameters.getBytes("UTF-8"); 
			CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setInstanceFollowRedirects(false);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");	
			conn.setRequestProperty("charset", "utf-8");
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
			
	

		return salida;

	}

	public static String remplaceSpecialCaracters(String cadena) {
		cadena = cadena.replaceAll(" ", "%20");
		cadena = cadena.replaceAll("&quot;", "%27");
		cadena = cadena.replaceAll("'", "%27");
		cadena = cadena.replaceAll("\"", "%27");
		cadena = cadena.replaceAll("&apos;", "%27");
		return cadena;
	}
	
	/*public static void main(String args[]) throws IOException {
	ServiciosRest f = new ServiciosRest();
     System.out.println(f.serviceRestGet("https://lab04bpmpc:9443/rest/bpm/wle/v1/task/84813", "?parts=all","deadmin", "deadmin"));
	//System.out.println(f.serviceRestGet("http://dummy.restapiexample.com/api/v1/employees", "","", ""));
	//System.out.println(f.serviceRestPost("http://www.httpbin.org/post", "{'name':'test','salary':'123','age':'23'}"));
}*/


}