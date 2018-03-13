package algorithm.kmeans;

public final class DataPoint
{
	private double[] properties;
	private int group;
	
	public DataPoint(double... data)
	{
		int length = data.length;
		properties = new double[length];
		
		for(int i = 0; i < length; i++)
		{
			properties[i] = data[i];
		}
		
		group = -1;
	}

	public double[] getProperties ()
	{
		return properties.clone ();
	}
	
	public void setGroup(int group)
	{
		if (group < -1)
		{
			return;
		}
		
		this.group = group;
	}
	
	public int getGroup()
	{
		return group;
	}
	
}
