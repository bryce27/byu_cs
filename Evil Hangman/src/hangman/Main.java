package hangman;

import java.io.File;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import hangman.IEvilHangmanGame.GuessAlreadyMadeException;

public class Main {

	public static void main(String[] args){
		String dictionaryName = "";
		int wordLength = 0;
		int guesses = 0;
		if(args.length == 3){
			dictionaryName = args[0];
			wordLength = Integer.parseInt(args[1]);
			guesses = Integer.parseInt(args[2]);
		}
		else{
			System.out.println("Use this format: java [main class name] dictionary wordlength guesses");
		}
		EvilHangmanGame game = new EvilHangmanGame();
		game.startGame(new File(dictionaryName), wordLength);
		runGame(game, guesses);
	}
	
	public static void runGame(EvilHangmanGame game, int guesses){
		
		Set<String> usedLetters = game.getUsedLetters();
		String pattern = game.getFirstPattern();
		printGame(guesses, usedLetters, pattern);
		Scanner input = new Scanner(System.in);
		String inputChar = "";
		char guess = 0;
		while(guesses > 0){
			Set<String> results = new TreeSet<String>();
			//inputChar = input.next().toLowerCase();
			inputChar = input.nextLine().toLowerCase();
			if (inputChar.length() > 1){
				System.out.print("Invalid character, try again: ");
				continue;
			}
			if (inputChar.length() > 0 ) {
				guess = inputChar.charAt(0);
			}
			if (Character.isSpaceChar(guess)){
				System.out.print("Invalid character, try again: ");
				continue;
			}
			if(!Character.isLetter(guess)){
				System.out.print("Invalid character, try again: ");
				continue;
			}
			
			try {
				results = game.makeGuess(guess);
				pattern = game.getPattern();
				if(pattern.contains(String.valueOf(guess))){
					int numchars = 0;
					for(int i = 0; i < pattern.length(); i++){
						if(pattern.charAt(i) == guess){
							numchars++;
						}
					}
					guesses++;
					printResponse(true, guess, numchars);
				}
				else{
					printResponse(false, guess, 0);
				}
			} catch (GuessAlreadyMadeException e) {
				System.out.print("You already made that guess or your input is invalid, try another one: ");
				continue;
			}
			int count = 0;
			for(int i = 0; i < pattern.length(); i++){
				if(pattern.charAt(i) != '-'){
					count++;
				}
			}
			if(count == pattern.length()){
				printWon(results.toString());
				break;
			}
			guesses--;
			if(guesses == 0){
				String word = results.iterator().next();
				printLost(word);
			}
			printGame(guesses, usedLetters, pattern);
		}
		if(input != null){
			input.close();
		}
		
	}
	
	public static void printResponse(boolean contains, char guess, int numbers){
		if(contains){
			System.out.println("Yes, there is " + numbers + " " + guess + "\n");
		}
		else{
			System.out.println("Sorry, there are no " + guess + "'s" + "\n");
		}
	}
	
	public static void printGame(int guesses, Set<String> usedLetters, String pattern){
			
			System.out.println("You have " + guesses + " guesses left");
			StringBuilder sb = new StringBuilder();
			for (Iterator<String> it = usedLetters.iterator(); it.hasNext(); ) {
		        String s = it.next();
		        sb.append(s);
		        sb.append(" ");
		    }
			System.out.println("Used letters: " + sb.toString());
			System.out.println("Word: " + pattern);
			System.out.print("Enter guess: ");
	}
	
	public static void printWon(String correct){
		System.out.println("You win! " + correct);
		System.exit(0);
	}
	
	public static void printLost(String correct){
		System.out.println("You lose!");
		System.out.println("The word was: " + correct);
		System.exit(0);
	}
	
}
