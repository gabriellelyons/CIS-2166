import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class MyArrayList<E> {

	public static <E> boolean unique(List<E> list) {				//O(n^2)
		for(int i = 0; i < list.size(); i++) {
			E item = list.get(i);
			for(int j = 0; j < list.size(); j++) {
				if(i != j) {
					E otherItem = list.get(j);
					if(item.equals(otherItem))
						return false;
				}
			}
		}
		return true;
	}
	
	public static List<Integer> allMultiples(List<Integer> list, int num) {			//O(n)
		List<Integer> newList = new ArrayList<>();
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i) % num == 0)
				newList.add(list.get(i));
		}
		return newList;
	}
	
	public static List<String> allStringsOfSize(List<String> list, int length) {		//O(n)
		List<String> newList = new ArrayList<>();
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).length() == length)
				newList.add(list.get(i));
		}
		return newList;
	}
	
	public static <E> boolean isPermutation(List<E> A, List<E> B) {			//O(n^2)
		if(A.size() != B.size()) {
			return false;
		}
		
		for(E item: A) {
			int countA = 0;
			int countB = 0;
			
			//count the number of times item appears in A
			for(int i = 0; i < A.size(); i++) {
				E otherItem = A.get(i);
				if(item.equals(otherItem)) {
					countA++;
				}
			}
			//count the number of times item appears in B
			for(int i = 0; i < B.size(); i++) {
				E otherItem = B.get(i);
				if(item.equals(otherItem)) {
					countB++;
				}
			}
			
			if(countA != countB) {
				return false;
			}
		}
		
		return true;
	}

	public static List<String> stringToListOfWords(String s) {				//O(n)
		List<String> newList = new ArrayList<>();
		
		//removes the white spaces and splits the string into individual word array
		String[] seperate = s.split("\\s+", -1);
		for(int i = 0; i < seperate.length; i++) {
			newList.add(seperate[i]);
		}
		
		//removes anything that is not a letter or number
		for(int i = 0; i < newList.size(); i++) {
			String element = newList.get(i);
			element = element.replaceAll("[^a-zA-Z0-9]", "");
			newList.set(i, element);
		}
		return newList;
	}
	
	public static <E> void removeAllInstances(List<E> list, E item) {		//O(n)
		for(int i = 0; i < list.size(); i++) {
			E element = list.get(i);
			if(element.equals(item)) {
				list.remove(i);
				i--;
			}
		}
	}
	
	public static void main(String args[]) {
		List<String> list1 = new ArrayList<>();
		list1.add("Milo");
		list1.add("Rachel");
		list1.add("Jeff");
		list1.add("Milo");
		
		//testing unique()
		if(unique(list1))
			System.out.println("unique: true");
		else
			System.out.println("unique: false");
		
		//testing allStringsOfSize()
		List<String> newStringList = new ArrayList<>();
		newStringList = allStringsOfSize(list1, 4);
		System.out.println("allStringsOfSize: " + newStringList);
		
		
		List<Integer> list2 = new ArrayList<>();
		list2.add(5);
		list2.add(29);
		list2.add(12);
		list2.add(162);
		
		List<Integer> list3 = new ArrayList<>();
		list3.add(162);
		list3.add(29);
		list3.add(5);
		list3.add(12);
		
		//testing allMultiples()
		List<Integer> newIntegerList = new ArrayList<>();
		newIntegerList = allMultiples(list2, 2);
		System.out.println("allMultiples: " + newIntegerList);
		
		//testing isPermutation()
		if(isPermutation(list2, list3))
			System.out.println("isPermutation: true");
		else
			System.out.println("isPermutation: false");
		
		
		//testing stringToListOfWords()
		String s = "Hi, my name is Gabrielle! What's your name?";
		List<String> newSeperatedList = new ArrayList<>();
		newSeperatedList = stringToListOfWords(s);
		System.out.println("stringToListOfWords: " + newSeperatedList);
				
		
		List<Integer> list4 = new ArrayList<>();
		list4.add(5);
		list4.add(162);
		list4.add(29);
		list4.add(5);
		list4.add(12);
		list4.add(5);
		list4.add(5);
		
		//testing removeAllInstances()
		removeAllInstances(list4, 5);
		System.out.println("removeAllInstances: " + list4);
		
	}
}
