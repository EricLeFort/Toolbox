package pokerTools;

/**
 * @author Eric
 * @version 1.0
 */
public class Card implements Comparable<Card>{
	final private String cardName, name;
	final private int suit, cardValue;
	
	/**
	 * Takes the integer value of the card as input assuming the suits go from Clubs to Diamonds to Hearts to Spades.
	 * 2 represents the first card in the suit and Ace the last.
	 * @param arg1
	 */
	public Card(int value){
		this.suit = value / 13;
		this.cardValue = value % 13;
		
		if(cardValue == 0){
			name = "two";
		}else if(cardValue == 1){
			name = "three";
		}else if(cardValue == 2){
			name = "four";
		}else if(cardValue == 3){
			name = "five";
		}else if(cardValue == 4){
			name = "six";
		}else if(cardValue == 5){
			name = "seven";
		}else if(cardValue == 6){
			name = "eight";
		}else if(cardValue == 7){
			name = "nine";
		}else if(cardValue == 8){
			name = "ten";
		}else if(cardValue == 9){
			name = "jack";
		}else if(cardValue == 10){
			name = "queen";
		}else if(cardValue == 11){
			name = "king";
		}else if(cardValue == 12){
			name = "ace";
		}else{
			name = "ERROR";
		}
		
		if(suit == 0){
			cardName = name + " of clubs";
		}else if(suit == 1){
			cardName = name + " of diamonds";
		}else if(suit == 2){
			cardName = name + " of hearts";
		}else if(suit == 3){
			cardName = name + " of spades";
		}else{
			cardName = "ERROR";
		}
	}//Constructor
	
	/**
	 * Checks the appropriate suit based on the value of the card.
	 * @return name of suit or that input is invalid in the form of a String.
	 */
	public String getSuitName(){
		if(this.suit == 0){
			return "clubs";
		}else if(this.suit == 1){
			return "diamonds";
		}else if(this.suit == 2){
			return "hearts";
		}else if (this.suit == 3){
			return "spades";
		}else{
			return "Error in reading suit";
		}
	}//getSuitName()
	
	/**
	 * Returns the name of the card (e.g. Seven or King)
	 * @return name
	 */
	public String getName(){ return name; }//getName()
	
	/**
	 * Returns the integer value representing each suit.
	 * 0 - Clubs
	 * 1 - Diamonds
	 * 2 - Hearts
	 * 3 - Spades
	 * @return suit
	 */
	public int getSuit(){ return suit; }//getSuit()
	
	/**
	 * Returns the integer value representing the card.
	 * 0 - 2
	 * ...
	 * 12 - Ace
	 * @return cardValue
	 */
	public int getCardValue(){ return cardValue; }//getcardValue()
	
	/**
	 * Compares this <code>Card</code> to another <code>Card</code>'s value.
	 * Returns:
	 * 1  - This <code>Card</code> is higher.
	 * 0  - The <code>Card</code>s are equal.
	 * -1 - The other <code>Card</code> is higher.
	 * IMPORTANT: Suit is not considered, use <code>equals()</code> for that.
	 * 
	 * @param other - The other <code>Card</code>.
	 * @return -1, 0 or 1 based on which <code>Card</code> is best.
	 */
	@Override
	public int compareTo(Card other){
		if(cardValue > other.getCardValue()){
			return 1;
		}else if(cardValue < other.getCardValue()){
			return -1;
		}
		return 0;
	}//compareTo()
	
	/**
	 * Compares this <code>Card</code> to another <code>Object</code>.
	 * Returns true if the other <code>Object</code> is also a <code>Card</code> of the same value and suit.
	 * 
	 * @param other - The <code>Object</code> to compare this <code>Card</code> to.
	 * @return true if the <code>Cards</code> are equal or false otherwise.
	 */
	@Override
	public boolean equals(Object other){
		return other instanceof Card
				&& suit == ((Card)other).getSuit()
				&& cardValue == ((Card)other).getCardValue();
	}//equals()
	
	/**
	 * Returns the string representing the name of the card.
	 * i.e. "Six of clubs"
	 * @return cardName
	 */
	@Override
	public String toString(){ return cardName; }//toString()
	
}//Card
