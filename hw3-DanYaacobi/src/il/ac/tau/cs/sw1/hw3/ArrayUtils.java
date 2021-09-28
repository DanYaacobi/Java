package il.ac.tau.cs.sw1.hw3;

public class ArrayUtils {
	public static void main(String[] args)
	{
		ArrayUtils test = new ArrayUtils();
		int[] test1 = {1,-2,3,4,5};
		int answer = test.alternateSum(test1);
		System.out.println(answer);

	}
	public static int[][] transposeMatrix(int[][] m)
	{
		int n = m.length;
		if (n == 0)
			return m;
		if (n == m[0].length)
		{
			for (int i = 0; i < n; i++)
			{
				for (int j=i; j < n; j++)
				{
					if (i != j)
					{
						int tmp = m[j][i];
						m[j][i] = m[i][j];
						m[i][j] = tmp;
					}

				}
			}
			return m;
		}
		int k = m[0].length;
		int[][] Tm = new int[k][n];
		for (int i2 = 0; i2 < k; i2++)
		{
			for (int j2 = 0; j2 < n; j2++)
			{
				Tm[i2][j2] = m[j2][i2];
			}
		}
		return Tm;
	}


	public static int[] shiftArrayCyclic(int[] array, int move, char direction)
	{
		boolean kivoon = true;
		if (direction == 'L')
			if (move < 0)
				move = move * -1;
			else
				kivoon = false;
		else if (move < 0)
		{
			move = move * -1;
			kivoon = false;
		}
		if (move > array.length)
			move = move % array.length;
		if (kivoon)
		{
			for (int i = 0; i < move; i++)
			{
				int temp = array[array.length - 1];
				for (int j = array.length - 2; j >= 0; j--)
				{
					array[j + 1] = array[j];
				}
				array[0] = temp;
			}
		}
		else
		{
			for (int i = 0; i < move; i++)
			{
				int temp = array[0];
				for (int j = 1; j <= array.length - 1; j++)
				{
					array[j-1] = array[j];
				}
				array[array.length -1] = temp;
			}
		}
		return array;

	}

	public static int alternateSum(int[] array)
	{
		 boolean even = false;
		 int max = 0;
		 for (int i = 0; i < array.length;i++)
		 {
		 	if (i%2 == 0)
		 		even = true;
		 	else
		 		even = false;
		 	for (int j = i; j < array.length; j++)
			{
					int sum = 0;
		 			for (int k = i; k <= j; k++)
					{
						if (even == true)
						{
							if (k % 2 == 0)
								sum += array[k];
							else
								sum -= array[k];
						}
						else
						{
							if (k % 2 == 1)
								sum += array[k];
							else
								sum -= array[k];
						}

					}
		 			if (sum > max)
		 				max = sum;
			}

		 }
		return max;

	}

	public static int findPath2(int[][] m, int i, int j, int k) {
		int answer = 0;
		int maslool = 0;
		int position = i;
		int n = m.length;
		if (k != 0)
		{
			int r = 0;
			while(r < n)
			{
					if(m[position][r] != 0)
					{
						maslool += 1;
						if (r == j)
						{
							if (maslool == k)
							{
								answer = 1;
								break;
							}
						}
						position = r;
						r = 0;
					}
					else
						r++;
			}
		}
		return answer;

	}
	public static int findPath(int[][] m, int i, int j, int k)
	{
		return 0;
	}
}
