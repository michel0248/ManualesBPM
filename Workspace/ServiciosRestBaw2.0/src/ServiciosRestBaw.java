
import java.io.DataOutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.xml.bind.DatatypeConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.HttpURLConnection;


public class ServiciosRestBaw {
	HttpURLConnection conn;

	public String serviceRestGet(final String uriAuth, final String uriRest, String parametro, final String username,
			final String password) throws IOException, Exception {
		String salida = "";
		URL url = null;
		final String userpass = String.valueOf(String.valueOf(username)) + ":" + password;
		final String basicAuth = "Basic " + new String(DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8")));
		final String resToken = this.getTokenBaw(uriAuth, basicAuth);
		ObjectMapper mapper = new ObjectMapper();
		DTOToken tokenResponse = mapper.readValue(resToken, DTOToken.class);
		if (parametro != null && !parametro.isEmpty()) {
			final char valor = parametro.charAt(0);
			if (valor == '?') {
				parametro = remplaceSpecialCaracters(parametro);
			} else {
				parametro = "?" + remplaceSpecialCaracters(parametro);
			}
		}
		url = new URL(String.valueOf(String.valueOf(remplaceSpecialCaracters(uriRest))) + parametro);
		(this.conn = (HttpURLConnection) url.openConnection()).setRequestMethod("GET");
		this.conn.setRequestProperty("Content-Type", "application/json");
		this.conn.setRequestProperty("Charset", "utf-8");
		this.conn.setRequestProperty("Authorization", basicAuth);
		this.conn.setRequestProperty("BPMCSRFToken", tokenResponse.csrf_token);
		this.conn.setRequestProperty("Accept", "*/*");
		final InputStreamReader in = new InputStreamReader(this.conn.getInputStream());
		final BufferedReader br = new BufferedReader(in);
		String output;
		while ((output = br.readLine()) != null) {
			salida = String.valueOf(String.valueOf(salida)) + output;
		}
		this.conn.disconnect();
		return salida;
	}

	public String serviceRestPost(final String uriAuth, final String uriRest, String urlParameters,
			final String username, final String password) throws IOException {
		String salida = "";
		final URL url = new URL(remplaceSpecialCaracters(uriRest));
		final String userpass = String.valueOf(String.valueOf(username)) + ":" + password;
		final String basicAuth = "Basic " + new String(DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8")));
		final String resToken = this.getTokenBaw(uriAuth, basicAuth);
		ObjectMapper mapper = new ObjectMapper();
		DTOToken tokenResponse = mapper.readValue(resToken, DTOToken.class);
		urlParameters = urlParameters.replaceAll("'", "\"");
		final byte[] postData = urlParameters.getBytes("UTF-8");
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
		final DataOutputStream dos = new DataOutputStream(this.conn.getOutputStream());
		dos.write(postData);
		final BufferedReader bf = new BufferedReader(new InputStreamReader(this.conn.getInputStream()));
		String line;
		while ((line = bf.readLine()) != null) {
			salida = String.valueOf(String.valueOf(salida)) + line;
		}
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

	private String getTokenBaw(final String uri, final String basicAuth) throws IOException {
		String salida = "";
		final URL url = new URL(remplaceSpecialCaracters(uri));
		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
		final String data = "{\"refresh-groups\": false,\"requested-lifetime\": 7200}";
		final byte[] postData = data.getBytes("UTF-8");
		(this.conn = (HttpURLConnection) url.openConnection()).setRequestMethod("POST");
		this.conn.setInstanceFollowRedirects(false);
		this.conn.setDoOutput(true);
		this.conn.setRequestProperty("Content-Type", "application/json");
		this.conn.setRequestProperty("charset", "utf-8");
		this.conn.setRequestProperty("Authorization", basicAuth);
		this.conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
		this.conn.setUseCaches(false);
		final DataOutputStream dos = new DataOutputStream(this.conn.getOutputStream());
		dos.write(postData);
		dos.close();
		final BufferedReader bf = new BufferedReader(new InputStreamReader(this.conn.getInputStream()));
		String line;
		while ((line = bf.readLine()) != null) {
			salida = String.valueOf(String.valueOf(salida)) + line;
		}
		this.conn.disconnect();
		return salida;
	}

	private class DTOToken {
		@SuppressWarnings("unused")
		Integer expiration;
		String csrf_token;
	}

}
