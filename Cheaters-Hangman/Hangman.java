import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Hangman {

	public static HashMap<Integer, List<String>> readFile(String fileName) {
		//key: number of letters
		//value: all the words that are key letters long
		HashMap<Integer, List<String>> wordMap = new HashMap<>();
		List<String> allWords = new ArrayList<>();
		List<String> wordList = new ArrayList<>();
		
		try {
			Scanner scanner = new Scanner(new File(fileName));
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				line = line.toLowerCase();
				allWords.add(line);
			}
			scanner.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		for(int i = 1; i < 45; i++) {
			wordList = separateList(i, allWords);
			wordMap.put(i, wordList);
		}
		
		return wordMap;
	}
		
	public static List<String> separateList(int size, List<String> list){
		List<String> newList = new ArrayList<>();
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).length() == size) {
				newList.add(list.get(i));
			}
		}
		return newList;
	}
	
	public static int getSize(HashMap<Integer, List<String>> wordLists, Scanner scanner) {
		System.out.println("What size word do you want to guess?");
		int size = scanner.nextInt();	
		while(wordLists.containsKey(size) == false || wordLists.get(size).size() == 0) {
			System.out.println("There are no words of that length. Try another size.");
			size = scanner.nextInt();
		}
		return size;
	}
	
	public static HashMap<String, List<String>> createWordFamilies(List<String> words, Set<Character> guessed) {
		//takes in list of all the words left, and all the letters already used
		//create a for loop to go through words and figure out which family it belongs to
		
		HashMap<String, List<String>> families = new HashMap<>();
		
		for(String word: words) {
			String wordFamily = "";
			for(int i = 0; i < word.length(); i++) {
				if(guessed.contains(word.charAt(i))) {
					wordFamily += word.charAt(i);
				} else {
					wordFamily += "_";
				}
			}
			
			if(families.containsKey(wordFamily)) {
				List<String> wordFamList = families.get(wordFamily);
				wordFamList.add(word);
				families.put(wordFamily, wordFamList);
			} else {
				List<String> wordFamList = new ArrayList<>();
				wordFamList.add(word);
				families.put(wordFamily, wordFamList);
			}
		}
		
		return families;
	}
	
	public static String getBestFamily(HashMap<String, List<String>> families) {
		Set<String> keySet = families.keySet();
		int biggestFam = 0;
		String bestFamKey = "";
		
		for(String key : keySet) {
			List<String> currentList = families.get(key);
			if(currentList.size() > biggestFam) {
				biggestFam = currentList.size();
				bestFamKey = key;
			}
		}
		
		return bestFamKey;
	}
	
	public static boolean playAgain(Scanner scanner) {
		System.out.println("\nWant to play again? (y/n)");
		boolean playing = true;
		
		String play = scanner.next();
		play = play.toLowerCase();
		if(play.equals("n") || play.equals("no")) {
			scanner.close();
			playing = false;
		}
		return playing;
	}
	
	public static void main(String[] args) {
		HashMap<Integer, List<String>> wordLists = readFile("words.txt");
		System.out.println("Let's play Hangman!");
		Scanner scanner = new Scanner(System.in);
		boolean playing = true;
		
		while(playing) {	
			int size = getSize(wordLists, scanner);
			
			System.out.println("How many guesses will you get?");
			int guesses = scanner.nextInt();
		
			//initiate list of hidden words, set of guessed letters, and word shown to user
			List<String> hiddenWords = wordLists.get(size);
			Set<Character> guessed = new HashSet<>();
		
			String word = "";
			for(int i = 0; i < size; i++) {
				word += "_";
			}
			
			//play hangman
			while(guesses != 0) {
				//print revealed letters, their wrong guesses, remaining number of wrong guesses
				System.out.println("word: '" + word + "', guessed letters: " + guessed + ", guesses left: " + guesses);
		
				//get user's new guess (if they repeat an old guess, ask them for another guess)
				System.out.println("Guess a letter: ");
				char letterGuess = scanner.next().charAt(0);
				while(guessed.contains(letterGuess)) {
					System.out.println("You already guessed that. Guess again.");
					letterGuess = scanner.next().charAt(0);
				}
				guessed.add(letterGuess);
				guesses--;
				
				//separate list of hidden words into word families based on input - createWordFamilies()
				HashMap<String, List<String>> families = createWordFamilies(hiddenWords, guessed);
				
				//choose word family using getBestFamily() - make that new hidden word list
				String bestFam = getBestFamily(families);
				word = bestFam;
				hiddenWords = families.get(bestFam);
				
				if(!word.contains("_")) {
					System.out.println("Congrats! You guessed my word: " + word);
					break;
				}
			}
			
			if(guesses == 0) {
				Random num = new Random();
				String hiddenWord = hiddenWords.get(num.nextInt(hiddenWords.size()));
				System.out.println("You ran out of guesses. You lose! My word was: " + hiddenWord);
			}
			
			playing = playAgain(scanner);
		}	
		scanner.close();
	}
}
