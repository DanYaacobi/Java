public class Assignment02Q03
{
    public static void main(String[] args)
    {
        String result = "1 1";
        int n1,n2;
        n1 = n2 = 1;
        int numOfEven = 0;
        int n = -1;
        n = Integer.parseInt(args[0]);
        for(int i = 0; i<n-2; i++)
        {
            int temp = n2;
            n2 = n2 + n1;
            n1 = temp;
            if (n2 % 2 ==0)
                numOfEven += 1;
            result += " "+ String.valueOf(n2);
        }
        System.out.println("The first "+ n +" Fibonacci numbers are:");
        System.out.println(result);
        System.out.println("The number of even numbers is: "+numOfEven);

    }

}
