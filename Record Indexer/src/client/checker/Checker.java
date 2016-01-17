package client.checker;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Checker {
	private Set<String> dictionary;
	
	public void useDictionary(File dictionaryFile) throws IOException {
		
		dictionary = new TreeSet<String>();
		
		Scanner scanner = new Scanner(dictionaryFile);
		
		String allWordsString = scanner.useDelimiter("\\Z").next();
		
		String[] knownWords = allWordsString.split(",");
		
		for(String word : knownWords){
			word = word.toUpperCase();
			dictionary.add(word);
		}
		
		scanner.close();
		
		
	}
	
	/**
	 * simple set function mainly made for testing existing dictionaries
	 * @param input dictionary
	 */
	
	public void setDictionary(Set<String> inDictionary){
		dictionary = inDictionary;
	}
	
	/**
	 * gets suggested words for a misspelled word user has entered
	 * @param inputWord
	 * @return a set of suggestions to display to the user
	 */

	public Set<String> getSuggestions(String inputWord){
		Set<String> edit1 = new TreeSet<String>();
		Set<String> suggestions = new TreeSet<String>();
		inputWord = inputWord.toUpperCase();
		if(dictionary.contains(inputWord)){
			suggestions.add(inputWord);
			return suggestions;
		}
		else{
			insertion(inputWord, edit1);
			deletion(inputWord, edit1);
			transposition(inputWord, edit1);
			alteration(inputWord, edit1);
			suggestions = suggestWords(edit1);
			
			Set<String> edit2 = new TreeSet<String>();
			edit2 = createEdit2(edit1);
			suggestions = suggestWords(edit2);
			
		}
		return suggestions;
	}
	/**
	 * check that words generated are in the dictionary
	 * @param set of possible words generated from user's incorrect input
	 * @return list of words that are in given dictionary; generated from user's misspelled word
	 */
	
	public Set<String> suggestWords(Set<String> edit){
		Set<String> suggestions = new TreeSet<String>();
		
		for(String s: edit){
			if(dictionary.contains(s.toUpperCase())){
				suggestions.add(s.toUpperCase());
			}
			
		}
		return suggestions;
	}
	
	public Set<String> createEdit2(Set<String> edit){
		Set<String> edit2 = new TreeSet<String>();
		for(String s : edit){
			insertion(s, edit2);
			deletion(s, edit2);
			transposition(s, edit2);
			alteration(s, edit2);
		}
		return edit2;
	}
	
	public void insertion(String word, Set<String> edit){
		for(int i = 0; i < word.length()+1; i++){
			for(char c = 'a'; c <= 'z'; c++){
				StringBuilder sb = new StringBuilder(word);
				sb.insert(i, c);
				String changed = sb.toString();
				edit.add(changed);
			}
		}
	}
	
	public void deletion(String word, Set<String> edit){
		for(int i = 0; i < word.length(); i++){
			StringBuilder sb = new StringBuilder(word);
			sb.deleteCharAt(i);
			String changed = sb.toString();
			edit.add(changed);
		}
	}
	
	public void transposition(String word, Set<String> edit){
		char[] wordArray = word.toCharArray();
		for(int i = 0; i < word.length()-1; i++){
			char temp = wordArray[i];
			wordArray[i] = wordArray[i+1];
			wordArray[i+1] = temp;
			String transposed = new String(wordArray);
			edit.add(transposed);
			wordArray = word.toCharArray();
		}
	}
	
	public void alteration(String word, Set<String> edit){
		char[] wordArray = word.toCharArray();
		for(int i = 0; i < word.length(); i++){
			for(char c = 'a'; c <= 'z'; c++){
				wordArray[i] = c;
				String altered = new String(wordArray);
				edit.add(altered);
			}
			wordArray = word.toCharArray();
		}
	}
}