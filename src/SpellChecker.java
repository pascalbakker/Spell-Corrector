import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

import sun.rmi.runtime.Log;

public class SpellChecker {
	static HashMap<String,Object> dictionary; 
	ArrayList<String> mispelled; //Mispelled words found in document
	
	SpellChecker(String fileLocation, String dictionaryLocation) throws IOException{
		writeDictionary(dictionaryLocation);
		mispelled = findMispelled(fileLocation);
	}
	
	/** Fills hashtable up with words
	 * 
	 * @param dictionaryLocation
	 * @throws IOException 
	 */
	public static void writeDictionary(String dictionaryLocation) throws IOException{
		HashMap<String,Object> dict = new HashMap<String,Object>();
        Scanner read = new Scanner(new FileReader(dictionaryLocation));
        while(read.hasNext()){
        	String word = read.next();
        	dict.put(word, null);
        }
        dictionary = dict;
        System.out.println(dictionary.size());
	}
	
	
	/**
	 * Determines if a word is mispelled
	 * @param word
	 * @return true if mispelled, false otherwise
	 */
	public boolean isMispelled(String word){
		//System.out.println("Word"+word);
		//System.out.println("In dict? "+dictionary.contains(word.toLowerCase()));
		if(word.length()==0)
			return false;
		
		//If in dictionary or is an integer or is a pronoun, return false.
		if(dictionary.containsKey(word.toLowerCase())
			||isNumber(word)
			||Character.isUpperCase(word.charAt(0))){
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
	public boolean isNumber(String word){
	    try { 
	        Integer.parseInt(word); 
	    }catch(NumberFormatException e){ 
	        return false; 
	    }catch(NullPointerException e){
	        return false;
	    }
	    return true;
	}
	
	public String removeCharacter(String word, int index){
		return word.substring(0,index)+word.substring(index+1);
	}
	
	/** Remove period and comma at end of words
	 * 
	 * @param word
	 * @return String
	 */
	public String removeUnwantedCharacters(String word){
		char [] weird_characters = {',','.',';',':','!','"','â','€','œ','˜','™'};
		String [] other = {"?","*","(",")","[","]"};
		for(char c: weird_characters){
			word = word.replace(c+"", "");
		}
		for(String s: other){
			word = word.replace(s, "");
		}
		return word;
	}
	/**
	 * Returns a list of mispelled words
	 * @param sourceDocument The document to correct
	 * @throws IOException
	 */
	public ArrayList<String> findMispelled(String sourceDocument) throws IOException{
		ArrayList<String> mispelled = new ArrayList<String>(); //Mispelled words placed in here
		String currentWord = ""; //used for parsing document
        Scanner read = new Scanner(new FileReader(sourceDocument)); //Document to read for errors
        
        while(read.hasNext()){
        	currentWord = read.next();
        	String fixedWord = removeUnwantedCharacters(currentWord);
        	if(isMispelled(fixedWord)){
				mispelled.add(fixedWord);
    		}
        }
        return mispelled;
	}
	
	/** returns mispelled words
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getMispelledWords(){
		return mispelled;
	}
	
	//Testing
	public static void main(String[] args) throws IOException{
		SpellChecker test = new SpellChecker("sample.txt","dictionary.txt");
		System.out.println(test.getMispelledWords());
	}
	
}
