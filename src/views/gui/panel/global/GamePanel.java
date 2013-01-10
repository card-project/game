package views.gui.panel.global;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.Point;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import models.Game;
import models.cards.Card;
import models.players.Player;
import models.stacks.player.BattleStack;
import models.stacks.player.HandStack;
import models.stacks.player.SafetyStack;
import views.gui.panel.BasicPanel;
import views.gui.panel.Constant;
import views.gui.panel.assets.CarAsset;
import views.gui.panel.assets.CardIcon;
import views.gui.panel.assets.DiscardLayer;
import views.gui.panel.assets.DrawingStepLayer;
import views.gui.panel.assets.HandAndTargetLayer;
import views.gui.panel.assets.MapLayer;
import views.gui.threads.MovingAnimation;
import controller.MainController;

public class GamePanel extends BasicPanel {

	// ------------ ATTRIBUTES ------------ //

	private static final long serialVersionUID = -2278103661907756089L;

	private double corrector;
	private int topLayer = new Integer( 0 );

	private Point boardPosition = new Point( 0, 0 );

	// Main container
	private JLayeredPane container;

	// Cars
	private ArrayList<CarAsset> cars = new ArrayList<CarAsset>();

	// Card icons
	private ArrayList<CardIcon> cards = new ArrayList<CardIcon>();

	// Board image.
	private JSVGCanvas background = null;
	private File backgroundFile = null;

	private DrawingStepLayer deckLayer;
	private HandAndTargetLayer handLayer;
	private DiscardLayer discardLayer;
	private MapLayer mapLayer;

	private JPanel handContainer = new JPanel();

	// ------------ CONSTRUCTORS ------------ //

	public GamePanel ( GraphicsDevice c, MainController controlleur ) {
		super( c, controlleur );
		this.initElements();
	}

	// ------------ METHODS ------------ //

	@Override
	public void initElements() {
		this.initContainer();
		this.drawGameboard();

		this.setElements();
		this.setListeners();
	}

	/**
	 * Assemble all the elements.
	 */
	private void setElements() {

		container.add( this.background, new Integer( this.topLayer ) );
		this.topLayer++;

		this.add( this.container );
	}

	private void setPlayerHand() {

		HandStack handStack = this.controller.getPlayer().getHandStack();
		BattleStack battleStack = this.controller.getPlayer().getBattleStack();
		SafetyStack safetyStack = this.controller.getPlayer().getSafetyStack();

		int rows = 0;

		if ( handStack.size() > 0 )
			rows++;
		if ( battleStack.size() > 0 )
			rows++;
		if ( safetyStack.size() > 0 )
			rows++;

		Dimension cardDimension = new Dimension( this.getSize().height / 6, this.getSize().height / 6 );

		handContainer = new JPanel();
		handContainer.setOpaque( false );
		handContainer.setLayout( null );
		handContainer.setBounds( this.getSize().width - ( cardDimension.width * 4 ) - 20, 20,
				( cardDimension.width * 4 ), rows * ( this.getSize().height / 6 ) );

		int i = 0, j = 0;

		if ( handStack.size() > 0 ) {
			for ( Card card : handStack ) {
				CardIcon icon = new CardIcon( card, cardDimension );
				icon.setBounds( ( 3 - i ) * cardDimension.width, j * cardDimension.height, cardDimension.width,
						cardDimension.height );

				handContainer.add( icon );
				i++;
			}

			j++;
		}

		if ( battleStack.size() > 0 ) {
			i = 0;
			for ( Card card : battleStack ) {
				CardIcon icon = new CardIcon( card, cardDimension );
				icon.setBounds( ( 3 - i ) * cardDimension.width, j * cardDimension.height, cardDimension.width,
						cardDimension.height );
				handContainer.add( icon );
				i++;
			}

			j++;
		}

		if ( safetyStack.size() > 0 ) {
			i = 0;
			for ( Card card : safetyStack ) {
				CardIcon icon = new CardIcon( card, cardDimension );
				icon.setBounds( ( 3 - i ) * cardDimension.width, j * cardDimension.height, cardDimension.width,
						cardDimension.height );

				handContainer.add( icon );
				i++;
			}
		}

		this.container.add( handContainer, new Integer( 9 ) );
	}

	public void refreshPlayerHand() {
		this.container.remove( this.container );
		this.handContainer.removeAll();

		this.setPlayerHand();
	}

	/**
	 * Configure the panel. Allows the assets to suit perfectly with the panel.
	 */
	private void initContainer() {
		this.setLayout( null );
		this.setBounds( 0, 0, this.getScreenDimension().width, this.getScreenDimension().height );

		this.container = new JLayeredPane();
		this.container.setBounds( 0, 0, this.getScreenDimension().width, this.getScreenDimension().height );
	}

	/**
	 * Create the main panel "Gameboard" from the SVG file.
	 */
	private void drawGameboard() {

		this.corrector = ( new Integer( this.getScreenDimension().height ).doubleValue() / Constant.GAMEBOARD.height );

		Dimension ajustedDimension = new Dimension( ( int ) ( this.corrector * Constant.GAMEBOARD.width ),
				( int ) ( this.corrector * Constant.GAMEBOARD.height ) );

		this.background = new JSVGCanvas();
		this.background.setBounds( boardPosition.x, boardPosition.y, ajustedDimension.width, ajustedDimension.height );

		try {

			this.backgroundFile = new File( getClass().getResource( "/gameboard.svgz" ).toURI() );
			this.background.setURI( this.backgroundFile.toURI().toURL().toString() );

		} catch ( MalformedURLException e ) {
			e.printStackTrace();
		} catch ( URISyntaxException e ) {
			e.printStackTrace();
		}
	}

	public void displayDeck() {

		this.deckLayer = new DrawingStepLayer( this.getScreenDimension().width, this.getScreenDimension().height,
				this.controller );
		this.deckLayer.drawHandAndDeck( this.controller.getGameModel() );
		this.container.add( this.deckLayer, new Integer( 13 ) );

		this.repaint();
	}

	public void hideDeck() {
		if ( this.deckLayer != null ) {
			this.container.remove( this.deckLayer );
			this.container.repaint();
		}
	}

	public void displayHandLayer( Player player ) {
		this.handLayer = new HandAndTargetLayer( this.getScreenDimension().width, this.getScreenDimension().height,
				this.controller );
		this.handLayer.drawHandAndOpponents( player );
		this.container.add( this.handLayer, new Integer( 11 ) );

		this.repaint();
	}

	public void hideHandLayer() {

		if ( this.handLayer != null ) {
			this.container.remove( this.handLayer );
			this.container.repaint();
		}
	}

	public void displayDiscardChoice( Player player ) {
		this.discardLayer = new DiscardLayer( this.getScreenDimension().width, this.getScreenDimension().height,
				this.controller );
		this.discardLayer.drawHandAndOpponents( player );
		this.container.add( this.discardLayer, new Integer( 12 ) );

		this.repaint();
	}

	public void hideDiscardChoice() {

		if ( this.discardLayer != null ) {
			this.container.remove( this.discardLayer );
			this.container.repaint();
		}
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setListeners() {

	}

	public JSVGCanvas getBackgroundPanel() {
		return background;
	}

	// ------------ INIT THE GAMEBOARD WITH DATA ------------ //

	public void initGameboard( Game game ) {
		try {
			this.setCarsAssets( game.getPlayers().length );
			this.setMiniMap( game.getPlayers().length );
			this.setPlayerHand();
		} catch ( TranscoderException e ) {
			e.printStackTrace();
		} catch ( URISyntaxException e ) {
			e.printStackTrace();
		}

		this.setGameElements();
	}

	/**
	 * Draw the map board, located at the bottom of screen.
	 * 
	 * @throws URISyntaxException
	 * @throws TranscoderException
	 */
	private void setMiniMap( int number ) throws TranscoderException, URISyntaxException {

		Dimension mapSize = new Dimension( this.getScreenDimension().width, 60 );

		Point mapPosition = new Point( 0, this.getScreenDimension().height - mapSize.height );

		this.mapLayer = new MapLayer( mapSize, mapPosition, number, Constant.CAR_DIMENSION );
	}

	private void setGameElements() {
		for ( CarAsset car : this.cars ) {
			this.container.add( car, new Integer( this.topLayer ) );
			this.topLayer++;
		}

		this.container.add( this.mapLayer, new Integer( this.topLayer ) );
		this.topLayer++;

		this.repaint();
	}

	private void setCarsAssets( int number ) throws TranscoderException, URISyntaxException {

		int interval = ( int ) ( ( Constant.MAX_POSITION_CAR.y * this.corrector - Constant.MIN_POSITION_CAR.y
				* this.corrector ) / number );

		Point position = null;

		Dimension dim = new Dimension( ( int ) ( this.corrector * Constant.CAR_DIMENSION.width ),
				( int ) ( this.corrector * Constant.CAR_DIMENSION.height ) );

		number--;
		for ( int i = number ; i >= 0 ; i-- ) {

			position = new Point( 0, ( int ) ( this.getScreenDimension().height - Constant.MAX_POSITION_CAR.y
					* this.corrector + i * interval ) );

			CarAsset c = new CarAsset( number - i );
			c.setBounds( position.x + i * 30, position.y, dim.width, dim.height );
			this.cars.add( c );
		}
	}

	public void animatePlayer( Player p ) {
		CarAsset icon = this.cars.get( p.getBib() );

		int distance = ( ( this.background.getWidth() - icon.getSize().width ) * p.getTraveledDistance() )
				/ this.controller.getGameModel().getGoal();
		MovingAnimation anim = new MovingAnimation( this, icon, distance, controller );

	}

}