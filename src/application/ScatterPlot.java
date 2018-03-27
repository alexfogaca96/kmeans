package application;

import java.awt.Color;
import java.util.LinkedList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import algorithm.kmeans.DataPoint;

public class ScatterPlot extends JFrame
{
	public ScatterPlot(String title, LinkedList<DataPoint> dataset, int groups)
	{
		XYDataset datasetToPlot = createDataset(dataset, groups);
		
		JFreeChart chart = ChartFactory.createScatterPlot("", "", "", datasetToPlot);

		XYPlot plot = (XYPlot)chart.getPlot();
		plot.setBackgroundPaint(new Color(255,228,196));
		    
		ChartPanel panel = new ChartPanel(chart);
		setContentPane(panel);
	}
	
	static XYDataset createDataset(LinkedList<DataPoint> dataset, int groups)
	{
	    XYSeriesCollection datasetToReturn = new XYSeriesCollection();

	    for (int i = 0; i < groups; i++)
	    {
	    	XYSeries seriesAux = new XYSeries(i); 
	    	for (int j = 0; j < dataset.size(); j++)
	    	{
	    		DataPoint dp = dataset.get ( j );
	    		if (dp.getGroup() == i)
	    		{
	    			double[] properties = dp.getProperties ();
	    			seriesAux.add ( properties[0], properties[1] );
	    		}
	    	}
	    	
	    	datasetToReturn.addSeries ( seriesAux );
	    }

	    return datasetToReturn;
	}
}
