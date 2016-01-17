package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class EvilHangmanGame implements IEvilHangmanGame{

	Set<String> currentWords = new TreeSet<String>();
	Map<String, Set<String>> wordMap = new TreeMap<String, Set<String>>();
	Set<String>	usedLetters = new TreeSet<String>();
	String pattern = "";
	int inputWordLen = 0;
	
	@Override
	public void startGame(File dictionary, int wordLength) {
		wordMap.clear(); // protects against calling startGame within a game
		Scanner s = null;
		inputWordLen = wordLength;
		pattern = getFirstPattern();
		try {
			s = new Scanner(dictionary);
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
		
		while(s.hasNext()){
			String currentWord = s.next();
			if (currentWord.length() == wordLength){
				currentWords.add(currentWord);
			}
		}
		
		if(s != null){
			s.close();
		}	
	}
	
	public String getFirstPattern(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < inputWordLen; i++){
			sb.append('-');
		}
		return sb.toString();
	}

	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
		String guessString = Character.toString(guess);
		if (usedLetters.contains(guessString)){
			throw new GuessAlreadyMadeException();
		}
		usedLetters.add(guessString);
		buildMap(guess);
		String key = chooseKey(guess);
		currentWords = wordMap.get(key);
		return currentWords;
	}
	
	public void buildMap(char guess){
		wordMap.clear();
		for(String word : currentWords){
			String pattern = createPattern(word, guess);
			if (wordMap.containsKey(pattern)){
				wordMap.get(pattern).add(word);
			}
			else {
				Set<String> currentPattern = new TreeSet<String>();
				currentPattern.add(word);
				wordMap.put(pattern, currentPattern);
			}
		}
	}
	
	public String createPattern(String currentWord, char guess){
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < currentWord.length(); i++){
			if (currentWord.charAt(i) == guess){
				sb.append(guess);
			}
			else if (pattern.charAt(i) != '-'){
				sb.append(pattern.charAt(i));
			}
			else {
				sb.append('-');
			}
		}
		return sb.toString();
	}
	
	public String chooseKey(char guess){
		String key = "";
		filterSize();
		if (wordMap.size() > 1){
			filterFreq(guess);
			if (wordMap.size() > 1){
				filterRight (guess);
				for (String s : wordMap.keySet()){
					key = s;
				}
			}
			else {
				for (String s : wordMap.keySet()){
					key = s;
				}
			}
		}
		else {
			for (String s : wordMap.keySet()){
				key = s;
			}
		}
		pattern = key;
		return key;
	}
	
	public void filterSize() {
		int maxSize = 0;
		for (Map.Entry<String, Set<String>> entry : wordMap.entrySet()){
			if (maxSize < entry.getValue().size()){
				maxSize = entry.getValue().size();
			}
		}
		Set<String> filter = new TreeSet<String>();
		for (Map.Entry<String, Set<String>> entry : wordMap.entrySet()){
			if (entry.getValue().size() != maxSize){
				filter.add(entry.getKey());
			}
		}
		wordMap.keySet().removeAll(filter);
	}
	
	public void filterFreq(char guess){
		int least = 100;
		for(Map.Entry<String, Set<String>> entry: wordMap.entrySet()){
			int freq = 0;
			for(int i = 0; i < entry.getKey().length(); i++){
				if(entry.getKey().charAt(i) == guess){
					freq++;
				}
			}
			if(freq < least){
				least = freq;
			}
		}
		Set<String> filter = new TreeSet<String>();
		for(Map.Entry<String, Set<String>> entry: wordMap.entrySet()){
			int frequency = 0;
			for(int i = 0; i < entry.getKey().length(); i++){
				if(entry.getKey().charAt(i) == guess){
					frequency++;
				}
			}
			if(frequency != least){
				filter.add(entry.getKey());
			}
		}
		wordMap.keySet().removeAll(filter);
	}
	
	public void filterRight(char guess){
		String comparing = "~";
		for(Map.Entry<String, Set<String>> entry: wordMap.entrySet()){
			String current = entry.getKey();
			if(current.compareTo(comparing) < 0){
				comparing = current;
			}
		}
		Set<String>filter = new TreeSet<String>();
		for(Map.Entry<String, Set<String>> entry: wordMap.entrySet()){
			if(!entry.getKey().equals(comparing)){
				filter.add(entry.getKey());
			}
		}
		wordMap.keySet().removeAll(filter);
	}
	
	public Set<String> getUsedLetters() {
		return usedLetters;
	}
	
	public String getPattern(){
		return pattern;
	}
	
}
