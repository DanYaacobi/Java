package il.ac.tau.cs.sw1.ex5;

import java.io.*;
import java.util.Locale;
import java.io.FileReader;

public class BigramModel {
	public static final int MAX_VOCABULARY_SIZE = 14500;
	public static final String VOC_FILE_SUFFIX = ".voc";
	public static final String COUNTS_FILE_SUFFIX = ".counts";
	public static final String SOME_NUM = "some_num";
	public static final int ELEMENT_NOT_FOUND = -1;
	
	String[] mVocabulary;
	int[][] mBigramCounts;
	
	// DO NOT CHANGE THIS !!! 
	public void initModel(String fileName) throws IOException{
		mVocabulary = buildVocabularyIndex(fileName);
		mBigramCounts = buildCountsArray(fileName, mVocabulary);
		
	}
	

	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */

	public String[] buildVocabularyIndex(String fileName) throws IOException{ // Q 1
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = reader.readLine();
		String[] vocabulary = new String[MAX_VOCABULARY_SIZE];
		for(int k = 0; k < vocabulary.length; k++)
			vocabulary[k] = "";
		int i = 0;
		while(line != null && i < MAX_VOCABULARY_SIZE)
		{
			for(String word: line.split(" "))
			{
				boolean num_check = false, letter_check = false, junk_check = false;
				if(i >= MAX_VOCABULARY_SIZE)
					break;
				for(int j = 0; j < word.length(); j++)
				{
					if (Character.isDigit(word.charAt(j)))
						num_check = true;
					else if (word.charAt(j) >= 'a' && word.charAt(j) <= 'z')
						letter_check = true;
					else
						junk_check = true;
				}
				if(letter_check)
				{
					word = word.toLowerCase(Locale.ROOT);
					if(find_index(vocabulary, word) == ELEMENT_NOT_FOUND)
					{
						vocabulary[i] = word;
						i++;
					}
				}
				else if (num_check == true && junk_check == false)
				{
					if(find_index(vocabulary, word) == ELEMENT_NOT_FOUND)
					{
						vocabulary[i] = SOME_NUM;
						i++;
					}
				}
			}
			line = reader.readLine();
		}
		int j = 0;
		while(vocabulary[j] != "")
			j++;
		String[] vocab = new String[j];
		for(int k = 0; k < vocab.length; k ++)
		{
			vocab[k] = vocabulary[k];
		}
		return vocab;
	}
	
	
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	private static int find_index(String[] vocab, String word)
	{// finds first appearance of a string in an array of strings, return -1 if cannot find.
		int index = 0, count = 0;
		for(int j = 0; j < vocab.length; j++)
		{
			if (vocab[j].equals(word))
			{
				index = j;
				break;
			}
			count++;
		}
		if(count > vocab.length -1)
			index = ELEMENT_NOT_FOUND;
		return index;
	}

	public int[][] buildCountsArray(String fileName, String[] vocabulary) throws IOException{ // Q - 2
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = reader.readLine();
		int[][] r_counts = new int[vocabulary.length][vocabulary.length];
		while(line != null)
		{
			String[] line_words = line.split(" ");
			for(int i = 0; i < line_words.length -1; i++)
			{
				int a = find_index(vocabulary,line_words[i].toLowerCase(Locale.ROOT));
				int b = find_index(vocabulary,line_words[i+1].toLowerCase(Locale.ROOT));
				if(a != -1 && b != -1)
					r_counts[a][b] ++;
			}
			line = reader.readLine();
		}
		return r_counts;
	}
	
	
	/*
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: fileName is a legal file path
	 */
	public void saveModel(String fileName) throws IOException { // Q-3
		File voc_file = new File(fileName + VOC_FILE_SUFFIX);
		File counts_file = new File(fileName + COUNTS_FILE_SUFFIX);
		FileWriter writer1 = new FileWriter(voc_file);
		FileWriter writer2 = new FileWriter(counts_file);
		writer1.write(mVocabulary.length + " words");
		writer1.write(System.lineSeparator());
		for (int i = 0; i < mVocabulary.length; i++)
		{
			writer1.write(i + "," + mVocabulary[i]);
			writer1.write(System.lineSeparator());
		}
		writer1.close();
		for (int j = 0; j < mBigramCounts.length; j++)
		{
			for(int k = 0; k < mBigramCounts[0].length; k ++)
			{
				writer2.write(j + "," + k + ":" + mBigramCounts[j][k]);
				writer2.write(System.lineSeparator());
			}
		}
		writer2.close();
	}
	/*
	 * @pre: fileName is a legal file path
	 */
	public void loadModel(String fileName) throws IOException{ // Q - 4
		File counts_file = new File(fileName + COUNTS_FILE_SUFFIX);
		File voc_file = new File(fileName + VOC_FILE_SUFFIX);
		BufferedReader counts_reader = new BufferedReader(new FileReader(counts_file));
		BufferedReader voc_reader = new BufferedReader(new FileReader(voc_file));
		String line = counts_reader.readLine();
		String line2 = voc_reader.readLine();
		String[] Vocabulary = new String[Character.getNumericValue(line2.charAt(0))];
		int[][] BigramCounts = new int[mBigramCounts.length][mBigramCounts.length];
		while(line != null)
		{
			int word1 = Character.getNumericValue(line.charAt(0));
			int word2 = Character.getNumericValue(line.charAt(2));
			int word1_before_word2 = Character.getNumericValue(line.charAt(4));
			BigramCounts[word1][word2] = word1_before_word2;
			line = counts_reader.readLine();
		}
		line2 = voc_reader.readLine();
		int i = 0;
		while(line2 != null)
		{
			String sliced = line2.substring(2);
			Vocabulary[i] = sliced;
			line2 = voc_reader.readLine();
			i++;
		}


		mBigramCounts = BigramCounts;
		mVocabulary = Vocabulary;
	}

	
	
	/*
	 * @pre: word is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: word is in lowercase
	 * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
	 */
	public int getWordIndex(String word){  // Q - 5

		int answer = find_index(mVocabulary,word);
			return answer;
	}
	
	
	
	/*
	 * @pre: word1, word2 are in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the count for the bigram <word1, word2>. if one of the words does not
	 * exist in the vocabulary, $ret = 0
	 */
	public int getBigramCount(String word1, String word2){ //  Q - 6
		int a = 0, b = 0, answer = 0;
		a = getWordIndex(word1);
		b = getWordIndex(word2);
		if(a != ELEMENT_NOT_FOUND && b != ELEMENT_NOT_FOUND)
			answer = mBigramCounts[a][b];
		return answer;
	}
	
	
	/*
	 * @pre word in lowercase, and is in mVocabulary
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post $ret = the word with the lowest vocabulary index that appears most fequently after word (if a bigram starting with
	 * word was never seen, $ret will be null
	 */
	public String getMostFrequentProceeding(String word){ //  Q - 7
		int vocab_index = getWordIndex(word), max =0,max_index =0;
		for(int i = 0; i < mBigramCounts[vocab_index].length; i++)
		{
			int pointer = mBigramCounts[vocab_index][i];
			if(pointer > max)
			{
				max = pointer;
				max_index = i;
			}
			else if (pointer == max)
			{
				if(i < max_index);
				max_index = i;
			}
		}
		String answer = mVocabulary[max_index];
		return answer;
	}
	
	
	/* @pre: sentence is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: each two words in the sentence are are separated with a single space
	 * @post: if sentence is is probable, according to the model, $ret = true, else, $ret = false
	 */
	public boolean isLegalSentence(String sentence){  //  Q - 8
		boolean answer = true;
		if(sentence.length() != 0)
		{
			String[] words = sentence.split(" ");
			if (words.length == 1)
			{
				int index = getWordIndex(words[0]);
				if (index == -1)
					answer = false;
			}
			else
			{

				for (int i = 0; i < words.length - 1; i++)
				{
					int index1 = getWordIndex(words[i]);
					int index2 = getWordIndex(words[i + 1]);
					if (index1 == -1 || index2 == -1)
						answer = false;
					else if (mBigramCounts[index1][index2] == 0)
					{
						answer = false;
						break;
					}
				}
			}
		}
		return answer;
	}
	
	
	
	/*
	 * @pre: arr1.length = arr2.legnth
	 * post if arr1 or arr2 are only filled with zeros, $ret = -1, otherwise calcluates CosineSim
	 */
	public static double calcCosineSim(int[] arr1, int[] arr2){ //  Q - 9
		boolean zero_check1 = false, zero_check2 = false;
		double answer = -1.0;
		for(int i = 0; i < arr1.length; i++)
		{
			if(arr1[i] != 0)
				zero_check1 = true;
			if(arr2[i] != 0)
				zero_check2 = true;
			if(zero_check1 && zero_check2)
				break;
		}
		if(zero_check1 && zero_check2)
		{
			double numerator = 0.0, denominator = 0.0, squared1 = 0.0, squared2 = 0.0;
			for(int j = 0; j < arr1.length; j ++)
			{
				numerator += arr1[j]*arr2[j];
				squared1 += arr1[j]*arr1[j];
				squared2 += arr2[j]*arr2[j];
			}
			denominator = (Math.sqrt(squared1))*(Math.sqrt(squared2));
			answer = numerator/denominator;
		}
		return answer;
	}

	
	/*
	 * @pre: word is in vocabulary
	 * @pre: the method initModel was called (the language model is initialized), 
	 * @post: $ret = w implies that w is the word with the largest cosineSimilarity(vector for word, vector for w) among all the
	 * other words in vocabulary
	 */
	private int[] WordVector(int index)
	{
		int[] word_vector = new int[mBigramCounts[index].length];
		for(int j = 0; j < mBigramCounts[index].length; j ++)
		{
			word_vector[j] = mBigramCounts[index][j];
		}

		return word_vector;
	}

	public String getClosestWord(String word){ //  Q - 10
		int index = find_index(mVocabulary,word), closest_index = index;
		double word_distance = 0.0, closest_word_distance = 0.0;
		int[] word_vector = WordVector(index);
		for(int i = 0; i < mBigramCounts.length; i ++)
		{
			if(i != index)
			{
				int[] temp_vector = WordVector(i);
				word_distance = calcCosineSim(word_vector, temp_vector);
				if(word_distance > closest_word_distance)
				{
					closest_word_distance = word_distance;
					closest_index = i;
				}
			}
		}
		return mVocabulary[closest_index];
	}
}
