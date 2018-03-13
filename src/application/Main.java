package application;

import java.util.LinkedList;

import algorithm.kmeans.DataPoint;
import algorithm.kmeans.KMeans;

public class Main {
    public static void main(final String[] args) {
	final double[][] data = new double[][] { { 3.0, 10.0 }, { 3.5, 10.5 }, { 10.0, 10.0 }, { 10.5, 10.5 },
		{ 3.0, 3.0 }, { 3.5, 3.5 }, { 10.0, 3.0 }, { 10.5, 3.5 } };

	final int groups = 4;
	final double error = 1.0;

	// Each DataPoint will have a group, represented by the Integer
	final LinkedList<DataPoint> result = KMeans.executeKMeans(data, groups, error);

	for (final DataPoint dp : result) {
	    System.out.println(dp.toString());
	}
    }
}
