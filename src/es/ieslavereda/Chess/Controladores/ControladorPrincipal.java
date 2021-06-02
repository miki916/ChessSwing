package es.ieslavereda.Chess.Controladores;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import es.ieslavereda.Chess.config.MyConfig;
import es.ieslavereda.Chess.model.common.Celda;
import es.ieslavereda.Chess.model.common.Color;
import es.ieslavereda.Chess.model.common.Coordenada;
import es.ieslavereda.Chess.model.common.GestionPiezasEliminadas;
import es.ieslavereda.Chess.model.common.JPTablero;
import es.ieslavereda.Chess.model.common.Movimientos;
import es.ieslavereda.Chess.model.common.Pawn;
import es.ieslavereda.Chess.model.common.Pieza;
import vista.JPEliminadas;
import vista.JPMovimientos;
import vista.JPTurno;
import vista.Preferencias;
import vista.VistaPrincipal;

public class ControladorPrincipal implements ActionListener, MouseListener, Serializable {
	
	private VistaPrincipal vistaPrincipal;
	private Pieza piezaSeleccionada;
	private JPTurno vistaTurno;
	private GestionPiezasEliminadas eliminadas;
	private JPMovimientos vistaMovimientos;
	private DefaultListModel<Movimientos> dlm;
	private Deque<Movimientos> stack;
	private Preferencias jfPreferencias;


	
	
	public ControladorPrincipal(VistaPrincipal vista) {
		
		this.vistaPrincipal = vista;
		
		stack = new ArrayDeque<Movimientos>();

		
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
		
		vistaPrincipal.getPanelMovimientos().getList().addMouseListener(this);

		// Añadimos los ActionListener
		vistaPrincipal.getBtnPreferencias().addActionListener(this);
		vistaPrincipal.getBtnSalir().addActionListener(this);
	
		vistaPrincipal.getBtnExportar().addActionListener(this);
		vistaPrincipal.getBtnImportar().addActionListener(this);	
		
		vistaMovimientos.getBtnAnterior().addActionListener(this);
		vistaMovimientos.getBtnSiguiente().addActionListener(this);
		
		vistaPrincipal.getBtnPreferencias().setActionCommand("Abrir preferencias");
		vistaPrincipal.getBtnSalir().setActionCommand("Nueva partida");
	
		vistaPrincipal.getBtnExportar().setActionCommand("Guardar");
		vistaPrincipal.getBtnImportar().setActionCommand("Cargar");	
		
		
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
			
		}else if (command.equals("Abrir preferencias")) {
			
			abrirPreferencias();
			
		} else if (command.equals("Guardar")) {
			
			guardar();
			
		} else if (command.equals("Cargar")) {
			
			cargar();
			
		}  else if (command.equals("Cambiar Color Celda Blanca")) {
			
			cambiarColorCeldaBlanca();
			
		} else if (command.equals("Cambiar Color Celda Negra")) {
			
			cambiarColorCeldaNegra();
			
		} else if (command.equals("Cambiar Color Borde Celda")) {
			
			cambiarColorBordeNormal();
			
		} else if (command.equals("Cambiar Color Borde Celda Comer")) {
			
			cambiarColorBordeMatando();
			
		} else if (command.equals("Nueva partida")) {
			
			if (dlm.size() > 0) {
				
				reiniciarPartida();
				
			}

		}else if(command.equals("Anterior")) {
			
			if (piezaSeleccionada != null) {
				desmarcarPosiblesDestinos();
			}
			previousMovement();
			piezaSeleccionada = null;
			
			
		}else if(command.equals("Siguiente")) {
			
			if (piezaSeleccionada != null) {
				desmarcarPosiblesDestinos();
			}
			nextMovement();
			piezaSeleccionada = null;
			
		}
		
		
	}


	private void reiniciarPartida() {
		// TODO Auto-generated method stub
		
		do {
			previousMovement();
			for (Celda c : vistaPrincipal.getPanelTablero().getTablero().values()) {
				c.setEnabled(true);
			}

		} while (dlm.size() > 0);
		stack.removeAll(stack);
		
	}


	private void cargar() {
		// TODO Auto-generated method stub
		
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new FileNameExtensionFilter("Formulario file", "app", "miapp"));
		int opcion = jfc.showOpenDialog(vistaPrincipal);
		if (opcion == JFileChooser.APPROVE_OPTION) {
			if (dlm.size() > 0) {
				reiniciarPartida();
			}

			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(jfc.getSelectedFile()))) {
				ArrayList<Movimientos> movimientos = (ArrayList<Movimientos>) ois.readObject();
				int m = (int) ois.readObject();

				
				for (Movimientos movimiento:movimientos) {
					if(movimiento.getTipoAccion()==Movimientos.RISE||movimiento.getTipoAccion()==Movimientos.RISE_KILLING) {
						movimiento.getFichaGenerada().setTablero(vistaPrincipal.getPanelTablero());
						movimiento.getFichaPeon().setTablero(vistaPrincipal.getPanelTablero());
					}
					nextMovement(movimiento);
				}
				
				while(Movimientos.getNumero()>m)
					previousMovement();
				
				

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
	}


	private void guardar() {
		// TODO Auto-generated method stub
		
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new FileNameExtensionFilter("Formulario file", "app", "miapp"));
		int opcion = jfc.showSaveDialog(vistaPrincipal);
		if (opcion == JFileChooser.APPROVE_OPTION) {

			ArrayList<Movimientos> movimientos = new ArrayList<Movimientos>();
			int m = Movimientos.getNumero();

			for (int i = 0; i < dlm.size(); i++) {
				if(dlm.get(i).getTipoAccion()==Movimientos.RISE|dlm.get(i).getTipoAccion()==Movimientos.RISE_KILLING) {
					dlm.get(i).getFichaGenerada().setTablero(null);
					dlm.get(i).getFichaPeon().setTablero(null);
				}
				movimientos.add(dlm.get(i));
			}
			for (Movimientos mov : stack) {
				movimientos.add(mov);
			}

			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(jfc.getSelectedFile()))) {
				oos.writeObject(movimientos);
				oos.writeObject(m);
				System.out.println(movimientos);
			} catch (FileNotFoundException o) {
				o.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}


	private void abrirPreferencias() {
		

		jfPreferencias = new Preferencias();

		jfPreferencias.setVisible(true);

		jfPreferencias.getBtnColorCeldaBlanca().addActionListener(this);
		jfPreferencias.getBtnColorCeldaNegra().addActionListener(this);
		jfPreferencias.getBtnColorBordeCelda().addActionListener(this);
		jfPreferencias.getBtnColorBordeCeldaComer().addActionListener(this);

		jfPreferencias.getBtnColorCeldaBlanca().setActionCommand("Cambiar Color Celda Blanca");
		jfPreferencias.getBtnColorCeldaNegra().setActionCommand("Cambiar Color Celda Negra");
		jfPreferencias.getBtnColorBordeCelda().setActionCommand("Cambiar Color Borde Celda");
		jfPreferencias.getBtnColorBordeCeldaComer().setActionCommand("Cambiar Color Borde Celda Comer");
		
	}
	
	private void cambiarColorCeldaBlanca() {

		java.awt.Color color = JColorChooser.showDialog(jfPreferencias.getBtnColorCeldaBlanca(),
				"Selecciona color de las celdas blancas", jfPreferencias.getBtnColorCeldaBlanca().getBackground());

		if (color != null) {
			jfPreferencias.getBtnColorCeldaBlanca().setBackground(color);
			MyConfig.getInstancia().setWhiteCellColor(color);
			Celda.colorCeldaBlanca = color;
			vistaPrincipal.getPanelTablero().repaint();

		}
	}

	private void cambiarColorCeldaNegra() {

		java.awt.Color color = JColorChooser.showDialog(jfPreferencias.getBtnColorCeldaNegra(),
				"Selecciona color de las celdas negras", jfPreferencias.getBtnColorCeldaNegra().getBackground());

		if (color != null) {
			jfPreferencias.getBtnColorCeldaNegra().setBackground(color);
			MyConfig.getInstancia().setBlackCellColor(color);
			Celda.colorCeldaNegra = color;
			vistaPrincipal.getPanelTablero().repaint();

		}
	}

	private void cambiarColorBordeNormal() {
		java.awt.Color color = JColorChooser.showDialog(jfPreferencias.getBtnColorBordeCelda(),
				"Selecciona color de los bordes de los posibles movimientos",
				jfPreferencias.getBtnColorBordeCelda().getBackground());

		if (color != null) {
			jfPreferencias.getBtnColorBordeCelda().setBackground(color);
			MyConfig.getInstancia().setBorderCell(color);
			Celda.colorBordeCelda = color;
			vistaPrincipal.getPanelTablero().repaint();
		}
	}

	private void cambiarColorBordeMatando() {
		java.awt.Color color = JColorChooser.showDialog(jfPreferencias.getBtnColorBordeCelda(),
				"Selecciona color de los bordes de los posibles movimientos matando",
				jfPreferencias.getBtnColorBordeCelda().getBackground());

		if (color != null) {
			jfPreferencias.getBtnColorBordeCeldaComer().setBackground(color);
			MyConfig.getInstancia().setBorderCellKill(color);
			Celda.colorBordeCeldaComer = color;
			vistaPrincipal.getPanelTablero().repaint();
		}
	}


	private void nextMovement() {
		// TODO Auto-generated method stub
		
		try {
			Movimientos m = stack.pop();
			

			nextMovement(m);
			
		} catch (NoSuchElementException ne) {
			JOptionPane.showMessageDialog(vistaPrincipal, "No hay movimientos para avanzar", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(vistaPrincipal, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}


	private void nextMovement(Movimientos m) throws Exception {
		// TODO Auto-generated method stub
		
		Coordenada origen, destino;
		dlm.addElement(m);

		origen = m.getOrigen();
		destino = m.getDestino();

		switch (m.getTipoAccion()) {
		case Movimientos.NOT_KILL:
			vistaPrincipal.getPanelTablero().getPiezaAt(origen).setPosicion(destino);
			vistaPrincipal.getPanelTablero().getCeldaAt(destino).setPieza(vistaPrincipal.getPanelTablero().getPiezaAt(origen));
			vistaPrincipal.getPanelTablero().getCeldaAt(origen).setPieza(null);

			break;

		case Movimientos.KILL:
			vistaPrincipal.getPanelTablero().getPiezaAt(origen).setPosicion(destino);
			vistaPrincipal.getPanelTablero().getCeldaAt(destino).setPieza(vistaPrincipal.getPanelTablero().getPiezaAt(origen));
			vistaPrincipal.getPanelTablero().getCeldaAt(origen).setPieza(null);
			if (m.getFicha().getColor() == Color.WHITE)
				vistaPrincipal.getPanelTablero().getBlancas().remove(m.getFicha());
			else
				vistaPrincipal.getPanelTablero().getNegras().remove(m.getFicha());
			eliminadas.añadirPieza(m.getFicha());
			break;
		case Movimientos.RISE:
			m.getFichaGenerada().setPosicion(destino);
			vistaPrincipal.getPanelTablero().getCeldaAt(destino).setPieza(m.getFichaGenerada());
			vistaPrincipal.getPanelTablero().getCeldaAt(origen).setPieza(null);

			if (m.getFichaPeon().getColor() == Color.WHITE) {
				vistaPrincipal.getPanelTablero().getBlancas().add(m.getFichaGenerada());
				vistaPrincipal.getPanelTablero().getBlancas().remove(m.getFichaPeon());
			} else {
				vistaPrincipal.getPanelTablero().getNegras().add(m.getFichaGenerada());
				vistaPrincipal.getPanelTablero().getNegras().remove(m.getFichaPeon());
			}
			break;
		case Movimientos.RISE_KILLING:
			vistaPrincipal.getPanelTablero().getCeldaAt(destino).setPieza(m.getFichaGenerada());
			vistaPrincipal.getPanelTablero().getCeldaAt(origen).setPieza(null);
			eliminadas.añadirPieza(m.getFicha());
			if (m.getFichaPeon().getColor() == Color.WHITE) {
				vistaPrincipal.getPanelTablero().getBlancas().add(m.getFichaGenerada());
				vistaPrincipal.getPanelTablero().getBlancas().remove(m.getFichaPeon());
				vistaPrincipal.getPanelTablero().getNegras().remove(m.getFicha());

			} else {
				vistaPrincipal.getPanelTablero().getBlancas().remove(m.getFicha());
				vistaPrincipal.getPanelTablero().getNegras().add(m.getFichaGenerada());
				vistaPrincipal.getPanelTablero().getNegras().remove(m.getFichaPeon());
			}
			break;

		default:
			throw new Exception("Error interno. Movimento desconocido");
		}

		vistaPrincipal.getPanelTurno().cambiarTurno();
		Movimientos.increaseNumberOfMovements();
		
		
	}

	
	private void previousMovement() {
		// TODO Auto-generated method stub
		
		try {

			Movimientos m = dlm.remove(dlm.getSize() - 1);
			stack.push(m);

			Coordenada origen, destino;
			destino = m.getDestino();
			origen = m.getOrigen();

			switch (m.getTipoAccion()) {
			case Movimientos.NOT_KILL:
				vistaPrincipal.getPanelTablero().getPiezaAt(destino).setPosicion(origen);
				vistaPrincipal.getPanelTablero().getCeldaAt(origen).setPieza(vistaPrincipal.getPanelTablero().getPiezaAt(destino));
				vistaPrincipal.getPanelTablero().getCeldaAt(destino).setPieza(null);

				break;

			case Movimientos.KILL:
				vistaPrincipal.getPanelTablero().getPiezaAt(destino).setPosicion(origen);
				m.getFicha().setPosicion(destino);
				vistaPrincipal.getPanelTablero().getCeldaAt(origen).setPieza(vistaPrincipal.getPanelTablero().getPiezaAt(destino));
				vistaPrincipal.getPanelTablero().getCeldaAt(destino).setPieza(m.getFicha());
				eliminadas.eliminarPieza(m.getFicha());

				if (m.getFicha().getColor() == Color.WHITE)
					vistaPrincipal.getPanelTablero().getBlancas().add(m.getFicha());
				else
					vistaPrincipal.getPanelTablero().getNegras().add(m.getFicha());

				break;

			case Movimientos.RISE:
				m.getFichaPeon().setPosicion(origen);
				vistaPrincipal.getPanelTablero().getCeldaAt(destino).setPieza(null);
				vistaPrincipal.getPanelTablero().getCeldaAt(origen).setPieza(m.getFichaPeon());

				if (m.getFichaPeon().getColor() == Color.WHITE) {
					vistaPrincipal.getPanelTablero().getBlancas().add(m.getFichaPeon());
					vistaPrincipal.getPanelTablero().getBlancas().remove(m.getFichaGenerada());
				} else {
					vistaPrincipal.getPanelTablero().getNegras().add(m.getFichaPeon());
					vistaPrincipal.getPanelTablero().getNegras().remove(m.getFichaGenerada());
				}
				break;
			case Movimientos.RISE_KILLING:
				m.getFichaPeon().setPosicion(origen);
				m.getFicha().setPosicion(destino);
				vistaPrincipal.getPanelTablero().getCeldaAt(origen).setPieza(m.getFichaPeon());
				vistaPrincipal.getPanelTablero().getCeldaAt(destino).setPieza(m.getFicha());
				eliminadas.eliminarPieza(m.getFicha());

				if (m.getFichaPeon().getColor() == Color.WHITE) {
					vistaPrincipal.getPanelTablero().getBlancas().remove(m.getFichaGenerada());
					vistaPrincipal.getPanelTablero().getBlancas().add(m.getFichaPeon());
					vistaPrincipal.getPanelTablero().getNegras().add(m.getFicha());
				} else {
					vistaPrincipal.getPanelTablero().getBlancas().add(m.getFicha());
					vistaPrincipal.getPanelTablero().getNegras().add(m.getFichaPeon());
					vistaPrincipal.getPanelTablero().getNegras().remove(m.getFichaGenerada());
				}
				break;
			default:
				throw new Exception("Tipo no conocido");

			}

			Movimientos.decreaseNumberOfMovements();
			vistaPrincipal.getPanelTurno().cambiarTurno();

		} catch (ArrayIndexOutOfBoundsException ae) {
			JOptionPane.showMessageDialog(vistaPrincipal, "No hay movimentos para deshacer", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {

			JOptionPane.showMessageDialog(vistaPrincipal, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
			
			if (vistaTurno.getTurno() == Color.WHITE) {
				if (vistaPrincipal.getPanelTablero().check(Color.BLACK)) {
					JOptionPane.showMessageDialog(vistaPrincipal, "El rey negro esta en jaque", "JAQUE",
							JOptionPane.DEFAULT_OPTION);
				}

			} else {
				
				if (vistaPrincipal.getPanelTablero().check(Color.WHITE)) {
					JOptionPane.showMessageDialog(vistaPrincipal, "El rey blanco esta en jaque", "JAQUE",
							JOptionPane.DEFAULT_OPTION);
				}

			}
			
			if (!vistaPrincipal.getPanelTablero().blackKingIsAlive()) {
				JOptionPane.showMessageDialog(vistaPrincipal, "Enhorabuena, han ganado las blancas", "VICTORIA",
						JOptionPane.DEFAULT_OPTION);
				finalPartida();
			} else if (!vistaPrincipal.getPanelTablero().whiteKingIsAlive()) {
				JOptionPane.showMessageDialog(vistaPrincipal, "Enhorabuena, han ganado las negras", "VICTORIA",
						JOptionPane.DEFAULT_OPTION);
				finalPartida();
			}
			

			vistaTurno.cambiarTurno();
			stack.removeAll(stack);

			
		}
		
		
		
		
			
		
	}


	private void finalPartida() {
		// TODO Auto-generated method stub
		for (Celda c : vistaPrincipal.getPanelTablero().getTablero().values()) {
			c.setEnabled(false);
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


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		

		Component c = e.getComponent();
		if (c == vistaPrincipal.getPanelMovimientos().getList()) {
			int index = vistaPrincipal.getPanelMovimientos().getList().getSelectedIndex();

			while (dlm.getSize() > index) {
				previousMovement();
			}

		}
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
		
		
	
	
}
