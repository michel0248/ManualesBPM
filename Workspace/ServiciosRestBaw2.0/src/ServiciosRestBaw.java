
import java.io.DataOutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.xml.bind.DatatypeConverter;
import java.net.HttpURLConnection;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServiciosRestBaw {
	HttpURLConnection conn;

	public String serviceRestGet(String uriAuth, String uriRest, String parametro, String username, String password)
			throws IOException, Exception {
		String salida = "";
		URL url = null;
		String userpass = String.valueOf(String.valueOf(String.valueOf(username))) + ":" + password;
		String basicAuth = "Basic " + new String(DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8")));
		String resToken = getTokenBaw(uriAuth, basicAuth);
		ObjectMapper mapper = new ObjectMapper();
		DTOToken tokenResponse = (DTOToken) mapper.readValue(resToken, DTOToken.class);
		if (parametro != null && !parametro.isEmpty()) {
			char valor = parametro.charAt(0);
			if (valor == '?') {
				parametro = remplaceSpecialCaracters(parametro);
			} else {
				parametro = "?" + remplaceSpecialCaracters(parametro);
			}
		}
		url = new URL(String.valueOf(String.valueOf(String.valueOf(remplaceSpecialCaracters(uriRest)))) + parametro);
		(this.conn = (HttpURLConnection) url.openConnection()).setRequestMethod("GET");
		this.conn.setRequestProperty("Content-Type", "application/json");
		this.conn.setRequestProperty("Charset", "utf-8");
		this.conn.setRequestProperty("Authorization", basicAuth);
		this.conn.setRequestProperty("BPMCSRFToken", tokenResponse.csrf_token);
		this.conn.setRequestProperty("Accept", "*/*");
		InputStreamReader in = new InputStreamReader(this.conn.getInputStream());
		BufferedReader br = new BufferedReader(in);
		String output;
		while ((output = br.readLine()) != null)
			salida = String.valueOf(String.valueOf(String.valueOf(salida))) + output;
		this.conn.disconnect();
		return salida;
	}

	public String serviceRestPost(String uriAuth, String uriRest, String urlParameters, String username,
			String password) throws IOException {
		String salida = "";
		URL url = new URL(remplaceSpecialCaracters(uriRest));
		String userpass = String.valueOf(String.valueOf(String.valueOf(username))) + ":" + password;
		String basicAuth = "Basic " + new String(DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8")));
		String resToken = getTokenBaw(uriAuth, basicAuth);
		ObjectMapper mapper = new ObjectMapper();
		DTOToken tokenResponse = (DTOToken) mapper.readValue(resToken, DTOToken.class);
		urlParameters = urlParameters.replaceAll("'", "\"");
		byte[] postData = urlParameters.getBytes("UTF-8");
		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
		(this.conn = (HttpURLConnection) url.openConnection()).setRequestMethod("POST");
		this.conn.setInstanceFollowRedirects(false);
		this.conn.setDoOutput(true);
		this.conn.setRequestProperty("Content-Type", "application/json");
		this.conn.setRequestProperty("charset", "utf-8");
		this.conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
		this.conn.setRequestProperty("Authorization", basicAuth);
		this.conn.setRequestProperty("BPMCSRFToken", tokenResponse.csrf_token);
		this.conn.setRequestProperty("Accept", "*/*");
		this.conn.setUseCaches(false);
		DataOutputStream dos = new DataOutputStream(this.conn.getOutputStream());
		dos.write(postData);
		BufferedReader bf = new BufferedReader(new InputStreamReader(this.conn.getInputStream()));
		String line;
		while ((line = bf.readLine()) != null)
			salida = String.valueOf(String.valueOf(String.valueOf(salida))) + line;
		this.conn.disconnect();
		return salida;
	}
	
	public String serviceRestPut(final String uriAuth, final String uriRest, String body, final String username,
			final String password) throws IOException, Exception {
		String salida = "";
		URL url = null;
		final String userpass = String.valueOf(String.valueOf(username)) + ":" + password;
		final String basicAuth = "Basic " + new String(DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8")));
		final String resToken = this.getTokenBaw(uriAuth, basicAuth);
		ObjectMapper mapper = new ObjectMapper();
		DTOToken tokenResponse = (DTOToken) mapper.readValue(resToken, DTOToken.class);
		url = new URL(String.valueOf(String.valueOf(remplaceSpecialCaracters(uriRest))));
		(this.conn = (HttpURLConnection) url.openConnection()).setRequestMethod("PUT");
		this.conn.setRequestProperty("Content-Type", "application/json");
		this.conn.setRequestProperty("Charset", "utf-8");
		this.conn.setRequestProperty("Authorization", basicAuth);
		this.conn.setRequestProperty("BPMCSRFToken", tokenResponse.csrf_token);
		this.conn.setRequestProperty("Accept", "*/*");
		conn.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
		out.write(body);
		out.close();
		final InputStreamReader in = new InputStreamReader(this.conn.getInputStream());
		final BufferedReader br = new BufferedReader(in);
		String output;
		while ((output = br.readLine()) != null) {
			salida = String.valueOf(String.valueOf(salida)) + output;
		}
		this.conn.disconnect();
		return salida;
	}

	private String getTokenBaw(String uri, String basicAuth) throws IOException {
		String salida = "";
		URL url = new URL(remplaceSpecialCaracters(uri));
		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
		byte[] postData = "{\"refresh-groups\": false,\"requested-lifetime\": 7200}".getBytes("UTF-8");
		(this.conn = (HttpURLConnection) url.openConnection()).setRequestMethod("POST");
		this.conn.setInstanceFollowRedirects(false);
		this.conn.setDoOutput(true);
		this.conn.setRequestProperty("Content-Type", "application/json");
		this.conn.setRequestProperty("charset", "utf-8");
		this.conn.setRequestProperty("Authorization", basicAuth);
		this.conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
		this.conn.setUseCaches(false);
		DataOutputStream dos = new DataOutputStream(this.conn.getOutputStream());
		dos.write(postData);
		dos.close();
		BufferedReader bf = new BufferedReader(new InputStreamReader(this.conn.getInputStream()));
		String line;
		while ((line = bf.readLine()) != null)
			salida = String.valueOf(String.valueOf(String.valueOf(salida))) + line;
		this.conn.disconnect();
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

	private static class DTOToken {
		  Integer expiration;
		  String csrf_token;
		  
		  public Integer getExpiration() {
		    return this.expiration;
		  }
		  
		  public void setExpiration(Integer expiration) {
		    this.expiration = expiration;
		  }
		  
		  public String getCsrf_token() {
		    return this.csrf_token;
		  }
		  
		  public void setCsrf_token(String csrf_token) {
		    this.csrf_token = csrf_token;
		  }
		}

}
