package tasks.task2;

public class WordStopSymbol extends Symbol {
	
	public static String wordsDividerPattern = "[\\s*,:;\\-\'\"\\[\\]\\(\\)\\{\\}(\\.\\.\\.)]+";
	private int wordId;
	
	private enum wordsStopSymbol {
		COMMA(","), COLON(":"), SEMICOLON(";"),DASH("-"),QUOTATION_MARK_1("'"),QUOTATION_MARK_2("\""),BRACKETS_RECTANGLE_OPEN("["),
		BRACKETS_RECTANGLE_CLOSE("]"),BRACKETS_ROUND_OPEN("("),BRACKETS_ROUND_CLOSE(")"),BRACKETS_FIGURE_OPEN("{"),BRACKETS_FIGURE_CLOSE("}"),
		ELLIPSIS("...");
		private String symbol;
		wordsStopSymbol(String symbol) {
			this.symbol = symbol;
		}
		public String getSymbol() {
			return this.symbol;
		}
	};
	
	public WordStopSymbol(String stringSymbol, int wordId) {
		super(stringSymbol);
		this.wordId = wordId;
	}
	
	public static boolean isWordStopSymbol(String symbol) {
		
		for (wordsStopSymbol currStopSymbol : wordsStopSymbol.values()) {
			if ( currStopSymbol.getSymbol().equals(symbol) )
				return true;
		}
		return false;
	}
	
	public int getWordId() {
		return this.wordId;
	}

}
