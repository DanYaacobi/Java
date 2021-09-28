package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.*;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogramIterator<T extends Comparable<T>> implements Iterator<T>{

	public HashMapHistogram<T> hmap = new HashMapHistogram<>();
	HashMapHistogramIterator(Map<T,Integer> map) {
		for(T item: map.keySet())
			hmap.hash.put(item, map.get(item)); }

	public Comparator<T> Tcompare = new Comparator<T>() {
		@Override
		public int compare(T o1, T o2) {
			return o1.compareTo(o2);
		}
	};
	@Override
	public boolean hasNext() { return hmap.getItemsSet().size() != 0; }

	@Override
	public T next() {
		T max_value = null;
		for(T item : hmap.getItemsSet()) {
			if(max_value == null){
				max_value = item;}
			if(hmap.hash.get(max_value) < hmap.hash.get(item))
				max_value = item;
			if(hmap.hash.get(max_value).equals(hmap.hash.get(item))) {
				if (max_value.compareTo(item) > 0) // max value is bigger
				{
					max_value = item;
				}
			}
		}
		try {
			hmap.removeItemKTimes(max_value, hmap.getCountForItem(max_value));
		}
		catch (IllegalItemException exp1){}
		catch (IllegalKValueException exp2){}
		return max_value;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(); //no need to change this
	}
}
