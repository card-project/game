package view.gui.panel.assets;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.net.URISyntaxException;

import javax.swing.JLabel;

import org.apache.batik.transcoder.TranscoderException;

import controller.MainController;

import view.gui.manipulations.SVGIcon;
import view.gui.threads.MovingAnimation;

public class CarAsset extends JLabel {

	// ------------ ATTRIBUTES ------------ //

	private static final long serialVersionUID = 2232084128696310732L;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public CarAsset(int position) throws TranscoderException, URISyntaxException {
		
		SVGIcon iconCloud = new SVGIcon(getClass().getResource("/cars/car" + position + ".svg").toURI().toString());
		this.setIcon(iconCloud);
	}
	
	public CarAsset(int position, Dimension dim) throws TranscoderException, URISyntaxException {
		
		SVGIcon iconCloud = new SVGIcon(getClass().getResource("/cars/car" + position + ".svg").toURI().toString(), dim.width, dim.height);
		this.setIcon(iconCloud);
	}
	
}
