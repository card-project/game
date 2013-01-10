package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import models.cards.Card;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XML parser used to parse the conf.xml file. This file contains the number of
 * {@link Card} instances contained within a deck.
 * 
 * @author Simon RENOULT
 * @version 1.0
 */
public class XMLParser {

	// -------------- CONSTANTS -------------- //

	private static final String pathToConfFile = "bin/conf.xml";

	// -------------- ATTRIBUTES -------------- //

	private DocumentBuilder builder;
	private File fileToParse;
	private Element rootTag;

	// -------------- CONSTRUCTORS -------------- //

	private static class XMLParserHolder {
		private final static XMLParser INSTANCE = new XMLParser();
	}

	private XMLParser () {

		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch ( ParserConfigurationException e ) {
			e.printStackTrace();
		}

		fileToParse = new File( pathToConfFile );

		if ( !fileToParse.exists() ) {
			try {
				throw new FileNotFoundException();
			} catch ( FileNotFoundException e ) {
				e.printStackTrace();
			}
		}
	}

	// -------------- GETTERS -------------- //

	public static XMLParser getInstance() {
		return XMLParserHolder.INSTANCE;
	}

	/**
	 * Return the number of {@link Card} instances specified within the
	 * configuration file.
	 * 
	 * @param className
	 *            Name of the class we are looking for.
	 * @param value
	 *            Name of the {@link Card} instance we are looking for (e.g.
	 *            25/50/... for {@link DistanceCard} or {@link CardType}.Speed
	 *            for {@link HazardCard}/{@link SafetyCard}/{@link RemedyCard}).
	 * @return The number of authorized instance.
	 */
	public int getQty( String className, String value ) {
		try {
			rootTag = builder.parse( fileToParse ).getDocumentElement();
		} catch ( SAXException e ) {
			e.printStackTrace();
		} catch ( IOException e ) {
			e.printStackTrace();
		}

		NodeList cards = rootTag.getElementsByTagName( "card" );

		for ( int i = 0 ; i < cards.getLength() ; i++ ) {

			Node card = cards.item( i );
			Node nodeClass = card.getAttributes().getNamedItem( "class" );

			if ( nodeClass.getNodeValue().equals( className ) ) {

				for ( int j = 0 ; j < card.getChildNodes().getLength() ; j++ ) {
					if ( card.getChildNodes().item( j ).hasAttributes() ) {

						Node instance = card.getChildNodes().item( j );
						String tag = ( className.equals( "DistanceCard" ) ) ? "value" : "family";

						if ( instance.getAttributes().getNamedItem( tag ).getNodeValue().equals( value ) ) {
							return Integer
									.parseInt( instance.getAttributes().getNamedItem( "quantity" ).getNodeValue() );
						}
					}
				}
			}
		}

		return -1;
	}
}
