package application;

import java.util.Map;

import algorithm.kmeans.DataPoint;
import algorithm.kmeans.KMeans;

public class Main
{
	public static void main ( String[] args )
	{
		double[][] data = new double[][] {
			{ 3.0, 10.0},
			{ 3.5, 10.5},
			{10.0, 10.0},
			{10.5, 10.5},
			{ 3.0,  3.0},
			{ 3.5,  3.5},
			{10.0,  3.0},
			{10.5,  3.5}
		};
		 
		int groups = 4;
		double error = 1.0;
		
		// Each DataPoint will have a group, represented by the Integer
		Map<DataPoint, Integer> result = KMeans.executeKMeans ( data, groups, error );
		
		System.out.println ( "Acabou!" );
	}
}
