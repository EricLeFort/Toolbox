package pokerHandTypes;

import java.util.Arrays;

import pokerTools.Card;

/**
* @author Eric Le Fort
* @version 01
*/

public class Pair implements PokerHand{
	private Card[] hand;
	private Card pair, lowKick, midKick, highKick;

	/**
	 * Creates this <code>Pair</code> according to the five <code>Card</code>s passed in.
	 * Assumes that the array passed in is exactly five <code>Card</code>s long.
	 * @param hand - The <code>Card</code>s representing this hand.
	 */
	public Pair(Card[] hand){
		Arrays.sort(hand);
		this.hand = hand;
		
		if(hand[0].getCardValue() == hand[1].getCardValue()){
			pair = hand[0];
			lowKick = hand[2];
			midKick = hand[3];
			highKick = hand[4];
		}else if(hand[1].getCardValue() == hand[2].getCardValue()){
			pair = hand[1];
			lowKick = hand[0];
			midKick = hand[3];
			highKick = hand[4];
		}else if(hand[2].getCardValue() == hand[3].getCardValue()){
			pair = hand[2];
			lowKick = hand[0];
			midKick = hand[1];
			highKick = hand[4];
		}else{
			pair = hand[3];
			lowKick = hand[0];
			midKick = hand[1];
			highKick = hand[2];
		}
	}//Constructor
	
	/**
	 * Compares this <code>Pair</code> to another <code>PokerHand</code>.
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
		Pair otherPair;
		
		if(other instanceof Pair){
			otherPair = (Pair)other;
			if(pair.compareTo(otherPair.getPair()) < 0){
				return -1;
			}else if(pair.compareTo(otherPair.getPair()) == 0){
				if(highKick.compareTo(otherPair.getHighKick()) < 0){
					return -1;
				}else if(highKick.compareTo(otherPair.getHighKick()) == 0){
					if(midKick.compareTo(otherPair.getMidKick()) < 0){
						return -1;
					}else if(midKick.compareTo(otherPair.getMidKick()) == 0){
						if(lowKick.compareTo(otherPair.getLowKick()) < 0){
							return -1;
						}else if(lowKick.compareTo(otherPair.getLowKick()) == 0){
							return 0;
						}
					}
				}
			}
			return 1;
		}
		
		if(other instanceof HighCard){
			return 1;
		}
		return -1;
	}//compareTo()

	/**
	 * The card value of this pair.
	 * @return A <code>Card</code> representing the value of the pair of this hand.
	 */
	public Card getPair(){ return pair; }//getPair()
	
	/**
	 * The high kicker of this triple.
	 * @return The <code>Card</code> that is the high kicker of this hand.
	 */
	public Card getHighKick(){ return highKick; }//getHighKick()
	
	/**
	 * The mid kicker of this triple.
	 * @return The <code>Card</code> that is the mid kicker of this hand.
	 */
	public Card getMidKick(){ return midKick; }//getMidKick()
	
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
		if(pair.getCardValue() == 4){
			return "pair of " + pair.getName() + "es";
		}
		return "pair of " + pair.getName() + "s";
	}//toString()
	
}//Pair
