package algorithm.kmeans;

public final class DataPoint {
    private final double[] properties;
    private int group;

    public DataPoint(final double... data) {
	final int length = data.length;
	properties = new double[length];

	for (int i = 0; i < length; i++) {
	    properties[i] = data[i];
	}

	group = -1;
    }

    public double[] getProperties() {
	return properties.clone();
    }

    public void setGroup(final int group) {
	if (group < -1) {
	    return;
	}

	this.group = group;
    }

    public int getGroup() {
	return group;
    }

    @Override
    public String toString() {
	final StringBuilder DP = new StringBuilder();

	DP.append(group + ": ");
	int i;
	for (i = 0; i < (properties.length - 1); i++) {
	    DP.append(properties[i] + ", ");
	}
	DP.append(properties[i] + ";");

	return DP.toString();
    }

}