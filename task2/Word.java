package tasks.task2;

public class Word implements Comparable<Word> {

	private String word;
	private Symbol[] wordAsSymbolArray;
	
	public Word(String word) {
		this.word = word;
		wordToSymbolArray();
	}
	
	private void wordToSymbolArray() {
		char[] charArray = this.word.toCharArray();
		wordAsSymbolArray = new Symbol[charArray.length];
		for (int i=0; i<charArray.length; i++)
			wordAsSymbolArray[i] = new Symbol(charArray[i]);
	}
	
	public Symbol getSymbolAt(int index) {
		
		if (index < 0 || index >= wordAsSymbolArray.length) 
			throw new IllegalArgumentException("index parameter in getSymbolAt method is wrong!!!");
		
		return wordAsSymbolArray[index];
	}
	
	public int indexOf(String targetString) {
		for (int i=0; i<wordAsSymbolArray.length; i++) {
			if ( wordAsSymbolArray[i].compareTo(new Symbol(targetString)) == 0 )
				return i;
		}
		return -1;
	}
	
	public int indexOfCaseInsensitive(String targetString) {
		for (int i=0; i<wordAsSymbolArray.length; i++) {
			if ( wordAsSymbolArray[i].compareToCaseInsensitive(new Symbol(targetString)) == 0 )
				return i;
		}
		return -1;
	}
	
	public boolean equals(Object object2) {
		
		if (object2 == null) 
			throw new IllegalArgumentException("object2 parameter in equals method is null!!!");
		
		if (object2 instanceof Word) {
			Word word2 = (Word)object2;
			int length1 = word.length();
			int length2 = word2.getWordLength();
			if ( length1 == length2 ) {			
				for (int i=0; i<length1; i++) {					
					if ( !wordAsSymbolArray[i].equals(word2.getSymbolAt(i)) )
						return false;
				}
				return true;
			}
		}
		return false;
	}
	
	public int compareTo(Word word2) {
		
		if (word2 == null) 
			throw new IllegalArgumentException("word2 parameter in compareTo method is null!!!");
		
		int length1 = word.length();
		int length2 = word2.getWordLength();
		if ( length1 == length2 ) {			
			for (int i=0; i<length1; i++) {
				int comp = wordAsSymbolArray[i].compareTo(word2.getSymbolAt(i));
				if ( comp != 0 )
					return comp;
			}
			return 0;
		}
		return length1 - length2;
	}
	
	public int compareToCaseInsensitive(Word word2) {
		
		if (word2 == null) 
			throw new IllegalArgumentException("word2 parameter in compareToCaseInsensitive method is null!!!");
		
		int length1 = word.length();
		int length2 = word2.getWordLength();
		if ( length1 == length2 ) {			
			for (int i=0; i<length1; i++) {
				int comp = wordAsSymbolArray[i].compareToCaseInsensitive(word2.getSymbolAt(i));
				if ( comp != 0 )
					return comp;
			}
			return 0;
		}
		return length1 - length2;
	}
	
	public int[] indexOfAllSymbols(String targetString) {
		int[] indexes = null;
		int counter = 0;
		for (int i=0; i<wordAsSymbolArray.length; i++) {
			if ( wordAsSymbolArray[i].compareToCaseInsensitive(new Symbol(targetString)) == 0 ) {
				counter++;
				int[] newIndexes = new int[counter];
				if (counter > 1) {
					for (int j=0; j<counter-1; j++)
						newIndexes[j] = indexes[j];
				}
				newIndexes[counter-1] = i;
				indexes = newIndexes;
			}
		}
		return indexes;
	}
	
	public int hashCode() {
		return word.hashCode();
	}
	
	public int getWordLength() {
		return word.length();
	}
	
	public String toString() {
		return word;
	}
	
	public int vowelsNumber() {
		int number = 0;
		for (int i=0; i<wordAsSymbolArray.length; i++) {
			if ( wordAsSymbolArray[i].isVowel() )
				number++;
		}
		return number;
	}
	
	public int indexOfFirstConsonant() {
		for (int i=0; i<wordAsSymbolArray.length; i++) {
			if ( !wordAsSymbolArray[i].isVowel() )
				return i;
		}
		return -1;
	}
	
	public int countLetterInWord(String letter) {
		
		int result = 0;
		for (int i=0; i<wordAsSymbolArray.length; i++)
			if ( wordAsSymbolArray[i].compareTo(new Symbol(letter)) == 0 )
				result++;
		return result;
	}
	
}
