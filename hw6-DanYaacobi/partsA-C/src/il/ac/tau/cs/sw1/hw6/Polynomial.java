package il.ac.tau.cs.sw1.hw6;

import java.util.Arrays;

public class Polynomial {
	/*
	 * Creates the zero-polynomial with p(x) = 0 for all x.
	 */
	private double[] polynomial;

	public Polynomial()
	{
		polynomial = new double[1];
		polynomial[0] = 0;
	}
	/*
	 * Creates a new polynomial with the given coefficients.
	 */
	public Polynomial(double[] coefficients) 
	{
		polynomial = new double[coefficients.length];
		for(int i = 0; i < coefficients.length; i ++)
		{
			polynomial[i] = coefficients[i];
		}

	}
	/*
	 * Addes this polynomial to the given one
	 *  and retruns the sum as a new polynomial.
	 */
	public Polynomial adds(Polynomial polynomial1)
	{
		double[] coef = Get_Coefficients(polynomial);
		double[] coef1 = Get_Coefficients(polynomial1.polynomial);
		double[] final_coef = new double[Math.max(coef.length,coef1.length)];
		for(int i = 0; i < final_coef.length; i++)
		{
			if(i > coef.length-1)
				final_coef[i] += coef1[i];
			else if (i > coef1.length-1)
				final_coef[i] += coef[i];
			else
				final_coef[i] += coef[i] + coef1[i];
		}
		Polynomial p = new Polynomial(final_coef);
		return p;
	}
	/*
	 * Multiplies a to this polynomial and returns 
	 * the result as a new polynomial.
	 */

	private double[] Get_Coefficients(double[] p){

		double[] answer = new double[p.length];
		for(int i = 0; i < answer.length; i ++)
		{
			answer[i] = p[i];
		}
		return answer;
	}

	public Polynomial multiply(double a)
	{
		double[] coef = Get_Coefficients(polynomial);
		Polynomial p = new Polynomial(coef);
		for(int i = 0; i < p.polynomial.length; i++)
		{
			p.polynomial[i] = p.polynomial[i]*a;
		}
		return p;
		
	}
	/*
	 * Returns the degree (the largest exponent) of this polynomial.
	 */
	public int getDegree()
	{
		int answer = polynomial.length;
		if(answer == 1)
			answer = 0;
		else
			answer --;
		return answer;
	}

	/*
	 * Returns the coefficient of the variable x 
	 * with degree n in this polynomial.
	 */
	public double getCoefficient(int n)
	{
		return polynomial[n];
	}
	
	/*
	 * set the coefficient of the variable x 
	 * with degree n to c in this polynomial.
	 * If the degree of this polynomial < n, it means that that the coefficient of the variable x 
	 * with degree n was 0, and now it will change to c. 
	 */
	public void setCoefficient(int n, double c)
	{
		int degree_difference = n - polynomial.length;
		if(degree_difference < 0)
			this.polynomial[n] = c;
		else
		{
			double[] coef = Get_Coefficients(polynomial);
			double[] true_coef = new double[coef.length + degree_difference+1];
			for(int k = 0; k < coef.length; k++)
			{
				true_coef[k] = coef[k];
			}
			true_coef[n] = c;
			Polynomial p = new Polynomial(true_coef);
			this.polynomial = Get_Coefficients(p.polynomial);
		}


	}
	
	/*
	 * Returns the first derivation of this polynomial.
	 *  The first derivation of a polynomal a0x0 + ...  + anxn is defined as 1 * a1x0 + ... + n anxn-1.
	
	 */
	public Polynomial getFirstDerivation()
	{
		double[] coef = Get_Coefficients(polynomial);
		double[] d_coef = {0.0};
		if(coef.length > 1)
		{
			d_coef = Arrays.copyOfRange(coef, 1, coef.length);
		}
		Polynomial p = new Polynomial(d_coef);
		for (int i = 0; i < p.polynomial.length; i++)
			p.polynomial[i] = p.polynomial[i] * (i + 1);
		return p;
	}
	
	/*
	 * given an assignment for the variable x,
	 * compute the polynomial value
	 */
	public double computePolynomial(double x)
	{
		double answer = 0.0;
		for(int i = 0; i < polynomial.length; i ++)
		{
			answer += polynomial[i]*Math.pow(x,i);
		}
		return answer;
	}
	
	/*
	 * given an assignment for the variable x,
	 * return true iff x is an extrema point (local minimum or local maximum of this polynomial)
	 * x is an extrema point if and only if The value of first derivation of a polynomal at x is 0
	 * and the second derivation of a polynomal value at x is not 0.
	 */
	public boolean isExtrema(double x)
	{
		boolean extrema = false;
		Polynomial first_derivative = getFirstDerivation();
		Polynomial second_derivative = first_derivative.getFirstDerivation();
		if(first_derivative.computePolynomial(x) == 0 && second_derivative.computePolynomial(x) != 0)
			extrema = true;
		return extrema;
	}
	
	
	
	

    
    

}
