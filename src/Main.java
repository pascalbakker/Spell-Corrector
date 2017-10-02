import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException{
		SpellChecker spellChecker = new SpellChecker("sample.txt","dictionary.txt");
		spellChecker.findMispelled();

		SpellCorrecter spellCorrecter = new SpellCorrecter(spellChecker.getMispelledWords());
		spellCorrecter.printToFile();
		
	}
}
