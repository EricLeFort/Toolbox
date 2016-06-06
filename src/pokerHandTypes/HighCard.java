package pokerHandTypes;

import java.util.Arrays;

import pokerTools.Card;

/**
* @author Eric Le Fort
* @version 01
*/

public class HighCard implements PokerHand{
	private Card[] hand;

	/**
	 * Creates this <code>HighCard</code> according to the five <code>Card</code>s passed in.
	 * Assumes that the array passed in is exactly five <code>Card</code>s long.
	 * @param hand - The <code>Card</code>s representing this hand.
	 */
	public HighCard(Card[] hand){
		Arrays.sort(hand);
		this.hand = hand;
	}//Constructor
	
	/**
	 * Compares this <code>HighCard</code> to another <code>PokerHand</code>.
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
		if(other instanceof HighCard){
			if(hand[4].compareTo(other.getHand()[4]) < 0){
				return -1;
			}else if(hand[4].compareTo(other.getHand()[4]) == 0){
				if(hand[3].compareTo(other.getHand()[3]) < 0){
					return -1;
				}else if(hand[3].compareTo(other.getHand()[3]) == 0){
					if(hand[2].compareTo(other.getHand()[2]) < 0){
						return -1;
					}else if(hand[2].compareTo(other.getHand()[2]) == 0){
						if(hand[1].compareTo(other.getHand()[1]) < 0){
							return -1;
						}else if(hand[1].compareTo(other.getHand()[1]) == 0){
							if(hand[0].compareTo(other.getHand()[0]) < 0){
								return -1;
							}else if(hand[0].compareTo(other.getHand()[0]) == 0){
								return 0;
							}
						}
					}
				}
			}
			return 1;
		}
		
		return -1;
	}//compareTo()

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
		return "high card, " + hand[4].getName();
	}//toString()
	
}//HighCard
