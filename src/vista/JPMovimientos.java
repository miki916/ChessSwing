package vista;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import es.ieslavereda.Chess.model.common.Movimientos;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class JPMovimientos extends JPanel {
	
	private JList<Movimientos> list;
	private JButton btnAnterior;
	private JButton btnSiguiente;
	

	/**
	 * Create the panel.
	 */
	public JPMovimientos() {
		
		setBorder(new TitledBorder(null, "Movimientos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		setBounds(181, 11, 195, 256);
		JPanel panelBotones = new JPanel();
		panelBotones.setBounds(10, 225, 175, 29);
		add(panelBotones);
		panelBotones.setLayout(new GridLayout(1, 0, 7, 0));
		
		btnAnterior = new JButton("<");
		panelBotones.add(btnAnterior);
		
		btnSiguiente = new JButton(">");
		panelBotones.add(btnSiguiente);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 175, 199);
		add(scrollPane);
		
		list = new JList<Movimientos>();
		scrollPane.setViewportView(list);

	}


	public JList<Movimientos> getList() {
		return list;
	}


	public JButton getBtnAnterior() {
		return btnAnterior;
	}


	public JButton getBtnSiguiente() {
		return btnSiguiente;
	}
	
	
	
}
