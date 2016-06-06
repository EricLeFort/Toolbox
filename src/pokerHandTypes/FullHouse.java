package pokerHandTypes;

import java.util.Arrays;

import pokerTools.Card;

/**
 * @author Eric Le Fort
 * @version 01
 */

public class FullHouse implements PokerHand{
	private Card[] hand;
	private Card triple, pair;
	
	/**
	 * Creates this <code>FullHouse</code> according to the five <code>Card</code>s passed in.
	 * Assumes that the array passed in is exactly five <code>Card</code>s long.
	 * @param hand - The <code>Card</code>s representing this hand.
	 */
	public FullHouse(Card[] hand){
		Arrays.sort(hand);
		this.hand = hand;
		
		/*
		 * Two configurations:
		 * XXXYY
		 * YYXXX
		 */
		triple = hand[2];
		if(hand[1].getCardValue() == hand[2].getCardValue()){
			pair = hand[4];
		}else{
			pair = hand[0];
		}
	}//Constructor
	
	/**
	 * Compares this <code>FullHouse</code> to another <code>PokerHand</code>.
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
		FullHouse otherFullHouse;
		
		if(other instanceof FullHouse){
			otherFullHouse = (FullHouse)other;
			if(triple.compareTo(otherFullHouse.getTriple()) < 0){
				return -1;
			}else if(triple.compareTo(otherFullHouse.getTriple()) == 0){
				if(pair.compareTo(otherFullHouse.getPair()) < 0){
					return -1;
				}else if(pair.compareTo(otherFullHouse.getPair()) == 0){
					return 0;
				}
			}
			return 1;
		}
		
		if(other instanceof StraightFlush
				|| other instanceof FourOfAKind){
			return -1;
		}
		return 1;
	}//compareTo()
	
	/**
	 * The card value of the three-of-a-kind portion of this full house.
	 * @return A <code>Card</code> representing the card value of the three-of-a-kind portion of this full house.
	 */
	public Card getTriple(){ return triple; }//getTriple()
	
	/**
	 * The card value of the pair portion of this full house.
	 * @return A <code>Card</code> representing the card value of the pair portion of this full house.
	 */
	public Card getPair(){ return pair; }//getPair()
	
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
		if(triple.getCardValue() == 4){
			return "full house, " + triple.getName() + "es full of " + pair.getName() + "s";
		}else if(pair.getCardValue() == 4){
			return "full house, " + triple.getName() + "s full of " + pair.getName() + "es";
		}
		return "full house, " + triple.getName() + "s full of " + pair.getName() + "s";
	}//toString()
	
}//FullHouse
