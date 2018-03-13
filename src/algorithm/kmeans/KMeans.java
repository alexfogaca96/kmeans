package algorithm.kmeans;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public final class KMeans
{
	private KMeans () {}
	
	public static final Map<DataPoint, Integer> executeKMeans(double[][] data, int groups, double error)
	{
		LinkedList<DataPoint> dataPoints = formatData(data);
		DataPoint[] prototypes = defineKPrototypes(data, groups);
		
		boolean[][] dataPointsGroups = actualKMeansExecution(dataPoints, prototypes, error);
		
		return DPGroupsToMap(dataPoints, dataPointsGroups);
	}

	private static final boolean[][] actualKMeansExecution(LinkedList<DataPoint> dataPoints, DataPoint[] prototypes, double error)
	{
		double[][] distanceOfDPToPrototypes = calculateDistanceDPtoPrototypes(dataPoints, prototypes);
		boolean[][] dataPointsGroups = defineDPtoGroup(distanceOfDPToPrototypes);
		prototypes = updatePrototypesPosition(prototypes, dataPoints, dataPointsGroups);
		
		if (calculateErrorFunction(distanceOfDPToPrototypes, dataPointsGroups) <= error)
		{
			return dataPointsGroups;
		}
		
		return actualKMeansExecution(dataPoints, prototypes, error);
	}

	private static Map<DataPoint, Integer> DPGroupsToMap ( LinkedList<DataPoint> dataPoints, boolean[][] dataPointsGroups )
	{
		Map<DataPoint, Integer> mapDP = new HashMap<DataPoint, Integer>();
		
		for (int i = 0; i < dataPoints.size (); i++)
		{
			for (int k = 0; k < dataPointsGroups.length; k++)
			{
				if (dataPointsGroups[k][i] == true)
					mapDP.put ( dataPoints.get ( i ), k );
			}
		}
		
		return mapDP;
	}
	
	private static double calculateErrorFunction (double[][] distDPToPrototype, boolean[][] DPGroups)
	{
		double error = 0.0;
		
		for (int k = 0; k < distDPToPrototype.length; k++)
		{
			for (int i = 0; i < distDPToPrototype[0].length; i++)
			{
				int boolToInt = (DPGroups[k][i] == true) ? 1 : 0;
				error += Math.pow ( distDPToPrototype[k][i] * boolToInt, 2);
			}
		}
		
		return error;
	}

	private static double[][] calculateDistanceDPtoPrototypes ( LinkedList<DataPoint> dataPoints,
			DataPoint[] prototypes )
	{
		double[][] distances = new double[prototypes.length][dataPoints.size ()];
		
		for (int i = 0; i < dataPoints.size (); i++)
		{
			double[] properties = dataPoints.get ( i ).getProperties ();
			
			for (int k = 0; k < prototypes.length; k++)
			{
				double distance = 0.0;
				double[] prototypeProperties = prototypes[k].getProperties ();
				
				for (int h = 0; h < properties.length; h++)
				{
					distance += Math.pow ( (properties[h] - prototypeProperties[h]), 2 );
				}
			
				distances[k][i] = Math.sqrt ( distance );
			}
		}
		
		return distances;
	}

	private static DataPoint[] updatePrototypesPosition ( DataPoint[] prototypes, LinkedList<DataPoint> dataPoints, boolean[][] dataPointsGroups )
	{
		DataPoint[] newPrototypes = new DataPoint[prototypes.length];
		
		for (int k = 0; k < prototypes.length; k++)
		{
			double[] propertiesMeans = new double[dataPoints.get ( 0 ).getProperties ().length];

			double[][] tempProperties = new double[dataPoints.size ()][propertiesMeans.length];
			for (int i = 0 ; i < tempProperties.length; i++)
			{
				tempProperties[i] = dataPoints.get ( i ).getProperties ();
			}
			
			for (int i = 0; i < propertiesMeans.length; i++)
			{
				double distanceSum = 0;
				int numberOfDP = 0;
				
				for(int j = 0; j < dataPoints.size (); j++)
				{
					int boolToInt = 0;
					if (dataPointsGroups[k][j] == true)
					{
						boolToInt = 1;
						numberOfDP++;
					}
					
					distanceSum += tempProperties[j][i] * boolToInt;
				}
				
				propertiesMeans[i] = distanceSum / numberOfDP;
			}
			
			newPrototypes[k] = new DataPoint(propertiesMeans);
		}
		
		return newPrototypes;
	}

	private static boolean[][] defineDPtoGroup ( double[][] distances )
	{
		boolean[][] dataToPrototypes = new boolean[distances.length][distances[0].length];

		for (int i = 0; i < distances.length; i++)
		{
			int group = 0;
			double minDistance = Double.MAX_VALUE;
			
			for(int k = 0; k < distances[0].length; k++)
			{
				if(distances[k][i] < minDistance)
				{
					group = k;
				}
			}
			
			dataToPrototypes[group][i] = true;
		}
		
		return dataToPrototypes;
	}

	private static DataPoint[] defineKPrototypes ( double[][] data, int groups )
	{
		if (data.length == 0 || data[0].length == 0) return null;
		
		double auxMax, auxMin;
		double[][] range = new double[groups][data.length];
		for (int i = 0; i < data[0].length; i++)
		{	
			auxMax = Double.MIN_VALUE;
			auxMin = Double.MAX_VALUE;
			for (double[] dataPoint : data)
			{
				if (dataPoint[i] > auxMax)
				{
					auxMax = dataPoint[i];
				}
				
				if (dataPoint[i] < auxMin)
				{
					auxMin = dataPoint[i];
				}
			}
			
			for (int k = 0; k < groups; k++)
			{
				range[k][i] = Math.random () * auxMax + auxMin;
			}
		}
		
		DataPoint[] dataPoints = new DataPoint[groups];
		for (int i = 0; i < groups; i++)
		{
			dataPoints[i] = new DataPoint(range[i]); 
		}
		
		return dataPoints;
	}
	

	private static LinkedList<DataPoint> formatData ( double[][] data )
	{
		LinkedList<DataPoint> dataList = new LinkedList<DataPoint>();
		for(double[] dataPoint : data)
			dataList.add ( new DataPoint(dataPoint) );
		
		return dataList;
	}
}
