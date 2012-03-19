package org.retentionprediction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

class InterpolationFunction
{
	public double[][] dInterpolationParameters; // Contains an, bn, cn, dn for each 3rd order polynomial
	public double[] dRanges;					// Contains the range of x to use for each polynomial
	public boolean bLinear = false;
	public LinearInterpolationFunction lifTwoPoint = null;
	
	class DataPointComparator implements Comparator<double[]>
	{
		@Override
		public int compare(double[] arg0, double[] arg1) 
		{
			if (arg0[0] > arg1[0])
			{
				return 1;
			}
			else if (arg0[0] < arg1[0])
			{
				return -1;
			}
			else
				return 0;
		}
	}


	// dataPoints[x][y]
	public InterpolationFunction(double[][] dataPoints)
	{
		if (dataPoints.length == 2)
		{
			bLinear = true;
			lifTwoPoint = new LinearInterpolationFunction(dataPoints);
			return;
		}
		// 1) Sort the data points
		// 2) Build the matrix
		// 3) Solve the matrix
		// 4) Save the parameters in dInterpolationParameters
		Comparator<double[]> byXVal = new DataPointComparator();

		Arrays.sort(dataPoints, byXVal);
		
		int m = (dataPoints.length - 1) * 4;
		int n = (dataPoints.length - 1) * 4;
		
		double[][] dMatrix = new double[n][m+1];
		
		// Equations to run through data points
		int iRowPos = 0;
		
		for (int i = 0; i < dataPoints.length - 1; i++)
		{
			// ai
			dMatrix[iRowPos][i*4] = 1;
			dMatrix[iRowPos+1][i*4] = 1;
			
			// bi
			dMatrix[iRowPos][(i*4)+1] = dataPoints[i][0];
			dMatrix[iRowPos+1][(i*4)+1] = dataPoints[i+1][0];
			
			// ci
			dMatrix[iRowPos][(i*4)+2] = Math.pow(dataPoints[i][0],2);
			dMatrix[iRowPos+1][(i*4)+2] = Math.pow(dataPoints[i+1][0],2);
						
			// di
			dMatrix[iRowPos][(i*4)+3] = Math.pow(dataPoints[i][0],3);
			dMatrix[iRowPos+1][(i*4)+3] = Math.pow(dataPoints[i+1][0],3);
						
			// y
			dMatrix[iRowPos][n] = dataPoints[i][1];
			dMatrix[iRowPos+1][n] = dataPoints[i+1][1];
			
			iRowPos += 2;
		}
		
		// Next add rows for the matched slopes
		for (int i = 0; i < dataPoints.length - 2; i++)
		{
			// bi
			dMatrix[iRowPos][(i*4)+1] = 1;
			
			// ci
			dMatrix[iRowPos][(i*4)+2] = 2 * dataPoints[i+1][0];
			
			// di
			dMatrix[iRowPos][(i*4)+3] = 3 * Math.pow(dataPoints[i+1][0],2);
			
			// bi+1
			dMatrix[iRowPos][(i*4)+5] = -1;
			
			// ci+1
			dMatrix[iRowPos][(i*4)+6] = -2 * dataPoints[i+1][0];
			
			// di+1
			dMatrix[iRowPos][(i*4)+7] = -3 * Math.pow(dataPoints[i+1][0],2);
			
			iRowPos += 1;
		}

		// Next add rows for the matched 2nd deriv.
		for (int i = 0; i < dataPoints.length - 2; i++)
		{
			// ci
			dMatrix[iRowPos][(i*4)+2] = 2;
			
			// di
			dMatrix[iRowPos][(i*4)+3] = 6 * dataPoints[i+1][0];
			
			// ci+1
			dMatrix[iRowPos][(i*4)+6] = -2;
			
			// di+1
			dMatrix[iRowPos][(i*4)+7] = -6 * dataPoints[i+1][0];
			
			iRowPos += 1;
		}
		
		// Next add rows for boundary conditions
		
		// Left side:
		// c0
		dMatrix[iRowPos][2] = 2;
		
		// d0
		dMatrix[iRowPos][3] = 6 * dataPoints[0][0];
		
		// c0+1
		dMatrix[iRowPos][6] = -2;
		
		// d0+1
		dMatrix[iRowPos][7] = -6 * dataPoints[1][0];
		
		iRowPos++;
		
		// Right side:
		// cn
		dMatrix[iRowPos][((dataPoints.length-2)*4)+2] = 2;
		
		// dn
		dMatrix[iRowPos][((dataPoints.length-2)*4)+3] = 6 * dataPoints[dataPoints.length-1][0];
		
		// cn-1
		dMatrix[iRowPos][((dataPoints.length-3)*4)+2] = -2;
		
		// dn-1
		dMatrix[iRowPos][((dataPoints.length-3)*4)+3] = -6 * dataPoints[dataPoints.length-2][0];
		
		// 2) Now solve the matrix
		// perform Gauss-Jordan Elimination
		// m is number columns
		// n is number of rows
		
		int i = 0;
		int j = 0;
		
		// n = num rows
		// m = num columns
		
		while(i < n && j < m)
		{
			//look for a non-zero entry in col j at or below row i
			int k = i;
			
			while (k < n && dMatrix[k][j] == 0) 
				k++;

			// if such an entry is found at row k
			if (k < n)
			{
				// if k is not i, then swap row i with row k
				if (k != i) 
					swap(dMatrix, i, k, j);

				// if dMatrix[i][j] is not 1, then divide row i by dMatrix[i][j]
				if (dMatrix[i][j] != 1)
					divide(dMatrix, i, j);

				// eliminate all other non-zero entries from col j by subtracting from each
				// row (other than i) an appropriate multiple of row i
				eliminate(dMatrix, i, j);
				i++;
			}
			j++;
		}
		
		// Now put the parameter values in dInterpolationParameters[][]
		
		dRanges = new double[dataPoints.length - 2];
		dInterpolationParameters = new double[dataPoints.length-1][4];
		
		for (i = 0; i < dInterpolationParameters.length; i++)
		{
			dInterpolationParameters[i][0] = dMatrix[(i*4)][m];
			dInterpolationParameters[i][1] = dMatrix[(i*4)+1][m];
			dInterpolationParameters[i][2] = dMatrix[(i*4)+2][m];
			dInterpolationParameters[i][3] = dMatrix[(i*4)+3][m];
		}
		
		for (i = 0; i < dRanges.length; i++)
		{
			dRanges[i] = dataPoints[i+1][0];
		}
	}
	
	public double getAt(double x)
	{
		if (bLinear)
		{
			return lifTwoPoint.getAt(x);
		}
		
		int i = 0;
		while (x > dRanges[i])
		{
			i++;
			if (i >= dRanges.length)
				break;
				
		}
		
		double y = dInterpolationParameters[i][0] + dInterpolationParameters[i][1]*x + dInterpolationParameters[i][2]*Math.pow(x,2) + dInterpolationParameters[i][3]*Math.pow(x,3);
		return y;
	}
	
	// swap row i with row k
	// pre: A[i][q]==A[k][q]==0 for 1<=q<j
	static void swap(double[][] A, int i, int k, int j)
	{
		int m = A[0].length - 1;
		double temp;
		for (int q = j; q <= m; q++)
		{
			temp = A[i][q];
			A[i][q] = A[k][q];
			A[k][q] = temp;
		}
	}

	// divide row i by A[i][j]
	// pre: A[i][j]!=0, A[i][q]==0 for 1<=q<j
	// post: A[i][j]==1;
	static void divide(double[][] A, int i, int j)
	{
		int m = A[0].length - 1;
		
		for (int q = j + 1; q <= m; q++) 
			A[i][q] /= A[i][j];
		
		A[i][j] = 1;
	}

	// subtract an appropriate multiple of row i from every other row
	// pre: A[i][j]==1, A[i][q]==0 for 1<=q<j
	// post: A[p][j]==0 for p!=i
	static void eliminate(double[][] A, int i, int j)
	{
		int n = A.length;
		int m = A[0].length - 1;
		
		for (int p = 0; p < n; p++)
		{
			if (p != i && A[p][j] != 0)
			{
				for (int q = j + 1; q <= m; q++)
				{
					A[p][q] -= A[p][j] * A[i][q];
				}
				A[p][j] = 0;
			}
		}
	}
}
