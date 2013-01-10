package models.stacks.player;

import java.util.Random;

import models.Game;
import models.cards.Card;
import models.cards.CardFamily;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.exceptions.IllegalCardTypeException;

/**
 * @author Simon RENOULT
 * @version 1.0
 * @see HandStackTest
 */
public class HandStack extends PlayerStack {

	// ------------ ATTRIBUTES ------------ //

	public static final int MAX_IN_PLAY_CARD = 5;
	public static final int MAX_CARD_NB = 4;
	public static final int MIN_CARD_NB = 1;

	// ------------ METHODS ------------ //

	@Override
	public void push( Card item ) throws IllegalCardTypeException {
		super.push( item );
	}

	/**
	 * Return the highest {@link DistanceCard}.
	 * 
	 * @param speedIsLimited
	 *            Research a {@link DistanceCard} depending on the
	 *            speedlimitation.
	 * @return The chosen {@link DistanceCard}.
	 */
	public DistanceCard getMaxDistance( boolean speedIsLimited ) {
		DistanceCard chosenCard = null;

		for ( Card handCard : this ) {
			if ( handCard instanceof DistanceCard ) {
				DistanceCard currentDistanceCard = ( DistanceCard ) handCard;

				if ( speedIsLimited ) {
					if ( currentDistanceCard.isAllowedOnSpeedLimit() ) {
						if ( chosenCard == null ) {
							chosenCard = currentDistanceCard;
						} else if ( currentDistanceCard.getRange() > ( ( DistanceCard ) chosenCard ).getRange() ) {
							chosenCard = currentDistanceCard;
						}
					}
				} else {
					if ( chosenCard == null ) {
						chosenCard = currentDistanceCard;
					} else if ( currentDistanceCard.getRange() > ( ( DistanceCard ) chosenCard ).getRange() ) {
						chosenCard = currentDistanceCard;
					}
				}
			}
		}

		return chosenCard;
	}

	/**
	 * Get the minimum {@link DistanceCard} available in player's hand.
	 * 
	 * @return The chosen {@link DistanceCard} or null if no one exists.
	 */
	public DistanceCard getMinDistance() {
		DistanceCard chosenCard = null;

		for ( Card handCard : this.cards ) {
			if ( handCard instanceof DistanceCard ) {
				DistanceCard currentDistanceCard = ( DistanceCard ) handCard;

				if ( chosenCard == null ) {
					chosenCard = currentDistanceCard;
				} else if ( currentDistanceCard.getRange() < ( ( DistanceCard ) chosenCard ).getRange() ) {
					chosenCard = currentDistanceCard;
				}
			}
		}

		return chosenCard;
	}

	/**
	 * Get a GoRoll in player's hand (if available). Else return null.
	 * 
	 * @return Get a GoRoll in player's hand (if available). Else return null.
	 */
	public RemedyCard getGoRoll() {

		for ( Card handCard : this.cards ) {
			if ( handCard instanceof RemedyCard ) {
				if ( handCard.getFamily() == CardFamily.GoStop ) {
					return ( RemedyCard ) handCard;
				}
			}
		}

		return null;
	}

	/**
	 * Get a {@link SafetyCard}. Its type depends on the {@link CardFamily}
	 * parameter.
	 * 
	 * @param searchedFamily
	 *            Chosen {@link SafetyCard} family.
	 * @return The chosen {@link SafetyCard}. Else return null.
	 */
	public SafetyCard getSafetyOf( CardFamily searchedFamily ) {
		for ( Card c : this.cards ) {
			if ( c instanceof SafetyCard ) {
				for ( CardFamily cf : c.getFamilies() ) {
					if ( cf == searchedFamily ) {
						return ( SafetyCard ) c;
					}
				}
			}
		}

		return null;
	}

	/**
	 * Get a {@link RemedyCard}. Its type depends on the {@link CardFamily}
	 * parameter.
	 * 
	 * @param searchedFamily
	 *            Chosen {@link RemedyCard} family.
	 * @return The chosen {@link RemedyCard}. Else return null.
	 */
	public RemedyCard getRemedyOf( CardFamily searchedFamily ) {
		for ( Card c : this.cards ) {
			if ( c instanceof RemedyCard ) {
				for ( CardFamily cf : c.getFamilies() ) {
					if ( cf == searchedFamily ) {
						return ( RemedyCard ) c;
					}
				}
			}
		}

		return null;
	}

	/**
	 * Return whether the stack contains a safety for the specified
	 * {@link CardFamily}.
	 * 
	 * @param family
	 *            {@link CardFamily} to test.
	 * @return Whether the stack contains a {@link SafetyCard} having the same
	 *         family than the parameter.
	 */
	public boolean hasSafetyFor( CardFamily family ) {
		for ( Card c : this.cards ) {
			if ( c instanceof SafetyCard ) {
				for ( CardFamily cf : c.getFamilies() ) {
					if ( cf == family ) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Return whether the stack contains a remedy for the specified
	 * {@link CardFamily}.
	 * 
	 * @param f
	 *            {@link CardFamily} to test.
	 * @return Whether the stack contains a {@link RemedyCard} having the same
	 *         family than the parameter.
	 */
	public boolean hasRemedyFor( CardFamily f ) {
		for ( Card c : this.cards ) {
			if ( c instanceof RemedyCard ) {
				if ( ( ( RemedyCard ) c ).getFamily() == f ) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Return whether the hand stack contains a {@link HazardCard}.
	 * 
	 * @return Return whether the hand stack contains a {@link HazardCard}.
	 */
	public boolean containsHazard() {
		for ( Card c : this.cards ) {
			if ( c instanceof HazardCard ) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Return whether the hand stack contains a {@link SafetyCard}.
	 * 
	 * @return Return whether the hand stack contains a {@link SafetyCard}.
	 */
	public boolean containsSafety() {
		for ( Card c : this.cards ) {
			if ( c instanceof SafetyCard ) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Return whether the hand stack contains a {@link RemedyCard}.
	 * 
	 * @return Whether the hand stack contains a {@link RemedyCard}.
	 */
	public boolean containsRemedy() {
		for ( Card c : this.cards ) {
			if ( c instanceof RemedyCard ) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Return whether the hand stack contains a {@link DistanceCard}.
	 * 
	 * @return Return whether the hand stack contains a {@link DistanceCard}.
	 */
	public boolean containsDistance() {
		for ( Card c : this.cards ) {
			if ( c instanceof DistanceCard ) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Get a random {@link Card} in hand stack.
	 * 
	 * @return A randomly chosen {@link Card}.
	 */
	public Card getRandomCard() {
		return this.get( new Random().nextInt( this.size() ) );
	}

	/**
	 * Get the correct {@link DistanceCard} when player can finish.
	 * 
	 * @param traveledDistance
	 *            Traveled distance so far.
	 * @param distanceGoal
	 *            {@link Game} distance goal.
	 * @return Suitable {@link DistanceCard}.
	 */
	public DistanceCard getFinishableDistance( int traveledDistance, int distanceGoal ) {
		for ( Card c : this.cards ) {
			if ( c instanceof DistanceCard ) {
				if ( ( ( DistanceCard ) c ).getRange() + traveledDistance == distanceGoal ) {
					return ( DistanceCard ) c;
				}
			}
		}
		return null;
	}
}
