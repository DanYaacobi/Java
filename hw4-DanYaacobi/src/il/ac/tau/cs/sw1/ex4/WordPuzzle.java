package il.ac.tau.cs.sw1.ex4;

import java.util.Random;
import java.util.Scanner;

public class WordPuzzle {
	public static final char HIDDEN_CHAR = '_';
	public static final int MAX_VOCABULARY_SIZE = 3000;
		/*
	 * @pre: template is legal for word
	 */
	public static char[] createPuzzleFromTemplate(String word, boolean[] template) { // Q - 1
		char[] riddle = new char[word.length()];
		for (int i = 0; i < word.length(); i++) {
			if (template[i])
				riddle[i] = HIDDEN_CHAR;
			else
				riddle[i] = word.charAt(i);
		}
		return riddle;
	}

	public static boolean checkLegalTemplate(String word, boolean[] template) { // Q - 2
		boolean answer = true;
		if (word.length() != template.length)
			answer = false;
		else {
			boolean check = template[0];
			int i = 1;
			while (template[i] == check)
				i++;
			if (i == template.length)
				answer = false;
			else {
				for (int j = 0; j < template.length; j++) {
					if (template[j]) {
						char temp = word.charAt(j);
						for (int k = 0; k < template.length; k++) {
							if (word.charAt(k) == temp) {
								if (!template[k])
									answer = false;
							}

						}
					}
				}

			}
		}

		return answer;
	}

	/*
	 * @pre: 0 < k < word.lenght(), word.length() <= 10
	 */
	private static String[] remove(String[] args, String a)
	{
		int count = 0;
		for(int j = 0; j < args.length; j++)
		{
			if(args[j].equals(a))
				count ++;
		}
		int n = args.length - count;
		int j = 0;
		String[] newargs = new String[n];
		for(int i = 0; i < args.length; i ++)
		{
			if(!(args[i].equals(a)))
			{
				newargs[j] = args[i];
				j++;
			}
		}
		if(newargs.length == 0)
			newargs = new String[]{""};
		return newargs;
	}
	private static boolean[] convert_binary_to_bool(String binary)
	{
		boolean[] result = new boolean[binary.length()];
		for(int i = 0; i < binary.length(); i ++)
		{
			if(binary.charAt(i) == '1')
				result[i] = true;
		}
		return result;
	}
	public static boolean[][] getAllLegalTemplates(String word, int k) {  // Q - 3
		int[] nums = new int[(int) (Math.pow(2, word.length()))];
		for (int i = 0; i < nums.length; i++)
			nums[i] = i;
		String[] binary = new String[nums.length];
		for (int j = 0; j < binary.length; j++) {
			binary[j] = Integer.toBinaryString(nums[j]);
			if (binary[j].length() < 4) {
				for (int r = binary[j].length(); r < word.length(); r++)
					binary[j] = "0" + binary[j];
			}
		} // converted all numbers to an array for their respective binary numbers in Strings
		int count = 0;
		int t = 0;
		while(t < binary.length)
		{
			for (int r = 0; r < binary[t].length(); r++)
			{
				if (binary[t].charAt(r) == '1')
					count++;
			}
			if (count != k)
			{
				binary = remove(binary, binary[t]);
				t--;
			}
			else
			{
				boolean[] temp = convert_binary_to_bool(binary[t]);
				if (!checkLegalTemplate(word,temp))
				{
					binary = remove(binary,binary[t]);
					t--;
				}
			}
			count = 0;
			t++;
		}// Array of binary strings containing k 1's in an ascending order
		boolean[][] result = new boolean[binary.length][binary[0].length()];
		for(int p = 0; p < binary.length; p++)
			result[p] = convert_binary_to_bool(binary[p]);
		return result;
	}


	/*
	 * @pre: puzzle is a legal puzzle constructed from word, guess is in [a...z]
	 */
	public static int applyGuess(char guess, String word, char[] puzzle) { // Q - 4
		int count = 0;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == guess) {
				if (puzzle[i] == HIDDEN_CHAR)
				{
					puzzle[i] = guess;
					count++;
				}
			}
		}
		return count;
	}


	/*
	 * @pre: puzzle is a legal puzzle constructed from word
	 * @pre: puzzle contains at least one hidden character.
	 * @pre: there are at least 2 letters that don't appear in word, and the user didn't guess
	 */
	public static char[] getHint(String word, char[] puzzle, boolean[] already_guessed) { // Q - 5
		Random rand = new Random();
		char[] answer = new char[2];
		char false_char =' ';
		int correct = rand.nextInt(puzzle.length);
		int incorrect;
		while (puzzle[correct] != HIDDEN_CHAR)
			correct = rand.nextInt(puzzle.length);
		char correct_char = word.charAt(correct);
		boolean check = false;
		while (!check) {
			String word_temp = new String(word);
			char[] puzzle_temp = puzzle.clone();
			incorrect = rand.nextInt(already_guessed.length);
			false_char = (char)(incorrect + 'a');
			String false_char_s = String.valueOf(false_char);
			if (already_guessed[incorrect] == false && false_char != correct_char && applyGuess(false_char, word_temp, puzzle_temp) == 0 && !(word.contains(false_char_s))) {
				check = true;
				if (false_char > correct_char) {
					answer[0] = correct_char;
					answer[1] = false_char;
				} else {
					answer[0] = false_char;
					answer[1] = correct_char;
				}
			}
		}
		return answer;
	}


	public static char[] mainTemplateSettings(String word, Scanner inputScanner) { // Q - 6
		System.out.println("--- Settings stage ---");
		Random rand = new Random();
		char[] answer = new char[word.length()];
		boolean[] temp = new boolean[word.length()];
		while (true)
		{
			System.out.println("Choose a (1) random or (2) manual template:");
			int input = inputScanner.nextInt();
			if (input == 1)
			{
				boolean[][] templates = new boolean[(int)(Math.pow(2,word.length()))][word.length()];
				System.out.println("Enter number of hidden characters");
				int h_chars = inputScanner.nextInt();
				if (h_chars > word.length() || h_chars <= 0)
					System.out.println("Cannot generate puzzle, try again.");
				else
				{
					templates = getAllLegalTemplates(word, h_chars);
					if (templates.length == 0)
						System.out.println("Cannot generate puzzle, try again.");

					else {
						int random_value = rand.nextInt(templates.length);
						temp = templates[random_value];
						answer = createPuzzleFromTemplate(word, temp);
						break;
					}
				}
			}
			else if (input == 2)
			{
				boolean check = true;
				System.out.println("Enter your puzzle template:");
				String s_input = inputScanner.next();
				String[] split_string = new String[word.length()];
				split_string = s_input.split(",");
				for(int j = 0; j < split_string.length; j ++)
				{
					if(split_string[j].charAt(0) == HIDDEN_CHAR)
						temp[j] = true;
					else if(split_string[j].charAt(0) == 'X')
						temp[j] = false;
					else
					{
						check = false;
						break;
					}
				}
				if (check)
				{
					if (checkLegalTemplate(word, temp))
					{
						answer = createPuzzleFromTemplate(word, temp);
						break;
					}
					else
						check = false;
				}
				else if(!check)
					System.out.println("Cannot generate puzzle, try again.");
			}
		}
		return answer;
	}

	public static void mainGame(String word, char[] puzzle, Scanner inputScanner) { // Q - 7
		System.out.println("--- Game stage ---");
		int guess_num = 3;
		boolean check_puzzle = false;
		char[] hint = new char[2];
		boolean[] already_guessed = new boolean[26];
		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i] == HIDDEN_CHAR)
				guess_num++;
		}
		while (guess_num > 0)
		{
			for (int j = 0; j < puzzle.length; j++)
				System.out.print(puzzle[j]);
			System.out.println(" ");
			System.out.println("Enter your guess:");
			char guess = inputScanner.next().charAt(0);
			if (guess == 'H')
			{
				char[]temp_puzzle = new char[puzzle.length];
				temp_puzzle = puzzle.clone();
				hint = getHint(word, temp_puzzle, already_guessed);
				System.out.println("Here's a hint for you: choose either "+ hint[0] + " or " + hint[1] + ".");
			}
			else
			{
				guess_num --;
				already_guessed[guess - 'a'] = true;
				if (applyGuess(guess,word,puzzle) > 0)
				{
					int puzzle_counter = 0;
					for (int r = 0; r < puzzle.length; r++)
					{
						if (puzzle[r] != HIDDEN_CHAR)
							puzzle_counter++;
					}
					if (puzzle_counter == puzzle.length)
						check_puzzle = true;
					if(!check_puzzle)
						System.out.println("Correct Guess, " + guess_num + " guesses left.");
				}
				else
					System.out.println("Wrong Guess, " + guess_num + " guesses left.");
				if(check_puzzle)
				{
					System.out.println("Congratulations! You solved the puzzle!");
					break;
				}
			}

		}
		if(!check_puzzle)
			System.out.println("Game over!");

	}




/*************************************************************/
/********************* Don't change this ********************/
/*************************************************************/

	public static void main(String[] args) throws Exception { 
		if (args.length < 1){
			throw new Exception("You must specify one argument to this program");
		}
		String wordForPuzzle = args[0].toLowerCase();
		if (wordForPuzzle.length() > 10){
			throw new Exception("The word should not contain more than 10 characters");
		}
		Scanner inputScanner = new Scanner(System.in);
		char[] puzzle = mainTemplateSettings(wordForPuzzle, inputScanner);
		mainGame(wordForPuzzle, puzzle, inputScanner);
		inputScanner.close();
	}


	public static void printSettingsMessage() {
		System.out.println("--- Settings stage ---");
	}

	public static void printEnterWord() {
		System.out.println("Enter word:");
	}
	
	public static void printSelectNumberOfHiddenChars(){
		System.out.println("Enter number of hidden characters:");
	}
	public static void printSelectTemplate() {
		System.out.println("Choose a (1) random or (2) manual template:");
	}
	
	public static void printWrongTemplateParameters() {
		System.out.println("Cannot generate puzzle, try again.");
	}
	
	public static void printEnterPuzzleTemplate() {
		System.out.println("Enter your puzzle template:");
	}


	public static void printPuzzle(char[] puzzle) {
		System.out.println(puzzle);
	}


	public static void printGameStageMessage() {
		System.out.println("--- Game stage ---");
	}

	public static void printEnterYourGuessMessage() {
		System.out.println("Enter your guess:");
	}

	public static void printHint(char[] hist){
		System.out.println(String.format("Here's a hint for you: choose either %s or %s.", hist[0] ,hist[1]));

	}
	public static void printCorrectGuess(int attemptsNum) {
		System.out.println("Correct Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWrongGuess(int attemptsNum) {
		System.out.println("Wrong Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWinMessage() {
		System.out.println("Congratulations! You solved the puzzle!");
	}

	public static void printGameOver() {
		System.out.println("Game over!");
	}

}
