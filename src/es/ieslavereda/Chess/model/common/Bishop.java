package es.ieslavereda.Chess.model.common;

import java.util.LinkedHashSet;
import java.util.Set;

public class Bishop extends Pieza {

	public Bishop(Color color,Coordenada posicion, JPTablero tablero) {
		super(posicion, tablero);
		
		if(color==Color.WHITE)
			tipo = Tipo.WHITE_BISHOP;
		else
			tipo = Tipo.BLACK_BISHOP;
		
		colocate(posicion);
	}

	@Override
	public Set<Coordenada> getNextMovements() {
		// TODO Auto-generated method stub
		
		return getNextMovements(this);
	}
	
	public static Set<Coordenada> getNextMovements(Pieza p){
		
		JPTablero t = p.tablero;
		Set<Coordenada> lista = new LinkedHashSet<Coordenada>();

		Coordenada c ;
			
		boolean ocupada;
		
		// UP LEFT
		
		c =  p.posicion.diagonalUpLeft();
		ocupada = false;
	
		while(t.contiene(c) && ocupada == false){
			
			if (t.getCeldaAt(c).contienePieza()) {	
				if (t.getCeldaAt(c).getPieza().getColor() != p.getColor()) {
					
					lista.add(c);
					ocupada = true;
					
				}else ocupada = true;
				
			}else {
				
				lista.add(c);
				c = c.diagonalUpLeft();	
			}
		}
				
		// UP RIGHT
		
		c =  p.posicion.diagonalUpRight();
		ocupada = false;
		
		while(t.contiene(c) && ocupada == false){
			
			if (t.getCeldaAt(c).contienePieza()) {
				if (t.getCeldaAt(c).getPieza().getColor() != p.getColor()) {
					
					lista.add(c);
					ocupada = true;
					
				}else ocupada = true;
				
			}else {
				
				lista.add(c);
				c = c.diagonalUpRight();	
			}
		}
		
		// DOWN LEFT
		
		c =  p.posicion.diagonalDownLeft();
		ocupada = false;
		
		while(t.contiene(c) && ocupada == false){
			
			if (t.getCeldaAt(c).contienePieza()) {
				if (t.getCeldaAt(c).getPieza().getColor() != p.getColor()) {
					
					lista.add(c);
					ocupada = true;
					
				}else ocupada = true;
				
			}else {
				
				lista.add(c);
				c = c.diagonalDownLeft();	
			}
		}
		
		
		// DOWN RIGHT
		
		c =  p.posicion.diagonalDownRight();
		ocupada = false;
		
		while(t.contiene(c) && ocupada == false){
			
			if (t.getCeldaAt(c).contienePieza()) {
				if (t.getCeldaAt(c).getPieza().getColor() != p.getColor()) {
					
					lista.add(c);
					ocupada = true;
					
				}else ocupada = true;
				
			}else {
				
				lista.add(c);
				c = c.diagonalDownRight();	
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
