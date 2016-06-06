package pokerHandTypes;

import pokerTools.Card;

/**
* @author Eric Le Fort
* @version 01
*/
public interface PokerHand extends Comparable<PokerHand>{
	public Card[] getHand();
}//PokerHand
