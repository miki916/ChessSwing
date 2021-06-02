package es.ieslavereda.Chess.model.common;

import java.util.LinkedHashSet;
import java.util.Set;

public class Rook extends Pieza {

	public Rook(Color color, Coordenada posicion, JPTablero tablero) {
		super(posicion, tablero);
		// TODO Auto-generated constructor stub
		
		if(color==Color.WHITE)
			tipo = Tipo.WHITE_ROOK;
		else
			tipo = Tipo.BLACK_ROOK;
		
		colocate(posicion);
		
	}

	@Override
	public Set<Coordenada> getNextMovements() {
		
		return getNextMovements(this);
	}
	
	public static Set<Coordenada> getNextMovements(Pieza p){
		
		JPTablero t = p.tablero;
		Set<Coordenada> lista = new LinkedHashSet<>();
		Coordenada c;
				
		boolean ocupada;
		
		// UP 
		
		c =  p.posicion.up();
		ocupada = false;
	
		while(t.contiene(c) && ocupada == false){
			
			if (t.getCeldaAt(c).contienePieza()) {	
				if (t.getCeldaAt(c).getPieza().getColor() != p.getColor()) {
					
					lista.add(c);
					ocupada = true;
					
				}else ocupada = true;
				
			}else {
				
				lista.add(c);
				c = c.up();
			}
		}
		
		// RIGHT
		
		c =  p.posicion.right();
		ocupada = false;
	
		while(t.contiene(c) && ocupada == false){
			
			if (t.getCeldaAt(c).contienePieza()) {	
				if (t.getCeldaAt(c).getPieza().getColor() != p.getColor()) {
					
					lista.add(c);
					ocupada = true;
					
				}else ocupada = true;
				
			}else {
				
				lista.add(c);
				c = c.right();
			}
		}
		
		// DOWN
		
		c =  p.posicion.down();
		ocupada = false;
	
		while(t.contiene(c) && ocupada == false){
			
			if (t.getCeldaAt(c).contienePieza()) {	
				if (t.getCeldaAt(c).getPieza().getColor() != p.getColor()) {
					
					lista.add(c);
					ocupada = true;
					
				}else ocupada = true;
				
			}else {
				
				lista.add(c);
				c = c.down();
			}
		}
		
		
		// Left
		
		c =  p.posicion.left();
		ocupada = false;
	
		while(t.contiene(c) && ocupada == false){
			
			if (t.getCeldaAt(c).contienePieza()) {	
				if (t.getCeldaAt(c).getPieza().getColor() != p.getColor()) {
					
					lista.add(c);
					ocupada = true;
					
				}else ocupada = true;
				
			}else {
				
				lista.add(c);
				c = c.left();
			}
		}
		
		return lista;
	}
	
	private static void addCelda(Coordenada c, Set<Coordenada> posicionesCandidatas, Pieza p) {
		JPTablero t = p.tablero;
		if (t.contiene(c)	&& (t.getCeldaAt(c).getPieza() == null || t.getCeldaAt(c).getPieza().getColor() != p.getColor()))
			posicionesCandidatas.add(c);
	}
	
}
