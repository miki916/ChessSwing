package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.ieslavereda.Chess.model.common.JPTablero;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class VistaPrincipal extends JFrame {

	private JPanel contentPane;
	private JPEliminadas panelEliminadas;
	private JPTurno panelTurno;
	private JPTablero panelTablero;
	private JPMovimientos panelMovimientos;
	private JMenuBar menuBar;
	private JButton btnPreferencias;
	private JButton btnSalir;

	
		/**
	 * Create the frame.
	 */
	public VistaPrincipal() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100,900, 562);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnFile);
		
		JButton btnExportar = new JButton("Exportar");
		mnFile.add(btnExportar);
		
		JButton btnImportar = new JButton("Importar");
		mnFile.add(btnImportar);
		
		btnSalir = new JButton("Salir");
		mnFile.add(btnSalir);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		btnPreferencias = new JButton("Preferencias");
		mnEdit.add(btnPreferencias);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelTablero = new JPTablero();
		panelTablero.setBounds(0, 0, 500, 500);
		contentPane.add(panelTablero);

		JPanel panelDatos = new JPanel();
		panelDatos.setBounds(498, 0, 386, 500);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);
			
		panelEliminadas = new JPEliminadas();
		panelEliminadas.setBounds(10, 266, 366, 223);
		panelDatos.add(panelEliminadas);
		
		panelTurno = new JPTurno();
		panelTurno.setBounds(10, 11, 161, 256);
		panelDatos.add(panelTurno);
		
		panelMovimientos = new JPMovimientos();
		panelMovimientos.setBounds(181, 11, 195, 256);
		panelDatos.add(panelMovimientos);
	
		
	}
	
	private Image getScaledImage(Image srcImg, int size){
		
		int h = size, w = size;
		
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}

	public JPMovimientos getPanelMovimientos() {
		
		return panelMovimientos;
			
	}


	public JPanel getContentPane() {
		
		return contentPane;
		
	}


	public JPTurno getPanelTurno() {
		
		return panelTurno;
		
	}


	public JPTablero getPanelTablero() {
		
		return panelTablero;
		
	}

	
	public JPEliminadas getPanelEliminadas() {
		
		return panelEliminadas;
		
	}
}
