package model;

public enum RoomType {
	SUITE(3.0),
	DELUX(2.5),
	STANDARD(1.0);
	
	private final double rate;
	
	RoomType(double rate){
		this.rate = rate;
	}
	
	public static RoomType matchGenre(String g) {
		//Genre gen = Genre.valueOf(g.toUpperCase());
		RoomType gen = STANDARD;
		// if g contains " " or "-", replace with _, also get rid of "
		g = g.replace("-", "").replace("\"", "");

		for(RoomType x : RoomType.values()) {
			if(x.toString().equalsIgnoreCase(g)) { //perfect genre match
				gen = x;
			}
		}
		return gen;
	}
	
	public double rate() {
		return rate;
	}
}

