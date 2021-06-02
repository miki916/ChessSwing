package es.ieslavereda.Chess.model.common;

import java.util.LinkedHashSet;
import java.util.Set;

public class Knight extends Pieza{

	public Knight(Color color, Coordenada posicion, JPTablero tablero) {
		super(posicion, tablero);
		
		if(color==Color.WHITE)
			tipo = Tipo.WHITE_KNIGHT;
		else
			tipo = Tipo.BLACK_KNIGHT;
		
		colocate(posicion);
		
	}

	@Override
	public Set<Coordenada> getNextMovements() {
		
		Set<Coordenada> lista = new LinkedHashSet<Coordenada>();
		
		addCoordenada(posicion.up().up().left(),lista);
		addCoordenada(posicion.up().up().right(),lista);
		addCoordenada(posicion.down().down().left(),lista);
		addCoordenada(posicion.down().down().right(),lista);
		addCoordenada(posicion.left().left().up(),lista);
		addCoordenada(posicion.left().left().down(),lista);
		addCoordenada(posicion.right().right().up(),lista);
		addCoordenada(posicion.right().right().down(),lista);
		
		return lista;
	}
	
	private void addCoordenada(Coordenada c, Set<Coordenada> lista) {
		if (tablero.contiene(c)) {
			if (tablero.getCeldaAt(c).contienePieza()) {
				if (tablero.getCeldaAt(c).getPieza().getColor() != getColor())
					lista.add(c);
			} else {
				lista.add(c);
			}
		}
	}
	
	private static void addCelda(Coordenada c, Set<Coordenada> posicionesCandidatas, Pieza p) {
		JPTablero t = p.tablero;
		if (t.contiene(c)	&& (t.getCeldaAt(c).getPieza() == null || t.getCeldaAt(c).getPieza().getColor() != p.getColor()))
			posicionesCandidatas.add(c);
	}
}
