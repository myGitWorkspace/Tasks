package tasks.task2;

import java.lang.Comparable;

public class Symbol implements Comparable<Symbol> {

	private String stringSymbol;
	private char charSymbol;
	private char charSymbolLowCase;
	private boolean isCapital = false;
	private String vowelSymbols = "AEIOUaeiouÀÅ¨ÈÎÓÛÝÞß²¯ªàå¸èîóýþÿ³¿º";
	
	public Symbol(String stringSymbol) {
		this.stringSymbol = stringSymbol.substring(0, 1);
		this.charSymbol = stringSymbol.charAt(0);
		this.charSymbolLowCase = stringSymbol.toLowerCase().charAt(0);
		if (this.stringSymbol.charAt(0) != this.charSymbolLowCase)
			this.isCapital = true;
	}
	
	public Symbol(char charSymbol) {
		this.stringSymbol = String.valueOf(charSymbol);
		this.charSymbol = charSymbol;
		this.charSymbolLowCase = this.stringSymbol.toLowerCase().charAt(0);
		if (this.charSymbol != this.charSymbolLowCase)
			this.isCapital = true;
	}
	
	public boolean equals(Object object2) {
		
		if (object2 == null) 
			throw new IllegalArgumentException("object2 parameter in equals method is null!!!");
		
		if (object2 instanceof Symbol) {
			Symbol symbol2 = (Symbol)object2;
			char char1 = this.charSymbol;
			char char2 = symbol2.getCharSymbol();
			if (char1 == char2)
				return true;
		}
		return false;
	}
	
	public int compareTo(Symbol symbol2) {
		
		if (symbol2 == null) 
			throw new IllegalArgumentException("symbol2 parameter in compareTo method is null!!!");
		
		return this.charSymbol - symbol2.getCharSymbol();
	}
	
	public int hashCode() {
		return stringSymbol.hashCode();
	}
	
	public int compareToCaseInsensitive(Symbol symbol2) {
		
		if (symbol2 == null) 
			throw new IllegalArgumentException("symbol2 parameter in compareToCaseInsensitive method is null!!!");
		
		return this.charSymbolLowCase - symbol2.getCharSymbolLowCase();
	}
	
	public String getStringSymbol() {
		return stringSymbol;
	}
	
	public char getCharSymbol() {
		return charSymbol;
	}
	
	public char getCharSymbolLowCase() {
		return charSymbolLowCase;
	}
	
	public boolean isCapital() {
		return isCapital;
	}
	
	public boolean isVowel() {
		if ( vowelSymbols.indexOf(charSymbolLowCase) == -1 )
			return false;
		return true;
	}
	
	public String toString() {
		return stringSymbol;
	}
	
}
