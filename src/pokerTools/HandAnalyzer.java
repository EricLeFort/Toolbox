package pokerTools;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

import pokerHandTypes.*;

/**
 * @author Eric Le Fort
 * @version 2.0
 */
public class HandAnalyzer{
	
	/**
	 * Takes in seven <code>Cards</code> and determines the best possible <code>PokerHand</code> from them.
	 * @param fullHand - The array of <code>Cards</code> to check for a hand.
	 * @return The best <code>PokerHand</code> possible from the cards given.
	 */
	public static PokerHand getPokerHand(Card[] fullHand){
		Arrays.sort(fullHand);
		PokerHand other = checkStraightFlush(fullHand);
		PokerHand sets;
		
		if(other instanceof StraightFlush){
			return other;
		}
		
		sets = checkSets(fullHand);
		if(sets instanceof FourOfAKind
				|| sets instanceof FullHouse){
			return sets;
		}
		
		other = checkFlush(fullHand);
		if(other instanceof Flush){
			return other;
		}
		
		other = checkStraight(fullHand);
		if(other instanceof Straight){
			return other;
		}
		
		if(sets != null){
			return sets;
		}
		
		return checkHighCard(fullHand);
	}//checkHand()	
	
	/**
	 * Checks to see if the given cards represent a straight flush.
	 * @param fullHand - The array of cards passed in to check for a straight flush.
	 * @return A <code>StraightFlush</code> representing the value of the hand.
	 */
	private static StraightFlush checkStraightFlush(Card[] fullHand){
		int values[] = new int[fullHand.length], count = 1;
		boolean[] hasAce = new boolean[4];
		
		for(int i = 0; i < values.length; i++){
			values[i] = fullHand[i].getSuit() * 13 + fullHand[i].getCardValue();
			if(fullHand[i].getCardValue() % 13 == 12){
				hasAce[fullHand[i].getSuit()] = true;
			}
		}
		Arrays.sort(values);
		
		for(int i = values.length - 1; i > 0; i--){
			if(values[i] == values[i - 1] + 1 && values[i] % 13 != 0){
				count++;
				if(count > 4){								//Straight found, record value.
					return new StraightFlush(new Card[]{
							new Card(values[i - 1]),
							new Card(values[i]),
							new Card(values[i + 1]),
							new Card(values[i + 2]),
							new Card(values[i + 3])
					});
				}
			}else if((values[i] % 13 == 0 && hasAce[values[i] / 13])){
				count++;
				if(count > 4){								//Straight found, record value.
					return new StraightFlush(new Card[]{
							new Card(values[i] + 12),
							new Card(values[i]),
							new Card(values[i + 1]),
							new Card(values[i + 2]),
							new Card(values[i + 3])
					});
				}
			}else{
				count = 1;
				if(i < 4){									//No straight flush possible.
					return null;
				}
			}
		}
		
		return null;
	}//checkStraightFlush()
	
	/**
	 * Checks to see if the given cards represent a flush.
	 * @param fullHand - The array of cards passed in to check for a flush.
	 * @return A <code>Flush</code> representing the value of the hand.
	 */
	private static Flush checkFlush(Card[] fullHand){
		Card suited[][] = new Card[4][5];
		int count[] = new int[4], suit;
		
		for(int i = fullHand.length - 1; i > -1; i--){
			suit = fullHand[i].getSuit();
			suited[suit][count[suit]] = fullHand[i];
			count[suit]++;
			if(count[suit] >= 5){
				return new Flush(suited[suit]);
			}
		}
		
		return null;
	}//checkFlush()
	
	/**
	 * Checks to see if the given cards represent a straight.
	 * @param fullHand - The array of cards passed in to check for a straight.
	 * @return A <code>Straight</code> representing the value of the hand.
	 */
	private static Straight checkStraight(Card[] fullHand){
		int count = 1, locations[] = new int[4];
		boolean hasAce;
		
		hasAce = fullHand[fullHand.length - 1].getCardValue() == 12;
		
		for(int i = fullHand.length - 1; i > 0; i--){
			if(fullHand[i].getCardValue() == fullHand[i - 1].getCardValue() + 1){
				locations[count - 1] = i;
				count++;
				if(count > 4){							//Straight found, record value.
					return new Straight(new Card[]{
							fullHand[locations[0]],
							fullHand[locations[1]],
							fullHand[locations[2]],
							fullHand[locations[3]],
							fullHand[locations[i - 1]]
					});
				}
			}else if((hasAce && fullHand[i].getCardValue() == 0)){
				locations[count - 1] = i;
				count++;
				if(count > 4){
					return new Straight(new Card[]{
							fullHand[fullHand.length - 1],
							fullHand[locations[0]],
							fullHand[locations[1]],
							fullHand[locations[2]],
							fullHand[0]
					});
				}
			}else if(fullHand[i].getCardValue() != fullHand[i - 1].getCardValue()){
				count = 1;
				if(i < 4){								//No straight possible.
					return null;
				}
			}
		}
		
		return null;
	}//checkStraight()
	
	/**
	 * Checks to see if the given cards represent a four-of-a-kind, a full house, three-of-a-kind, a two-pair or a pair.
	 * @param fullHand - The array of cards passed in to check for the listed hands.
	 * @return A <code>PokerHand</code> representing the best hand found.
	 */
	private static PokerHand checkSets(Card[] fullHand){
		Card sets[][] = new Card[13][4];
		int count[] = new int[13], trips[] = new int[2], pairs[] = new int[3], numPairs = 0, numTrips = 0;
		
		for(int i = fullHand.length - 1; i > -1; i--){		//Counts occurrences of cards.
			sets[fullHand[i].getCardValue()][count[fullHand[i].getCardValue()]] = fullHand[i];
			count[fullHand[i].getCardValue()]++;
		}
		
		for(int i = count.length -1; i > -1; i--){
			if(count[i] == 4){
				if(fullHand[fullHand.length -1].getCardValue() == i){	//Third card is kicker.
					return new FourOfAKind(new Card[]{
							sets[i][0],
							sets[i][1],
							sets[i][2],
							sets[i][3],
							fullHand[2]
					});
				}else{													//Last card is kicker.
					return new FourOfAKind(new Card[]{
							sets[i][0],
							sets[i][1],
							sets[i][2],
							sets[i][3],
							fullHand[6]
					});
				}
			}else if(count[i] == 3){
				trips[numTrips] = i;
				numTrips++;
			}else if(count[i] == 2){
				pairs[numPairs] = i;
				numPairs++;
			}
		}
		
		if(numTrips > 0){
			if((numPairs > 0 && numTrips > 1 && sets[trips[0]][0].getCardValue() > sets[pairs[0]][0].getCardValue())
					|| numTrips > 1 && numPairs == 0){
				return new FullHouse(new Card[]{
						sets[trips[0]][0],
						sets[trips[0]][1],
						sets[trips[0]][2],
						sets[trips[1]][0],
						sets[trips[1]][1]
				});
			}else if(numPairs > 0){
				return new FullHouse(new Card[]{
						sets[trips[0]][0],
						sets[trips[0]][1],
						sets[trips[0]][2],
						sets[pairs[0]][0],
						sets[pairs[0]][1]
				});
			}else if(trips[0] != fullHand[5].getCardValue()){	//Last two cards are kickers.
				return new ThreeOfAKind(new Card[]{
						sets[trips[0]][0],
						sets[trips[0]][1],
						sets[trips[0]][2],
						fullHand[5],
						fullHand[6]
				});
			}else{												//Triple is part of the last five cards.
				return new ThreeOfAKind(new Card[]{
						fullHand[2],
						fullHand[3],
						fullHand[4],
						fullHand[5],
						fullHand[6]
				});
			}
		}else if(numPairs > 1){
			if(fullHand[5].getCardValue() != fullHand[6].getCardValue()){
				return new TwoPair(new Card[]{			//format: XXYYZ
						sets[pairs[0]][0],
						sets[pairs[0]][1],
						sets[pairs[1]][0],
						sets[pairs[1]][1],
						fullHand[6]
				});
			}else if(fullHand[3].getCardValue() != fullHand[4].getCardValue()){
				return new TwoPair(new Card[]{			//format: XXYZZ
						sets[pairs[0]][0],
						sets[pairs[0]][1],
						fullHand[4],
						fullHand[5],
						fullHand[6],
				});
			}else{
				return new TwoPair(new Card[]{			//format: XYYZZ
						fullHand[2],
						fullHand[3],
						fullHand[4],
						fullHand[5],
						fullHand[6]
				});
			}
		}else if(numPairs == 1){
			if(fullHand[6] == fullHand[5] || fullHand[5] == fullHand[4] ||
					fullHand[4] == fullHand[3] || fullHand[3] == fullHand[2]){	//WXYZZ, WXYYZ, WXXYZ, WWXYZ
				return new Pair(new Card[]{
						fullHand[2],
						fullHand[3],
						fullHand[4],
						fullHand[5],
						fullHand[6]
				});
			}else{
				return new Pair(new Card[]{
						sets[pairs[0]][0],
						sets[pairs[0]][1],
						fullHand[4],
						fullHand[5],
						fullHand[6]
				});
			}
		}
		return null;
	}//checkSets()
	
	/**
	 * Returns a hand a <code>HighCard</code> using the five best cards of those passed in.
	 * @param fullHand - The array of cards passed in to create a <code>HighCard</code> with.
	 * @return A <code>HighCard</code> using the five best cards passed in.
	 */
	private static HighCard checkHighCard(Card[] fullHand){
		return new HighCard(Arrays.copyOfRange(fullHand, fullHand.length - 5, fullHand.length));
	}//checkHighCard()
	
	/**
	 * Creates a shuffled deck of cards.
	 * @return An array holding 52 <code>Cards</code> in a random order.
	 */
	public static Card[] getShuffledDeck(){
		ArrayList<Card> orderedDeck = new ArrayList<Card>();
		Card[] shuffledDeck = new Card[52];
		
		for(int i = 0; i < 52; i++){											//Populates the orderedDeck.
			orderedDeck.add(new Card(i));
		}
		
		for(int i = 51; i > -1; i--){			
			shuffledDeck[i] = orderedDeck.remove((int)(Math.random() * (i+1)));	//Grabs random card, adds it to the shuffled deck.
		}
		return shuffledDeck;
	}//getShuffledDeck()
	
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
	}//rndNumGen()
	
}//HandAnalyzer