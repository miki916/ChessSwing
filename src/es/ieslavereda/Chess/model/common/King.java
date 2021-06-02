package es.ieslavereda.Chess.model.common;

import java.util.LinkedHashSet;
import java.util.Set;

public class King extends Pieza {

	public King(Color color, Coordenada posicion, JPTablero tablero) {
		super(posicion, tablero);
		if (color == Color.WHITE)
			tipo = Tipo.WHITE_KING;
		else
			tipo = Tipo.BLACK_KING;
		
		colocate(posicion);
	}

	@Override
	public Set<Coordenada> getNextMovements() {

		Set<Coordenada> lista = new LinkedHashSet<Coordenada>();

		addCoordenada(posicion.up(), lista);
		addCoordenada(posicion.right(), lista);
		addCoordenada(posicion.down(), lista);
		addCoordenada(posicion.left(), lista);
		addCoordenada(posicion.diagonalUpRight(), lista);
		addCoordenada(posicion.diagonalUpLeft(), lista);
		addCoordenada(posicion.diagonalDownRight(), lista);
		addCoordenada(posicion.diagonalDownLeft(), lista);

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
