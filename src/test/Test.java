package test;

public class Test {
public static void main(String[] args) {
	String test = "pasbeauxtotoji";
	System.out.println("taille du mot "+test.length() + " pour le mot : "+test);

	test = test.substring(0, test.length() - (test.length()-7));
	System.out.println("taille du mot "+test.length() + " pour le mot : "+test);
}
}
