package es.ieslavereda.Chess.model.common;

import java.util.LinkedHashSet;
import java.util.Set;

public class Pawn extends Pieza {

	public Pawn(Color color, Coordenada posicion, JPTablero tablero) {
		super(posicion, tablero);
		
		if(color==Color.WHITE)
			tipo = Tipo.WHITE_PAWN;
		else
			tipo = Tipo.BLACK_PAWN;
		
		colocate(posicion);
		
	}
	@Override
	public Set<Coordenada> getNextMovements() {

		Set<Coordenada> lista = new LinkedHashSet<Coordenada>();

		
		
		if(getColor()==Color.BLACK) {
			
			coordenadaNegras(lista);
		
		}else if (getColor()==Color.WHITE){
			
			coordenadaBlancas(lista);
			
		}
		
	
		
		return lista;
	}
	
		//POSIBLES MOVIEMIENTOS DE LA PIEZA (TANTO NEGRAS COMO BLANCAS)
	
	private void coordenadaBlancas(Set lista) {
		
		addCoordenada(posicion.up(), lista);
		
		if(posicion.getRow() == 2) {
			
			addCoordenada(posicion.up().up(), lista);
			
		}
		
		addCoordenadaDiagonal(posicion.diagonalUpLeft(),lista);
		addCoordenadaDiagonal(posicion.diagonalUpRight(),lista);
		
	}
	
	private void coordenadaNegras(Set lista) {
		
		addCoordenada(posicion.down(), lista);
		
		if(posicion.getRow() == 7) {
			
			addCoordenada(posicion.down().down(), lista);
			
		}
		
		
		addCoordenadaDiagonal(posicion.diagonalDownLeft(),lista);
		addCoordenadaDiagonal(posicion.diagonalDownRight(),lista);
		
		
	}
	
		//COMPRUEBO QUE SE PUEDEN AÑADIR LA COORDENADAS Y LAS AÑADO 
	
	private void addCoordenada(Coordenada c, Set<Coordenada> lista) {
		if (tablero.contiene(c)) {
			
			if (!tablero.getCeldaAt(c).contienePieza()) {
			
				lista.add(c);
			}
		}
	}
	
	private void addCoordenadaDiagonal(Coordenada c, Set<Coordenada> lista) {
		
		if (tablero.contiene(c)) {	
			if (tablero.getCeldaAt(c).contienePieza()) {
				if(tablero.getCeldaAt(c).getPieza().getColor() != getColor())
					lista.add(c);
			}
		}
	}
	
	
	public void moveTo(Coordenada c) {
		super.moveTo(c);
		
		if(getColor()==Color.WHITE && posicion.getRow()==8) {
			
			tablero.eliminarPieza(this);
			tablero.getBlancas().add(new Queen(Color.WHITE,c,tablero));
			
		} else if (getColor()==Color.BLACK && posicion.getRow()==1){
			
			tablero.eliminarPieza(this);
			tablero.getNegras().add(new Queen(Color.BLACK,c,tablero));
			
		}
		
	}
	
	private static void addCelda(Coordenada c, Set<Coordenada> posicionesCandidatas, Pieza p) {
		JPTablero t = p.tablero;
		if (t.contiene(c)	&& (t.getCeldaAt(c).getPieza() == null || t.getCeldaAt(c).getPieza().getColor() != p.getColor()))
			posicionesCandidatas.add(c);
	}

}
