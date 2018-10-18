package other;

public enum Service {
	EMAIL(0), FACEBOOK(1), TWITTER(2);
	
	private int typeOfServiceNumber;
	
	private Service(int typeOfServiceNumber) {
		this.typeOfServiceNumber = typeOfServiceNumber;
	}
	
	public int getTypeOfServiceNumber() {
		return typeOfServiceNumber;
	}
	
	public String stringFormat() {
		return name().charAt(0) + name().substring(1).toLowerCase();
	}
}
