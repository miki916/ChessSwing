package main;

import es.ieslavereda.Chess.Controladores.ControladorPrincipal;
import vista.VistaPrincipal;

public class Test {

	public static void main(String[] args) {
		
		VistaPrincipal vista = new VistaPrincipal();
		
		ControladorPrincipal cp = new ControladorPrincipal(vista);
		
		cp.go();
		
		
	}
	
}
