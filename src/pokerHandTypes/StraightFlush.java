package pokerHandTypes;

import java.util.Arrays;

import pokerTools.Card;

/**
 * @author Eric Le Fort
 * @version 01
 */
public class StraightFlush implements PokerHand{
	private Card[] hand;
	private boolean isRoyal;
	
	/**
	 * Creates this <code>StraightFlush</code> according to the five <code>Card</code>s passed in.
	 * Assumes that the array passed in is exactly five <code>Card</code>s long.
	 * @param hand - The <code>Card</code>s representing this hand.
	 */
	public StraightFlush(Card[] hand){
		Card temp;
		
		Arrays.sort(hand);
		if(hand[0].getCardValue() == 0 && hand[4].getCardValue() == 12){		//A2345 not 2345A
			temp = hand[4];
			hand[4] = hand[3];
			hand[3] = hand[2];
			hand[2] = hand[1];
			hand[1] = hand[0];
			hand[0] = temp;
		}
		this.hand = hand;
		
		isRoyal = hand[4].getCardValue() == 12;
	}//Constructor
	
	/**
	 * Compares this <code>StraightFlush</code> to another <code>PokerHand</code>.
	 * Returns:
	 * 1  - This hand is better.
	 * 0  - The hands are equal.
	 * -1 - The other hand is better.
	 * 
	 * @param other - The other <code>PokerHand</code>.
	 * @return -1, 0 or 1 based on which hand is best.
	 */
	@Override
	public int compareTo(PokerHand other){
		if(other instanceof StraightFlush){
			if(hand[4].compareTo(other.getHand()[4]) < 0){
				return -1;
			}else if(hand[4].compareTo(other.getHand()[4]) == 0){
				return 0;
			}
		}
		return 1;
	}//compareTo()
	
	/**
	 * Returns the <code>Card</code> array representing this hand.
	 * @return The <code>Card</code> array representing this hand.
	 */
	public Card[] getHand(){ return hand; }//getHand()
	
	/**
	 * Returns a <code>String</code> representation of this hand.
	 * @return a <code>String</code> representation of this hand.
	 */
	@Override
	public String toString(){
		if(isRoyal){
			return "royal flush";
		}
		return "straight flush, " + hand[4].getName() + " high";
	}//toString()
	
}//StraightFlush
