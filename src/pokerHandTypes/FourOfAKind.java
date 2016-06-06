package pokerHandTypes;

import java.util.Arrays;

import pokerTools.Card;

/**
 * @author Eric Le Fort
 * @version 01
 */
public class FourOfAKind implements PokerHand{
	private Card[] hand;
	private Card kicker, set;
	
	/**
	 * Creates this <code>FourOfAKind</code> according to the five <code>Card</code>s passed in.
	 * Assumes that the array passed in is exactly five <code>Card</code>s long.
	 * @param hand - The <code>Card</code>s representing this hand.
	 */
	public FourOfAKind(Card[] hand){
		Arrays.sort(hand);
		this.hand = hand;
		
		if(hand[0].getCardValue() == hand[1].getCardValue()){
			set = hand[0];
			kicker = hand[4];
		}else{
			set = hand[4];
			kicker = hand[0];
		}
	}//Constructor
	
	/**
	 * Compares this <code>FourOfAKind</code> to another <code>PokerHand</code>.
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
		FourOfAKind otherFourOfAKind;
		
		if(other instanceof FourOfAKind){
			otherFourOfAKind = (FourOfAKind)other;
			if(set.compareTo(otherFourOfAKind.getSet()) < 0){
				return -1;
			}else if(set.compareTo(otherFourOfAKind.getSet()) == 0){
				if(kicker.compareTo(otherFourOfAKind.getKicker()) < 0){
					return -1;
				}else if(kicker.compareTo(otherFourOfAKind.getKicker()) == 0){
					return 0;
				}
			}
			return 1;
		}
		
		if(other instanceof StraightFlush){
			return -1;
		}
		return 1;
	}//compareTo()
	
	/**
	 * Returns the <code>Card</code> array representing this hand.
	 * @return The <code>Card</code> array representing this hand.
	 */
	@Override
	public Card[] getHand(){ return hand; }//getHand()
	
	/**
	 * The card value of this four of a kind.
	 * @return A <code>Card</code> representing the card value of the four-of-a-kind.
	 */
	public Card getSet(){ return set; }//getSetValue()
	
	/**
	 * Returns the kicker of this hand.
	 * @return The <code>Card</code> that is the kicker of this hand.
	 */
	public Card getKicker(){ return kicker; }//getKicker()
	
	/**
	 * Returns a <code>String</code> representation of this hand.
	 * @return a <code>String</code> representation of this hand.
	 */
	@Override
	public String toString(){
		if(set.getCardValue() == 4){
			return "four of a kind, " + set.getName() + "es";
		}
		return "four of a kind, " + set.getName() + "s";
	}//toString()
	
}//FourOfAKind
