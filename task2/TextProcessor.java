package tasks.task2;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;

public class TextProcessor {

	private String stringText;
	private Sentence[] sentences;
	private List<SentenceStopSymbol> punctuation = new ArrayList<>();
	
	public TextProcessor(String stringText) {
		this.stringText = stringText;
		textToSentenceArray();
		findPunctuation();
	}
	
	private void textToSentenceArray() {
		String sentenceDividerPattern = SentenceStopSymbol.sentenceDividerPattern;
		String[] newSentences = this.stringText.split(sentenceDividerPattern);
		sentences = new Sentence[newSentences.length];
		for(int i=0; i<newSentences.length; i++) {
			String currSentence = newSentences[i].trim();
			String punctuationStr = findPunctuationBetweenSentences(currSentence, (i == newSentences.length-1)?currSentence:newSentences[i+1].trim(), i);
			sentences[i] = new Sentence(currSentence, punctuationStr );			
		}			
	}
	
	// task1 - Find max number of sentences with the same words
	public int getMaxNumbSentencesWithSameWords() {
		Map<Word,Integer> wordsMap = new HashMap<Word,Integer>();
		for (int i=0; i<sentences.length; i++) {
			for (int j=0; j<sentences[i].getNumberWords(); j++) {
				Word currentWord = sentences[i].getWordAt(j);				
				if ( !wordsMap.containsKey(currentWord) ) {
					int counter = 0;
					for (int k=0; k<sentences.length; k++)
						if (sentences[k].containsWord(currentWord))
							counter++;
					wordsMap.put(currentWord,new Integer(counter));
				}
					
			}
		}
		
		List<Integer> mapValues = new ArrayList<>(wordsMap.values());
		Collections.sort(mapValues);
		
		return mapValues.get(mapValues.size()-1);
	}
	
	// task2 - display all sentences sorted by number of words in them
	public List<Sentence> displaySentencesSortedByWordsNumber() {
				
		List<Sentence> listSentences = Arrays.asList(sentences);		
		Collections.sort(listSentences, new Comparator<Sentence>() {
			public int compare(Sentence sentence1, Sentence sentence2) {
				return sentence1.getNumberWords() - sentence2.getNumberWords();
			}
		});
		
		for(Sentence currSentence : listSentences)
			System.out.println(currSentence.toString());
		return listSentences;
	}
	
	// task3 - find one word from 1st sentence that is absent in others
	public String findUniqueWordFromFirstSentence() {
		String wordFound = "No unique word in 1st sentence";
		boolean isUnique = true;
		for (int i=0; i<sentences[0].getNumberWords(); i++) {
			Word currentWord = sentences[0].getWordAt(i);
			isUnique = true;
			for (int j=1; j<sentences.length; j++) {
				if ( sentences[j].containsWord(currentWord) ) {
					isUnique = false;
					break;
				}
			}
			if (isUnique) {
				wordFound = currentWord.toString();
				break;
			}
			
		}
		return wordFound;
	}
	
	// task4 - print without repeat all words in question sentences that match given size 
	public Set<String> printWordsInQuestionSentencesByLength(int wordLength) {
		
		if (wordLength < 0 ) 
			throw new IllegalArgumentException("wordLength parameter in printWordsInQuestionSentencesByLength method is wrong!!!");
		
		Set<String> wordsSet = new HashSet<>();
		for (int i=0; i<sentences.length; i++) {			
			if ( sentences[i].getStopSymbol().equals("?") ) {				
				for (int j=0; j<sentences[i].getNumberWords(); j++)
					if (sentences[i].getWordAt(j).getWordLength() == wordLength)
						wordsSet.add(sentences[i].getWordAt(j).toString());
			}
		}
		if (wordsSet.isEmpty()) {
			System.out.println("No words found");
			return null;
		}			
		else
			System.out.println(wordsSet);
		return wordsSet;
	}
	
	// task5 - in each sentence swap first and last words
	public void swapFirstAndLastWordsInSentence() {
		for (int i=0; i<sentences.length; i++) {
			String firstWord = sentences[i].getWordAt(0).toString();
			String lastWord = sentences[i].getWordAt(sentences[i].getNumberWords()-1).toString();
			String newSentence = sentences[i].toString().replaceFirst(firstWord, lastWord);
			int lastWordPosition = newSentence.lastIndexOf(lastWord);
			newSentence = newSentence.substring(0, lastWordPosition) + firstWord + newSentence.substring( lastWordPosition+lastWord.length()-1, newSentence.length()-1);
			sentences[i] = new Sentence(newSentence, sentences[i].getStopSymbol());
		}
		submitChangesInSentenceArray();
	}
	
	// task6 - print words of text in alphabet order
	public String printWordsOfTextInAlphabetOrder() {
		List<Word> words = new LinkedList<>();
		for (int i=0; i<sentences.length; i++)
			for (int j=0; j<sentences[i].getNumberWords(); j++)
				words.add(sentences[i].getWordAt(j));
		Collections.sort(words, new Comparator<Word>() {
			public int compare(Word word1, Word word2) {
				return word1.getSymbolAt(0).compareToCaseInsensitive(word2.getSymbolAt(0));
			}
		});
		String resultString = "";
		char currentSymbol = '1', prevSymbol = '2';
		for (Word currentWord : words) {
			currentSymbol = currentWord.getSymbolAt(0).getCharSymbolLowCase();
			if ( currentSymbol != prevSymbol)
				resultString += "\n"+currentWord.toString();				
			else
				resultString += ", "+currentWord.toString();				
			prevSymbol = currentSymbol;
		}
		System.out.println(resultString);
		return resultString;
	}
	
	// task7 - print all words sorted by the ratio of vowel symbols in it
	public List<Word> printWordsByRatioOfVowelSymbols() {
		List<Word> words = new LinkedList<>();
		for (int i=0; i<sentences.length; i++)
			for (int j=0; j<sentences[i].getNumberWords(); j++)
				words.add(sentences[i].getWordAt(j));
		Collections.sort(words, new Comparator<Word>() {
			public int compare(Word word1, Word word2) {
				float vowelsRatio1 = (float)word1.vowelsNumber()/word1.getWordLength();
				float vowelsRatio2 = (float)word2.vowelsNumber()/word2.getWordLength();				
				float result = vowelsRatio1 - vowelsRatio2;
				if (result > 0)
					return 1;
				if (result < 0)
					return -1;
				return 0;
			}
		});
		for (Word currentWord : words)
			System.out.println(currentWord);
		return words;
	}
		
	// task8 - print words that start with a vowel, sorted by first consonant symbol
	public List<Word> printWordsWithLeadingVowelSortByConsonant() {
		List<Word> words = new LinkedList<>();
		for (int i=0; i<sentences.length; i++)
			for (int j=0; j<sentences[i].getNumberWords(); j++)
				if ( sentences[i].getWordAt(j).getSymbolAt(0).isVowel() )
					words.add(sentences[i].getWordAt(j));
		Collections.sort(words, new Comparator<Word>() {
			public int compare(Word word1, Word word2) {
				int posFirstConsonant1 = word1.indexOfFirstConsonant();
				int posFirstConsonant2 = word2.indexOfFirstConsonant();
				if ( posFirstConsonant1 == -1)
					return 1;
				if ( posFirstConsonant2 == -1)
					return -1;
				Symbol firstConsonantSymbol1 = word1.getSymbolAt(posFirstConsonant1);
				Symbol firstConsonantSymbol2 = word2.getSymbolAt(posFirstConsonant2);
				return firstConsonantSymbol1.compareToCaseInsensitive(firstConsonantSymbol2);
			}
		});
		for (Word currentWord : words)
			System.out.println(currentWord);
		return words;
	}
	
	// task9 - sort words by occurrence of given letter
	public List<Word> sortWordsByOccurrenceOfGivenLetter(String letter) {
		List<Word> words = new LinkedList<>();
		for (int i=0; i<sentences.length; i++)
			for (int j=0; j<sentences[i].getNumberWords(); j++)
				if ( sentences[i].getWordAt(j).indexOf(letter) != -1 )
					words.add(sentences[i].getWordAt(j));
		Collections.sort(words, new Comparator<Word>() {
			
			public int compare(Word word1, Word word2) {
				int countLetter1 = word1.countLetterInWord(letter);
				int countLetter2 = word2.countLetterInWord(letter);
				if ( countLetter1 != countLetter2 )
					return countLetter1 - countLetter2;
				char firstLetter1 = word1.getSymbolAt(0).getCharSymbolLowCase();
				char firstLetter2 = word2.getSymbolAt(0).getCharSymbolLowCase();
				return firstLetter1 - firstLetter2;		
			}
		});
		for (Word currentWord : words)
			System.out.println(currentWord);
		return words;
	}
	
	// task10 - sort words by occurrence in sentences
	public TreeMap<Integer,String> sortWordsByOccurrenceInSentence(String[] wordsToSearch) {
		
		if (wordsToSearch == null) 
			throw new IllegalArgumentException("wordsToSearch parameter in sortWordsByOccurrenceInSentence method is null!!!");
		
		TreeMap<Integer,String> wordsWithNumbers = new TreeMap<>( new Comparator<Integer>() {
			public int compare(Integer number1, Integer number2) {
				return number1-number2;
			}
		});
		for (int k=0; k<wordsToSearch.length; k++) {
			String currWord = wordsToSearch[k];
			int counter = 0;
			for (int i=0; i<sentences.length; i++) {
				counter += sentences[i].countWordInSentence(currWord);
			}
			wordsWithNumbers.put(counter, currWord);
		}		
		Iterator<Map.Entry<Integer,String>> iterator = wordsWithNumbers.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<Integer,String> treeValues = iterator.next();
			System.out.println("Word " + treeValues.getValue() + " found " + treeValues.getKey() + " times in text");
		}
		return wordsWithNumbers;
		
	}
	
	
	// task11 - in each sentence delete max substring that starts and ends with the given letters
	public void deleteMaxSubstring(String letter1, String letter2) {
		for (int i=0; i<sentences.length; i++) {
			int maxSubstringWordId = -1;
			int maxSubstringWordLength = -1;
			int startIndex = -1;
			int endIndex = -1;
			for (int j=0; j<sentences[i].getNumberWords(); j++) {
				int[] currStartIndex = sentences[i].getWordAt(j).indexOfAllSymbols(letter1);
				int[] currEndIndex = sentences[i].getWordAt(j).indexOfAllSymbols(letter2);
				if ( currStartIndex != null && currEndIndex != null ) {
					for (int k=0; k<currStartIndex.length; k++) {
						for (int l=0; l<currEndIndex.length; l++) {
							int start = currStartIndex[k];
							int end = currEndIndex[l];
							int substringLength = end-start;
							if ( (end > start) && ( substringLength > maxSubstringWordLength ) ) {								
								maxSubstringWordId = j;
								maxSubstringWordLength = substringLength;
								startIndex = start;
								endIndex = end;
							}
								
						}
					}						
						
							
				}
			}
			if (maxSubstringWordId != -1) {				
				String oldWord = sentences[i].getWordAt(maxSubstringWordId).toString();
				String foundSubstring = oldWord.substring(startIndex, endIndex+1 );				
				String newWord = oldWord.replaceAll(foundSubstring, "");
				if ( newWord.equals("") ) {					
					sentences[i].deleteWordAt(maxSubstringWordId);					
				}
				else if ( !newWord.equals("") ) {
					sentences[i].setWordAt(maxSubstringWordId, newWord);					
				}				
			}
		}
		submitChangesInSentenceArray();
			
	}
	
	// task12 - remove all words with the fixed length that starts with consonant letter 
	public void removeWordsThatStartWithConsonantFixedLength(int wordLength) {
		
		if (wordLength < 0 ) 
			throw new IllegalArgumentException("wordLength parameter in removeWordsThatStartWithConsonantFixedLength method is wrong!!!");
		
		for (int i=0; i<sentences.length; i++) {			
			boolean changed = true;
			while(changed) {
				changed = false;
				for (int j=0; j<sentences[i].getNumberWords(); j++) {
					if (sentences[i].getWordAt(j).getWordLength() == wordLength && !sentences[i].getWordAt(j).getSymbolAt(0).isVowel()) {						
						changed = true;
						sentences[i].deleteWordAt(j);
						Sentence newSentence = new Sentence(sentences[i].toString(),sentences[i].getStopSymbol());
						sentences[i] = newSentence;
						break;
					}
				}
			}			
				
		}
		submitChangesInSentenceArray();
	}
	
	// task13 - sort words by occurrence of given letter descending
	public List<Word> sortWordsByOccurrenceOfGivenLetterDesc(String letter) {
		List<Word> allWords = getAllWords();
		List<Word> foundWords = new LinkedList<>();
		for (int i=0; i<allWords.size(); i++) {
			Word currentWord = allWords.get(i);
			if ( currentWord.indexOf(letter) != -1 )
				foundWords.add(currentWord);
		}		
				
		Collections.sort(foundWords, new Comparator<Word>() {
			
			public int compare(Word word1, Word word2) {
				int countLetter1 = word1.countLetterInWord(letter);
				int countLetter2 = word2.countLetterInWord(letter);
				if ( countLetter1 != countLetter2 )
					return countLetter2 - countLetter1;
				char firstLetter1 = word1.getSymbolAt(0).getCharSymbolLowCase();
				char firstLetter2 = word2.getSymbolAt(0).getCharSymbolLowCase();
				return firstLetter1 - firstLetter2;
			}
		});
		for (Word currentWord : foundWords)
			System.out.println(currentWord);
		return allWords;
	}
	
	// task14 - find max substring that is palindrom
	public String findMaxPalindromInText() {
		List<Word> allWords = getAllWords();
		int maxLength = 0;
		String palindrom = "";
		
		for (int i=0; i<allWords.size(); i++) {
			Word currentWord = allWords.get(i);
			int currentLength = currentWord.getWordLength();
			if (currentLength >= 3) {
				for (int j=0; j<currentLength-2; j++) {
					Symbol startLetter = currentWord.getSymbolAt(j);
					for (int k=j+1; k<currentLength; k++) {						
						Symbol endLetter = currentWord.getSymbolAt(k);						
						if (startLetter.compareToCaseInsensitive(endLetter) == 0) {
							String subString = currentWord.toString().substring(j, k+1).toLowerCase();														
							String reversedSubString = new StringBuffer(subString).reverse().toString().toLowerCase();							
							if ( subString.equals(reversedSubString) && subString.length() > maxLength ) {
								palindrom = subString;
								maxLength = subString.length();
							}
								
						}
					}
				}				
			}

		}
		return palindrom;
	}
	
	// task15 - in all words delete all letters like first and last letters
	public void removeAllLettersLikeFirstAndLastLettersInEveryWord() {
		for (int i=0; i<sentences.length; i++) {
			for (int j=0; j<sentences[i].getNumberWords(); j++) {
				Word currentWord = sentences[i].getWordAt(j);
				String firstLetter = currentWord.getSymbolAt(0).toString();
				String lastLetter = currentWord.getSymbolAt(currentWord.getWordLength()-1).toString();
				String newWord = currentWord.toString().substring(1, currentWord.toString().length());
				newWord = newWord.replaceAll(firstLetter, "");
				newWord = newWord.replaceAll(lastLetter, "");
				newWord = firstLetter + newWord + lastLetter;				
				sentences[i].setWordAt(j, newWord);
			}
		}
		submitChangesInSentenceArray();
	}
	
	// task16 - in one sentence in text replace words that have fixed length with the given substring
	public void replaceWordsByLengthWithGivenSubstring(int indexSentence, int length, String replacement) {
		
		if ( (indexSentence < 0) || (length < 0) || (indexSentence > sentences.length-1) )
			throw new IllegalArgumentException("In function replaceWordsByLengthWithGivenSubstring wrong input value!!!");
		
		for (int i=0; i<sentences[indexSentence].getNumberWords(); i++) {
			if (sentences[indexSentence].getWordAt(i).getWordLength() == length) {
				sentences[indexSentence].setWordAt(i, replacement);
			}
		}
		submitChangesInSentenceArray();
	}
	
	private String findPunctuationBetweenSentences(String sentence1, String sentence2, int index) {
		
		int beginIndex;
		int endIndex;
		if ( index == sentences.length - 1 ) {
			beginIndex = stringText.indexOf(sentence1)+sentence1.length();
			endIndex = stringText.length();
		} else {
			beginIndex = stringText.indexOf(sentence1)+sentence1.length();
			endIndex = stringText.indexOf(sentence2);
		}

		String punctuationStr = "";
		if (beginIndex == -1 || endIndex == -1)
			punctuationStr = ".";
		else {			
			punctuationStr = stringText.substring(beginIndex, endIndex).trim();			
			if ( !SentenceStopSymbol.isSentenceStopSymbol(punctuationStr) )
				punctuationStr = ".";
		}
		punctuation.add(new SentenceStopSymbol(punctuationStr, index));
		return punctuationStr;
	}	
	
	private void findPunctuation() {
		for (int i=0; i<sentences.length-1; i++) {
			int beginIndex = stringText.indexOf(sentences[i].toString());
			int endIndex = stringText.indexOf(sentences[i+1].toString());
			if (beginIndex == -1 || endIndex == -1)
				continue;
			String punctuationStr = stringText.substring(beginIndex, endIndex).trim();
			if ( SentenceStopSymbol.isSentenceStopSymbol(punctuationStr) )
				punctuation.add(new SentenceStopSymbol(punctuationStr,i));
		}
		int endSentenceIndex = sentences.length-1;
		String endStopSymbol = findStopSymbolForSentence(sentences[endSentenceIndex].toString(), endSentenceIndex);
		punctuation.add(new SentenceStopSymbol(endStopSymbol, endSentenceIndex));
	}
	
	private String findStopSymbolForSentence(String sentence, int index) {
		
		String regExpression = sentence + "\\s*(.{1})\\s*.+";
		Pattern pattern = Pattern.compile(regExpression);
		Matcher matcher = pattern.matcher(this.stringText);
		String stopSymbol = "";
		if ( !matcher.find() )
			stopSymbol = ".";
		else stopSymbol = matcher.group(1);
		punctuation.add(new SentenceStopSymbol(stopSymbol, index));
		return stopSymbol;
	}
	
	public List<Word> getAllWords() {
		List<Word> listWords = new LinkedList<>();
		for (int i=0; i<sentences.length; i++)
			for (int j=0; j<sentences[i].getNumberWords(); j++)
				listWords.add(sentences[i].getWordAt(j));
		return listWords;
	}
	
	private void submitChangesInSentenceArray() {
		String newString = "";
		for (int i=0; i<sentences.length; i++) {
			newString += sentences[i].toString() + getPunctuationForSentenceAt(i)+ " ";
		}
		stringText = newString;
	}
	
	private String getPunctuationForSentenceAt(int sentenceId) {
		for (int i=0; i<punctuation.size(); i++) {
			SentenceStopSymbol currentStopSymbol = punctuation.get(i);
			int currentWordId = currentStopSymbol.getSentenceId();
			if (currentWordId == sentenceId)
				return currentStopSymbol.getStringSymbol();
		}
		return "";
	}
	
	public String toString() {
		return stringText;
	}
	
}
