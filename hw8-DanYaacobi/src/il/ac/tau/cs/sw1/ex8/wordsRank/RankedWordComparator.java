package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.util.Comparator;

import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

class RankedWordComparator implements Comparator<RankedWord>{
	rankType rank;
	public RankedWordComparator(rankType cType) {
		rank = cType;
	}
	
	@Override
	public int compare(RankedWord o1, RankedWord o2) {
		if (o1.getRankByType(this.rank) < o2.getRankByType(this.rank))
			return 1;
		if (o1.getRankByType(this.rank) > o2.getRankByType(this.rank))
			return -1;
		return 0;
	}	
}
