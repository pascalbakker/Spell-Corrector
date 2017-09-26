import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException{
		SpellChecker spellChecker = new SpellChecker("dictionary.txt");
		
		SpellCorrecter spellCorrecter = new SpellCorrecter();
		System.out.println(spellCorrecter.getPermutationsOfAWord("cant"));
		
	}
}
