package org.retentionprediction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

class LinearInterpolationFunction
{
	public double[][] dDataArray;
	
	// dataPoints[x][y]
	public LinearInterpolationFunction(double[][] dataPoints)
	{
		dDataArray = dataPoints;
	}
	
	public double getAt(double x)
	{
		int i = 0;
		while (x > dDataArray[i][0])
		{
			i++;
			if (i >= dDataArray.length)
				break;
		}
		
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
}
