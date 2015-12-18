package data;

public class Mot {
	
	private String mot;
	private float score;
	
	public String getMot() {
		return mot;
	}
	public void setMot(String mot) {
		this.mot = mot;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	
	public Mot(String mot, float score) {
		super();
		this.mot = mot;
		this.score = score;
	}
	
	
	
	

}
