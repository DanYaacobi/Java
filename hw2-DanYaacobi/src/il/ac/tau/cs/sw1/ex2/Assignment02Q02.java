public class Assignment02Q02 {
    public static void  main (String[] args){
        double piEstimation = 0.0;
        int n = Integer.parseInt(args[0]);
        for (int i = 1; i <=n; i++)
        {
            if (i % 2 == 1)
            {
                piEstimation += (double) 1 / (i*2-1);
            }
            else
            {
                piEstimation -= (double) 1 / (i*2-1);
            }
        }
        piEstimation = piEstimation*4;
        System.out.println(piEstimation + " " + Math.PI);
    }
}
