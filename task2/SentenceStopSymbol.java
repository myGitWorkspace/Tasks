package tasks.task2;

public class SentenceStopSymbol extends Symbol {

	public static String sentenceDividerPattern = "[\\.\\!\\?]";
	private int sentenceId;
	
	private enum sentenceStopSymbol {
		FULL_STOP("."),EXCLAMATION("!"),QUESTION("?");
		private String symbol;
		sentenceStopSymbol(String symbol) {
			this.symbol = symbol;
		}
		public String getSymbol() {
			return this.symbol;
		}
	};
	
	public SentenceStopSymbol(String stringSymbol, int sentenceId) {
		super(stringSymbol);
		this.sentenceId = sentenceId;
	}
	
	public static boolean isSentenceStopSymbol(String symbol) {
		
		for (sentenceStopSymbol currStopSymbol : sentenceStopSymbol.values()) {
			if ( currStopSymbol.getSymbol().equals(symbol) )
				return true;
		}
		return false;
	}
	
	public int getSentenceId() {
		return sentenceId;
	}
	
}
