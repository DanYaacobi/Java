package il.ac.tau.cs.sw1.hw3;


import java.util.Arrays;

public class StringUtils {

	public static String findSortedSequence(String str)
	{

		String result_string = "";
		if (!(str.equals("")))
		{
			String[] arr = str.split(" ");
			int count = 1;
			int countmax = 1;
			String temp_string = arr[0];
			char first = arr[0].charAt(0);
			for (int i = 1; i < arr.length; i++) {
				if (arr[i].charAt(0) > first || arr[i].equals(arr[i-1])) {
					temp_string += " " + arr[i];
					count += 1;
					first = arr[i].charAt(0);
				} else {
					count = 1;
					temp_string = arr[i];
					first = arr[i].charAt(0);
				}
				if (count >= countmax) {
					countmax = count;
					result_string = temp_string;
				}
			}
		}
		return result_string;
	}
	static String[] remove(String[] args, String a)
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
	public static boolean isAnagram(String a, String b)
	{
		boolean answer = false;
		String[] arr1 = a.split("");
		String[] arr2 = b.split("");
		int count = 0;
		for(int j =0;j<arr1.length;j++)
		{
			if(arr1[j].equals(" "))
				arr1 = remove(arr1," ");
			else
				arr1[j] = arr1[j].toLowerCase();
		}
		for(int k = 0;k < arr2.length; k++)
		{
			if(arr2[k].equals(" "))
				arr2 = remove(arr2," ");
			else
				arr2[k] = arr2[k].toLowerCase();
		}
		Arrays.sort(arr1);
		Arrays.sort(arr2);
		if (arr1.length == arr2.length)
		{
			for (int i = 0; i < arr1.length; i++)
			{
				if(arr1[i].equals(arr2[i]))
					count += 1;
			}
			if(count == arr1.length)
				answer = true;
		}
		return answer;
	}
	public static boolean isEditDistanceOne(String a, String b)

	{
		boolean answer = false;
		if(a.length() == b.length())
		{
			int count = 0;
			for(int i = 0; i < a.length(); i++)
			{
				if(a.charAt(i) != b.charAt(i))
				{
					count ++;
				}

			}
			if (count <= 1)
			{
				answer = true;
			}
		}
		else if(a.length() == b.length()-1 || b.length() == a.length()-1)
		{
			if((a.substring(0,a.length())).equals(b))
			{
				answer = true;
			}
			else if ((b.substring(0,b.length())).equals(a))
			{
				answer = true;
			}
			else if ((a.substring(1,a.length())).equals(b))
			{
				answer = true;
			}
			else if ((b.substring(1,b.length())).equals(b))
			{
				answer = true;
			}
			else
			{
				int count = 0;
				int n = Math.min(b.length(),a.length());
				for(int j = 0; j < n; j++)
				{
					if (a.charAt(j) != b.charAt(j))
					{
						if (j < n-1)
						{
							if (a.charAt(j + 1) == b.charAt(j) || b.charAt(j + 1) == a.charAt(j))
								count++;
						}
					}
				}
				if (count <= 1)
					answer = true;
			}
		}
			return answer;
	}
	
}
