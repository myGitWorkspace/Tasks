package tasks.task3;

public class Tariff {
	
	private String name;
	private String tariffID;
	private String operatorName;
	private int payroll;
	private CallPrices callPrices = new CallPrices();
	private double smsPrice;
	private Parameters parameters = new Parameters();
	
	public Tariff() {
		
	}
	public Tariff(String name,String tariffID, String operatorName, int payroll, CallPrices callPrices, double smsPrice, Parameters parameters) {
		this.name = name;
		this.tariffID = tariffID;
		this.operatorName = operatorName;
		this.payroll = payroll;
		this.callPrices = callPrices;
		this.smsPrice = smsPrice;
		this.parameters = parameters;
	}
	
	static class CallPrices {
		private double insideNetwork;
		private double outsideNetwork;
		private double landLinePhone;
		
		public CallPrices() {
			
		}
		
		public void setInsideNetwork(double insideNetwork) {
			this.insideNetwork = insideNetwork;
		}
		
		public void setOutsideNetwork(double outsideNetwork) {
			this.outsideNetwork = outsideNetwork;
		}
		
		public void setLandLinePhone(double landLinePhone) {
			this.landLinePhone = landLinePhone;
		}
		
		public double getInsideNetwork() {
			return insideNetwork;
		}
		
		public double getOutsideNetwork() {
			return outsideNetwork;
		}
		
		public double getLandLinePhone() {
			return landLinePhone;
		}
		
		public String toString() {
			String result = "CallPrices:\n[\ninsideNetwork="+insideNetwork+";\noutsideNetwork="+outsideNetwork+";\nlandLinePhone="+landLinePhone+";\n]\n";
			return result;
		}
	}
	public enum Tariffication {
		SECONDS12, MINUTE1;
	}
	static class Parameters {
		
		private String[] favouriteNumber;
		private int countFavourNumber;
		private Tariffication tariffication;
		private double connectionCost;
		
		public Parameters() {
			
		}
		
		public void setFavouriteNumber(String newFavouriteNumber) {
			
			if (favouriteNumber != null) {
				
				int length = favouriteNumber.length;
				String[] newArrayNumbers = new String[length+1];
				for(int i=0; i<length; i++)
					newArrayNumbers[i] = favouriteNumber[i];
				newArrayNumbers[length] = newFavouriteNumber;				
				favouriteNumber = newArrayNumbers;
				countFavourNumber = length+1;
			} else {				
				favouriteNumber = new String[1];
				this.favouriteNumber[0] = newFavouriteNumber;
			}
			
		}
		
		public void setCountFavourNumber(int countFavourNumber) {
			this.countFavourNumber = countFavourNumber;
		}
		
		public void setConnectionCost(double connectionCost) {
			this.connectionCost = connectionCost;
		}
		
		public void setTariffication(String newTariffication) {
			Tariffication value = Tariffication.valueOf(newTariffication.toUpperCase());
			if ( value == Tariffication.MINUTE1 || value == Tariffication.SECONDS12 )
				tariffication = value;
			else
				tariffication = Tariffication.MINUTE1;
		}
		
		public String[] getFavouriteNumber() {
			return favouriteNumber;
		}
		
		public int getCountFavourNumber() {
			return countFavourNumber;
		}
		
		public Tariffication getTariffication() {
			return tariffication;
		}
		
		public double getConnectionCost() {
			return connectionCost;
		}
		public String toString() {
			String favouriteNumbers = "FavouriteNumbers:\n[\n";
			for(int i=0; i<favouriteNumber.length; i++)
				favouriteNumbers += favouriteNumber[i]+",";
			favouriteNumbers += "\n]\n";
			String result = "Parameters:\n[\n"+favouriteNumbers+"countFavourNumber="+countFavourNumber+";\nconnectionCost="+connectionCost+";\ntariffication="+tariffication+"\n]";
			return result;
		}
	}
	
	public void setName(String name) {		
		this.name = name;
	}
	
	public void setTariffID(String tariffID) {
		this.tariffID = tariffID;
	}
	
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public void setPayroll(int payroll) {
		this.payroll = payroll;
	}
	
	public void setCallPrices(CallPrices callPrices) {
		this.callPrices = callPrices;
	}
	
	public void setSmsPrice(double smsPrice) {
		this.smsPrice = smsPrice;
	}
	
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
	
	public String getName() {
		return name;
	}
	
	public String getTariffID() {
		return tariffID;
	}
	
	public String getOperatorName() {
		return operatorName;
	}
	
	public int getPayroll() {
		return payroll;
	}
	
	public CallPrices getCallPrices() {
		return callPrices;
	}
	
	public double getSmsPrice() {
		return smsPrice;
	}
	
	public Parameters getParameters() {
		return parameters;
	}
	
	public String toString() {
		String result = "\n\nname="+name+";\ntariffID="+tariffID+";\noperatorName="+operatorName+";\npayroll="+payroll+";\n"+callPrices+"smsPrice="+smsPrice+"\n"+parameters;
		return result;
	}
}
