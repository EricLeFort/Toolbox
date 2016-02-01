package encryptionTools;
/**
 * @author Eric Le Fort
 * @version 01
 */
import java.util.ArrayList;
import java.util.HashMap;

public abstract class VignereCipher{

	public static void main(String... args){
		System.out.println(decrypt("aaaba z", 1));
		System.out.println(decrypt("VGJNFNOEVTUGPBYQQNLVANCEVYNAQGURPYBPXFJRERFGEVXVATGUVEGRRA", 1));
		System.out.println(decrypt(encrypt("Erc", "Chris is actually the worst"), 5));
	}

	/**
	 * Encrypts the message with the key passed in. The key can only contain the characters a-z, A-Z and spaces.
	 * Any other inputs will pass through unencrypted.
	 * Special note: A key of length 1 operates the same as a Caesar cipher.
	 * @param key - The key to encrypt the message with.
	 * @param message - The message to be encrypted.
	 */
	public static String encrypt(String key, String message){
		char[] encryptedMessage = new char[message.length()];
		int shift;

		if(key.length() == 0 || message.length() == 0){
			return message;
		}

		message = message.toLowerCase();									//Note: a - 97, z - 122 in ASCII
		key = key.toLowerCase();

		if(!validKey(key)){
			return message;
		}

		for(int i = 0; i < message.length(); i++){
			if(key.charAt(i % key.length()) == ' '){						//a shifts 0, b shifts 1,... space shifts 26
				shift = 26;
			}else{
				shift = key.charAt(i % key.length()) - 'a';
			}

			encryptedMessage[i] = shiftLetter(message.charAt(i), shift);
		}

		return new String(encryptedMessage);
	}//encrypt()

	/**
	 * Decrypts the encrypted message using the inverse of the key passed in. If the key passed in is the same as the one used to
	 * encrypt the message, the original message will be returned.
	 * @param key - The key to be used to decrypt the message.
	 * @param encodedMessage - A message that has been encyrpted using the Vigenere cipher.
	 * @return The decrypted message.
	 */
	public static String decrypt(String key, String encodedMessage){
		char[] decryptedMessage = new char[encodedMessage.length()];

		if(key.length() == 0 || encodedMessage.length() == 0){
			return encodedMessage;
		}

		encodedMessage = encodedMessage.toLowerCase();						//Note: a - 97, z - 122 in ASCII
		key = key.toLowerCase();

		if(!validKey(key)){
			return encodedMessage;
		}

		for(int i = 0; i < encodedMessage.length(); i++){
			decryptedMessage[i] = shiftLetter(encodedMessage.charAt(i),
					getShift(key.charAt(i % key.length()), false));
		}

		return new String(decryptedMessage);
	}//decrypt()

	public static String decrypt(String encodedMessage, int keyLength){
		HashMap<Character, Double> englishFrequencies = createEnglishFrequencyTable();
		ArrayList<String> potentialKeys = new ArrayList<String>();
		ArrayList<Character> frequentChars = new ArrayList<Character>();
		String key = "";
		int counts[] = new int[27], highest = 0;
		char mostFrequent[];

		encodedMessage = encodedMessage.toLowerCase();
		
		for(int i = 0; i < keyLength; i++){
			for(int j = i; j < encodedMessage.length(); j += keyLength){	//Build array of letter counts belonging to current key letter.
				if(encodedMessage.charAt(j) == ' '){
					counts[26]++;
				}else{
					counts[encodedMessage.charAt(j) - 97]++;
				}
			}

			for(int j = 0; j < counts.length; j++){							//Finds most frequent values.
				if(counts[j] > highest){									//Remove old values, add new value.
					highest = counts[j];
					frequentChars.clear();
					if(j == 26){
						frequentChars.add(' ');
					}else{
						frequentChars.add((char)(j + 97));
					}
				}else if(counts[j] == highest){								//Add new value.
					if(j == 26){
						frequentChars.add(' ');
					}else{
						frequentChars.add((char)(j + 97));
					}
				}
			}
			
			//FIND KEY using permutations of all most frequent letter distances from 'e'
			//TODO Grab all most occurring, may or may not work.
		}
		System.out.println("Key length: " + keyLength);

		return new String(decrypt(key, encodedMessage));
	}//decrypt()

	/**
	 * Determines the shift to be applied based on the letter passed in. The shift will be changed to negative if the value passed
	 * in for <code>encoding</code> is <code>false</code>.
	 * @param letter - The letter from the key, used to determine how much to shift.
	 * @param encrypting - Whether we are encrypting or decrypting.
	 * @return The amount to shift by.
	 */
	public static int getShift(char letter, boolean encrypting){
		int shift = letter - 'a';

		if(encrypting){
			return shift;
		}
		return -1 * shift;
	}//getShift()

	/**
	 * Shifts a given letter in the alphabet (or a space) according to the shift specified according to the examples below.
	 * shiftLetter('a', 2) would return 'c' 
	 * shiftLetter('z', 1) would return ' ' 
	 * shiftLetter('z', 2) would return 'a' 
	 * shiftLetter('c', -2) would return 'a'
	 * If the letter doesn't belong to the alphabet or if the shift is less than -27 or greater than 27, the letter passed in is returned.
	 * 
	 * @param letter - The character to be shifted.
	 * @param shift - The amount of positions to shift the character.
	 * @return The shifted character.
	 */
	public static char shiftLetter(char letter, int shift){
		if(letter != ' ' && (letter < 'a' || letter > 'z')		//Letter not in alphabet.
				|| shift < -27 || shift > 27					//Shift out of bounds.
				|| shift == 0){
			return letter;
		}

		if(shift >= 0){											//Encoding, positive shift
			if(letter == ' '){
				return (char)('a' + shift - 1);
			}else if(letter + shift <= 'z'){
				return (char)(letter + shift);
			}else if(letter + shift == 'z' + 1){
				return ' ';
			}else{
				return(char)(letter + shift - 27);
			}
		}else{													//Decoding, negative shift
			if(letter == ' '){
				return (char)('z' + shift + 1);
			}else if(letter + shift >= 'a'){
				return (char)(letter + shift);
			}else if(letter + shift == 'a' - 1){
				return ' ';
			}else{
				return (char)(letter + shift + 27);
			}
		}

	}//shiftLetter()

	/**
	 * Checks whether the key provided contains only lowercase letters and spaces.
	 * @param key
	 * @return Whether the key is valid.
	 */
	public static boolean validKey(String key){
		for(int i = 0; i < key.length(); i++){								//Ensures key holds only letters and spaces.
			if((key.charAt(i) < 'a' || key.charAt(i) > 'z')
					&& key.charAt(i) != ' '){
				return false;
			}
		}
		return true;
	}//validKey()

	/**
	 * Prepares a <code>HashMap</code> that maps letters to their approximate average frequencies in the English language.
	 * @return A <code>HashMap</code> mapping letters to their frequencies.
	 */	
	public static HashMap<Character, Double> createEnglishFrequencyTable(){
		HashMap<Character, Double> frequencies = new HashMap<Character, Double>();

		putMultiple(frequencies, 8.12, 'a', 'A');
		putMultiple(frequencies, 1.49, 'b', 'B');
		putMultiple(frequencies, 2.71, 'c', 'B');
		putMultiple(frequencies, 4.32, 'd', 'D');
		putMultiple(frequencies, 12.02, 'e', 'E');
		putMultiple(frequencies, 2.3, 'f', 'F');
		putMultiple(frequencies, 2.03, 'g', 'G');
		putMultiple(frequencies, 5.92, 'h', 'H');
		putMultiple(frequencies, 7.31, 'i', 'I');
		putMultiple(frequencies, 0.1, 'j', 'J');
		putMultiple(frequencies, 0.69, 'k', 'K');
		putMultiple(frequencies, 3.98, 'l', 'L');
		putMultiple(frequencies, 2.61, 'm', 'M');
		putMultiple(frequencies, 6.95, 'n', 'N');
		putMultiple(frequencies, 7.68, 'o', 'O');
		putMultiple(frequencies, 1.82, 'p', 'P');
		putMultiple(frequencies, 0.11, 'q', 'Q');
		putMultiple(frequencies, 6.02, 'r', 'R');
		putMultiple(frequencies, 6.28, 's', 'S');
		putMultiple(frequencies, 9.1, 't', 'T');
		putMultiple(frequencies, 2.88, 'u', 'U');
		putMultiple(frequencies, 1.11, 'v', 'V');
		putMultiple(frequencies, 2.09, 'w', 'W');
		putMultiple(frequencies, 0.17, 'x', 'X');
		putMultiple(frequencies, 2.11, 'y', 'Y');
		putMultiple(frequencies, 0.07, 'z', 'Z');

		return frequencies;
	}//createEnglishFrequencyTable()

	/**
	 * Inserts multiple characters to map to the same value in a HashMap 
	 * @param map - The map to put the new mappings into.
	 * @param value - The value to map all the characters to.
	 * @param chars - The characters to map from.
	 */
	public static void putMultiple(HashMap<Character, Double> map, double value, char... chars){
		if(map == null){ return; }

		for(char c: chars){
			map.put(c, value);
		}
	}//putMultiple()
	
}//VigenereCipher
