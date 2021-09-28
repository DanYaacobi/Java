package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.*;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{

	Map<T,Integer> hash = new HashMap<>();

	@Override
	public Iterator<T> iterator() {
		return new HashMapHistogramIterator<T>(this.hash);
	}

	@Override
	public void addItem(T item) {
		if(hash.containsKey(item))
			hash.replace(item,hash.get(item)+1);
		else
			hash.put(item,1);
	}

	@Override
	public void removeItem(T item) throws IllegalItemException {
		if(!hash.containsKey(item) || hash.get(item) == 0)
			throw new IllegalItemException();
		else
			hash.replace(item, hash.get(item) -1);
		}


	@Override
	public void addItemKTimes(T item, int k) throws IllegalKValueException {
		if(k < 1)
			throw new IllegalKValueException(k);
		else
			for(int i = 0; i < k; i++)
				addItem(item);
	}

	@Override
	public void removeItemKTimes(T item, int k) throws IllegalItemException, IllegalKValueException {
		if(!hash.containsKey(item))
			throw new IllegalItemException();
		else if(hash.get(item) < k || k < 1)
			throw new IllegalKValueException(k);
		else
			hash.replace(item, hash.get(item)-k);
	}

	@Override
	public int getCountForItem(T item) {
		int returned_value = 0;
		if(this.hash.containsKey(item))
			returned_value = hash.get(item);
		return returned_value;
	}

	@Override
	public void addAll(Collection<T> items) {
		for(T item : items)
			addItem(item);
	}

	@Override
	public void clear() {
		hash.clear();
	}

	@Override
	public Set<T> getItemsSet() {
		Set<T> keys_over_zero = new HashSet<>();
		for(T item : hash.keySet())
			if(hash.get(item) > 0 )
				keys_over_zero.add(item);
		return keys_over_zero; //replace this with the actual returned value
	}

	@Override
	public void update(IHistogram<T> anotherHistogram) {

		for(T item : anotherHistogram.getItemsSet())
		{
			int item_value = anotherHistogram.getCountForItem(item);
			try {addItemKTimes(item, item_value);}
			catch(IllegalKValueException exp) {}
		}
	}
}
