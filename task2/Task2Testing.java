package tasks.task2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;
import org.junit.Assert;

public class Task2Testing {

	public static void main(String[] args) {
		
		String filename = "src\\tasks\\task2\\task2InputText.txt";
		File file = new File(filename);
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String textToParse = "";
		String data = "";
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			while ( (data = bufferedReader.readLine()) != null ) {
				textToParse += data;
			}
		} catch (IOException e) {
			System.err.println("File error"+e);
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
			} catch (IOException e) {
				System.err.println("Closing file error"+e);
			}
		}
		
		TextProcessor textProcessor = new TextProcessor(textToParse);
		
		int number = textProcessor.getMaxNumbSentencesWithSameWords();		
		System.out.println( number );
		textProcessor.displaySentencesSortedByWordsNumber();		
		
		String wordToFind = textProcessor.findUniqueWordFromFirstSentence();		
		System.out.println( wordToFind );
				
		textProcessor.printWordsInQuestionSentencesByLength(7);
				
		textProcessor.swapFirstAndLastWordsInSentence();
		System.out.println(textProcessor.toString());
		
		textProcessor.printWordsOfTextInAlphabetOrder();
		
		textProcessor.printWordsByRatioOfVowelSymbols();		
		
		textProcessor.printWordsWithLeadingVowelSortByConsonant();
		
		textProcessor.sortWordsByOccurrenceOfGivenLetter("а");
		
		String[] wordsToFind = new String[]{"сказав","матиме","Бога"};
		textProcessor.sortWordsByOccurrenceInSentence(wordsToFind);
		
		textProcessor.deleteMaxSubstring("м", "о");
		System.out.println(textProcessor.toString());
		
		textProcessor.removeWordsThatStartWithConsonantFixedLength(11);
		System.out.println(textProcessor.toString());
		
		textProcessor.sortWordsByOccurrenceOfGivenLetterDesc("о");
		
		String palindrom = textProcessor.findMaxPalindromInText();
		System.out.println(palindrom);
		
		textProcessor.removeAllLettersLikeFirstAndLastLettersInEveryWord();
		System.out.println(textProcessor.toString());
				
		textProcessor.replaceWordsByLengthWithGivenSubstring(5, 3, "aaaaa");
		System.out.println(textProcessor.toString());		
		
		symbolClassTest();
		wordStopSymbolClassTest();
		sentenceStopSymbolClassTest();
		wordClassTest();
		sentenceClassTest();
		textProcessorClassTest();
	}
	
	@Test
	public static void symbolClassTest() {
		Symbol symbol1 = new Symbol("a");
		Symbol symbol2 = new Symbol("a");
		Symbol symbol1Capital = new Symbol("A");
		Assert.assertEquals(true, symbol1.equals(symbol2));
		Assert.assertEquals(0, symbol1.compareTo(symbol2));
		Assert.assertEquals(0, symbol1.compareToCaseInsensitive(symbol1Capital));
		Assert.assertEquals("a", symbol1.getStringSymbol());
		Assert.assertEquals('A', symbol1Capital.getCharSymbol());
		Assert.assertEquals('a', symbol1Capital.getCharSymbolLowCase());
		Assert.assertEquals(true, symbol1Capital.isCapital());
		Assert.assertEquals(true, symbol1Capital.isVowel());
		Assert.assertEquals("A", symbol1Capital.toString());
	}
	
	@Test
	public static void wordStopSymbolClassTest() {
		WordStopSymbol wordsStopSymbol = new WordStopSymbol(",",2);
		Assert.assertEquals(true, WordStopSymbol.isWordStopSymbol(":"));
		Assert.assertEquals(false, WordStopSymbol.isWordStopSymbol("?"));
		Assert.assertEquals(false, WordStopSymbol.isWordStopSymbol("#"));
		Assert.assertEquals(2, wordsStopSymbol.getWordId());
	}
	
	@Test
	public static void sentenceStopSymbolClassTest() {
		SentenceStopSymbol sentenceStopSymbol = new SentenceStopSymbol(".",4);
		Assert.assertEquals(false, SentenceStopSymbol.isSentenceStopSymbol("$"));
		Assert.assertEquals(true, SentenceStopSymbol.isSentenceStopSymbol("!"));
		Assert.assertEquals(true, SentenceStopSymbol.isSentenceStopSymbol("?"));
		Assert.assertEquals(4, sentenceStopSymbol.getSentenceId());
	}
	
	@Test
	public static void wordClassTest() {
		Word word1 = new Word("qwerty");
		Word word2 = new Word("asafg");
		Assert.assertEquals(new Symbol("q"), word1.getSymbolAt(0));
		Assert.assertEquals(2, word1.indexOf("e"));
		Assert.assertEquals(-1, word1.indexOf("o"));
		Assert.assertEquals(4, word1.indexOfCaseInsensitive("T"));
		Assert.assertEquals(-1, word1.indexOfCaseInsensitive("A"));
		Assert.assertEquals(-1, word1.indexOfCaseInsensitive("A"));
		Assert.assertEquals(false, word1.equals(word2));
		Assert.assertEquals(true, word1.equals(new Word("qwerty")));
		Assert.assertEquals(1, word1.compareTo(word2));
		Assert.assertEquals(0, word1.compareToCaseInsensitive(new Word("QWERTY")));
		Assert.assertEquals(2, word2.indexOfAllSymbols("a")[1]);
		Assert.assertEquals(5, word2.getWordLength());
		Assert.assertEquals("asafg", word2.toString());
		Assert.assertEquals(2, word2.vowelsNumber());
		Assert.assertEquals(1, word2.indexOfFirstConsonant());
		Assert.assertEquals(2, word2.countLetterInWord("a"));
	}
	
	@Test
	public static void sentenceClassTest() {
		Sentence sentence1 = new Sentence("This is first sentence","!");
		Sentence sentence2 = new Sentence("That is second words sentence",".");		
		Assert.assertEquals(8, sentence1.indexOf("first"));
		Assert.assertEquals(3, sentence1.indexOf(new Word("sentence")));
		Assert.assertEquals("This", sentence1.getWordAt(0).toString());
		Assert.assertEquals(false, sentence1.equals(sentence2));
		Assert.assertEquals(true, sentence1.equals(new Sentence("This is first sentence","!")));
		Assert.assertEquals(-7, sentence1.compareTo(sentence2));
		Assert.assertEquals(true, sentence1.containsWord(new Word("first")));
		Assert.assertEquals(22, sentence1.getLength());
		Assert.assertEquals(4, sentence1.getNumberWords());
		Assert.assertEquals("!", sentence1.getStopSymbol());
		Assert.assertEquals("This is first sentence", sentence1.toString());
		Assert.assertEquals(1, sentence1.countWordInSentence("is"));
		Assert.assertEquals(0, sentence1.countWordInSentence("that"));
		sentence1.setWordAt(2, "best");
		Assert.assertEquals("This is best sentence ", sentence1.toString());
		sentence1.deleteWordAt(2);
		Assert.assertEquals("This is sentence ", sentence1.toString());
	}
	
	@Test
	public static void textProcessorClassTest() {
		TextProcessor textProcessor = new TextProcessor("This is first, sentence! That is second, words sentence? Last sentence in this text.");
		TextProcessor textProcessor2 = new TextProcessor("This is first mololom, sentence! That is second, words sentence? Last sentence in this text.");
		Assert.assertEquals(3, textProcessor.getMaxNumbSentencesWithSameWords());
		Assert.assertEquals("This is first, sentence", textProcessor.displaySentencesSortedByWordsNumber().get(0).toString());
		Assert.assertEquals("This", textProcessor.findUniqueWordFromFirstSentence());
		Assert.assertEquals("words", textProcessor.printWordsInQuestionSentencesByLength(5).iterator().next());
		textProcessor.swapFirstAndLastWordsInSentence();
		Assert.assertEquals(0, textProcessor.toString().indexOf("sentence"));
		Assert.assertEquals(1, textProcessor.printWordsOfTextInAlphabetOrder().indexOf("first"));
		Assert.assertEquals("first", textProcessor.printWordsByRatioOfVowelSymbols().get(0).toString());
		Assert.assertEquals("in", textProcessor.printWordsWithLeadingVowelSortByConsonant().get(0).toString());
		Assert.assertEquals("second", textProcessor.sortWordsByOccurrenceOfGivenLetter("e").get(0).toString());
		Assert.assertEquals("is", textProcessor.sortWordsByOccurrenceInSentence(new String[]{"sentence","is"}).firstEntry().getValue());
		Assert.assertEquals("is", textProcessor.sortWordsByOccurrenceInSentence(new String[]{"sentence","is"}).firstEntry().getValue());
		textProcessor.deleteMaxSubstring("s","t");
		Assert.assertEquals(0, textProcessor.toString().indexOf("ence"));
		textProcessor.removeWordsThatStartWithConsonantFixedLength(5);
		Assert.assertEquals(8, textProcessor.toString().indexOf("This"));
		Assert.assertEquals("ence", textProcessor.sortWordsByOccurrenceOfGivenLetterDesc("e").get(0).toString());
		Assert.assertEquals("mololom", textProcessor2.findMaxPalindromInText());
		textProcessor2.removeAllLettersLikeFirstAndLastLettersInEveryWord();
		Assert.assertEquals(23, textProcessor2.toString().indexOf("sntnce"));
		textProcessor2.replaceWordsByLengthWithGivenSubstring(0,5,"aaaaa");
		Assert.assertEquals(8, textProcessor2.toString().indexOf("aaaaa"));		
	}
	
}
