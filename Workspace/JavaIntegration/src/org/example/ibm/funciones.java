package org.example.ibm;

import com.lombardisoftware.core.TWObject;

public class funciones {
	
	public String getVariable(TWObject objeto) {
		return 	objeto.getTWClassName().toString();
	}
	
	public String sumarNumeros(Integer numero1,Integer numero2) {
		return "Suma es: "+ (numero1+numero2);
	}
	
	public String reverseText(String texto) {
		StringBuilder sb = new StringBuilder(texto);
		return sb.reverse().toString();
	}
	
	
	

}
