package es.ieslavereda.Chess.Controladores;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.ieslavereda.Chess.model.common.Celda;
import es.ieslavereda.Chess.model.common.Color;
import es.ieslavereda.Chess.model.common.Coordenada;
import es.ieslavereda.Chess.model.common.GestionPiezasEliminadas;
import es.ieslavereda.Chess.model.common.Pieza;
import vista.JPEliminadas;
import vista.VistaPrincipal;

public class ControladorEliminadas implements GestionPiezasEliminadas, ActionListener {

	private JPEliminadas panelEliminadas;
	private HashMap<Pieza,JLabel> fichasEliminadas;
	
	public ControladorEliminadas(JPEliminadas panel) {
		
		panelEliminadas = panel;
		fichasEliminadas = new HashMap<Pieza,JLabel>();
	}
	

	@Override
	public void añadirPieza(Pieza pieza) {
		// TODO Auto-generated method stub
		
		if(pieza.getColor() == Color.WHITE) 
			
			añadir(panelEliminadas.getPanelBlancas(),pieza);
			
		else 
			añadir(panelEliminadas.getPanelNegras(),pieza);
		
		
		
	}
	

	private void añadir(JPanel panel, Pieza pieza) {
		// TODO Auto-generated method stub
		
		JLabel label = new JLabel();
		label.setOpaque(true);
		
		Image image = (new ImageIcon(VistaPrincipal.class.getResource("/es/ieslavereda/Chess/recursos/" + pieza.getFileName())).getImage());
		ImageIcon imageIconResized = new ImageIcon(getScaledImage(image,25));
		label.setIcon(imageIconResized);
		panel.add(label);
		fichasEliminadas.put(pieza, label);
		
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


	@Override
	public void eliminarPieza(Pieza ficha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
