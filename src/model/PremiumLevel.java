package model;

public enum PremiumLevel {
	DIAMOND(0.75),
	GOLD(0.85),
	SILVER(0.95),
	NORMAL(1.0);
	
	private final double levelDiscount;
	
	PremiumLevel(double levelDiscount){
		this.levelDiscount = levelDiscount;
	}
	
	public double discount() {
		return levelDiscount;
	}
}
