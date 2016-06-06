package pokerHandTypes;

import java.util.Arrays;

import pokerTools.Card;

/**
* @author Eric Le Fort
* @version 01
*/

public class ThreeOfAKind implements PokerHand{
	private Card[] hand;
	private Card highKick, lowKick;
	
	/**
	 * Creates this <code>ThreeOfAKind</code> according to the five <code>Card</code>s passed in.
	 * Assumes that the array passed in is exactly five <code>Card</code>s long.
	 * @param hand - The <code>Card</code>s representing this hand.
	 */
	public ThreeOfAKind(Card[] hand){
		Arrays.sort(hand);
		this.hand = hand;
		
		/*
		 * Three configurations:
		 * XXXYZ
		 * XYYYZ
		 * XYZZZ
		 */
		if(hand[2].getCardValue() != hand[3].getCardValue()){
			lowKick = hand[3];
			highKick = hand[4];
		}else if(hand[1].getCardValue() != hand[2].getCardValue()){
			lowKick = hand[0];
			highKick = hand[1];
		}else{
			lowKick = hand[0];
			highKick = hand[4];
		}
	}//Constructor
	
	/**
	 * Compares this <code>ThreeOfAKind</code> to another <code>PokerHand</code>.
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
		ThreeOfAKind otherThreeOfAKind;
		
		if(other instanceof ThreeOfAKind){
			otherThreeOfAKind = (ThreeOfAKind)other;
			
			if(hand[2].compareTo(otherThreeOfAKind.getHand()[2]) < 0){
				return -1;
			}else if(hand[2].compareTo(otherThreeOfAKind.getHand()[2]) == 0){
				if(highKick.compareTo(otherThreeOfAKind.getHighKick()) < 0){
					return -1;
				}else if(highKick.compareTo(otherThreeOfAKind.getHighKick()) == 0){
					if(lowKick.compareTo(otherThreeOfAKind.getLowKick()) < 0){
						return -1;
					}else if(lowKick.compareTo(otherThreeOfAKind.getLowKick()) == 0){
						return 0;
					}
				}
			}
			return 1;
		}
		
		if(other instanceof TwoPair
				|| other instanceof Pair
				|| other instanceof HighCard){
			return 1;
		}
		
		return -1;
	}//compareTo()

	/**
	 * The high kicker of this triple.
	 * @return The <code>Card</code> that is the high kicker of this hand.
	 */
	public Card getHighKick(){ return highKick; }//getHighKick()
	
	/**
	 * The low kicker of this triple.
	 * @return The <code>Card</code> that is the low kicker of this hand.
	 */
	public Card getLowKick(){ return lowKick; }//getLowKick()
	
	/**
	 * Returns the <code>Card</code> array representing this hand.
	 * @return The <code>Card</code> array representing this hand.
	 */
	@Override
	public Card[] getHand(){ return hand; }//getHand()

	/**
	 * Returns a <code>String</code> representation of this hand.
	 * @return a <code>String</code> representation of this hand.
	 */
	@Override
	public String toString(){
		if(hand[2].getCardValue() == 4){
			return "three of a kind, " + hand[2].getName() + "es";
		}
		return "three of a kind, " + hand[2].getName() + "s";
	}//toString()
	
}//ThreeOfAKind
