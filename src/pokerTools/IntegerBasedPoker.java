package pokerTools;
/**
 * @author Eric Le Fort
 * @version 01
 */

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

public class IntegerBasedPoker{
	
	/**
	 * Creates a new deck of cards and returns a shuffled version.
	 * @return shuffledDeck
	 */
	public static ArrayList<Card> shuffleUp(){
		ArrayList<Card> orderedDeck = new ArrayList<Card>();
		ArrayList<Card> shuffledDeck = new ArrayList<Card>();
		int highValue = 52, random;
		
		for(int i = 0; i < 52; i++){							//Populates the orderedDeck.
			orderedDeck.add(new Card(i));
		}
		
		for(int i = 0; i < 52; i++){
			random = (int)(Math.random() * (highValue));	//Creates range from first index to last index of orderedDeck.
			highValue--;
			shuffledDeck.add(orderedDeck.remove(random));
		}
		return shuffledDeck;
	}//getShuffledDeck()
	
	/**
	 * Decides what hand a certain player has given the cards in the player's hand and those on the field. Returns the corresponding value.
	 * returns:
	 * 1 - High Card
	 * 2 - Pair
	 * 3 - Two Pair
	 * 4 - Three of a Kind
	 * 5 - Straight
	 * 6 - Flush
	 * 7 - Full House
	 * 8 - Four of a Kind
	 * 9 - Straight Flush
	 * 10 - Royal Flush
	 * 
	 * @param fullHand
	 * @return handType (between 1 and 10)
	 */
	public static int[] checkHand(ArrayList<Card> fullHand, String playerName){
		int[] handType = new int[2], setType = new int[2];
		//fullHand should be passed in by creating an ArrayList and using .addAll(field), .addAll(table.get(index).getHand())
		
		handType = flushValue(fullHand);
		if(handType[0] == 0){						//Checks to see if there was a flush detected and looks for straights if not.
			handType = straightValue(fullHand);
		}
		setType = checkSets(fullHand);
		if(setType[0] >= 7 && handType[0] < 9){		//Checks to make sure it's not a straight or royal flush and that the hand type is a full house or four-of-a-kind.
			handType = setType;
		}else if(handType[0] < 5){					//Checks if handType[0] is less than that representing a straight.	
			handType = setType;
		}
		System.out.println(playerName + ": ");
		
		System.out.println(getHandMessage(handType));//TEMP
		
		return handType;
	}//checkHand()	
	
	/**
	 * Checks to see if the cards given will represent a flush and returns an int from 0-10.
	 * 0 - No straight.
	 * 1 - 5 High
	 * 2 - 6 High
	 * 3 - 7 High
	 * 4 - 8 High
	 * 5 - 9 High
	 * 6 - 10 High
	 * 7 - Jack High
	 * 8 - Queen High
	 * 9 - King High
	 * 10 - Ace High
	 * @param hand
	 * @return flush
	 */
	public static int[] flushValue(ArrayList<Card> fullHand){
		Card currentCard;
		int[] handType = {0, 0, 0}, straightType = {0, 0};
		int highClub = 0, highDiamond = 0, highHeart = 0, highSpade = 0, currentCardValue, suit;
		int numClub = 0, numDiamond = 0, numHeart = 0, numSpade = 0;

		for(int i = 0; i < fullHand.size(); i++){
			currentCard = fullHand.get(i);
			currentCardValue = currentCard.getCardValue();
			suit = fullHand.get(i).getSuit();
			if(suit == 0){
				numClub++;
				if(currentCardValue > highClub){
					highClub = currentCardValue;
				}
			}else if(suit == 1){
				numDiamond++;
				if(currentCardValue > highDiamond){
					highDiamond = currentCardValue;
				}
			}else if(suit == 2){
				numHeart++;
				if(currentCardValue > highHeart){
					highHeart = currentCardValue;
				}
			}else if(suit == 3){
				numSpade++;
				if(currentCardValue > highSpade){
					highSpade = currentCardValue;
				}
			}
		}//For loop to check how many of each suit is in the players hand.

		if(numClub > 4){
			handType[0] = 6;
			handType[1] = highClub - 2;
		}else if(numDiamond > 4){
			handType[0] = 6;
			handType[1] = highDiamond - 2;
		}else if(numHeart > 4){
			handType[0] = 6;
			handType[1] = highHeart - 2;
		}else if(numSpade > 4){
			handType[0] = 6;
			handType[1] = highSpade - 2;
		}
		
		if(handType[0] != 0){
			straightType = straightValue(fullHand);
			if(straightType[0] != 0 && straightType[1] == 10){	//Royal Flush.
				handType[0] = 10;
				handType[1] = 0;
			}else if(straightType[0] != 0){						//Straight Flush.
				handType[0] = 9;
				handType[1] = straightType[1];
			}
		}
		return handType;
	}//isFlush()

	/**
	 * Checks to see if the given cards represent a straight and returns an int from 0-10.
	 * 0 - No straight.
	 * 1 - 5 High
	 * 2 - 6 High
	 * 3 - 7 High
	 * 4 - 8 High
	 * 5 - 9 High
	 * 6 - 10 High
	 * 7 - Jack High
	 * 8 - Queen High
	 * 9 - King High
	 * 10 - Ace High
	 * @param hand
	 * @return straight
	 */
	public static int[] straightValue(ArrayList<Card> fullHand){
		ArrayList<Integer> values = new ArrayList<Integer>();
		int[] straightType = {0, 0, 0};
		int count = 1, start;
		
		for(int i = 0; i < fullHand.size(); i++){
			values.add(fullHand.get(i).getCardValue());
		}//populates array with card values of the player's hand (without taking suit into account) and the community cards.
		
		Collections.sort(values);
		
		for(int i = 0; i < fullHand.size() - 4; i++){	//TEMP replace hand.size() - 4 to be 3? size will always be 7 presumably
			count = 1;
			start = values.get(i);
			if(values.contains(start - 1)){
				count++;
				if(values.contains(start - 2)){
					count++;
					if(values.contains(start - 3)){
						count++;
						if(values.contains(start - 4)){
							count++;
								straightType[1] = start - 2;
						}
					}
				}
			}
			if(values.contains(start + 1)){
				count++;
				if(count >= 5){
					straightType[1] = start - 1;
				}
				if(values.contains(start + 2)){
					count++;
					if(count >= 5){
						straightType[1] = start;
					}
					if(values.contains(start + 3)){
						count++;
						if(count >= 5){
							straightType[1] = start + 1;
						}
						if(values.contains(start + 4)){
							count++;
							if(count >= 5){
								straightType[1] = start + 2;
							}
							if(values.contains(start + 5)){
								count++;
								straightType[1] = start + 3;
								if(values.contains(start + 6)){
									count++;
									straightType[1] = start + 4;
								}//start + 6
							}//start + 5
						}//start + 4
					}//start + 3
				}//start + 2
			}//start + 1
		}//Checks for any type of straight except 5-high.
		
		if(values.get(values.size() - 1) == 12 && straightType[1] < 5){	
			if(values.contains(0) && values.contains(1) && values.contains(2) && values.contains(3)){
				straightType[1] = 1;
				if(values.contains(4)){
					straightType[1] = 2;
					if(values.contains(5)){
						straightType[1] = 3;
					}
				}
				count = 5;
			}
		}//Checks for the A, 2, 3, 4, 5 case of a straight.
		
		if(straightType[1] > 0){
			straightType[0] = 5;
		}
	
		return straightType;
	}
	
	/**
	 * Checks to see if the user has a four-of-a-kind, a full house, a three-of-a-kind, a two-pair, a 
	 * single pair or none of them. 
	 * 1 - High Card
	 * 2 - Pair
	 * 3 - Two pair
	 * 4 - Three-of-a-Kind
	 * 7 - Full House
	 * 8 - Four-of-a-Kind
	 * @param hand
	 * @return set
	 */
	public static int[] checkSets(ArrayList<Card> hand){
		ArrayList<Integer> values = new ArrayList<Integer>();
		ArrayList<int[]> sets = new ArrayList<int[]>();
		int[] handType = {0, 0, 0};
		int currentValue, setCount = 1;
		handType[0] = 1;
		
		for(int i = 0; i < hand.size(); i ++){	//Allocates the card values of the given ArrayList<Card> into a list.
			values.add(hand.get(i).getCardValue());
		}
		values.add(500);						//Ensures all critical values are reached since the loop that checks needs to check one card past the last entry.
		
		Collections.sort(values);				//List of values from 0-12 corresponding to 2-A. (in that order)
		
		for(int i = 0; i < values.size() - 1; i++){
			int[] holder = new int[2];
			currentValue = values.get(i);
			
			if(values.get(i + 1) == currentValue){
				setCount++;
			}else{
				holder[0] = setCount;
				holder[1] = currentValue;
				sets.add(holder);
				setCount = 1;
			}
		}//Checks all cards to determine any sets present in the hand.
		
		for(int i = 0; i < sets.size(); i++){									//checks all sets.
			if(sets.get(i)[0] == 4){
				handType = highPairValue(sets, 8);
			}else if(sets.get(i)[0] == 3 && handType[0] < 8){					//At least Three-of-a-Kind present.
				if(handType[0] == 2 || handType[0] == 3 || handType[0] == 4){	//Full House.
					handType = fullHouseValue(sets);
				}else if(handType[0] < 7){										//Three-of-a-Kind.
					handType = highPairValue(sets, 4);
				}
			}else if(sets.get(i)[0] == 2 && handType[0] < 8){					//At least a Pair present.
				if(handType[0] == 2){											//Two Pair.
					handType = twoPairValue(sets);
				}else if(handType[0] == 4){										//Full House.
					handType = fullHouseValue(sets);
				}else if(handType[0] < 3){										//Pair.
					handType = highPairValue(sets, 2);
				}
			}
		}
		
		if(handType[0] == 1){													//Checks for the high card when no other sets are present.
			handType[1] = values.get(values.size() - 2) % 13;					
			handType[2] = 0;
		}else if(handType[0] != 7){												//Checks for high card if hand isn't a full house.
			int highSet = 0, highValue = -1, secondValue = -1, setValue;
			
			for(int i = 0; i < sets.size(); i++){								//Goes through sets, remove cards represented by highest sets.
				setCount = sets.get(i)[0];
				setValue = sets.get(i)[1];
				if(setCount == 4){
					if(highSet != 4){
						highSet = 4;
						highValue = setValue;
					}else{
						if(highValue < setValue){
							highValue = setValue;
						}
					}
				}else if(setCount == 3){
					if(highSet == 3){
						if(setValue > highValue){
							highValue = setValue;
						}
					}else if(highSet == 2){
						secondValue = highValue;
						highValue = setValue;
					}else if(highSet < 2){
						highValue = setValue;
					}
					
					if(highSet != 4){
						highSet = 3;
					}
				}else if(setCount == 2){
					if(highSet == 3){
						if(setValue > secondValue){
							secondValue = setValue;
						}
					}else if(highSet == 2){
						if(setValue > highValue){
							secondValue = highValue;
							highValue = setValue;
						}else if(setValue > secondValue){
							secondValue = setValue;
						}
					}else if(highSet < 2){
						highValue = setValue;
					}
					
					if(highSet < 2){
						highSet = 2;
					}
				}
			}

			while(values.contains(highValue)){				//Remove all cards involved with the actual hand
				values.remove((Integer)highValue);
			}
			while(values.contains(secondValue)){
				values.remove((Integer)secondValue);
			}
			
			handType[2] = values.get(values.size() - 2);
			
		}
		return handType;
	}//checkSet(ArrayList<Card> hand)
	
	/**
	 * Gets passed an ArrayList of a set of values that represent the occurrences of a card value (at index 0) and the value of the card at (index 1).
	 * Uses this to determine the value of the full house present.
	 * @param sets
	 * @return value
	 */
	public static int[] fullHouseValue(ArrayList<int[]> sets){
		int[] handType = {7, 0, 0};
		int  current, currentValue, highTrip = -1, highDbl = -1;
		
		for(int i = 0; i < sets.size(); i++){					//Checks for the highest triple and double.
			current = sets.get(i)[0]; 
			currentValue = sets.get(i)[1];
			if(current == 3 && currentValue > highTrip){		//New high triple
				if(highTrip > highDbl){							//Old triple greater than old double.
					highDbl = highTrip;
				}
				highTrip = currentValue;
			}else if(current >= 2 && currentValue > highDbl){	//New high double.
				highDbl = currentValue;
			}
		}
		
		if(highTrip == 0){										//Checks for twos since it happens to be a break in the pattern.
			handType[1] = highDbl;
		}else if(highTrip > highDbl){
			handType[1] = (12 * highTrip) + highDbl + 1;
		}else if(highTrip < highDbl){
			handType[1] = (12 * highTrip) + highDbl;
		}else{
			handType[1] = 0;
		}
		
		return handType;
	}//flushValue(ArrayList<int[]> sets)

	/**
	 * Gets passed an ArrayList of a set of values that represent the occurrences of a card value (at index 0) and the value of the card (at index 1).
	 * Uses this ArrayList to determine the specific two pair a player has. Returns 0 if there isn't a two pair.
	 * @param sets
	 * @return value
	 */
	public static int[] twoPairValue(ArrayList<int[]> sets){
		int[] handType = {3, 0, 0};
		int largest = 0, secondLargest = 0, setSize, setValue;
		
		for(int i = 0; i < sets.size(); i++){
			setSize = sets.get(i)[0];
			setValue = sets.get(i)[1];
			if(setSize == 2){
				if(setValue > largest){
					secondLargest = largest;
					largest = setValue;
				}else if(setValue > secondLargest){
					secondLargest = setValue;
				}
			}
		}
		
		if(largest == 12){			//Pair of Aces
			if(secondLargest == 11){
				handType[1] = 78;
			}else if(secondLargest == 10){
				handType[1] = 77;
			}else if(secondLargest == 9){
				handType[1] = 76;
			}else if(secondLargest == 8){
				handType[1] = 75;
			}else if(secondLargest == 7){
				handType[1] = 74;
			}else if(secondLargest == 6){
				handType[1] = 73;
			}else if(secondLargest == 5){
				handType[1] = 72;
			}else if(secondLargest == 4){
				handType[1] = 71;
			}else if(secondLargest == 3){
				handType[1] = 70;
			}else if(secondLargest == 2){
				handType[1] = 69;
			}else if(secondLargest == 1){
				handType[1] = 68;
			}else if(secondLargest == 0){
				handType[1] = 67;
			}
		}else if(largest == 11){	//Pair of Kings
			if(secondLargest == 10){
				handType[1] = 66;
			}else if(secondLargest == 9){
				handType[1] = 65;
			}else if(secondLargest == 8){
				handType[1] = 64;
			}else if(secondLargest == 7){
				handType[1] = 63;
			}else if(secondLargest == 6){
				handType[1] = 62;
			}else if(secondLargest == 5){
				handType[1] = 61;
			}else if(secondLargest == 4){
				handType[1] = 60;
			}else if(secondLargest == 3){
				handType[1] = 59;
			}else if(secondLargest == 2){
				handType[1] = 58;
			}else if(secondLargest == 1){
				handType[1] = 57;
			}else if(secondLargest == 0){
				handType[1] = 56;
			}
		}else if(largest == 10){	//Pair of Queens
			if(secondLargest == 9){
				handType[1] = 55;
			}else if(secondLargest == 8){
				handType[1] = 54;
			}else if(secondLargest == 7){
				handType[1] = 53;
			}else if(secondLargest == 6){
				handType[1] = 52;
			}else if(secondLargest == 5){
				handType[1] = 51;
			}else if(secondLargest == 4){
				handType[1] = 50;
			}else if(secondLargest == 3){
				handType[1] = 49;
			}else if(secondLargest == 2){
				handType[1] = 48;
			}else if(secondLargest == 1){
				handType[1] = 47;
			}else if(secondLargest == 0){
				handType[1] = 46;
			}
		}else if(largest == 9){		//Pair of Jacks
			if(secondLargest == 8){
				handType[1] = 45;
			}else if(secondLargest == 7){
				handType[1] = 44;
			}else if(secondLargest == 6){
				handType[1] = 43;
			}else if(secondLargest == 5){
				handType[1] = 42;
			}else if(secondLargest == 4){
				handType[1] = 41;
			}else if(secondLargest == 3){
				handType[1] = 40;
			}else if(secondLargest == 2){
				handType[1] = 39;
			}else if(secondLargest == 1){
				handType[1] = 38;
			}else if(secondLargest == 0){
				handType[1] = 37;
			}
		}else if(largest == 8){		//Pair of Tens
			if(secondLargest == 7){
				handType[1] = 36;
			}else if(secondLargest == 6){
				handType[1] = 35;
			}else if(secondLargest == 5){
				handType[1] = 34;
			}else if(secondLargest == 4){
				handType[1] = 33;
			}else if(secondLargest == 3){
				handType[1] = 32;
			}else if(secondLargest == 2){
				handType[1] = 31;
			}else if(secondLargest == 1){
				handType[1] = 30;
			}else if(secondLargest == 0){
				handType[1] = 29;
			}
		}else if(largest == 7){		//Pair of Nines
			if(secondLargest == 6){
				handType[1] = 28;
			}else if(secondLargest == 5){
				handType[1] = 27;
			}else if(secondLargest == 4){
				handType[1] = 26;
			}else if(secondLargest == 3){
				handType[1] = 25;
			}else if(secondLargest == 2){
				handType[1] = 24;
			}else if(secondLargest == 1){
				handType[1] = 23;
			}else if(secondLargest == 0){
				handType[1] = 22;
			}
		}else if(largest == 6){		//Pair of Eights
			if(secondLargest == 5){
				handType[1] = 21;
			}else if(secondLargest == 4){
				handType[1] = 20;
			}else if(secondLargest == 3){
				handType[1] = 19;
			}else if(secondLargest == 2){
				handType[1] = 18;
			}else if(secondLargest == 1){
				handType[1] = 17;
			}else if(secondLargest == 0){
				handType[1] = 16;
			}
		}else if(largest == 5){		//Pair of Sevens
			if(secondLargest == 4){
				handType[1] = 15;
			}else if(secondLargest == 3){
				handType[1] = 14;
			}else if(secondLargest == 2){
				handType[1] = 13;
			}else if(secondLargest == 1){
				handType[1] = 12;
			}else if(secondLargest == 0){
				handType[1] = 11;
			}
		}else if(largest == 4){		//Pair of Sixes
			if(secondLargest == 3){
				handType[1] = 10;
			}else if(secondLargest == 2){
				handType[1] = 9;
			}else if(secondLargest == 1){
				handType[1] = 8;
			}else if(secondLargest == 0){
				handType[1] = 7;
			}
		}else if(largest == 3){		//Pair of Fives
			if(secondLargest == 2){
				handType[1] = 6;
			}else if(secondLargest == 1){
				handType[1] = 5;
			}else if(secondLargest == 0){
				handType[1] = 4;
			}
		}else if(largest == 2){		//Pair of Fours
			if(secondLargest == 1){
				handType[1] = 3;
			}else if(secondLargest == 0){
				handType[1] = 2;
			}
		}else if(largest == 1){		//Pair of Threes
			if(secondLargest == 0){
				handType[1] = 1;
			}
		}
		
		return handType;
	}//twoPairValue(ArrayList<int[]> sets)

	/**
	 * Gets passed an ArrayList of a set of values that represent the occurrences of a card value (at index 0) and the value of the card at (index 1).
	 * Uses this to determine the highest pair in this set.
	 * @param sets
	 * @return value
	 */
	public static int[] highPairValue(ArrayList<int[]> sets, int setType){
		int[] handType = {setType, 0, 0};
		int highPair = 0;
		for(int i = 0; i < sets.size(); i++){
			if(sets.get(i)[0] == 4){
				highPair = 4;
				handType[1] = sets.get(i)[1];
				i = 10;
			}else if(sets.get(i)[0] == 3){
				highPair = 3;
				if(sets.get(i)[1] > handType[1]){
					handType[1] = sets.get(i)[1];
				}
			}else if(sets.get(i)[0] == 2 && highPair < 3){
				if(sets.get(i)[1] > handType[1]){
					handType[1] = sets.get(i)[1];
				}
			}
		}
		return handType;
	}//highPairValue(ArrayList<int[]> sets)
	
	/**
	 * Generates a random number within the specified range(from 0 to the range value inclusive). Returns -1 if an error occurs.
	 * @param range
	 * @return rnd
	 */
	public static int rndNumGen(int range){
		int rnd = 0, stdRnd = (int)(Math.random() * 500);							//Arbitrary value of 500 to give a decent range to the seed value.
		
		try{
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
			sr.setSeed(stdRnd);
			rnd = sr.nextInt(range + 1);
		}catch(NoSuchAlgorithmException nsae){
			return -1;
		}catch(NoSuchProviderException nspe){
			return -1;
		}
		
		return rnd;
	}//rndNumGen(int range)

	/**
	 * Checks the handType specified by the int[] list of length two and returns the appropriate message.
	 * @param handType
	 * @return message
	 */
	public static String getHandMessage(int[] handType){
		String message = "";
		
		switch(handType[0]){
		case 10:									//Royal flush.
			message = "Royal Flush";
			break;
			
		case 9: case 6: case 5:						//Straight and/or flush.
			if(handType[0] == 9){
				message = "Straight Flush, ";
			}else if(handType[0] == 6){
				message = "Flush, ";
			}else if(handType[0] == 5){
				message = "Straight, ";
			}
			if(handType[1] == 1){	
				message += "5 high";
			}else if(handType[1] == 2){
				message += "6 high";
			}else if(handType[1] == 3){
				message += "7 high";
			}else if(handType[1] == 4){
				message += "8 high";
			}else if(handType[1] == 5){
				message += "9 high";
			}else if(handType[1] == 6){
				message += "10 high";
			}else if(handType[1] == 7){
				message += "Jack high";
			}else if(handType[1] == 8){
				message += "Queen high";
			}else if(handType[1] == 9){
				message += "King high";
			}else if(handType[1] == 10){
				message += "Ace high";
			}
			break;
			
		case 8: case 4: case 2:						//Pairs, three- and four-of-a-kind.
			if(handType[0] == 8){
				message = "Four-of-a-Kind, ";
			}else if(handType[0] == 4){
				message = "Three-of-a-Kind, ";
			}else if(handType[0] == 2){
				message = "Pair of ";
			}

			if(handType[1] == 12){
				message += "Aces";
			}else if(handType[1] == 11){
				message += "Kings";
			}else if(handType[1] == 10){
				message += "Queens";
			}else if(handType[1] == 9){
				message += "Jacks";
			}else if(handType[1] == 8){
				message += "Tens";
			}else if(handType[1] == 7){
				message += "Nines";
			}else if(handType[1] == 6){
				message += "Eights";
			}else if(handType[1] == 5){
				message += "Sevens";
			}else if(handType[1] == 4){
				message += "Sixes";
			}else if(handType[1] == 3){
				message += "Fives";
			}else if(handType[1] == 2){
				message += "Fours";
			}else if(handType[1] == 1){
				message += "Threes";
			}else if(handType[1] == 0){
				message += "Twos";
			}
			break;
			
		case 7:										//Full House.
			message = "Full House" + fullHouseMessage(handType[1]);
			break;
			
		case 3:										//Two Pair.
			message = "Two Pair" + twoPairMessage(handType[1]);
			break;
			
		case 1:										//HighCard.
			message = new Card(handType[1]).getName() + " high";
			break;
		}
		
		return message;
	}//getHandMessage(int[] handType)
	
	/**
	 * Depending on the value that was put it, this method returns a String that represents the two pair.
	 * @param value
	 * @return message
	 */
	public static String twoPairMessage(int value){
		String message = ", ", kings = " and Kings", queens = " and Queens", jacks = " and Jacks",
				tens = " and tens", nines = " and Nines", eights = " and Eights", sevens = " and Sevens",
				sixes = " and Sixes", fives = " and Fives", fours = " and Fours", threes = " and Threes", twos = " and Twos";
		
		if(value > 66){
			message += "Aces";
			if(value == 78){
				message += kings;
			}else if(value == 77){
				message += queens;
			}else if(value == 76){
				message += jacks;
			}else if(value == 75){
				message += tens;
			}else if(value == 74){
				message += nines;
			}else if(value == 73){
				message += eights;
			}else if(value == 72){
				message += sevens;
			}else if(value == 71){
				message += sixes;
			}else if(value == 70){
				message += fives;
			}else if(value == 69){
				message += fours;
			}else if(value == 68){
				message += threes;
			}else if(value == 67){
				message += twos;
			}
		}else if(value > 55){
			message += "Kings";
			if(value == 66){
				message += queens;
			}else if(value == 65){
				message += jacks;
			}else if(value == 64){
				message += tens;
			}else if(value == 63){
				message += nines;
			}else if(value == 62){
				message += eights;
			}else if(value == 61){
				message += sevens;
			}else if(value == 60){
				message += sixes;
			}else if(value == 59){
				message += fives;
			}else if(value == 58){
				message += fours;
			}else if(value == 57){
				message += threes;
			}else if(value == 56){
				message += twos;
			}
		}else if(value > 45){
			message += "Queens";
			if(value == 55){
				message += jacks;
			}else if(value == 54){
				message += tens;
			}else if(value == 53){
				message += nines;
			}else if(value == 52){
				message += eights;
			}else if(value == 51){
				message += sevens;
			}else if(value == 50){
				message += sixes;
			}else if(value == 49){
				message += fives;
			}else if(value == 48){
				message += fours;
			}else if(value == 47){
				message += threes;
			}else if(value == 46){
				message += twos;
			}
		}else if(value > 36){
			message += "Jacks";
			if(value == 45){
				message += tens;
			}else if(value == 44){
				message += nines;
			}else if(value == 43){
				message += eights;
			}else if(value == 42){
				message += sevens;
			}else if(value == 41){
				message += sixes;
			}else if(value == 40){
				message += fives;
			}else if(value == 39){
				message += fours;
			}else if(value == 38){
				message += threes;
			}else if(value == 37){
				message += twos;
			}
		}else if(value > 28){
			message += "Tens";
			if(value == 36){
				message += nines;
			}else if(value == 35){
				message += eights;
			}else if(value == 34){
				message += sevens;
			}else if(value == 33){
				message += sixes;
			}else if(value == 32){
				message += fives;
			}else if(value == 31){
				message += fours;
			}else if(value == 30){
				message += threes;
			}else if(value == 29){
				message += twos;
			}
		}else if(value > 21){
			message += "Nines";
			if(value == 28){
				message += eights;
			}else if(value == 27){
				message += sevens;
			}else if(value == 26){
				message += sixes;
			}else if(value == 25){
				message += fives;
			}else if(value == 24){
				message += fours;
			}else if(value == 23){
				message += threes;
			}else if(value == 22){
				message += twos;
			}
		}else if(value > 15){
			message += "Eights";
			if(value == 21){
				message += sevens;
			}else if(value == 20){
				message += sixes;
			}else if(value == 19){
				message += fives;
			}else if(value == 18){
				message += fours;
			}else if(value == 17){
				message += threes;
			}else if(value == 16){
				message += twos;
			}
		}else if(value > 10){
			message += "Sevens";
			if(value == 15){
				message += sixes;
			}else if(value == 14){
				message += fives;
			}else if(value == 13){
				message += fours;
			}else if(value == 12){
				message += threes;
			}else if(value == 11){
				message += twos;
			}
		}else if(value > 6 ){
			message += "Sixes";
			if(value == 10){
				message += fives;
			}else if(value == 9){
				message += fours;
			}else if(value == 8){
				message += threes;
			}else if(value == 7){
				message += twos;
			}
		}else if(value > 3){
			message += "Fives";
			if(value == 6){
				message += fours;
			}else if(value == 5){
				message += threes;
			}else if(value == 4){
				message += twos;
			}
		}else if(value > 1){
			message += "Fours";
			if(value == 3){
				message += fours;
			}else if(value == 2){
				message += threes;
			}
		}else if(value == 1){
			message += "Threes" + twos;
		}
		
		return message;
	}//twoPairMessage(int value)

	/**
	 * Depending on the value that was put it, this method returns a String that represents the full house.
	 * @param value
	 * @return message
	 */
	public static String fullHouseMessage(int value){
		String message = ", ", aces = " full of Aces", kings = " full of Kings", queens = " full of Queens",jacks = " full of Jacks",
				tens = " full of Tens", nines = " full of Nines", eights = " full of Eights", 
				sevens = " full of Sevens", sixes = " full of Sixes", fives = " full of Fives",
				fours = " full of Fours", threes = " full of Threes", twos = " full of Twos";
		
		if(value > 144){						//Three Aces
			if(value == 156){
				message += "Aces" + kings;
			}else if(value == 155){
				message += "Aces" + queens;
			}else if(value == 154){
				message += "Aces" + jacks;
			}else if(value == 153){
				message += "Aces" + tens;
			}else if(value == 152){
				message += "Aces" + nines;
			}else if(value == 151){
				message += "Aces" + eights;
			}else if(value == 150){
				message += "Aces" + sevens;
			}else if(value == 149){
				message += "Aces" + sixes;
			}else if(value == 148){
				message += "Aces" + fives;
			}else if(value == 147){
				message += "Aces" + fours;
			}else if(value == 146){
				message += "Aces" + threes;
			}else if(value == 145){
				message += "Aces" + twos;
			}
		}else if(value > 132){					//Three Kings
			if(value == 144){
				message += "Kings" + aces;
			}else if(value == 143){
				message += "Kings" + queens;
			}else if(value == 142){
				message += "Kings" + jacks;
			}else if(value == 141){
				message += "Kings" + tens;
			}else if(value == 140){
				message += "Kings" + nines;
			}else if(value == 139){
				message += "Kings" + eights;
			}else if(value == 138){
				message += "Kings" + sevens;
			}else if(value == 137){
				message += "Kings" + sixes;
			}else if(value == 136){
				message += "Kings" + fives;
			}else if(value == 135){
				message += "Kings" + fours;
			}else if(value == 134){
				message += "Kings" + threes;
			}else if(value == 133){
				message += "Kings" + twos;
			}
		}else if(value > 120){					//Three Queens
			if(value == 132){
				message += "Queens" + aces;
			}else if(value == 131){
				message += "Queens" + kings;
			}else if(value == 130){
				message += "Queens" + jacks;
			}else if(value == 129){
				message += "Queens" + tens;
			}else if(value == 128){
				message += "Queens" + nines;
			}else if(value == 127){
				message += "Queens" + eights;
			}else if(value == 126){
				message += "Queens" + sevens;
			}else if(value == 125){
				message += "Queens" + sixes;
			}else if(value == 124){
				message += "Queens" + fives;
			}else if(value == 123){
				message += "Queens" + fours;
			}else if(value == 122){
				message += "Queens" +  threes;
			}else if(value == 121){
				message += "Queens" + twos;
			}
		}else if(value > 108){					//Three Jacks
			if(value == 120){
				message += "Jacks" + aces;
			}else if(value == 119){
				message += "Jacks" + kings;
			}else if(value == 118){
				message += "Jacks" + queens;
			}else if(value == 117){
				message += "Jacks" + tens;
			}else if(value == 116){
				message += "Jacks" + nines;
			}else if(value == 115){
				message += "Jacks" + eights;
			}else if(value == 114){
				message += "Jacks" + sevens;
			}else if(value == 113){
				message += "Jacks" + sixes;
			}else if(value == 112){
				message += "Jacks" + fives;
			}else if(value == 111){
				message += "Jacks" + fours;
			}else if(value == 110){
				message += "Jacks" + threes;
			}else if(value == 109){
				message += "Jacks" + twos;
			}
		}else if(value > 96){					//Three Tens
			if(value == 108){
				message += "Tens" + aces;
			}else if(value == 107){
				message += "Tens" + kings;
			}else if(value == 106){
				message += "Tens" + queens;
			}else if(value == 105){
				message += "Tens" + jacks;
			}else if(value == 104){
				message += "Tens" + nines;
			}else if(value == 103){
				message += "Tens" + eights;
			}else if(value == 102){
				message += "Tens" + sevens;
			}else if(value == 101){
				message += "Tens" + sixes;
			}else if(value == 100){
				message += "Tens" + fives;
			}else if(value == 99){
				message += "Tens" + fours;
			}else if(value == 98){
				message += "Tens" + threes;
			}else if(value == 97){
				message += "Tens" + twos;
			}
		}else if(value > 84){					//Three Nines
			if(value == 96){
				message += "Nines" + aces;
			}else if(value == 95){
				message += "Nines" + kings;
			}else if(value == 94){
				message += "Nines" + queens;
			}else if(value == 93){
				message += "Nines" + jacks;
			}else if(value == 92){
				message += "Nines" + tens;
			}else if(value == 91){
				message += "Nines" + eights;
			}else if(value == 90){
				message += "Nines" + sevens;
			}else if(value == 89){
				message += "Nines" + sixes;
			}else if(value == 88){
				message += "Nines" + fives;
			}else if(value == 87){
				message += "Nines" + fours;
			}else if(value == 86){
				message += "Nines" + threes;
			}else if(value == 85){
				message += "Nines" + twos;
			}
		}else if(value > 72){					//Three Eights
			if(value == 84){
				message += "Eights" + aces;
			}else if(value == 83){
				message += "Eights" + kings;
			}else if(value == 82){
				message += "Eights" + queens;
			}else if(value == 81){
				message += "Eights" + jacks;
			}else if(value == 80){
				message += "Eights" + tens;
			}else if(value == 79){
				message += "Eights" + nines;
			}else if(value == 78){
				message += "Eights" + sevens;
			}else if(value == 77){
				message += "Eights" + sixes;
			}else if(value == 76){
				message += "Eights" + fives;
			}else if(value == 75){
				message += "Eights" + fours;
			}else if(value == 74){
				message += "Eights" + threes;
			}else if(value == 73){
				message += "Eights" + twos;
			}
		}else if(value > 60){					//Three Sevens
			if(value == 72){
				message += "Sevens" + aces;
			}else if(value == 71){
				message += "Sevens" + kings;
			}else if(value == 70){
				message += "Sevens" + queens;
			}else if(value == 69){
				message += "Sevens" + jacks;
			}else if(value == 68){
				message += "Sevens" + tens;
			}else if(value == 67){
				message += "Sevens" + nines;
			}else if(value == 66){
				message += "Sevens" + eights;
			}else if(value == 65){
				message += "Sevens" + sixes;
			}else if(value == 64){
				message += "Sevens" + fives;
			}else if(value == 63){
				message += "Sevens" + fours;
			}else if(value == 62){
				message += "Sevens" + threes;
			}else if(value == 61){
				message += "Sevens" + twos;
			}
		}else if(value > 48){					//Three Sixes
			if(value == 60){
				message += "Sixes" + aces;
			}else if(value == 59){
				message += "Sixes" + kings;
			}else if(value == 58){
				message += "Sixes" + queens;
			}else if(value == 57){
				message += "Sixes" + jacks;
			}else if(value == 56){
				message += "Sixes" + tens;
			}else if(value == 55){
				message += "Sixes" + nines;
			}else if(value == 54){
				message += "Sixes" + eights;
			}else if(value == 53){
				message += "Sixes" + sevens;
			}else if(value == 52){
				message += "Sixes" + fives;
			}else if(value == 51){
				message += "Sixes" + fours;
			}else if(value == 50){
				message += "Sixes" + threes;
			}else if(value == 49){
				message += "Sixes" + twos;
			}
		}else if(value > 36){					//Three Fives
			if(value == 48){
				message += "Fives" + aces;
			}else if(value == 47){
				message += "Fives" + kings;
			}else if(value == 46){
				message += "Fives" + queens;
			}else if(value == 45){
				message += "Fives" + jacks;
			}else if(value == 44){
				message += "Fives" + tens;
			}else if(value == 43){
				message += "Fives" + nines;
			}else if(value == 42){
				message += "Fives" + eights;
			}else if(value == 41){
				message += "Fives" + sevens;
			}else if(value == 40){
				message += "Fives" + sixes;
			}else if(value == 39){
				message += "Fives" + fours;
			}else if(value == 38){
				message += "Fives" + threes;
			}else if(value == 37){
				message += "Fives" + twos;
			}
		}else if(value > 24){					//Three Fours
			if(value == 36){
				message += "Fours" + aces;
			}else if(value == 35){
				message += "Fours" + kings;
			}else if(value == 34){
				message += "Fours" + queens;
			}else if(value == 33){
				message += "Fours" + jacks;
			}else if(value == 32){
				message += "Fours" + tens;
			}else if(value == 31){
				message += "Fours" + nines;
			}else if(value == 30){
				message += "Fours" + eights;
			}else if(value == 29){
				message += "Fours" + sevens;
			}else if(value == 28){
				message += "Fours" + sixes;
			}else if(value == 27){
				message += "Fours" + fives;
			}else if(value == 26){
				message += "Fours" + threes;
			}else if(value == 25){
				message += "Fours" + twos;
			}
		}else if(value > 12){					//Three Threes
			if(value == 24){
				message += "Threes" + aces;
			}else if(value == 23){
				message += "Threes" + kings;
			}else if(value == 22){
				message += "Threes" + queens;
			}else if(value == 21){
				message += "Threes" + jacks;
			}else if(value == 20){
				message += "Threes" + tens;
			}else if(value == 19){
				message += "Threes" + nines;
			}else if(value == 18){
				message += "Threes" + eights;
			}else if(value == 17){
				message += "Threes" + sevens;
			}else if(value == 16){
				message += "Threes" + sixes;
			}else if(value == 15){
				message += "Threes" + fives;
			}else if(value == 14){
				message += "Threes" + fours;
			}else if(value == 13){
				message += "Threes" + twos;
			}
		}else if(value > 0){					//Three Twos
			if(value == 12){
				message += "Twos" + aces;
			}else if(value == 11){
				message += "Twos" + kings;
			}else if(value == 10){
				message += "Twos" + queens;
			}else if(value == 9){
				message += "Twos" + jacks;
			}else if(value == 8){
				message += "Twos" + tens;
			}else if(value == 7){
				message += "Twos" + nines;
			}else if(value == 6){
				message += "Twos" + eights;
			}else if(value == 5){
				message += "Twos" + sevens;
			}else if(value == 4){
				message += "Twos" + sixes;
			}else if(value == 3){
				message += "Twos" + fives;
			}else if(value == 2){
				message += "Twos" + fours;
			}else if(value == 1){
				message += "Twos" + threes;
			}
		}else{
			message = "error in full house.. Value didn't exist.";
		}
		
		return message;
	}//flushMessage(int value)

}//IntegerBasedPoker