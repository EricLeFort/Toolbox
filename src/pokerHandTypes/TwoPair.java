package pokerHandTypes;

import java.util.Arrays;

import pokerTools.Card;

/**
 * @author Eric Le Fort
 * @version 01
 */

public class TwoPair implements PokerHand{
	private Card[] hand;
	private Card highPair, lowPair, kick;
	
	/**
	 * Creates this <code>TwoPair</code> according to the five <code>Card</code>s passed in.
	 * Assumes that the array passed in is exactly five <code>Card</code>s long.
	 * @param hand - The <code>Card</code>s representing this hand.
	 */
	public TwoPair(Card[] hand){
		Arrays.sort(hand);
		this.hand = hand;
		
		/*
		 * Three configurations:
		 * XXYZZ
		 * XXZZY
		 * YXXZZ
		 */
		lowPair = hand[1];
		highPair = hand[3];
		
		if(hand[0].getCardValue() != hand[1].getCardValue()){
			kick = hand[0];
		}else if(hand[3].getCardValue() != hand[4].getCardValue()){
			kick = hand[4];
		}else{
			kick = hand[2];
		}
	}//Constructor
	
	/**
	 * Compares this <code>TwoPair</code> to another <code>PokerHand</code>.
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
		TwoPair otherTwoPair;
		
		if(other instanceof TwoPair){
			otherTwoPair = (TwoPair)other;
			
			if(highPair.compareTo(otherTwoPair.getHighPair()) < 0){
				return -1;
			}else if(highPair.compareTo(otherTwoPair.getHighPair()) == 0){
				if(lowPair.compareTo(otherTwoPair.getLowPair()) < 0){
					return -1;
				}else if(lowPair.compareTo(otherTwoPair.getLowPair()) == 0){
					if(kick.compareTo(otherTwoPair.getKick()) < 0){
						return -1;
					}else if(kick.compareTo(otherTwoPair.getKick()) == 0){
						return 0;
					}
				}
			}
			return 1;
		}
		
		if(other instanceof Pair
				|| other instanceof HighCard){
			return 1;
		}
		return -1;
	}//compareTo()

	/**
	 * The high pair of the two.
	 * @return A <code>Card</code> that represents the high pair of this hand.
	 */
	public Card getHighPair(){ return highPair; }//getHighPair()
	
	/**
	 * The low pair of the two.
	 * @return A <code>Card</code> that represents the low pair of this hand.
	 */
	public Card getLowPair(){ return lowPair; }//getLowPair()
	
	/**
	 * The kicker of this hand.
	 * @return The <code>Card</code> that is the kicker of this hand.
	 */
	public Card getKick(){ return kick; }//getKick()
	
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
		if(highPair.getCardValue() == 4){
			return "two pair, " + highPair.getName() + "es and " + lowPair.getName() + "s";
		}else if(lowPair.getCardValue() == 4){
			return "two pair, " + highPair.getName() + "s and " + lowPair.getName() + "es";
		}
		return "two pair, " + highPair.getName() + "s and " + lowPair.getName() + "s";
	}//toString()
	
}//TwoPair
