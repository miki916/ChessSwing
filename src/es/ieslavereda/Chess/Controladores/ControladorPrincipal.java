package es.ieslavereda.Chess.Controladores;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import es.ieslavereda.Chess.model.common.Celda;
import es.ieslavereda.Chess.model.common.Coordenada;
import es.ieslavereda.Chess.model.common.GestionPiezasEliminadas;
import es.ieslavereda.Chess.model.common.JPTablero;
import es.ieslavereda.Chess.model.common.Movimientos;
import es.ieslavereda.Chess.model.common.Pawn;
import es.ieslavereda.Chess.model.common.Pieza;
import vista.JPEliminadas;
import vista.JPMovimientos;
import vista.JPTurno;
import vista.VistaPrincipal;

public class ControladorPrincipal implements ActionListener {
	
	private VistaPrincipal vistaPrincipal;
	private Pieza piezaSeleccionada;
	private JPTurno vistaTurno;
	private GestionPiezasEliminadas eliminadas;
	private JPMovimientos vistaMovimientos;
	private DefaultListModel<Movimientos> dlm;
	
	
	public ControladorPrincipal(VistaPrincipal vista) {
		
		this.vistaPrincipal = vista;
		
		
		inicializar();
		
		
	}


	private void inicializar() {
		// TODO Auto-generated method stub
		vistaTurno = vistaPrincipal.getPanelTurno();
		eliminadas = new ControladorEliminadas(vistaPrincipal.getPanelEliminadas());
		vistaMovimientos = vistaPrincipal.getPanelMovimientos();
		dlm = new DefaultListModel<Movimientos>();
		
		vistaMovimientos.getList().setModel(dlm);
		
		Component[] componentes = vistaPrincipal.getPanelTablero().getComponents();
		
		for(Component c : componentes) {
			
			if(c instanceof Celda) {
				
				((Celda) c).addActionListener(this);
				((Celda) c).setActionCommand("Celda");
				
			}
		}
		
		vistaMovimientos.getBtnAnterior().addActionListener(this);
		vistaMovimientos.getBtnSiguiente().addActionListener(this);
		
		vistaMovimientos.getBtnAnterior().setActionCommand("Anterior");
		vistaMovimientos.getBtnSiguiente().setActionCommand("Siguiente");
		
		
		
		
	}
	
	public void go() {
		
		vistaPrincipal.setVisible(true);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand();
		
		if(command.equals("Celda")){
			
			comprobarMovimientos((Celda) e.getSource());		
			
		}else if(command.equals("Anterior")) {
			
			
			
		}else if(command.equals("Siguiente")) {
			
			
		}
		
		
	}


	private void comprobarMovimientos(Celda celda) {
		
	
		if(piezaSeleccionada == null) {
			
			movimientosSinSeleccionarPieza(celda);
			
		}else {
			
			movimientoSeleccionandoPieza(celda);
			
		}
		
		
	}


	private void movimientoSeleccionandoPieza(Celda celda) {
		// TODO Auto-generated method stub
	
		JPTablero tablero =  vistaPrincipal.getPanelTablero();
		Movimientos mov = null;
		
		if(!piezaSeleccionada.getNextMovements().contains(tablero.getCoordenadaOfCelda(celda))) {
			
			JOptionPane.showMessageDialog(vistaPrincipal, "Debes seleccionar una celda valida");
			
		
			
		}else {
			
			Coordenada origen = piezaSeleccionada.getPosicion();
			Coordenada destino = tablero.getCoordenadaOfCelda(celda);

			desmarcarPosiblesDestinos();
			
			if(celda.contienePieza()) {
				
				if(tablero.getCoordenadaOfCelda(celda).getRow() == 8 || tablero.getCoordenadaOfCelda(celda).getRow() == 1 
					&& piezaSeleccionada instanceof Pawn) 
					
					mov = new Movimientos(origen, destino , Movimientos.RISE_KILLING , celda.getPieza() , null , piezaSeleccionada);
									
				else 
					
					mov = new Movimientos(origen, destino , Movimientos.KILL , celda.getPieza() , null , null);
				
				
				eliminadas.añadirPieza(celda.getPieza());
						
			}else {
				
				if(tablero.getCoordenadaOfCelda(celda).getRow() == 8 || tablero.getCoordenadaOfCelda(celda).getRow() == 1 
					&& piezaSeleccionada instanceof Pawn)
						
					mov = new Movimientos(origen, destino , Movimientos.RISE , null , null , piezaSeleccionada);
				
				else  
					
					mov = new Movimientos(origen, destino , Movimientos.NOT_KILL , null , null , null);

					
			}
			dlm.addElement(mov);
			
			piezaSeleccionada.moveTo(tablero.getCoordenadaOfCelda(celda));
			
			if(mov.getNumeroMovimiento() == Movimientos.RISE || mov.getNumeroMovimiento() == Movimientos.RISE_KILLING) {
				
				mov.setFichaGenerada(celda.getPieza());
				
			}
			piezaSeleccionada = null;
			vistaPrincipal.getPanelTurno().getLblSeleccion().setIcon(null);

			vistaTurno.cambiarTurno();
			
		}
		
		
		
		
			
		
	}


	private void movimientosSinSeleccionarPieza(Celda celda) {
		// TODO Auto-generated method stub
	
		if(!celda.contienePieza()) {
			
			JOptionPane.showMessageDialog(vistaPrincipal, "Debes seleccionar una celda con pieza");
			
		}else if(celda.getPieza().getColor() != vistaTurno.getTurno()) {
			
			JOptionPane.showMessageDialog(vistaPrincipal, "Debes seleccionar pieza de tu color");
			
		}else if(celda.getPieza().getNextMovements().size() == 0){
			
			JOptionPane.showMessageDialog(vistaPrincipal, "Esta pieza no tiene movimientos posibles");

		}else {
			
			piezaSeleccionada = celda.getPieza();
			
			resaltarMovimientos();
			mostrarSeleccionada();

		}
		
		
	}
	
	


	private void resaltarMovimientos() {
		
		for(Coordenada c : piezaSeleccionada.getNextMovements()) {
			
			if(vistaPrincipal.getPanelTablero().getCeldaAt(c).contienePieza()) {
				
				vistaPrincipal.getPanelTablero().getCeldaAt(c).resaltar(Celda.colorBordeCeldaComer, 1);
				
			}else {
				
				vistaPrincipal.getPanelTablero().getCeldaAt(c).resaltar(Celda.colorBordeCelda, 1);
				
			}
			
		}
		
	}
	
	private void desmarcarPosiblesDestinos() {
		
		Set<Coordenada> posiblesMovimientos = piezaSeleccionada.getNextMovements();
		
		JPTablero tablero =  vistaPrincipal.getPanelTablero();

		for (Coordenada cord : posiblesMovimientos) {
			Celda celda = tablero.getCeldaAt(cord);

			celda.resaltar(java.awt.Color.GRAY, 1);

		}

	}
	
	
	private void mostrarSeleccionada() {
		
		vistaTurno.getLblSeleccion().setIcon(new ImageIcon(JPTurno.class.getResource(("/es/ieslavereda/Chess/recursos/" + piezaSeleccionada.getFileName()))));
		
		
	}
		
		
	
	
}
