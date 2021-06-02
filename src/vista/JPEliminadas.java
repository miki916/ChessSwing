package vista;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.border.TitledBorder;
import java.awt.FlowLayout;

public class JPEliminadas extends JPanel {

	private JPanel panelBlancas;
	private JPanel panelNegras;

	/**
	 * Create the panel.
	 */
	public JPEliminadas() {
		setLayout(new GridLayout(2, 0, 4, 4));
		setBounds(new Rectangle(0, 0, 500, 500));
		panelBlancas = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBlancas.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelBlancas.setBorder(new TitledBorder(null, "Blancas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panelBlancas);
		
		panelNegras = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelNegras.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panelNegras.setBorder(new TitledBorder(null, "Negras", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panelNegras);

	}
	
	public JPanel getPanelBlancas() {
		return panelBlancas;
	}

	public JPanel getPanelNegras() {
		return panelNegras;
	}

	

}
