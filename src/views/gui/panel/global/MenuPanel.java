package views.gui.panel.global;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import listeners.MultiplayerChosenMenuListener;
import listeners.OnePlayerChosenMenuListener;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.transcoder.TranscoderException;

import views.gui.manipulations.JLabelRotation;
import views.gui.manipulations.SVGIcon;
import views.gui.panel.BasicPanel;
import views.gui.panel.Constant;
import views.gui.threads.CloudAnimation;
import controller.MainController;


public class MenuPanel extends BasicPanel {
	
	// ------------ ATTRIBUTES ------------ //
	private static final long serialVersionUID = -8443512178289401864L;
	
	// proportions for images
	private final int O_HEIGHT = 645; // Original Height
	private final int O_WIDTH = 1024; // Original Width
	
	private final int LANDSCAPE_HEIGHT = 605;
	private final int LANDSCAPE_WIDTH = 1024;
	private final int OFFSET = 20;
	
	// Background container
	private JSVGCanvas background = null;
	
	//
	private JPanel containerCounters = null;
	
	// List of files
	private File cloudFile = null;
	private File backgroundFile = null;
	private File landscapeFile = null;
	private File logoFile = null;
	
	// Our 3 clouds
	private JLabel cloudLabel = null;
	private JLabel cloudLabel2 = null;
	private JLabel cloudLabel3 = null;
	
	private JLabel landscape = null;
	private JLabel logo = null;
	
	private JButton onePlayerButton = null;
	private JButton multiPlayerButton = null;
	private JButton optionsButton = null;
	private JButton creditsButton = null;
	
	private ActionListener oneplayerMenuListener = null;
	private ActionListener multiplayerMenuListener = null;
	
	// Animations
	private CloudAnimation anim = null;
	private CloudAnimation anim2 = null;
	private CloudAnimation anim3 = null;
	
	// ------------ CONSTRUCTORS ------------ //
	public MenuPanel(GraphicsDevice c, MainController basicController) {
		super(c, basicController);
		
		this.initElements();
	}
	
	// ------------ METHODS ------------ //
	public void initFiles() {
		
		try {
			this.cloudFile = new File(getClass().getResource("/cloud.svg").toURI());
			this.backgroundFile = new File(getClass().getResource("/background.svg").toURI());
			this.landscapeFile = new File(getClass().getResource("/landscape.svg").toURI());
			this.logoFile = new File(getClass().getResource("/logo.svg").toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}
	
	public void setSkyBackground() {
	
		double corrector = (new Integer(this.getScreenDimension().width + OFFSET).doubleValue() / O_WIDTH);
		int width = (int)(O_WIDTH*corrector);
		int height = (int)(O_HEIGHT*corrector);
		
		int paddingTopBackground = -(this.getScreenDimension().height - (this.getScreenDimension().width * O_HEIGHT)/O_WIDTH);
		
		this.background = new JSVGCanvas();
		this.background.setBackground(Constant.SKYCOLOR);
		this.background.setBounds(-OFFSET/2, -paddingTopBackground, width, height);
		try {
            background.setURI(this.backgroundFile.toURI().toURL().toString());
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
	}
	
	public void setClouds() throws TranscoderException {
		
		SVGIcon iconCloud = new SVGIcon(this.cloudFile.toURI().toString(), 200, 200);
		this.cloudLabel = new JLabel(iconCloud);
		this.cloudLabel.setBounds(-200, 80, iconCloud.getIconWidth(),iconCloud.getIconHeight());
		
		iconCloud = new SVGIcon(this.cloudFile.toURI().toString(), 150, 150);
		this.cloudLabel2 = new JLabel(iconCloud);
		this.cloudLabel2.setBounds(146, 55, iconCloud.getIconWidth(),iconCloud.getIconHeight());
		
		iconCloud = new SVGIcon(this.cloudFile.toURI().toString(), 210, 210);
		this.cloudLabel3 = new JLabel(iconCloud);
		this.cloudLabel3.setBounds(300, 90, iconCloud.getIconWidth(),iconCloud.getIconHeight());
	}
	
	public void setLandscape() throws TranscoderException {
	
		double corrector = (new Integer(this.getScreenDimension().width + OFFSET).doubleValue() / LANDSCAPE_WIDTH);
		int width = (int)(LANDSCAPE_WIDTH*corrector);
		int height = (int)(LANDSCAPE_HEIGHT*corrector);
		
		SVGIcon landscapeIcon = new SVGIcon(this.landscapeFile.toURI().toString(), width, height);
				
		int paddingTopLandscape = (this.getScreenDimension().height > height)
										? Math.abs(height - this.getScreenDimension().height)
										: -(height - (this.getScreenDimension().height)) ;
		
		paddingTopLandscape += OFFSET/2;
		
		this.landscape = new JLabel(landscapeIcon);
		this.landscape.setBounds(0, paddingTopLandscape, width, height);
	}
	
	public void setCounterBar() throws TranscoderException{
		
		int wPanel = 512;
		int hPanel = 127;
		
		double corrector = (new Integer(this.getScreenDimension().width + OFFSET).doubleValue() / LANDSCAPE_WIDTH);
		wPanel = (int)(wPanel*corrector);
		hPanel = (int)(hPanel *corrector);
		
		int xPanel = (this.getScreenDimension().width/2) - (wPanel / 2);
		int yPanel = this.getScreenDimension().height - hPanel - OFFSET;
		
		this.containerCounters = new JPanel();
		this.containerCounters.setLayout(new GridLayout(1,4));
		this.containerCounters.setOpaque(false);
		this.containerCounters.setBounds(xPanel, yPanel, wPanel, hPanel);
		
		try {
			onePlayerButton = this.createButton(getClass().getResource("/onePlayerCounter.svg").toURI(), (wPanel/4));
			multiPlayerButton = this.createButton(getClass().getResource("/multiplayerCounter.svg").toURI(), (wPanel/4));
			optionsButton = this.createButton(getClass().getResource("/optionsCounter.svg").toURI(), (wPanel/4));
			creditsButton = this.createButton(getClass().getResource("/creditsCounter.svg").toURI(), (wPanel/4));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		this.containerCounters.add(onePlayerButton);
		this.containerCounters.add(multiPlayerButton);
		this.containerCounters.add(optionsButton);
		this.containerCounters.add(creditsButton);
		
	}
	
	private JButton createButton(URI file, int size) throws TranscoderException {
		
		SVGIcon icon = new SVGIcon(file.toString(), size, size);
		JButton button = new JButton(icon);
		
		button.setBackground(null);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		
		return button;
	}
	
	public void setLogo() throws TranscoderException {
		SVGIcon logo = new SVGIcon(this.logoFile.toURI().toString(), this.getScreenDimension().width/3, this.getScreenDimension().height/3);
		this.logo = new JLabel(logo);
		this.logo.setBounds(this.getScreenDimension().width/2 - logo.getIconWidth()/2, 0, logo.getIconWidth(), logo.getIconWidth());
	}
	
	@Override
	public void setListeners() {
		
		this.oneplayerMenuListener = new OnePlayerChosenMenuListener(getController());
		this.multiplayerMenuListener = new MultiplayerChosenMenuListener(getController());		
		
		this.onePlayerButton.addActionListener(this.oneplayerMenuListener);
		this.multiPlayerButton.addActionListener(this.multiplayerMenuListener);
	}
	
	@Override
	public void initElements()
	{
		this.setLayout(null);
		this.setBounds(0,0,this.getScreenDimension().width, this.getScreenDimension().height);
		this.setBackground(Constant.SKYCOLOR);
		
		JLayeredPane p = new JLayeredPane();
		p.setBounds(0,0,this.getScreenDimension().width, this.getScreenDimension().height);
		
		this.initFiles();
		
		try {
			this.setSkyBackground();
			this.setClouds();
			this.setLandscape();
			this.setCounterBar();
			this.setLogo();
		} catch (TranscoderException e1) {
			e1.printStackTrace();
		}
		
		p.add(this.background, new Integer(0));
		p.add(this.cloudLabel, new Integer(1));
		p.add(this.cloudLabel2, new Integer(2));
		p.add(this.cloudLabel3, new Integer(3));
		p.add(this.landscape, new Integer(4));
		p.add(this.containerCounters, new Integer(5));
		p.add(this.logo, new Integer(6));

		this.add(p);
		this.animate();
	
		this.setListeners();
	}
	
	@Override
	public void animate() {
		this.setCloudAnimation();
	}
	
	private void setCloudAnimation() {
		this.addAnimation(new CloudAnimation(this.cloudLabel, this.getWidth(), 35));
		this.addAnimation(new CloudAnimation(this.cloudLabel2, this.getWidth(), 50));
		this.addAnimation(new CloudAnimation(this.cloudLabel3, this.getWidth(), 28));
	}
	
	public CloudAnimation getAnim() {
		return this.anim;
	}
}
