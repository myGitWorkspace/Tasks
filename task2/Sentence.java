package tasks.task2;

import java.util.List;
import java.util.ArrayList;

public class Sentence implements Comparable<Sentence> {

	private String sentence;
	private Word[] words;
	private String stopSymbol;
	private List<WordStopSymbol> punctuation = new ArrayList<>();
	
	public Sentence(String sentence, String stopSymbol) {
		this.sentence = sentence;
		this.stopSymbol = stopSymbol;
		sentenceToWordsArray();
		findPunctuation();
	}
	
	private void sentenceToWordsArray() {
		
		String wordsDividerPattern = WordStopSymbol.wordsDividerPattern;		
		String[] stringWords = sentence.split(wordsDividerPattern);
		
		words = new Word[stringWords.length];		
		for (int i=0; i<stringWords.length; i++) {
			String currWord = stringWords[i].trim();			
			if ( currWord != "" )
				words[i] = new Word(currWord);			
		}
			
	}
	
	public int indexOf(String targetString) {
		return sentence.indexOf(targetString);
	}
	
	public int indexOf(Word targetWord) {
		
		if (targetWord == null) 
			throw new IllegalArgumentException("targetWord parameter in indexOf method is null!!!");
		
		for (int i=0; i<words.length; i++)
			if ( words[i].equals(targetWord) )
				return i;
		return -1;
	}
	
	public Word getWordAt(int index) {
		
		if (index < 0 || index >= words.length) 
			throw new IllegalArgumentException("index parameter in getWordAt method is wrong!!!");
		
		return words[index];
	}
	
	public boolean equals(Object object2) {
		
		if (object2 == null) 
			throw new IllegalArgumentException("object2 parameter in equals method is null!!!");
		
		if (object2 instanceof Sentence) {
			Sentence sentence2 = (Sentence)object2;
			if (stopSymbol.equals(sentence2.getStopSymbol())) {
				for (int i=0; i<words.length; i++)
					if ( !words[i].equals(sentence2.getWordAt(i)) )
						return false;
				return true;
			}
		}
		return false;
	}
	
	public int compareTo(Sentence sentence2) {
		
		if (sentence2 == null) 
			throw new IllegalArgumentException("sentence2 parameter in compareTo method is null!!!");
		
		if ( sentence.length() == sentence2.getLength() ) {
			if ( stopSymbol.equals(sentence2.getStopSymbol()) && this.equals(sentence2) )
				return 0;
			return -1;
		}
		return sentence.length() - sentence2.getLength(); 		
	}
	
	public int hashCode() {
		return sentence.hashCode();
	}
	
	public boolean containsWord(Word searchWord) {
		
		if (searchWord == null) 
			throw new IllegalArgumentException("searchWord parameter in containsWord method is null!!!");
		
		for (int i=0; i<words.length; i++) {
			if ( words[i].equals(searchWord) )
				return true;
		}
		return false;
	}
	
	public int getLength() {
		return sentence.length();
	}	
	
	public int getNumberWords() {
		return words.length;
	}
	
	public String getStopSymbol() {
		return stopSymbol;
	}
	
	public String toString() {
		return sentence;
	}
	
	private void findPunctuation() {
		int startFrom = 0;
		for (int i=0; i<words.length-1; i++) {
			int word1Length = words[i].getWordLength();
			int beginIndex = sentence.indexOf(words[i].toString(),startFrom)+word1Length;
			int endIndex = sentence.indexOf(words[i+1].toString(),startFrom+word1Length);
			startFrom = endIndex;
			if (beginIndex == -1 || endIndex == -1)
				continue;
		
			String punctuationStr = sentence.substring(beginIndex, endIndex).trim();			
			if ( WordStopSymbol.isWordStopSymbol(punctuationStr) )
				punctuation.add(new WordStopSymbol(punctuationStr,i));
		}
	}
	
	public int countWordInSentence(String targetWord) {
		
		if (targetWord == null) 
			throw new IllegalArgumentException("targetWord parameter in countWordInSentence method is null!!!");
		
		int count = 0;
		for (int i=0; i<words.length-1; i++)
			if ( words[i].compareToCaseInsensitive(new Word(targetWord)) == 0)
				count++;
		return count;
	}
	
	public void setWordAt(int index, String newWord) {
		
		if (index < 0 || index >= words.length) 
			throw new IllegalArgumentException("index parameter in setWordAt method is wrong!!!");
		
		if (newWord == "") 
			throw new IllegalArgumentException("newWord parameter in setWordAt method is wrong!!!");
		
		words[index] = new Word(newWord);
		submitChangesInWordsArray();
	}
	
	private void submitChangesInWordsArray() {
		String newString = "";
		for (int i=0; i<words.length; i++) {
			newString += words[i].toString() + getPunctuationForWordAt(i) + " ";
		}
		sentence = newString;
	}
	
	private String getPunctuationForWordAt(int wordId) {
		for (int i=0; i<punctuation.size(); i++) {
			WordStopSymbol currentStopSymbol = punctuation.get(i);
			int currentWordId = currentStopSymbol.getWordId();
			if (currentWordId == wordId)
				return currentStopSymbol.getStringSymbol();
		}
		return "";
	}
	
	public void deleteWordAt(int index) {
		
		if (index < 0 || index >= words.length) 
			throw new IllegalArgumentException("index parameter in deleteWordAt method is wrong!!!");
		
		if (index < words.length-1) {
			for (int i=index; i<words.length-1; i++) {
				words[i]=words[i+1];
			}			
		}
		Word[] newWords = new Word[words.length-1];
		for (int i=0; i<words.length-1; i++) {
			newWords[i] = words[i];
		}
		
		words = newWords;
		submitChangesInWordsArray();
	}
	
}
