package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector{
	
	Trie Trie = new Trie();
	
	public void useDictionary(String dictionaryFileName) throws IOException{
		
		String word;
		File inputFile = new File(dictionaryFileName);
		Scanner scan = new Scanner(inputFile);
		while(scan.hasNext()){
			word = scan.next().toLowerCase();
			Trie.add(word);
		}
		//System.out.print(Trie.toString());
		scan.close();
	}
		
	public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException{
		Set<String> edit1 = new TreeSet<String>();
		inputWord = inputWord.toLowerCase();
		if (Trie.find(inputWord) != null){
			return inputWord;
		}
		else{
			insertion(inputWord, edit1);
			deletion(inputWord, edit1);
			transposition(inputWord, edit1);
			alteration(inputWord, edit1);
			String suggestedWord = suggestWord(edit1);
			if(suggestedWord == null){
				Set<String> edit2 = createEdit2(edit1);
				suggestedWord = suggestWord(edit2);
				if(suggestedWord == null){
					throw new NoSimilarWordFoundException();
				}
			}
			return suggestedWord;
		}
	}
	
	public Set<String> createEdit2(Set<String> edit){
		Set<String> edit2 = new TreeSet<String>();
		for(String s: edit){
			insertion(s, edit2);
			deletion(s, edit2);
			transposition(s, edit2);
			alteration(s, edit2);
		}
		return edit2;
	}
	
	public String suggestWord(Set<String> edit){
		int max = 0; 
		String suggested = null;
		for(String s: edit){
			if(Trie.find(s) != null){
				Trie.Node n = Trie.find(s);
				if(n.getValue() > max){
					max = n.getValue();
					suggested = s;
				}
			}
		}
		return suggested;
	}
	
	public void insertion(String word, Set<String> edit){
		for(int i = 0; i < word.length() + 1; i++){
			for(char c = 'a'; c <= 'z'; c++){
				StringBuilder sb = new StringBuilder(word);
				sb.insert(i, c);
				String sbword = sb.toString();
				edit.add(sbword);
			}
		}
	}
	
	public void deletion(String word, Set<String> edit){
		for(int i = 0; i < word.length(); i++){
			StringBuilder sb = new StringBuilder(word);
			sb.deleteCharAt(i);
			String sbword = sb.toString();
			edit.add(sbword);
		}
	}
	
	public void transposition(String word, Set<String> edit){
		char[] c = word.toCharArray();
		for (int i = 0; i < word.length()-1; i++){
			char temp = c[i];
			c[i] = c[i+1];
			c[i+1] = temp;
			String transposed = new String(c);
			//System.out.println(transposed);
			edit.add(transposed);
			c = word.toCharArray();
		}
	}
	
	public void alteration(String word, Set<String> edit){
		char[] alterString = word.toCharArray();
		for(int i = 0; i < word.length(); i++){
			for(char c = 'a'; c <= 'z'; c++){
				alterString[i] = c;
				String altered = new String(alterString);
				edit.add(altered);
			}
			alterString = word.toCharArray();
		}
	}
	}
