package org.capgemini.project;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SimpleSoapClient {

	public static void main(String args[]) throws IOException, NoSuchAlgorithmException, KeyManagementException {

		String url = "https://10.96.47.219:9001/eai_esn/start.swe?SWEExtSource=WebService&SWEExtCmd=Execute&WSSOAP=1";

		String xml = "<soapenv:Envelope xmlns:soapenv=http://schemas.xmlsoap.org/soap/envelope/ xmlns:cus=http://siebel.com/CustomUI>\r\n" + 
				"     <UsernameToken xmlns=http://siebel.com/webservices>EAILINEA</UsernameToken>\r\n" + 
				"     <PasswordText xmlns=http://siebel.com/webservices>EAILINEA</PasswordText>\r\n" + 
				"     <SessionType xmlns=http://siebel.com/webservices>None</SessionType>\r\n" + 
				"   <soapenv:Body>\r\n" + 
				"      <cus:ActualizarCliente_Input>\r\n" + 
				"         <cus:numeroIdentificacion>9876543210</cus:numeroIdentificacion>\r\n" + 
				"         <cus:tipoIdentificacion>Cedula</cus:tipoIdentificacion>\r\n" + 
				"         <cus:codigoCIF>9876543210</cus:codigoCIF>\r\n" + 
				"         <cus:documentoOnbase></cus:documentoOnbase>\r\n" + 
				"         <cus:fechaVencimiento>2021/08/19</cus:fechaVencimiento>\r\n" + 
				"         <cus:estadoValidacion></cus:estadoValidacion>\r\n" + 
				"         <cus:codigoRiesgo></cus:codigoRiesgo>\r\n" + 
				"      </cus:ActualizarCliente_Input>\r\n" + 
				"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>\r\n" + 
				"";
		
	    // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };
 
        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
 
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	
		String responseF = callSoapService(url,xml);
		System.out.println(responseF);
	}

	static String callSoapService(String url,String soapRequest) {
		try {
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(soapRequest);
			wr.flush();
			wr.close();
			String responseStatus = con.getResponseMessage();
			System.out.println(responseStatus);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

		
			String finalvalue = response.toString();
			finalvalue = finalvalue.substring(finalvalue.indexOf("<response>") + 10, finalvalue.indexOf("</response>"));
			return finalvalue;
		
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}