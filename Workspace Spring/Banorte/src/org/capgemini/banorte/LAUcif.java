package org.capgemini.banorte;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class LAUcif {
	
	private final String strmessage;

    public LAUcif(String message, String key1, String key2) {
		StringBuilder sb = new StringBuilder();
		sb.append(message);
		String key = key1 + key2;
		byte[] response = hmacSha256(key.getBytes(StandardCharsets.US_ASCII),message.getBytes(StandardCharsets.US_ASCII));
		StringBuilder hex = new StringBuilder();
		
		for (byte b : response) {
			hex.append(String.format("%02X", b));
		}
		sb.append("{S:\n{MDG:" + hex + "}}");
		this.strmessage = sb.toString();
	}

	static private byte[] hmacSha256(byte[] secretKey, byte[] message) {
		byte[] hmacSha256 = null;
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "HmacSHA256");
			mac.init(secretKeySpec);
			hmacSha256 = mac.doFinal(message);
		} catch (Exception e) {
			throw new RuntimeException("Fallo al Calcular HMAC-SHA256", e);
		}
		return hmacSha256;
	}

	/**
	 * @return the strmessage
	 */
	public String getStrmessage() {
		return strmessage;
	}
	
	
	public static void main(String [] args) {
		LAUcif lau = new LAUcif(args[0], args[1], args[2]);
		System.out.println(lau.getStrmessage());
	}
}
