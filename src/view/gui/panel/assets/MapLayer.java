package view.gui.panel.assets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import org.apache.batik.transcoder.TranscoderException;

public class MapLayer extends JLayeredPane {
	
	// ------------ ATTRIBUTES ------------ //
	
	private Dimension size;
	private Dimension carDimension;
	private Point position;
	
	// Main container
	private int topLayer = 0;
	
	private JPanel roadPanel;
	private Color mapColor = new Color(117, 117, 117);
	
	// Cars
	private ArrayList<CarAsset> cars = new ArrayList<CarAsset>();
	
	// ------------ CONSTRUCTORS ------------ //
	
	public MapLayer(Dimension size, Point position, int numberOfPlayers, Dimension carDimension) throws TranscoderException, URISyntaxException {
		this.size = size;
		this.position = position;
		this.carDimension = carDimension;
		
		this.setElements(numberOfPlayers);
	}
	
	// ------------ METHODS ------------ //
	
	private void setElements(int numberOfPlayers) throws TranscoderException, URISyntaxException {
		
		this.setConfiguration();
		this.drawRoad();
		
		this.setCarsAssets(numberOfPlayers);
	}
	
	
	private void drawRoad() {
		this.roadPanel = new JPanel();
		this.roadPanel.setBackground(mapColor);
		this.roadPanel.setBounds(0, this.size.height/2, this.size.width, this.size.height/2);
		
		this.add(this.roadPanel, new Integer(this.topLayer));
		this.topLayer++;
	}
	
	private void setConfiguration() {
		this.setBounds(this.position.x, this.position.y, this.size.width, this.size.height);
		this.setLayout(null);
		this.setOpaque(false);
	}
	
	private void setCarsAssets(int number) throws TranscoderException, URISyntaxException {
		
		double corrector = ((this.size.height /2) / (double) carDimension.height);
		
		
		Dimension dim = new Dimension(
				( int ) (corrector * carDimension.width),
				( int ) (corrector * carDimension.height)
			);
		
		for (int i = number-1; i >= 0 ; i--) {
			
			CarAsset c = new CarAsset(i, dim);
			c.setBounds(
					dim.width * i,
					10,
					dim.width, dim.height);
			
			this.add(c, new Integer(this.topLayer));
			this.topLayer++;
			
			this.cars.add(c);
		}
	}
	

}
