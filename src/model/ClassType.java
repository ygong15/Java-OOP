package model;


public enum ClassType {
	FIRST(2.5),
	BUSINESS(1.5),
	ECONOMY(1.0);
	
	private final double classRate;
	ClassType(double classRate) {
		this.classRate = classRate;
	}
	
	public static ClassType matchGenre(String g) {
		//Genre gen = Genre.valueOf(g.toUpperCase());
		ClassType gen = ECONOMY;
		// if g contains " " or "-", replace with _, also get rid of "
		g = g.replace("-", "").replace("\"", "");

		for(ClassType x : ClassType.values()) {
			if(x.toString().equalsIgnoreCase(g)) { //perfect genre match
				gen = x;
			}
		}
		return gen;
	}
	
	public double classRatio() {
		return classRate;
	}
}
