package view.gui.panel.global;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.Border;

import listeners.BackToMenuListener;
import listeners.InitializeGameListener;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.transcoder.TranscoderException;

import view.gui.manipulations.SVGIcon;
import view.gui.panel.BasicPanel;
import view.gui.threads.CloudAnimation;
import controller.MainController;

public class OnePlayerPanel extends BasicPanel {

	// ------------ ATTRIBUTES ------------ //
	
	// proportions for images
	private final int O_HEIGHT = 645; // Original Height
	private final int O_WIDTH = 1024; // Original Width
	
	private final int LANDSCAPE_HEIGHT = 605;
	private final int LANDSCAPE_WIDTH = 1024;
	private final int OFFSET = 20;
	
	// COLORS & FONT
	private final Color skyColor = new Color(0,165,220);
	private final Font font  = new Font("ForteMT", Font.PLAIN, 20);
	
	// Background container
	private JSVGCanvas background = null;
		
	// List of files
	private File cloudFile = null;
	private File backgroundFile = null;
	private File landscapeFile = null;
	
	// Our 3 clouds
	private JLabel cloudLabel = null;
	private JLabel cloudLabel2 = null;
	private JLabel cloudLabel3 = null;
	private JLabel landscape = null;
	
	private JPanel gameInfoPanel = null;
	
	private JButton startGameButton = null;
	private JButton backToMenuButton = null;
	
	private JTextField usernameField;
	private JSlider slider;
	
	
	// ------------ CONSTRUCTORS ------------ //
	
	public OnePlayerPanel(GraphicsDevice graphicsDevice, MainController controller) {
		super(graphicsDevice, controller);
		this.initElements();
	}
	
	// ------------ METHODS ------------ //
	public void initFiles() {
		
		try {
			this.cloudFile = new File(getClass().getResource("/cloud.svg").toURI());
			this.backgroundFile = new File(getClass().getResource("/background.svg").toURI());
			this.landscapeFile = new File(getClass().getResource("/landscape_noboard.svg").toURI());
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
		this.background.setBackground(this.skyColor);
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
	
	public void setGameInfosPanel() {
		
		this.startGameButton = new JButton("Start the game.");
		this.backToMenuButton = new JButton("Back to the menu.");
		
		this.gameInfoPanel = new JPanel();
		this.gameInfoPanel.setLayout(new GridLayout(6,1));
		
		int x, y, height, width;
		
		height = 300;
		width = this.getScreenDimension().width / 2;
		x = (this.getScreenDimension().width/2) - width/2;
		y = (this.getScreenDimension().height/2) - height/2;
		
		this.gameInfoPanel.setBounds(x, y, width, height);
		this.gameInfoPanel.setBackground(new Color(5, 59, 68));
		
		Border paddingBorder = BorderFactory.createEmptyBorder(10,50,10,10);
		
		
		JLabel usernameLabel = new JLabel("Please enter a username : ");
		usernameLabel.setBorder(BorderFactory.createCompoundBorder(null,paddingBorder));
		usernameLabel.setFont(font);
		usernameLabel.setForeground(Color.white);
		
		this.usernameField = new JTextField();
		
		JLabel AILabel = new JLabel("How many opponents do you want : ");
		AILabel.setBorder(BorderFactory.createCompoundBorder(null,paddingBorder));
		AILabel.setFont(font);
		AILabel.setForeground(Color.white);
		
		this.setSlider();
		
		this.gameInfoPanel.add(usernameLabel);
		this.gameInfoPanel.add(this.usernameField);
		this.gameInfoPanel.add(AILabel);
		this.gameInfoPanel.add(slider);
		this.gameInfoPanel.add(this.startGameButton);
		this.gameInfoPanel.add(this.backToMenuButton);
		
	}
	
	public void setSlider() {
		this.slider = new JSlider(1, 3, 3);
		this.slider.setMajorTickSpacing(1);
		this.slider.setPaintLabels(true);
		this.slider.setPaintTicks(true);
		this.slider.setFont(font);
		this.slider.setForeground(Color.white);
	}
	
	@Override
	public void setListeners() {
		
		this.backToMenuButton.addActionListener(new BackToMenuListener(controller));
		this.startGameButton.addActionListener(new InitializeGameListener(controller, this.usernameField, this.slider));
		
	}
	
	@Override
	public void initElements()
	{
		this.setLayout(null);
		this.setBounds(0,0,this.getScreenDimension().width, this.getScreenDimension().height);
		this.setBackground(this.skyColor);
		
		JLayeredPane p = new JLayeredPane();
		p.setBounds(0,0,this.getScreenDimension().width, this.getScreenDimension().height);
		
		this.initFiles();
		
		try {
			this.setSkyBackground();
			this.setClouds();
			this.setLandscape();
			this.setGameInfosPanel();
		} catch (TranscoderException e1) {
			e1.printStackTrace();
		}
		
		p.add(this.background, new Integer(0));
		p.add(this.cloudLabel, new Integer(1));
		p.add(this.cloudLabel2, new Integer(2));
		p.add(this.cloudLabel3, new Integer(3));
		p.add(this.landscape, new Integer(4));
		p.add(this.gameInfoPanel, new Integer(5));

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
	
}
