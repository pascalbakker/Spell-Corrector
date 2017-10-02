import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class SpellCorrecter {
	private ArrayList<String> misspelledWords;
	private HashMap<String, ArrayList<String>> wordListWithPerm;
	
	//Use this object if correcting a list of words
	public SpellCorrecter(ArrayList<String> misspelledWords){
		wordListWithPerm = new HashMap<String, ArrayList<String>>();
		this.misspelledWords = misspelledWords;
		getAllPermutations();
	}
	
	//Use this to correct a single word
	public SpellCorrecter(){}

	
	/** Finds all permutations of a misspelled word and puts it a hashmap.
	 *   
	 */
	public void getAllPermutations(){
		for(String word: misspelledWords){
			ArrayList<String> wordPerms = getPermutationsOfAWord(word); //Get permutations for the word.
			wordListWithPerm.put(word, wordPerms); //put misspelled word and permutations in hashmap.

		}
	}
	
	/** Get all permutations of a word using insertion, deletion, subtitution, and transpose.
	 * 
	 * @param word
	 * @return
	 */
	public ArrayList<String> getPermutationsOfAWord(String word){
		ArrayList<String> perms = new ArrayList<String>();
		perms.addAll(insertionMethod(word,perms));
		perms.addAll(deletionMethod(word,perms));
		perms.addAll(substitutionMethod(word,perms));
		perms.addAll(transposeMethod(word,perms));
		return perms;
	}
	
	/** Gets all permutations of word using insertion method
	 * 
	 * @param word
	 * @return
	 */
	public ArrayList<String> insertionMethod(String word,ArrayList<String> currentList){
		ArrayList<String> wordPerms = new ArrayList<String>();
		String [] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","'"," "};
		
		//For every letter, insert it into every spot of the word
		for(String letter: alphabet){
			for(int i=0;i<word.length();i++){
				String newWord = word.substring(0, i) + letter + word.substring(i,word.length()); //New word with letter
				
				//Check if valid word and not in current list
				if(!SpellChecker.isMispelled(newWord)&&!currentList.contains(newWord)){
					wordPerms.add(newWord);
				}
			}
		}
		
		return wordPerms;
	}
	
	/** Gets all permutations of word using deletion method
	 * 
	 * @param word
	 * @return
	 */
	public ArrayList<String> deletionMethod(String word,ArrayList<String> currentList){
		ArrayList<String> wordPerms = new ArrayList<String>();
		for(int i=0;i<word.length();i++){
			String newWord;
			
			//Removes letter from word
			if(i!=0)
				newWord = word.substring(0, i) + word.substring(i+1,word.length()); //New word with letter
			else if(i==word.length()-1)
				newWord = word.substring(0,word.length()-1);
			else
				newWord = word.substring(1,word.length());
			
			//Check if valid word and not in current list
			if(!SpellChecker.isMispelled(newWord)&&!currentList.contains(newWord)){
					wordPerms.add(newWord);
			}
		}
		
		return wordPerms;
	}
	
	/** Get all permutations of word using substitution method
	 * 
	 * @param word
	 * @return
	 */
	public ArrayList<String> substitutionMethod(String word,ArrayList<String> currentList){
		ArrayList<String> wordPerms = new ArrayList<String>();
		String [] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","'"," "};
		
		//Insert letter into every spot
		for(String letter: alphabet){
			for(int i=0;i<word.length();i++){
				String newWord = word;
				StringBuilder str = new StringBuilder(word);
				str.insert(i, letter);
				newWord = str.toString();
				
				//Check if valid word and not in current list
				if(!SpellChecker.isMispelled(newWord)&&!currentList.contains(newWord)){
					wordPerms.add(newWord);
				}
			}
		}
		return wordPerms;
	}
	
	/** Get all permutations of word using transpose method
	 * 
	 * @param word
	 * @return
	 */
	public ArrayList<String> transposeMethod(String word,ArrayList<String> currentList){
		ArrayList<String> wordPerms = new ArrayList<String>();
		for(int i=1;i<word.length();i++){
			
			//Convert word to char array then swap i and i-1
			char [] wordChar = word.toCharArray();
			char temp = wordChar[i-1];
			wordChar[i-1] = wordChar[i];
			wordChar[i] = temp;
			String newWord = wordChar.toString();
			
			//Check if valid word and not in current list
			if(!SpellChecker.isMispelled(newWord)&&!currentList.contains(newWord)){
				wordPerms.add(newWord);
			}
		}
		
		return wordPerms;
	}
		
	/** Returns a hashmap with the key being a misspelled word and the object being an arraylist of permutations(order 1).
	 * 
	 * @return
	 */
	public HashMap<String, ArrayList<String>> getWordListWithPerm(){
		return wordListWithPerm;
	}
	
	public void printToFile() throws IOException{
		if(wordListWithPerm.size()==0)
			return;
		FileWriter out = new FileWriter("output.txt");
		for(String key: wordListWithPerm.keySet()){
			out.write(key);
			System.out.print(key);
			out.write("[");
			for(String correction: wordListWithPerm.get(key)){
				out.write(" "+correction);
			}
			
			out.write("]\r\n");
		}
		out.close();
	}
	
	public static void main(String[] args) throws IOException{
		SpellChecker spellChecker = new SpellChecker("dictionary.txt","sample.txt");
		spellChecker.findMispelled();

		SpellCorrecter spellCorrecter = new SpellCorrecter(spellChecker.getMispelledWords());
		System.out.println(spellCorrecter.getWordListWithPerm().size());
		spellCorrecter.printToFile();
	}
	
}
