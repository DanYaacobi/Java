public class Assignment02Q01 {
    public static void main(String[] args){
        for (int i = 0;i<args.length;i++)
        {
            char first_letter = args[i].charAt(0);
            if (first_letter % 3 == 0 && first_letter %2 == 0)
            {
                System.out.println(first_letter);

            }
        }
    }
}
