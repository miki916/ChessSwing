package vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;

import es.ieslavereda.Chess.model.common.Color;


import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class JPTurno extends JPanel {
	
	private JPanel panelColor;
	private JPanel panelSeleccionada;
	private Color turno;
	private JLabel lblTurno;
	private JLabel lblSeleccion;

	/**
	 * Create the panel.
	 */
	public JPTurno() {
		setBorder(new TitledBorder(null, "Turno", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(2, 0, 0, 0));
		
		panelColor = new JPanel();
		add(panelColor);
		panelColor.setLayout(new BorderLayout(0, 0));
		
		JLabel lblColorTitle = new JLabel("Color");
		lblColorTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelColor.add(lblColorTitle, BorderLayout.NORTH);
		
		lblTurno = new JLabel("");
		lblTurno.setHorizontalAlignment(SwingConstants.CENTER);
		turno = Color.WHITE;
			
		lblTurno.setIcon(new ImageIcon(JPTurno.class.getResource("/es/ieslavereda/Chess/recursos/b_peon.gif")));
		panelColor.add(lblTurno, BorderLayout.CENTER);
		
		panelSeleccionada = new JPanel();
		add(panelSeleccionada);
		panelSeleccionada.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSeleccionadoTitle = new JLabel("Seleccionado");
		lblSeleccionadoTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelSeleccionada.add(lblSeleccionadoTitle, BorderLayout.NORTH);
		
		lblSeleccion = new JLabel("");
		lblSeleccion.setHorizontalAlignment(SwingConstants.CENTER);
		panelSeleccionada.add(lblSeleccion, BorderLayout.CENTER);
		

	}
	
	
	
	public JLabel getLblTurno() {
		return lblTurno;
	}
	
	
	


	public JLabel getLblSeleccion() {
		return lblSeleccion;
	}



	public Color getTurno() {
		return turno;
	}



	public void cambiarTurno() {
		
		turno = Color.values()[(turno.ordinal()+1)%2];
		
		if(turno==Color.WHITE) {
			
			lblTurno.setIcon(new ImageIcon(JPTurno.class.getResource(("/es/ieslavereda/Chess/recursos/b_peon.gif"))));
			
		}else {
			
			lblTurno.setIcon(new ImageIcon(JPTurno.class.getResource(("/es/ieslavereda/Chess/recursos/n_peon.gif"))));

		}
		
		
	}

}
