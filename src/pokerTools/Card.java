package pokerTools;
/**
 * @author Eric
 * @version 1
 */

public class Card{
	private String cardName, name;
	private int suit, cardValue;
		
	/**
	 * Takes the integer value of the card as input assuming the suits go from Clubs to Diamonds to Hearts to Spades.
	 * 2 represents the first card in the suit and Ace the last.
	 * @param arg1
	 */
	public Card(int value){
		this.suit = value / 13;
		this.cardValue = value % 13;
		this.cardName = "";
		
		if(cardValue == 0){
			name = "2";
		}else if(cardValue == 1){
			name = "3";
		}else if(cardValue == 2){
			name = "4";
		}else if(cardValue == 3){
			name = "5";
		}else if(cardValue == 4){
			name = "6";
		}else if(cardValue == 5){
			name = "7";
		}else if(cardValue == 6){
			name = "8";
		}else if(cardValue == 7){
			name = "9";
		}else if(cardValue == 8){
			name = "10";
		}else if(cardValue == 9){
			name = "Jack";
		}else if(cardValue == 10){
			name = "Queen";
		}else if(cardValue == 11){
			name = "King";
		}else if(cardValue == 12){
			name = "Ace";
		}
			
		if(suit == 0){
			cardName = name + " of Clubs";
		}else if(suit == 1){
			cardName = name + " of Diamonds";
		}else if(suit == 2){
			cardName = name + " of Hearts";
		}else if(suit == 3){
			cardName = name + " of Spades";
		}

		
	}//Card(int value)

	/**
	 * Checks the appropriate suit based on the value of the card.
	 * @return name of suit or that input is invalid in the form of a String.
	 */
	public String getSuitName(){
		if(this.suit == 0){
			return "Clubs";
		}else if(this.suit == 1){
			return "Diamonds";
		}else if(this.suit == 2){
			return "Hearts";
		}else if (this.suit == 3){
			return "Spades";
		}else{
			return "Error in reading suit";
		}
	}//getSuitName()
	
	/**
	 * Returns the name of the card (i.e. 7 or King)
	 * @return name
	 */
	public String getName(){
		return this.name;
	}//getName()
	
	/**
	 * Returns the integer value representing each suit.
	 * 0 - Clubs
	 * 1 - Diamonds
	 * 2 - Hearts
	 * 3 - Spades
	 * @return suit
	 */
	public int getSuit(){
		return this.suit;
	}//getSuit()
	
	/**
	 * Returns the integer value representing the card.
	 * @return cardValue
	 */
	public int getCardValue(){
		return this.cardValue;
	}//getcardValue()
	
	/**
	 * Returns the string representing the name of the card.
	 * i.e. "6 of clubs"
	 * @return cardName
	 */
	public String getCardName(){
		return this.cardName;
	}//getCardName()
	
}//Card(int value)
