import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.xml.bind.DatatypeConverter;

public class FuncionesComunes {

	public String serviceRestGet(String uri, String parametro, String username, String password) {
		String salida = "";
		URL url = null;
		try {

			String userpass = username + ":" + password;
			String basicAuth = "Basic " + new String(DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8")));

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
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			conn.setRequestProperty("content-type", "application/atom+xml;type=feed; charset=utf-8");
			conn.setRequestProperty("Authorization", basicAuth);
			conn.setRequestProperty("Accept", "*/*");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException(url + " - Failed : HTTP Error code : " + conn.getResponseCode());
			}
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String output;

			while ((output = br.readLine()) != null) {
				salida += output;
			}

			conn.disconnect();

		} catch (IOException e) {
			salida = url + " - Error al consumir servicio:- " + e;
		}

		return salida;

	}

	public String getLocalHost() {
		try {
			InetAddress ia = InetAddress.getLocalHost();
			return ia.toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public static String remplaceSpecialCaracters(String cadena) {
		cadena = cadena.replaceAll(" ", "%20");
		cadena = cadena.replaceAll("&quot;", "%27");
		cadena = cadena.replaceAll("'", "%27");
		cadena = cadena.replaceAll("\"", "%27");
		cadena = cadena.replaceAll("&apos;", "%27");
		return cadena;
	}

	public static void main(String args[]) {

		FuncionesComunes f = new FuncionesComunes();
		System.out.println(f.getLocalHost());
		// System.out.println(f.serviceRestGet(
		// "http://lab01secc.desabpd.popular.local:8005/sap/opu/odata/sap/Z_GW_BUSCAR_DATOS_EMP_SRV/ExTDatosEmpSet",
		// "?$filter=(ImPernr eq '35994')", "WS_BPM", "C5dXfg3=d7>GL>P"));
	}

}