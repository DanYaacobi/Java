package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;
import il.ac.tau.cs.sw1.ex8.histogram.IHistogram;
import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {

	public static final int UNRANKED_CONST = 30;

	HashMapHistogram<String> map = new HashMapHistogram<>();
	Map<String,HashMapHistogram<String>> data = new HashMap<>();
	Map<String,RankedWord> rankings = new HashMap<>();
	Map<String,Set<String>> total_words = new HashMap<>();
	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 */
  	public void indexDirectory(String folderPath) throws IOException{
		//This code iterates over all the files in the folder. add your code wherever is needed
		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		List<String> words = new ArrayList<>();
		for (File file : listFiles) {
			// for every file in the folder
			if (file.isFile()) {
					words = (FileUtils.readAllTokens(file));
					Set<String> words_to_add = new HashSet<>();
					for(String s : words) {
						map.addItem(s);
						words_to_add.add(s);
					}
					total_words.put(file.getName(),words_to_add);
				HashMapHistogram<String> temp = new HashMapHistogram<>();
				temp.update(this.map);
				data.put(file.getName(),temp);
				map.clear();
				}
			}

		for(String files_name : total_words.keySet()) {
			for (String word : total_words.get(files_name)) {
				Map<String, Integer> ranking = new HashMap<>();
				for (String file_name : data.keySet()) {
					HashMapHistogram<String> map = new HashMapHistogram<String>();
					map.update(data.get(file_name));
					int i = 1;
					Iterator<String> mapiterator = map.iterator();
					String name;
					while (mapiterator.hasNext()) {
						name = mapiterator.next();
						if (name.equals(word)) {
							ranking.put(file_name, i);
							break;
						}
						i++;
					}

				}
				RankedWord r_word = new RankedWord(word, ranking);
				this.rankings.put(word, r_word);
			}
		}
  	}




  	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getCountInFile(String filename, String word) throws FileIndexException{
		int answer = 0;
		answer = (data.get(filename)).getCountForItem(word.toLowerCase(Locale.ROOT));
		return answer;
	}
	
	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getRankForWordInFile(String filename, String word) throws FileIndexException{
		int rank = 0;
		if(total_words.containsKey(filename)) {
			rank = total_words.get(filename).size() + UNRANKED_CONST;
			for (RankedWord r : rankings.values()) {
				if (r.getWord().equals(word) && r.getRanksForFile().containsKey(filename)) {
					rank = r.getRanksForFile().get(filename);
					break;
				}
			}
		}
		else
			throw new FileIndexException("File name does not exist.");
		return rank;

	}
	
	/*
	 * @pre: the index is initialized
	 * @pre word is not null
	 */
	public int getAverageRankForWord(String word){
		return rankings.get(word).getRankByType(rankType.average);
	}
	
	private List<String> K_Sorting(rankType c, int k)
	{
		List<RankedWord> ranked_words = new ArrayList<>();
		List<String> words = new ArrayList<>();
		for (RankedWord word : rankings.values())
			if(word.getRankByType(c) < k)
				ranked_words.add(word);
		ranked_words.sort(new RankedWordComparator(c));
		for(RankedWord r : ranked_words)
			words.add(r.getWord());
		return words;
	}
	public List<String> getWordsWithAverageRankSmallerThanK(int k){
		List<String> words = K_Sorting(rankType.average, k);
		return words;
	}
	
	public List<String> getWordsWithMinRankSmallerThanK(int k){
		List<String> words = K_Sorting(rankType.min, k);
		return words;
	}
	
	public List<String> getWordsWithMaxRankSmallerThanK(int k){
		List<String> words = K_Sorting(rankType.max, k);
		return words;
	}

}
