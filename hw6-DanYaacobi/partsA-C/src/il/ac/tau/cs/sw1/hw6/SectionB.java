package il.ac.tau.cs.sw1.hw6;

import java.util.Arrays;

public class SectionB {
	
	/*
	* @post $ret == true iff exists i such that array[i] == value
	*/
	public static boolean contains(int[] array, int value) {
		boolean check = false;
		int i = 0;
		while(i < array.length && check == false)
		{
			if (array[i] == value)
				check = true;
			i ++;
		}
		return check;
	}
	
	// there is intentionally no @post condition here 
	/*
	* @pre array != null
	* @pre array.length > 2
	* @pre Arrays.equals(array, Arrays.sort(array))
	*/
	public static int unknown(int[] array) {
		boolean check = true;
		int answer = 1;
		if(array == null)
			check = false;
		else if(array.length <= 2)
			check = false;
		else
		{
			int[] array2 = array.clone();
			Arrays.sort(array2);
			if (Arrays.equals(array,array2) == false)
				check = false;
		}
		if(check == false)
			answer = 0;
		return answer;
	}
	/*
	* @pre Arrays.equals(array, Arrays.sort(array))
	* @pre array.length >= 1
	* @post for all i array[i] <= $ret
	*/
	public static int max(int[] array) { 
		return array[0];
	}
	
	/*
	* @pre array.length >=1
	* @post for all i array[i] >= $ret
	* @post Arrays.equals(array, prev(array))
	*/
	public static int min(int[] array) {
		Arrays.sort(array);
		return array[array.length-1];
	}
	
	/*
	* @pre word.length() >=1
	* @post for all i : $ret.charAt(i) == word.charAt(word.length() - i - 1)

	*/
	public static String reverse(String word) 
	{
		String r_word = "";
		String[] word_split= word.split("");
		for(int i = word_split.length - 1; i >= 0; i--)
		{
			r_word += word_split[i];
		}
		return r_word;
	}
	
	/*
	* @pre array != null
	* @pre array.length > 2
	* @pre Arrays.equals(array, Arrays.sort(array))
	* @pre exist i,j such that: array[i] != array[j]
	* @post !Arrays.equals($ret, Arrays.sort($ret))
	* @post for any x: contains(prev(array),x) == true iff contains($ret, x) == true
	*/
	public static int[] guess(int[] array) { 
		for(int i = 0; i < array.length - 1; i++)
		{
			if(array[i] < array[i+1])
			{
				int temp = array[i];
				array[i] = array[i+1];
				array[i+1] = temp;
			}
		}
		return array;
	}

}
