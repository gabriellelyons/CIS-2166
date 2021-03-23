package index;

import java.util.ArrayList;
import java.util.List;

public class IndexNode  {

	String word;			// The word for this entry
	int occurrences;		// The number of occurrences for this word
	List<Integer> list; 	// A list of line numbers for this word.
	IndexNode left;
	IndexNode right;
	
	
	// Constructors
	// Constructor should take in a word and a line number
	// it should initialize the list and set occurrences to 1
	public IndexNode(String word, int lineNumber) {
		this.word = word;
		this.list = new ArrayList<Integer>();
		this.list.add(lineNumber);
		this.occurrences = 1;
	}

	// Complete This
	// return the word, the number of occurrences, and the lines it appears on.
	// string must be one line
	public String toString(){
		String result = "";
		result += (this.word + " - occurrences: " + this.occurrences + ", found at:" + this.list);
		return result;
	}
}
