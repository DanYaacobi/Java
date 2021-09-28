import java.util.Arrays;
public class Assignment02Q05 {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int[][] matrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = Integer.parseInt(args[1 + (i * N) + j]);
            }
        }
        for (int i = 0; i < N; i++)
            System.out.println(Arrays.toString(matrix[i]));
        System.out.println("");
        for (int i = 0; i < N - 2; i++) {
            for (int j = i; j < N - 1 - i; j++) {
                int temp = matrix[j][N - i - 1]; 
                matrix[j][N - i - 1] = matrix[i][j]; 
                int temp2 = matrix[N - i - 1][N - j - 1];
                matrix[N - i - 1][N - j - 1] = temp; 
                int temp3 = matrix[N - j - 1][i];
                matrix[N - j - 1][i] = temp2;
                matrix[i][j] = temp3;
                }
            }
            for (int i = 0; i < N; i++) {
                System.out.println(Arrays.toString(matrix[i]));
            }
        }
    }

