package org.hplcsimulator;

import java.util.Arrays;
import java.util.Comparator;

class LinearInterpolationFunction
{
	public double[][] dDataArray;
	
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
	public LinearInterpolationFunction(double[][] dataPoints)
	{
		Comparator<double[]> byXVal = new DataPointComparator();

		Arrays.sort(dataPoints, byXVal);

		dDataArray = dataPoints;
	}
	
	// In order of increasing solvent composition
	public double getAt(double x)
	{
		if (x < dDataArray[0][0])
			return extrapolateBefore(x);
		else if (x > dDataArray[dDataArray.length - 1][0])
			return extrapolateAfter(x);
		
		int i = 0;
		while (x > dDataArray[i][0])
		{
			i++;
			if (i >= dDataArray.length)
				break;
		}
		
		//i will be equal to dataarray.length
		
		double y = 0;
		
		if (i >= dDataArray.length)
		{
			y = dDataArray[dDataArray.length - 1][1];
		}
		else if (i == 0)
		{
			y = dDataArray[0][1];
		}
		else
		{
			double dXValAfter = dDataArray[i][0];
			double dXValBefore = dDataArray[i - 1][0];
			double dXPosition = (x - dXValBefore)/(dXValAfter - dXValBefore);
			double dYValAfter = dDataArray[i][1];
			double dYValBefore = dDataArray[i - 1][1];
			
			y = (dXPosition * (dYValAfter - dYValBefore)) + dYValBefore;
		}

		return y;
	}	
	
	public double extrapolateBefore(double x)
	{
		// Find slope
		double dSlope = (dDataArray[1][1] - dDataArray[0][1]) / (dDataArray[1][0] - dDataArray[0][0]);
		double dIntercept = dDataArray[0][1] - (dSlope * dDataArray[0][0]);
		
		return (dSlope * x) + dIntercept;
	}
	
	public double extrapolateAfter(double x)
	{
		// Find slope
		double dSlope = (dDataArray[dDataArray.length - 1][1] - dDataArray[dDataArray.length - 2][1]) / (dDataArray[dDataArray.length - 1][0] - dDataArray[dDataArray.length - 2][0]);
		double dIntercept = dDataArray[dDataArray.length - 1][1] - (dSlope * dDataArray[dDataArray.length - 1][0]);
		
		return (dSlope * x) + dIntercept;
	}
}
