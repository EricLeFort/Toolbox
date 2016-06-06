package pokerHandTypes;

import java.util.Arrays;

import pokerTools.Card;

/**
* @author Eric Le Fort
* @version 01
*/
public class Flush implements PokerHand{
	private Card[] hand;

	/**
	 * Creates this <code>Flush</code> according to the five <code>Cards</code>s passed in.
	 * Assumes that the array passed in is exactly five <code>Cards</code>s long.
	 * @param hand - The <code>Cards</code> representing this hand.
	 */
	public Flush(Card[] hand){
		Arrays.sort(hand);
		this.hand = hand;
	}//Constructor
	
	/**
	 * Compares this <code>Flush</code> to another <code>PokerHand</code>.
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
		Card[] otherHand;
		
		if(other instanceof Flush){
			otherHand = other.getHand();
			if(hand[4].compareTo(otherHand[4]) < 0){
				return -1;
			}else if(hand[4].compareTo(otherHand[4]) == 0){
				if(hand[3].compareTo(otherHand[3]) < 0){
					return -1;
				}else if(hand[3].compareTo(otherHand[3]) == 0){
					if(hand[2].compareTo(otherHand[2]) < 0){
						return -1;
					}else if(hand[2].compareTo(otherHand[2]) == 0){
						if(hand[1].compareTo(otherHand[1]) < 0){
							return -1;
						}else if(hand[1].compareTo(otherHand[1]) == 0){
							if(hand[0].compareTo(otherHand[0]) < 0){
								return -1;
							}else if(hand[0].compareTo(otherHand[0]) == 0){
								return 0;
							}
						}
					}
				}
			}
			return 1;
		}
		
		if(other instanceof StraightFlush
				|| other instanceof FourOfAKind
				|| other instanceof FullHouse){
			return -1;
		}
		return 1;
	}

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
		return "flush, " + hand[4].getName() + " high";
	}//toString()
	
}//Flush
