package index;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

// Your class. Notice how it has no generics.
// This is because we use generics when we have no idea what kind of data we are getting
// Here we know we are getting two pieces of data:  a string and a line number
public class IndexTree {

	// This is your root 
	// again, your root does not use generics because you know your nodes
	// hold strings, an int, and a list of integers
	private IndexNode root;
	
	// Make your constructor
	// It doesn't need to do anything
	public IndexTree() {		
		this.root = root;
	}
	
	// complete the methods below
	
	// this is your wrapper method
	// it takes in two pieces of data rather than one
	// call your recursive add method
	public void add(String word, int lineNumber){
		this.root = add(this.root, word, lineNumber);
	}

	
	// your recursive method for add
	// Think about how this is slightly different the the regular add method
	// When you add the word to the index, if it already exists, 
	// you want to  add it to the IndexNode that already exists
	// otherwise make a new indexNode
	private IndexNode add(IndexNode root, String word, int lineNumber){
		if(root == null) {
			return new IndexNode(word, lineNumber);
		}
		
		int comparing = word.compareTo(root.word);
		if(comparing == 0) {
			root.list.add(lineNumber);
			root.occurrences++;
			return root;
		} else if(comparing < 0) {
			IndexNode adding = new IndexNode(word, lineNumber);
			root.left = add(root.left, word, lineNumber);
			adding = root.left;
			return root;
		} else {
			IndexNode adding = new IndexNode(word, lineNumber);
			root.right = add(root.right, word, lineNumber);
			adding = root.right;
			return root;
		}
	}

	
	// returns true if the word is in the index
	public boolean contains(String word){
		if(word == null) {
			return false;
		}
		
		int comparing = word.compareTo(root.word);
		if(comparing == 0) {
			return true;
		} else if(comparing < 0 && root.left != null) {
			root = root.left;
			return contains(word);
		} else if(comparing > 0 && root.right != null ){
			root = root.right;
			return contains(word);
		} else {
			return false;
		}
	}
	
	// call your recursive method
	// use book as guide
	public void delete(String word){
		this.root = delete(this.root, word);
	}
	
	// your recursive case
	// remove the word and all the entries for the word
	// This should be no different than the regular technique.
	private IndexNode delete(IndexNode root, String word){
		if(root == null) {
			return null;
		}
		
		int comparing = word.compareTo(root.word);
		if(comparing < 0) {
			root.left = delete(root.left, word);
			return root;
		} else if(comparing > 0){
			root.right = delete(root.right, word);
			return root;
		} else {
			if(root.left == null && root.right == null) {
				return null;
			} else if(root.left != null && root.right == null) {
				return root.left;
			} else if(root.left == null && root.right != null) {
				return root.right;
			} else {
				IndexNode current = root.left;
				while(current.right != null) {
					current = current.right;
				}
				root.word = current.word;
				root.left = delete(root.left, root.word);
				return root;
			}
		}
	}
	
	
	// prints all the words in the index in inorder order
	// To successfully print it out
	// this should print out each word followed by the number of occurrences and the list of all occurrences
	// each word and its data gets its own line
	public void printIndex(){
		StringBuilder sb = new StringBuilder();
		inOrderTraverse(root, sb);
		System.out.println(sb);
	}
	
	private void inOrderTraverse(IndexNode root, StringBuilder sb) {
		if(root == null) {
			sb.append(" ");
		} else {
			inOrderTraverse(root.left, sb);
			sb.append(root.toString());
			sb.append("\n");
			inOrderTraverse(root.right, sb);
		}
	}
	
	public static void main(String[] args){
		IndexTree index = new IndexTree();
		String fileName = "pg100.txt";
		
		// add all the words to the tree
		String[] words = {};
		try {
			Scanner scanner = new Scanner(new File(fileName));
			int lineNumber = 1;
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				words = line.split("\\s+");
				for(String word : words) {
					word = word.replaceAll("[^a-zA-Z0-9'-]", "");
					word = word.toLowerCase();
					index.add(word, lineNumber);
				}
				lineNumber++;
			}
			scanner.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		// print out the index
		index.printIndex();
		
		// test removing a word from the index
		index.delete("zounds");
		System.out.println(index.contains("zounds"));
		
	}
}
