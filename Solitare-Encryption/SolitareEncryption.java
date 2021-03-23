import circularlinkedlist.CircularLinkedList;
import java.util.Scanner;

public class SolitaireEncryption {

    public static char encryptChar(char letter, int key) {
        int value =   letter - 'a';
        int encryptedValue =  (value + key) % 26;
        char encryptedChar = (char) (encryptedValue+'a');

        return encryptedChar;
    }


    public static char decryptChar(char letter, int key) {
        int value =   letter - 'a';
        int decryptedValue =  (value + (26-key)) % 26;
        char decryptedChar = (char) (decryptedValue+'a');

        return decryptedChar;
    }

    public static String toSimpleString(String s) {
    	s = s.toLowerCase();
		s = s.replaceAll("[^a-z0-9]", "");
		s = s.replaceAll("\\s", "");
		
    	return s;
    }
    
    public static int getKey(CircularLinkedList<Integer> deck){ 
    	// calls the steps methods
    	
    	step1(deck); 
    	step2(deck);
    	step3(deck);
    	step4(deck);
    	
    	if(step5(deck) == 27 || step5(deck) == 28) {
    		getKey(deck);
    	}
    	
    	int keyVal = step5(deck);
    	return keyVal;
    }

    private static void step1(CircularLinkedList<Integer> deck){
    	//find joker A (27), swap with card beneath it (shift down one)
    	//if bottom card, switch with top card
    	
    	for(int i = 0; i < deck.size(); i++) {
    		if (deck.get(i) == 27) {    	
    			if(i == deck.size()-1) {				
    				int oldCard = deck.set(0, 27);
        			deck.set(i, oldCard);
    			} else {
    				int oldCard = deck.set(i+1, 27);
    				deck.set(i, oldCard);
    			}
    			break;
    		}
    	}
    }

    private static void step2(CircularLinkedList<Integer> deck){
    	//find joker B (28), shift down two cards
    	
    	for(int i = 0; i < deck.size(); i++) {
    		if (deck.get(i) == 28) {  
    			if(i == deck.size()-1) {
    				int lowerCard = deck.set(1, 28);
    				int upperCard = deck.set(0, lowerCard);
    				deck.set(i, upperCard);
    			} else if(i == deck.size()-2) {
    				int lowerCard = deck.set(0, 28);
    				int upperCard = deck.set(deck.size()-1, lowerCard);
    				deck.set(i, upperCard);
    			} else {
    				int lowerCard = deck.set(i+2, 28);
    				int upperCard = deck.set(i+1, lowerCard);
    				deck.set(i, upperCard);
    			}
    			break;
    		}
    	}
    }
    private static void step3(CircularLinkedList<Integer> deck){
    	//triple cut, take all cards above joker A, swap with cards below joker B
    	
    	int indexJokerA = -1, indexJokerB = -1;
    	
    	for(int i = 0; i < deck.size(); i++) {
    		if(deck.get(i) == 27) {
    			indexJokerA = i;
    		}
    		if(deck.get(i) == 28) {
    			indexJokerB = i;
    		} 
    	}
 
    	//makes sure jokerA is the one earlier in the deck, so the triple cut works
    	if(indexJokerA > indexJokerB) {
    		int temp = indexJokerA;
    		indexJokerA = indexJokerB; 
    		indexJokerB = temp;
    	}
    	
    	//gather cards from above joker A
    	CircularLinkedList<Integer> aboveDeck = new CircularLinkedList<>();
    	for(int i = 0; i < indexJokerA; i++) {
    		aboveDeck.add(i, deck.get(i));
    	}
    		
    	//gather cards from below joker B
    	CircularLinkedList<Integer> belowDeck = new CircularLinkedList<>();
    	int j = 0;
    	for(int i = indexJokerB+1; i < deck.size(); i++) {
    		belowDeck.add(j, deck.get(i));
    		j++;
    	}
        
    	//gather cards between (and including) jokers 
    	CircularLinkedList<Integer> middleDeck = new CircularLinkedList<>();
    	j = 0;
    	for(int i = indexJokerA; i <= indexJokerB; i++) {
    		middleDeck.add(j, deck.get(i));
    		j++;
    	}
    	
    	//swap the above and below mini decks
    	
    	int currentSize = belowDeck.size();
    	for(int i = 0; i < currentSize; i++) {					//adding belowDeck to top of deck
    		deck.set(i, belowDeck.get(i));				
    	}
    	
    	int newSize = currentSize + middleDeck.size();
    	j = 0;
    	for(int i = currentSize; i < newSize; i++) {			//adding middleDeck back to middle
    		deck.set(i, middleDeck.get(j));
    		j++;
    	}
    	
    	int oldSize = newSize;
    	newSize = oldSize + aboveDeck.size();
    	j = 0;
    	for(int i = oldSize; i < newSize; i++) {				//adding aboveDeck to bottom of deck
    		deck.set(i, aboveDeck.get(j));
    		j++;
    	}
    }
    
    private static void step4(CircularLinkedList<Integer> deck){
    	//remove bottom card, count from top of deck that number of cards
    	//put those cards on bottom of deck, then put bottom card back
    	
    	int bottomCard = deck.get(deck.size()-1);
    	CircularLinkedList<Integer> topDeckCards = new CircularLinkedList<>();
    	
		//if bottom card is a joker, it will always have value 27, else value is bottom card's value
		int cardVal;
		if(bottomCard == 28) {
			cardVal = 27;
		} else {
			cardVal = bottomCard;
		}
		
		//gather top cardVal cards from top of deck
    	for(int i = 0; i < cardVal; i++) {
    		topDeckCards.add(i, deck.get(i));
    	}
    	
		//shift cards after after top cards up to the front of the deck
    	int sizeRemainingCards = deck.size()-cardVal-1;
    	int j = cardVal;
    	for(int i = 0; i < sizeRemainingCards; i++) {
    		deck.set(i, deck.get(j));
    		j++;
    	}
    	
    	//put top cards after the ones just shifted but above bottom card
    	j = 0;
    	for(int i = sizeRemainingCards; i < deck.size()-1; i++) {    		
    		deck.set(i, topDeckCards.get(j));
    		j++;
    	}
    }
    
    private static int step5(CircularLinkedList<Integer> deck){
    	//look at top card's value, count down from deck that many cards (top card = index 0)
    	//record value of NEXT card in deck (one after value reached)
    	//if next card is a joker, do not return; repeat from step1 until no joker at end --> in getKey
    	
    	int value = deck.get(0);
    	
    	int cardVal;
		if(value == 28) {
			cardVal = 27;
		} else {
			cardVal = value;
		}
    	
		int target = deck.get(cardVal);
    	
        return target;
    }

    
    //deck order: 1 4 7 10 13 16 19 22 25 28 3 6 9 12 15 18 21 24 27 2 5 8 11 14 17 20 23 26
    //srjfxzxppaplmrgjufv
    //lqylfbomozwswe
    //gqvhqwodfyckuuwyb

    
    public static void main(String[] args) {
        CircularLinkedList<Integer> deck = new CircularLinkedList<>();
        Scanner scan = new Scanner(System.in);
        Scanner scanDeck = new Scanner(System.in);
        Scanner scanText = new Scanner(System.in);
        
        //reads in card order
        System.out.println("Enter order of deck (w/ spaces between cards) then type 'next': ");
        boolean hasNextInt = scanDeck.hasNextInt();
        int newItem;
        while(hasNextInt) {
        	newItem = scanDeck.nextInt();
        	deck.add(newItem);
        	hasNextInt = scanDeck.hasNextInt();
        }   
        
        System.out.println("Enter E to encrypt or D to decrypt: ");
        String answer = scan.next();
        answer.toLowerCase();
        
        //encrypts input text
        if(answer.equals("e")) {
        	while(true) {
        		System.out.println("Enter plain text to encrypt or type 'x' to exit: ");
        		String plainText = scanText.nextLine();
        		plainText.toLowerCase();
        		
        		if(plainText.equals("x")) {
        			break;
        		}

        		plainText = toSimpleString(plainText);
        		String cipherText;
        		cipherText = "";
        		for(int i = 0; i < plainText.length(); i++) {
        			cipherText += encryptChar(plainText.charAt(i), getKey(deck));
        		}
        		System.out.println("Cipher Text: " + cipherText);
        	}
        }
        
        //decrypts input text
        if(answer.equals("d")) {
            while(true) {
            	System.out.println("Enter cipher text to decrypt or type 'x' to exit: ");
            	String cipherText = scanText.nextLine();
            	cipherText.toLowerCase();
            	
            	if(cipherText.equals("x")) {
            		break;
            	}
            	
            	String decryptedText = "";
            	for(int i = 0; i < cipherText.length(); i++) {
            		decryptedText += decryptChar(cipherText.charAt(i), getKey(deck));
            	}
            	System.out.println("Decrypted Text: " + decryptedText);
            }
        }
          
        scan.close();
        scanDeck.close();
        scanText.close();   
    }
}
