import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

import sun.rmi.runtime.Log;

/**
 * Spell Checker Class
 * 
 * <P> Determines if word is misspelled. If given a 
 * @author Pascal Bakker
 *
 */
public class SpellChecker {
	static HashMap<String,Object> dictionary; 
	ArrayList<String> mispelled; //Misspelled words found in document
	String fileLocation;		//File to check for errors
	
	
	SpellChecker(String fileLocation, String dictionaryLocation) throws IOException{
		writeDictionary(dictionaryLocation); //Writes words to hashmap data structure
		this.fileLocation = fileLocation;
	}
	
	SpellChecker(String dictionaryLocation) throws IOException{
		writeDictionary(dictionaryLocation); //Writes words to hashmap data structure
		mispelled = new ArrayList<String>();
	}
	

	/* ===========================================================
	 * Static methods used by both SpellChecker and SpellCorrecter.
	 * ===========================================================
	 */
	
	/** Fills hashtable up with words
	 * 
	 * @param dictionaryLocation
	 * @throws IOException 
	 */
	public static void writeDictionary(String dictionaryLocation) throws IOException{
		HashMap<String,Object> dict = new HashMap<String,Object>();
        Scanner read = new Scanner(new FileReader(dictionaryLocation)); //Read words from dictionary.txt
        while(read.hasNext()){
        	String word = read.next();
        	dict.put(word, null); //put word in dictionary as key
        }
        dictionary = dict;
	}
	
	
	/**
	 * Determines if a word is misspelled
	 * @param word
	 * @return true if misspelled, false otherwise
	 */
	public static boolean isMispelled(String word){
		if(word.length()==0)
			return false;
		
		//If in dictionary or is an integer or is a pronoun, return false.
		if(dictionary.containsKey(word.toLowerCase())
			||isNumber(word)
			||Character.isUpperCase(word.charAt(0))
			||(word.contains("www")&&word.contains("org"))){
			return false;
		}else{
			return true;	
		}
	}
	
	/**
	 * Determines if word is an integer
	 * @param word
	 * @return True if number, false otherwise
	 */
	public static boolean isNumber(String word){
	    try { 
	        Integer.parseInt(word); 
	    }catch(NumberFormatException e){ 
	        return false; 
	    }catch(NullPointerException e){
	        return false;
	    }
	    return true;
	}
	
	/* ===========================================================
	 * Methods used by SpellChecker object
	 * ===========================================================
	 */
	
	/** Removes all unwanted characters from a word
	 * 
	 * @param word
	 * @return String of new word
	 */
	public String removeUnwantedCharacters(String word){
		//Replaces all unwanted characters in the string
		String newWord = word.replaceAll("[\\-\\+\\=\\.\\|\\^\\:\\.\\;\\â\\€\\!\\˜\\™\\*\\œ\\,\\?\\_\\(\\[\\]\"\\$\\”\\#\\'\\)]", "");
		//IN PROGRESS: remove ? from string
		newWord = newWord.replace("?", "");
		return newWord;
	}
	
	/**
	 * Returns a list of misspelled words
	 * @param sourceDocument The document to correct
	 * @throws IOException
	 */
	public void findMispelled() throws IOException{
		try{
			ArrayList<String> mispelled = new ArrayList<String>(); //Misspelled words placed in here
			String currentWord = ""; //used for parsing document
	        Scanner read = new Scanner(new FileReader(fileLocation)); //Document to read for errors
	        
	        //Reads word from document and puts each misspelled word in a array
	        while(read.hasNext()){
	        	currentWord = read.next();
	        	String fixedWord = removeUnwantedCharacters(currentWord);
	    			
	        	if(isMispelled(fixedWord)){
					mispelled.add(fixedWord);
	    		}
	        }
	        //Set mispelled list to local one
			this.mispelled = mispelled;
		}catch(NullPointerException e){
			System.out.println("File to read for errors not initialized: "+ e);
		}
	}
	
	/** returns misspelled words
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getMispelledWords(){
		return mispelled;
	}
	
	
	//Testing Method
	public static void main(String[] args) throws IOException{
		SpellChecker test = new SpellChecker("sample.txt","dictionary.txt");
		test.findMispelled();
		System.out.println(test.getMispelledWords());

	}
	
}
