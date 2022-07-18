package model;

public class Customer extends User implements Upgrade{
	
	protected PremiumLevel premiumLevel;
	protected int currentPoints;
	
	private static final int NORMAL_THRESHOLD = 1000;
	private static final int SILVER_THRESHOLD = 3000;
	private static final int GOLD_THRESHOLD = 5000;
	private static final int DIAMOND_THRESHOLD = 10000;
	
	public Customer(String email, String name, String passWord, int currentPoints) {
		super(email, name, passWord);
		this.currentPoints = currentPoints;
		upGradeLevel();
	}
	
	public void upGradeLevel() {
		if(this.currentPoints <= Customer.NORMAL_THRESHOLD) {
			this.premiumLevel = PremiumLevel.NORMAL;
		}else if(this.currentPoints > Customer.NORMAL_THRESHOLD && this.currentPoints <= Customer.SILVER_THRESHOLD ) {
			this.premiumLevel = PremiumLevel.SILVER;
		}else if(this.currentPoints > Customer.SILVER_THRESHOLD && this.currentPoints <= Customer.GOLD_THRESHOLD) {
			this.premiumLevel = PremiumLevel.GOLD;
		}else if(this.currentPoints > Customer.GOLD_THRESHOLD && this.currentPoints <= Customer.DIAMOND_THRESHOLD) {
			this.premiumLevel = PremiumLevel.DIAMOND;
		}else {
			this.premiumLevel = PremiumLevel.DIAMOND;
		}
	}

	public PremiumLevel getPremiumLevel() {
		return premiumLevel;
	}

	public int getCurrentPoints() {
		return currentPoints;
	}

	public void setPremiumLevel(PremiumLevel premiumLevel) {
		this.premiumLevel = premiumLevel;
	}

	public void setCurrentPoints(int currentPoints) {
		this.currentPoints = currentPoints;
	}
	
	public String toString() {
		return "Hi, " + this.name + ". You current premium levle is:  " + this.premiumLevel.name() + ". Your current discount is: " + 
				Math.round((1.0-this.premiumLevel.discount()) * 100) + "%";
		
	}
	

}
