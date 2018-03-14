package algorithm.kmeans;

import java.util.LinkedList;

public final class KMeans {
    private KMeans() {
    }

    public static final LinkedList<DataPoint> executeKMeans(final double[][] data, final int groups,
	    final double error) {
	final LinkedList<DataPoint> dataPoints = formatData(data);
	final DataPoint[] prototypes = defineKPrototypes(data, groups);

	boolean[][] dataPointsGroups = actualKMeansExecution(dataPoints, prototypes, error);
	while (groupsInDPGroupsIsNotValid(dataPointsGroups)) {
	    final DataPoint[] newPrototypes = defineKPrototypes(data, groups);
	    dataPointsGroups = actualKMeansExecution(dataPoints, newPrototypes, error);
	}

	return addDPAndGroupsToLinkedList(dataPoints, dataPointsGroups);
    }

    private static boolean groupsInDPGroupsIsNotValid(final boolean[][] dataPointsGroups) {
	for (int i = 0; i < dataPointsGroups.length; i++) {
	    boolean elementPresence = false;
	    for (int j = 0; j < dataPointsGroups[0].length; j++) {
		if (dataPointsGroups[i][j]) {
		    elementPresence = true;
		}
	    }

	    if (!elementPresence) {
		return true;
	    }
	}

	return false;
    }

    private static final boolean runKMeansAgain(final double errorFunction, final int count, final double error) {
	if (errorFunction <= error) {
	    return false;
	}

	if (count >= 3) {
	    return false;
	}

	return true;
    }

    private static final boolean[][] actualKMeansExecution(final LinkedList<DataPoint> dataPoints,
	    DataPoint[] prototypes, final double error) {
	double[][] distanceOfDPToPrototypes = calculateDistanceDPtoPrototypes(dataPoints, prototypes);
	boolean[][] dataPointsGroups = defineDPtoGroup(distanceOfDPToPrototypes);
	double errorFunction = calculateErrorFunction(distanceOfDPToPrototypes, dataPointsGroups);

	double lastErrorFunction = 0;
	int count = 0;
	while (runKMeansAgain(errorFunction, count, error)) {
	    prototypes = updatePrototypesPosition(prototypes, dataPoints, dataPointsGroups);
	    distanceOfDPToPrototypes = calculateDistanceDPtoPrototypes(dataPoints, prototypes);
	    dataPointsGroups = defineDPtoGroup(distanceOfDPToPrototypes);

	    lastErrorFunction = errorFunction;
	    errorFunction = calculateErrorFunction(distanceOfDPToPrototypes, dataPointsGroups);

	    if (isNotThatDifferent(errorFunction, lastErrorFunction, error)) {
		count++;
		continue;
	    }
	}

	return dataPointsGroups;
    }

    private static boolean isNotThatDifferent(final double errorFunction, final double lastErrorFunction,
	    final double errorAccepted) {
	return ((errorFunction >= (lastErrorFunction - (errorAccepted / 2)))
		&& (errorFunction <= (lastErrorFunction + (errorAccepted / 2))));
    }

    private static LinkedList<DataPoint> addDPAndGroupsToLinkedList(final LinkedList<DataPoint> dataPoints,
	    final boolean[][] dataPointsGroups) {
	final LinkedList<DataPoint> linkedListDP = new LinkedList<DataPoint>();

	for (int i = 0; i < dataPoints.size(); i++) {
	    for (int k = 0; k < dataPointsGroups.length; k++) {
		if (dataPointsGroups[k][i] == true) {
		    dataPoints.get(i).setGroup(k);
		    linkedListDP.add(dataPoints.get(i));
		}
	    }
	}

	return linkedListDP;
    }

    private static double calculateErrorFunction(final double[][] distDPToPrototype, final boolean[][] DPGroups) {
	double error = 0.0;

	for (int k = 0; k < distDPToPrototype.length; k++) {
	    for (int i = 0; i < distDPToPrototype[0].length; i++) {
		final int boolToInt = (DPGroups[k][i] == true) ? 1 : 0;
		if (boolToInt == 1) {
		    error += Math.pow(distDPToPrototype[k][i] * boolToInt, 2);
		}
	    }
	}

	return error;
    }

    private static double[][] calculateDistanceDPtoPrototypes(final LinkedList<DataPoint> dataPoints,
	    final DataPoint[] prototypes) {
	final double[][] distances = new double[prototypes.length][dataPoints.size()];

	for (int i = 0; i < dataPoints.size(); i++) {
	    final double[] properties = dataPoints.get(i).getProperties();

	    for (int k = 0; k < prototypes.length; k++) {
		double distance = 0.0;
		final double[] prototypeProperties = prototypes[k].getProperties();

		for (int h = 0; h < properties.length; h++) {
		    distance += Math.pow((properties[h] - prototypeProperties[h]), 2);
		}

		distances[k][i] = Math.sqrt(distance);
	    }
	}

	return distances;
    }

    private static DataPoint[] updatePrototypesPosition(final DataPoint[] prototypes,
	    final LinkedList<DataPoint> dataPoints, final boolean[][] dataPointsGroups) {
	final DataPoint[] newPrototypes = new DataPoint[prototypes.length];

	for (int k = 0; k < prototypes.length; k++) {
	    final double[] propertiesMeans = new double[dataPoints.get(0).getProperties().length];

	    final double[][] tempProperties = new double[dataPoints.size()][propertiesMeans.length];

	    for (int i = 0; i < tempProperties.length; i++) {
		tempProperties[i] = dataPoints.get(i).getProperties();
	    }

	    for (int i = 0; i < propertiesMeans.length; i++) {
		double distanceSum = 0;
		int numberOfDP = 0;

		for (int j = 0; j < dataPoints.size(); j++) {
		    int boolToInt = 0;
		    if (dataPointsGroups[k][j] == true) {
			boolToInt = 1;
			numberOfDP++;
		    }

		    distanceSum += (tempProperties[j][i] * boolToInt);
		}

		propertiesMeans[i] = (distanceSum / numberOfDP);
	    }

	    newPrototypes[k] = new DataPoint(propertiesMeans);
	}

	return newPrototypes;
    }

    private static boolean[][] defineDPtoGroup(final double[][] distances) {
	final boolean[][] dataToPrototypes = new boolean[distances.length][distances[0].length];

	for (int i = 0; i < distances[0].length; i++) {
	    int group = 0;
	    double minDistance = Double.MAX_VALUE;

	    for (int k = 0; k < distances.length; k++) {
		if (distances[k][i] < minDistance) {
		    minDistance = distances[k][i];
		    group = k;
		}
	    }

	    dataToPrototypes[group][i] = true;
	}

	return dataToPrototypes;
    }

    private static DataPoint[] defineKPrototypes(final double[][] data, final int groups) {
	if ((data.length == 0) || (data[0].length == 0)) {
	    return null;
	}

	double auxMax, auxMin;
	final double[][] range = new double[groups][data[0].length];
	for (int i = 0; i < data[0].length; i++) {
	    auxMax = Double.MIN_VALUE;
	    auxMin = Double.MAX_VALUE;

	    for (int j = 0; j < data.length; j++) {
		if (data[j][i] > auxMax) {
		    auxMax = data[j][i];
		}

		if (data[j][i] < auxMin) {
		    auxMin = data[j][i];
		}
	    }

	    for (int k = 0; k < groups; k++) {
		range[k][i] = (Math.random() * (auxMax - auxMin)) + auxMin;
	    }
	}

	final DataPoint[] dataPoints = new DataPoint[groups];
	for (int i = 0; i < groups; i++) {
	    dataPoints[i] = new DataPoint(range[i]);
	}

	return dataPoints;
    }

    private static LinkedList<DataPoint> formatData(final double[][] data) {
	final LinkedList<DataPoint> dataList = new LinkedList<DataPoint>();
	for (final double[] dataPoint : data) {
	    dataList.add(new DataPoint(dataPoint));
	}

	return dataList;
    }
}
